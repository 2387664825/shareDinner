<template>
    <div>
     	<head-top></head-top>
        <header class="form_header">店铺信息</header>
        <el-divider content-position="right"></el-divider>
        <el-form :inline="true"  :model="family">
        <el-row style="margin-top: 20px;">
                    <el-col :span="12">
                        <el-form-item label="用户id：" label-width="100px">
                            <label >{{family.id}}</label>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="店铺名称：" label-width="100px">
                            <label >{{family.name}}</label>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="店铺地址：" label-width="100px">
                            <label >{{family.location}}</label>
                        </el-form-item>
                    </el-col>
                    <!--<el-col :span="12">-->
                        <!--<el-form-item label="身份证号码：" label-width="100px">-->
                            <!--<label >{{family.idCard}}</label>-->
                        <!--</el-form-item>-->
                    <!--</el-col>-->
                    <!--<el-col :span="12">-->
                        <!--<el-form-item label="性别：" label-width="100px">-->
                            <!--<label >{{family.sexValue}}</label>-->
                        <!--</el-form-item>-->
                    <!--</el-col>-->
                    <!--<el-col :span="12">-->
                        <!--<el-form-item label="出生日期：" label-width="100px">-->
                            <!--<label >{{family.birthday}}</label>-->
                        <!--</el-form-item>-->
                    <!--</el-col>-->
                    <!--<el-col :span="12">-->
                        <!--<el-form-item label="信用：" label-width="100px">-->
                            <!--<label >{{family.credit}}</label>-->
                        <!--</el-form-item>-->
                    <!--</el-col>-->
                    <!--<el-col :span="12">-->
                        <!--<el-form-item label="类型：" label-width="100px">-->
                            <!--<label >{{family.typeValue}}</label>-->
                        <!--</el-form-item>-->
                    <!--</el-col>-->
                    <!--<el-col :span="12">-->
                        <!--<el-form-item label="地区：" label-width="100px">-->
                            <!--<label >{{family.city}}</label>-->
                        <!--</el-form-item>-->
                    <!--</el-col>-->
                    <!--<el-col :span="12">-->
                        <!--<el-form-item label="登录时间：" label-width="100px">-->
                            <!--<label >{{family.loginTime}}</label>-->
                        <!--</el-form-item>-->
                    <!--</el-col>-->
  		</el-row>
        </el-form>
        <header class="form_header">评论信息</header>
        <div class="table_container">
            <el-table
                :data="tableData"
                stripe
                style="width: 100%">
                <el-table-column
                    type="index"
                    width="100px">
                </el-table-column>
                <el-table-column
                    label="用户名称"
                    width="150"
                    prop="userName">
                </el-table-column>
                <el-table-column
                    label="评价分数"
                    prop="star"
                width="100">
                </el-table-column>
                <el-table-column
                    label="评价内容"
                    prop="content"
                    width="400"
                    :show-overflow-tooltip='true'>
                </el-table-column>
                <el-table-column
                    label="评论时间"
                    prop="createTime">
                </el-table-column>
            </el-table>
            <div class="Pagination">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-size="10"
                    layout="total, prev, pager, next"
                    :total="count">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
 	import headTop from '@/components/headTop'
    export default {
    	data(){
    		return {
    		    familyId:'',
                family: {},
                offset: 0,
                limit: 10,
                count: 0,
                currentPage: 1,
                tableData: [],
    		}
    	},
    	components: {
    		headTop,
    	},
    	created(){
            this.familyId = this.$route.query.id;
    		this.initData();
    		this.getComment();
    	},
    	computed: {
    	},
    	methods: {
    		initData(){
                const url = window.fdConfig.url.feature.family +"/"+this.familyId;
                const _this = this;
                this.$http.get(url,).then(function(res){
                    var data = res.body.data;
                    console.log('商家信息',data);
                    _this.family = data;
                },function(){
                    console.log('请求失败处理');
                });

    		},
            getComment(){
                const url = window.fdConfig.url.feature.comment;
                var params={
                    offset:this.offset,
                    limit:this.limit,
                    familyId:this.familyId
                };
                const _this = this;
                this.$http.get(url,{
                    params:params
                }).then(function(res){
                    var data = res.body;
                    console.log('评论信息',data);
                    _this.count = data.pageInfo.rowCount;
                    for(var i =0 ;i<data.data.length;i++){
                        data.data[i].star += '分';
                    }
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
                this.getComment()
            },
		}
    }
</script>

<style lang="less">
	@import '../../style/mixin';
	.form{
		min-width: 400px;
		margin-bottom: 30px;
		&:hover{
			box-shadow: 0 0 8px 0 rgba(232,237,250,.6), 0 2px 4px 0 rgba(232,237,250,.5);
			border-radius: 6px;
			transition: all 400ms;
		}
	}
	.food_form{
		border: 1px solid #eaeefb;
		padding: 10px 10px 0;
	}
	.form_header{
		//text-align: center;
        margin-left: 30px;
		margin-bottom: 10px;
        font-size: 1.2em;
	}
	.category_select{
		border: 1px solid #eaeefb;
		padding: 10px 30px 10px 10px;
		border-top-right-radius: 6px;
		border-top-left-radius: 6px;
	}
	.add_category_row{
		height: 0;
		overflow: hidden;
		transition: all 400ms;
		background: #f9fafc;
	}
	.showEdit{
		height: 185px;
	}
    .table_container{
        padding: 20px;
    }
    .Pagination{
        display: flex;
        justify-content: flex-start;
        margin-top: 8px;
    }
	.add_category{
		background: #f9fafc;
		padding: 10px 30px 0px 10px;
		border: 1px solid #eaeefb;
		border-top: none;
	}
	.add_category_button{
		text-align: center;
		line-height: 40px;
		border-bottom-right-radius: 6px;
		border-bottom-left-radius: 6px;
		border: 1px solid #eaeefb;
		border-top: none;
		transition: all 400ms;
		&:hover{
			background: #f9fafc;
			span, .edit_icon{
				color: #20a0ff;
			}
		}
		span{
			.sc(14px, #999);
			transition: all 400ms;
		}
		.edit_icon{
			color: #ccc;
			transition: all 400ms;
		}
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
	.cell{
		text-align: center;
	}
</style>
