<template>
  <el-button-group class="jw-button-group">
    <el-button v-for="(operation, index) in operations" :key="operation.id"
               size="mini"
               :type="operation.type"
               :title="operation.tips || operation.title"
               :disabled="!hasPermission(operation) || isDisabled(operation)"
               @click.stop="onClick(operation)">
      <i :class="operation.icon" style="min-width:14px;"></i>{{operation.text ? (' ' + operation.text) : ''}}
    </el-button>
  </el-button-group>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        operations: [],
        defaultOperations: {
          add: {
            title: '增加',
            icon: 'fa fa-plus',
            permission: 'add'
          },
          edit: {
            title: '修改',
            icon: 'fa fa-edit',
            permission: 'edit'
          },
          audit: {
            title: '审批',
            icon: 'fa fa-check',
            permission: 'audit'
          },
          review: {
            title: '审核',
            icon: 'fa fa-check',
            permission: 'review'
          },
          view: {
            title: '查看',
            icon: 'fa fa-file-text'
          },
          print: {
            title: '打印',
            icon: 'fa fa-print',
            permission: 'print'
          },
          sql: {
            title: '导出SQL脚本',
            icon: 'fa fa-file-code-o',
            permission: 'sql'
          },
          remove: {
            title: '删除',
            type: 'danger',
            icon: 'fa fa-trash',
            permission: 'remove'
          }
        }
      }
    },
    computed: {
      entity () {
        return this.params.node && this.params.node.data ? this.params.node.data : {}
      }
    },
    created () {
      this.operations = []
      this.params.operations.forEach((operation) => {
        let defaultOperation = this.defaultOperations[operation.id] || {title: ''}
        if (operation.id !== 'remove') {
          operation.type = 'primary'
        }
        this.operations.push(Vue.lodash.merge({}, defaultOperation, {title: defaultOperation.title + this.params.context.name}, operation))
      })
    },
    methods: {
      hasPermission (operation) {
        if (operation.permission) {
          if (this.params.context.getPermissions) {
            return this.params.context.getPermissions.call(this, this.params, operation)[operation.permission]
          } else {
            return false
          }
        }
        return true
      },
      isDisabled (operation) {
        if (operation.isDisabled) {
          return operation.isDisabled.call(this, this.params, this.entity)
        }
        return false
      },
      onClick (operation) {
        if (!operation.onClick) {
          this.params.context.jwGridVueComponent.onDetailOperation(operation, this.entity)
          return
        }

        operation.onClick.call(this, this.params, this.entity, () => {
          this.params.context.jwGridVueComponent.onDetailOperation(operation, this.entity)
        })
      }
    }
  })
</script>
