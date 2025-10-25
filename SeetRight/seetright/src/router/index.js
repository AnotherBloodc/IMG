import {createRouter, createWebHashHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AboutView from '../views/AboutView.vue'
import style from '../components/style.vue'

// 创建路由实例
const router = createRouter({
    history: createWebHashHistory(), // 使用哈希模式
    routes: [
        {path: '/', redirect: '/HomeView'},
        {path: '/HomeView', component: HomeView},//children:[此路径下的其他路由]
        {path: '/AboutView', component: AboutView, children: [
                {path: ":id", component: style},
                {path: ":name", component: style,props: true}
            ]}
    ]
})

export default router