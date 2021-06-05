// app.js
App({
  globalData: {
    userInfo: null,
    user:null,
    auth:false
  },
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    let _this = this;
    // 登录
    wx.login({
      success: res => {
        if(res.code){
          wx.request({
            url: 'http://localhost:8080/api/v1/wc/user',
            method: 'GET',
            data: { code:res.code},
            header: {'Accept': 'application/json'},
            success: function (res) {
              _this.globalData.auth = res.data.type,
              _this.globalData.user = res.data
              console.log('用户登录完成！')
            }
          })
        }else{
          console.log("获取用户登录态失败！"+res.errMsg)
        }
      }
    })
    // 获取用户信息
    // wx.getSetting({
    //   success: res => {
    //     if (res.authSetting['scope.userInfo']) {
    //       // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
    //       wx.getUserInfo({
    //         success: res => {
    //           // 可以将 res 发送给后台解码出 unionId
    //           this.globalData.userInfo = res.userInfo
              
    //           // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
    //           // 所以此处加入 callback 以防止这种情况
    //           if (this.userInfoReadyCallback) {
    //             this.userInfoReadyCallback(res)
    //           }
    //         }
    //       })
    //     }
    //   }
    // })
  },
})
