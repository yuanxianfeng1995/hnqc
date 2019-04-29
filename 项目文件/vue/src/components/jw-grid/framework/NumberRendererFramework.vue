<style scoped>
</style>

<template>
  <span>{{ this.params.value | number(this.params.precision, this.params.separator,this.params.point) }}</span>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {}
    }
  })
</script>
