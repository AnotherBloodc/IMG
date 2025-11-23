<template>
  <div class="Login">

    <h2 class="LoginTitle">{{ Login }}</h2>

    <input class="LoginUser" type="text" id="username" placeholder="Username" autocomplete="off">

    <input class="LoginUser" type="password" id="password" placeholder="Password">

    <input class="LoginSubmit" type="submit" value="Login" id="LoginBin">

    <div class="LoginFooter">

      <div class="LoginFooterRemember">

        <input class="LoginRememberCheckbox" type="checkbox" id="rememberMe">

        <label for="rememberMe">保存这次登录</label>

      </div>

      <button class="LoginRememberRegister" @click="toRegister">{{ txtLogin }}</button>

    </div>


  </div>
</template>

<style>
@import "../assets/css/Login1.css";
</style>

<script>
export default {
  name: "Login1",
  data: function () {
    return {
      id: 1,
      Login: "Login",
      txtLogin: "去注册",
    }
  },
  methods: {
    toRegister() {
      if (this.id === 1) {
        this.id = 2
        this.Login = "Register"
        this.txtLogin = "去登录"
      } else if (this.id === 2) {
        this.id = 1
        this.Login = "Login"
        this.txtLogin = "去注册"
      }
    }
  }
}
</script>