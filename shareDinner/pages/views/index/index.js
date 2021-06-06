const app = getApp()
Page({
  data: {
    imgUrls: [
      '/image/h1.jpg',
      '/image/h2.jpg',
      '/image/h3.jpg'
    ],
    // 商店数据
    business: [],
    search:'',
    inputShowed:false,
    indicatorDots: false,
    autoplay: false,
    interval: 3000,
    duration: 800,
  },
  onLoad:function(res){
    var _this = this
    wx.getLocation({
      type:'wgs84',
      success(res){
        console.log("当前登录用户位置信息：",res)
        wx.request({
          url: 'http://localhost:8080/api/v1/wc/family/qy',
          method: 'GET',
          data: {
            x: res.latitude,
            y: res.longitude,
            search:_this.data.search
            },
          header: {'Accept': 'application/json'},
          success:function(res){
            console.log('店铺信息列表：', res.data.data)
            var data = res.data.data
            for(var i =0;i<data.length;i++){
              data[i].jl = data[i].jl.toFixed(2)+'km'
            }
            _this.setData({
              business:data
            })
          }
        })
      }
    })
  },
  // 搜索
  search:function(){
    console.log(this.data.search)
    this.onLoad()
  },
  // 页面跳转
  redTo: function(e) {
    let shopid = e.currentTarget.dataset.shop
    wx.navigateTo({
      url: '/pages/views/shop/list/list?id='+shopid
    })
  },
  /*  搜索框样式调整 */
  showInput: function () {
    this.setData({
    inputShowed: true
    });
  },
  hideInput: function () {
    this.setData({
    inputVal: "",
    inputShowed: false
    });
    // getList(this);
  },
  clearInput: function () {
    this.setData({
      search: ""
    });
    // getList(this);
  },
  inputTyping: function (e) {
    this.setData({
    search: e.detail.value
    });
  }
})