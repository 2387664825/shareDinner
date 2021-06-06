let app = getApp();
Page({
    data: {
        content: '',
        email: ''
    },
    onLoad: function (options) {
    },
    getEmail(e){
        var email = e.detail.value;
        //对电子邮件的验证
        var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        if(!myreg.test(email))
        {
             wx.showModal({
                 title:'提示',
                 showCancel:false,
                 content:'请输入正确的邮箱地址'
             })
             return false;
        }
        this.setData({
            email:e.detail.value
        })
    },
    getContent(e){
        this.setData({ content: e.detail.value })
    },
    submit(e){
        const _this = this
        wx.request({
            url: 'http://localhost:8080/api/v1/wc/back/insert',
            method: 'POST',
            data: {
              userId:app.globalData.user.id,
              content:_this.data.content,
              email:_this.data.email,
              userName:e.detail.value.name,
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
})