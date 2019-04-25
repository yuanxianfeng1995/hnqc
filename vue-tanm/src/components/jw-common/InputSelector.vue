<template>
  <div class="jw-input-selector">
    <el-select ref="input" class="jw-input-selector-input" popper-class="jw-input-selector-popper"
               v-model="ids" :size="size" :disabled="disabled" multiple readonly :placeholder="placeholder"
               @visible-change="openSelector" @remove-tag="onRemoveTag">
      <el-option v-for="item in model" :key="item.id" :label="item.name" :value="item.id"></el-option>
    </el-select>

    <jy-selector-dialog ref="selectorDialog"
                        :dialog-options="options.dialogOptions"
                        :selector-options="options.selectorOptions">
      <slot></slot>
    </jy-selector-dialog>
  </div>
</template>


<script>
  export default {
    name: 'jwInputSelector',
    props: {
      value: {
        type: Object | Array,
        default () {
          return {id: null, name: null}
        }
      },
      disabled: {type: Boolean, default: false},
      size: {type: String, default: null},
      placeholder: {type: String, default: '请选择'},
      inputOptions: {
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
          idFiled: 'id',
          nameFiled: 'name',
          dialogOptions: {dialogClass: 'jy-dialog-selector jy-dialog-selector-input'},
          selectorOptions: {selectedFn: selectedRows => this.onSelected(selectedRows)}
        }
      }
    },
    computed: {
      ids: {
        get () {
          return this.model ? this.model.map((item, idx) => {
            return item.id
          }) : []
        },
        set (ids) {
        }
      }
    },
    watch: {
      value (newval, oldval) {
        this._setModel(newval)
      }
    },
    created () {
      this.$lodash.merge(this.options, this.inputOptions)
      this._setModel(this.value)
    },
    methods: {
      _setModel (val) {
        if (val) {
          let id = val[this.options.idFiled]
          if (id || id === 0) {
            this.model = [{id: id, name: val[this.options.nameFiled]}]
            return
          }
        }

        this.model = null
      },
      openSelector (visible) {
        if (visible) {
          this.$refs.selectorDialog.open()
        }
      },
      onSelected (selectedRows) {
        let entity = selectedRows[0]
        this._setModel(entity)
        this.$emit('input', entity)
        this.$emit('on-change', entity)
        return new Promise((resolve, reject) => resolve())
      },
      onRemoveTag (tagComponent) {
        this.model = null

        let entity = {}
        entity[this.options.idFiled] = null
        entity[this.options.nameFiled] = null
        this.$emit('input', entity)

        entity[this.options.idFiled] = tagComponent.value
        entity[this.options.nameFiled] = tagComponent.label
        this.$emit('on-remove', entity)
      }
    }
  }
</script>
