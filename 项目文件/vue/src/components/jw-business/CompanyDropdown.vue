<style scoped>
</style>

<template>
  <div>
    <div v-if="companyList.length === 0" style="margin: auto 20px;color: #fff;">
      <a style="cursor: pointer;" @click="onJoinCompany">加入企业</a>
    </div>
    <div v-else>
      <el-dropdown @command="onCommand">
        <el-button>
          {{company.name || '我的有效企业列表'}}<i class="el-icon-caret-bottom el-icon--right"></i>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item v-for="item in companyList" :key="item.id" :command="'switchCompany_' + item.id" :disabled="item.id === company.id">
            <i class="fa fa-copyright" style="min-width:14px;"></i> {{item.name}}
          </el-dropdown-item>
          <el-dropdown-item command="myCompanies" divided>
            <i class="fa fa-bars" style="min-width:14px;"></i> 我的企业列表
          </el-dropdown-item>
          <el-dropdown-item command="joinCompany" divided>
            <i class="fa fa-plus-square" style="min-width:14px;"></i> 申请加入企业
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <jy-selector-dialog ref="selector"
                        :selector-options="{name: '待加入的企业', selectedFn: rows => this.joinCompany(rows)}">
      <div style="padding: 20px;height: 100%;">
        <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>
      </div>
    </jy-selector-dialog>
  </div>
</template>

<script>
  import {
    IndexRendererFramework,
    LikeFilterFramework,
    LikeFloatingFilterComponentFramework
  } from 'components/ag-grid'

  export default {
    name: 'jwCompanyDropdown',
    data () {
      return {
        gridOptions: this.$grid.buildOptions({
          context: {
            name: '企业信息',
            url: '/api/zkpms/workbench/companies',
            featureComponent: this,
            params: {
              orderBy: 'name'
            }
          },
          columnDefs: [{
            headerName: '',
            pinned: 'left',
            checkboxSelection: true,
            cellStyle: {'text-align': 'center'},
            width: 24
          }, {
            headerName: '#',
            pinned: 'left',
            cellStyle: {'text-align': 'right'},
            cellRendererFramework: IndexRendererFramework,
            width: 38
          }, {
            headerName: '名称',
            field: 'name',
            tooltipField: 'name',
            pinned: 'left',
            suppressSorting: false,
            suppressFilter: false,
            filterFramework: LikeFilterFramework,
            floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
            width: 250
          }, {
            headerName: '创建人',
            field: 'creator_name',
            width: 100
          }, {
            headerName: '联系电话',
            field: 'telephone',
            tooltipField: 'telephone',
            width: 150
          }, {
            headerName: '联系地址',
            field: 'address',
            tooltipField: 'address',
            width: 200
          }, {
            headerName: '描述',
            field: 'desc',
            tooltipField: 'desc',
            width: 500
          }]
        })
      }
    },
    computed: {
      company () {
        return this.$store.state.company
      },
      companyList () {
        return this.$store.state.companyList
      }
    },
    methods: {
      _switchCompany (companyId) {
        this.$http.post('/api/zkpms/workbench/my/companies/' + companyId + '/switch', {}, {showSuccessMessage: false}).then((response) => {
          this.$store.dispatch('switch', response.body.success ? response.body.data : {})

          let menu = this.$store.state.menuList[0]
          this.$store.dispatch('openTab', {path: menu.route_path, params: menu})
        })
      },
      getSelectedRows () {
        return this.gridOptions.api.getSelectedRows()
      },
      clearSelectedRows () {
        this.gridOptions.api.deselectAll()
      },
      onCommand (command) {
        if (command === 'joinCompany') {
          this.onJoinCompany()
        } else if (command === 'myCompanies') {
          let menu = this.findMenuById('GRZX-WDQY')
          if (menu) {
            this.$store.dispatch('openTab', {path: menu.route_path, params: menu})
          }
        } else {
          this._switchCompany(Number.parseInt(command.split('_')[1]))
        }
      },
      onJoinCompany () {
        this.$refs['selector'].open()
      },
      joinCompany (selectedRows) {
        let selectedCompany = selectedRows[0]
        for (let i in this.companyList) {
          if (selectedCompany.id === this.companyList[i].id) {
            this.$alert('已经加入过该企业了，无需重复加入！', '错误', {
              confirmButtonText: '关闭',
              type: 'error'
            })
            return
          }
        }

        this.$prompt('请输入申请说明', '申请加入企业', {
          confirmButtonText: '提 交',
          cancelButtonText: '取 消'
        }).then(({value}) => {
          return this.$http.post('/api/zkpms/workbench/companies/' + selectedCompany.id + '/join', {remark: value}, {emulateJSON: true}).then((response) => {
          })
        }).catch(() => {
        })
      }
    }
  }
</script>
