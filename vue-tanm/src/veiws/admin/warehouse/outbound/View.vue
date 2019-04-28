<template>
  <div class="jw-view-page">
    <jw-grid ref="grid" :grid-options="gridOptions">
      <outbound-md ref="md" :mode="'dialog'"></outbound-md>
      <equipment-detail ref="detail" :detail-options="detailOptions"></equipment-detail>
    </jw-grid>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'
  export default {
    name: 'outboundView',
    mixins: [ViewlMixin],
    components: {
      EquipmentDetail: r => require.ensure([], () => r(require('./Detail')), 'warehouse-outbound'),
      OutboundMd: r => require.ensure([], () => r(require('./OutboundMd')), 'warehouse-outbound')
    },
    data () {
      return {
        detailOptions: {
          context: {
            featureComponent: this,
            getGridComponent (options) {
              return options.context.featureComponent.$refs['grid']
            }
          }
        },
        gridOptions: {
          toolbarOptions: {
            operations: [{id: 'refresh'}, {id: 'add',}, {id: 'export'}]
          },
          context: {
            name: '出库单',
            url: '/api/OutboundAction.action',
            featureComponent: this,
            getPermissions (params, operation) {
              return params.context.featureComponent.permission
            },
            getDetailComponent (params, operation) {
              return params.context.featureComponent.$refs['detail']
            },
            params: {
              orderBy: 'id desc'
            }
          }
        }
      }
    },
    computed: {
      permission () {
        return {
          add: true,
          edit: true,
          remove: true,
          print: true,
          export: true,
        }
      }
    },
    created () {
      this.gridOptions.columnDefs = [{
        hide: this.mode !== 'selector',
        type: 'Checkbox'
      }, {
        headerName: '单据编号',
        field: 'no',
        tooltipField: 'no',
        type: ['ViewRender', 'LikeFilter'],
        width: 120
      }, {
        headerName: '客户名称',
        field: 'name',
        tooltipField: 'name',
        type: ['LikeFilter'],
        width: 120
      }, {
        headerName: '销货单位',
        field: 'manufacturer',
        tooltipField: 'manufacturer',
        type: ['LikeFilter'],
        width: 120
      }, {
        headerName: '地址电话',
        field: 'addr',
        tooltipField: 'addr',
        type: ['LikeFilter'],
        width: 120
      }, {
        headerName: '总计数量',
        field: 'number',
        type: ['NumberRender'],
        width: 120
      }, {
        headerName: '小计金额',
        field: 'money',
        type: ['NumberRender'],
        width: 120
      }, {
        headerName: '录单日期',
        field: 'purchasedDate',
        type: ['TimestampRender'],
        cellRendererParams: {options: {format: 'YYYY-MM-DD'}},
        width: 120
      }, {
        headerName: '制单人',
        field: 'making',
        tooltipField: 'making',
        width: 120
      }, {
        headerName: '经手人',
        field: 'Handle',
        tooltipField: 'Handle',
        width: 120
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
            id: 'edit',
            permission: 'edit'
          }, {
            id: 'print',
            onClick (params, entity) {
              let vm = params.context.featureComponent
              vm.$refs['md'].open(entity)
            }
          }, {
            id: 'remove',
            permission: 'remove'
          }]
        }
      }]
    },
    activated () {
      this.registQrcodeScannedListener()
    },
    methods: {
      registQrcodeScannedListener () {
        this.$store.commit('registQrcodeScannedListener', this.equipmentScan)
      },
      equipmentScan (param) {
        this.$refs.grid.api.setFilterModel({
          'no': {filter: param.no, init: true}
        })
        this.$refs.grid.reload()
      }
    }
  }
</script>
