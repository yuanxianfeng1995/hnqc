<template>
  <el-tooltip effect="dark" content="全选" placement="bottom-start" v-if="visible">
    <el-checkbox v-model="value" @change="onChange"></el-checkbox>
  </el-tooltip>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        value: false,
        visible: true
      }
    },
    created () {
      this.gridOptions = this.params.api.gridOptionsWrapper.gridOptions
      this.visible = this.gridOptions.rowSelection === 'multiple'

      this.sortChanged = this.gridOptions.onSortChanged
      this.filterChanged = this.gridOptions.onFilterChanged
      this.paginationChanged = this.gridOptions.onPaginationChanged
      this.rowSelected = this.gridOptions.onRowSelected

      this.gridOptions.onSortChanged = this.onSortChanged
      this.gridOptions.onFilterChanged = this.onFilterChanged
      this.gridOptions.onPaginationChanged = this.onPaginationChanged
      this.gridOptions.onRowSelected = this.onRowSelected
    },
    methods: {
      onSortChanged (event) {
        this._deselectAll()
        if (this.sortChanged) {
          this.sortChanged.call(this.gridOptions, event)
        }
      },
      onFilterChanged (event) {
        this._deselectAll()
        if (this.filterChanged) {
          this.filterChanged.call(this.gridOptions, event)
        }
      },
      _deselectAll () {
        this.value = false
        this.params.api.deselectAll()
      },
      onPaginationChanged (event) {
        this.value = this._isAllRenderedNodesSelected()
        if (this.paginationChanged) {
          this.paginationChanged.call(this.gridOptions, event)
        }
      },
      onRowSelected (event) {
        if (this.gridOptions.pagination) {
          this.value = this._isAllRenderedNodesSelected()
        } else {
          let allSelected = true
          this.params.api.forEachNode((rowNode, index) => {
            if (!rowNode.isSelected()) {
              allSelected = false
              return true
            }
          })
          this.value = allSelected
        }

        if (this.rowSelected) {
          this.rowSelected.call(this.gridOptions, event)
        }
      },
      _isAllRenderedNodesSelected () {
        if (this.params.api.getRenderedNodes().find(rowNode => !rowNode.data || (this.gridOptions.isRowSelectable(rowNode) && !rowNode.isSelected()))) {
          return false
        }
        return this.params.api.getSelectedNodes().length > 0
      },
      onChange (e) {
        if (this.gridOptions.pagination) {
          this.params.api.getRenderedNodes().forEach((rowNode, index) => rowNode.setSelected(e.target.checked))
        } else {
          this.params.api.forEachNode((rowNode, index) => rowNode.setSelected(e.target.checked))
        }
      }
    }
  })
</script>
