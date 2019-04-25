<template>
  <div style="display: inline;overflow: hidden;white-space: nowrap;text-overflow:ellipsis;" :title="html" v-html="html"></div>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        html: null
      }
    },
    created () {
      this._loadDictItems()
    },
    methods: {
      _loadDictItems () {
        if (this.params.value === undefined || this.params.value === null || this.params.value === '') {
          return
        }

        if (this.params.dictItemList) {
          if (typeof this.params.dictItemList === 'function') {
            this.params.dictItemList.call(this, itemList => this.updateDictItemText(itemList))
          } else {
            this.updateDictItemText(this.params.dictItemList)
          }
          return
        }

        Vue.store.dispatch('getDictItemText', {
          dictCode: this.params.dictCode || this.params.dict,
          itemCode: this.params.value
        }).then(itemText => this.updateDictItemText(itemText))
      },
      updateDictItemText (itemText) {
        if (Array.isArray(itemText)) {
          let item = itemText.find(item => (item.item_code + '') === (this.params.value + '')) || {item_text: this.params.value + ''}
          itemText = item.item_text
        }
        this.html = itemText
        if (this.params.render) {
          this.html = this.params.render(this.params, itemText)
        }
      }
    }
  })
</script>
