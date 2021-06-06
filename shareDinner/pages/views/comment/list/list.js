var app = getApp();
Page({
  data:{
    starAll:0,
    comments:[],
    series: [],
  },
  onLoad:function(options){
    var _this = this
    wx.request({
      url: 'http://localhost:8080/api/v1/wc/comment/list',
      method: 'GET',
      data: {
        familyId: options.id,
      },
      header: {
        'Accept': 'application/json'
      },
      success:function(res){
        console.log("评论列表：",res.data.data)
        var data = res.data.data
        var sum = 0;
        var array=[0,0,0,0,0,0];
        for(var i=0;i<data.length;i++){
          sum += data[i].star
          array[data[i].star]++
          data[i].star += '分'
        }
        var fb = []
        for(var i = 1;i<=5;i++){
          var temp = {
            name: i + '分',
            data: (array[i]/data.length*100).toFixed(0),
            number:array[i]
          }
          fb.push(temp)
        }
        _this.setData({
          comments: data,
          starAll:(sum/data.length).toFixed(1),
          series:fb
        })
      }
  })
  }
})