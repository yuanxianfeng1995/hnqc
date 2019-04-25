<style>
  .jy-dialog-report {
    width: auto;
  }

  .jy-dialog-body > .jw-report > .jw-report-body > .jw-report-iframe {
    margin: 20px;
  }
</style>


<template>
  <jy-dialog ref="dialog">
    <jw-report ref="report" style="border: none;" :report-options="options.reportOptions"></jw-report>
  </jy-dialog>
</template>


<script>
  export default {
    name: 'jwReportDialog',
    props: {
      dialogOptions: {
        type: Object,
        default () {
          return {}
        }
      },
      reportOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        options: {
          dialogOptions: {
            modal: true,
            dialogClass: 'jy-dialog-report',
            height: 'auto',
            commandFn: command => this.onCommand(command)
          },
          reportOptions: {
            context: {
              reportDialogComponent: this
            }
          }
        }
      }
    },
    created () {
      this._$options = this.$lodash.merge({}, this.options)
    },
    methods: {
      close () { // 供外部调用的接口
        this.$refs.dialog.close()
      },
      open (options) { // 供外部调用的接口
        // console.log('open', options)
        this.options = this.$lodash.merge({}, this._$options, {
          dialogOptions: this.dialogOptions,
          reportOptions: this.reportOptions
        }, options || {})

        this.$refs.dialog.open(this.options.dialogOptions)
      },
      onCommand (command) {
        return new Promise((resolve, reject) => {
          switch (command.id) {
            case 'save':
              this.$refs.report.onCommand({id: 'save'}).then(() => {
                resolve()
                let gridComponent = this.options.context.getGridComponent && this.options.context.getGridComponent.call(this, this.options)
                if (gridComponent) {
                  gridComponent.reload()
                }
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
