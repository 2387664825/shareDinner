const serviceRootPath = '/api/v1/wc';
// 服务数据
const serverUrl = {
    // 测试模块
    feature:{
        // 家庭
        family: `${serviceRootPath}/family`,
        familyUpdate: `${serviceRootPath}/family/insert`,
        // 菜单
        food: `${serviceRootPath}/food/list`,
        // 订单
        order: `${serviceRootPath}/order`,
        orderSeven: `${serviceRootPath}/order/seven`,
        indexData: `${serviceRootPath}/order/indexData`,
        // 用户
        user: `${serviceRootPath}/user/list`,
        userFb: `${serviceRootPath}/user/fb`,
        province: `${serviceRootPath}/user/sf`,
        // 管理员
        admin: `${serviceRootPath}/admin/list`,
        // 评论
        comment: `${serviceRootPath}/comment`,
        // 反馈模块
        back:`${serviceRootPath}/back`,
    },
};
export default serverUrl;
