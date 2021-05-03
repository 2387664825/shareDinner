const serviceRootPath = '/api/v1/wc';
// 服务数据
const serverUrl = {
    // 测试模块
    feature:{
        // 家庭
        family: `${serviceRootPath}/family`,
        // 用户
        user: `${serviceRootPath}/user/list`,
    },
};
export default serverUrl;
