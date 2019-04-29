<template>
  <div class="jw-view-page">
    <jw-grid ref="grid" :grid-options="gridOptions">
      <equipment-detail ref="detail" :detail-options="detailOptions"></equipment-detail>
    </jw-grid>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'
  export default {
    name: 'maintainView',
    mixins: [ViewlMixin],
    components: {
      EquipmentDetail: r => require.ensure([], () => r(require('./Detail')), 'customer-repair'),
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
            name: '维修信息',
            url: '/hoNeng/RepairAction.action',
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
        type: ['IndexRender']
      }, {
        headerName: '客户姓名',
        field: 'userName',
        tooltipField: 'userName',
        type: ['ViewRender', 'LikeFilter'],
        width: 120
      }, {
        headerName: '车牌号码',
        field: 'license',
        tooltipField: 'license',
        width: 120
      }, {
        headerName: '里程',
        field: 'mileage',
        tooltipField: 'mileage',
        width: 120
      }, {
        headerName: '交强险到期时间',
        field: 'compulsoryInsurance',
        type: ['TimestampRender'],
        cellRendererParams: {options: {format: 'YYYY-MM-DD'}},
        width: 120
      }, {
        headerName: '维修内容',
        field: 'content',
        tooltipField: 'content',
        width: 120
      }, {
        hide: this.mode === 'selector',
        type: 'OperationRender',
        cellRendererParams: {
          operations: [{
            id: 'edit',
            permission: 'edit'
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
