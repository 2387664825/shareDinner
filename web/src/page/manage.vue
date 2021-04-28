<template>
	<div class="manage_page fillcontain">
        <!--<el-row style="height: 100px;">-->
            <!--<header></header>-->
        <!--</el-row>-->
		<el-row style="height: 100%;">
	  		<el-col :span="4"  style="min-height: 100%; background-color: #324057;">
				<el-menu :default-active="defaultActive" style="min-height: 100%;" theme="dark" router>
					<el-menu-item index="manage"><i class="el-icon-menu"></i>首页</el-menu-item>
                    <el-menu-item index="userList"><i class="el-icon-menu"></i>用户列表</el-menu-item>
                    <el-menu-item index="shopList"><i class="el-icon-menu"></i>商家列表</el-menu-item>
                    <el-menu-item index="foodList"><i class="el-icon-menu"></i>食品列表</el-menu-item>
                    <el-menu-item index="orderList"><i class="el-icon-menu"></i>订单列表</el-menu-item>
                    <el-menu-item index="adminList"><i class="el-icon-menu"></i>管理员列表</el-menu-item>
				</el-menu>
			</el-col>
			<el-col :span="20" style="height: 100%;overflow: auto;">
				<keep-alive>
				    <router-view></router-view>
				</keep-alive>
			</el-col>
		</el-row>
  	</div>
</template>

<script>
    import header from '../components/Header'
    export default {
		computed: {
			defaultActive: function(){
				return this.$route.path.replace('/', '');
			}
		},
    }
</script>


<style lang="less" scoped>
	@import '../style/mixin';
	.manage_page{
		
	}
</style>
