<template>
  <div>
    <router-link to="/AboutView/1">组件1</router-link>
    <router-link to="/AboutView/2">组件2</router-link>
    <router-link to="/AboutView/cxvae">组件3</router-link>
    <router-view></router-view>

  </div>
</template>

<script>

export default {
  name: 'AboutView',
}

</script>

