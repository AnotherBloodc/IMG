<template>
  <div>
    <p>{{ id }}</p>
    <p>{{ Username }}</p>
    <p>{{ Password }}</p>
  </div>
</template>

<script>
export default {
  name: 'Hello',
  data: function () {
    return {}
  },
  props: [
    "id",
    "Username",
    "Password",
  ],
}
</script>

<style scoped>

</style>