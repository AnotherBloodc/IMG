import {createApp} from 'vue'
import App from './App.vue'
import header from './components/Header.vue'
import router from './router/index.js'
import store from './store/index.js'
import ElementPlus from 'element-plus'//引入element-plus
import '@/assets/css/Global.css'//引入全局样式
import 'element-plus/dist/index.css'//引入element-plus相关样式

//使用element-plus组件
createApp(App).use(header).use(store).use(router).use(ElementPlus).mount('#app')