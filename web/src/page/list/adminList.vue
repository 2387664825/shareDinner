<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
            <el-table
		      :data="tableData"
		      style="width: 100%">
		      <el-table-column
		        prop="name"
		        label="姓名"
		        width="180">
		      </el-table-column>
		      <el-table-column
		        prop="createTime"
		        label="注册日期"
		        width="220">
		      </el-table-column>
              <el-table-column
                prop="city"
                label="地址"
                width="180">
              </el-table-column>
		      <el-table-column
		        prop="admin"
		        label="权限">
		      </el-table-column>
		    </el-table>
		    <div class="Pagination" style="text-align: left;margin-top: 10px;">
                <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="currentPage"
                  :page-size="20"
                  layout="total, prev, pager, next"
                  :total="count">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    import headTop from '../../components/headTop'
    import {adminList, adminCount} from '@/api/getData'
    export default {
        data(){
            return {
                tableData: [],
                currentRow: null,
                offset: 0,
                limit: 15,
                count: 0,
                currentPage: 1,
            }
        },
    	components: {
    		headTop,
    	},
        created(){
            this.getAdmin();
        },
        methods: {
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.offset = (val - 1)*this.limit;
                this.getAdmin()
            },
            getAdmin(){
                const url = window.fdConfig.url.feature.admin;
                var params={
                    offset:this.offset,
                    limit:this.limit
                };
                const _this = this;
                this.$http.get(url,{
                    params:params
                }).then(function(res){
                    var data = res.body;
                    console.log('管理员列表',data);
                    _this.count = data.pageInfo.rowCount;
                    _this.tableData = data.data;
                },function(){
                    console.log('请求失败处理');
                });
            }
        },
    }
</script>

<style lang="less">
	@import '../../style/mixin';
    .table_container{
        padding: 20px;
    }
</style>


