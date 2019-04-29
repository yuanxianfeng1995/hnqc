/**
 * Install plugin.
 */
const plugin = function (Vue) {
  if (plugin.installed) {
    return
  }

  console && console.log('window.cfg', window.cfg)
  Vue.prototype.$cfg = window.cfg
  Vue.cfg = window.cfg
}

if (typeof window !== 'undefined' && window.Vue) {
  window.Vue.use(plugin)
}

export default plugin
