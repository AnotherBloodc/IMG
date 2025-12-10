<template>

  <div class="Login2_CSS">

    <div class="Login2">

      <div class="Login2_toggle" :class="{ 'Log': Log }">

        <div class="Login2_toggle_panel">

          <h1>Hello,Welcome</h1>

          <p>Don 't have an account?</p>

          <button @click="toLogin" :class="{'LogGotBut':LogGotBut }">{{ Texts }}</button>

        </div>

      </div>

      <div class="Login2_Style" :class="{ 'Log': Log }">

        <div id="Login2_Style_DivID" :class="{ 'Logins': Logins }">

          <h1>{{ Text }}</h1>

          <div>
            <input type="text" placeholder="Username (Phone or Email)" v-model="user.username" required>
            <i class='bx  bx-user'></i>
          </div>

          <div>
            <input type="password" placeholder="Password" v-model="user.password" required>
            <i class='bx  bx-lock'></i>
          </div>

          <a href="#">Forgot Password?</a>

          <button type="submit" @click="Login">{{ Text }}</button>

          <p>or login with social platform</p>

          <div id="Login2_Style_icons">
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
@import "../assets/css/Login2.css";
</style>

<script>
import {useRouter} from 'vue-router'

export default {
  name: "Login2",
  data: function () {
    return {
      user: {
        username: null,
        password: null,
      },
      token: null,
      Text: "Login",
      Texts: "Register",
      Logins: false,  // 添加控制元素显示状态的数据
      Registers: false,  // 添加控制元素显示状态的数据
      Log: false,
      LogGotBut: false,//欢迎页面按钮
    }
  },
  setup() {
    const router = useRouter()
    return {router}
  },
  created: function () {
    this.token = localStorage.getItem("token");
    if (this.token != null) {
      this.axiosTool.get("/user/Tokens", {
        headers: {token: this.token}
      }).then(res => {
            if (res.data.message === "1") {
              this.user.username = res.data.returns.user.username;
              this.user.password = res.data.returns.user.password;
            } else if (res.data.message === "0") {
              localStorage.removeItem("token");
              this.token = null;
            } else {
              localStorage.removeItem("token");
              this.token = null;
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
      this.axiosTool.post("/user/login", this.user).then(res => {
        if (res.data.message === "1") {
          alert("登录成功")
          this.token = res.data.returns.token;
          localStorage.setItem("token", this.token);
          this.router.push("/Header");
        } else if (res.data.message === "0") {
          alert("登录失败")
          localStorage.removeItem("token");
          this.token = null;
        } else {
          alert("系统错误")
          localStorage.removeItem("token");
          this.token = null;
        }
      })
    },
  }
}
</script>