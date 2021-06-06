// page/component/details/details.js
Page({
  data:{
    food:{},
    curIndex: 0,
    show: false,
    scaleCart: false
  },
  onLoad(option) {
    var _this = this
    wx.getStorage({
      key: 'shopDetail',
      success:function(res){
        _this.setData({
          food:res.data
        })
      }
    })
  },
  bindTap(e) {
    const index = parseInt(e.currentTarget.dataset.index);
    this.setData({
      curIndex: index
    })
  }
 
})