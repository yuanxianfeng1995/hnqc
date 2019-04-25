<template>
  <a @click.stop="onClick(operation)" :title="operation.title" style="cursor: pointer;">
    {{ params.value | timestamp(options.format || 'YYYY-MM-DD HH:mm:ss') }}
  </a>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        operation: {},
        options: {
          format: 'YYYY-MM-DD HH:mm:ss'
        },
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
      this.$lodash.merge(this.options, this.params.options || {})
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
