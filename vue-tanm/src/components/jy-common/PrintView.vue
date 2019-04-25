<style>
  .jw-iframe-pdf {
    display: block;
    min-width: 450px;
    min-height: 300px;
  }
</style>


<template>
  <jy-dialog ref="dialog">
    <div style="min-width: 450px;min-height: 300px;" v-loading="$store.getters.isLoading()"
         element-loading-text="正在下载PDF文件……">
      <span v-if="!allowPDF">&nbsp&nbsp若PDF没有自动打开，请按此操作设置“在Chrome地址栏中输入：chrome://settings/content/pdfDocuments，进入PDF文档设置，关闭"下载 PDF 文件，而不是在 Chrome 中自动打开它们"选项”</span>
      <iframe ref="iframe" class="jw-iframe-pdf" :style="iframeStyle" frameborder="0" scrolling="auto" :src="url"
              @load="onLoad" v-if="url">
        当前浏览器不支持在线预览PDF，请<a :href="url">下载</a>后查看。
      </iframe>
    </div>
  </jy-dialog>
</template>


<script>
  export default {
    name: 'jyPrintView',
    props: {
      dialogOptions: {
        type: Object,
        default () {
          return {}
        }
      },
      printViewOptions: {
        type: Object | Array,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        allowPDF: true,
        url: null,
        contentPane: {
          currentSessionID: null,
          reportTotalPage: 0,
          currentPageIndex: 1,
          pageContentWidth: 793, // 21cm
          pageContentHeight: 1122 // 29.7cm
        },
        options: {
          context: {
            reportDialogComponent: this
          },
          iframePaddingTop: 40,
          iframePaddingLeft: 72,
          dialogOptions: {
            modal: true,
            dialogClass: 'jy-dialog-report',
            height: 'auto'
          },
          printViewOptions: []
        }
      }
    },
    computed: {
      iframeStyle () {
        return {
          'height': (48 + this.options.printViewOptions.length * 222 + 20) + 'px',
          'max-height': (this.$store.getters.getDialogBodyMaxHeight(this.$refs.dialog.options.maxHeight, this.$refs.dialog.options.footer) - 1) + 'px'
        }
      }
    },
    created () {
      this._$options = this.$lodash.merge({}, this.options)
    },
    beforeDestroy () {
      if (this.url) {
        URL.revokeObjectURL(this.url)
      }
    },
    methods: {
      open (options) { // 供外部调用的接口
        this.url = null
        this.options = this.$lodash.merge({}, this._$options, {
          dialogOptions: options.dialogOptions,
          printViewOptions: options.printViewOptions
        }, options || {})
        this.$refs.dialog.open(this.options.dialogOptions)

        this.$nextTick(() => {
          this.$http.post(this.$cfg.apiUrl + '/api/frreport/index/pdf/print/view', this.options.printViewOptions, {responseType: 'blob'}).then(response => {
            this.url = URL.createObjectURL(response.bodyBlob) + '#zoom=100&toolbar=1'
          })
        })
      },
      onLoad () {
        if (this.$refs.iframe && !this.$refs.iframe.contentDocument) {
          this.allowPDF = false
        } else {
          this.allowPDF = true
        }
      }
    }
  }
</script>
