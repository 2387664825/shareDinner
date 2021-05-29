<template>
    <div class="fillcontain">
        <el-row class="query">
            <el-col :span="8">
                用户名称：
                <el-input
                    placeholder="请输入内容"
                    prefix-icon="el-icon-search"
                    v-model="query.name" style="width:190px;">
                </el-input>
            </el-col>
            <el-col :span="8">
                状态：
                <el-select v-model="query.type" placeholder="请选择">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select>
            </el-col>
            <el-col :span="8">
                <el-button type="primary"  @click="getBacks">查询</el-button>
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
                  label="用户名称"
                  prop="userName">
                </el-table-column>
                <el-table-column
                  label="内容"
                  prop="content"
                  :show-overflow-tooltip='true'>
                </el-table-column>
                <el-table-column
                  label="状态"
                  prop="typeValue">
                </el-table-column>
                <el-table-column
                    label="时间"
                    prop="createTime">
                </el-table-column>
                <el-table-column label="操作" width="160">
                  <template slot-scope="scope">
                    <el-button
                      size="small"
                      type="primary"
                      v-if="!scope.row.type"
                      @click="handleEdit(scope.row,true)">处理</el-button>
                      <el-button
                          size="small"
                          v-if="scope.row.type"
                          @click="handleEdit(scope.row,false)">详情</el-button>
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
            <el-dialog title="工单处理" v-model="dialogFormVisible">
                <el-form :model="selectTable">
                    <el-row class="query">
                        <el-col :span="12">
                            <el-form-item label="用户名称：" label-width="100px">
                                <label :title="selectTable.userName" style="display:inline-block;width: 400px;">{{selectTable.userName}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="用户邮箱：" label-width="100px">
                                <label :title="selectTable.email" style="display:inline-block;width: 400px;">{{selectTable.email}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="创建时间：" label-width="100px">
                                <label :title="selectTable.createTime" style="display:inline-block;width: 400px;">{{selectTable.createTime}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24" >
                            <el-form-item label="内容：" label-width="100px">
                                <label :title="selectTable.content" style="display:inline-block;width: 400px;">{{selectTable.content}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24" >
                            <el-form-item v-if="!falg" label="回复内容：" label-width="100px">
                                <label :title="selectTable.backContent" style="display:inline-block;width: 400px;">{{selectTable.backContent}}</label>
                            </el-form-item>
                            <el-form-item label="回复：" label-width="100px"  v-if="falg">
                                <el-input v-model="selectTable.backContent"  type="textarea" :rows="3" :max='300' style="width: 400px;"  show-word-lmit></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
              <div slot="footer" class="dialog-footer" v-if="falg">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateBack">确 定</el-button>
              </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        data(){
            return {
                query:{name:'',type:'',},
                offset: 0,
                limit: 15,
                count: 0,
                tableData: [],
                currentPage: 1,
                selectTable: {},
                dialogFormVisible: false,
                falg:true,
                options: [{
                    value: '0',
                    label: '未反馈'
                }, {
                    value: '1',
                    label: '已反馈'
                }]
            }
        },
        created(){
            this.getBacks();
        },
    	components: {
    		headTop,
    	},
        methods: {
            getBacks(){
                const url = window.fdConfig.url.feature.back;
                var params={
                    offset:this.offset,
                    limit:this.limit
                };
                const _this = this;
                if(this.query.name){params.name = this.query.name;}
                if(this.query.type){params.type = this.query.type;}
                this.$http.get(url,{
                    params:params
                }).then(function(res){
                    var data = res.body;
                    console.log('反馈列表',data);
                    _this.count = data.pageInfo.rowCount;
                    for(var i =0 ;i<data.data.length;i++){
                        data.data[i].typeValue = _this.options[data.data[i].type ].label;
                    }
                    _this.tableData = data.data;
                },function(){
                    console.log('请求失败处理');
                });
            },
            resetData(){
              this.query = {};
              this.getBacks();
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.offset = (val - 1)*this.limit;
                this.getBacks()
            },
            expand(row, status){
                const index = this.expendRow.indexOf(row.index);
                this.expendRow.splice(index, 1)
            },
            handleEdit(row,falg) {
                this.selectTable = Object.assign({},row);
                this.dialogFormVisible = true;
                this.falg = falg;
            },
            updateBack(){
                const url = window.fdConfig.url.feature.back;
                const _this = this;
                var data = {
                    id:this.selectTable.id,
                    backContent:this.selectTable.backContent,
                    email:this.selectTable.email
                }
                this.$http.post(url,data
                ).then(function(res){
                    const data  = res.body;
                    if(data.status === 200) {
                        this.$notify({
                            title: '成功',
                            message: '回复成功',
                            type: 'success'
                        });
                        _this.dialogFormVisible = false;
                        _this.getBacks();
                    }else{
                        this.$notify.error({
                            title: '错误',
                            message: data.data
                        });
                    }
                },function(){
                    this.$notify.error({
                        title: '错误',
                        message: '操作错误'
                    });
                });
            }

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
