<template>

  <div class="Login_CSS">

    <div class="Login">

      <div class="Login_toggle" :class="{ 'Log': Log }">

        <div class="Login_toggle_panel">

          <h1>Hello,Welcome</h1>

          <p>Don 't have an account?</p>

          <button @click="toLogin" :class="{'LogGotBut':LogGotBut }">{{ Texts }}</button>

        </div>

      </div>

      <div class="Login_Style" :class="{ 'Log': Log }">

        <div class="Login_Style_Div" :class="{ 'Logins': Logins }">

          <h1>{{ Text }}</h1>

          <div class="Login_Style_Div_Div">
            <input type="text" placeholder="Username (Phone or Email)" v-model="user.username" required>
            <i class='bx  bx-user'></i>
          </div>

          <div class="Login_Style_Div_Div">
            <input type="password" placeholder="Password" v-model="user.password" required>
            <i class='bx  bx-lock'></i>
          </div>

          <div class="Login_Style_Div_Div2">
            <el-switch class="AutoLogin" size="large" v-model="AutoLogin" inline-prompt active-text="Automatic"
                       inactive-text="Automatic" style="--el-switch-on-color: #1b1b1b;"/>
            <a href="#">Forgot Password?</a>
          </div>

          <button type="submit" @click="Login">{{ Text }}</button>

          <p>or login with social platform</p>

          <div id="Login_Style_icons">
            <a href="#">
              <i class='bxl  bx-google'></i>
            </a>
            <a href="#">
              <i class='bxl  bx-facebook-circle'></i>
            </a>
            <a href="#">
              <i class='bxl  bx-github'></i>
            </a>
            <a href="#">
              <i class='bxl  bx-steam'></i>
            </a>
          </div>

        </div>

      </div>

    </div>

  </div>

</template>

<style scoped>
@import "../assets/css/Login.css";
</style>

<script>
import {useRouter} from 'vue-router'

export default {
  name: "Login",
  data: function () {
    return {
      user: {
        username: null,
        password: null,
        token: null
      },
      Text: "Login",
      Texts: "Register",
      Logins: false,  // 添加控制元素显示状态的数据
      Registers: false,  // 添加控制元素显示状态的数据
      Log: false,
      LogGotBut: false,//欢迎页面按钮
      AutoLogin: false,//自动登录
    }
  },
  setup() {
    const router = useRouter()
    return {router}
  },
  created: function () {
    //验证Token是否正确
    this.user.token = localStorage.getItem("token");
    if (this.user.token != null) {
      this.axiosTool.get("/user/loginToken", {
        headers: {
          token: this.user.token,
          AutoLogin: localStorage.getItem("AutoLogin"),
        }
      }).then(res => {
            if (res.data.message === "1") {
              if (res.data.returns.user != null) {
                this.user.username = res.data.returns.user.username;
                this.user.password = res.data.returns.user.password;
                this.AutoLogin = localStorage.getItem("AutoLogin") === "true";
              }
            } else {
              localStorage.removeItem("token");
            }
          }
      )
    }
  },
  methods: {
    toLogin() {
      if (this.Text === "Login") {
        this.Registers = false;  // 触发隐藏元素
        this.Logins = true;  // 显示注册表单
        this.Log = true;
        this.LogGotBut = true;
        setTimeout(() => {
          this.Text = "Register"; // 更改按钮
          this.Texts = "Login"; // 更改标题
          this.LogGotBut = false;
        }, 180)
      } else {
        this.Registers = true;  // 触发隐藏元素
        this.Logins = false;  // 显示注册表单
        this.Log = false;
        this.LogGotBut = true;
        setTimeout(() => {
          this.Text = "Login";
          this.Texts = "Register";
          this.LogGotBut = false;
        }, 180)
      }
    },
    Login() {
      this.axiosTool.post("/Login/LoginUser", this.user)
          .then(res => {
            if (res.data.success === true) {
              this.user.token = res.data.data.token;//获取后端返回的token
              if (this.AutoLogin) {
                // 自动登录：持久化存储（localStorage）
                localStorage.setItem("token", this.user.token);
                localStorage.setItem("AutoLogin", "true"); // 保存自动登录状态
              } else {
                // 非自动登录：会话级存储（sessionStorage）
                sessionStorage.setItem("token", this.user.token);
                localStorage.setItem("AutoLogin", "false"); // 记录自动登录状态
              }
              this.router.push("/Header");//跳转页面
            } else if (res.data.message === "0") {
              alert("登录失败")
            } else {
              alert("系统错误")
            }
          })
    }
  }
}
</script>