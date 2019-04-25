<template>
  <div class="jw-floating-filter-cell">
    <el-select ref="input" v-model="value" size="small" clearable :filterable="!multiple" :multiple="multiple"
               @input="onInput">
      <el-option v-for="item in dictItems" :key="item.item_code" :label="item.item_text"
                 :value="item.item_code + ''">
      </el-option>
    </el-select>
  </div>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        multiple: false,
        dictItems: [],
        value: '',
        filterParams: {
          filterType: 'Integer',
          type: 'equal' // equal、in、notIn
        }
      }
    },
    created () {
      let colDef = this.params.column.colDef
      this.$lodash.merge(this.filterParams, colDef.cellRendererParams, colDef.filterParams, colDef.floatingFilterComponentParams)
      this.multiple = this.filterParams.type === 'in' || this.filterParams.type === 'notIn' || this.filterParams.type === 'containsAll' || this.filterParams.type === 'containsAny'

      this._loadDictItems()

      if (this.multiple) {
        this.value = []
      }
    },
    methods: {
      _loadDictItems () {
        if (this.filterParams.dictItemList) {
          if (typeof this.filterParams.dictItemList === 'function') {
            this.filterParams.dictItemList.call(this, itemList => this.updateDictItems(itemList))
          } else {
            this.updateDictItems(this.filterParams.dictItemList)
          }
          return
        }

        Vue.store.dispatch('getDictItemList', this.filterParams.dictCode || this.filterParams.dict).then(itemList => this.updateDictItems(itemList))
      },
      updateDictItems (itemList) {
        this.dictItems = itemList
      },
      onParentModelChanged (parentModel) {
        this.value = parentModel ? parentModel.filter : (this.multiple ? [] : '')
      },
      onInput (val) {
        this.params.onFloatingFilterChanged({filter: val})
      }
    }
  })
</script>
