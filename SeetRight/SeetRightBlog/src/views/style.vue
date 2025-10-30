<script>
import {mapGetters, mapState} from "vuex";

export default {
  name: 'style',
  props: [],
  computed: {
    //获取store.state：count的属性，todos的属性
    ...mapState(['count', 'todos']),
    //获取store.getters：doneTodos的属性
    ...mapGetters(['doneTodos'])
  },
  data: function () {
    return {}
  },
  methods: {
    //调用store.mutations：increment方法
    increment() {
      this.$store.commit('increment')
    },
  }
}
</script>

<template>

  <div>
    <h1>主页</h1>

    <div>
      获取 (AboutView中，to="/AboutView/id) 的参数id：{{ $route.params.id }}
    </div>

    <div>
      获取 (AboutView中，to="/AboutView/name/name) 的参数name：{{ $route.params.name }}
    </div>

    <div>
      获取store中定义的属性count：{{ count }}
      <button @click="increment">按钮+</button>
    </div>

    <ul>
      <li v-for="todo in doneTodos" :key="todo.id">{{ todo.text }}</li>
    </ul>

    <ul>
      <li v-for="todo in todos" :key="todo.id">{{ todo.text }}</li>
    </ul>
  </div>

</template>

<style>

</style>