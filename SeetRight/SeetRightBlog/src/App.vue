<template>
  <router-link to="/Login2"></router-link>
  <!--    <router-link to="/HomeView">HomeView</router-link><br>-->
  <!--    <router-link to="/AboutView">AboutView</router-link><br>-->
  <!--    <router-link to="/Header">Header</router-link><br>-->
  <!--    <router-link to="/Hello">Hello</router-link><br>-->
  <router-view></router-view>
</template>

<script>
export default {
  name: 'App',
  created: function () {
    console.log("App.vue被创建时")
  },
  mounted: function () {
    console.log("App.vue被创建后")
    this.axiosTool.get("/user").then(res => {
      this.user = res.data;
    })

    //定义回调函数
    function myCallback(a) {
      console.log("回调函数被调用:" + a);
    }

    //定义主函数
    function doSomething(callback) {
      let result = "处理完成";
      callback(result);
    }

    //调用主函数，传递回调函数
    doSomething(myCallback);
  },
  data: function () {
    return {
      user: []
    }
  },
}
</script>

<style>
@import "@/assets/css/Global.css";
</style>
