<template>
    <div class="fillcontain">
        <el-row class="query">
            <el-col :span="8">
                用户名称：
                <el-input
                    placeholder="请输入内容"
                    prefix-icon="el-icon-search"
                    v-model="query.userName" style="width:190px;">
                </el-input>
            </el-col>
            <el-col :span="8">
                店铺名称：
                <el-input
                    placeholder="请输入内容"
                    prefix-icon="el-icon-search"
                    v-model="query.familyName" style="width:190px;">
                </el-input>
            </el-col>
            <el-col :span="8">
                订单状态：
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
                产生日期：
                <el-date-picker
                    v-model="query.beginTime"
                    type="date"
                    placeholder="请选择开始日期">
                </el-date-picker>
                -
                <el-date-picker
                    v-model="query.endTime"
                    type="date"
                    placeholder="请选择结束日期">
                </el-date-picker>
            </el-col>
            <el-col :span="8">
                <el-button type="primary"  @click="getOrders">查询</el-button>
                <el-button type="primary"  @click="resetData" plain>重置</el-button>
            </el-col>
        </el-row>
        <div class="table_container">
            <el-table
			    :data="tableData"
			    @expand='expand'
                :expand-row-keys='expendRow'
                :row-key="row => row.index"
			    style="width: 100%">
			    <el-table-column type="expand">
			      <template slot-scope="props">
			        <el-form label-position="left" inline class="demo-table-expand">
			          <el-form-item label="用户名：" >
			            <span>{{ props.row.userName }}</span>
			          </el-form-item>
			          <el-form-item label="店铺名称：">
			            <span>{{ props.row.familyName }}</span>
			          </el-form-item>
			          <el-form-item label="价格：">
			            <span>{{ props.row.price }}</span>
			          </el-form-item>
			          <el-form-item label="时间：">
			            <span>{{ props.row.createTime }}</span>
			          </el-form-item>
			          <el-form-item label="店铺地址：">
			            <span>{{ props.row.location }}</span>
			          </el-form-item>
                        <el-form-item label="订单状态：">
                            <span>{{ props.row.statusValue }}</span>
                        </el-form-item>
			        </el-form>
			      </template>
			    </el-table-column>
                <el-table-column
                    type="index"
                    width="100px">
                </el-table-column>
			    <el-table-column
			      label="用户名称"
			      prop="userName">
			    </el-table-column>
                <el-table-column
                    label="店铺名称"
                    prop="familyName">
                </el-table-column>
			    <el-table-column
			      label="总价格"
			      prop="price">
			    </el-table-column>
			    <el-table-column
			      label="订单状态"
			      prop="statusValue">
			    </el-table-column>
                <el-table-column
                    label="创建时间"
                    prop="createTime">
                </el-table-column>
			</el-table>
            <div class="Pagination" style="text-align: left;margin-top: 10px;">
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
    </div>
</template>

<script>
    import headTop from '../../components/headTop'
    import util from '../../util/util.js'
    export default {
        data(){
            return {
                query:{userName:'',familyName:'',beginTime:'',endTime:'',type:''},
                tableData: [],
                currentRow: null,
                offset: 0,
                limit: 15,
                count: 0,
                currentPage: 1,
                restaurant_id: null,
                expendRow: [],
                options: [{
                    value: '0',
                    label: '已取消'
                }, {
                    value: '1',
                    label: '已预约'
                }, {
                    value: '2',
                    label: '待完成'
                }, {
                    value: '3',
                    label: '待评价'
                }, {
                    value: '4',
                    label: '已完成'
                }]
            }
        },
    	components: {
    		headTop,
    	},
        created(){
        	this.restaurant_id = this.$route.query.restaurant_id;
            this.getOrders();
        },
        mounted(){

        },
        methods: {
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.offset = (val - 1)*this.limit;
                this.getOrders()
            },
            resetData(){
              this.query = {userName:'',familyName:'',beginTime:'',endTime:'',type:''}
              this.getOrders();
            },
            getOrders(){
                const url = window.fdConfig.url.feature.order;
                var params={
                    offset:this.offset,
                    limit:this.limit
                };
                console.log("时间：",this.query.beginTime);
                if(this.query.userName!==''){params.userName = this.query.userName;}
                if(this.query.familyName!==''){params.familyName = this.query.familyName;}
                if(this.query.beginTime){params.beginTime = util.dateFtt("yyyy-MM-dd",this.query.beginTime);}
                if(this.query.endTime){params.endTime = util.dateFtt("yyyy-MM-dd",this.query.endTime);}
                if(this.query.type!==''){params.type = this.query.type;}
                console.log(params)
                const _this = this;
                this.$http.get(url,{
                    params:params
                }).then(function(res){
                    var data = res.body;
                    console.log('订单列表',data);
                    _this.count = data.pageInfo.rowCount;
                    for(var i =0 ;i<data.data.length;i++){
                        data.data[i].statusValue = _this.options[data.data[i].status].label;
                    }
                    _this.tableData = data.data;
                },function(){
                    console.log('请求失败处理');
                });
            },
            async expand(row, status){
            	if (status) {
            		const restaurant = await getResturantDetail(row.restaurant_id);
	            	const userInfo = await getUserInfo(row.user_id);
	            	const addressInfo = await getAddressById(row.address_id);

	                this.tableData.splice(row.index, 1, {...row, ...{restaurant_name: restaurant.name, restaurant_address: restaurant.address, address: addressInfo.address, user_name: userInfo.username}});
                    this.$nextTick(() => {
                        this.expendRow.push(row.index);
                    })
	            }else{
                    const index = this.expendRow.indexOf(row.index);
                    this.expendRow.splice(index, 1)
                }
            },
        }
    }
</script>

<style lang="less">
	@import '../../style/mixin';
    .table_container{
        padding: 20px;
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
