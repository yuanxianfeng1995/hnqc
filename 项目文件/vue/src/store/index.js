import Vue from 'vue'
import Vuex from 'vuex'
import menu from './modules/menu'
import user from './modules/user'
import layout from './modules/layout'
Vue.use(Vuex)


const store = new Vuex.Store({
  modules: {
    menu: menu,
    user: user,
    layout: layout
  },
  state: {
    loading: false,
    loadingCount: 0,
    devMode: process.env.NODE_ENV === 'development',
    dialog: {
      showNumber: 0,
      header: {height: 56},
      footer: {height: 56}
    },
    adminHome: {
      id: 'AdminHome',
      name: '首页',
      route_path: '/admin/home',
      icon: 'iconfont icon-shouye',
      closable: false,
      type: 2
    },
    webviewHome: {
      id: 'WebviewHome',
      name: '首页',
      route_path: '/webview/home',
      icon: 'iconfont icon-shouye',
      closable: false,
      type: 2
    },
    originalRoute: null,
    qrcodeScannedListener: null
  },
  getters: {
    isLoading: (state, getters, rootState) => () => state.loadingCount > 0,
    getDialogBodyMaxHeight: (state, getters, rootState) => (maxBodyHeight, footer) => {
      let maxHeight = maxBodyHeight || (rootState.layout.center.height - (rootState.layout.bottom.height ? 0 : 11))
      return maxHeight - state.dialog.header.height - ((footer === undefined || footer === true) ? state.dialog.footer.height : 0)
    }
  },
  mutations: {
    registQrcodeScannedListener (state, callback) {
      state.qrcodeScannedListener = callback
    },
    loading (state, payload) {
      state.loadingCount += payload
    },
    ChangLoading (state, payload) {
      state.loading = payload
    },
    backupRoute (state, payload) {
      if (payload) {
        state.originalRoute = payload
        // console && console.log('backupRoute', payload.path)
      }
    },
    logout (state) {
      state.originalRoute = null
    }
  },
  actions: {
    toLoading ({dispatch, commit}, val) {
      commit('ChangLoading', val)
    }
  }
})
Vue.store = store

Vue.prototype.formatNumber = function (value, precision, separator, point) {
  if (value === undefined || value === null) {
    return null
  }
  // 判断是否为数字
  if (!isNaN(parseFloat(value)) && isFinite(value)) {
    // 把类似 .5, 5. 之类的数据转化成0.5, 5, 为数据精度处理做准,
    // 至于为什么不在判断中直接写 if (!isNaN(num = parseFloat(num)) && isFinite(num))
    // 是因为parseFloat有一个奇怪的精度问题, 比如 parseFloat(12312312.1234567119)的值变成了 12312312.123456713
    let num = Number(value)
    // 处理小数点位数
    num = num.toFixed(precision || 0).toString()
    // 分离数字的小数部分和整数部分
    let parts = num.split(point || '.')
    // 整数部分加[separator]分隔, 借用一个著名的正则表达式
    parts[0] = parts[0].toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1' + (separator || ','))

    return parts.join(point || '.')
  }
  return NaN
}

export default store
