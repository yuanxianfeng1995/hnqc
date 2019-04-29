<template>
  <el-select ref="input" style="min-width:10px;width:100%;" v-model="value" size="mini" filterable
             :clearable="options.clearable" :disabled="isDisabled()" @visible-change="onVisibleChange" @input="onInput"
             @change="onChange">
    <el-option v-for="(item, idx) in dictItems"
               :key="idx"
               :value="item.value"
               :label="item.label">
      <slot :item="item"></slot>
    </el-option>
  </el-select>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    store: Vue.store,
    data () {
      return {
        dictItems: [],
        options: {
          clearable: false,
          value: 'item_code',
          label: 'item_text',
          disabled: null,
          isDisabled: null,
          onVisibleChange: null,
          onDropdownOpen: null,
          onDropdownClose: null,
          onSelect: null,
          onChange: null,
          rule: []
        },
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

      this._loadDictItems()

      this.$nextTick(() => {
        this.value = this.params.value ? (this.params.value + '') : null
      })
    },
    methods: {
      _loadDictItems () {
        if (this.params.dictItemList) {
          if (typeof this.params.dictItemList === 'function') {
            this.params.dictItemList.call(this, itemList => this.updateDictItems(itemList))
          } else {
            this.updateDictItems(this.params.dictItemList)
          }
          return
        }

        Vue.store.dispatch('getDictItemList', this.params.dictCode || this.params.dict).then(itemList => {
          // console.log('itemList', this.params.dictCode || tthis.params.dict, itemList)
          this.updateDictItems(itemList)
        })
      },
      updateDictItems (itemList) {
        this.dictItems = itemList.map(item => {
          item.value = (typeof this.options.value === 'function' ? this.options.value(item) : item[this.options.value]) + ''
          item.label = typeof this.options.label === 'function' ? this.options.label(item) : item[this.options.label]
          return item
        })
      },
      isDisabled () {
        if (this.options.isDisabled) {
          return this.options.isDisabled.call(this, this.params, this.entity)
        }
        if (this.options.disabled !== undefined && this.options.disabled !== null) {
          return this.options.disabled
        }
        return false
      },
      onVisibleChange (visible) {
        if (this.options.onVisibleChange) {
          this.options.onVisibleChange.call(this, visible)
        }
        if (visible) {
          if (this.options.onDropdownOpen) {
            this.options.onDropdownOpen.call(this)
          }
        } else {
          if (this.options.onDropdownClose) {
            this.options.onDropdownClose.call(this)
          }
        }
      },
      onInput (newValue) {
        if (this.params.node.data[this.params.colDef.field] + '' !== newValue + '') {
          // this.params.node.data[this.params.colDef.field] = newValue 此时不要emit，到下面的onChange中去emit，这样在on-select事件监听方法中v-model绑定的属性值还是老值
          if (this.options.onSelect) {
            this.options.onSelect.call(this, newValue)
          }
        }
      },
      onChange (newValue) {
        this.params.node.data[this.params.colDef.field] = newValue
        if (this.options.onChange) {
          this.options.onChange.call(this, this.params, newValue)
        }
        if (this.params.onChange) {
          this.params.onChange.call(this, this.params, newValue)
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
