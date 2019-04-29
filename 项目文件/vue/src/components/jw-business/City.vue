<template>
  <el-cascader v-model="model"
               :size="size"
               :disabled="disabled"
               :clearable="clearable"
               :filterable="filterable"
               :expand-trigger="options.expandTrigger"
               :options="cityTree"
               :props="options.props"
               :change-on-select="options.changeOnSelect"
               @change="onChange">
  </el-cascader>
</template>


<script>
  export default {
    name: 'jwCity',
    props: {
      value: {type: String, default: null},
      size: {type: String, default: null},
      disabled: {type: Boolean, default: false},
      clearable: {type: Boolean, default: false},
      filterable: {type: Boolean, default: true},
      cityOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        model: [],
        options: {
          props: {value: 'id', label: 'name', children: 'children'},
          expandTrigger: 'click', // click、hover
          changeOnSelect: true,
          isDisabled: null
        }
      }
    },
    computed: {
      cityTree () {
        return this.$store.getters.getCityTreeByParentPath('/000000/')
      }
    },
    watch: {
      value (newval, oldval) {
        this.model = this._valueToModel(newval)
      }
    },
    created () {
      this.$store.dispatch('loadCities') // 加载城市信息

      this.$lodash.merge(this.options, this.cityOptions)
      this.model = this._valueToModel(this.value)
    },
    methods: {
      _valueToModel (value) {
        if (value) {
          if (value.endsWith('/')) {
            value = value.substring(0, value.length - 1)
          }
          if (value.startsWith('/')) {
            value = value.substring(1)
          }
          if (value.length > 0) {
            return value.split('/')
          }
        }

        return []
      },
      _modelToValue (model) {
        if (model && model.length > 0) {
          return '/' + model.join('/') + '/'
        }

        return null
      },
      onChange (value) {
        let val = this._modelToValue(this.model)
        this.$emit('input', val)
        this.$emit('on-change', val, this.$store.getters.getCityByFullPath(val))
      }
    }
  }
</script>
