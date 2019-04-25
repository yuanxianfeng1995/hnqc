<style>
  .jw-grid-container {
    width: 100%;
    height: 100%;
  }

  .jw-grid-container .jw-grid {
    width: 100%;
    height: 100%;
    border: 1px solid #d1dbe5;
  }

  .jw-grid-body {
    width: 100%;
    height: 100%;
  }

  .jw-grid-body > div {
    border-radius: 4px;
  }

  .jw-grid-footer {
    height: 28px; /* 27 + 1 */
    line-height: 28px; /* 27 + 1 */
    border-top: 1px solid #d1dbe5;
    text-align: right;
  }

  .jw-grid-pagination {
    padding: 0px 5px;
    margin: -1px 0px;
  }

  .jw-grid-pagination .el-pagination__editor {
    width: 36px;
    height: 28px;
  }
</style>


<template>
  <div class="jw-grid-container">
    <div class="jw-grid" v-loading="loading">
      <jw-toolbar ref="toolGrid" :tool-options="options" @export="onExport" @operation="onToolbarOperation"
                  v-if="!options.suppressToolbar">
      </jw-toolbar>
      <ag-grid ref="agGrid" class="ag-fresh jw-grid-body"
               :style="{'margin-top': (options.suppressToolbar ? 0 : -33) + 'px', 'padding-top': (options.suppressToolbar ? 0 : 33) + 'px', 'margin-bottom': (!options.pagination ? 0 : -27) + 'px', 'padding-bottom': (!options.pagination ? 0 : 27) + 'px'}"
               :rowHeight="options.rowHeight" :grid-options="options">
      </ag-grid>

      <div class="jw-grid-footer" v-if="options.pagination">
        <el-pagination class="jw-grid-pagination"
                       layout="slot, prev, pager, next, jumper"
                       :page-size="pagination.pageSize"
                       :total="pagination.totalCount"
                       :current-page="pagination.currentPage"
                       @current-change="onCurrentChange">
          <span style="margin: 0 10px;">每页 {{pagination.pageSize}} 条，共 {{pagination.totalCount}} 条</span>
        </el-pagination>
      </div>
    </div>

    <jw-report-dialog ref="gridPrintDialog" :report-options="options.gridPrintOptions"
                      v-if="options.gridPrintOptions">
    </jw-report-dialog>
    <jw-report-dialog ref="detailPrintDialog" :report-options="options.detailPrintOptions"
                      v-if="options.detailPrintOptions">
    </jw-report-dialog>
    <jy-print-view ref="printViewDialog" v-if="options.printViewOptions"/>
    <slot></slot>
  </div>
</template>


<script>
  import defaultGridOptions from '../jw-grid/options/defaultGridOptions'
  import jwToolBar from '../jw-grid/ToolBar'

  import {PropertyKeys} from 'ag-grid-community/dist/lib/propertyKeys'

  PropertyKeys.ALL_PROPERTIES.push('gridPrintOptions')
  PropertyKeys.ALL_PROPERTIES.push('detailPrintOptions')
  PropertyKeys.ALL_PROPERTIES.push('printViewOptions')
  PropertyKeys.ALL_PROPERTIES.push('suppressToolbar')
  PropertyKeys.ALL_PROPERTIES.push('toolbarOptions')
  import {ColDefUtil} from 'ag-grid-community/dist/lib/components/colDefUtil'

  ColDefUtil.ALL_PROPERTIES.push('sortColId')
  ColDefUtil.ALL_PROPERTIES.push('filterColId')
  import {AgGridVue} from 'ag-grid-vue'
  import async from 'async'

  export default {
    name: 'jwGrid',
    props: {
      gridOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    components: {
      'jw-toolbar': jwToolBar,
      'ag-grid': AgGridVue
    },
    data () {
      return {
        loading: false,
        pagination: {
          pageSize: 10,
          currentPage: 1,
          totalPages: 0,
          totalCount: 0
        },
        datasource: {getRows: (getRowsParams) => this._getRows(getRowsParams)},
        options: {
          rowHeight: null,
          context: {jwGridVueComponent: this},
          gridPrintOptions: null,
          detailPrintOptions: null,
          printViewOptions: null,
          suppressToolbar: false,
          suppressPaginationPanel: true, // 使用自定义的分页组件
          onPaginationChanged: (paginationChangedParams) => this.onPaginationChanged(paginationChangedParams)
        }
      }
    },
    created () {
      this.$lodash.merge(this.options, defaultGridOptions, {toolbarOptions: {title: this.gridOptions.context.name + '列表'}}, this.gridOptions)
      if (this.gridOptions.context.featureComponent && this.gridOptions.context.featureComponent.mode === 'disabled') {
        this.options.suppressToolbar = true
        this.options = this.setDisabled(this.options)
      }
      let operationColumn = this.options.columnDefs[this.options.columnDefs.length - 1]
      if (operationColumn.type === 'OperationRender') {
        operationColumn.width = operationColumn.cellRendererParams.operations.length * 24 + 10
      }
      if (this.options.context.params.tree) {
        this.options.rowModelType = 'clientSide'
        this.options.pagination = false
        this.options.enableServerSideFilter = false
      }
      if (this.options.rowModelType === 'clientSide') {
        this.options.rowData = []
      } else {
        this.options.getNodeChildDetails = null // 服务端模式下不支持分组和树形结构，只有企业版的ag-grid才支持
      }
      this.options.onGridReady = (e) => this.onGridReady(e)
    },
    methods: {
      onGridReady (e) {
        // console.log('onGridReady', e)
        this.api = e.api
        this.gridOptions.api = e.api
        e.api.setFilterModel(e.columnApi.getAllColumns().reduce((filterModel, column) => {
          let columnDef = column.colDef
          if (columnDef.suppressFilter === false && columnDef.filterParams && (columnDef.filterParams.value || columnDef.filterParams.value === 0)) {
            filterModel[columnDef.field] = {filter: columnDef.filterParams.value, init: true}
          }

          return filterModel
        }, {}))

        this.reload()

        if (this.gridOptions.onGridReady) {
          this.gridOptions.onGridReady(e)
        }
      },
      setData (data) {
        if (this.options.rowModelType === 'clientSide') {
          if (this.options.api) {
            this.options.api.setRowData(data || [])
            this.options.api.sizeColumnsToFit()
          } else {
            console.warn('ag-grid还未初始化完成')
            this.options.rowData = data || []
          }
        }
      },
      reload (gridOptions) {
        this.options.context.params.totalCount = null
        this.$lodash.merge(this.options, this.gridOptions, gridOptions || {})

        if (!this.options.suppressToolbar && this.options.title) {
          this.$refs['toolGrid'].setTableTitle(this.options.title)
        }

        if (this.options.gridPrintOptions) {
          this.$lodash.merge(this.options.gridPrintOptions, {
            commands: [{id: 'print'}, {id: 'exportPdf'}],
            context: {
              featureComponent: this.options.context.featureComponent,
              getGridComponent () {
                return this
              }
            }
          }, this.options.gridPrintOptions.context || {})
        }
        if (this.options.detailPrintOptions) {
          this.$lodash.merge(this.options.detailPrintOptions, {
            commands: [{id: 'print'}, {id: 'exportPdf'}],
            context: {
              featureComponent: this.options.context.featureComponent,
              getGridComponent () {
                return this
              }
            }
          }, this.options.detailPrintOptions.context || {})
        }
        if (this.options.rowModelType === 'clientSide') {
          this._getRows4Normal()
        } else {
          if (this.options.api) {
            if (this.pagination.currentPage > 1) {
              this.options.api.refreshInfiniteCache()
            } else {
              this.options.api.setDatasource(this.datasource)
            }
          } else {
            console.warn('ag-grid还未初始化完成')
          }
        }
      },
      onToolbarOperation (operation) {
        let fnName = 'onGrid' + operation.id.substring(0, 1).toUpperCase() + operation.id.substring(1)
        let fn = this.options.context[fnName] || this[fnName]
        fn ? fn.call(this, operation) : this.$jw.error('不支持操作类型：' + operation.id)
      },
      onGridRefresh (operation) {
        this.options.api.deselectAll()
        this.reload()
      },
      onGridAdd (operation) {
        let fn = this.options.context.getDetailComponent
        if (fn) {
          let detailComponent = fn.call(this, this.options, operation)
          if (detailComponent) {
            detailComponent.open({
              operation: operation.id,
              title: operation.title,
              params: this.options.api.getSelectedRows()
            })
          }
        }
      },
      onGridPrint (operation, reportOptions) {
        this.$refs.gridPrintDialog.open({
          dialogOptions: {title: operation.title},
          reportOptions: this.$lodash.merge({queryString: Object.assign({}, this.options.context.params, this.options.context.page, this.options.context.filters)}, reportOptions || {})
        })
      },
      onGridPrintDetails (operation) {
        let rows = this.getSelectedRows()
        let reportlets = []
        async.map(rows, (row, callback) => {
          if (this.options.context.getDetailReportOptions) {
            this.options.context.getDetailReportOptions.call(this, operation, row).then(async reportOptions => {
              let reportlet = await this.$store.dispatch('loadReportlet', reportOptions)
              reportlets.push({
                reportlet: reportlet,
                id: row.id,
                queryString: {'url': decodeURIComponent(reportOptions.queryString.url)}
              })
              callback(null, reportlets)
            })
          }
        }, (err, result) => {
          if (err || reportlets.length === 0) {
            err && console.error(err)
            if (reportlets.length === 0) {
              this.$alert('请至少选择一条数据', '提示', {confirmButtonText: '关闭', type: 'error'})
            }
            return
          }
          this.options.printViewOptions = reportlets
          this.$nextTick(() => {
            this.$refs.printViewDialog.open({
              dialogOptions: {title: operation.title},
              printViewOptions: reportlets
            })
          })
        })
      },
      onGridSql (operation) {
        if (!this.options.context.featureComponent || !this.options.context.featureComponent.$refs.sql) {
          return
        }
        this.options.context.featureComponent.$refs.sql.open({
          operation: operation.id,
          title: operation.title,
          params: Object.assign({}, this.options.context.params, this.options.context.filters)
        })
      },
      onGridRemove (operation) {
        let rows = this.getSelectedRows()
        if (!rows || rows.length === 0) {
          this.$jw.error('请至少选择一条' + this.options.context.name + '！', operation.title)
          return
        }

        this.$jw.confirm('确定要删除所选的' + this.options.context.name + '吗?', operation.title).then(() => {
          this.$http.delete(this.options.context.url + '?id_in=' + rows.map(row => row.id).join(',')).then(response => {
            if (response.body.success) {
              this.reload()
            }
          })
        })
      },
      onDetailOperation (operation, entity) {
        let fnName = 'onDetail' + operation.id.substring(0, 1).toUpperCase() + operation.id.substring(1)
        let fn = this.options.context[fnName] || this[fnName] || this.options.context.onDetailDefault || this.onDetailDefault
        fn.call(this, operation, entity)
      },
      onDetailDefault (operation, entity) {
        let fn = this.options.context.getDetailComponent
        if (fn) {
          let detailComponent = fn.call(this, this.options, operation, entity)
          if (detailComponent) {
            detailComponent.open({operation: operation.id, title: operation.title, params: entity})
          }
        }
      },
      onDetailRemove (operation, entity) {
        this.$jw.confirm('确定要删除所选的' + this.options.context.name + '吗?', operation.title).then(() => {
          this.$http.delete(this.options.context.url + '/' + entity.id).then(response => {
            if (response.body.success) {
              this.reload()
            }
          })
        })
      },
      onDetailSql (operation, entity) {
        if (this.options.context.featureComponent.$refs.sql) {
          this.options.context.featureComponent.$refs.sql.open({
            operation: operation.id,
            title: operation.title,
            params: entity
          })
        }
      },
      onDetailPrint (operation, entity) {
        (this.options.context.getDetailReportOptions || this.getDetailReportOptions).call(this, operation, entity).then(reportOptions => {
          this.$refs.detailPrintDialog.open({
            dialogOptions: {title: operation.title},
            reportOptions: this.$lodash.merge({
              templateCode: this.options.detailPrintOptions.templateCode,
              templateVersion: null,
              params: {id: entity.id}
            }, reportOptions || {})
          })
        })
      },
      getDetailReportOptions (operation, entity) {
        return new Promise((resolve, reject) => resolve({})) // 默认的不加工reportOptions
      },
      onCurrentChange (currentPage) {
        this.options.api.paginationGoToPage(currentPage - 1)
      },
      onPaginationChanged (paginationChangedParams) {
        this.pagination.pageSize = paginationChangedParams.api.paginationGetPageSize()
        this.pagination.currentPage = paginationChangedParams.api.paginationGetCurrentPage() + 1 // 从0开始编号
        this.pagination.totalPages = paginationChangedParams.api.paginationGetTotalPages()
        this.pagination.totalCount = paginationChangedParams.api.paginationGetRowCount()
      },
      _getRows (getRowsParams) {
        this.options.context.page = this._buildPage(getRowsParams)
        this.options.context.filters = this._buildFilter(getRowsParams)
        this._getRows4Infinite(getRowsParams, this.options.context.page, this.options.context.filters)
      },
      _getRows4Infinite (getRowsParams, page, filters) {
        let gridOptions = this.options
        if (!getRowsParams.context.url) {
          getRowsParams.context.params.totalCount = null
          getRowsParams.successCallback([], 0)
          gridOptions.api.showNoRowsOverlay()
          console.warn('gridOptions.context.url为空，无需从服务器加载数据！')
          return
        }

        // gridOptions.api.showLoadingOverlay()
        this.$http.get(getRowsParams.context.url, {params: Object.assign({}, getRowsParams.context.params, page, filters)}).then((response) => {
          gridOptions.api.hideOverlay()
          if (response.body.success) {
            getRowsParams.context.params.totalCount = response.body.data.totalCount
            getRowsParams.successCallback(response.body.data.items, response.body.data.totalCount)
          } else {
            // getRowsParams.failCallback()
            getRowsParams.context.params.totalCount = null
            getRowsParams.successCallback([], 0)
          }
          if (getRowsParams.context.params.totalCount === 0) {
            gridOptions.api.showNoRowsOverlay()
          }
          this.options.api.sizeColumnsToFit()
        }, (response) => {
          // getRowsParams.failCallback()
          getRowsParams.context.params.totalCount = null
          getRowsParams.successCallback([], 0)
          gridOptions.api.showNoRowsOverlay()
          this.options.api.sizeColumnsToFit()
        })
      },
      _getRows4Normal () {
        if (!this.options.context.url) {
          return
        }
        this.options.context.params.totalCount = null
        if (this.options.context.params.tree) {
          delete this.options.context.params.totalCount
        }
        // this.options.api.showLoadingOverlay()
        this.$http.get(this.options.context.url, {params: Object.assign({}, this.options.context.params)}).then((response) => {
          this.options.api.hideOverlay()
          if (response.body.success) {
            let rowData = response.body.data.items || []
            if (this.options.context.rowDataConvertor) {
              rowData = this.options.context.rowDataConvertor.call(this, rowData)
            }
            if (this.options.context.columnDefsGenerator) {
              this.options.columnDefs = this.options.context.columnDefsGenerator.call(this.options, rowData, response.body.data.items)
              this.options.api.setColumnDefs(this.options.columnDefs)
            }
            this.options.context.params.totalCount = response.body.data.totalCount
            this.options.api.setRowData(rowData)
          } else {
            this.options.context.params.totalCount = null
            this.options.api.setRowData([])
          }
          this.options.api.sizeColumnsToFit()
        }, (response) => {
          this.options.api.hideOverlay()
          this.options.context.params.totalCount = null
          this.options.api.setRowData([])
          this.options.api.sizeColumnsToFit()
        })
      },
      onExport (params, config) {
        let gridOptions = this.options
        let gridParams = {
          startRow: null,
          endRow: null,
          filterModel: gridOptions.api.getFilterModel(),
          sortModel: gridOptions.api.getSortModel(),
          context: gridOptions.context
        }
        let page = this._buildPage(gridParams)
        let filters = this._buildFilter(gridParams)
        let cfg = this.$lodash.merge({
          fileName: gridOptions.context.name + this.$moment().format('YYYYMMDDHHmmss'),
          columnDefs: gridOptions.columnApi.getAllGridColumns().map((item, idex) => item.getColDef()).filter((item, idex) => !item.suppressExport)
        }, config || {})
        let dictCols = cfg.columnDefs.filter(columnDef => columnDef.cellRendererParams && (columnDef.cellRendererParams.dictCode || columnDef.cellRendererParams.dict))
        if (dictCols.length) {
          Promise.all(dictCols.map(columnDef => this.$store.dispatch('getDictItemMap', columnDef.cellRendererParams.dictCode || columnDef.cellRendererParams.dict))).then(results => {
            this._doExport(Object.assign({}, gridOptions.context.params, page, filters, params), cfg)
          })
        } else {
          this._doExport(Object.assign({}, gridOptions.context.params, page, filters, params), cfg)
        }
      },
      _doExport (params, config) {
        let vm = this
        this.$jw.exportToXlsx(this.options.context.url, {params: params}, this.$lodash.merge({}, {
          sheet: {
            worksheet (data, cfg, sheet, idx) {
              if (data.items.length === 0) {
                vm.$alert('无可导出数据', '提示', {confirmButtonText: '关闭', type: 'info'})
              }
              let worksheet = Object.assign({}, vm._buildHeadCells(cfg.columnDefs), vm._buildDataCells(cfg.columnDefs, data.items)) // 合并 head 和 data
              let cellPos = Object.keys(worksheet) // 获取所有单元格的位置
              worksheet['!ref'] = cellPos[0] + ':' + cellPos[cellPos.length - 1] // 计算出范围
              worksheet['!cols'] = vm._buildCols(cfg.columnDefs)
              return worksheet
            }
          }
        }, config))
      },
      _buildCols (columnDefs) {
        let vm = this
        let gridOptions = this.options
        return columnDefs.map((columnDef, colIdx) => { // 设置列宽，数组
          return columnDef.exportCellWidth.call(vm, {
            colIdx: colIdx,
            colDef: columnDef,
            api: gridOptions.api, // the grid API
            columnApi: gridOptions.columnApi, // the grid Column API
            context: gridOptions.context
          })
        })
      },
      _buildHeadCells (columnDefs) {
        let headCell = columnDefs
          .map((columnDef, colIdx) => { // 列头的单元格位置和内容，数组
            return {
              position: (colIdx >= 26 ? String.fromCharCode(65 + Math.floor(colIdx / 26) - 1) : '') + String.fromCharCode(65 + colIdx % 26) + 1,
              value: {
                v: columnDef.headerName || '',
                t: 's',
                s: {
                  fill: {bgColor: {indexed: 64}, fgColor: {rgb: 'D9D9D9'}},
                  font: {name: '宋体', bold: true},
                  alignment: {horizontal: 'center'},
                  border: {
                    top: {style: 'thin'}, left: {style: 'thin'}, bottom: {style: 'thin'}, right: {style: 'thin'}
                  }
                }
              }
            }
          })
        return this._transFormWorksheet(headCell)
      },
      _buildDataCells (columnDefs, rows) {
        let vm = this
        let gridOptions = this.options
        let celldata = rows
          .map((entity, rowIdx) => { // 表体的单元格位置和内容，二维数组
            return columnDefs.map((columnDef, colIdx) => {
              return {
                position: (colIdx >= 26 ? String.fromCharCode(65 + Math.floor(colIdx / 26) - 1) : '') + String.fromCharCode(65 + colIdx % 26) + (1 + rowIdx + 1),
                value: vm.$lodash.merge({
                  t: 's',
                  s: {
                    font: {name: '宋体'},
                    border: {
                      top: {style: 'thin'},
                      left: {style: 'thin'},
                      bottom: {style: 'thin'},
                      right: {style: 'thin'}
                    },
                    alignment: {horizontal: (columnDef.cellStyle ? columnDef.cellStyle['text-align'] : null) || 'left'}
                  }
                }, columnDef.exportFormatter.call(vm, {
                  rowIndex: rowIdx,
                  value: columnDef.valueGetter ? columnDef.valueGetter({data: entity}) : entity[columnDef.field],
                  data: entity,
                  colDef: columnDef,
                  api: gridOptions.api, // the grid API
                  columnApi: gridOptions.columnApi, // the grid Column API
                  context: gridOptions.context
                }))
              }
            })
          })
          .reduce((result, cell) => { // 对刚才的结果进行降维处理（二维数组变成一维数组）
            return result.concat(cell)
          })
        return this._transFormWorksheet(celldata)
      },
      _transFormWorksheet (data) {
        let cellMap = {}
        for (let i = 0; i < data.length; i++) { // 数组转换成 worksheet 需要的结构
          let cell = data[i]
          // cellMap = Object.assign({}, cellMap, {[cell.position]: cell.value})
          cellMap[cell.position] = cell.value
        }
        return cellMap
      },
      _buildPage (getRowsParams) {
        let page = {
          pageSize: getRowsParams.endRow ? (getRowsParams.endRow - getRowsParams.startRow) : null,
          pageNo: getRowsParams.endRow ? (getRowsParams.startRow / (getRowsParams.endRow - getRowsParams.startRow)) : null
        }
        if (getRowsParams.sortModel && getRowsParams.sortModel.length > 0) {
          page.orderBy = ''
          getRowsParams.sortModel.forEach((order, idx, orders) => {
            let colDef = this.options.columnApi.getColumn(order.colId).colDef
            page.orderBy += page.orderBy + (colDef.sortColId || order.colId) + ' ' + order.sort + ', '
          })
          page.orderBy = page.orderBy.substr(0, page.orderBy.length - 2)
        }

        return page
      },
      _buildFilter (getRowsParams) {
        // console.log('_buildFilter', getRowsParams)
        let filters = {}
        for (let key in getRowsParams.filterModel) {
          getRowsParams.context.params.totalCount = null // TODO 这里需要优化，当只有页码变更时，这会导致设置成-1，从而导致后台查询总数。

          let where = getRowsParams.filterModel[key]
          if ((where.type === 'in' || where.type === 'notIn' || where.type === 'containsAll' || where.type === 'containsAny') &&
            !(where.filter && where.filter.length > 0)) {
            continue
          }

          let filterValue = null
          if (where.filterType === 'String' && !(where.type === 'in' || where.type === 'notIn' || where.type === 'containsAll' || where.type === 'containsAny')) {
            filterValue = where.filter ? where.filter.trim() : null
            if (!filterValue) {
              continue
            }
          } else {
            filterValue = where.filter
            if (filterValue === undefined || filterValue === null) {
              continue
            }
          }

          let colDef = this.options.columnApi.getColumn(key).colDef
          let filterColId = colDef.filterColId || key
          if (where.filterType === 'String') {
            if (where.type === 'like' || where.type === 'leftLike' || where.type === 'rightLike') {
              filters[filterColId + '_' + where.type] = filterValue
            } else if (where.type === 'in') {
              if (filterValue.length === 1) {
                filters[filterColId] = filterValue[0]
              } else {
                filters[filterColId + '_' + where.type] = '\'' + filterValue.join('\',\'') + '\''
              }
            } else if (where.type === 'containsAll' || where.type === 'containsAny') {
              filters[filterColId + '_' + where.type] = '\'' + filterValue.join('\',\'') + '\''
            } else {
              filters[filterColId] = filterValue
            }
          } else if (where.filterType === 'Integer') {
            if (where.type === 'in') {
              if (filterValue.length === 1) {
                filters[filterColId] = filterValue[0]
              } else {
                filters[filterColId + '_' + where.type] = filterValue.join(',')
              }
            } else if (where.type === 'containsAll' || where.type === 'containsAny') {
              filters[filterColId + '_' + where.type] = filterValue.join(',')
            } else {
              filters[filterColId] = filterValue
            }
          } else if (where.filterType === 'Timestamp') {
            if (where.type === 'between') {
              filters[filterColId + '_begin'] = filterValue[0]
              filters[filterColId + '_end'] = filterValue[1]
            } else if (where.type === 'begin' || where.type === 'end') {
              filters[filterColId + '_' + where.type] = filterValue
            } else {
              filters[filterColId] = filterValue
            }
          } else {
            filters[filterColId] = where.filter
          }
        }

        return filters
      },
      setDisabled (options) {
        let columnTypes = options.columnTypes
        delete columnTypes.DictEditor
        delete columnTypes.AddHeader
        delete columnTypes.Checkbox
        delete columnTypes.DictFilter
        delete columnTypes.FieldEditor
        delete columnTypes.LikeFilter
        delete columnTypes.NumberEditor
        delete columnTypes.OperationRender
        delete columnTypes.TextEditor
        delete columnTypes.TimestampFilter
        return options
      },
      getSelectedRows () {
        return this.gridOptions.api.getSelectedRows()
      }
    }
  }
</script>
