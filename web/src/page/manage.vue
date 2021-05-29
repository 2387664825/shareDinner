<template>
    <el-container>
        <el-header>
            <header-Top></header-Top>
        </el-header>
            <div class="manage_page fillcontain">
                <el-row style="height: 100%;">
                    <el-col :span="4"  style="height: 100%; background-color: #324057;">
                        <el-menu :default-active="defaultActive" style="min-height: 100%;" theme="dark" router>
                            <el-menu-item index="manage"><i class="el-icon-menu"></i>首页</el-menu-item>
                            <el-menu-item index="userFb"><i class="el-icon-menu"></i>用户分布</el-menu-item>
                            <el-menu-item index="userList"><i class="el-icon-menu"></i>用户列表</el-menu-item>
                            <el-menu-item index="shopList"><i class="el-icon-menu"></i>商家列表</el-menu-item>
                            <el-menu-item index="foodList"><i class="el-icon-menu"></i>食品列表</el-menu-item>
                            <el-menu-item index="orderList"><i class="el-icon-menu"></i>订单列表</el-menu-item>
                            <el-menu-item index="backList"><i class="el-icon-menu"></i>反馈列表</el-menu-item>
                            <el-menu-item index="adminList"><i class="el-icon-menu"></i>管理员列表</el-menu-item>
                        </el-menu>
                    </el-col>
                    <el-col :span="20" style="height: 100%;overflow: auto">
                        <keep-alive >
                            <router-view ></router-view>
                        </keep-alive>
                    </el-col>
                </el-row>
            </div>
    </el-container>
</template>

<script>
    import headerTop from '../components/Header'
    export default {
		computed: {
			defaultActive: function(){
				return this.$route.path.replace('/', '');
			}
		},
        components: {
            headerTop,
        },
    }
</script>


<style lang="less" scoped>
	@import '../style/mixin';
	.manage_page{

	}
    .fd-main {
        height: 100%;
        background-color: #e4dede;
    }
    .fd-iframe-box {
        /*width: 100%;*/
        height: 100%;
        padding: 20px;
        background-color: white;
        border-radius: 10px;
    }
</style>
