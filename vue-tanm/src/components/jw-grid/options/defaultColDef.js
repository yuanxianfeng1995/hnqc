export default {
  defaultColDef: {
    hide: false,
    unSortIcon: true,
    suppressSizeToFit: true,
    suppressSorting: true,
    suppressMenu: true,
    suppressFilter: true,
    suppressExport: false, // 导出时，不导出该列。自己扩展的属性，不是ag-grid的。
    cellRendererParams: {},
    filterParams: {doesFilterPass: null},
    floatingFilterComponentParams: {suppressFilterButton: true},
    exportCellWidth (params) {
      return {wpx: params.colDef.width || 40} // {wch: 19}
    },
    exportFormatter (params) {
      return {v: typeof params.value === 'number' ? (params.value + '') : (params.value || '')}
    },
    minWidth: 33
  }
}
