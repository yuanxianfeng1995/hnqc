<template>
  <div class="jw-form jw-form-small">
    <div class="jw-form-body">
      <el-form ref="form" :model="audit" :rules="options.rules" label-position="top" label-width="300px">
        <el-form-item style="margin-bottom: 0;" :label="options.label" prop="opinion">
          <el-input v-model="audit.opinion" type="textarea" :autosize="{minRows: 10, maxRows: 18}"
                    :placeholder="options.placeholder">
          </el-input>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<script>
  export default {
    name: 'jyAudit',
    props: {
      auditOptions: {type: Object, required: true}
    },
    data () {
      return {
        size: 'small',
        audit: {
          approve: true,
          opinion: null, // 审批意见
          entities: []
        },
        options: {
          title: null,
          label: null,
          placeholder: null,
          closeOnClickModal: false,
          modal: false,
          context: {
            url: null,
            featureComponent: null,
            getGridComponent: null // 函数
          },
          rules: {
            opinion: [{
              validator: (rule, value, callback) => {
                if (!this.audit.approve && !this.audit.opinion) {
                  callback(new Error('请输入' + this.options.label))
                  return
                }
                callback()
              }
            }]
          },
          onReject: null, //
          onApprove: null //
        }
      }
    },
    methods: {
      load (options) {
        this.audit.opinion = null
        this.audit.entities = options.entities || []
        this.$lodash.merge(this.options, this.auditOptions, options)
      },
      onReject () {
        return new Promise((resolve, reject) => {
          this.audit.approve = false
          this.$refs.form.validate(valid => {
            if (!valid) {
              return false
            }

            if (this.options.onReject) {
              this.options.onReject.call(this, this.options, this.audit, errorMsg => {
                if (!errorMsg) {
                  this._refreshGrid()
                }
              })
            } else {
              this._submit().then(() => resolve())
            }
          })
        })
      },
      onApprove () {
        return new Promise((resolve, reject) => {
          this.audit.approve = true
          if (this.options.onApprove) {
            this.options.onApprove.call(this, this.options, this.audit, errorMsg => {
              if (!errorMsg) {
                this._refreshGrid().then(() => resolve())
              }
            })
          } else {
            this._submit().then(() => resolve())
          }
        })
      },
      _submit () {
        return new Promise((resolve, reject) => {
          if (!this.options.context.url) {
            console.error('请配置提交的url！')
            return
          }
          this.$http.put(this.options.context.url, this.audit).then(response => {
            if (response.body.success) {
              this._refreshGrid()
              resolve()
            }
          })
        })
      },
      _refreshGrid () {
        let gridComponent = this.options.context.getGridComponent && this.options.context.getGridComponent.call(this, this.options)
        if (gridComponent) {
          gridComponent.gridOptions.api.deselectAll()
          if (gridComponent.$options.name === 'jwGrid') {
            gridComponent.reload()
          } else {
            gridComponent.gridOptions.reload()
          }
        } else {
          gridComponent = this.options.context.getGridComponent(this.options.context.featureComponent.options)
          gridComponent.gridOptions.api.deselectAll()
          gridComponent.reload()
        }
      }
    }
  }
</script>
