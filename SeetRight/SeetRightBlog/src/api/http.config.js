const axios = require('axios');

//发起一个get请求
axios.get('/Server')
    //处理成功响应结果
    .then(function (response) {
        console.log(response);
    })
    //处理失败响应结果
    .catch(function (error) {
        console.log(error)
    })
    //不管成功失败都会执行
    .then(function () {
        console.log("每次都会执行")
    })