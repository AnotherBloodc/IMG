import {createRouter, createWebHashHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AboutView from '../views/AboutView.vue'
import Style from '../components/style.vue'
import Header from '../components/Header.vue'

// 创建路由实例
const router = createRouter({
    history: createWebHashHistory(), // 使用哈希模式
    routes: [
        {path: '/', redirect: '/HomeView'},
        {path: '/HomeView', component: HomeView},
        {
            path: '/AboutView', component: AboutView, children: [//children:[此路径下的其他路由]
                {path: ":id", component: Style},
                {path: ":name", component: Style, props: true}
            ]
        },
        {path: '/Header', component: Header}
    ]
})

export default router