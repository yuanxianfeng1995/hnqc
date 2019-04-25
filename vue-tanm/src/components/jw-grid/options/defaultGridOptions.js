import icons from './icons'
import localeTextFunc from './localeTextFunc'
import defaultContext from './defaultContext'
import defaultColDef from './defaultColDef'
import columnTypes from './columnTypes'

export default {
  getRowNodeId: (item) => item.id,
  isRowSelectable: (rowNode) => true,
  context: defaultContext.context,
  columnTypes: columnTypes.columnTypes,
  defaultColDef: defaultColDef.defaultColDef,
  columnDefs: [],
  icons: icons,
  localeTextFunc: localeTextFunc.localeTextFunc,
  debug: false,
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
  rowMultiSelectWithClick: true,
  rowModelType: 'infinite',
  rowData: null,
  pagination: true,
  paginationAutoPageSize: true, // 是否启用自动每页条数
  paginationPageSize: 10, // 不启用自动每页条数时使用该值作为初始每页条数
  cacheOverflowSize: 2,
  infiniteInitialRowCount: 1,
  cacheBlockSize: 30, // 启动自动每页条数时，一次向后台请求的条目数（即发给后台的每页条数），一般每屏条数最多不会超过30条。
  maxBlocksInCache: 2, // maxBlocksInCache取值必须大于 ceil(cacheBlockSize/每页条数)，否则会导致不断的向后台请求加载数据。
  maxConcurrentDatasourceRequests: 1
}
