<template>

  <div id="app" style="height: 200px;border-bottom: 1px solid #ccc">

    <el-table :data="user" style="width: 100%">
      <el-table-column
          prop="id"
          label="编号"
          width="180">
      </el-table-column>
      <el-table-column
          prop="username"
          label="姓名"
          width="180">
      </el-table-column>
      <el-table-column
          prop="password"
          label="密码">
      </el-table-column>
      <el-table-column
          prop="email"
          label="邮箱">
      </el-table-column>
      <el-table-column
          prop="datetime"
          label="创建日期">
      </el-table-column>
      <el-table-column
          prop="effect"
          label="是否删除">
      </el-table-column>
    </el-table>

  </div>

</template>

<style>
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
      this.$http.get('http://localhost:8080/user').then((response) => {
        this.user = response.data
      })
    }
  }
}
</script>
