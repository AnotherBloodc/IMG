<template>

  <div id="app" style="height: 200px;border-bottom: 1px solid #ccc">

    <p>{{ message }}</p>
    <p>{{ name2 }}</p>

  </div>

</template>

<style>
</style>

<script>
export default {
  name: 'Header',

  data: function () {
    return {
      message: 'Hello World!',
      name2: 'Hwadwadfasfasdf!'
    }
  },
  methods: {

  }
}
</script>
