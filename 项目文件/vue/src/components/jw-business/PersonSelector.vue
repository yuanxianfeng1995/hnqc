<style>
  .jw-person-select-popper .el-select-dropdown__item {
    height: 55px;
  }
</style>


<template>
  <el-select
    v-model="model"
    value-key="id"
    clearable
    filterable
    allow-create
    default-first-option
    popper-class="jw-person-select-popper"
    :size="size"
    :disabled="disabled"
    placeholder="请选择或输入"
    @change="onChange">
    <el-option
      v-for="item in employeeList"
      :key="item.id"
      :value="item"
      :label="item.name"
      :disabled="isDisabled(item)">
      <div>
        <div>
          <span style="">{{ item.name }}</span>
          <span style="float: right; color: #8492a6; font-size: 12px">工号：{{ item.no }}</span>
        </div>
        <div style="color: #8492a6; font-size: 12px">
          部门：{{ item.department_name }}
        </div>
      </div>
    </el-option>
  </el-select>
</template>


<script>
  export default {
    name: 'jwPersonSelector',
    props: {
      value: {
        type: Object,
        default () {
          return null
        }
      },
      disabled: {type: Boolean, default: false},
      size: {type: String, default: null},
      selectorOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        model: '',
        employeeList: [],
        options: {
          idFiledType: 'string', // id字段数据类型
          active: false, // 是否只能选择非注销用户
          isDisabled: null // (item) { return true }
        }
      }
    },
    watch: {
      value (newval, oldval) {
        this._setModel(newval)
      }
    },
    created () {
      this.$lodash.merge(this.options, this.selectorOptions)
      this.employeeList = this.$store.getters.getCache('employeeList', [])
      if (this.employeeList && this.employeeList.length) {
        this._setModel(this.value)
        return
      }
      this.$http.get('/api/platform/index/selector/employees').then(response => {
        this.employeeList = response.body.success ? response.body.data.items : []
        this.$store.commit('addCache', {key: 'employeeList', value: this.employeeList})
        this._setModel(this.value)
      })
    },
    methods: {
      _setModel (val) {
        if (val) {
          /* if (val.id) {
            if (val.id * 1 !== this.model.id) {
              this.model = val
            }
            return
          } */
          this.model = val.name || ''
          return
        }

        this.model = ''
      },
      isDisabled (item) {
        if (this.options.isDisabled) {
          return this.options.isDisabled.call(this, item)
        }

        return false
      },
      onChange (val) {
        let entity = val
        if (typeof (val) === 'string') {
          entity = {id: this.options.idFiledType === 'string' ? '' : null, name: val}
        }
        this.$emit('input', entity)
        this.$emit('on-change', entity)
      }
    }
  }
</script>
