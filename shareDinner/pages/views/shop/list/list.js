let app = getApp();
Page({
    data: {
      category:[],
      family: {},
      status: 0,
      curIndex: 0,
      sc:false,
      type:'',
      showModalStatus: false,
      toView: 'guowei',
      count:0,
      yy:0,
      bz:''
    },
    //初始化
    onLoad: function (options) {
      console.log("店铺id:",options.id)
      var _this = this;
      if(app.globalData.auth == 1 && options.id == app.globalData.user.familyId  ){
        this.setData({
          type: 1
        })
      }
      wx.request({
          url:'http://localhost:8080/api/v1/wc/category/' + options.id,
          success(res){
            console.log("店铺目录：",res.data.data)
            _this.setData({
                  category : res.data.data
              })
           }
      });
      wx.request({
        url:'http://localhost:8080/api/v1/wc/family/' + options.id,
        success(res){
          console.log("店铺详情",res.data.data)
          _this.setData({
            family : res.data.data,
            count:res.data.data.receiveCount,
            yy:res.data.data.yy
            })
         }
    });
    wx.request({
      url: 'http://localhost:8080/api/v1/wc/order/one',
      method: 'GET',
      data: {
        familyId:options.id,
        userId:app.globalData.user.id
      },
      header: {
        'Accept': 'application/json'
      },
      success: function (res) {
        console.log("订单信息",res.data.data)
        if(res.data.status == 200){
          _this.setData({
            status:res.data.data.status
          })
        }
      }
    })
    wx.request({
      url: 'http://localhost:8080/api/v1/wc/user/sc',
      method: 'GET',
      data: {
        familyId:options.id,
        userId:app.globalData.user.id
      },
      header: {
        'Accept': 'application/json'
      },
      success: function (res) {
        console.log("收藏信息",res.data.data)
        if(res.data.status == 200){
          _this.setData({
            sc:res.data.data
          })
        }
      }
    })
    },
    getBeizhu:function(e){
      var value = e.detail.value;
      console.log('备注触发：',value)
      this.setData({
        bz:value
      })
    },
    // 预约订单
    submitOrder:function(){
      console.log("提交订单")
      const _this = this;
      wx.request({
        url: 'http://localhost:8080/api/v1/wc/order',
        method: 'POST',
        data: {
          familyId:_this.data.family.id,
          userId:app.globalData.user.id,
          price:_this.data.family.price,
          bz:_this.data.bz
        },
        header: {
          'Accept': 'application/json'
        },
        success: function (res) {
          _this.setData({
            status:1
          })
          _this.showModalStatus()
        }
      })
    },
    cancelOrder(){
      const _this = this
      wx.showModal({
        title: '提示',
        content: '确定要取消吗？',
        success: function (sm) {
          if (sm.confirm) {
              _this.cancelData()
          } else if (sm.cancel) {
            console.log('用户点击取消')
          }
        }
      })
    },
   cancelData(){
      const _this = this;
      wx.request({
        url: 'http://localhost:8080/api/v1/wc/order/cancel',
        method: 'POST',
        data: {
          familyId:_this.data.family.id,
          userId:app.globalData.user.id,
        },
        header: {
          'Accept': 'application/json'
        },
        success: function (res) {
          _this.setData({
            status:0
          })
        }
      })
    },
        // 关注
        concern:function(){
          console.log("关注店铺")
          const _this = this;
          wx.request({
            url: 'http://localhost:8080/api/v1/wc/user/concern',
            method: 'GET',
            data: {
              familyId:_this.data.family.id,
              userId:app.globalData.user.id,
              type :1
            },
            header: {
              'Accept': 'application/json'
            },
            success: function (res) {
              _this.setData({
                sc:true
              })
            }
          })
        },
        cancelConcern(){
          const _this = this
          wx.showModal({
            title: '提示',
            content: '确定要取消关注吗？',
            success: function (sm) {
              if (sm.confirm) {
                  _this.cancelDataConcern()
              } else if (sm.cancel) {
                console.log('用户点击取消')
              }
            }
          })
        },
       cancelDataConcern(){
          const _this = this;
          wx.request({
            url: 'http://localhost:8080/api/v1/wc/user/concern',
            method: 'GET',
            data: {
              familyId:_this.data.family.id,
              userId:app.globalData.user.id,
              type: 0
            },
            header: {
              'Accept': 'application/json'
            },
            success: function (res) {
              _this.setData({
                sc:false
              })
            }
          })
        },
    switchTab(e){
      const self = this;
      this.setData({
        isScroll: true
      })
      setTimeout(function(){
        self.setData({
          toView: 'a' + e.target.dataset.id,
          curIndex: e.target.dataset.index
        })
      },0)
      setTimeout(function () {
        self.setData({
          isScroll: false
        })
      },1)
    },
    // 页面跳转
    showModalStatus: function () {
      this.setData({
        showModalStatus: !this.data.showModalStatus,
      });
    },
    redTo: function(e) {
      let shopdetail = e.currentTarget.dataset.value 
      console.log('菜品详情：',shopdetail)
      wx.setStorage({
        data: shopdetail,
        key: 'shopDetail',
      })
      wx.navigateTo({
        url: '/pages/views/shop/details/details'
      })
    },
    toComment: function(e) {
      wx.navigateTo({
        url: '/pages/views/comment/list/list?id=' + this.data.family.id
      })
    }
    
})