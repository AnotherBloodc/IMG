<template>
  <div>
    <router-link to="/HomeView">HomeView</router-link>
    <router-link to="/AboutView">AboutView</router-link>
    <router-view></router-view>
  </div>
</template>

<script>
import Header from './components/Header.vue';
import Hello from './components/Hello.vue';

export default {
  name: 'App',
  components: {
    Header,
    Hello
  },
  created: function () {
    console.log("App.vue被创建时")
  },
  mounted: function () {
    console.log("App.vue被创建后")
  },
  data: function () {
    return {
      user: [
        {
          id: 1,
          Username: "张三",
          Password: "123456"
        },
        {
          id: 2,
          Username: "张三",
          Password: "123456"
        },
        {
          id: 3,
          Username: "张三",
          Password: "123456"
        },
        {
          id: 4,
          Username: "张三",
          Password: "123456"
        },
      ]
    }
  },
}
</script>

<style>
</style>
