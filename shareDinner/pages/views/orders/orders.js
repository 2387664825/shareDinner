var app = getApp();
Page({
  data:{
    orders:[ ],
    type:''
  },
  onShow:function(){
    var _this = this
    this.setData({
      type:app.globalData.user.type
    })
  wx.request({
    url: 'http://localhost:8080/api/v1/wc/order/user',
    method: 'GET',
    data: {
      userId: app.globalData.user.id,
      type:app.globalData.user.type,
      familyId:app.globalData.user.type == 1?app.globalData.user.familyId :0
    },
    header: {
      'Accept': 'application/json'
    },
    success: function (res) {
      var data = res.data.data
      console.log("用户订单：",data)
      for(var i =0 ;i<data.length;i++){
        data[i].price += '元'
        if(data[i].status == 0 ){
          data[i].statusValue = '已取消'
          data[i].btnValue = ''
        }else if(data[i].status == 1 ){
          data[i].statusValue = '待完成'
          if(_this.data.type == 0){
            data[i].btnValue = '取消'
          }else{
            data[i].btnValue = '已就餐'
          }
        }else if(data[i].status == 2){
          data[i].statusValue = '已就餐'
          if(_this.data.type == 0){
            data[i].btnValue = '付款'
          }else{
            data[i].btnValue = ''
          }
        }else if(data[i].status == 3){
          data[i].statusValue = '已付款'
          if(_this.data.type == 0){
            data[i].btnValue = '评价'
          }else{
            data[i].btnValue = ''
          }
        }else{
          data[i].statusValue = '已评价'
          data[i].btnValue = ''
        }
      }
      _this.setData({
        orders:data
      })
    }
  })
  },
  handle:function(e){
    let index = e.currentTarget.dataset.index
    var item = this.data.orders[index]
    const _this = this
    if(item.status == 1 ){
      if(_this.data.type == 0){
        _this.cancel(item.id)
      }else{
        _this.toDd(item.id)
      }
    }else if(_this.data.type == 0 && item.status == 2){
      _this.toPay(item.id,item.familyId)
    }else if(_this.data.type == 0 && item.status == 3){
      _this.toComment(item.id,item.familyId)
    }
  },
  toPay(id,familydId) {
    const _this = this;
    wx.request({
      url: 'http://localhost:8080/api/v1/wc/order/update',
      method: 'POST',
      data: {
        id:id,
        status:3,
        familyId:familydId
      },
      header: {
        'Accept': 'application/json'
      },
      success: function (res) {
          wx.showToast({
            title: '付款成功',
            icon: 'success',     //默认值是success,就算没有icon这个值，就算有其他值最终也显示success
            duration: 1000,      //停留时间
          })
        _this.onShow()
      }
    })
  },
  toDd(id) {
    const _this = this;
    wx.request({
      url: 'http://localhost:8080/api/v1/wc/order/update',
      method: 'POST',
      data: {
        id:id,
        status:2
      },
      header: {
        'Accept': 'application/json'
      },
      success: function (res) {
        _this.onShow()
      }
    })
  },
  toComment: function(id,shopid) {
    console.log('评价shopId：',shopid)
    wx.navigateTo({
      url: '/pages/views/comment/add/add?id='+id+'&shopid='+shopid
    })
  },
  cancel:function(id){
    const _this = this;
    wx.request({
      url: 'http://localhost:8080/api/v1/wc/order/update',
      method: 'POST',
      data: {
        id:id,
        status:0
      },
      header: {
        'Accept': 'application/json'
      },
      success: function (res) {
        _this.onShow()
      }
    })
  }
})