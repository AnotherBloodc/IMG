import {createApp} from 'vue'
import App from './App.vue'
import router from './router/index.js'
import store from './store/index.js'
import ElementPlus from 'element-plus'//引入element-plus
import axios from 'axios'
// import './mockjs'
import '@/assets/css/Global.css'//引入全局样式
import 'element-plus/dist/index.css'//引入element-plus相关样式

const app = createApp(App)

axios.defaults.baseURL = 'http://localhost:8080'
app.config.globalProperties.$http = axios

//注册组件
app.use(store).use(router).use(ElementPlus).mount('#app')