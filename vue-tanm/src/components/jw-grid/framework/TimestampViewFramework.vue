<template>
  <span  style="color: #20a0ff;cursor:pointer" @click.stop="onClick(operation)">{{ params.value | timestamp(options.format || 'YYYY-MM-DD HH:mm:ss') }}</span>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        options: {
          format: 'YYYY-MM-DD'
        },
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
