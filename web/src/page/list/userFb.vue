<template>
    <div>
		<section class="data_section">
			<header class="section_title">数据统计</header>
			<el-row :gutter="20" style="margin-bottom: 10px;">
			</el-row>
            <el-row :gutter="20">
            </el-row>
		</section>
		<userMap :pieData='userData' ></userMap>
    </div>
</template>

<script>
    import headTop from '../../components/headTop'
	import visitor from '../../components/visitorPie'
    import userMap from '../../components/userMap'
	import dtime from 'time-formater'
	import {userCount, orderCount, getUserCount, getOrderCount, adminDayCount, adminCount} from '@/api/getData'
    export default {
    	data(){
    		return {
                userData: {beijing:10,shanghai:20,hangzhou:20,qita:20,shenzhen:20},
    		}
    	},
    	components: {
    		headTop,
            visitor,
            userMap
    	},
    	mounted(){
    		this.initData();
    		this.getSevenData();
    	},
    	methods: {
    		async initData(){
    			const today = dtime().format('YYYY-MM-DD')
    			// Promise.all([userCount(today), orderCount(today), adminDayCount(today), getUserCount(), getOrderCount(), adminCount()])
    			// .then(res => {
    			// 	this.userCount = res[0].count;
    			// 	this.orderCount = res[1].count;
                //     this.adminCount = res[2].count;
                //     this.allUserCount = res[3].count;
                //     this.allOrderCount = res[4].count;
                //     this.allAdminCount = res[5].count;
    			// }).catch(err => {
    			// 	console.log(err)
    			// })
    		},
    		async getSevenData(){
    			// const apiArr = [[],[],[]];
    			// this.sevenDay.forEach(item => {
    			// 	apiArr[0].push(userCount(item));
    			// 	apiArr[1].push(orderCount(item));
                //     apiArr[2].push(adminDayCount(item))
    			// });
    			// const promiseArr = [...apiArr[0], ...apiArr[1], ...apiArr[2]];
    			// Promise.all(promiseArr).then(res => {
    			// 	const resArr = [[],[],[]];
				// 	res.forEach((item, index) => {
				// 		if (item.status === 1) {
				// 			resArr[Math.floor(index/7)].push(item.count)
				// 		}
				// 	});
				// 	this.sevenDate = resArr;
    			// }).catch(err => {
    			// 	console.log(err)
    			// })
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
