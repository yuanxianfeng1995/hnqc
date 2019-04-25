<template>
  <el-select ref="input" v-model="value" clearable :multiple="multiple" @input="onInput">
    <el-option v-for="item in dictItems" :key="item.item_code" :label="item.item_text" :value="item.item_code + ''">
    </el-option>
  </el-select>
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
      this.$lodash.merge(this.filterParams, colDef.cellRendererParams, colDef.filterParams)
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
      isFilterActive () {
        if (this.multiple) {
          return this.value !== undefined && this.value !== null && this.value.length > 0
        } else {
          return this.value !== undefined && this.value !== null && this.value !== ''
        }
      },
      doesFilterPass (filterPassParams) {
        console && console.info('doesFilterPass', this.$options.name, filterPassParams)
        if (this.filterParams.doesFilterPass) {
          return this.filterParams.doesFilterPass(this.params, this.value, filterPassParams)
        } else {
          let cellValue = this.params.valueGetter(filterPassParams.node) + ''
          if (this.filterParams.type === 'in') {
            if (this.value && this.value.length > 0) {
              return this.value.indexOf(cellValue) > -1
            }
          } else if (this.filterParams.type === 'notIn') {
            return this.value.indexOf(cellValue) < 0
          } else {
            return this.value === cellValue
          }

          return true
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
        // console.log('setModel', model)
        if (model) {
          if (this.multiple) {
            this.value = (model.filter && model.filter.length) ? model.filter.map(i => i + '') : []
          } else {
            this.value = model.filter + ''
          }
        } else {
          this.value = this.multiple ? [] : ''
        }
      },
      onFloatingFilterChanged (model) {
        this.setModel(model)
        this.params.filterChangedCallback()
      },
      onInput (val) {
        this.params.filterChangedCallback()
      }
    }
  })
</script>
