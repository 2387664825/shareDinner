const app = getApp()
Page({
  data: {
    business: [],
  },
  onShow:function(res){
    var _this = this
    wx.request({
      url: 'http://localhost:8080/api/v1/wc/family/sc',
      method: 'GET',
      data: {
        userId: app.globalData.user.id
        },
      header: {'Accept': 'application/json'},
      success:function(res){
        console.log('店铺关注列表：', res.data)
        var data = res.data.data
        _this.setData({
          business:data
        })
      }
    })
  },
  // 页面跳转
  redTo: function(e) {
    let shopid = e.currentTarget.dataset.shop
    wx.navigateTo({
      url: '/pages/views/shop/list/list?id='+shopid
    })
  },
})