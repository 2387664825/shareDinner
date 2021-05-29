const serviceRootPath = '/api/v1/wc';
// 服务数据
const serverUrl = {
    // 测试模块
    feature:{
        // 家庭
        family: `${serviceRootPath}/family`,
        // 菜单
        food: `${serviceRootPath}/food/list`,
        // 订单
        order: `${serviceRootPath}/order`,
        // 用户
        user: `${serviceRootPath}/user/list`,
        // 管理员
        admin: `${serviceRootPath}/admin/list`,
        // 反馈模块
        back:`${serviceRootPath}/back`,
    },
};
export default serverUrl;
