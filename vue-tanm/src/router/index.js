import Vue from 'vue'
import Router from 'vue-router'
import views from 'views'
import adviews from 'adviews'

// path必须唯一
function getViews (arr) {
  arr.forEach(r => {
    if (r.children) {
      getViews(r.children)
    }
    for (var i = 0; i < _arr.length; i++) {
      if (r.path === _arr[i].path) {
        r.component = _arr[i].component
        break
      }
    }
  })
}
var _arr = []
function deep (arr) {
  arr.map(r => {
    if (r.children) {
      if (r.component) {
        _arr.push(r)
      }
      deep(r.children)
    } else {
      _arr.push(r)
    }
  })
}
deep(adviews)
getViews(views)
Vue.use(Router)
const router = new Router({
  routes: views
})
// 注册全局导航钩子
router.beforeEach((to, from, next) => {
  // console && console.info('Router beforeEach:', from.path, '=>', to.path)
  next()
})
router.afterEach((route) => {
  // console && console.info('Router afterEach', 'route', route)
  let menu = Vue.store.getters.findMenuByRoutePath(route.path)
  if (menu) {
    Vue.store.dispatch('openTab', {path: menu.route_path, params: menu, status: 'afterRoute'})
  }
})
Vue.router = router

export default router
