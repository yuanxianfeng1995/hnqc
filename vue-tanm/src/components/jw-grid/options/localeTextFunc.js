
export default {
  name: 'localeTextFunc',
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
  }
}
