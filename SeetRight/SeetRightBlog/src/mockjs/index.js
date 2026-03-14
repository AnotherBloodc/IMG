import Mock from 'mockjs'

/*拦截此接口，用于模拟数据*/
Mock.mock('http://localhost:8081/user', 'get', {
    "red": 0,
    user: {
        "id|1-100": 1,//1-100随机数字
        "username": "@cname",//随机中文名字
        "password|1-10": "@cname",//随机中文
        "datetime": "@datetime",//随机日期
        "effect|1": true,//随机日期
    }
})