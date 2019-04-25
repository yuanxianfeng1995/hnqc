<style>
  .jw-grid-header {
    position: relative;
    height: 33px;
    border-bottom: 1px solid #eee;
  }

  .jw-grid-title {
    position: absolute;
    line-height: 33px;
    padding: 0px 15px;
  }

  .jw-grid-toolbar {
    position: absolute;
    right: 0px;
    line-height: 24px;
    padding: 4px 5px;
    display: flex;
  }
</style>
<template>
  <div>
    <el-popover
      ref="popover"
      placement="bottom-end"
      trigger="click"
      v-model="visible">
      <jw-grid ref="grid" :grid-options="gridOptions" style="height: 467px; width: 300px"></jw-grid>
      <div style="text-align: right; margin-top: 10px">
        <el-button type="primary" size="mini" @click="changeColumn">{{currOperation === 'export' ? '导出' : '确定'}}
        </el-button>
      </div>
    </el-popover>
    <div class="jw-grid-header" v-if="!toolOptions.suppressToolbar">
      <div class="jw-grid-title">{{ toolOptions.toolbarOptions.title }}</div>
      <div class="jw-grid-toolbar">
        <el-button-group class="jw-button-group">
          <el-button size="mini"
                     v-for="(operation, index) in toolbarOperations"
                     :key="operation.id"
                     :type="operation.type"
                     :title="operation.tips || operation.title"
                     :disabled="!_hasPermission(operation) || _isDisabled(operation)"
                     @click.stop="onToolbarButtonClick(operation, $event)">
            <i :class="operation.icon" style="min-width:14px;"></i>{{operation.text ? (' ' + operation.text) : ''}}
          </el-button>
        </el-button-group>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'toolBar',
    props: {
      toolOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        currOperation: null,
        toolbarOptions: {
          title: this.toolOptions.context.name + '列表',
          operations: [],
          filterOperations: [],
          supportFilter: false,
          printOptions: {
            // layout: '',  // 纵向（默认）、横向（portrait）
            queryString: {
              // reportlet: 'hnjky/device/equipment.cpt', // 模板路径
              op: 'print_preview'
            }
          }
        },
        gridOptions: {
          pagination: false,
          enableFilter: false,
          floatingFilter: false,
          suppressToolbar: true,
          rowSelection: 'multiple',
          groupSelectsChildren: true,
          context: {
            name: '筛选',
            featureComponent: this,
            params: {
              tree: true
            }
          },
          getRowNodeId (item) {
            return item.headerName
          },
          getNodeChildDetails (rowData) {
            if (rowData.children && rowData.children.length > 0) {
              return {
                group: true,
                expanded: true, // 全展开
                children: rowData.children,
                key: rowData.field
              }
            } else {
              return null
            }
          }
        },
        visible: false,
        toolbarOperations: this.toolOptions.toolbarOptions.operations,
        options: this.toolOptions
      }
    },
    created () {
      // 启用了顶部工具栏
      if (!this.toolbarOptions.suppressToolbar) {
        let defaultToolbarOperations = {
          refresh: {
            title: '刷新' + this.toolbarOptions.title,
            icon: 'fa fa-refresh',
            permission: ''
          },
          filter: {
            title: '选择列',
            icon: 'fa fa-sliders',
            permission: ''
          },
          printDetails: {
            title: '批量打印' + this.toolOptions.context.name,
            icon: 'fa fa-object-group',
            permission: 'printDetails',
            sort: -200
          },
          print: {
            title: '打印' + this.toolbarOptions.title,
            icon: 'fa fa-print',
            permission: 'print',
            sort: -300
          },
          export: {
            title: '导出' + this.toolbarOptions.title,
            icon: 'fa fa-file-excel-o',
            permission: 'export',
            sort: -500
          },
          sql: {
            title: '导出SQL脚本',
            icon: 'fa fa-file-code-o',
            permission: 'sql',
            sort: -510
          },
          remove: {
            title: '批量删除' + this.toolOptions.context.name,
            icon: 'fa fa-trash',
            permission: 'remove',
            sort: 800
          },
          add: {
            title: '增加' + this.toolOptions.context.name,
            icon: 'fa fa-plus',
            permission: 'add',
            sort: 900
          }
        }
        let defaultToolbarOperationsTwo = {
          refresh: {
            id: 'refresh',
            type: 'primary',
            title: '刷新' + this.toolbarOptions.title,
            icon: 'fa fa-refresh',
            permission: ''
          },
          filter: {
            id: 'filter',
            type: 'primary',
            title: '选择列',
            icon: 'fa fa-sliders',
            permission: ''
          }
        }
        this.toolbarOperations = []
        var arr = this.toolOptions.toolbarOptions.operations
        arr = arr.filter((element, index) => {
          return element.id !== 'refresh' && element.id !== 'filter'
        })
        this.toolbarOperations.push(defaultToolbarOperationsTwo['refresh'])
        this.toolbarOperations.push(defaultToolbarOperationsTwo['filter'])
        arr = this._sort(arr, defaultToolbarOperations)
        arr.forEach(operation => {
          let defaultToolbarOperation = defaultToolbarOperations[operation.id] || {}
          operation.type = 'primary'
          this.toolbarOperations.push(this.$lodash.merge({}, defaultToolbarOperation, operation))
          // this.options.toolbarOptions.operations(this.$lodash.merge({}, defaultToolbarOperation, operation))
        })
      }
      this.gridOptions.columnDefs = [{
        hide: false,
        type: 'Checkbox'
      }, {
        type: ['IndexRender'],
        width: 22
      }, {
        headerName: '列名',
        field: 'headerName',
        tooltipField: 'headerName',
        cellRenderer: 'agGroupCellRenderer',
        suppressSizeToFit: false,
        minWidth: 100,
        width: 100
      }]
    },
    methods: {
      _sort (arr, defaultToolbarOperations) { // 按照defaultToolbarOperations sort排序，大值放后面
        let len = arr.length - 1
        for (let i = 0; i < len; i++) {
          for (let j = 0; j < len - i; j++) {
            let defaultToolbarOperation = defaultToolbarOperations[arr[j].id] || {}
            let defaultToolbarOperationTwo = defaultToolbarOperations[arr[j + 1].id] || {}
            if (defaultToolbarOperation.sort > defaultToolbarOperationTwo.sort) {
              let temp = arr[j]
              arr[j] = arr[j + 1]
              arr[j + 1] = temp
            }
          }
        }
        return arr
      },
      _hasPermission (operation) {
        if (operation.permission) {
          if (this.toolOptions.context.getPermissions) {
            return this.toolOptions.context.getPermissions.call(this, this.toolOptions, operation)[operation.permission]
          } else {
            return false
          }
        }
        return true
      },
      _isDisabled (operation) {
        if (operation.isDisabled) {
          return operation.isDisabled.call(this, this.toolOptions, null)
        }
        return false
      },
      onToolbarButtonClick (operation, event) {
        if (operation.onClick) {
          operation.onClick.call(this, operation, this.toolOptions.api.getSelectedRows(), () => {
            this._doToolbarButtonClick(operation)
          })
          return
        }
        this._doToolbarButtonClick(operation, event)
      },
      _doToolbarButtonClick (operation, event) {
        if (operation.id === 'filter' || operation.id === 'export') {
          this.$refs.popover.$refs.reference = event.target // 这行不加的话，打开弹框后，点击弹框外的地方，弹框不会关闭
          this.$refs.popover.referenceElm = event.target // 这行不加的话，打开弹框的尖角位置没有指到点击的按钮上
          this.$refs.popover.doShow()

          operation.id === 'filter' ? this.openPopOverFilter(operation.id) : this.openPopOverExport(operation.id)
          return
        }

        this.$emit('operation', operation)
      },
      setTableTitle (title) {
        this.$forceUpdate()
        this.$set(this.toolOptions.toolbarOptions, 'title', title)
      },
      openPopOverFilter (operation) {
        // console.log('openPopovoer', this.toolOptions.api, this.toolOptions.columnApi.getAllColumns(), this.toolOptions.columnApi.getAllDisplayedColumns())
        this.currOperation = operation
        let columnData = this.toolOptions.api.gridOptionsWrapper.gridOptions.columnDefs.filter(item => !item.type | (item.type && item.type.indexOf('Checkbox') < 0 && item.type.indexOf('IndexRender') < 0 && item.type.indexOf('OperationRender') < 0))
        for (let i = 0; i < columnData.length; i++) {
          columnData[i].hide = columnData[i].hide ? columnData[i].hide : false
          columnData[i].suppressExport = columnData[i].suppressExport ? columnData[i].suppressExport : false
          if (columnData[i].children) {
            columnData[i].field = columnData[i].headerName
            columnData[i].children.forEach((item, index, list) => {
              item.hide = item.hide ? item.hide : false
            })
          }
        }
        this.$nextTick(() => {
          this.$refs.grid.setData(columnData)
          let vm = this
          let timeout = setTimeout(function () {
            vm.gridOptions.api.forEachNode(function select (node, idx) {
              node.setSelected(!node.data.hide)
            })
            clearTimeout(timeout)
          }, 200)
        })
      },
      openPopOverExport (operation) {
        this.currOperation = operation
        let columnData = []
        this.toolOptions.api.gridOptionsWrapper.gridOptions.columnApi.getAllGridColumns().map((item, idex) => {
          if (!item.colDef.type | (item.colDef.type && item.colDef.type.indexOf('Checkbox') < 0 && item.colDef.type.indexOf('IndexRender') < 0 && item.colDef.type.indexOf('OperationRender') < 0)) {
            item.colDef.suppressExport = item.colDef.suppressExport ? item.colDef.suppressExport : false
            columnData.push(item.colDef)
          }
        })
        this.$nextTick(() => {
          this.$refs.grid.setData(columnData)
          let vm = this
          let timeout = setTimeout(function () {
            vm.gridOptions.api.forEachNode(function select (node, idx) {
              node.setSelected(!node.data.suppressExport)
            })
            clearTimeout(timeout)
          }, 200)
        })
      },
      changeColumn () {
        let vm = this
        if (this.currOperation === 'filter') {
          let allColumns = this.toolOptions.api.gridOptionsWrapper.gridOptions.columnDefs.concat()
          this.gridOptions.api.forEachNode(function select (node, idx) {
            allColumns.forEach((item, index, list) => {
              if (node.data.field === item.field) {
                item.hide = !node.selected
                return
              }
              if (item.children) {
                item.children.forEach((child, num, array) => {
                  if (node.data.field === child.field) {
                    child.hide = !node.selected
                    // return
                  }
                })
              }
            })
          })
          this.$parent.$refs.agGrid.gridOptions.api.setColumnDefs(allColumns)
        }
        if (this.currOperation === 'export') {
          this.gridOptions.api.forEachNode(function select (node, idx) {
            vm.toolOptions.api.gridOptionsWrapper.gridOptions.columnApi.getAllGridColumns().map((item, idex) => {
              if (node.data.headerName === item.colDef.headerName) {
                item.colDef.suppressExport = !node.selected
              }
            })
          })
          this.$emit('export', {}, {serverSide: false})
        }
        this.visible = false
      }
    }
  }
</script>
