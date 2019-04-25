<template>
  <span style="color: #20a0ff;cursor:pointer" @click.stop="onClick(operation)">{{ dictItemText }}</span>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        dictItemText: null,
        operation: {},
        defaultOperation: {
          id: 'view',
          title: '查看'
        }
      }
    },
    computed: {
      entity () {
        return this.params.node && this.params.node.data ? this.params.node.data : {}
      }
    },
    created () {
      this.operation = Vue.lodash.merge({}, this.defaultOperation, {title: this.defaultOperation.title + this.params.context.name}, this.params.operation)
      Vue.store.dispatch('getDictItemText', {
        dictCode: this.params.dictCode || this.params.dict,
        itemCode: this.params.value
      }).then(itemText => {
        // console.log('itemText', this.params.dictCode || this.params.dict, this.params.value, itemText)
        this.dictItemText = itemText
      })
    },
    methods: {
      onClick (operation) {
        if (operation.onClick) {
          operation.onClick.call(this, this.params, this.entity)
          return
        }
        let detailComponent = this.params.context.getDetailComponent && this.params.context.getDetailComponent.call(this, this.params, operation)
        if (detailComponent) {
          detailComponent.open({
            operation: operation.id,
            title: operation.title,
            params: this.entity
          })
        }
      }
    }
  })
</script>
