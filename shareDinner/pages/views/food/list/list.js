let app = getApp();
Page({
  data: {
    foods:[],               // 购物车列表
    hasList:false,          // 列表是否有数据
    selectAllStatus:true,    // 全选状态，默认全选
    selectFoods:[]
  },
  onShow() {
    let _this = this
    wx.request({
      url: 'http://localhost:8080/api/v1/wc/food/user',
      method: 'GET',
        data: {
          userId:app.globalData.user.id,
        },
        header: {
          'Accept': 'application/json'
        },
        success: function (res) {
          console.log("菜单编辑信息：",res.data.data)
          _this.setData({
            foods:res.data.data,
            hasList:true
          })
        }
    })
    
  },

  selectList(e) {
    const index = e.currentTarget.dataset.index;
    let foods = this.data.foods;
    const selected = foods[index].selected;
    foods[index].selected = !selected;
    let selectFoods = this.data.selectFoods;
    if(foods[index].selected){
      selectFoods.push(foods[index])
    }else{
      for(var i =0 ;i<selectFoods.length;i++){
        if(selectFoods[i].id == foods[index].id){
          selectFoods.splice(i,1)
          break
        }
      }
    }
    this.setData({
      foods: foods
    });
  },
   // 全选事件
  selectAll(e) {
    let selectAllStatus = this.data.selectAllStatus;
    selectAllStatus = !selectAllStatus;
    let foods = this.data.foods;
    if(selectAllStatus){
      this.setData({
        selectFoods:foods
      })
    }else{
      this.setData({
        selectFoods:[]
      })
    }
    for (let i = 0; i < foods.length; i++) {
      foods[i].selected = selectAllStatus;
    }
    this.setData({
      selectAllStatus: selectAllStatus,
      foods: foods
    });
  },
  toEdit(e){
    let foodId = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/views/food/edit/edit?id='+foodId
    })
  },
  today(){
    if(!this.data.selectFoods || this.data.selectFoods.length == 0 ){
      wx.showModal({
        title:'提示',
        showCancel:false,
        content:'请选择菜品'
    })
    return false;
    }
    console.log('选中今日菜品列表，',this.data.selectFoods)
    wx.setStorageSync('foods', this.data.selectFoods);
    wx.navigateTo({
      url: '/pages/views/food/today/today'
    })
  },
  delete:function(e){
    var _this = this;
    var id = e.currentTarget.dataset.id
    wx.showModal({
      title:'提示',
      content:'确定要删除该菜品吗？',
      showCancel:true,
      cancelText:"否",
      confirmText:"是",
      success:function(res){
        if(res.cancel){

        }else{
          wx.request({
            url: 'http://localhost:8080/api/v1/wc/food/'+id,
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
  }
})