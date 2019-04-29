import Vue from 'vue'


export default {
  state: {
    activeMenu: {
      id: 'AdminHome',
      name: '首页'
    },
    routes: [{
      path: '/admin/home',
      params: {
        id: 'AdminHome',
        name: '首页',
        route_path: '/admin/home',
        icon: 'iconfont icon-shouye',
        closable: false,
        type: 2
      }
    }],
    collapseMenu: true,
    isSwitch: false // 暂用标识，标识是否是左侧菜单栏点击操作
  },
  getters: {},
  mutations: {
    updateTabs (state, tabs) {
      for (let k in tabs) {
        state[k] = tabs[k]
      }
    },
    resetTabs (state) {
      state.routes = state.routes.slice(0, 1)
    }
  },
  actions: {
    initTabs (context) {
      let tabs = Vue.cookie.get('tabs')
      if (tabs) {
        context.commit('updateTabs', JSON.parse(tabs))
      }
      Vue.cookie.delete('tabs')
    },
    initCollapseMenu ({commit, state, getters}) {
      state.collapseMenu = localStorage.getItem('collapseMenu', true, Boolean)
    },
    backupTabs (context) {
      let tabs = context.state
      tabs.routes = Vue.lodash.compact(tabs.routes.map((item, index) => {
        if (item.path.indexOf('360') < 0) {
          return item
        }
      }))
      Vue.cookie.set('tabs', JSON.stringify(tabs), {expires: '5s'})
    },
    openTab ({commit, state, getters}, payload) {
      // console.log('openTab', payload)
      if (typeof (payload) === 'string') {
        let menu = getters.findMenuById(payload)
        if (!menu) {
          Vue.prototype.$alert('菜单[' + payload + ']不存在！', '错误', {
            confirmButtonText: '关闭',
            type: 'error'
          })
          return
        }
        payload = {path: menu.route_path, params: menu}
      }

      let route = {
        path: payload.path,
        query: payload.query,
        params: {
          id: payload.params.id,
          name: payload.params.name,
          full_path: payload.params.full_path,
          route_path: payload.params.route_path,
          icon: payload.params.icon ? payload.params.icon : 'iconfont icon-yiqishebeiliuzhuan',
          closable: payload.params.closable,
          type: payload.params.type
        }
      }

      let tabs = {activeMenu: route.params}
      if (state.routes.findIndex(item => item.params.id === route.params.id) < 0) {
        tabs.routes = [...state.routes, route]
      }
      commit('updateTabs', tabs)
      if (!state.isSwitch) {
        Vue.store.dispatch('computeTab', route)
      }
      state.isSwitch = false
      if (payload.status !== 'afterRoute') {
        Vue.router.push(route)
      }
    },
    switchTab ({commit, state}, activeTabId) {
      // console.log('switchTab', state, activeTabId)
      state.isSwitch = true
      let route = state.routes.find(item => item.params.id === activeTabId)
      if (route) {
        commit('updateTabs', {activeMenu: route.params})
        Vue.router.push(route)
      }
    },
    removeTab ({commit, state}, removedTabId) {
      // console.log('removeTab', state, removedTabId)
      let tabs = {routes: state.routes.filter(item => item.params.id !== removedTabId)}
      if (state.activeMenu.id === removedTabId) { // 如果移除的是当前Tab页
        let index = state.routes.findIndex(item => item.params.id === removedTabId)
        let route = state.routes[index + 1] || state.routes[index - 1]
        tabs.activeMenu = route.params
        Vue.router.push(route)
      }

      commit('updateTabs', tabs)
    },
    collapseMenu ({commit, state}, isCollapseMenu) {
      localStorage.setItem('collapseMenu', isCollapseMenu)
      state.collapseMenu = localStorage.getItem('collapseMenu', isCollapseMenu, Boolean)
    },
    openLab360View ({dispatch, commit, state, getters}, options) {
      let title = {order: '委托单', sample: '样品', report: '报告', task: '任务单'}
      let path = '/admin/hnjky/testing/lab/360/' + options.entityType + '/' + options.entitySeqNo
      dispatch('openTab', {
        path: path,
        query: {activeKey: options.activeKey || options.entityType, operation: options.operation || 'view'},
        params: {
          name: title[options.entityType] + '：' + options.entitySeqNo,
          id: 'Lab360View' + options.entitySeqNo,
          route_path: path
        }
      })
    }
  }
}
