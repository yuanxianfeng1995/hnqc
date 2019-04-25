<template>
  <jy-form-dialog ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item class="jw-field jw-field-1" label="编号" prop="no">
        <el-input v-model="entity.no"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="商品名称" prop="name">
        <el-input v-model="entity.name"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="单位" prop="manufacturer">
        <el-input v-model="entity.manufacturer"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="数量" prop="number">
        <el-input-number v-model="entity.number" :controls="false"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="价格(元)" prop="price">
        <el-input-number v-model="entity.price" :controls="false"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="金额" prop="purchasedDate">
        <el-input-number v-model="getmoney" disabled/>
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
    name: 'outboundDetail',
    mixins: [DetailMixin],
    // components: {
    //   commodityView: r => require.ensure([], () => r(require('../commodity/View')), 'warehouse-commodity')
    // },
    data () {
      return {
        options: {
          size: 'small',
          context: {
            name: '出库单',
            url: '/hoNeng/OutboundAction.action',
            detailComponent: this
          },
          createEntity(options, cb) {
            cb({
              id: '',
              no: '',
              manufacturer: '',
              price: '',
              number: '',
              purchasedDate: this.$moment().toDate(),
              remark: '',
              money: ''
            })
          }
        },
        entity: {
        },
        rules: {
          name: [
            {required: true, message: '请输入商品名称', trigger: 'blur'},
            {max: 128, message: '长度在128字符以内', trigger: 'blur'}
          ],
        }
      }
    },
    computed: {
      getmoney () {
        return this.entity.price * this.entity.number
      }
    }
  }
</script>
