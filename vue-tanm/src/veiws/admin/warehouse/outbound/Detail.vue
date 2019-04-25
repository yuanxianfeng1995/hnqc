<template>
  <jy-form-dialog ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item class="jw-field jw-field-1" label="单据编号" prop="no">
        <el-input v-model="entity.no"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="客户名称" prop="name">
        <el-input v-model="entity.name"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="销货单位" prop="manufacturer">
        <el-input v-model="entity.manufacturer"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="地址" prop="addr">
        <el-input v-model="entity.addr"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="总计数量" prop="number">
        <el-input-number v-model="entity.number" disabled/>
      </el-form-item>

      <el-form-item class="jw-field jw-field-1" label="页小计">
        <el-input-number v-model="getmoney" disabled/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="录单日期" prop="purchasedDate">
        <el-date-picker v-model="entity.purchasedDate"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="制单人" prop="making">
        <el-input-number v-model="entity.making" disabled/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="经手人" prop="Handle">
        <el-input-number v-model="entity.Handle" disabled/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="备注" prop="remark">
        <el-input v-model="entity.remark" type="textarea" :autosize="{maxRows: 6}"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-4" label="商品列表"  prop="f_lendApply">
        <jw-grid ref="grid" style="height: 320px;" :grid-options="gridOptions" >
        </jw-grid>
      </el-form-item>
    </template>

    <template slot="other" >
      <jy-selector-dialog ref="selector" :selector-options="{name: '模板列表', selectedFn: rows => this.onSelected(rows)}">
        <commodity-view ref="lendapplyView" :mode="'selector'" :multiple="true" :view-options="orderViewOptions"></commodity-view>
      </jy-selector-dialog>
    </template>
  </jy-form-dialog>
</template>


<script>
  import {DetailMixin} from 'mixins'

  export default {
    name: 'outboundDetail',
    mixins: [DetailMixin],
    components: {
      commodityView: r => require.ensure([], () => r(require('../commodity/View')), 'warehouse-commodity')
    },
    data () {
      return {
        gridOptions: {
          toolbarOptions: {operations: []},
          rowModelType: 'clientSide',
          pagination: false,
          enableFilter: false,
          floatingFilter: false,
          suppressToolbar: true,
          context: {
            featureComponent: this,
            getPermissions (params, operation) {
              return {add: true, remove: true}
            }
          }
        },
        options: {
          size: 'small',
          context: {
            name: '出库单',
            url: '/api/OutboundAction.action',
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
              money: '',
              Handle: '',
              making: '',
              addr: ''
            })
          },
          loadRemoteEntity (options, cb) {
            let vm = options.context.detailComponent
            this.$http.get(options.context.url + '?' + options.params.f_id).then((response) => {
              let entity = response.body.success ? response.body.data : {}
              cb(entity)
              vm.$refs['grid'].setData(entity.equipmentDetailList)
            })
          },
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
    created () {
      this.gridOptions.columnDefs = [{
        pinned: 'left',
        type: ['IndexRender', 'AddHeader'],
        headerComponentParams: {
          operation: {
            isDisabled (params, entity) {
              return params.context.featureComponent.options.operation === 'view'
            },
            onClick (params, entity) {
              params.context.featureComponent.onAddItem(params)
            }
          }
        }
      }, {
        headerName: '编号',
        field: 'no',
        tooltipField: 'no',
        type: ['ViewRender', 'LikeFilter'],
        width: 120
      }, {
        headerName: '商品全名',
        field: 'name',
        tooltipField: 'name',
        type: ['ViewRender', 'LikeFilter'],
        width: 120
      }, {
        headerName: '单位',
        field: 'manufacturer',
        tooltipField: 'manufacturer',
        width: 120
      }, {
        headerName: '数量',
        field: 'number',
        type: ['NumberRender'],
        width: 120
      }, {
        headerName: '单价',
        field: 'price',
        type: ['NumberRender'],
        width: 120
      }, {
        headerName: '金额',
        field: 'money',
        type: ['NumberRender'],
        width: 120
      }, {
        headerName: '备注',
        field: 'remark',
        tooltipField: 'remark',
        width: 150,
        suppressSizeToFit: false
      }, {
        hide: this.mode === 'selector',
        type: 'OperationRender',
        cellRendererParams: {
          operations: [{
            id: 'remove',
            permission: 'remove',
            isDisabled (params, entity) {
              let operation = params.context.featureComponent.options.operation
              if (operation === 'view' | operation === 'audit') {
                return true
              }
              return false
            },
            onClick (params, entity) {
              params.context.featureComponent.onRemoveItem(entity)
            }
          }]
        }
      }]
    },
    computed: {
      getmoney () {
        return this.entity.price * this.entity.number
      }
    }
    ,
    methods: {
      onAddItem (params, entity) {
        params.context.featureComponent.$refs.selector.open()
      },
      onSelected (selectedRows, cb) {
        selectedRows.forEach((selectedTemplate) => {
          let exist = false
          if (this.entity.equipmentDetailList.length > 0) {
            exist = this.entity.equipmentDetailList.some((template) => {
              return template.f_id === selectedTemplate.f_id
            })
          } else {
            this.entity.equipmentDetailList = []
          }
          if (!exist) {
            this.entity.equipmentDetailList.push({
              f_id: selectedTemplate.f_id,
              f_equipment_id: selectedTemplate.f_equipment_id,
              f_apply_id: selectedTemplate.f_apply_id,
              f_apply_no: selectedTemplate.f_apply_no,
              f_equipment_no: selectedTemplate.f_equipment_no,
              f_equipment_name: selectedTemplate.f_equipment_name,
              f_borrower: selectedTemplate.f_borrower,
              f_depositary: selectedTemplate.f_depositary,
              f_project_name: '',
              f_restitute_expect_date: selectedTemplate.f_restitute_expect_date || '',
              f_restitute_state: 101,
              f_lend_state: selectedTemplate.f_lend_status || 101,
              f_status: 101,
              f_remark: ' '
            })
            this.orderViewOptions.gridOptions.context.params.f_id.push({
              f_id: selectedTemplate.f_id
            })
          }
        })
        this.$refs['grid'].setData(this.entity.equipmentDetailList)
        return new Promise((resolve, reject) => resolve())
      },
      onRemoveItem (entity) {
        for (let i = 0; i < this.entity.equipmentDetailList.length; i++) {
          if (this.entity.equipmentDetailList[i].f_id === entity.f_id) {
            this.entity.equipmentDetailList.splice(i, 1)
            break
          }
        }
        this.gridOptions.api.setRowData(this.entity.equipmentDetailList)
      }
    }
  }
</script>
