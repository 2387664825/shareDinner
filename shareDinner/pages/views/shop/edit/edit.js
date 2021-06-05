let app = getApp();
Page({
    data: {
        shop:{
          name:'',
          phone:'',
          location:'',
          bumber:'',
          js:'',
          statusValue:'申请'
        },
        money: 0,
        isShowPicker: false,
        pics:[]
    },
    // 位置读取
    onGoWeizhi(e){
        let that = this
        wx.chooseLocation({
            success(res){
              console.log('店铺位置信息:',res)
              let value = 'shop.location'
                that.setData({
                    [value]: res.name
                })
            }
        })
    },
    onLoad: function (options) {
      const _this = this
      const id = app.globalData.user.familyId;
      if(id==null)
        return;
      wx.request({
        url: 'http://localhost:8080/api/v1/wc/family/' + id,
        header: {'Accept': 'application/json'},
        success: function (res) {
         console.log('店铺信息：',res.data)
         var data = res.data.data
         if(data.status == 1){
          data.statusValue = '待审批'
         }else if(data.status == 2){
          data.statusValue = '未通过'
         }else if(data.status == 3){
          data.statusValue = '已通过'
         }
         _this.setData({
           shop:data
         })
        }
      })
    },
    // 失去焦点获取手机号
    getPhone(e){
        this.setData({ phone: e.detail.value})
    },
    // 失去焦点获取备注
    getBeizhu(e){
      let value = 'shop.js'
        this.setData({ [value]: e.detail.value })
    },
    submit(e){
        const value = e.detail.value;
        var _this = this;
        wx.request({
          url: 'http://localhost:8080/api/v1/wc/family/insert',
          method: 'POST',
          data: {
            id:_this.data.shop.id,
            name:value.name,
            familyCount:value.familyCount,
            phone:value.phone,
            js:_this.data.shop.js,
            location:_this.data.shop.location,
            imgLocation:_this.data.shop.imgLocation,
            jkz:_this.data.shop.jkz,
            userId:app.globalData.user.id
        },
          header: {
            'Accept': 'application/json'
          },
          success: function (res) {
            wx.navigateBack()
          }
        })
    },
    onShow: function () {
    },
    // 图片
    choose: function (e) {//这里是选取图片的方法
        var that = this;
        let value ;
        let type = e.currentTarget.dataset['index'];
        if(type == 1){
           value = 'shop.imgLocation'
        }else{
          value = 'shop.jkz'
        }
        var pics = that.data.pics;
        wx.chooseImage({
          sizeType: ['original', 'compressed'], // original 原图，compressed 压缩图，默认二者都有
          sourceType: ['album', 'camera'], // album 从相册选图，camera 使用相机，默认二者都有
          success: function (res) {
            var imgsrc = res.tempFilePaths;
            console.log('选择图片路径：',imgsrc)
            that.setData({
              [value]: imgsrc,
            });
            that.uploadimg(type,value);
          },
          fail: function () {
            // fail
          },
          complete: function () {
            // complete
          }
        })
    
      },
      uploadimg: function (type,value) {//这里触发图片上传的方法
        const _this = this;
        var pics = type == 1 ? this.data.shop.imgLocation : this.data.shop.jkz;
        console.log("图片上传：",pics);
        wx.uploadFile({
          url: 'http://localhost:8080/api/v1/image/upload',//这里是你图片上传的接口
          filePath: pics[0],//这里是选取的图片的地址数组
          name:'file',
          success:function(res){
            console.log(res.data)
            var json = JSON.parse(res.data)
            
            console.log('服务端返回路径：', json)
            _this.setData({
              [value]: json.data
            })
          }, 
          fail: function (err) { 
            console.log(err) 
          } 
        });
      },
      deleteImg:function(e){
        let value 
        let type = e.currentTarget.dataset['index'];
        if(type == 1){
           value = 'shop.imgLocation'
        }else{
          value = 'shop.jkz'
        }
        this.setData({
          [value]:''
        })
      }
})