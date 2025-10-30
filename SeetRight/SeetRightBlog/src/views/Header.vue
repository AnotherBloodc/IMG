<template>

  <div id="app" style="height: 200px;border-bottom: 1px solid #ccc">

    <el-table :data="user" style="width: 100%">
      <el-table-column
          label="编号"
          prop="id"
          width="180">
      </el-table-column>
      <el-table-column
          label="姓名"
          prop="username"
          width="180">
      </el-table-column>
      <el-table-column
          label="密码"
          prop="password">
      </el-table-column>
      <el-table-column
          label="邮箱"
          prop="email">
      </el-table-column>
      <el-table-column
          label="创建日期"
          prop="datetime">
      </el-table-column>
      <el-table-column
          label="是否删除"
          prop="effect">
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
