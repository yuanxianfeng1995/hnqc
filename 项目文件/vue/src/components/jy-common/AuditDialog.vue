<template>
  <jy-dialog ref="dialog">
    <jy-audit ref="audit" :audit-options="options.auditOptions">
      <slot></slot>
    </jy-audit>
  </jy-dialog>
</template>


<script>
  export default {
    name: 'jyAuditDialog',
    props: {
      dialogOptions: {
        type: Object,
        default () {
          return {}
        }
      },
      auditOptions: {type: Object, required: true}
    },
    data () {
      return {
        options: {
          dialogOptions: {
            modal: true,
            height: 'auto',
            commands: [{id: 'approve'}, {id: 'reject'}],
            commandFn: command => this.onCommand(command)
          },
          auditOptions: {}
        }
      }
    },
    created () {
      this._$options = this.$lodash.merge({}, this.options)
    },
    methods: {
      close () { // 供外部调用的接口
        this.$refs.dialog.close()
        let timeout = setTimeout(() => {
          clearTimeout(timeout)
          if (this.$parent.$refs.form) {
            this.$parent.$refs.form.$parent.close()
          }
        }, 500)
      },
      open (options) { // 供外部调用的接口
        this.options = this.$lodash.merge({
          dialogOptions: {title: (this.auditOptions.title)}
        }, this._$options, {
          dialogOptions: this.dialogOptions,
          auditOptions: this.auditOptions
        }, options || {})
        this.$refs.dialog.open(this.options.dialogOptions)

        let _this = this
        let timeout = setTimeout(() => {
          clearTimeout(timeout)
          _this.$refs['audit'].load(options)
        }, 0)
      },
      onCommand (command) {
        return new Promise((resolve, reject) => {
          switch (command.id) {
            case 'approve':
              this.$refs.audit.onApprove().then(() => {
                resolve()
                this.close()
              })
              break
            case 'reject':
              this.$refs.audit.onReject().then(() => {
                resolve()
                this.close()
              })
              break
            default:
              resolve()
          }
        })
      }
    }
  }
</script>
