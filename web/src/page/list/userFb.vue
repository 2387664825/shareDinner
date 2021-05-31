<template>
    <div>
		<section class="data_section">
			<header class="section_title">全国注册用户分布</header>
			<el-row :gutter="20" style="margin-bottom: 10px;">
			</el-row>
            <el-row :gutter="20">
            </el-row>
		</section>
		<userMap :sevenDate='sevenDate' ></userMap>
    </div>
</template>

<script>
    import headTop from '../../components/headTop'
	import visitor from '../../components/visitorPie'
    import userMap from '../../components/userMap'
	import dtime from 'time-formater'
    export default {
    	data(){
    		return {
                sevenDate :[]
    		}
    	},
    	components: {
    		headTop,
            visitor,
            userMap
    	},
    	mounted(){
    		this.initData();
    	},
    	methods: {
    		 initData(){
                 let url = window.fdConfig.url.feature.userFb;
                 const _this = this;
                 this.$http.get(url).then(function(res){
                     var data = res.body.data;
                     url = window.fdConfig.url.feature.province;
                     this.$http.get(url).then(function(res){
                         var data2 = res.body.data;
                         var array = [];
                         for(var i =0 ;i<data.length;i++){
                             array.push({name:data2[i].name,value:data[i]});
                         }
                         console.log("数据：",array);
                         _this.sevenDate = array;
                     },function(){
                     });
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
