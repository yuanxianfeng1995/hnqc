<template>
  <jy-form-dialog ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item class="jw-field jw-field-1" label="姓名" prop="name">
        <el-input v-model="entity.name"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="电话" prop="sex">
        <el-input  v-model="entity.phone" :controls="false"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="性别" prop="sex">
        <el-radio-group v-model="entity.sex">
          <el-radio :label="'男'">男</el-radio>
          <el-radio :label="'女'">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="年龄" prop="age">
        <el-input v-model="entity.age" :controls="false"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="生日" prop="birthday">
        <el-date-picker v-model="entity.birthday" type="date"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="会员等级" prop="level">
        <jw-dict v-model="entity.level" clearable allow-create :data-type="'string'"
                 :dict-item-list="['普通', '银牌', '铜牌', '金牌', '钻石']"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="备注" prop="remark">
        <el-input v-model="entity.remark" type="textarea" :autosize="{maxRows: 6}"/>
      </el-form-item>
    </template>
  </jy-form-dialog>
</template>


<script>
  import {DetailMixin} from 'mixins'

  export default {
    name: 'customerDetail',
    mixins: [DetailMixin],
    data () {
      return {
        options: {
          size: 'small',
          context: {
            name: '出库单',
            url: '/api/UserAction.action',
            detailComponent: this
          },
          createEntity(options, cb) {
            cb({
              id: '',
              sex: '男',
              phone: '',
              age: '',
              birthday: this.$moment().toDate(),
              remark: '',
              level: ''
            })
          }
        },
        entity: {
        },
        rules: {
          name: [
            {required: true, message: '请输入设备名称', trigger: 'blur'},
            {max: 128, message: '长度在128字符以内', trigger: 'blur'}
          ],
        }
      }
    }
  }
</script>
