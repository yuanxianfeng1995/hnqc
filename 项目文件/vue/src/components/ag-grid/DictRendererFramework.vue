<template>
  <span>{{ dictItemText }}</span>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        dictItemText: null
      }
    },
    created () {
      Vue.store.dispatch('getDictItemText', {
        dictCode: this.params.dictCode || this.params.dict,
        itemCode: this.params.value
      }).then(itemText => {
        // console.log('itemText', this.params.dictCode || this.params.dict, this.params.value, itemText)
        this.dictItemText = itemText
      })
    }
  })
</script>
