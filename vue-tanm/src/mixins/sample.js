/**
 * Created by 袁进勇 on 2019/1/14.
 */
export default {
  props: {
    itemCategory: {type: Object, required: true},
    domain: {type: Object, required: true},
    options: {
      type: Object,
      default: function () {
        return {getFormFn: null}
      }
    }
  },
  data: function () {
    return {
      other: {}
    }
  },
  computed: {
    systemId () {
      return this.$store.state.settings.systemId
    }
  },
  created: function () {
    // 创建时，必须把所有的字段都初始化一遍，否则Vue没法响应式
    this.domain.sampleEntity.other = this.$lodash.merge(this.other, this.domain.sampleEntity.other)
  },
  methods: {
    cleanSelectedEvaluationList: function () {
      let vm = this
      vm.itemCategory.itemEvaluationList.forEach(function (itemEvaluation) {
        vm.domain.orderEntity.evaluation_group[itemEvaluation.code] = null
      })
    },
    cleanSelectedParameterList: function () {
      this.domain.orderEntity.params = this.$store.getters.getOrderParamsByCodes(this.domain.orderEntity, [])
    },
    updateEvaluationList: function (predicate) {
      let vm = this
      let evaluationList = []
      vm.itemCategory.itemEvaluationList.forEach(function (evaluationGroup) {
        let evaluation_standard_nos = []
        evaluationGroup.evaluation_standard_nos.forEach(function (no) {
          if (predicate(no)) {
            evaluation_standard_nos.push(no)
          }
        })
        evaluationList.push({
          code: evaluationGroup.code,
          item_id: evaluationGroup.item_id,
          evaluation_standard_nos: evaluation_standard_nos
        })
      })
      vm.itemCategory.evaluationList = evaluationList
    }
  }
}
