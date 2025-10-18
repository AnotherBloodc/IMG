<template>

  <div id="app" style="height: 200px;border-bottom: 1px solid #ccc">

    <p>Message is: {{ message }}</p>
    <input v-model="message" placeholder="">

  </div>

</template>

<style>
</style>

<script>

const hello = {
  data() {
    return {
      message: 'Hello World'
    }
  }
}
const app = Vue.createApp(hello)
app.mount("#app")

/*export default {
  name: 'Header',
  data() {
    return {
      message: 'Hello Vue!!'
    }
  }
}*/

</script>
