<template>
  <div class="jw-floating-filter-cell">
    <el-select ref="input" v-model="value" size="small" clearable :multiple="multiple" @change="onChange">
      <el-option v-for="item in dictItems" :key="item.item_code" :label="item.item_text" :value="item.item_code">
      </el-option>
    </el-select>
  </div>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        defaultFloatingFilterComponentParams: {
          suppressFilterButton: true
        },
        defaultFilterParams: {
          type: 'equal',
          filterType: 'Integer'
        },
        dictItems: [],
        parentModel: null,
        value: null
      }
    },
    computed: {
      filterParams () {
        return this.params.column.colDef.filterParams
      },
      multiple () {
        return this.params.column.colDef.filterParams.type === 'in' || this.params.column.colDef.filterParams.type === 'notIn'
      }
    },
    created () {
      this.params.column.colDef.filterParams = this.$lodash.merge({},
        this.defaultFilterParams,
        this.params.column.colDef.cellRendererParams || {},
        this.params.column.colDef.filterParams || {})
      this.params.column.colDef.floatingFilterComponentParams = this.$lodash.merge({},
        this.defaultFloatingFilterComponentParams,
        this.params.column.colDef.floatingFilterComponentParams || {})

      Vue.store.dispatch('getDictItemList', this.params.column.colDef.filterParams.dictCode || this.params.column.colDef.filterParams.dict).then(itemList => {
        // console.log('itemList', this.params.column.colDef.filterParams.dictCode || this.params.column.colDef.filterParams.dict, itemList)
        this.dictItems = itemList
      })

      if (this.multiple) {
        this.value = []
      }
    },
    methods: {
      onParentModelChanged (parentModel) {
        let newValue = parentModel ? parentModel.filter : (this.multiple ? [] : null)
        if (this.value === newValue) {
          this.parentModel = null
        } else {
          this.parentModel = parentModel
          this.value = newValue
        }
      },
      onChange (val) {
        if (this.parentModel) {
          this.parentModel = null
        } else {
          this.params.onFloatingFilterChanged({filter: val, floating: true})
        }
      }
    }
  })
</script>
