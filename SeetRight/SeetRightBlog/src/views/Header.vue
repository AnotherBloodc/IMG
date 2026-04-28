<template>
  <div class="Header">
    <el-container>

      <el-header class="Navigation_bar">
        <div class="Navigation_bar_div1">
          <h3>最后的页面</h3>
        </div>

        <div class="Navigation_bar_div2">
          <el-button round>Damo1</el-button>
          <el-button round>Damo1</el-button>
          <el-button round>Damo1</el-button>
          <el-button round>Damo1</el-button>
          <el-button round>Damo1</el-button>
          <el-button round>Damo1</el-button>
        </div>

        <div class="Navigation_bar_div3">
          <div class="Navigation_bar_div3_div1">

          </div>

          <h4>你好</h4>
        </div>
      </el-header>

      <el-main class="Content">
        内容主题
      </el-main>

    </el-container>
  </div>
</template>

<style>
@import "../assets/css/Header.css";
</style>

<script>
export default {
  name: 'Header',
  //组件创建时执行
  created: function () {
    /*this.selectUser();*/
  },
  //数据
  data: function () {
    return {
      user: [],
    }
  },
  //函数
  methods: {
    //访问：查询全部接口
    selectUser() {
      this.axiosTool.post('/User', {
        params: {}
      }).then(res => {
        this.user = res.data.data;
      })
    }
  }
}
</script>
