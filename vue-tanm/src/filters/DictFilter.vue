<script type="text/ecmascript-6">
  import Vue from 'vue'

  Vue.filter('dict', (value, dictCode) => {
    return Vue.store.dispatch('getDictItemText', {dictCode: dictCode, itemCode: value}) // Vue filter暂时不支持Promise
  })
</script>
