var app = getApp();
Page({
  data: {
    categorys: [],
    category:{},
    showModalStatus: false,
  },
  onShow() {
    const _this = this;
    wx.request({
      url: 'http://localhost:8080/api/v1/wc/category/family',
      method: 'GET',
      data: {id:app.globalData.user.familyId},
      header: {
        'Accept': 'application/json'
      },
      success: function (res) {
        if (res.data.data) {
          console.log('分类信息：',res.data.data)
          _this.setData({
            categorys: res.data.data,
          });
        } 
      }
    })
  },
  showModalStatus: function (event) {
    const id = event.currentTarget.dataset.id
    if(id){
      for(var i=0;i<this.data.categorys.length;i++){
        if(this.data.categorys[i].id == id){
          this.setData({
            category:this.data.categorys[i]
          })
        }
      }
    }else{
      this.setData({
        category:{}
      })
    }
    this.setData({
      showModalStatus: !this.data.showModalStatus,
    });
  },
  delete:function(e){
    var _this = this;
    var id = e.currentTarget.dataset.id
    wx.showModal({
      title:'提示',
      content:'确定要删除该分类吗？',
      showCancel:true,
      cancelText:"否",
      confirmText:"是",
      success:function(res){
        if(res.cancel){

        }else{
          wx.request({
            url: 'http://localhost:8080/api/v1/wc/category/'+id,
            method: 'DELETE',
            data: {},
            header: {
              'Accept': 'application/json'
            },
            success: function (res) {
              _this.onShow()
            }
          })
        }
      }
    })
  },
  submit(e){
    const _this = this
    const value = e.detail.value;
    wx.request({
        url: 'http://localhost:8080/api/v1/wc/category',
        method: 'POST',
        data:{
            id:_this.data.category.id,
            name:value.name,
            sort: value.sort,
            familyId:app.globalData.user.familyId
        },
        header: {
          'Accept': 'application/json'
        },
        success: function (res) {
         _this.onShow()
         _this.setData({
          showModalStatus: false,
        });
        }
    })
},
});
