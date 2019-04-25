<template>
  <el-select v-model="model" :size="size" :multiple="options.multiple" :disabled="disabled"
             :readonly="!options.multiple" :filterable="!options.multiple" placeholder="请选择"
             :filter-method="onFilter" @input="onInput" @change="onChanged" @visible-change="onVisibleChange">
    <el-option
      v-for="item in itemList"
      :key="item[options.fullPathFiled]"
      :label="item[options.nameFiled]"
      :value="item[options.idFiled]"
      :disabled="_isItemDisabled(item)">
      <div :style="{'padding-left': _getLevel(item[options.parentPathFiled]) * 14 + 'px'}">
        <slot :item="item">{{ item[options.nameFiled] }}</slot>
      </div>
    </el-option>
  </el-select>
</template>


<script>
  export default {
    name: 'jwTreeSelector',
    props: {
      value: {
        type: String | Number | Array,
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
        emitChange: false,
        model: null,
        itemList: [],
        options: {
          multiple: false,
          cacheKey: null,
          idFiled: 'id',
          nameFiled: 'name',
          parentPathFiled: 'parent_path',
          fullPathFiled: 'full_path',
          offsetLevel: 0,
          loadItemListFn: null, // Promise
          url: null,
          params: {
            tree: false,
            parent_path_like: null,
            orderBy: 'parent_path, order'
          }
        }
      }
    },
    watch: {
      value (newval, oldval) {
        if (!this.emitChange) {
          this._setModel(newval)
        }
      }
    },
    created () {
      this.$lodash.merge(this.options, this.selectorOptions)
      this._loadItemList()

      this._setModel(this.value)
    },
    methods: {
      _loadItemList () {
        if (this.options.loadItemListFn) {
          this.options.loadItemListFn().then(itemList => {
            this.itemList = itemList
          })
          return
        }

        if (this.options.cacheKey) {
          this.itemList = this.$store.getters.getCache(this.options.cacheKey, [])
        }
        if (this.itemList && this.itemList.length > 0) {
          return
        }

        this.$http.get(this.options.url, {showLoading: false, params: this.options.params}).then((response) => {
          this.itemList = response.body.success ? response.body.data.items : []
          if (this.options.cacheKey) {
            this.$store.commit('addCache', {key: this.options.cacheKey, value: this.itemList})
          }
        })
      },
      _isItemDisabled (item) {
        if (this.options.isItemDisabled) {
          return this.options.isItemDisabled.call(this, item)
        }
        return false
      },
      _setModel (val) {
        if (val === this.model) {
          return
        }
        this.model = (val || val === 0) ? val : (this.options.multiple ? [] : null)
      },
      _getLevel (parent_path) {
        let offsetLevel = this.options.params.parent_path_like ? this.options.params.parent_path_like.split('/').length : 2
        return parent_path.split('/').length - offsetLevel
      },
      onFilter (val) {
        if (val === this.model) {
          return
        }

        if (!this.$itemList) {
          this.$itemList = [...this.itemList] // 备份起来
        }

        if (!val || val.trim().length === 0) {
          this.itemList = this.$itemList
          return
        }

        let idSet = this.$itemList.filter(item => !this._isItemDisabled(item) && item[this.options.nameFiled].indexOf(val) > -1).reduce((set, item) => {
          item[this.options.fullPathFiled].split('/').forEach(id => set.add(id))
          set.add(item[this.options.idFiled])
          return set
        }, new Set())
        this.itemList = this.$itemList.filter(item => idSet.has(item[this.options.idFiled]))
      },
      onVisibleChange () {
        if (this.$itemList) {
          this.itemList = this.$itemList
        }
      },
      onInput (newValue) {
        this.$emit('on-select', newValue)
      },
      onChanged (val) {
        this.$emit('input', this.model)

        let selectedItems = null
        if (this.model || this.model === 0) {
          if (typeof this.model === 'object' && this.model.length >= 0) {
            selectedItems = []
            this.model.forEach(selectedItemId => {
              for (let item of this.itemList) {
                if (selectedItemId === item[this.options.idFiled]) {
                  selectedItems.push(item)
                  break
                }
              }
            })
          } else {
            for (let item of this.itemList) {
              if (this.model === item[this.options.idFiled]) {
                selectedItems = item
                break
              }
            }
          }
        }

        this.emitChange = true
        this.$emit('on-change', this.model, this.$lodash.merge({}, selectedItems)) // 这里需要传selectedItems的副本，不传内存地址
        this.$nextTick(() => {
          this.emitChange = false
        })
      }
    }
  }
</script>
