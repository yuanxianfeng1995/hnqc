<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  /* Firefox下有bug，需要把overflow-x重新设置下*/
  .el-tree-node > .el-tree-node__children {
    overflow: initial;
  }

  .jw-tree-tooltip {
    float: right;
    margin-right: 20px;
    display: none;
    color: #20a0ff;
  }

  .el-tree-node__content:hover .jw-tree-tooltip {
    display: inline-block;
  }
</style>


<template>
  <el-tree ref="menuTree"
           show-checkbox
           node-key="id"
           :props="props"
           :check-strictly="true"
           :default-expanded-keys="expandedMenuIds"
           :default-checked-keys="checkedMenuIds"
           :data="nodes"
           :render-content="renderContent"
           @check-change="onCheckChange">
  </el-tree>
</template>


<script>
  export default {
    name: 'jwMenuSelector',
    props: {
      selectorOptions: Object,
      default () {
        return {}
      }
    },
    data () {
      return {
        nodes: [],
        expandedMenuIds: [],
        checkedMenuIds: [],
        props: {
          children: 'children',
          label: 'name'
        },
        options: {
          isExpanded (node) {
            return node.type < 2
          },
          isChecked (node) {
            return node.menu_id
          }
        }
      }
    },
    created () {
      this.$lodash.merge(this.options, this.selectorOptions)
    },
    methods: {
      getCheckedKeys () {
        return this.$refs['menuTree'].getCheckedKeys()
      },
      setData (data) {
        this.nodes = data || []
        this.expandedMenuIds = []
        this.checkedMenuIds = []
        this._updateCheckedNode(this.nodes)
      },
      _updateCheckedNode (nodes) {
        nodes.forEach((node) => {
          if (this.options.isChecked(node)) {
            this.checkedMenuIds.push(node.id)
          }
          if (this.options.isExpanded(node)) {
            this.expandedMenuIds.push(node.id)
          }
          if (node.children && node.children.length > 0) {
            this._updateCheckedNode(node.children)
          }
        })
      },
      _getParent (treeNodes, child) {
        let parent = null
        treeNodes.every((node) => {
          let parentPath = node.parent_path + node.id + '/'
          if (node.id === child.parent_id) {
            parent = node
            return false
          }
          if (child.parent_path.substr(0, parentPath.length) === parentPath) {
            parent = this._getParent(node.children, child)
            return false
          }
          return true
        })

        return parent
      },
      renderContent (h, {node, data, store}) {
        if (this.allShowName) {
          return (
            <span>
              <span>
                <i style="min-width:16px;"></i> { data.name }
              </span>
              <span style="color: #f00;font-weight: bold;">{data.mark ? '*' : ''}</span>
              <span class="jw-tree-tooltip">
                {data.remark ? data.remark : (data.desc ? data.desc : data.name)}
              </span>
            </span>
          )
        }
        return (
          <span>
            <span>
              <i class={data.icon || (data.type < 2 ? 'iconfont icon-renwuguanli' : (data.type === 2 ? 'iconfont icon-yiqishebeiliuzhuan' : ''))} style="min-width:16px;"></i> {data.type < 3 ? data.name : data.desc}
            </span>
            <span style="color: #f00;font-weight: bold;">{data.mark ? '*' : ''}</span>
            <span class="jw-tree-tooltip">
              {data.remark ? data.remark : (data.desc ? data.desc : data.name)}
            </span>
          </span>
        )
      },
      onCheckChange (data, checked, indeterminate) {
        if (checked) {
          let node = this._getParent(this.nodes, data)
          if (node && !node.menu_id) {
            node.menu_id = node.id // 这里需要先赋值
            this.$refs['menuTree'].setChecked(node, checked, false) // 勾选父节点
          }

          if (!data.menu_id && data.children && data.children.length) {
            this._setChildrenChecked(data.children)
          }
          data.menu_id = data.id
        } else {
          if (data.children && data.children.length) {
            data.children.forEach((node) => {
              this.$refs['menuTree'].setChecked(node, checked, true) // 不勾选所有的子节点
            })
          }
          data.menu_id = null
        }
      },
      _setChildrenChecked (nodes) {
        nodes.forEach((node) => {
          this.$refs['menuTree'].setChecked(node, true, false)

          if (node.children && node.children.length) {
            this._setChildrenChecked(node.children)
          }
        })
      }
    }
  }
</script>
