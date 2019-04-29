<template>
  <jy-dialog ref="dialog">
    <jy-selector ref="selector" :selector-options="options.selectorOptions">
      <slot></slot>
    </jy-selector>
  </jy-dialog>
</template>


<script>
  export default {
    name: 'jySelectorDialog',
    props: {
      dialogOptions: {
        type: Object,
        default () {
          return {}
        }
      },
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
          dialogOptions: {
            dialogClass: 'jy-dialog-selector',
            commands: [{id: 'cancel'}, {id: 'select'}],
            commandFn: command => this.onCommand(command)
          },
          selectorOptions: {}
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
        this.options = this.$lodash.merge({
          dialogOptions: {title: '请选择' + (this.selectorOptions.name || this.selectorOptions.context.name || '')}
        }, this._$options, {
          dialogOptions: this.dialogOptions,
          selectorOptions: this.selectorOptions
        }, options || {})

        this.$refs.dialog.open(this.options.dialogOptions)
      },
      onCommand (command) {
        return new Promise((resolve, reject) => {
          switch (command.id) {
            case 'select':
              this.$refs.selector.onSelect().then(() => resolve())
              break
            default:
              resolve()
          }
        })
      }
    }
  }
</script>
