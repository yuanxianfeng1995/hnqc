<template>
  <jw-tree-selector v-model="model" :size="size" :disabled="disabled" :selectorOptions="options"
                    @on-change="onChanged">
  </jw-tree-selector>
</template>


<script>
  export default {
    name: 'jwDepartmentSelector',
    props: {
      value: {
        type: Number,
        default: null
      },
      disabled: {type: Boolean, default: false},
      size: {type: String, default: null},
      selectorOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        model: null,
        options: {
          cacheKey: 'departmentList',
          url: '/api/platform/index/selector/departments'
        }
      }
    },
    watch: {
      value (newval, oldval) {
        this._setModel(newval)
      }
    },
    created () {
      this.$lodash.merge(this.options, this.selectorOptions)
      this._setModel(this.value)
    },
    methods: {
      _setModel (val) {
        if (val) {
          this.model = val
          return
        }

        this.model = null
      },
      onChanged (val, selectedItems) {
        this.$emit('input', this.model)
        this.$emit('on-change', this.model, selectedItems)
      }
    }
  }
</script>
