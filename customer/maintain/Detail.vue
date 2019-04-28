<template>
  <jy-form-dialog ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item class="jw-field jw-field-1" label="客户姓名" prop="name">
        <jw-input-selector v-model="contract"
                           :input-options="contractInputOptions"
                           :disabled="options.operation !== 'add'">
          <information-view ref="informationView" :mode="'selector'"/>
        </jw-input-selector>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="车牌号码" prop="license">
        <el-input  v-model="entity.license" />
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="里程" prop="license">
        <el-input  v-model="entity.mileage" />
      </el-form-item>
      <el-form-item class="jw-field jw-field-1 jw-middle-label" label="交强险到期时间" prop="birthday">
        <el-date-picker v-model="entity.CompulsoryInsurance" type="date"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="保养内容" prop="remark">
        <el-input v-model="entity.content" type="textarea" :autosize="{maxRows: 6}"/>
      </el-form-item>
    </template>
  </jy-form-dialog>
</template>


<script>
  import {DetailMixin} from 'mixins'

  export default {
    name: 'maintainDetail',
    mixins: [DetailMixin],
    components: {
      InformationView: r => require.ensure([], () => r(require('../information/View')), 'customer-information'),
    },
    data () {
      return {
        contractOptions: {
          gridOptions: {
          }
        },
        contractInputOptions: {
          nameFiled: 'name',
          selectorOptions: {
            height: 400,
            context: {
              name: '客户信息',
              featureComponent: this,
              getViewComponent (options) {
                return options.context.featureComponent.$refs['informationView']
              }
            }
          }
        },
        options: {
          size: 'small',
          context: {
            name: '保养信息',
            url: '/api/UserAction.action',
            detailComponent: this
          },
          createEntity(options, cb) {
            cb({
              id: '',
              license: '',
              mileage: '',
              CompulsoryInsurance: this.$moment().toDate(),
              content: '',
              userID: ''
            })
          },
          loadRemoteEntity (options, cb) {
            let vm = options.context.detailComponent
            this.$http.get(options.context.url + '?orderBy=id&totalCount=&pageSize=30&pageNo=0&id=' + options.params.id).then((response) => {
              let entity = response.body.success ? response.body.data : {}
              this.$http.get('/api/UserAction.action?' + entity.userID).then((response) => {
                vm.contractEntity = response.body.success ? response.body.data : {}
                entity.username = vm.contractEntity.name
                cb(entity)
              })
            })
          }
        },
        entity: {
        },
        rules: {
          name: [
            {required: true, message: '请选择客户名称', trigger: 'blur'}
          ],
        }
      }
    },
    computed: {
      contract: { // 客户列表
        get () {
          return {id: this.entity.userID, name: this.entity.name}
        },
        set (contract) {
          this.entity.userID = contract.id
          this.entity.name = contract.name
        }
      }
    }
  }
</script>
