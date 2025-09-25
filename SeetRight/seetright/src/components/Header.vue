<template>

  <div style="height: 200px;border-bottom: 1px solid #ccc">

    <a v-bind:href="url">http://localhost:8081/</a>

    <div>a</div>
    <div>a</div>
    <div>a</div>
    <div>a</div>
    <div>a</div>

    <p v-if="visible">1</p>
    <p v-if="visible">1</p>
    <p v-if="visible">1</p>
    <p v-if="visible">1</p>
    <p v-else>2</p>

    <ul>
      <li v-for="item in items" :key="item.id">{{ item.text }}</li>
    </ul>

    <input v-model="message" placeholder="">

    <p>Message is: {{ message }}</p>

  </div>

</template>

<style>
</style>

<script>

export default {
  name: 'Header',
  data() {
    return {
      message: 'Hello Vue!!'
    }
  }
}

</script>
