<style>
  .jw-json-editor, .jw-json-editor-group {
    width: 100%;
    height: 100%;
  }

  .jw-json-editor-left, .jw-json-editor-right {
    width: 50%;
    height: 100%;
  }

  .jw-json-editor div.jsoneditor {
    border: 1px solid #bfcbd9;
    border-radius: 4px;
  }

  .jw-json-editor-left div.jsoneditor {
    border-top-right-radius: 0;
    border-bottom-right-radius: 0;
  }

  .jw-json-editor-right div.jsoneditor {
    border-top-left-radius: 0;
    border-bottom-left-radius: 0;
  }

  .jw-json-editor div.jsoneditor-menu {
    border-top-left-radius: inherit;
    border-top-right-radius: inherit;
  }
</style>

<template>
  <div style="width: 100%;height: 100%;">
    <div class="jw-json-editor" v-if="options"></div>
    <el-row class="jw-json-editor-group" v-else>
      <el-col :span="12" class="jw-json-editor jw-json-editor-left"></el-col>
      <el-col :span="12" class="jw-json-editor jw-json-editor-right"></el-col>
    </el-row>
  </div>
</template>

<script>
  import 'jsoneditor/dist/jsoneditor.css'
  import JsonEditor from 'jsoneditor'

  export default {
    name: 'jwJsonEditor',
    props: {
      value: {required: true},
      options: {
        type: Object,
        default () {
          return null
        }
      },
      leftOptions: {
        type: Object,
        default () {
          return {mode: 'tree'}
        }
      },
      rightOptions: {
        type: Object,
        default () {
          return {mode: 'code'}
        }
      }
    },
    data () {
      return {
        editor: null,
        leftEditor: null,
        rightEditor: null,
        error: false,
        json: this.value,
        internalChange: false
      }
    },
    watch: {
      value (newValue) {
        if (!this.internalChange) {
          if (this.editor) {
            this.editor.set(newValue)
          } else {
            this.leftEditor.set(newValue)
            this.rightEditor.set(newValue)
          }
          this.$emit('change', newValue)
        }
      }
    },
    mounted () {
      if (this.options) {
        this.editor = new JsonEditor(this.$el.querySelector('.jw-json-editor'), this._buildOptions({onChange: () => this.onChange('editor')}, this.options), this.json)
      } else {
        this.leftEditor = new JsonEditor(this.$el.querySelector('.jw-json-editor-left'), this._buildOptions({onChange: () => this.onChange('leftEditor')}, this.leftOptions), this.json)
        this.rightEditor = new JsonEditor(this.$el.querySelector('.jw-json-editor-right'), this._buildOptions({onChange: () => this.onChange('rightEditor')}, this.rightOptions), this.json)
      }
    },
    methods: {
      _buildOptions (defaultOptions, options) {
        let opt = this.$lodash.merge({mode: 'tree'}, defaultOptions, options)
        if (!opt.modes) {
          opt.modes = ['tree', 'code', 'form', 'text', 'view']
        }
        return opt
      },
      onChange (editorName) {
        try {
          this.json = this[editorName].get()
          if (editorName === 'leftEditor') {
            this.rightEditor.set(this.json)
          } else if (editorName === 'rightEditor') {
            this.leftEditor.set(this.json)
          }

          this.internalChange = true
          this.$emit('input', this.json)
          this.$emit('change', this.json)
          this.$nextTick(() => {
            this.internalChange = false
          })
        } catch (e) {
          console.error(e)
          this.$emit('has-error')
        }
      }
    }
  }
</script>
