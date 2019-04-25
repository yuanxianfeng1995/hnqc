<template>
  <jw-field ref="input" v-model="value" size="mini" :disabled="isDisabled()" :options="controlOptions()"></jw-field>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    store: Vue.store,
    data () {
      return {
        options: {rule: [], isDisabled: null},
        value: null
      }
    },
    computed: {
      entity () {
        return this.params.node && this.params.node.data ? this.params.node.data : {}
      }
    },
    created () {
      Vue.lodash.merge(this.options, this.params.options || {})

      this.$nextTick(() => {
        this.value = this.params.value
      })
    },
    methods: {
      controlOptions () {
        if (this.options.controlOptions) {
          return this.options.controlOptions.call(this, this.params, this.entity)
        }
        return {tag: 'el-input'}
      },
      isDisabled () {
        if (this.options.isDisabled) {
          return this.options.isDisabled.call(this, this.params, this.entity)
        }
        return false
      },
      onChange (val) {
        if (this.params.onChange) {
          this.params.onChange.call(this, this.params, val)
        } else {
          this.params.node.data[this.params.colDef.field] = val
        }
      },
      getValue () {
        return this.value
      },
      isCancelBeforeStart () {
        return this.true
      }
    }
  })
</script>
