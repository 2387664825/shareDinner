import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const login = r => require.ensure([], () => r(require('@/page/login')), 'login');
const manage = r => require.ensure([], () => r(require('@/page/manage')), 'manage');
const home = r => require.ensure([], () => r(require('@/page/list/home')), 'home');
const userFb = r => require.ensure([], () => r(require('@/page/list/userFb')), 'userFb');
const userList = r => require.ensure([], () => r(require('@/page/list/userList')), 'userList');
const shopList = r => require.ensure([], () => r(require('@/page/list/shopList')), 'shopList');
const foodList = r => require.ensure([], () => r(require('@/page/list/foodList')), 'foodList');
const orderList = r => require.ensure([], () => r(require('@/page/list/orderList')), 'orderList');
const adminList = r => require.ensure([], () => r(require('@/page/list/adminList')), 'adminList');
const backList = r => require.ensure([], () => r(require('@/page/list/backList')), 'backList');
const addGoods = r => require.ensure([], () => r(require('@/page/edit/addGoods')), 'addGoods');


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
			meta: ['添加商品'],
		},{
			path: '/userList',
			component: userList,
			meta: ['用户列表'],
		},{
			path: '/shopList',
			component: shopList,
			meta: [ '商家列表'],
		},{
			path: '/foodList',
			component: foodList,
			meta: [ '食品列表'],
		},{
			path: '/orderList',
			component: orderList,
			meta: [ '订单列表'],
		},{
			path: '/adminList',
			component: adminList,
			meta: [ '管理员列表'],
		},{
            path: '/backList',
            component: backList,
            meta: [ '反馈列表'],
        },{
            path: '/userFb',
            component: userFb,
            meta: [ '用户分布'],
        }]
	}
]

export default new Router({
	routes,
	strict: process.env.NODE_ENV !== 'production',
})
