<template>
  <el-date-picker ref="input" v-model="value"
                  :type="filterParams.datePickerOptions.type"
                  :format="filterParams.datePickerOptions.format"
                  :picker-options="filterParams.datePickerOptions.pickerOptions"
                  :title="filterParams.datePickerOptions.placeholder"
                  :placeholder="filterParams.datePickerOptions.placeholder"
                  @change="onChange">
  </el-date-picker>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        value: null,
        filterParams: {
          filterType: 'Timestamp',
          type: 'between', // between、begin、end
          datePickerOptions: {
            type: 'daterange',
            format: 'yyyy-MM-dd',
            placeholder: '请选择日期范围',
            pickerOptions: {
              shortcuts: this.$jw.buildPickerOptionsShortcuts([
                'today',
                'tomorrow',
                'currentWeek',
                'lastWeek',
                'currentMonth',
                'lastMonth',
                // 'currentQuarter',
                // 'lastQuarter',
                'currentYear',
                'lastYear',
                // 'latestWeek',
                // 'latestMonth',
                // 'latestQuarter',
                'latestYear'
              ])
            }
          }
        }
      }
    },
    created () {
      this.$lodash.merge(this.filterParams, this.params.column.colDef.filterParams)

      if (this.filterParams.type === 'between') {
        this.value = [null, null]
      }
    },
    methods: {
      isFilterActive () {
        if (this.filterParams.type === 'between') {
          return this.value[0] && this.value[1]
        } else {
          return this.value !== undefined && this.value !== null && this.value !== ''
        }
      },
      doesFilterPass (filterPassParams) {
        console && console.info('doesFilterPass', this.$options.name, filterPassParams)
        return this.params.valueGetter(filterPassParams.node).contains(this.value)
      },
      getModel () {
        return {
          filter: this._valueToFilter(this.value),
          filterType: this.filterParams.filterType,
          type: this.filterParams.type
        }
      },
      setModel (model) {
        this.value = this._filterToValue(model ? model.filter : null)
      },
      onFloatingFilterChanged (model) {
        this.setModel(model)
        this.params.filterChangedCallback()
      },
      onChange (val) { // 这里的val是格式化后的字符串，即和看到的输入框中的文本一致
        this.params.filterChangedCallback()
      },
      _filterToValue (filter) {
        if (this.filterParams.type === 'between') {
          filter = filter || [null, null]
          return [filter[0] ? Vue.moment(filter[0]).toDate() : null, filter[1] ? Vue.moment(filter[1]).toDate() : null]
        } else {
          return filter ? Vue.moment(filter).toDate() : null
        }
      },
      _valueToFilter (value) {
        if (this.filterParams.type === 'between') {
          value = value || [null, null]
          if (this.filterParams.datePickerOptions.type === 'daterange') {
            return [value[0] ? Vue.moment(value[0]).startOf('day').format() : null, value[1] ? Vue.moment(value[1]).endOf('day').format() : null]
          } else {
            return [value[0] ? Vue.moment(value[0]).format() : null, value[1] ? Vue.moment(value[1]).format() : null]
          }
        } else if (this.filterParams.type === 'begin') {
          return value ? Vue.moment(value).startOf('day').format() : null
        } else if (this.filterParams.type === 'end') {
          return value ? Vue.moment(value).endOf('day').format() : null
        } else {
          return value ? Vue.moment(value).format() : null
        }
      }
    }
  })
</script>
