<template>
  <div class="jw-input-selector">
    <el-select ref="input" class="jw-input-selector-input" popper-class="jw-input-selector-popper"
               v-model="ids" :size="size" :disabled="disabled" multiple readonly placeholder="请选择…"
               @visible-change="openSelector" @remove-tag="onRemoveTag">
      <el-option v-for="item in employeeList"
                 :key="item.item_code"
                 :value="item.item_code"
                 :label="item.item_text">
      </el-option>
    </el-select>

    <jy-selector-dialog ref="selector" class="jw-input-selector-selector" :selector-options="options.selectorOptions">
      <slot>
        <div class="jw-view-page">
          <jw-grid ref="grid" :grid-options="gridOptions"></jw-grid>
        </div>
      </slot>
    </jy-selector-dialog>
  </div>
</template>


<script>
  export default {
    name: 'jwEmployeeSelector',
    props: {
      value: {
        type: Number | Array,
        default: null
      },
      disabled: {type: Boolean, default: false},
      size: {type: String, default: null},
      inputOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        employeeList: [],
        options: {
          idFiled: 'id',
          nameFiled: 'name',
          selectorOptions: {
            selectedFn: selectedRows => this.onSelected(selectedRows),
            context: {
              params: {employee_status_in: [101]}
            }
          }
        },
        gridOptions: {
          suppressToolbar: true,
          getRowStyle (params) {
            if (params.node.data && params.node.data.employee_status === 102) {
              return {color: '#f7ba2a'}
            } else if (params.node.data && params.node.data.employee_status === 103) {
              return {color: '#959595'}
            }
          },
          context: {
            name: '员工',
            url: '/api/platform/index/selector/employees',
            featureComponent: this,
            params: {
              orderBy: 'no, convert(name USING gbk)'
            }
          }
        }
      }
    },
    computed: {
      ids: {
        get () {
          return this.value ? (this.options.selectorOptions.multiple ? this.value : [this.value]) : []
        },
        set (ids) {
        }
      }
    },
    created () {
      this.$lodash.merge(this.options, this.inputOptions)
      this.$store.dispatch('getDictItemList', 'UserNameAll').then(itemList => {
        this.employeeList = itemList
      })

      this.gridOptions.rowSelection = this.options.selectorOptions.multiple ? 'multiple' : 'single'
      this.gridOptions.columnDefs = [{
        hide: false,
        type: 'Checkbox'
      }, {
        type: ['IndexRender']
      }, {
        headerName: '姓名',
        field: 'name',
        pinned: 'left',
        suppressSorting: false,
        sortColId: 'convert(name USING gbk)',
        type: 'LikeFilter',
        width: 65
      }, {
        headerName: '工号',
        field: 'no',
        pinned: 'left',
        suppressSorting: false,
        type: ['LikeFilter'],
        width: 100
      }, {
        headerName: '证件号码',
        field: 'identity_card_no',
        tooltipField: 'identity_card_no',
        type: 'LikeFilter',
        width: 160
      }, {
        headerName: '职称',
        field: 'technical_title',
        tooltipField: 'technical_title',
        type: 'LikeFilter',
        width: 160
      }, {
        headerName: '手机',
        field: 'telephone',
        tooltipField: 'telephone',
        type: 'LikeFilter',
        width: 100
      }, {
        headerName: '所属部门',
        field: 'department_name',
        tooltipField: 'department_name',
        type: 'LikeFilter',
        width: 120
      }, {
        headerName: '岗位职务',
        field: 'post_list',
        // tooltipField: 'post_list',
        type: ['DictRender'],
        cellRendererParams: {dict: 'PostNameAll'},
        width: 200
      }, {
        headerName: '在职状态',
        field: 'employee_status',
        pinned: 'right',
        type: ['DictRender', 'DictFilter'],
        filterParams: {type: 'in', value: this.options.selectorOptions.context.params.employee_status_in || []},
        cellRendererParams: {dictCode: 'EmployeeStatus'},
        width: 88
      }]
    },
    methods: {
      openSelector (visible) {
        if (visible) {
          this.$refs['selector'].open()
        }
      },
      clearSelectedRows () {
        this.gridOptions.api.deselectAll()
      },
      getSelectedRows () {
        return this.gridOptions.api.getSelectedRows()
      },
      onSelected (selectedRows) {
        if (this.options.selectorOptions.multiple) {
          let ids = selectedRows.map((entity, idx) => {
            return entity.id
          })
          this.$emit('input', ids)
          this.$emit('on-change', ids, selectedRows)
        } else {
          let entity = selectedRows[0]
          this.$emit('input', entity.id)
          this.$emit('on-change', entity.id,entity)
        }
        return new Promise((resolve, reject) => resolve())
      },
      onRemoveTag (tagComponent) {
        if (this.options.selectorOptions.multiple) {
          this.$emit('input', this.ids.filter(id => id !== tagComponent.value).map(id => Number(id)))
        } else {
          this.$emit('input', null)
        }

        this.$emit('on-remove', tagComponent.value)
      }
    }
  }
</script>
