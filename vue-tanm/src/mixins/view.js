/**
 * Created by 袁进勇 on 2017/6/12.
 */
export default {
  props: {
    mode: {type: String, default: 'view'},
    multiple: {type: Boolean, default: false},
    viewOptions: {
      type: Object,
      default () {
        return {}
      }
    }
  },
  data () {
    let detailOptions = {}
    if (this.mode === 'selector') {
      // detailOptions.size = 'mini'
      detailOptions.top = 0
      detailOptions.maxHeight = this.$store.state.layout.center.height - 20 - 20 - 4
    }
    return {detailOptions: detailOptions}
  },
  computed: {
    permission () {
      return {
        add: false,
        edit: false,
        remove: false
      }
    }
  },
  created () {
    console.log("view.js")
    this.$lodash.merge(this.detailOptions, this.viewOptions.detailOptions || {})
    if (this.gridOptions) {
      this.$lodash.merge(this.gridOptions, this.viewOptions.gridOptions || {})
      this.gridOptions.rowSelection = this.multiple ? 'multiple' : 'single'
      this.gridOptions.columnDefs = []
    }
  },
  methods: {
    getSelectedRows () {
      if (this.gridOptions) {
        return this.gridOptions.api.getSelectedRows()
      } else {
        return this.multiple ? [] : null
      }
    },
    clearSelectedRows () {
      if (this.gridOptions) {
        this.gridOptions.api.deselectAll()
      }
    }
  }
}
