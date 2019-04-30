<template>
  <div class="jw-view-page">
    <jw-grid ref="grid" :grid-options="gridOptions">
      <detail ref="detail" :detail-options="detailOptions"></detail>
    </jw-grid>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'
  export default {
    name: 'customerView',
    mixins: [ViewlMixin],
    components: {
      Detail: r => require.ensure([], () => r(require('./Detail')), 'customer-information'),
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
            url: '/api/UserAction.action',
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
        headerName: '名称',
        field: 'name',
        tooltipField: 'name',
        type: ['ViewRender', 'LikeFilter'],
        width: 120
      }, {
        headerName: '电话',
        field: 'phone',
        tooltipField: 'phone',
        type: ['LikeFilter'],
        width: 120
      }, {
        headerName: '性别',
        field: 'sex',
        tooltipField: 'sex',
        type: ['LikeFilter'],
        width: 120
      }, {
        headerName: '年龄',
        field: 'age',
        tooltipField: 'age',
        type: ['LikeFilter'],
        width: 120
      }, {
        headerName: '会员等级',
        field: 'level',
        tooltipField: 'level',
        type: ['LikeFilter'],
        width: 120
      }, {
        headerName: '生日',
        field: 'birthday',
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
