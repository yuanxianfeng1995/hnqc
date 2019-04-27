<template>
  <div class="jw-form"
       :class="(options.operation === 'view' | options.operation === 'audit' ? 'jw-form-view ' : '') + 'jw-form-' + options.size + ' ' + options.formClass"
       v-loading="$store.getters.isLoading()">
    <div class="jw-form-body">
      <el-form ref="form" :model="entity" :rules="rules" :inline="options.inline">
        <slot>
          <fieldset :disabled="options.operation === 'view' || options.operation === 'audit'">
            <slot name="fieldset"></slot>
          </fieldset>
        </slot>
      </el-form>
    </div>
    <slot name="other"></slot>
    <jy-audit-dialog ref="audit" :dialog-options="{modal: false}" :audit-options="options.auditOptions">
    </jy-audit-dialog>
  </div>
</template>
<script>
  import Vue from 'vue'
  import AsyncValidator from 'async-validator'

  export default {
    name: 'jyForm',
    props: {
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
        titles: {add: '新增', edit: '修改', view: '查看', audit: '审批', review: '审核'},
        bus: new Vue(),
        options: {
          size: 'small', // 可选值：mini（phones 1列）、small（tablets 2列）、middle（desktops 3列）、large（ larger desktops 4列）、full（全屏）
          elDialogSize: {mini: 'tiny', small: 'small', middle: 'small', large: 'large', full: 'full'},
          formClass: '',
          inline: true,
          labelWidth: 100, // 单位px
          auditOptions: {
            title: '',
            label: '',
            placeholder: '',
            context: {
              url: '',
              featureComponent: this,
              getGridComponent (options) {
                return options.context.featureComponent.$refs['grid']
              }
            }
          }
        }
      }
    },
    created () {
      this.options.params = {}
      this.$lodash.merge(this.options, {title: this.titles[this.formOptions.operation]}, this.formOptions)
      if (this.$store.state.layout.window.width < 768) {
        this.options.size = 'mini'
      }
      if (document.getElementsByClassName('jy-dialog-body').length > 0) {
        let parentElmWidth = this.$vnode.elm.previousElementSibling.clientWidth
        switch (true) {
          case parentElmWidth < 992:
            this.options.size = 'mini'
            break
          case parentElmWidth < 1116:
            this.options.size = 'small'
            break
          case parentElmWidth < 1470:
            this.options.size = 'middle'
            break
          default:
            this.options.size = 'large'
        }
      }
    },
    methods: {
      validateField (fieldName) {
        this.$refs.form.validateField(fieldName)
      },
      close () {
        this.$emit('close')
      },
      _loadEntity () {
        console.log("_loadEntity")
        if (this.options.operation === 'add') {
          if (this.options.createEntity) {
            this.options.createEntity.call(this, this.options, (entity) => {
              // this.bus.$emit('loaded-entity', entity)
              this.$emit('loadEntity', entity)
            })
          } else {
            // this.bus.$emit('loaded-entity', {})
            this.$emit('loadEntity', {})
          }
        } else {
          if (this.options.context.url) {
            if (this.options.loadRemoteEntity) {
              this.options.loadRemoteEntity.call(this, this.options, (entity) => {
                // this.bus.$emit('loaded-entity', entity)
                this.$emit('loadEntity', entity)
              })
            } else {
              this.$http.get(this.options.context.url + '?orderBy=id&totalCount=&pageSize=30&pageNo=0&id=' + this.options.params.id, {params: this.options.queryString}).then((response) => {
                // this.bus.$emit('loaded-entity', response.body.success ? response.body.data : {})
                this.$emit('loadEntity', response.body.success ? response.body.data : {})
              })
            }
          } else {
            if (this.options.loadLocalEntity) {
              this.options.loadLocalEntity.call(this, this.options, (entity) => {
                // this.bus.$emit('loaded-entity', entity)
                this.$emit('loadEntity', entity)
              })
            } else {
              // this.bus.$emit('loaded-entity', this.$lodash.cloneDeep(this.options.params))
              this.$emit('loadEntity', this.$lodash.cloneDeep(this.options.params))
            }
          }
        }
      },
      onReject () {
        if (this.entity.audited_comments) {
          this._doAudit(4)
        } else {
          this.$alert('请填写审核意见！', '错误', {
            confirmButtonText: '关闭',
            type: 'error'
          })
        }
      },
      _doAudit (status) {
        this.entity.status = status
        this.$http.put(this.options.context.url + '?' + this.entity.id + '/audit', this.entity, {params: this.options.queryString}).then((response) => {
          this._submitted(response.body)
        })
      },
      _doCustomValidate () {
        return new Promise((resolve, reject) => {
          if (!this.options.getValidateRules) {
            resolve()
            return
          }

          let validateRules = this.options.getValidateRules.call(this, this.options)
          let validator = new AsyncValidator(validateRules)
          validator.validate(this.entity, (errors, fields) => {
            if (errors) {
              let errorMsg = errors ? errors[0].message + '&nbsp' : ''
              this.$message.error({content: errorMsg, duration: 10, closable: true})
              reject(new Error(errorMsg))
              return false
            }
            resolve()
            return true
          })
        })
      },
      onSubmit () {
        return new Promise((resolve, reject) => {
          this.$jw.validate(this.$refs.form).then(() => {
            return this._doCustomValidate()
          }).then(() => {
            if (this.options.submitEntity) {
              this.options.submitEntity.call(this, this.options, (entity) => {
                this._submitted({success: true, data: entity}).then(() => resolve())
              })
              return
            }

            if (!this.options.context.url) {
              this._submitted({success: true, data: this.entity}).then(() => resolve()).catch(e => reject(e))
              return
            }
            console.log("onSubmit",this.entity)
            let p = null
            let subUrl = this.options.context.param ? ('/' + this.options.context.param) : ''
            let cfg = {params: this.options.queryString}
            if (this.options.operation === 'add') {
              p = this.$http.post(this.options.context.url, this.entity, cfg)
            } else if (this.options.operation === 'edit') {
              p = this.$http.put(this.options.context.url + subUrl + '?orderBy=id&totalCount=&pageSize=30&pageNo=0&id=' + this.entity.id, this.entity, cfg)
            } else if (this.options.operation === 'custom') {
              p = this.$http.put(this.options.context.url + subUrl, this.entity, cfg)
            } else {
              let url = this.options.context.url + '?' + this.entity.id + '/' + this.options.operation
              p = this.options.method === 'POST' ? this.$http.post(url, this.entity, cfg) : this.$http.put(url, this.entity, cfg)
            }
            p.then(response => this._submitted(response.body)).then(() => resolve()).catch(e => reject(e))
          }).catch(e => reject(e))
        })
      },
      onAudit () {
        let entities = []
        entities.push(this.entity)
        let auditContext = this.$refs.audit.auditOptions.context
        if (auditContext.params) {
          this.$refs.audit.auditOptions.context.url = auditContext.url + '/' + this.entity.id + '/' + auditContext.params
        }
        // this.$parent.$vnode.context.$refs.audit.open({entities: this.entity})
        this.$refs.audit.open({entities: entities})
      },
      _submitted (result) {
        return new Promise((resolve, reject) => {
          if (result.success) {
            this._refreshGrid()
            this.$emit('submitted', {options: this.options, result: result})
            if (this.options.submitted) {
              this.options.submitted.call(this, this.options, result)
            }
            resolve()
          } else {
            reject(new Error(result.message))
          }
        })
      },
      _refreshGrid () {
        let gridComponent = this.options.context.getGridComponent && this.options.context.getGridComponent.call(this, this.options)
        if (gridComponent) {
          if (gridComponent.$options.name === 'jwGrid') {
            gridComponent.reload()
          } else {
            gridComponent.gridOptions.reload()
          }
        }
      }
    }
  }
</script>
