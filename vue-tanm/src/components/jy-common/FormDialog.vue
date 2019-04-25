<style>
  .jy-dialog-form {
    width: auto;
  }
</style>

<template>
  <jy-dialog ref="dialog">
    <jy-form ref="form" :form-options="options" :entity="entity" :rules="rules" @loadEntity="loadEntity" @close="close">
      <slot>
        <fieldset :disabled="options.operation === 'view' || options.operation === 'audit'">
          <slot name="fieldset"></slot>
        </fieldset>
      </slot>
      <slot name="other"></slot>
    </jy-form>
  </jy-dialog>
</template>


<script>
  import Vue from 'vue'

  export default {
    name: 'jyFormDialog',
    props: {
      dialogOptions: {
        type: Object,
        default () {
          return {}
        }
      },
      formOptions: {
        type: Object,
        default () {
          return {}
        }
      },
      entity: {
        type: Object,
        required: true
      },
      rules: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        bus: new Vue(),
        titles: {add: '新增', edit: '修改', view: '查看', audit: '审批', review: '审核'},
        commands: {audit: 'audit', review: 'review'},
        options: {
          dialogOptions: {
            modal: true,
            dialogClass: 'jy-dialog-form',
            height: 'auto',
            commandFn: command => this.onCommand(command)
          },
          formOptions: {}
        }
      }
    },
    created () {
      this._$options = this.$lodash.merge({}, this.options)
    },
    methods: {
      getForm () {
        return this.$refs['form']
      },
      validateField (fieldName) {
        this.$refs.form.validateField(fieldName)
      },
      close () { // 供外部调用的接口
        this.$refs.dialog.close()
      },
      open (options) { // 供外部调用的接口
        this.options = this.$lodash.merge({
          dialogOptions: {
            title: options.title || (this.titles[this.formOptions.operation] + (this.formOptions.context.name || ''))
          }
        }, this._$options, {
          dialogOptions: this.dialogOptions,
          formOptions: this.formOptions
        }, options || {})

        if (!this.options.dialogOptions.commands) {
          if (this.options.operation === 'view') {
            this.options.dialogOptions.commands = []
          } else {
            this.options.dialogOptions.commands = [{id: 'cancel'}, {id: this.commands[this.formOptions.operation] || 'save'}]
          }
        }

        this.$refs.dialog.open(this.options.dialogOptions)

        let _this = this
        _this.timeout = setTimeout(() => {
          _this.$refs['form']._loadEntity()
          clearTimeout(_this.timeout)
        }, 0)
      },
      onCommand (command) {
        return new Promise((resolve, reject) => {
          switch (command.id) {
            case 'save':
              this.$refs.form.onSubmit().then(() => resolve())
              break
            case 'audit':
              this.$refs.form.onAudit()
              break
            case 'review':
              this.$refs.form.onAudit()
              break
            default:
              resolve()
          }
        })
      },
      loadEntity (entity) {
        this.bus.$emit('loaded-entity', entity)
      }
    }
  }
</script>
