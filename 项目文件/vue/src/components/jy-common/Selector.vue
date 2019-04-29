<style>
  .jy-selector {
    width: 100%;
  }
</style>
<template>
  <div class="jy-selector">
    <slot></slot>
  </div>
</template>


<script>
  export default {
    name: 'jySelector',
    props: {
      selectorOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        options: {
          selectFn: null, // function(rows)，必须返回一个Promise
          selectedFn: null // function(rows)，必须返回一个Promise
        }
      }
    },
    created () {
      if (!this._$options) {
        this._$options = this.options
      }
      this.options = this.$lodash.merge({}, this._$options, this.selectorOptions)
    },
    methods: {
      onSelect () {
        return new Promise((resolve, reject) => {
          let viewComponent = this.$children[0]
          // let viewComponent = this.$slots.default[0].componentInstance ? this.$slots.default[0].componentInstance : (this.options.context.getViewComponent && this.options.context.getViewComponent.call(this, this.options))
          if (!viewComponent.getSelectedRows) {
            console.error('请在组件' + viewComponent.$options.name + '中添加getSelectedRows方法！')
            return
          }
          let selectedRows = viewComponent.getSelectedRows()
          if (!selectedRows || selectedRows.length === 0) {
            this.$alert('请先选择一条记录！', '错误', {confirmButtonText: '关闭', type: 'error'})
            return
          }
          if (this.options.selectFn) {
            this.options.selectFn.call(this, selectedRows).then(() => {
              this.options.selectedFn.call(this, selectedRows).then(() => resolve())
            }).catch(e => {
              this.$alert(e.message, '错误', {confirmButtonText: '关闭', type: 'error'})
            })
          } else {
            this.options.selectedFn.call(this, selectedRows, () => resolve()).then(() => resolve())
          }
        })
      }
    }
  }
</script>
