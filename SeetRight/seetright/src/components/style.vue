<script>
import {mapState, mapGetters} from "vuex";

export default {
  name: 'style',
  props: [],
  computed: {
    ...mapState(['count', 'todos']),
    ...mapGetters(['doneTodos'])
  },
  data: function () {
    return {}
  },
  methods: {
    increment() {
      this.$store.commit('increment')
    },
  }
}
</script>

<template>
  <div>
    <h1>熵</h1>
    {{ $route.params.id }}
    {{ $route.params.name }}
    {{ count }}
    <button @click="increment">+</button>
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