<template>
    <div class="fillcontain">
        <el-row class="query">
            <el-col :span="8">
                店铺名称：
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
                店铺状态：
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
            </el-col>
            <el-col :span="8">
                <el-button type="primary"  @click="getShop">查询</el-button>
                <el-button type="primary"  @click="resetData" plain>重置</el-button>
            </el-col>
        </el-row>
        <div class="table_container">
            <el-table
                :data="tableData"
                stripe
                style="width: 100%">
                <el-table-column type="expand">
                  <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                      <el-form-item label="店铺名称：">
                        <span>{{ props.row.name }}</span>
                      </el-form-item>
                      <el-form-item label="店铺地址：">
                        <span>{{ props.row.location }}</span>
                      </el-form-item>
                      <el-form-item label="店铺介绍：">
                        <span>{{ props.row.js }}</span>
                      </el-form-item>
                      <el-form-item label="店铺 ID：">
                        <span>{{ props.row.id }}</span>
                      </el-form-item>
                      <el-form-item label="联系电话：">
                        <span>{{ props.row.phone }}</span>
                      </el-form-item>
                      <el-form-item label="评分：">
                        <span>{{ props.row.rate }}</span>
                      </el-form-item>
                      <el-form-item label="销售量：">
                        <span>{{ props.row.number }}</span>
                      </el-form-item>
                    </el-form>
                  </template>
                </el-table-column>
                <el-table-column
                    type="index"
                    width="100px">
                </el-table-column>
                <el-table-column
                  label="店铺名称"
                  prop="name">
                </el-table-column>
                <el-table-column
                  label="店铺地址"
                  prop="location">
                </el-table-column>
                <el-table-column
                  label="店铺介绍"
                  prop="js"
                  width="400"
                  :show-overflow-tooltip='true'>
                </el-table-column>
                <el-table-column
                    label="申请状态"
                    prop="statusValue">
                </el-table-column>
                <el-table-column label="操作" width="200">
                <template slot-scope="scope">
                    <el-button
                        size="mini"
                        @click="handleSp(scope.$index, scope.row)">审批</el-button>
                    <el-button
                        size="mini"
                        type="Success"
                        @click="toFoodList(scope.$index, scope.row)">查看菜品</el-button>
                    <el-button
                        size="mini"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)">禁止</el-button>
                </template>
            </el-table-column>
            </el-table>
            <div class="Pagination">
                <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="currentPage"
                  :page-size="20"
                  layout="total, prev, pager, next"
                  :total="count">
                </el-pagination>
            </div>
            <el-dialog title="审核店铺信息" v-model="dialogFormVisible" >
                <el-form :model="selectTable">
                    <el-form-item label="店铺名称：" label-width="100px">
                        <label >{{selectTable.name}}</label>
                    </el-form-item>
                    <el-form-item label="详细地址：" label-width="100px">
                        <label >{{selectTable.location}}</label>
                        <!--<span>当前城市：{{city.name}}</span>-->
                    </el-form-item>
                    <el-form-item label="店铺介绍：" label-width="100px">
                        <label >{{selectTable.js}}</label>
                    </el-form-item>
                    <el-form-item label="联系电话：" label-width="100px">
                        <label >{{selectTable.phone}}</label>
                    </el-form-item>
                    <el-form-item label="商铺图片：" label-width="100px">
                          <img  :src="selectTable.imgLocation" class="avatar">
                    </el-form-item>
                    <el-form-item label="卫生证：" label-width="100px">
                        <img  :src="selectTable.jkz" class="avatar">
                    </el-form-item>
                    <el-form-item label="审批意见：" label-width="100px">
                        <el-select v-model="sp.type" placeholder="请选择">
                            <el-option
                                v-for="item in spType"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="原因：" label-width="100px" v-if="sp.type == 2">
                        <el-input type="textarea" v-model="sp.yy"></el-input>
                    </el-form-item>
                </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateShop">保 存</el-button>
              </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import headTop from '../../components/headTop'
    export default {
        data(){
            return {
                query:{name:'',city:'',type:'',},
                offset: 0,
                limit: 15,
                count: 0,
                tableData: [],
                currentPage: 1,
                selectTable: {},
                dialogFormVisible: false,
                categoryOptions: [],
                selectedCategory: [],
                address: {},
                options: [{
                    value: '1',
                    label: '待审批'
                }, {
                    value: '2',
                    label: '不通过'
                },{
                    value: '3',
                    label: '已通过'
                }],
                spType: [{
                    value: '1',
                    label: '通过'
                }, {
                    value: '2',
                    label: '不通过'
                }],
                sp:{type:'',yy:''}
            }
        },
    	components: {
    		headTop,
    	},
        created(){
            this.getShop();
        },
        methods: {
             getShop(){
                const url = window.fdConfig.url.feature.family;
                var params={
                    offset:this.offset,
                    limit:this.limit
                };
               const _this = this;
                 if(this.query.name!==''){params.name = this.query.name;}
                 if(this.query.city!==''){params.city = this.query.city;}
                 if(this.query.type!==''){params.type = this.query.type;}
                 this.$http.get(url,{
                     params:params
                 }).then(function(res){
                     var data = res.body;
                     console.log('商家信息',data);
                     _this.count = data.pageInfo.rowCount;
                     for(var i =0 ;i<data.data.length;i++){
                         data.data[i].statusValue = _this.options[data.data[i].status - 1].label;
                     }
                     _this.tableData = data.data;
                 },function(){
                     console.log('请求失败处理');
                 });
            },
            resetData(){
              this.query = {};
              this.getShop();
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.offset = (val - 1)*this.limit;
                this.getShop()
            },
            handleSp(index, row) {
                this.selectTable = row;
                this.address.address = row.address;
                this.dialogFormVisible = true;
            },
            toFoodList(index, row){
                this.$router.push({ path: 'addGoods', query: { restaurant_id: row.id }})
            },
            handleDelete(index, row) {
                // try{
                //     const res = await deleteResturant(row.id);
                //     if (res.status == 1) {
                //         this.$message({
                //             type: 'success',
                //             message: '删除店铺成功'
                //         });
                //         this.tableData.splice(index, 1);
                //     }else{
                //         throw new Error(res.message)
                //     }
                // }catch(err){
                //     this.$message({
                //         type: 'error',
                //         message: err.message
                //     });
                //     console.log('删除店铺失败')
                // }
            },
            updateShop(){

            },
            addressSelect(vale){
                const {address, latitude, longitude} = vale;
                this.address = {address, latitude, longitude};
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
