let app = getApp();

Page({
    data: {
        user: {sex:''},
        isShowPicker: false
    },
    onLoad: function () {
      console.log('用户数据：',app.globalData.user)
        this.setData({
            user:app.globalData.user
        })
        let value = 'user.sex'
        if(this.data.user.sex == 0){
          this.setData({
            [value]:'男'
          })
        }else{
          this.setData({
            [value]:'女'
          })
        }
    },
    submit(e){
        const value = e.detail.value;
        var _this = this;
        if(value.telephone.length != 11){
          wx.showToast({
            icon: 'none',
            title: '联系电话格式错误'
          })
          return
        }
        wx.request({
          url: 'http://localhost:8080/api/v1/wc/user',
          method: 'POST',
          data: {
            id:_this.data.user.id,
            name:value.name,
            sex:value.sex == '男'?0:1,
            telephone:value.telephone,
            idCard:value.idCard,
            city:value.city
        },
          header: {
            'Accept': 'application/json'
          },
          success: function (res) {
            wx.navigateBack()
          }
        })
    },
    // 位置读取
    onGoWeizhi(e){
      let that = this
      wx.chooseLocation({
          success(res){
            console.log('店铺位置信息:',res)
            let value = 'user.city'
              that.setData({
                  [value]: res.address
              })
          }
      })
  }
})