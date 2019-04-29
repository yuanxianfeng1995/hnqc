<style>
  .jw-report {
    border: 1px solid #d1dbe5;
    border-top: none;
  }

  .jw-report-toolbar {
    padding: 0 20px;
    height: 28px;
    background: #f5f7f9;
    border-top: 1px solid #d1dbe5;
    border-bottom: 1px solid #d1dbe5;
  }

  .jw-report-body {
    background-color: #ccc;
    overflow-x: auto;
    overflow-y: scroll;
    text-align: center;
  }

  .jw-report-body-write {

  }

  .jw-report-body-print_preview {

  }

  .jw-report-iframe {
    background-color: #fff;
    box-shadow: 0 0 0.5cm rgba(0, 0, 0, 0.5);
    border: none;
    display: block;
    margin: 20px auto;
  }

  .Box {
    position: relative;
  }

  .Box iframe {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }

  .allowPDF {
    width: 100%;
    text-align: center;
  }
</style>


<template>
  <div class="jw-report" v-loading="loading || loadIframe">
    <div class="jw-report-toolbar">
      <el-button-group class="jw-button-group" style="padding: 1px 0px;">
        <el-button type="primary" size="mini"
                   v-if="options.queryString.op.indexOf('pdf') < 0"
                   v-for="command in options.commandList"
                   :key="command.id"
                   :title="command.text"
                   @click="onCommand(command)">
          <i :class="command.icon" style="width: 14px;height: 14px;"></i>
        </el-button>
        <el-button type="primary" size="mini" title="刷新" @click="onRefresh">
          <i class="fa fa-refresh" style="width: 14px;height: 14px;"></i>
        </el-button>
      </el-button-group>
      <el-pagination style="float: right;margin: -1px 0;padding: 0 15px;"
                     v-if="options.queryString.op.indexOf('pdf') < 0"
                     layout="prev, pager, next, jumper"
                     :current-page.sync="contentPane.currentPageIndex"
                     :page-count="contentPane.reportTotalPage"
                     @current-change="onCurrentChange">
      </el-pagination>
    </div>

    <div class="jw-report-body" :class="'jw-report-body-' + options.queryString.op" :style="reportBodyStyle"
         v-if="url && options.queryString.op.indexOf('pdf') < 0">
      <div>
        <iframe ref="iframe" class="jw-report-iframe" :style="iframeStyle"
                frameborder="0" scrolling="auto" :src="url" @load="onLoad">
        </iframe>
      </div>
    </div>
    <div v-if="options.queryString.op.indexOf('pdf') >= 0" :style="reportBodyStyle" ref="allowPDF">
      <span v-if="!allowPDF" class="allowPDF">&nbsp&nbsp若PDF没有自动打开，请按此操作设置“在Chrome地址栏中输入：chrome://settings/content/pdfDocuments，进入PDF文档设置，关闭"下载 PDF 文件，而不是在 Chrome 中自动打开它们"选项”</span>
      <div style="min-width: 833px;min-height: 300px"
           :style="{height: !allowPDF ? '97%' : '100%'}"
           class="Box"
           element-loading-text="正在下载PDF文件……">
        <iframe ref="iframePdf" id="iframePdf" v-if="url"
                frameborder="0" scrolling="auto"
                :src="url"
                @load="onPdfIframeLoad">
          当前浏览器不支持在线预览PDF，请<a :href="url">下载</a>后查看。
        </iframe>
      </div>
    </div>
  </div>
</template>


<script>
  // import async from 'async'

  export default {
    name: 'jwReport',
    props: {
      lazy: {type: Boolean, default: false},
      reportOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        loading: false, // 这里没有对它进行设为true，而是在vue-resource的拦截器中做的
        loadIframe: false,
        url: null,
        allowPDF: true,
        allowTop: null,
        iframeHheight: null,
        contentPane: {
          currentSessionID: null,
          reportTotalPage: 0,
          currentPageIndex: 1,
          pageContentWidth: 793, // 21cm
          pageContentHeight: 1122 // 29.7cm
        },
        exeContentPaneFunctionHandler: {},
        commands: {
          save: {
            text: '保存',
            icon: 'fa fa-save'
          },
          printPreview: {
            text: '预览',
            icon: 'fa fa-print'
          },
          print: {
            text: '打印',
            icon: 'fa fa-print'
          },
          pdfPreview: {
            text: '预览PDF',
            icon: 'fa fa-file-pdf-o'
          },
          exportPdf: {
            text: '导出PDF',
            icon: 'fa fa-file-pdf-o'
          },
          exportWord: {
            text: '导出Word',
            icon: 'fa fa-file-word-o'
          }
        },
        options: {
          context: {
            reportDialogComponent: null
          },
          reportServerUrl: '/api/frreport-server/ReportServer',
          templateCode: null, // 模板编码，templateCode和templateVersionId必须传入一个
          templateVersion: null, // 模板版本ID，templateCode和templateVersionId必须传入一个
          commands: [],
          commandList: [],
          iframePaddingTop: 121.5,
          iframePaddingLeft: 72,
          params: {},
          queryString: {
            op: 'print_preview' // 操作类型 print_preview pdprint, pdpreview, pdview
          }
        }
      }
    },
    computed: {
      bodyStyle () {
        if (this.options.context.reportDialogComponent) {
          return {'max-height': (this.$store.getters.getDialogBodyMaxHeight(null, this.options.context.reportDialogComponent.$refs.dialog.options.footer) - 1 - 28) + 'px'}
        }
      },
      reportBodyStyle () {
        let maxHeight = this.$store.getters.getDialogBodyMaxHeight(this.options.full ? this.$store.state.layout.window.height : this.options.maxHeight, this.options.footer) - 1 // 1为边框
        return {
          height: maxHeight - this.allowTop + 20 + 'px' // 20 padding 的高度
        }
      },
      iframeStyle () {
        if (this.options.queryString.op === 'write') {
          return {
            'padding-top': this.options.iframePaddingTop + 'px',
            'padding-left': this.options.iframePaddingLeft + 'px',
            'width': (this.contentPane.pageContentWidth + this.options.iframePaddingLeft * 2) + 'px',
            'height': (this.contentPane.pageContentHeight + this.options.iframePaddingTop * 2) + 'px'
          }
        } else {
          return {
            'width': this.contentPane.pageContentWidth + 'px',
            'min-height': (this.contentPane.pageContentHeight + 30) + 'px'
          }
        }
      }
    },
    created () {
      this._$options = this.$lodash.merge({}, this.options)
      window.addEventListener('message', this.onMessage)
      if (!this.lazy) {
        this.load()
      }
    },
    mounted () {
      this.$nextTick(() => {
        this.allowTop = this.$refs.allowPDF ? this.$refs.allowPDF.offsetTop : 100
      })
    },
    beforeDestroy () {
      window.removeEventListener('message', this.onMessage)
      if (this.url && this.options.queryString.op.indexOf('pdf') >= 0) {
        URL.revokeObjectURL(this.url)
      }
    },
    methods: {
      onRefresh () {
        this.$refs.iframe.contentWindow.postMessage({cmd: 'reload'}, '*')
      },
      sizeToFit () {
        // console.log('sizeToFit')
        this.$refs.iframe.contentWindow.postMessage({cmd: 'sizeToFit'}, '*')
      },
      load (options) {
        // console.log('load', options)
        this.options = this.$lodash.merge({}, this._$options, this.reportOptions, options || {})
        this.options.commandList = this.options.commands.map(command => this.$lodash.merge({}, this.commands[command.id] || {}, command))
        this.url = null
        let p = this.options.templateList ? this._loadReportletListUrl() : this._loadReportUrl()
        p.then(url => {
          this.url = url
        })
      },
      _loadReportUrl () {
        return new Promise((resolve, reject) => {
          this._loadReportlet().then(reportlet => {
            if (this.options.queryString.op.indexOf('pdf') >= 0) {
              this._loadpdfUrl(reportlet).then(url => {
                resolve(url)
              })
              return
            }
            let queryString = '?_uid=' + this._uid
            if (Array.isArray(reportlet)) {
              queryString += '&reportlets=' + encodeURIComponent('(' + reportlet.map(item => '{reportlet:' + item + '}').join(',') + ')')
            } else {
              queryString += '&reportlet=' + reportlet
            }
            for (let param in this.options.queryString) {
              queryString += '&' + param + '=' + encodeURIComponent(this.options.queryString[param])
            }
            if (this.options.params.id || this.options.params.id === 0) {
              queryString += '&id=' + this.options.params.id
            }
            let url = this.$cfg.apiUrl + this.options.reportServerUrl + queryString
            this.loadIframe = true
            resolve(url)
          })
        }).catch(e => {
          this.$alert(e.message, '错误', {confirmButtonText: '关闭', type: 'error'})
        })
      },
      _loadReportlet () {
        return new Promise((resolve, reject) => {
          if (!this.options.templateCode && !this.options.templateVersion) {
            reject(new Error('请指定模板编码或模板版本ID！'))
            return
          }
          let cacheDef = {
            key: 'templateCode-' + this.options.templateCode,
            url: '/api/platform/index/templates/' + this.options.templateCode,
            convertHandler: (templateVersion) => templateVersion.local_path.trim()
          }
          if (this.options.templateVersion) {
            cacheDef.key += '-' + this.options.templateVersion
            cacheDef.url += '/' + this.options.templateVersion
          }
          cacheDef.url += '?nullable=false'

          this.$store.dispatch('loadCache', cacheDef).then(reportlet => {
            console.log('_loadReportlet', reportlet)
            resolve(reportlet)
          }).catch(e => reject(e))
        })
      },
      _loadReportletListUrl () {
        return new Promise((resolve, reject) => {
          let reportTemplates = []
          for (let i = 0; i < this.options.templateList.length; i++) {
            let item = this.options.templateList[i]
            let cacheDef = {
              key: 'templateCode-' + item,
              url: '/api/platform/index/templates/' + item,
              convertHandler: (templateVersion) => templateVersion.local_path.trim()
            }
            cacheDef.url += '?nullable=false'
            this.$store.dispatch('loadCache', cacheDef).then(reportlet => {
              reportTemplates.push(reportlet)
              if (i === this.options.templateList.length - 1) {
                this._loadpdfUrl(reportTemplates).then(url => {
                  resolve(url)
                })
              }
            })
          }
        })
      },
      _loadpdfUrl (reportlet) {
        return new Promise((resolve, reject) => {
          let options = []
          let reportlets = Array.isArray(reportlet) ? reportlet : [].concat(reportlet)
          let toolbar = this.options.queryString.op === 'pdview' ? 0 : 1
          reportlets.map((item) => {
            options.push({
              id: this.options.params.id || 0,
              queryString: {url: this.options.queryString.url},
              reportlet: item
            })
          })
          this.$http.post(this.$cfg.apiUrl + '/api/frreport/index/pdf/print/view', options, {responseType: 'blob'}).then(response => {
            let url = URL.createObjectURL(response.bodyBlob) + '#zoom=100&toolbar=' + toolbar
            resolve(url)
          })
        })
      },
      onMessage (e) {
        if (!e.data.cmd) {
          return
        }

        // console.log('message', this.url, e.data.$locationHref, e)
        let data = e.data
        if (this.url !== e.data.$locationHref) {
          return
        }

        switch (data.cmd) {
          case 'onContentPaneAfterload':
            this.loadIframe = false
            if (data.contentPane.pageContentWidth) {
              this.contentPane = data.contentPane
            }
            break
          case 'onVerifyAndWriteReportResponse':
            this.$message({type: 'success', duration: 1500, message: '处理成功。'})
            if (this.exeContentPaneFunctionHandler['onVerifyAndWriteReportResponse']) {
              this.exeContentPaneFunctionHandler['onVerifyAndWriteReportResponse'].call(this, null)
              delete this.exeContentPaneFunctionHandler['onVerifyAndWriteReportResponse']
            }
        }
      },
      onLoad () {

      },
      onPdfIframeLoad () {
        if (this.$refs.iframePdf && !this.$refs.iframePdf.contentDocument) {
          this.allowPDF = false
        } else {
          this.allowPDF = true
        }
      },
      onCurrentChange (currentPageIndex) {
        this.loadIframe = true
        this._exeContentPaneFunction('gotoPage', currentPageIndex)
      },
      onCommand (command) {
        return new Promise((resolve, reject) => {
          switch (command.id) {
            case 'save':
              this.exeContentPaneFunctionHandler['onVerifyAndWriteReportResponse'] = response => resolve(response)
              this._exeContentPaneFunction('verifyAndWriteReport', false)
              break
            case 'print':
              this._exeContentPaneFunction('noClientPrint', false)
              break
            case 'exportPdf':
              this._exeContentPaneFunction('exportReportToPDF')
              break
            case 'exportWord':
              this._exeContentPaneFunction('exportReportToWord')
              break
            default:
              this.$emit('command', command)
          }
        })
      },
      _exeContentPaneFunction (functionName, args) {
        this.$refs.iframe.contentWindow.postMessage({
          cmd: 'exeContentPaneFunction',
          functionName: functionName,
          args: args
        }, '*')
      }
    }
  }
</script>
