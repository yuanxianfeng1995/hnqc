<template>
  <div class="jw-view-page">
    <jw-grid ref="grid" :grid-options="gridOptions">
      <!--<jy-dialog ref="mbDialog">-->
        <!--<outbound-mb ref="mb" :mode="'dialog'"></outbound-mb>-->
      <!--</jy-dialog>-->
      <detail ref="detail" :detail-options="detailOptions"></detail>
    </jw-grid>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'
  export default {
    name: 'commodityView',
    mixins: [ViewlMixin],
    components: {
      Detail: r => require.ensure([], () => r(require('./Detail')), 'warehouse-commodity'),
      OutboundMb: r => require.ensure([], () => r(require('./Mb')), 'warehouse-commodity')
    },
    data () {
      return {
        prints: {},
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
            url: '/api/CommodityAction.action',
            featureComponent: this,
            getPermissions (params, operation) {
              return params.context.featureComponent.permission
            },
            getDetailComponent (params, operation) {
              return params.context.featureComponent.$refs['detail']
            },
            params: {
              orderBy: 'id'
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
        headerName: '录入时间',
        field: 'purchasedDate',
        type: ['TimestampRender'],
        cellRendererParams: {options: {format: 'YYYY-MM-DD'}},
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
            id: 'edit',
            permission: 'edit'
          }, {
            id: 'print',
            onClick (params, entity) {
              let vm = params.context.featureComponent
              vm.$refs['mbDialog'].open({title: '打印商品信息'})
              // setTimeout(() => {
              //   printJS({ printable: 'printJS-iframe', type: 'html',
              //     scanStyles: false,
              //     style: '#printJS-iframe{width: 600px;margin: 0 auto;font-size: 18px;}#printJS-iframe header{\n' +
              //       '  text-align: center;}#printJS-iframe>div>span{  display: inline-block;\n' +
              //       '  width: 250px;margin-right: 30px;height: 40px;\n' +
              //       '  line-height: 40px;overflow: hidden;}#printJS-iframe h2 {\n' +
              //       '  text-align: center;}'
              //   })
              // },600)
            }
          }, {
            id: 'remove',
            permission: 'remove'
          }]
        }
      }]
    },
    methods: {
    }
  }
</script>
