<template>
  <div>
    <!--动态路由，这里给AboutView传递参数-->
    <router-link to="/AboutView/1">组件1</router-link>
    <router-link to="/AboutView/2">组件2</router-link>
    <router-link to="/AboutView/3">组件3</router-link>
    <router-link to="/AboutView/Another">组件4</router-link>
    <router-link to="/AboutView/name/哈哈哈哈">组件5</router-link>
    <router-view></router-view>

  </div>
</template>

<script>

export default {
  name: 'AboutView',
}

</script>

