<template>
    <div>
     	<head-top></head-top>
        <el-row style="margin-top: 20px;">
  			<el-col :span="14" :offset="4">
  				<header class="form_header">选择食品种类</header>
	  			<el-form :model="categoryForm" ref="categoryForm" label-width="110px" class="form">
                    <el-row class="query">
                        <el-col :span="12">
                            <el-form-item label="用户名称：" label-width="100px">
                                <label >{{selectTable.name}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="分数" label-width="100px">
                                <label >{{selectTable.star}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="22">
                            <el-form-item label="评论标签：" label-width="100px">
                                <label :title="selectTable.typeValue" style="display:inline-block;width: 400px;">{{selectTable.typeValue}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="22">
                            <el-form-item label="评论内容：" label-width="100px">
                                <label :title="selectTable.content" style="display:inline-block;width: 400px;">{{selectTable.content}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="评论时间：" label-width="100px">
                                <label >{{selectTable.createTime}}</label>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24" >
                            <el-form-item v-if="!falg" label="回复内容：" label-width="100px">
                                <label :title="selectTable.back" style="display:inline-block;width: 400px;">{{selectTable.back}}</label>
                            </el-form-item>
                            <el-form-item label="回复：" label-width="100px"  v-if="falg">
                                <el-input v-model="selectTable.back"  type="textarea" :rows="3" :max='300' style="width: 400px;"  show-word-lmit></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
	  			</el-form>
	  			<header class="form_header">添加食品</header>


  			</el-col>
  		</el-row>
    </div>
</template>

<script>
 	import headTop from '@/components/headTop'
    import {getCategory, addCategory, addFood} from '@/api/getData'
    import {baseUrl, baseImgPath} from '@/config/env'
    export default {
    	data(){
    		return {
    			baseUrl,
    			baseImgPath,
    			restaurant_id: 1,
    			categoryForm: {
    				categoryList: [],
    				categorySelect: '',
    				name: '',
    				description: '',
    			},
    			foodForm: {
    				name: '',
    				description: '',
    				image_path: '',
    				activity: '',
    				attributes: [],
    				specs: [{
    					specs: '默认',
			          	packing_fee: 0,
			          	price: 20,
    				}],
    			},
    			foodrules: {
    				name: [
						{ required: true, message: '请输入食品名称', trigger: 'blur' },
					],
    			},
    			attributes: [{
		          	value: '新',
		          	label: '新品'
		        }, {
		          	value: '招牌',
		          	label: '招牌'
		        },],
    			showAddCategory: false,
    			foodSpecs: 'one',
    			dialogFormVisible: false,
		        specsForm: {
		          	specs: '',
		          	packing_fee: 0,
		          	price: 20,
		        },
		        specsFormrules: {
		        	specs: [
						{ required: true, message: '请输入规格', trigger: 'blur' },
					],
		        }
    		}
    	},
    	components: {
    		headTop,
    	},
    	created(){
    		if (this.$route.query.restaurant_id) {
    			this.restaurant_id = this.$route.query.restaurant_id;
    		}else{
    			this.restaurant_id = Math.ceil(Math.random()*10);
    			this.$msgbox({
		          title: '提示',
		          message: '添加食品需要选择一个商铺，先去就去选择商铺吗？',
		          showCancelButton: true,
		          confirmButtonText: '确定',
		          cancelButtonText: '取消',
		          beforeClose: (action, instance, done) => {
		            if (action === 'confirm') {
		              this.$router.push('/shopList');
		              done();
		            } else {
		            	this.$message({
				            type: 'info',
				            message: '取消'
				        });
		              	done();
		            }
		          }
		        })
    		}
    		this.initData();
    	},
    	computed: {
    		selectValue: function (){
    			return this.categoryForm.categoryList[this.categoryForm.categorySelect]||{}
    		}
    	},
    	methods: {
    		async initData(){
    			try{
    				const result = await getCategory(this.restaurant_id);
	    			if (result.status == 1) {
	    				result.category_list.map((item, index) => {
	    					item.value = index;
	    					item.label = item.name;
	    				})
	    				this.categoryForm.categoryList = result.category_list;
	    			}else{
	    				console.log(result)
	    			}
    			}catch(err){
    				console.log(err)
    			}
    		},
		    addCategoryFun(){
		    	this.showAddCategory = !this.showAddCategory;
		    },
		    submitcategoryForm(categoryForm) {
				this.$refs[categoryForm].validate(async (valid) => {
					if (valid) {
						const params = {
							name: this.categoryForm.name,
							description: this.categoryForm.description,
							restaurant_id: this.restaurant_id,
						}
						try{
							const result = await addCategory(params);
							if (result.status == 1) {
								this.initData();
								this.categoryForm.name = '';
								this.categoryForm.description = '';
								this.showAddCategory = false;
								this.$message({
					            	type: 'success',
					            	message: '添加成功'
					          	});
							}
						}catch(err){
							console.log(err)
						}
					} else {
						this.$notify.error({
							title: '错误',
							message: '请检查输入是否正确',
							offset: 100
						});
						return false;
					}
				});
			},
			uploadImg(res, file) {
				if (res.status == 1) {
					this.foodForm.image_path = res.image_path;
				}else{
					this.$message.error('上传图片失败！');
				}
			},
			beforeImgUpload(file) {
				const isRightType = (file.type === 'image/jpeg') || (file.type === 'image/png');
				const isLt2M = file.size / 1024 / 1024 < 2;

				if (!isRightType) {
					this.$message.error('上传头像图片只能是 JPG 格式!');
				}
				if (!isLt2M) {
					this.$message.error('上传头像图片大小不能超过 2MB!');
				}
				return isRightType && isLt2M;
			},
			addspecs(){
				this.foodForm.specs.push({...this.specsForm});
				this.specsForm.specs = '';
				this.specsForm.packing_fee = 0;
				this.specsForm.price = 20;
				this.dialogFormVisible = false;
			},
			handleDelete(index){
				this.foodForm.specs.splice(index, 1);
			},
			tableRowClassName(row, index) {
		        if (index === 1) {
		        	return 'info-row';
		        } else if (index === 3) {
		        	return 'positive-row';
		        }
		        return '';
		    },
		    addFood(foodForm){
		    	this.$refs[foodForm].validate(async (valid) => {
					if (valid) {
						const params = {
							...this.foodForm,
							category_id: this.selectValue.id,
							restaurant_id: this.restaurant_id,
						}
						try{
							const result = await addFood(params);
							if (result.status == 1) {
								console.log(result)
								this.$message({
					            	type: 'success',
					            	message: '添加成功'
					          	});
					          	this.foodForm = {
				    				name: '',
				    				description: '',
				    				image_path: '',
				    				activity: '',
				    				attributes: [],
				    				specs: [{
				    					specs: '默认',
							          	packing_fee: 0,
							          	price: 20,
				    				}],
				    			}
							}else{
								this.$message({
					            	type: 'error',
					            	message: result.message
					          	});
							}
						}catch(err){
							console.log(err)
						}
					} else {
						this.$notify.error({
							title: '错误',
							message: '请检查输入是否正确',
							offset: 100
						});
						return false;
					}
				});
		    }
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
		text-align: center;
		margin-bottom: 10px;
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
