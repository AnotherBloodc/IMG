<template>

  <div id="app" style="height: 100vh">

    <el-container>

      <el-header>
        <div class="HeaderCSS">
          头部导航栏
        </div>
      </el-header>


      <el-container>

        <el-aside width="200px">
          <div class="HeaderCSS">
            左边工具栏
          </div>
        </el-aside>


        <el-main>中间内容</el-main>

      </el-container>

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
    this.selectUser();
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
