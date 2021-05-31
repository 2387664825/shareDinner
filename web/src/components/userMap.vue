<template>
    <div class="visitorpie">
        <div id="mapChart" class="" style="width: 90%;height:500px;"></div>
    </div>
</template>

<script>
    import echarts from 'echarts/lib/echarts';
    // 引入柱状图
    import 'echarts/lib/component/title';
    import 'echarts/lib/component/legend';
    import "echarts/map/js/china.js"

    export default {
        mounted() {
            this.myChart = echarts.init(document.getElementById('mapChart'));
            this.getMap();
        },
        props: ['sevenDate'],
        methods: {
            getMap() {
                const option = {
                    title: {
                        text: ''
                    },
                    backgroundColor: '',
                    tooltip: {
                        trigger: 'item',
                        formatter: '{b}: {c}'
                    }, // 鼠标移到图里面的浮动提示框
                    dataRange: {
                        show: true,
                        min: 0,
                        max: 100,
                        range: [0, 100],
                        text: ['', ''],
                        realtime: false,
                        calculable: true,
                        startAngle: 0,
                        inRange: {
                            color: ["#3dda8e", "#eac736", "#d94e5d"]
                        },
                        orient: 'horizontal',
                        itemWidth: '15',
                        itemHeight: '120',
                        left: 20,
                        bottom: 20,
                        precision: 0  // 数据展示的小数精度
                    },
                    geo: { // 这个是重点配置区
                        map: 'china', // 表示中国地图
                        roam: true,
                        z: '2',
                        scaleLimit: {
                            min: 1.8,
                            max: 1.8
                        },
                        label: {
                            normal: {
                                show: false, // 是否显示对应地名
                                textStyle: {
                                    color: '#c3e5dc'
                                }
                            }
                        },
                        itemStyle: {
                            normal: {
                                borderColor: 'rgba(0, 0, 0, 0.2)'
                                // areaColor: '#ededed' // 地图背景色
                            },
                            emphasis: {
                                areaColor: null,
                                shadowOffsetX: 0,
                                shadowOffsetY: 0,
                                shadowBlur: 20,
                                borderWidth: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        },
                        right: '500',
                        left: 'auto',
                        bottom: '0'
                    },
                    series: [
                        // {
                        //     type: 'scatter',
                        //     coordinateSystem: 'geo' // 对应上方配置
                        // },
                        {
                            name: '', // 浮动框的标题
                            type: 'map',
                            geoIndex: 0,
                            data: this.sevenDate
                        }
                    ]
                };
                this.myChart.setOption(option);
            }
        },
        watch: {
            sevenDate: function () {
                console.log("初始化");
                this.getMap()
            }
        }
    }
</script>

<style lang="less">
	@import '../style/mixin';
    /*.visitorpie{*/
        /*display: flex;*/
        /*justify-content: center;*/
        /*margin-top: 20px;*/
    /*}*/
</style>
