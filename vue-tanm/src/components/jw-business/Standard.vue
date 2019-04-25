<script>
  import Vue from 'vue'

  export default {
    name: 'jw-standard',
    props: {
      value: {type: Number | Array, required: true},
      type: {type: String, required: true},
      render: {type: Function, default: null}
    },
    data () {
      return {text: null}
    },
    watch: {
      value (newVal, oldVal) {
        this._updateText()
      }
    },
    created () {
      this._updateText()
    },
    render (h) {
      if (this.render) {
        return this.render(h)
      }
      return h('div', {
        style: 'display: inline;overflow: hidden;white-space: nowrap;text-overflow:ellipsis;',
        domProps: {title: this.text}
      }, this.text)
    },
    methods: {
      _updateText () {
        if (!this.value) {
          this.text = null
          return
        }

        Vue.store.dispatch('loadCache', {
          key: this.type + 'StandardMap',
          url: '/api/hnjky/testing/cfg/' + this.type + '/standards',
          convertHandler (standardList) {
            return standardList.reduce((standardMap, standard) => {
              standard.name = '《' + standard.name + '》' + standard.no
              standardMap[standard.id] = standard
              return standardMap
            }, {})
          }
        }).then(standardMap => {
          if (typeof this.value === 'number') {
            this.text = standardMap[this.value].name
          } else {
            this.text = this.value.map((standardId, idx) => (idx + 1) + '、' + standardMap[standardId].name + '；').join('\n')
          }
        })
      }
    }
  }
</script>
