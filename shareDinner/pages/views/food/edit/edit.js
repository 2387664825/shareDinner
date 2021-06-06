let app = getApp();
Page({
    data: {
        food:{picture:''},
        type:'',
        category:[],
        index:0,
        pics:[]
    },
    onLoad: function (options) {
        var _this = this;
        wx.request({
            url:'http://localhost:8080/api/v1/wc/category/family?id=' + app.globalData.user.familyId,
            success(res){
              console.log("菜品分类：",res.data.data)
              _this.setData({
                category : res.data.data
             })
             if(options.type == 1){
                _this.setData({
                    type:options.type
                })
                return
            }
            console.log("菜品id:",options.id)
            _this.initFood(options.id)
             }
        });
    },
    initFood: function(id){
        const _this = this;
        wx.request({
            url:'http://localhost:8080/api/v1/wc/food/' +id,
            success(res){
              console.log("菜品详细信息：",res.data.data)
              var index = 0
              for(var i =0 ;i<_this.data.category.length;i++){
                  if(_this.data.category[i].id == res.data.data.categoryId){
                        index = i
                        console.log('找到对应目录index',i)
                        break
                  }
              }
              _this.setData({
                    food : res.data.data,
                    index: index
                })
             }
        });
    },
    bindPickerChange: function(e) {
        console.log('picker发送选择改变，携带值为', e.detail.value)
        this.setData({
            index: e.detail.value
        })
    },
    onShow: function () {
    },
    // 失去焦点获取手机号
    getPhone(e){
        this.setData({ phone: e.detail.value})
    },
    // 失去焦点获取备注
    getBeizhu(e){
        this.setData({ beizhu: e.detail.value })
    },
    // 图片
    choose: function (e) {//这里是选取图片的方法
        var that = this;
        let value ='food.picture'
        wx.chooseImage({
            sizeType: ['original', 'compressed'], // original 原图，compressed 压缩图，默认二者都有
            sourceType: ['album', 'camera'], // album 从相册选图，camera 使用相机，默认二者都有
            success: function (res) {
            var imgsrc = res.tempFilePaths;
            console.log('选择图片路径：',imgsrc)
            that.setData({
                [value]: imgsrc,
            });
            that.uploadimg();
            },
            fail: function () {
            // fail
            },
            complete: function () {
            // complete
            }
        })
    
        },
        uploadimg: function () {//这里触发图片上传的方法
            const _this = this;
            var pics = this.data.food.picture;
             let value ='food.picture'
            console.log("图片上传：",pics);
            wx.uploadFile({
              url: 'http://localhost:8080/api/v1/image/upload',//这里是你图片上传的接口
              filePath: pics[0],//这里是选取的图片的地址数组
              name:'file',
              success:function(res){
                  var body = res.data
                   var json = JSON.parse(body)
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
            let value = 'food.picture'
            this.setData({
              [value]:''
            })
          },
    submit(e){
        const _this = this
        const value = e.detail.value;
        wx.request({
            url: 'http://localhost:8080/api/v1/wc/food',
            method: 'POST',
            data:{
                id:_this.data.food.id,
                name:value.name,
                categoryId: _this.data.category[_this.data.index].id,
                price : value.price,
                picture:_this.data.food.picture,
                js:value.js,
                familyId:app.globalData.user.familyId
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