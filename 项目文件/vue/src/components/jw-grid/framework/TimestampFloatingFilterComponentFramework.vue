<template>
  <div class="jw-floating-filter-cell">
    <el-date-picker ref="input" v-model="value" size="small"
                    :editable="false"
                    :type="filterParams.datePickerOptions.type"
                    :format="filterParams.datePickerOptions.format"
                    :picker-options="filterParams.datePickerOptions.pickerOptions"
                    :title="filterParams.datePickerOptions.placeholder"
                    :placeholder="filterParams.datePickerOptions.placeholder"
                    @change="onChange">
    </el-date-picker>
  </div>
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
      this.$lodash.merge(this.filterParams, this.params.column.colDef.filterParams, this.params.column.colDef.floatingFilterComponentParams)

      if (this.filterParams.type === 'between') {
        this.value = [null, null]
      }
    },
    methods: {
      onParentModelChanged (parentModel) {
        this.value = this._filterToValue(parentModel ? parentModel.filter : null)
      },
      onChange (val) {
        this.params.onFloatingFilterChanged({filter: this._valueToFilter(this.value)})
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
