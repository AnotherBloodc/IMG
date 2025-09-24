import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

//引入全局样式
import '@/assets/css/Global.css'

//引入element-plus
import ElementPlus from 'element-plus'

//引入element-plus相关样式
import 'element-plus/dist/index.css'

//使用element-plus组件
createApp(App).use(store).use(router).use(ElementPlus).mount('#app')