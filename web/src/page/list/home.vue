<template>
    <div>
		<section class="data_section">
			<header class="section_title">数据统计</header>
			<el-row :gutter="20" style="margin-bottom: 10px;">
                <el-col :span="4"><div class="data_list today_head"><span class="data_num head">当日数据：</span></div></el-col>
				<el-col :span="4"><div class="data_list"><span class="data_num">{{userCount}}</span> 新增用户</div></el-col>
				<el-col :span="4"><div class="data_list"><span class="data_num">{{shopCount}}</span> 新增商家</div></el-col>
                <el-col :span="4"><div class="data_list"><span class="data_num">{{orderCount}}</span> 新增订单</div></el-col>
			</el-row>
            <el-row :gutter="20">
                <el-col :span="4"><div class="data_list all_head"><span class="data_num head">总数据：</span></div></el-col>
                <el-col :span="4"><div class="data_list"><span class="data_num">{{allUserCount}}</span> 用户总量</div></el-col>
                <el-col :span="4"><div class="data_list"><span class="data_num">{{allshopCount}}</span> 商家总量</div></el-col>
                <el-col :span="4"><div class="data_list"><span class="data_num">{{allorderCount}}</span> 订单总量</div></el-col>
            </el-row>
		</section>
		<tendency :sevenDate='sevenDate' :sevenDay='sevenDay'></tendency>
    </div>
</template>

<script>
	import headTop from '../../components/headTop'
	import tendency from '../../components/tendency'
	import dtime from 'time-formater'
    export default {
    	data(){
    		return {
    			userCount: 0,
                shopCount: 0,
                orderCount: 0,
                allUserCount: 0,
                allshopCount: 0,
                allorderCount: 0,
    			sevenDay: [],
    			sevenDate: [],
    		}
    	},
    	components: {
    		headTop,
    		tendency,
    	},
    	mounted(){
    		this.initData();
    		for (let i = 6; i > -1; i--) {
    			const date = dtime(new Date().getTime() - 86400000*i).format('YYYY-MM-DD')
    			this.sevenDay.push(date)
    		}
    		this.getSevenData();
    	},
    	methods: {
             initData(){
                 const _this = this;
                 const url = window.fdConfig.url.feature.indexData;
                 this.$http.get(url).then(function(res){
                     var data = res.body.data;
                     _this.userCount = data[0];
                     _this.shopCount = data[1];
                     _this.orderCount = data[2];
                     _this.allUserCount = data[3];
                     _this.allshopCount = data[4];
                     _this.allorderCount = data[5];
                 },function(){
                 });
    		},
            getSevenData(){
                 const _this = this;
                const url = window.fdConfig.url.feature.orderSeven;
                this.$http.get(url).then(function(res){
                    var data = res.body.data;
                    var array = new Array(7);
                    for(var i =0 ;i<7;i++){
                        array[i] = 0;
                    }
                    var j = 0;
                    var day = new Date(data[0].createTime).getDate();
                    for(var i =0 ;i<data.length;i++){
                        var time = new Date(data[i].createTime);
                        if(time.getDate() !== day){
                            j++;
                        }
                        array[j]++;
                    }
                    console.log(array);
                     _this.sevenDate = [10,9,13,8,7,23,7];
                },function(){
                });
    		}
    	}
    }
</script>

<style lang="less">
	@import '../../style/mixin';
	.data_section{
		padding: 20px;
		margin-bottom: 40px;
		.section_title{
			text-align: center;
			font-size: 30px;
			margin-bottom: 10px;
		}
		.data_list{
			text-align: center;
			font-size: 14px;
			color: #666;
            border-radius: 6px;
            background: #E5E9F2;
            .data_num{
                color: #333;
                font-size: 26px;

            }
            .head{
                border-radius: 6px;
                font-size: 22px;
                padding: 4px 0;
                color: #fff;
                display: inline-block;
            }
        }
        .today_head{
            background: #FF9800;
        }
        .all_head{
            background: #20A0FF;
        }
	}
    .wan{
        .sc(16px, #333)
    }
</style>
