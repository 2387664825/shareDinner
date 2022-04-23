<template>
    <div class="fillcontain">
        <el-row class="query">
            <el-col :span="8">
                食品名称：
                <el-input
                    placeholder="请输入内容"
                    prefix-icon="el-icon-search"
                    v-model="query.name" style="width:190px;">
                </el-input>
            </el-col>
            <el-col :span="8">
                店家名称：
                <el-input
                    placeholder="请输入内容"
                    prefix-icon="el-icon-search"
                    v-model="query.familyName" style="width:190px;">
                </el-input>
            </el-col>
            <el-col :span="8">
                <el-button type="primary"  @click="getFoods">查询</el-button>
                <el-button type="primary"  @click="resetData" plain>重置</el-button>
            </el-col>
        </el-row>
        <div class="table_container">
            <el-table
                :data="tableData"
                style="width: 100%">
                <el-table-column
                    type="index"
                    width="100px">
                </el-table-column>
                <el-table-column
                  label="食品名称"
                  prop="name">
                </el-table-column>
                <el-table-column
                  label="食品介绍"
                  prop="js"
                  :show-overflow-tooltip='true'>
                </el-table-column>
                <el-table-column
                  label="店家名称"
                  prop="familyName">
                </el-table-column>
                <el-table-column label="操作" width="160">
                  <template slot-scope="scope">
                    <el-button
                      size="small"
                      @click="handle(scope.row)">详情</el-button>
                    <el-button
                      size="small"
                      type="danger"
                      @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                  </template>
                </el-table-column>
            </el-table>
            <div class="Pagination">
                <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="currentPage"
                  :page-size="15"
                  layout="total, prev, pager, next"
                  :total="count">
                </el-pagination>
            </div>
        </div>
        <el-dialog title="食品详情" v-model="dialogFormVisible" >
            <el-form :model="selectTable">
                <el-form-item label="食品名称：" label-width="100px">
                    <label >{{selectTable.name}}</label>
                </el-form-item>
                <el-form-item label="所属店铺：" label-width="100px">
                    <label >{{selectTable.familyName}}</label>
                </el-form-item>
                <el-form-item label="食品分类：" label-width="100px">
                    <label >{{selectTable.categoryName}}</label>
                </el-form-item>
                <el-form-item label="食品简述：" label-width="100px">
                    <label >{{selectTable.js}}</label>
                </el-form-item>
                <el-form-item label="食品图片：" label-width="100px">
                    <img  :src="selectTable.picture" class="avatar">
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    import headTop from '../../components/headTop'
    export default {
        data(){
            return {
                query:{name:'',familyName:''},
                offset: 0,
                limit: 15,
                count: 0,
                tableData: [],
                currentPage: 1,
                selectTable: {},
                dialogFormVisible: false
            }
        },
        created(){
            this.getFoods();
        },
    	components: {
    		headTop,
    	},
        methods: {
            resetData(){
                this.query = {};
                this.getFoods();
            },
            getFoods(){
                const url = window.fdConfig.url.feature.food;
                var params={
                    offset:this.offset,
                    limit:this.limit
                };
                const _this = this;
                if(this.query.name!==''){params.name = this.query.name;}
                if(this.query.familyName!==''){params.familyName = this.query.familyName;}
                this.$http.get(url,{
                    params:params
                }).then(function(res){
                    var data = res.body;
                    console.log('菜品列表',data);
                    _this.count = data.pageInfo.rowCount;
                    _this.tableData = data.data;
                },function(){
                    console.log('请求失败处理');
                });
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.offset = (val - 1)*this.limit;
                this.getFoods()
            },
            handle(row) {
                this.selectTable = row;
                this.dialogFormVisible = true;
            },
             handleDelete(index, row) {
            },
        },
    }
</script>

<style lang="less">
	@import '../../style/mixin';
    .query{
        margin: 20px 20px 0 20px;
    }
    .query .el-col {
        min-height: 1px;
    }
    .query .el-button {
        width: 100px;
    }
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
    .table_container{
        padding: 20px;
    }
    .Pagination{
        display: flex;
        justify-content: flex-start;
        margin-top: 8px;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #20a0ff;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 120px;
        height: 120px;
        line-height: 120px;
        text-align: center;
    }
    .avatar {
        width: 120px;
        height: 120px;
        display: block;
    }
</style>
