import {createRouter, createWebHashHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AboutView from '../views/AboutView.vue'
import Style from '../views/style.vue'
import Header from '../views/Header.vue'
import Hello from '../views/Hello.vue'
import Login1 from '../views/Login1.vue'
import Login2 from '../views/Login2.vue'

// 创建路由实例
const router = createRouter({
    history: createWebHashHistory(), // 使用哈希模式
    routes: [
        {path: '/', redirect: '/Login2'},//访问路径为：/时，重定向到/HomeView
        {path: '/Login1', component: Login1},//访问路径为：/时，重定向到/HomeView
        {path: '/Login2', component: Login2},//访问路径为：/时，重定向到/HomeView
        {path: '/HomeView', component: HomeView},//访问路径为：/HomeView时，加载HomeView组件
        {
            //动态路由， 加上冒号就是一个动态路由，children:[此路径下的其他路由]
            path: '/AboutView', component: AboutView, children: [//访问路径为：/AboutView时，加载AboutView组件
                //id是路由参数，不管参数是什么，最终都会跳转到Style组件
                {path: ":id", component: Style},//访问路径为：/AboutView/（id）时，加载Style组件
                {path: "name/:name", component: Style, props: true}//访问路径为：/AboutView/name/（name）时，加载Style组件
            ]
        },
        {path: '/Header', component: Header},
        {path: '/Hello', component: Hello},
    ]
})

export default router