<template>
  <el-select v-model="model"
             :clearable="clearable"
             :filterable="filterable || allowCreate"
             :allow-create="allowCreate"
             :multiple="multiple"
             :size="size"
             :disabled="disabled"
             :placeholder="placeholder || (allowCreate ? '请输入或选择' : '请选择')"
             @visible-change="onVisibleChange"
             @input="onInput"
             @change="onChange">
    <el-option v-for="(item, idx) in dictItems"
               :key="idx"
               :value="item.value"
               :label="item.label"
               :disabled="isDisabled(item)">
      <slot :item="item"></slot>
    </el-option>
  </el-select>
</template>


<script>
  export default {
    name: 'jwDict',
    props: {
      value: {type: String | Number | Array, default: null},
      dataType: {type: String, default: 'number'},
      disabled: {type: Boolean, default: false},
      clearable: {type: Boolean, default: false},
      allowCreate: {type: Boolean, default: false},
      multiple: {type: Boolean, default: false},
      filterable: {type: Boolean, default: false},
      size: {type: String, default: null},
      placeholder: {type: String, default: null},
      dictCode: {type: String, required: null},
      dictItemList: {type: Array | Function, default: null},
      dictOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        model: null,
        dictItems: [],
        options: {
          value: 'item_code',
          label: 'item_text',
          isDisabled: null // (item) { return true }
        }
      }
    },
    watch: {
      value (newval, oldval) {
        this._updateModel()
      },
      dictCode (newval, oldval) {
        this._loadDictItems()
      },
      dictItemList (newval, oldval) {
        this._loadDictItems()
      }
    },
    created () {
      this.$lodash.merge(this.options, this.dictOptions)

      this._loadDictItems()
      this._updateModel()
    },
    methods: {
      _loadDictItems () {
        if (this.dictItemList) {
          if (typeof this.dictItemList === 'function') {
            this.dictItemList(this, itemList => {
              this.updateDictItems(itemList)
            })
          } else {
            this.updateDictItems(this.dictItemList)
          }
          return
        }

        this.$store.dispatch('getDictItemList', this.dictCode).then(itemList => {
          this.updateDictItems(itemList)
        })
      },
      updateDictItems (itemList) {
        this.dictItems = itemList.map(item => {
          if (typeof item === 'string') {
            return {value: item, label: item}
          }
          item.value = (typeof this.options.value === 'function' ? this.options.value(item) : item[this.options.value]) + ''
          item.label = typeof this.options.label === 'function' ? this.options.label(item) : item[this.options.label]
          return item
        })
      },
      _updateModel () {
        if (this.multiple) {
          if (this.value) {
            this.model = this.value.map(i => i + '')
          } else {
            this.model = []
          }
          return
        }

        if (this.value || this.value === 0) {
          this.model = this.value + ''
        } else {
          this.model = ''
        }
      },
      _updateValue (newValue) {
        if (this.dataType === 'string' || this.dataType === 'String') {
          return newValue
        } else {
          if (newValue) {
            if (this.multiple) {
              return newValue.map(i => i * 1)
            } else {
              return newValue * 1
            }
          } else {
            return null
          }
        }
      },
      isDisabled (item) {
        if (this.options.isDisabled) {
          return this.options.isDisabled.call(this, item)
        }

        return false
      },
      onVisibleChange (visible) {
        this.$emit(visible ? 'on-dropdown-open' : 'on-dropdown-close')
      },
      onInput (newValue) {
        if (this.value + '' === newValue + '') {
          return
        }
        // this.$emit('input', newValue) 此时不要emit，到下面的onChange中去emit，这样在on-select事件监听方法中v-model绑定的属性值还是老值
        this.$emit('on-select', this._updateValue(newValue))
      },
      onChange (newValue) {
        if (this.value + '' === newValue + '') {
          return
        }
        let val = this._updateValue(newValue)
        this.$emit('input', val)
        this.$emit('on-change', val)
      }
    }
  }
</script>
