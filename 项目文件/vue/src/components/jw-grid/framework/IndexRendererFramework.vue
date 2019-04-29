<style scoped>
  .index-render {
    text-align: center;
    display: block;
  }
</style>

<template>
  <span class="index-render">{{ this.params.rowIndex + 1 }}</span>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {}
    }
  })
</script>
