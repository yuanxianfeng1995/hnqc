<template>
  <div class="jw-floating-filter-cell">
    <el-input ref="input" v-model="value" size="small" @change="onChange" placeholder="支持模糊过滤"></el-input>
  </div>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        value: null
      }
    },
    methods: {
      onParentModelChanged (parentModel) {
        this.value = parentModel ? parentModel.filter : null
      },
      onChange (val) {
        this.params.onFloatingFilterChanged({filter: val})
      }
    }
  })
</script>
