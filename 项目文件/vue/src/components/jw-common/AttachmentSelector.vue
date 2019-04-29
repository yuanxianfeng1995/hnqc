<style>
  .el-upload-list__item.is-success > .el-icon-picture {
    cursor: pointer;
    color: #97a8be;
    display: inline;
    float: left;
    padding-top: 6px;
    padding-left: 4px;
    margin-right: 3px;
  }

  .el-upload-list__item.is-success > .el-icon-picture:hover {
    color: #20a0ff;
  }
</style>


<template>
  <el-upload class="jw-upload"
             :class="(options.uploadOptions.listType.startsWith('picture') ? 'jw-upload-picture ' : '') + (showUpload ? '' : 'jw-upload-view ') + options.uploadOptions.class"
             :disabled="disabled"
             :file-list="fileList"
             :list-type="options.uploadOptions.listType"
             :multiple="options.uploadOptions.multiple"
             :drag="options.uploadOptions.drag"
             :auto-upload="options.uploadOptions.autoUpload"
             :action="options.uploadOptions.action"
             :headers="options.uploadOptions.headers"
             :accept="options.uploadOptions.accept"
             :on-error="onError"
             :on-success="onSuccess"
             :on-remove="onRemove"
             :on-preview="onPreview"
             :before-upload="onBeforeUpload">
    <div v-if="options.uploadOptions.drag">
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__text" v-if="options.uploadOptions.text">{{options.uploadOptions.text}}</div>
    </div>
    <div class="el-upload__tip" slot="tip" v-if="options.uploadOptions.tip">
      {{options.uploadOptions.tip}}
    </div>
  </el-upload>
</template>


<script>
  import 'viewerjs/dist/viewer.css'
  import Viewer from 'viewerjs'

  var effective = true
  export default {
    name: 'jwAttachmentSelector',
    props: {
      value: {
        type: Array,
        default () {
          return []
        }
      },
      disabled: {type: Boolean, default: false},
      attachmentOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        emitInput: false,
        fileList: [],
        attachmentList: [],
        options: {
          idFiled: 'id',
          nameFiled: 'name',
          uploadOptions: {
            class: '',
            multiple: false,
            listType: 'text', // text、picture、picture-card
            fileList: [],
            drag: true,
            autoUpload: true,
            size: null, // 文件大小限制，单位KB
            number: 0, // 文件个数限制，0为不限
            accept: '', // 接受上传的文件类型，多种类型用“,”分隔
            action: this.$cfg.apiUrl + '/zuul/api/platform/index/attachments',
            headers: {
              'X-REST-TOKEN': this.$cookie.get('X-REST-TOKEN')
            },
            tip: null,
            text: null
          }
        }
      }
    },
    computed: {
      showUpload () {
        if (this.options.uploadOptions.multiple) {
          return this.options.uploadOptions.number === 0 || this.attachmentList.length < this.options.uploadOptions.number
        } else {
          return this.attachmentList.length === 0
        }
      }
    },
    watch: {
      value (newval, oldval) {
        if (!this.emitInput) {
          this._setModel(newval)
        }
      }
    },
    created () {
      this.$lodash.merge(this.options, this.attachmentOptions)
      this._setModel(this.value)
    },
    updated () {
      this.updateFileView()
    },
    methods: {
      updateFileView () {
        if (!effective) {
          this.removeDom()
        }
        this.$el.querySelectorAll('.el-upload-list__item.is-success').forEach((item, idx) => {
          let file = this.attachmentList[idx]
          if (file) {
            let aTag = item.querySelector('a')
            let preview = item.querySelector('.el-icon-view')
            if (preview) {
              preview.onclick = e => this.viewClick(e, file)
            }
            aTag.title = '下载 ' + file.name
            if (!(file.type && file.type.startsWith('image/'))) {
              return false
            }
            let iTag = aTag.querySelector('i')
            if (!iTag) {
              return false
            }
            iTag.title = '预览 ' + file.name
            iTag.className = 'el-icon-picture'
            aTag.removeChild(iTag)
            item.insertBefore(iTag, aTag)
            iTag.onclick = e => this.viewClick(e, file)
          }
        })
      },
      removeDom () {
        this.$el.querySelectorAll('.el-upload-list__item').forEach((item, idx) => {
          item.removeChild(this.$el.querySelector('.el-upload-list__item-status-label'))
        })
      },
      viewClick (e, file) {
        let image = new Image()
        image.src = file.url
        let viewer = new Viewer(image, {
          hidden: () => viewer.destroy(),
          zIndex: '99999'
        })
        viewer.show()
        e.stopPropagation()
      },
      _setModel (val) {
        // console.log('_setModel', 'val', val)
        if (!val || val.length === 0) {
          this.fileList = []
          this.attachmentList = []
          return
        }

        if (JSON.stringify(val) === JSON.stringify(this.attachmentList.map(attachment => attachment[this.options.idFiled]))) { // 附件ID完全相同
          return
        }

        this.fileList = []
        this.attachmentList = []
        val.forEach(id => {
          this._getAttachment(id).then(attachment => {
            attachment.url = this._convertUrl(attachment.url)
            this.attachmentList.push(attachment)

            let file = {
              id: attachment[this.options.idFiled],
              name: attachment[this.options.nameFiled],
              url: attachment.url
            }
            if (this.options.uploadOptions.multiple) {
              this.fileList.push(file)
            } else {
              this.fileList = [file]
            }
          })
        })
      },
      _getAttachment (id) {
        return new Promise((resolve, reject) => {
          this.$http.get('/zuul/api/platform/index/attachments/' + id).then(response => {
            if (response.body.success) {
              effective = true
              resolve(response.body.data)
            } else {
              reject(response)
            }
          }).catch(response => {
            effective = false
            console.error(response)
            reject(response)
          })
        })
      },
      _convertUrl (url) {
        if (!(url.startsWith('http') || url.startsWith('blob:http'))) {
          return this.$cfg.apiUrl + '/zuul' + url
        }

        let idx = url.indexOf('-internal.aliyuncs.com')
        if (idx > 0) { // 走OSS内网地址，则通过Nginx反向代理下载
          return location.protocol + '//' + location.host + '/oss' + url.substring(idx + '-internal.aliyuncs.com'.length)
        }

        return url
      },
      onError (err, file, fileList) {
        console.error('onError', 'err', err, 'file', file, 'fileList', fileList)
        let responseBody = JSON.parse(err.message.substring((err.status + ' ').length))
        this.$alert(responseBody.message || err, '错误', {
          confirmButtonText: '关闭',
          type: 'error'
        })
      },
      onSuccess (responseBody, file, fileList) {
        // console.log('onSuccess', 'responseBody', responseBody, 'file', file, 'fileList', fileList)
        if (!responseBody.success) {
          this.$alert(responseBody.message, '错误', {
            confirmButtonText: '关闭',
            type: 'error'
          })
          return
        }

        let attachmentList = file.response.data.items
        attachmentList.forEach(attachment => {
          attachment.url = this._convertUrl(attachment.url)
          file.id = attachment[this.options.idFiled]
        })

        if (this.options.uploadOptions.multiple) {
          this.attachmentList.push(...attachmentList)
        } else {
          this.attachmentList = attachmentList
        }
        this._emitInput()
        this.$nextTick(() => {
          this.updateFileView()
        })
      },
      onRemove (file, fileList) {
        // console.log('onRemove', 'file', file, 'fileList', fileList, 'attachmentList', this.attachmentList)
        let idx = this.attachmentList.findIndex(attachment => attachment[this.options.idFiled] === file.id)
        if (idx >= 0) {
          this.attachmentList.splice(idx, 1)
          this._emitInput()
        }
        this.updateFileView()
      },
      _emitInput () {
        this.emitInput = true
        this.$emit('input', this.attachmentList.map(attachment => attachment[this.options.idFiled]))
        this.$nextTick(() => {
          this.emitInput = false
        })
      },
      onPreview (file) {
        this.$jw.download(file.url, file)
      },
      onBeforeUpload (file) {
        return new Promise((resolve, reject) => {
          // console.log('onBeforeUpload', this.attachmentList.length, file)
          let size = (this.options.uploadOptions.size || 0) * 1024
          if (size && size < file.size) {
            return this._validateFailed(reject, '文件大小不能超过' + this.$jw.byte2Str(size))
          }

          let accept = this.options.uploadOptions.accept || ''
          let typeList = accept.split(',').filter(type => type)
          if (typeList.length > 0 && !typeList.some(type => type.trim() === file.type)) {
            return this._validateFailed(reject, this.options.uploadOptions.text || '文件类型错误')
          }

          resolve()
        })
      },
      _validateFailed (reject, msg) {
        this.$message.error(msg)
        reject(new Error(msg))
      }
    }
  }
</script>
