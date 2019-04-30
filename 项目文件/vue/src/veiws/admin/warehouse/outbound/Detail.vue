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
      <el-form-item class="jw-field jw-field-1" label="地址电话" prop="addr">
        <el-input v-model="entity.addr"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="总计数量" prop="number">
        <el-input-number v-model="count" disabled/>
      </el-form-item>

      <el-form-item class="jw-field jw-field-1" label="页小计">
        <el-input-number v-model="money" disabled/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="录单日期" prop="purchasedDate">
        <el-date-picker v-model="entity.purchasedDate"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="制单人" prop="making">
        <el-input v-model="entity.making"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="经手人" prop="Handle">
        <el-input v-model="entity.handle"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="备注" prop="remark">
        <el-input v-model="entity.remark" type="textarea" :autosize="{maxRows: 6}"/>
      </el-form-item>
      <el-form-item class="jw-field jw-field-4" label="商品列表"  prop="lendApply">
        <jw-grid ref="grid" style="height: 320px;" :grid-options="gridOptions" >
        </jw-grid>
      </el-form-item>
    </template>

    <template slot="other" >
      <jy-selector-dialog ref="selector" :selector-options="{name: '模板列表', selectedFn: rows => this.onSelected(rows)}">
        <commodity-view ref="lendapplyView" :mode="'selector'" :multiple="true"></commodity-view>
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
              name: '城南宏能汽车',
              manufacturer: '湖南鑫亿德汽车服务有限公司',
              price: 1,
              number: 1,
              purchasedDate: this.$moment().toDate(),
              remark: '',
              money: 1,
              handle: '余琴',
              making: '管理员',
              addr: '长沙市城汽配城2栋132-133 0731-88577735',
              equipmentDetailList: []
            })
          },
          loadRemoteEntity (options, cb) {
            let vm = options.context.detailComponent
            this.$http.get(options.context.url + '?orderBy=id&totalCount=&pageSize=30&pageNo=0&id=' + options.params.id).then((response) => {
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
        type: ['NumberEditor'],
        width: 80
      }, {
        headerName: '单价',
        field: 'price',
        type: ['NumberRender'],
        width: 80
      }, {
        headerName: '备注',
        field: 'remark',
        tooltipField: 'remark',
        width: 150
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
      money: {
        get: function () {
          let money = 0;
          if(this.entity.equipmentDetailList){
            if(this.entity.equipmentDetailList.length > 0){
              this.entity.equipmentDetailList.forEach(function(equipment) {
                money= money + equipment.price * equipment.number || ''
              })
            }
          }
          this.entity.money = money
          return money
        },
        // setter
        set: function (newValue) {
        }
      },
      count: {
        get: function () {
          let count = 0;
          if(this.entity.equipmentDetailList){
            if(this.entity.equipmentDetailList.length > 0){
              this.entity.equipmentDetailList.forEach(function(equipment) {
                count= count + equipment.number || ''
              })
            }
          }
          this.entity.number = count
          return count
        },
        // setter
        set: function (newValue) {
        }
      }
    },
    methods: {
      onLondmoney (params, entity) {
        console.log('onLondmoney', entity)
      },
      onAddItem (params, entity) {
        params.context.featureComponent.$refs.selector.open()
      },
      onSelected (selectedRows, cb) {
        selectedRows.forEach((selectedTemplate) => {
          console.log('selectedTemplate----', selectedTemplate)
          let exist = false
          if (this.entity.equipmentDetailList.length > 0) {
            exist = this.entity.equipmentDetailList.some((template) => {
              return template.id === selectedTemplate.id
            })
          } else {
            this.entity.equipmentDetailList = []
          }
          if (!exist) {
            this.entity.equipmentDetailList.push({
              id: selectedTemplate.id,
              no: selectedTemplate.no,
              name: selectedTemplate.name,
              number: 1,
              price: selectedTemplate.price,
              manufacturer: selectedTemplate.manufacturer,
              purchasedDate: selectedTemplate.purchasedDate,
              remark: ''
            })
          }
        })
        this.$refs['grid'].setData(this.entity.equipmentDetailList)
        return new Promise((resolve, reject) => resolve())
      },
      onRemoveItem (entity) {
        for (let i = 0; i < this.entity.equipmentDetailList.length; i++) {
          if (this.entity.equipmentDetailList[i].id === entity.id) {
            this.entity.equipmentDetailList.splice(i, 1)
            break
          }
        }
        this.gridOptions.api.setRowData(this.entity.equipmentDetailList)
      }
    }
  }
</script>
