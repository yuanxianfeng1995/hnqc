import Vue from 'vue'
import XLSX from 'xlsx-style'

var VueJw = {
  vm: null,
  error (message, title) {
    Vue.prototype.$alert(message, title || '错误', {
      confirmButtonText: '关闭',
      type: 'error'
    })
  },
  confirm (message, title) {
    return new Promise((resolve, reject) => {
      Vue.prototype.$confirm(message, title || '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(confirm => resolve(confirm)).catch(e => reject(e))
    })
  },
  validate (form, errorMsg) {
    return new Promise((resolve, reject) => {
      if (form) {
        form.validate(valid => {
          if (!valid) {
            if (errorMsg !== false) {
              Vue.prototype.$message.error(errorMsg || '表单数据校验未通过！')
            }
            reject(new Error(errorMsg || '表单数据校验未通过！'))
            return false
          }
          resolve()
          return true
        })
      } else {
        resolve()
      }
    })
  },
  byte2Str (size) {
    let unit = 'KB'
    size /= 1024
    if (size > 1000) {
      unit = 'MB'
      size /= 1024
    }
    if (size > 1000) {
      unit = 'GB'
      size /= 1024
    }
    return size + unit
  },
  dateEqual (date1, date2) {
    if ((date1 === null && date2 === null) || (date1 && date2 && date1.getTime() === date2.getTime())) {
      return true
    }
    return false
  },
  formatNumber (value, precision, separator, point) {
    if (value === undefined || value === null) {
      return null
    }
    // 判断是否为数字
    if (!isNaN(parseFloat(value)) && isFinite(value)) {
      // 把类似 .5, 5. 之类的数据转化成0.5, 5, 为数据精度处理做准,
      // 至于为什么不在判断中直接写 if (!isNaN(num = parseFloat(num)) && isFinite(num))
      // 是因为parseFloat有一个奇怪的精度问题, 比如 parseFloat(12312312.1234567119)的值变成了 12312312.123456713
      let num = Number(value)
      // 处理小数点位数
      num = num.toFixed(precision || 0).toString()
      // 分离数字的小数部分和整数部分
      let parts = num.split(point || '.')
      // 整数部分加[separator]分隔, 借用一个著名的正则表达式
      parts[0] = parts[0].toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1' + (separator || ','))

      return parts.join(point || '.')
    }
    return NaN
  },
  buildPickerOptionsShortcuts (shortcutIds) {
    var shortcuts = {
      today: {
        text: '今天',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().startOf('day').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      tomorrow: {
        text: '昨天',
        onClick (picker) {
          var tomorrow = this.$moment().subtract(1, 'days')
          picker.$emit('pick', [tomorrow.startOf('day').toDate(), tomorrow.endOf('day').toDate()])
        }
      },
      currentWeek: {
        text: '本周',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().startOf('week').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      lastWeek: {
        text: '上周',
        onClick (picker) {
          var lastWeek = this.$moment().subtract(1, 'weeks')
          picker.$emit('pick', [lastWeek.startOf('week').toDate(), lastWeek.endOf('week').toDate()
          ])
        }
      },
      currentMonth: {
        text: '本月',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().startOf('month').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      lastMonth: {
        text: '上月',
        onClick (picker) {
          var lastMonth = this.$moment().subtract(1, 'months')
          picker.$emit('pick', [lastMonth.startOf('month').toDate(), lastMonth.endOf('month').toDate()
          ])
        }
      },
      currentQuarter: {
        text: '本季',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().startOf('quarter').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      lastQuarter: {
        text: '上季',
        onClick (picker) {
          var lastQuarter = this.$moment().subtract(1, 'quarters')
          picker.$emit('pick', [lastQuarter.startOf('quarter').toDate(), lastQuarter.endOf('quarter').toDate()
          ])
        }
      },
      currentYear: {
        text: '今年',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().startOf('year').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      lastYear: {
        text: '去年',
        onClick (picker) {
          var lastYear = this.$moment().subtract(1, 'years')
          picker.$emit('pick', [lastYear.startOf('year').toDate(), lastYear.endOf('year').toDate()
          ])
        }
      },
      latestWeek: {
        text: '最近一周',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().subtract(6, 'days').startOf('day').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      latestMonth: {
        text: '最近一个月',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().subtract(1, 'months').startOf('day').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      latestQuarter: {
        text: '最近三个月',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().subtract(3, 'months').startOf('day').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      latestYear: {
        text: '最近一年',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().subtract(1, 'years').startOf('day').toDate(), this.$moment().endOf('day').toDate()])
        }
      }
    }

    var pickerOptionsShortcuts = []
    shortcutIds.forEach((shortcutId) => {
      var shortcut = shortcuts[shortcutId]
      if (shortcut) {
        shortcut.$moment = this.vm.moment
        pickerOptionsShortcuts.push(shortcut)
      }
    })

    return pickerOptionsShortcuts
  },
  readFromXlsx (file) {
    return new Promise((resolve, reject) => {
      let reader = new FileReader()
      reader.onload = (e) => resolve(XLSX.read(e.target.result, {type: 'binary'}))
      reader.readAsBinaryString(file)
    })
  },
  sheetToJson (sheet, headRowIdx) {
    if (headRowIdx) {
      let range = sheet['!range']
      let cols = []
      for (let colIdx = range.s.c; colIdx < range.e.c; colIdx++) {
        let colId = (colIdx >= 26 ? String.fromCharCode(65 + Math.floor(colIdx / 26) - 1) : '') + String.fromCharCode(65 + colIdx % 26)
        let cell = sheet[colId + headRowIdx]
        if (cell) {
          cols.push({id: colId, name: cell.w})
        }
      }

      let rows = []
      for (let rowIdx = headRowIdx + 1; rowIdx <= range.e.r; rowIdx++) {
        let row = {}
        for (let colIdx = 0; colIdx < cols.length; colIdx++) {
          let cell = sheet[cols[colIdx].id + rowIdx]
          if (cell) {
            row[cols[colIdx].name] = cell.w
          }
        }

        // 不是空对象才添加到数组中
        if (Object.keys(row).length) {
          rows.push(row)
        }
      }

      return rows
    }

    return XLSX.utils.sheet_to_json(sheet)
  },
  exportToXlsx (url, params, config) {
    let cfg = Vue.lodash.merge({
      serverSide: false,
      sheet: {
        name: 'sheet1',
        json (data, cfg, sheet, idx) {
          return data.items
        },
        worksheet (data, cfg, sheet, idx) {
          return XLSX.utils.json_to_sheet((typeof this.json === 'function') ? this.json(data, cfg, sheet, idx) : this.json)
        }
      },
      fileName: Vue.moment().format('YYYYMMDDHHmmss'),
      workbook (data, cfg) {
        let workbook = {SheetNames: [], Sheets: {}}
        let sheets = cfg.sheet.length === undefined ? [cfg.sheet] : cfg.sheet
        sheets.forEach((sheet, idx) => {
          workbook.Sheets[sheet.name] = (typeof sheet.worksheet === 'function') ? sheet.worksheet(data, cfg, sheet, idx) : sheet.worksheet
          workbook.SheetNames.push(sheet.name)
        })
        return workbook
      },
      blob (data, cfg) {
        let workbook = (typeof cfg.workbook === 'function') ? cfg.workbook(data, cfg) : cfg.workbook
        let binaryStr = XLSX.write(workbook, {bookType: 'xlsx', type: 'binary', bookSST: false})

        // 字符串数组转字节数组
        let len = binaryStr.length
        let buf = new ArrayBuffer(len)
        let view = new Uint8Array(buf)
        for (let i = 0; i < len; ++i) {
          view[i] = binaryStr.charCodeAt(i) & 0xFF
        }

        // 创建Blob对象
        return new Blob([buf], {type: 'application/octet-stream'})
      }
    }, config || {})

    if (cfg.serverSide) { // 远程服务器导出（文件在服务器上生成）
      this.download(url + '/export', params)
      return
    }

    // 本地客户端导出（文件在浏览器中生成）
    Vue.http.get(url, Vue.lodash.merge({}, params || {})).then((response) => {
      this.saveAs(cfg.fileName + '.xlsx', cfg.blob(response.body.data, cfg))
    })
  },
  download (url, params) {
    if (url.startsWith('http') || url.startsWith('blob:http')) {
      this.saveAs(params.name, url)
      return
    }

    Vue.http.get(url, Vue.lodash.merge({responseType: 'blob'}, params || {})).then((response) => {
      let fileName = response.headers.get('Content-Disposition')
      let attachmentName = decodeURIComponent(fileName.substring('attachment; filename="'.length, fileName.length - 1))
      this.saveAs(attachmentName, response.bodyBlob)
    })
  },
  saveAs (fileName, blob) {
    let downloadLink = document.getElementById('____downloadLink')
    if (!downloadLink) {
      downloadLink = document.createElement('a')
      document.body.appendChild(downloadLink)

      downloadLink.setAttribute('id', '____downloadLink')
      downloadLink.setAttribute('target', '_blank') // 在新窗口中下载
      downloadLink.setAttribute('href', '')
      downloadLink.setAttribute('download', '')
    }

    if (typeof blob === 'string' && (blob.startsWith('http') || blob.startsWith('blob:http'))) {
      downloadLink.setAttribute('href', blob)
      downloadLink.setAttribute('download', fileName)
      downloadLink.click() // 模拟点击实现下载
      return
    }

    let url = URL.createObjectURL(blob) // 创建对象超链接
    downloadLink.setAttribute('href', url)
    downloadLink.setAttribute('download', fileName)
    downloadLink.click() // 模拟点击实现下载
    setTimeout(() => { // 延时释放
      URL.revokeObjectURL(url) // 用URL.revokeObjectURL()来释放这个object URL
    }, 100)
  },
  entityList2InstSql (entityList, entityName, tableName, columnList) {
    let sql = '-- changeset 请修改为自己的姓名:' + Vue.moment(new Date().getTime()).format('YYYYMMDDHHmm00') + ' runOnChange:true'
    sql += '\n-- comment: ' + entityName + '实例化'
    sql += '\n-- ' + entityName + '实例化 BEGIN ************************'
    sql += this.entityList2Sql(entityList, '', tableName, '1=1', columnList)
    sql += '\n-- ' + entityName + '实例化 END **************************'
    return sql
  },
  entityList2Sql (entityList, entityName, tableName, where, columnList) {
    if (!Array.isArray(entityList)) {
      return this.entity2Sql(entityList, entityName, tableName, where, columnList)
    }

    let sql = ''
    if (entityName) {
      sql += '\n-- ' + entityName
    }
    if (where) {
      sql += '\nDELETE FROM `' + tableName + '` WHERE ' + where + ';'
    }
    if (entityList && entityList.length) {
      sql += '\nINSERT INTO `' + tableName + '`(' + columnList.map(column => '`' + column + '`').join(',') + ') VALUES'
      sql += '\n' + entityList.map(entity => '(' + columnList.map(column => this.fieldValue(entity[column])).join(',') + ')').join(',\n') + ';'
    }
    return sql
  },
  entity2Sql (entity, entityName, tableName, where, columnList) {
    let sql = ''
    if (entityName) {
      sql += '\n-- ' + entityName
    }
    if (where) {
      sql += '\nDELETE FROM `' + tableName + '` WHERE ' + where + ';'
    }
    if (entity) {
      sql += '\nINSERT INTO `' + tableName + '`(' + columnList.map(column => '`' + column + '`').join(',') + ') VALUES'
      sql += '\n(' + columnList.map(column => this.fieldValue(entity[column])).join(',') + ');'
    }
    return sql
  },
  fieldValue (value) {
    if (value || value === 0) {
      if (typeof value === 'string') {
        // 2018-11-06T08:00:00.000+0000
        if (/^[1-9][0-9]{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])T(2[0-3]|[0-1][0-9]):[0-5][0-9]:[0-5][0-9]\.[0-9]{3}\+[0-9]{4}$/.test(value)) {
          return '\'' + Vue.moment(value).format('YYYY-MM-DD HH:mm:ss') + '\''
        }
        return '\'' + value.replace(/'/g, '\\\'').replace(/\r\n/g, '\\r\\n').replace(/\n/g, '\\n') + '\''
      } else if (typeof value === 'object') {
        value = JSON.stringify(value)
        return '\'' + value.replace(/'/g, '\\\'').replace(/\r\n/g, '\\r\\n').replace(/\n/g, '\\n') + '\''
      } else {
        return value
      }
    }
    return 'NULL'
  }
}

/**
 * Install plugin.
 */
const plugin = function (Vue) {
  if (plugin.installed) {
    return
  }

  VueJw.vm = Vue

  Vue.prototype.$jw = VueJw
  Vue.jw = VueJw
}

if (typeof window !== 'undefined' && window.Vue) {
  window.Vue.use(plugin)
}

export default plugin
