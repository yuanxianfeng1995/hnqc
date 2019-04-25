/**
 * Created by 袁进勇 on 2019/1/14.
 */
export default {
  data: function () {
    return {
      other: {}
    }
  },
  computed: {
    systemId () {
      return this.$store.state.settings.systemId
    },
    entity () {
      return this.$store.state.entrust.orderDomain.sampleEntity
    },
    orderDomain () {
      return this.$store.state.entrust.orderDomain
    },
    globalOrderEntity () {
      return this.$store.state.entrust.orderEntity
    }
  },
  created: function () {
    // 创建时，必须把所有的字段都初始化一遍，否则Vue没法响应式
    this.entity.other = this.$lodash.merge({}, this.other, this.entity.other)
  },
  methods: {
    cleanSelectedEvaluationList: function () {
      let vm = this
      vm.orderDomain.itemEntity.itemEvaluationList.forEach(function (itemEvaluation) {
        vm.orderDomain.orderEntity.evaluation_group[itemEvaluation.code] = null
      })
    },
    cleanSelectedParameterList: function () {
      this.orderDomain.orderEntity.params = this.$store.getters.getOrderParamsByCodes(this.orderDomain.orderEntity, []) // TODO
    },
    updateEvaluationList: function (predicate) {
      let vm = this
      let evaluationList = []
      vm.orderDomain.itemEntity.itemEvaluationList.forEach(function (evaluationGroup) {
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
      vm.orderDomain.itemEntity.evaluationList = evaluationList
    }
  }
}
