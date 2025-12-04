<template>

  <div id="app" style="height: 100vh">

    <el-container>

      <el-header>

        <el-aside width="200px">

          <el-scrollbar>

            <el-menu :default-openeds="['1', '3']">

              <el-sub-menu index="1">
                <template #title>
                  <el-icon>
                    <message/>
                  </el-icon>
                  用户列表
                </template>
              </el-sub-menu>

              <el-sub-menu index="2">
                <template #title>
                  <el-icon>
                    <icon-menu/>
                  </el-icon>
                  Navigator Two
                </template>
                <el-menu-item-group>
                  <template #title>Group 1</template>
                  <el-menu-item index="2-1">Option 1</el-menu-item>
                  <el-menu-item index="2-2">Option 2</el-menu-item>
                </el-menu-item-group>
                <el-menu-item-group title="Group 2">
                  <el-menu-item index="2-3">Option 3</el-menu-item>
                </el-menu-item-group>
                <el-sub-menu index="2-4">
                  <template #title>Option 4</template>
                  <el-menu-item index="2-4-1">Option 4-1</el-menu-item>
                </el-sub-menu>
              </el-sub-menu>

              <el-sub-menu index="3">
                <template #title>
                  <el-icon>
                    <setting/>
                  </el-icon>
                  Navigator Three
                </template>
                <el-menu-item-group>
                  <template #title>Group 1</template>
                  <el-menu-item index="3-1">Option 1</el-menu-item>
                  <el-menu-item index="3-2">Option 2</el-menu-item>
                </el-menu-item-group>
                <el-menu-item-group title="Group 2">
                  <el-menu-item index="3-3">Option 3</el-menu-item>
                </el-menu-item-group>
                <el-sub-menu index="3-4">
                  <template #title>Option 4</template>
                  <el-menu-item index="3-4-1">Option 4-1</el-menu-item>
                </el-sub-menu>
              </el-sub-menu>

            </el-menu>

          </el-scrollbar>

        </el-aside>

      </el-header>

      <el-container>

        <el-aside width="200px"></el-aside>

        <el-main>
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
        </el-main>

      </el-container>

    </el-container>

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
      this.axiosTool.post('/user', {}).then(res => {
        this.user = res.data.returns.list;
      })
    }
  }
}
</script>
