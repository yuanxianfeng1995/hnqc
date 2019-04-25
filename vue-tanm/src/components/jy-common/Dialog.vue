<style>
  .jy-dialog-full {
    width: 100%!important;
  }
</style>

<template>
  <div v-if="visible">
    <el-dialog v-model="visible"
               :title="options.title"
               :modal="options.modal"
               :modal-append-to-body="options.modalAppendToBody"
               :close-on-click-modal="options.closeOnClickModal"
               :size="options.full ? 'full' : 'large'"
               :top="top"
               :custom-class="customClass"
               :before-close="onBeforeClose"
               @open="onDialogOpen"
               @close="onDialogClose">
      <el-collapse-transition>
        <div v-if="visible">
          <div class="jy-dialog-body" :style="bodyStyle">
            <slot></slot>
          </div>
        </div>
      </el-collapse-transition>


      <div slot="footer" class="jy-dialog-footer" v-if="options.footer">
        <slot name="commands">
          <el-button v-for="command in options.commandList"
                     :key="command.id"
                     :type="command.type"
                     :loading="command.loading !== false && $store.getters.isLoading()"
                     @click="onCommand(command)">
            {{command.text}}
          </el-button>
        </slot>
      </div>
    </el-dialog>
  </div>
</template>


<script>
  export default {
    name: 'jyDialog',
    props: {
      dialogOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        visible: false,
        commands: {
          close: {
            text: '关 闭',
            loading: false,
            type: null // primary、success、warning、danger、info、text
          },
          cancel: {
            text: '取 消',
            loading: false,
            type: null // primary、success、warning、danger、info、text
          },
          select: {
            text: '选 择',
            type: 'primary' // primary、success、warning、danger、info、text
          },
          save: {
            text: '保 存',
            type: 'primary' // primary、success、warning、danger、info、text
          },
          submit: {
            text: '提 交',
            type: 'primary' // primary、success、warning、danger、info、text
          },
          review: {
            text: '审 核',
            loading: false,
            type: 'primary' // primary、success、warning、danger、info、text
          },
          audit: {
            text: '审 批',
            loading: false,
            type: 'primary' // primary、success、warning、danger、info、text
          },
          approve: {
            text: '同 意',
            loading: false,
            type: 'primary' // primary、success、warning、danger、info、text
          },
          reject: {
            text: '驳 回',
            loading: false,
            type: 'warning' // primary、success、warning、danger、info、text
          }
        },
        options: {
          title: '',
          draggable: true, // 是否可以拖动
          modal: false, // 是否为模态对话框
          closeOnClickModal: false, // 点击遮罩层是否关闭对话框
          modalAppendToBody: false, // 遮罩层是否插入至 body 元素上，若为 false，则遮罩层会插入至 Dialog 的父元素上
          full: false, // 是否全屏显示,
          footer: true,
          top: null,
          dialogClass: '',
          height: 450 + 17, // 默认10行的高度，17为水平滚动的高度
          maxHeight: null,
          commands: [],
          commandList: [],
          beforeOpenFn: null, // function()，必须返回一个Promise
          openedFn: null, // function()
          commandFn: null, // function(command)，必须返回一个Promise
          beforeCloseFn: null, // function()，必须返回一个Promise
          closedFn: null // function()
        }
      }
    },
    computed: {
      top () {
        return (this.options.top === null ? this.$store.state.layout.top.height : this.options.top) + 'px'
      },
      customClass () {
        return 'jy-dialog ' + (this.options.full ? 'jy-dialog-full ' : '') + this.options.dialogClass
      },
      bodyStyle () {
        let maxHeight = this.$store.getters.getDialogBodyMaxHeight(this.options.full ? this.$store.state.layout.window.height : this.options.maxHeight, this.options.footer) - 1 // 1为边框
        return {
          'height': this.options.full ? (maxHeight + 'px') : ((typeof this.options.height === 'number') ? (this.options.height + 'px') : this.options.height),
          'max-height': maxHeight + 'px'
        }
      }
    },
    created () {
      let dom = document.createElement('div')
      document.getElementsByTagName('body')[0].appendChild(dom)
      this.$mount(dom)
      this._$options = this.$lodash.merge({}, this.options)
      this.options = this.$lodash.merge({}, this._$options, this.dialogOptions)
    },
    methods: {
      close () { // 供外部调用的接口
        // console.log('close')
        this.visible = false
      },
      open (options) { // 供外部调用的接口
        // console.log('open', options)
        this.options = this.$lodash.merge({}, this._$options, this.dialogOptions, options || {})

        if (this.options.beforeOpenFn) {
          this.options.beforeOpenFn.call(this).then(() => this._open())
        } else {
          this._open()
        }
      },
      onDialogOpen () {
        // console.log('onDialogOpen')
        if (this.options.openedFn) {
          this.options.openedFn.call(this)
        }
      },
      onBeforeClose (cb) {
        // console.log('onBeforeClose')
        if (this.options.beforeCloseFn) {
          this.options.beforeCloseFn.call(this).then(() => cb())
        } else {
          cb()
        }
      },
      onDialogClose () {
        // console.log('onDialogClose')
        if (this.options.closedFn) {
          this.options.closedFn.call(this)
        }
      },
      onCommand (command) {
        // console.log('onCommand', command)
        if (command.onClick) {
          command.onClick.call(this, command).then(() => {
            this.close()
            this.onDialogClose()
          })
          return
        }

        if (this.options.commandFn) {
          this.options.commandFn.call(this, command).then(() => {
            this.close()
            this.onDialogClose()
          })
        } else {
          this.close()
        }
      },
      _open () {
        if (this.options.commands.length === 0) {
          this.options.commands.push({id: 'close'})
        }
        this.options.commandList = this.options.commands.map(command => this.$lodash.merge({}, this.commands[command.id] || {}, command))

        this.visible = true
        this.$nextTick(() => {
          this._addDraggable()
        })
      },
      _addDraggable () {
        if (!this.options.draggable || this.options.full) {
          return
        }
        let dialog = this.$el.querySelector('.jy-dialog')
        let dialogHeader = dialog.firstChild
        dialogHeader.style.cursor = 'move'
        dialogHeader.onmousedown = function (mouseDownEvent) {
          mouseDownEvent.preventDefault()

          let minLeft = -dialog.clientWidth / 2 + 40
          let offset = {
            left: mouseDownEvent.clientX - dialog.offsetLeft,
            top: mouseDownEvent.clientY - dialog.offsetTop
          }

          document.onmousemove = function (mouseMoveEvent) {
            let left = mouseMoveEvent.clientX - offset.left
            let top = mouseMoveEvent.clientY - offset.top
            dialog.style.left = (left < minLeft) ? minLeft : left + 'px'
            dialog.style.top = (top < 0 ? 0 : top) + 'px'
          }

          document.onmouseup = function () {
            document.onmousemove = null
            document.onmousedown = null
          }
        }
      }
    }
  }
</script>
