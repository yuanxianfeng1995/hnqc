<template>
  <jy-dialog ref="dialog" :dialog-options="{dialogClass:'jy-dialog-selector'}">
    <div class="jw-view-page">
      <jw-grid ref="grid" :grid-options="gridOptions"></jw-grid>
    </div>
  </jy-dialog>
</template>


<script>
  import Vue from 'vue'

  export default {
    name: 'jwAuditHistoryDialog',
    data () {
      return {
        gridOptions: {
          toolbarOptions: {operations: [{id: 'refresh'}]},
          context: {
            name: '流程记录',
            url: '/api/hnjky/testing/lab/saga/node/instance',
            featureComponent: this,
            getDetailComponent (params, operation) {
              return params.context.featureComponent.$refs['detail']
            },
            params: {}
          }
        }
      }
    },

    created () {
      this.gridOptions.columnDefs = [{
        type: ['IndexRender']
      }, {
        headerName: '节点名称',
        field: 'node_id',
        pinned: 'left',
        type: ['DictRender'],
        cellRendererParams: {dict: 'SagaNodeName'},
        width: 150
      }, {
        headerName: '创建时间',
        field: 'created_time',
        type: ['TimestampRender', 'TimestampFilter']
      }, {
        headerName: '办理人',
        field: 'dealt_id',
        type: ['DictRender'],
        cellRendererParams: {dict: 'UserNameAll'},
        width: 80
      }, {
        headerName: '办理时间',
        field: 'dealt_time',
        type: ['TimestampRender', 'TimestampFilter']
      }, {
        headerName: '办理结果',
        field: 'other',
        suppressSizeToFit: false,
        cellRendererFramework: Vue.extend({
          render (createElement) {
            // console.log('render', this.params.value)
            let other = this.params.value
            if (!other) {
              return createElement('div', null, '')
            }

            if (other.approve !== undefined) {
              let text = other.approve ? '同意。' : '驳回。'
              if (other.opinion) {
                text += '\n' + other.opinion
              }
              return createElement('div', {
                style: 'overflow: hidden;white-space: nowrap;text-overflow:ellipsis;',
                domProps: {title: text}
              }, text)
            }
            return createElement('div', null)
          },
          methods: {
            onClick () {
              let detailComponent = this.params.context.getDetailComponent.call(this, this.params, {})
              detailComponent.open({
                operation: 'view',
                title: '查看检测项目可选评定标准组',
                params: this.params.node && this.params.node.data ? this.params.node.data : {}
              })
            }
          }
        }),
        minWidth: 100,
        width: 150
      }, {
        headerName: '节点状态',
        field: 'status',
        pinned: 'right',
        type: ['DictRender', 'DictFilter'],
        cellRendererParams: {dict: 'SagaNodeStatus'},
        width: 80
      }]
    },
    methods: {
      close () { // 供外部调用的接口
        this.$refs.dialog.close()
      },
      open (options) { // 供外部调用的接口
        this.gridOptions.context.params = this.$lodash.merge({orderBy: 'created_time desc'}, options.params || {})
        this.$refs.dialog.open(options)
      }
    }
  }
</script>
