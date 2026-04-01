import axios from 'axios';


// 创建 axios 实例并配置
const http = axios.create({
    baseURL: 'http://localhost:8081', // 你的后端地址，根据实际修改
    withCredentials: true, // 允许携带 Cookie
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json',
    }
});

// 请求拦截器
http.interceptors.request.use(
    config => {
        // 从 sessionStorage 获取 token（如果需要的话）
        const token = sessionStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => {
        console.error('请求错误:', error);
        return Promise.reject(error);
    }
);

// 响应拦截器
http.interceptors.response.use(
    response => {
        return response;
    },
    error => {
        if (error.response) {
            switch (error.response.status) {
                case 401:
                    console.error('未授权，请重新登录');
                    // 清除 session 存储并跳转到登录页
                    sessionStorage.removeItem('token');
                    sessionStorage.removeItem('user');
                    sessionStorage.removeItem('AutoLogin');
                    if (window.location.pathname !== '/Login') {
                        window.location.href = '/Login';
                    }
                    break;
                case 403:
                    console.error('拒绝访问');
                    break;
                case 500:
                    console.error('服务器错误');
                    break;
                default:
                    console.error('请求失败:', error.response.data);
            }
        }
        return Promise.reject(error);
    }
);

export default http;