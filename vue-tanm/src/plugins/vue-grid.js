/**
 * Install plugin.
 */
import Vue from 'vue'
import {
  AddHeaderComponenetFramework,
  DictEditorFramework,
  DictFilterFramework,
  DictFloatingFilterComponentFramework,
  DictRendererFramework,
  LikeFilterFramework,
  LikeFloatingFilterComponentFramework,
  IndexRendererFramework,
  NumberRendererFramework,
  NumberEditorFramework,
  OperationRendererFramework,
  TextEditorFramework,
  TimestampFilterFramework,
  TimestampFloatingFilterComponentFramework,
  TimestampRendererFramework,
  ViewRendererFramework,
  DictViewFramework,
  TimestampViewFramework
} from '../components/ag-grid'

var VueGrid = {
  defaultOptions: {
    debug: false,
    context: {
      name: '',
      url: null,
      getDetailComponent: null,
      getFeatureComponent: null,
      getPermissions: null,
      columnDefsGenerator: null,
      rowDataConvertor: null,
      params: {
        orderBy: null,
        totalCount: null
      }
    },
    animateRows: true,
    enableFilter: true,
    floatingFilter: true,
    enableServerSideFilter: true,
    enableServerSideSorting: true,
    suppressContextMenu: true,
    suppressCellSelection: true,
    enableColResize: true,
    headerHeight: 30, // default is 25px
    floatingFiltersHeight: 38, // default is 20px
    rowHeight: 30, // default is 25px
    rowSelection: 'single', // single, multiple
    rowModelType: 'infinite',
    rowData: null,
    pagination: true,
    paginationAutoPageSize: true, // 是否启用自动每页条数
    paginationPageSize: 10, // 不启用自动每页条数时使用该值作为初始每页条数
    cacheOverflowSize: 2,
    infiniteInitialRowCount: 1,
    cacheBlockSize: 20, // 启动自动每页条数时，一次向后台请求的条目数（即发给后台的每页条数）
    maxBlocksInCache: 2,
    maxConcurrentDatasourceRequests: 1,
    columnTypes: {
      'Null': {},
      'AddHeader': {
        headerComponentFramework: AddHeaderComponenetFramework
      },
      'Checkbox': {
        headerName: '',
        pinned: 'left',
        suppressResize: true,
        suppressExport: true,
        hide: true,
        checkboxSelection: true,
        cellStyle: {'text-align': 'center'},
        width: 24
      },
      'DictEditor': {
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: DictEditorFramework
      },
      'DictFilter': {
        suppressFilter: false,
        filterFramework: DictFilterFramework,
        floatingFilterComponentFramework: DictFloatingFilterComponentFramework
      },
      'DictRender': {
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: DictRendererFramework,
        exportFormatter (exportFormatterParams) {
          return Vue.prototype.getDictValue(exportFormatterParams.value, exportFormatterParams.colDef.cellRendererParams.dict)
        }
      },
      'NumberRender': {
        cellStyle: {'text-align': 'right'},
        cellRendererFramework: NumberRendererFramework,
        exportFormatter (exportFormatterParams) {
          return Vue.prototype.formatNumber(exportFormatterParams.value, exportFormatterParams.colDef.cellRendererParams.precision, exportFormatterParams.colDef.cellRendererParams.separator, exportFormatterParams.colDef.cellRendererParams.point)
        }
      },
      'NumberEditor': {
        cellRendererFramework: NumberEditorFramework
      },
      'IndexRender': {
        headerName: '#',
        pinned: 'left',
        cellStyle: {'text-align': 'right'},
        cellRendererFramework: IndexRendererFramework,
        exportFormatter (exportFormatterParams) {
          return exportFormatterParams.rowIndex + 1
        },
        width: 38
      },
      'LikeFilter': {
        suppressFilter: false,
        filterFramework: LikeFilterFramework,
        floatingFilterComponentFramework: LikeFloatingFilterComponentFramework
      },
      'OperationRender': {
        headerName: '操作',
        pinned: 'right',
        suppressResize: true,
        suppressExport: true,
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: OperationRendererFramework
      },
      'TextEditor': {
        cellRendererFramework: TextEditorFramework
      },
      'TimestampFilter': {
        suppressFilter: false,
        filterFramework: TimestampFilterFramework,
        floatingFilterComponentFramework: TimestampFloatingFilterComponentFramework,
        width: 200 - 7 // 有filter的为200，没有的为140
      },
      'TimestampRender': {
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: TimestampRendererFramework,
        exportFormatter (exportFormatterParams) {
          let params = Vue.lodash.merge({colDef: {cellRendererParams: {options: {format: 'YYYY-MM-DD HH:mm:ss'}}}}, exportFormatterParams)
          let timestamp = parseInt(params.value)
          if (!timestamp) {
            return ''
          } else {
            // return Vue.moment(timestamp).format(params.colDef.cellRendererParams.options.format)
            return {
              v: params.value,
              t: 'd',
              s: {
                fill: {
                  fgColor: {
                    rgb: '33000000'
                  }
                },
                alignment: {
                  horizontal: 'center'
                }
              }
            }
          }
        },
        width: 140 // 有filter的为200，没有的为140
      },
      'ViewRender': {
        cellRendererFramework: ViewRendererFramework
      },
      'ViewGroupRender': {
        cellRenderer: 'agGroupCellRenderer',
        cellRendererParams: {
          suppressCount: true,
          innerRendererFramework: ViewRendererFramework
        }
      },
      'DictView': {
        cellRendererFramework: DictViewFramework
      },
      'TimestampView': {
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: TimestampViewFramework,
        exportFormatter (exportFormatterParams) {
          let params = Vue.lodash.merge({colDef: {cellRendererParams: {options: {format: 'YYYY-MM-DD HH:mm:ss'}}}}, exportFormatterParams)
          let timestamp = parseInt(params.value)
          if (!timestamp) {
            return ''
          } else {
            return {
              v: params.value,
              t: 'd',
              s: {
                fill: {
                  fgColor: {
                    rgb: '33000000'
                  }
                },
                alignment: {
                  horizontal: 'center'
                }
              }
            }
          }
        },
        width: 140 // 有filter的为200，没有的为140
      }
    },
    defaultColDef: {
      unSortIcon: true,
      suppressSizeToFit: true,
      suppressSorting: true,
      suppressMenu: true,
      suppressFilter: true,
      suppressExport: false, // 导出时，不导出该列。自己扩展的属性，不是ag-grid的。
      exportFormatter (params) {
        return params.value || ''
      },
      minWidth: 33
    },
    columnDefs: [],
    getRowNodeId (item) {
      return item.id
    },
    icons: {
      menu: '<i class="fa fa-bars"/>',
      filter: '<i class="fa fa-filter"/>',
      columns: '<i class="fa fa-handshake-o"/>',
      sortUnSort: '<i class="fa fa-arrows-v"/>',
      sortAscending: '<i class="fa fa-long-arrow-down"/>',
      sortDescending: '<i class="fa fa-long-arrow-up"/>',
      groupExpanded: '<i class="fa fa-minus-square-o"/>',
      groupContracted: '<i class="fa fa-plus-square-o"/>',
      columnMovePin: '<i class="fa fa-hand-grab-o"/>',
      columnMoveAdd: '<i class="fa fa-plus-square-o"/>',
      columnMoveHide: '<i class="fa fa-remove"/>',
      columnMoveMove: '<i class="fa fa-arrows"/>',
      columnMoveLeft: '<i class="fa fa-arrow-left"/>',
      columnMoveRight: '<i class="fa fa-arrow-right"/>',
      columnMoveGroup: '<i class="fa fa-group"/>',
      rowGroupPanel: '<i class="fa fa-bank"/>',
      pivotPanel: '<i class="fa fa-magic"/>',
      valuePanel: '<i class="fa fa-magnet"/>',
      menuPin: 'P', // just showing letters, no graphic
      menuValue: 'V',
      menuAddRowGroup: 'A',
      menuRemoveRowGroup: 'R',
      clipboardCopy: '>>',
      clipboardPaste: '>>',
      checkboxChecked: '<i class="fa fa-check-square-o"/>',
      checkboxUnchecked: '<i class="fa fa-square-o"/>',
      checkboxIndeterminate: '<i class="fa fa-minus-square-o"/>'
    },
    localeTextFunc (key, defaultValue) {
      let localeTextMap = {
        // 公共
        loadingOoo: '加载中……',
        noRowsToShow: '无记录',

        // 过滤相关
        filterOoo: '过滤…',
        equals: '=',
        notEqual: '!=',
        startsWith: '^(起始)',
        endsWith: '$(结束)',
        contains: '*(包含)',
        notContains: '!(不包含)',
        clearFilter: '清除',
        applyFilter: '确定',

        // 翻页相关
        page: '第',
        to: '-',
        of: '共',
        first: '第一页',
        previous: '上一页',
        next: '下一页',
        last: '最末页',
        more: '未知'
      }

      let localeText = localeTextMap[key]
      if (localeText) {
        return localeText
      } else {
        console && console.warn(key + ': \'' + defaultValue + '\',')
        return defaultValue
      }
    },
    datasource: {
      getRows (gridParams) {
        // console && console.warn('datasource getRows', this, 'gridParams', gridParams)
        this.gridOptions.context.page = this.gridOptions.buildPage(gridParams)
        this.gridOptions.context.filters = this.gridOptions.buildFilter(gridParams)
        this.gridOptions.getRows4Infinite(gridParams, this.gridOptions.context.page, this.gridOptions.context.filters)
      }
    },
    buildPage (gridParams) {
      let page = {
        pageSize: gridParams.endRow ? (gridParams.endRow - gridParams.startRow) : null,
        pageNo: gridParams.endRow ? (gridParams.startRow / (gridParams.endRow - gridParams.startRow)) : null
      }
      if (gridParams.sortModel && gridParams.sortModel.length > 0) {
        page.orderBy = ''
        gridParams.sortModel.forEach((order, idx, orders) => {
          let colDef = this.columnApi.getColumn(order.colId).colDef
          page.orderBy += page.orderBy + (colDef.sortColId || order.colId) + ' ' + order.sort + ', '
        })
        page.orderBy = page.orderBy.substr(0, page.orderBy.length - 2)
      }

      return page
    },
    buildFilter (gridParams) {
      let filters = {}
      for (let key in gridParams.filterModel) {
        gridParams.context.params.totalCount = null // TODO 这里需要优化，当只有页码变更时，这会导致设置成-1，从而导致后台查询总数。

        let where = gridParams.filterModel[key]
        if ((where.type === 'in' || where.type === 'notIn') && !(where.filter && where.filter.length > 0)) {
          continue
        }

        let filterValue = null
        if (where.filterType === 'String' && !(where.type === 'in' || where.type === 'notIn')) {
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

        if (where.filterType === 'String') {
          if (where.type === 'like') {
            filters[key + '_like'] = filterValue
          } else if (where.type === 'leftLike') {
            filters[key + '_leftLike'] = filterValue
          } else if (where.type === 'rightLike') {
            filters[key + '_rightLike'] = filterValue
          } else if (where.type === 'in') {
            if (filterValue.length === 1) {
              filters[key] = filterValue[0]
            } else {
              filters[key + '_in'] = '\'' + filterValue.join('\',\'') + '\''
            }
          } else {
            filters[key] = filterValue
          }
        } else if (where.filterType === 'Integer') {
          if (where.type === 'in') {
            if (filterValue.length === 1) {
              filters[key] = filterValue[0]
            } else {
              filters[key + '_in'] = filterValue.join(',')
            }
          } else {
            filters[key] = filterValue
          }
        } else if (where.filterType === 'Timestamp') {
          if (where.type === 'between') {
            filters[key + '_begin'] = filterValue[0]
            filters[key + '_end'] = filterValue[1]
          } else if (where.type === 'begin') {
            filters[key + '_begin'] = filterValue
          } else if (where.type === 'end') {
            filters[key + '_end'] = filterValue
          } else {
            filters[key] = filterValue
          }
        } else {
          filters[key] = where.filter
        }
      }

      return filters
    },
    getRows4Infinite (gridParams, page, filters) {
      let gridOptions = this
      if (gridParams.context.url) {
        gridOptions.api.showLoadingOverlay()
        Vue.http.get(gridParams.context.url, {params: Object.assign({}, gridParams.context.params, page, filters)}).then((response) => {
          gridOptions.api.hideOverlay()
          if (response.body.success) {
            gridParams.context.params.totalCount = response.body.data.totalCount
            gridParams.successCallback(response.body.data.items, response.body.data.totalCount)
          } else {
            // gridParams.failCallback()
            gridParams.context.params.totalCount = null
            gridParams.successCallback([], 0)
          }
          if (gridParams.context.params.totalCount === 0) {
            gridOptions.api.showNoRowsOverlay()
          }
        }, (response) => {
          // gridParams.failCallback()
          gridParams.context.params.totalCount = null
          gridParams.successCallback([], 0)
          gridOptions.api.showNoRowsOverlay()
        })
      } else {
        gridParams.context.params.totalCount = null
        gridParams.successCallback([], 0)
        gridOptions.api.showNoRowsOverlay()
        Vue.prototype.$alert('请通过gridOptions.context.url配置项设置加载数据的URL地址！', '错误', {
          confirmButtonText: '关闭',
          type: 'error'
        })
      }
    },
    getRows4Normal () {
      // console.log('getRows4Normal', typeof this.api)
      let gridOptions = this
      if (gridOptions.context.url) {
        gridOptions.context.params.totalCount = null
        if (gridOptions.context.params.tree) {
          delete gridOptions.context.params.totalCount
        }
        gridOptions.api.showLoadingOverlay()
        Vue.http.get(gridOptions.context.url, {params: Object.assign({}, gridOptions.context.params)}).then((response) => {
          gridOptions.api.hideOverlay()
          if (response.body.success) {
            let rowData = response.body.data.items
            if (gridOptions.context.rowDataConvertor) {
              rowData = gridOptions.context.rowDataConvertor.call(gridOptions, rowData)
            }
            if (gridOptions.context.columnDefsGenerator) {
              gridOptions.columnDefs = gridOptions.context.columnDefsGenerator.call(gridOptions, rowData, response.body.data.items)
              gridOptions.api.setColumnDefs(gridOptions.columnDefs)
            }
            gridOptions.context.params.totalCount = response.body.data.totalCount
            gridOptions.api.setRowData(rowData)
            gridOptions.api.sizeColumnsToFit()
          } else {
            gridOptions.context.params.totalCount = null
            gridOptions.api.setRowData([])
          }
        }, (response) => {
          gridOptions.api.hideOverlay()
          gridOptions.context.params.totalCount = null
          gridOptions.api.setRowData([])
        })
      }
    },
    setData (data) {
      let gridOptions = this
      if (gridOptions.rowModelType === 'clientSide') {
        if (gridOptions.api) {
          gridOptions.api.setRowData(data || [])
          gridOptions.api.sizeColumnsToFit()
        } else {
          console.warn('ag-grid还未初始化完成')
          gridOptions.rowData = data || []
        }
      }
    },
    reload (params) {
      let gridOptions = this
      gridOptions.context.params.totalCount = null
      Vue.lodash.merge(gridOptions.context.params, params || {})
      if (gridOptions.rowModelType === 'clientSide') {
        gridOptions.getRows4Normal()
      } else {
        if (gridOptions.api) {
          gridOptions.api.setDatasource(gridOptions.datasource)
        } else {
          console.warn('ag-grid还未初始化完成')
        }
      }
    },
    export (params, config) {
      let gridOptions = this
      let gridParams = {
        startRow: null,
        endRow: null,
        filterModel: gridOptions.api.getFilterModel(),
        sortModel: gridOptions.api.getSortModel(),
        context: gridOptions.context
      }
      let page = gridOptions.buildPage(gridParams)
      let filters = gridOptions.buildFilter(gridParams)

      if (!config.columnDefs) {
        config.columnDefs = gridOptions.columnApi.getAllGridColumns().map((item, idex) => item.getColDef()).filter((item, idex) => !item.suppressExport)
      }

      Vue.jw.exportToXlsx(gridOptions.context.url, {
        params: Object.assign({}, gridOptions.context.params, page, filters, params)
      }, Vue.lodash.merge({}, {
        fileName: gridOptions.context.name + Vue.moment().format('YYYYMMDDHHmmss'),
        sheet: {
          worksheet (data, cfg, sheet, idx) {
            let colDefs = cfg.columnDefs.map((columnDef, colIdx) => { // 设置列宽，数组
              // console.log('column ', columnDef.headerName, columnDef)
              return {wch: 19} // {wpx: columnDef.width || 40}
            })

            let headerCells = cfg.columnDefs
              .map((columnDef, colIdx) => { // 列头的单元格位置和内容，数组
                return {
                  position: String.fromCharCode(65 + colIdx) + 1,
                  value: columnDef.headerName || ''
                }
              })
              .reduce((result, item) => { // 数组转换成 worksheet 需要的结构
                return Object.assign({}, result, {[item.position]: typeof item.value === 'string' ? {v: item.value} : item.value})
              }, {})
            let dataCells = data.items
              .map((entity, rowIdx) => { // 表体的单元格位置和内容，二维数组
                return cfg.columnDefs.map((columnDef, colIdx) => {
                  let value = entity[columnDef.field]
                  if (typeof columnDef.exportFormatter === 'function') {
                    value = columnDef.exportFormatter({
                      rowIndex: rowIdx,
                      value: entity[columnDef.field],
                      data: entity,
                      colDef: columnDef,
                      api: gridOptions.api, // the grid API
                      columnApi: gridOptions.columnApi, // the grid Column API
                      context: gridOptions.context
                    })
                  }

                  return {
                    position: String.fromCharCode(65 + colIdx) + (1 + rowIdx + 1),
                    value: value || ''
                  }
                })
              })
              .reduce((result, item) => { // 对刚才的结果进行降维处理（二维数组变成一维数组）
                return result.concat(item)
              })
              .reduce((result, item) => { // 数组转换成 worksheet 需要的结构
                return Object.assign({}, result, {[item.position]: typeof item.value === 'string' ? {v: item.value} : item.value})
              }, {})

            let cells = Object.assign({}, headerCells, dataCells) // 合并 header 和 data
            let cellPos = Object.keys(cells) // 获取所有单元格的位置
            let ref = cellPos[0] + ':' + cellPos[cellPos.length - 1] // 计算出范围
            return Object.assign({}, cells, {'!ref': ref}, {'!cols': colDefs})
          }
        }
      }, config))
    }
  },
  buildOptions (gridOptions) {
    let options = Vue.lodash.merge({}, this.defaultOptions, gridOptions)
    options.datasource.gridOptions = options
    if (options.context.params.tree) {
      options.rowModelType = 'clientSide'
      options.pagination = false
      options.enableServerSideFilter = false
    }
    if (options.rowModelType === 'clientSide') {
      options.datasource = null
      options.rowData = []
    } else {
      options.getRows4Normal = null
      options.getNodeChildDetails = null // 服务端模式下不支持分组和树形结构，只有企业版的ag-grid才支持
    }
    options.onGridReady = (e) => {
      if (options.rowModelType === 'clientSide') {
        options.getRows4Normal()
      }
      if (gridOptions.onGridReady) {
        gridOptions.onGridReady(e)
      }
      e.api.sizeColumnsToFit()
    }

    // console.log('buildOptions', options)
    return options
  }
}

const plugin = function (Vue) {
  if (plugin.installed) {
    return
  }

  Vue.prototype.$grid = VueGrid
  Vue.grid = VueGrid
}

if (typeof window !== 'undefined' && window.Vue
) {
  window.Vue.use(plugin)
}

export default plugin
