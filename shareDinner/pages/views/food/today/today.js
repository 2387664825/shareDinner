let app = getApp();
Page({
    data: {
        family:{
            beginTime:'',
            endTime:''
        },
        isShowPicker: false,
        foods:['鱼香肉丝','土豆丝','番茄蛋汤']
    },
    onLoad: function () {
        this.setData({
            foods:wx.getStorageSync('foods')
        })
    },
    submit(e){
        const _this = this
        if(!_this.data.family.beginTime){
            wx.showModal({
                title:'提示',
                showCancel:false,
                content:'请输入开始时间'
            })
            return false;
        }
        if(!_this.data.family.endTime){
            wx.showModal({
                title:'提示',
                showCancel:false,
                content:'请输入结束时间'
            })
            return false;
        }
        if(!e.detail.value.price){
            wx.showModal({
                title:'提示',
                showCancel:false,
                content:'请输入就餐价格'
            })
            return false;
        }
        if(!e.detail.value.number){
            wx.showModal({
                title:'提示',
                showCancel:false,
                content:'请输入就餐人数'
            })
            return false;
        }
        wx.request({
            url: 'http://localhost:8080/api/v1/wc/food/today',
            method: 'POST',
            data: {
              familyId:app.globalData.user.familyId,
              beginTime:_this.data.family.beginTime,
              endTime:_this.data.family.endTime,
              number:e.detail.value.number,
              price:e.detail.value.price,
              foods:_this.data.foods
            },
            header: {
              'Accept': 'application/json'
            },
            success: function (res) {
              wx.navigateBack()
            }
        })
    },
    bindBeginTimeChange: function(e) {
        let value = 'family.beginTime'
        this.setData({
          [value]: e.detail.value
        })
      },
      bindEndTimeChange: function(e) {
        let value = 'family.endTime'
        this.setData({
          [value]: e.detail.value
        })
      },
})