import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const login = r => require.ensure([], () => r(require('@/page/login')), 'login');
const manage = r => require.ensure([], () => r(require('@/page/manage')), 'manage');
const home = r => require.ensure([], () => r(require('@/page/list/home')), 'home');
const userList = r => require.ensure([], () => r(require('@/page/list/userList')), 'userList');
const shopList = r => require.ensure([], () => r(require('@/page/list/shopList')), 'shopList');
const foodList = r => require.ensure([], () => r(require('@/page/list/foodList')), 'foodList');
const orderList = r => require.ensure([], () => r(require('@/page/list/orderList')), 'orderList');
const adminList = r => require.ensure([], () => r(require('@/page/list/adminList')), 'adminList');
const backList = r => require.ensure([], () => r(require('@/page/list/backList')), 'backList');
const addGoods = r => require.ensure([], () => r(require('@/page/edit/addGoods')), 'addGoods');
const visitor = r => require.ensure([], () => r(require('@/page/visitor')), 'visitor');
const newMember = r => require.ensure([], () => r(require('@/page/newMember')), 'newMember');
const uploadImg = r => require.ensure([], () => r(require('@/page/uploadImg')), 'uploadImg');
const vueEdit = r => require.ensure([], () => r(require('@/page/vueEdit')), 'vueEdit');
const adminSet = r => require.ensure([], () => r(require('@/page/edit/adminSet')), 'adminSet');
const explain = r => require.ensure([], () => r(require('@/page/explain')), 'explain');

const routes = [
	{
		path: '/',
		component: login
	},
	{
		path: '/manage',
		component: manage,
		name: '',
		children: [{
			path: '',
			component: home,
			meta: [],
		},{
			path: '/addGoods',
			component: addGoods,
			meta: ['添加数据', '添加商品'],
		},{
			path: '/userList',
			component: userList,
			meta: ['数据管理', '用户列表'],
		},{
			path: '/shopList',
			component: shopList,
			meta: ['数据管理', '商家列表'],
		},{
			path: '/foodList',
			component: foodList,
			meta: ['数据管理', '食品列表'],
		},{
			path: '/orderList',
			component: orderList,
			meta: ['数据管理', '订单列表'],
		},{
			path: '/adminList',
			component: adminList,
			meta: ['数据管理', '管理员列表'],
		},{
            path: '/backList',
            component: backList,
            meta: ['数据管理', '反馈列表'],
        },{
			path: '/visitor',
			component: visitor,
			meta: ['图表', '用户分布'],
		},{
			path: '/newMember',
			component: newMember,
			meta: ['图表', '用户数据'],
		},{
			path: '/uploadImg',
			component: uploadImg,
			meta: ['文本编辑', 'MarkDown'],
		},{
			path: '/vueEdit',
			component: vueEdit,
			meta: ['编辑', '文本编辑'],
		},{
			path: '/adminSet',
			component: adminSet,
			meta: ['设置', '管理员设置'],
		}]
	}
]

export default new Router({
	routes,
	strict: process.env.NODE_ENV !== 'production',
})
