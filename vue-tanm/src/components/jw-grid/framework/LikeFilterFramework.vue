<template>
  <el-input :ref="'input'" v-model="value" @change="onChange" placeholder="支持模糊过滤"></el-input>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        value: null,
        filterParams: {
          filterType: 'String',
          type: 'like'
        }
      }
    },
    created () {
      this.doFilterChanged = this.$lodash.debounce(this.params.filterChangedCallback, 500)
      this.$lodash.merge(this.filterParams, this.params.column.colDef.filterParams)
    },
    methods: {
      isFilterActive () {
        return this.value !== undefined && this.value !== null && this.value !== ''
      },
      doesFilterPass (filterPassParams) {
        if (this.filterParams.doesFilterPass) {
          return this.filterParams.doesFilterPass(this.params, this.value, filterPassParams)
        } else {
          let cellValue = this.params.valueGetter(filterPassParams.node) + ''
          if (this.filterParams.type === 'leftLike') {
            return cellValue.endsWith(this.value)
          } else if (this.filterParams.type === 'rightLike') {
            return cellValue.startsWith(this.value)
          } else {
            return cellValue.indexOf(this.value) > -1
          }
        }
      },
      getModel () {
        return {
          filter: this.value,
          filterType: this.filterParams.filterType,
          type: this.filterParams.type
        }
      },
      setModel (model) {
        this.value = model ? model.filter : null
      },
      onFloatingFilterChanged (model) {
        this.setModel(model)
        this.doFilterChanged()
      },
      onChange (val) {
        this.doFilterChanged()
      }
    }
  })
</script>
