<template>
  <el-autocomplete v-model="model"
                   :size="size"
                   :disabled="disabled"
                   :trigger-on-focus="triggerOnFocus"
                   :select-when-unmatched="selectWhenUnmatched"
                   :fetch-suggestions="fetchSuggestions"
                   :placeholder="placeholder || '请输入后再选择'">
  </el-autocomplete>
</template>


<script>
  export default {
    name: 'jwAutocomplete',
    props: {
      value: {type: String, default: null},
      code: {type: String, required: true},
      params: {
        type: Object,
        default () {
          return {}
        }
      },
      triggerOnFocus: {type: Boolean, default: true},
      selectWhenUnmatched: {type: Boolean, default: true},
      size: {type: String, default: null},
      disabled: {type: Boolean, default: false},
      placeholder: {type: String, default: null}
    },
    data () {
      return {
        model: null
      }
    },
    watch: {
      value (newval, oldval) {
        this.model = newval
      },
      model (newval, oldval) {
        if (this.model !== this.value) {
          this.$emit('input', this.model)
          this.$emit('on-change', this.model)
        }
      }
    },
    created () {
      this.model = this.value
    },
    methods: {
      fetchSuggestions (queryString, cb) {
        this.$http.get('/api/platform/index/autocomplete/' + this.code, {
          showLoading: false,
          params: this.$lodash.merge({pageSize: 5}, this.params, {queryString: queryString})
        }).then(response => {
          cb(response.body.success ? response.body.data.items : [])
        }).catch(e => {
          console.error(e)
          cb([])
        })
      }
    }
  }
</script>
