<template>
  <div style="display: inline;overflow: hidden;white-space: nowrap;text-overflow:ellipsis;" :title="html" v-html="html"></div>
</template>

<script>
  import Vue from 'vue'

  export default {
    name: 'jwDictRenderer',
    props: {
      value: {type: String | Number, default: null},
      dictCode: {type: String, required: true}
    },
    data () {
      return {
        html: null
      }
    },
    watch: {
      value (newval, oldval) {
        this._updateText()
      },
      dictCode (newval, oldval) {
        this._updateText()
      }
    },
    created () {
      this._updateText()
    },
    methods: {
      _updateText () {
        Vue.store.dispatch('getDictItemText', {
          dictCode: this.dictCode,
          itemCode: this.value
        }).then(itemText => {
          this.html = itemText
        })
      }
    }
  }
</script>
