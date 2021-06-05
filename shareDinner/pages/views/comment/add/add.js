let app = getApp();
Page({
    data: {
        total: 5,
        isShowPicker: false,
        family:{},
        pj:'',
        orderId:''
    },
    onLoad: function (options) {
        var _this = this;
        this.setData({
          orderId:options.id
        })
        wx.request({
            url:'http://localhost:8080/api/v1/wc/family/' + options.shopid,
            success(res){
              console.log("店铺详情",res.data.data)
              _this.setData({
                family : res.data.data
                })
             }
        });
    },
    getPj:function(e){
      this.setData({
        pj:e.detail.value
      })
    },
    submit(e){
        const _this = this
        wx.request({
            url: 'http://localhost:8080/api/v1/wc/comment',
            method: 'POST',
            data: {
              familyId:_this.data.family.id,
              userId:app.globalData.user.id,
              content:_this.data.pj,
              star:_this.data.total,
              orderId:_this.data.orderId
            },
            header: {
              'Accept': 'application/json'
            },
            success: function (res) {
              wx.navigateBack()
            }
        })
    },
    onShow: function () {
    },
    img_bind: function(event) {
        var id = event.currentTarget.dataset.item + 1;
        console.log(id)
        this.setData({
          total: id,
        })
      },
})