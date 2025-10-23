<template>
  <div>
    <Hello v-for="users in user" :key="users.id" :id="users.id" :titles="users.Username"
           :message="users.Password"></Hello>
    <Header></Header>
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
      users: [
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
