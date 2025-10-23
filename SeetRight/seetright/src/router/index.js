import {createRouter, createWebHashHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AboutView from '../views/AboutView.vue'

// 创建路由实例
const router = createRouter({
    history: createWebHashHistory(), // 使用哈希模式
    routes: [
        {path: '/', redirect: '/HomeView'},
        {path: '/HomeView', component: HomeView},
        {path: '/AboutView', component: AboutView}
    ]
})

export default router