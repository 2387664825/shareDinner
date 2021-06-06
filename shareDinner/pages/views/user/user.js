let app = getApp();
Page({
  data:{
    auth:'',
    shopId:''
  },
  onLoad(){
    if(app.globalData.user != ''){
      this.setData({
        auth: app.globalData.auth,
        shopId:app.globalData.user.familyId
      })
    }
  },
  onShow(){
    if(this.data.auth == ''){
      this.onLoad()
    }
  },
  toShopEdit: function(e) {
    wx.navigateTo({
      url: '/pages/views/shop/edit/edit'
    })
  },
  toFood: function(e) {
    wx.navigateTo({
      url: '/pages/views/food/list/list'
    })
  },
  toFoodAdd: function(e) {
    wx.navigateTo({
      url: "/pages/views/food/edit/edit?type=1",
    })
  },
  toCategory: function(e) {
    wx.navigateTo({
      url: '/pages/views/category/category'
    })
  },
  toComment: function(e) {
    wx.navigateTo({
      url: '/pages/views/comment/list/list?id='+ app.globalData.user.familyId
    })
  },
  toConcern: function(e) {
    wx.navigateTo({
      url: '/pages/views/concern/concern'
    })
  },
  toShop: function(e) {
    wx.navigateTo({
      url: '/pages/views/shop/edit/edit'
    })
  },
  toBack: function(e) {
    wx.navigateTo({
      url: '/pages/views/back/back'
    })
  }
})