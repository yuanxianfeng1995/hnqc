import Vue from 'vue'
import {
  AddHeaderComponenetFramework,
  CheckboxHeaderComponenetFramework,
  DictEditorFramework,
  DictFilterFramework,
  DictFloatingFilterComponentFramework,
  DictRendererFramework,
  FieldEditorFramework,
  LikeFilterFramework,
  LikeFloatingFilterComponentFramework,
  IndexRendererFramework,
  NumberRendererFramework,
  NumberEditorFramework,
  OperationRendererFramework,
  TextEditorFramework,
  TimestampEditorFramework,
  TimestampFilterFramework,
  TimestampFloatingFilterComponentFramework,
  TimestampRendererFramework,
  TimestampLinkRendererFramework,
  ViewRendererFramework,
  DictViewFramework,
  TimestampViewFramework,
  ImgRenderFrameWork
} from '../../jw-grid/framework'


export default {
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
      checkboxSelection: true,
      headerComponentFramework: CheckboxHeaderComponenetFramework,
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
        return {v: Vue.store.getters._getDictItemText(exportFormatterParams.colDef.cellRendererParams.dictCode || exportFormatterParams.colDef.cellRendererParams.dict, exportFormatterParams.value) || ''}
      }
    },
    'DictView': {
      cellStyle: {'text-align': 'center'},
      cellRendererFramework: DictViewFramework,
      exportFormatter (exportFormatterParams) {
        return {v: Vue.store.getters._getDictItemText(exportFormatterParams.colDef.cellRendererParams.dictCode || exportFormatterParams.colDef.cellRendererParams.dict, exportFormatterParams.value) || ''}
      }
    },
    'FieldEditor': {
      cellStyle: {'text-align': 'center'},
      cellRendererFramework: FieldEditorFramework
    },
    'NumberRender': {
      cellStyle: {'text-align': 'right'},
      cellRendererFramework: NumberRendererFramework,
      exportFormatter (exportFormatterParams) {
        let cellRendererParams = exportFormatterParams.colDef.cellRendererParams || {}
        return {v: this.formatNumber(exportFormatterParams.value, cellRendererParams.precision, cellRendererParams.separator, cellRendererParams.point) || ''}
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
        return {v: exportFormatterParams.rowIndex + 1, t: 'n'}
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
    'TimestampEditorFramework': {
      cellRendererFramework: TimestampEditorFramework
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
      exportCellWidth (exportCellWidthParams) {
        let params = this.$lodash.merge({colDef: {cellRendererParams: {options: {format: 'YYYY-MM-DD HH:mm:ss'}}}}, exportCellWidthParams)
        return {wpx: params.colDef.cellRendererParams.options.format === 'YYYY-MM-DD HH:mm:ss' ? 160 : 90}
      },
      exportFormatter (exportFormatterParams) {
        let params = this.$lodash.merge({colDef: {cellRendererParams: {options: {format: 'YYYY-MM-DD HH:mm:ss'}}}}, exportFormatterParams)
        let timestamp = parseInt(params.value)
        if (!timestamp) {
          return {v: params.value || ''}
        } else {
          return {v: this.$moment(params.value).format(params.colDef.cellRendererParams.options.format)}
        }
      },
      width: 140 // 有filter的为200，没有的为140
    },
    'TimestampLinkRenderer': {
      cellStyle: {'text-align': 'center'},
      cellRendererFramework: TimestampLinkRendererFramework,
      exportCellWidth (exportCellWidthParams) {
        let params = this.$lodash.merge({colDef: {cellRendererParams: {options: {format: 'YYYY-MM-DD HH:mm:ss'}}}}, exportCellWidthParams)
        return {wpx: params.colDef.cellRendererParams.options.format === 'YYYY-MM-DD HH:mm:ss' ? 160 : 90}
      },
      exportFormatter (exportFormatterParams) {
        let params = this.$lodash.merge({colDef: {cellRendererParams: {options: {format: 'YYYY-MM-DD HH:mm:ss'}}}}, exportFormatterParams)
        let timestamp = parseInt(params.value)
        if (!timestamp) {
          return {v: params.value || ''}
        } else {
          return {v: this.$moment(params.value).format(params.colDef.cellRendererParams.options.format)}
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
    'GroupRender': {
      cellRenderer: 'agGroupCellRenderer'
    },
    'ImgRender': {
      cellRendererFramework: ImgRenderFrameWork
    },
    'TimestampView': {
      cellStyle: {'text-align': 'center'},
      cellRendererFramework: TimestampViewFramework,
      exportCellWidth (exportCellWidthParams) {
        let params = this.$lodash.merge({colDef: {cellRendererParams: {options: {format: 'YYYY-MM-DD HH:mm:ss'}}}}, exportCellWidthParams)
        return {wpx: params.colDef.cellRendererParams.options.format === 'YYYY-MM-DD HH:mm:ss' ? 160 : 90}
      },
      exportFormatter (exportFormatterParams) {
        let params = this.$lodash.merge({colDef: {cellRendererParams: {options: {format: 'YYYY-MM-DD HH:mm:ss'}}}}, exportFormatterParams)
        let timestamp = parseInt(params.value)
        if (!timestamp) {
          return {v: params.value || ''}
        } else {
          return {v: this.$moment(params.value).format(params.colDef.cellRendererParams.options.format)}
        }
      },
      width: 140 // 有filter的为200，没有的为140
    }
  }
}

