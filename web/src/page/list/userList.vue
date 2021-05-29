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
                所属地区：
                <el-input
                    placeholder="请输入内容"
                    prefix-icon="el-icon-search"
                    v-model="query.city" style="width:190px;">
                </el-input>
            </el-col>
            <el-col :span="8">
                用户类型：
                <el-select v-model="query.type" placeholder="请选择">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select>
            </el-col>
        </el-row>
        <el-row class="query">
            <el-col :span="16">
                登录日期：
                <el-date-picker
                    v-model="query.loginBeginTime"
                    type="date"
                    placeholder="请选择开始日期">
                </el-date-picker>
                -
                <el-date-picker
                    v-model="query.loginEndTime"
                    type="date"
                    placeholder="请选择结束日期">
                </el-date-picker>
            </el-col>
            <el-col :span="8">
                <el-button type="primary"  @click="getUsers">查询</el-button>
                <el-button type="primary"  @click="resetData" plain>重置</el-button>
            </el-col>
        </el-row>
        <div class="table_container">
            <el-table
                :data="tableData"
                stripe
                highlight-current-row
                style="width: 100%">
                <el-table-column
                  type="index"
                  width="100px">
                </el-table-column>
                <el-table-column
                    property="id"
                    label="用户id">
                </el-table-column>
                <el-table-column
                    property="name"
                    label="用户姓名">
                </el-table-column>
                <el-table-column
                    property="telephone"
                    label="联系电话">
                </el-table-column>
                <el-table-column
                  property="city"
                  label="所属地区">
                </el-table-column>
                <el-table-column
                    property="typeValue"
                    label="用户类型">
                </el-table-column>
                <el-table-column
                    property="loginTime"
                    label="最新登录日期">
                </el-table-column>
                <el-table-column label="操作" >
                    <template slot-scope="scope">
                        <el-button
                            size="small"
                            @click="handleXq(scope.$index, scope.row)">详情</el-button>
                    </template>
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
            <el-dialog title="用户个人信息" v-model="dialogFormVisible">
                <el-form :inline="true"  :model="selectTable">
                    <el-row class="query">
                        <el-col :span="12">
                            <el-form-item label="用户id：" label-width="100px">
                                <label >{{selectTable.id}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="用户姓名：" label-width="100px">
                                <label >{{selectTable.name}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="联系电话：" label-width="100px">
                                <label >{{selectTable.telephone}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="身份证号码：" label-width="100px">
                                <label >{{selectTable.idCard}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="性别：" label-width="100px">
                                <label >{{selectTable.sex}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="出生日期：" label-width="100px">
                                <label >{{selectTable.birthday}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="信用：" label-width="100px">
                                <label >{{selectTable.credit}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="类型：" label-width="100px">
                                <label >{{selectTable.typeValue}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="地区：" label-width="100px">
                                <label >{{selectTable.city}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="登录时间：" label-width="100px">
                                <label >{{selectTable.loginTime}}</label>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        data(){
            return {
                query:{name:'',loginBeginTime:'',loginEndTime:'',type:'',},
                tableData: [],
                selectTable:{},
                dialogFormVisible:false,
                currentRow: null,
                offset: 0,
                limit: 15,
                count: 0,
                currentPage: 1,
                options: [{
                    value: '0',
                    label: '用户'
                }, {
                    value: '1',
                    label: '店家'
                }]
            }
        },
    	components: {
    		headTop,
    	},
        created(){
            this.getUsers();
        },
        methods: {
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.offset = (val - 1)*this.limit;
                this.getUsers();
            },
            handleXq(index, row) {
                this.selectTable = row;
                this.dialogFormVisible = true;
            },
            resetData(){
              this.query = {};
              this.getUsers();
            },
            getUsers(){
                this.query.offset = this.offset;
                this.query.limit = this.limit;
                const url = window.fdConfig.url.feature.user;
                const _this = this;
                this.$http.get(url,{
                    params:_this.query
                }).then(function(res){
                    const data = res.body;
                    _this.count = data.pageInfo.rowCount;
                    const users =  data.data;
                    for (var i = 0;i<users.length;i++){
                        users[i].typeValue = users[i].type === 0?'用户':'店家';
                    }
                    this.tableData = users;
                    console.log("用户列表：{}",users);
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
    .query{
        margin: 20px 20px 0 20px;
    }
    .query .el-col {
        min-height: 1px;
    }
    .query .el-button {
        width: 100px;
    }
</style>
