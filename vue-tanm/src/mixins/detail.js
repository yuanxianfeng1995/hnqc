/**
 * Created by 袁进勇 on 2017/6/12.
 */
export default {
  props: {
    detailOptions: {
      type: Object,
      default () {
        return {}
      }
    }
  },
  data () {
    return {
      options: {},
      entity: {},
      rules: {}
    }
  },
  created () {
    this._$options = this.$lodash.merge({}, this.options)
  },
  mounted () {
    this.$refs['form'].bus.$on('loaded-entity', (entity) => {
      this.entity = entity || {}
    })
  },
  beforeDestroy () {
    this.$refs['form'].bus.$off('loaded-entity')
  },
  methods: {
    close () { // 供外部调用的接口
      this.$refs['form'].close()
    },
    open (options) { // 供外部调用的接口
      if (!this._$entity) {
        this._$entity = this.$lodash.merge({}, this.entity)
      } else {
        this.entity = this.$lodash.merge({}, this._$entity)
      }

      this.options.params = {}
      this.options.subOperation = null
      this.options = this.$lodash.merge({}, this._$options, this.detailOptions, options)
      this.$refs['form'].open(this.options)
    }
  }
}
