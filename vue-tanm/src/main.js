// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import 'babel-polyfill'
import Vue from "vue";
import App from "./App";
import VueResource from 'vue-resource'
Vue.use(VueResource)



import moment from 'moment'
Object.defineProperty(Vue.prototype, '$moment', {value: moment})
Vue.moment = moment

import lodash from 'lodash'
Object.defineProperty(Vue.prototype, '$lodash', {value: lodash})
Vue.lodash = lodash

import VueLocalStorage from 'vue-localstorage'
Vue.use(VueLocalStorage)

import VueCookie from 'vue-cookie'
Vue.use(VueCookie)

import 'font-awesome-webpack'

import ElementUI from 'element-ui'
Vue.use(ElementUI)
import 'element-ui/lib/theme-default/index.css'
// import 'element-ui/lib/theme-defaut/base.css' // fade/zoom 等
import CollapseTransition from 'element-ui/lib/transitions/collapse-transition' // collapse 展开折叠
Vue.component(CollapseTransition.name, CollapseTransition)


import VueCfg from './plugins/vue-cfg'
Vue.use(VueCfg)

import VueClipboard from './plugins/vue-clipboard'
Vue.use(VueClipboard)


import VueCropper from 'vue-cropper'
Vue.use(VueCropper)

// import {AgGridVue} from 'ag-grid-vue'
// Vue.component('ag-grid', AgGridVue) // 注册全局组件

import 'ag-grid-community/dist/styles/ag-grid.css'
// import 'ag-grid/dist/styles/theme-blue.css'
// import 'ag-grid/dist/styles/theme-bootstrap.css'
// import 'ag-grid/dist/styles/theme-dark.css'
import 'ag-grid-community/dist/styles/ag-theme-fresh.css'
// import 'ag-grid/dist/styles/theme-material.css'
// import 'ag-grid-enterprise/main' // need if you use ag-grid enterprise features
// import VueGrid from './plugins/vue-grid'

import VueJw from './plugins/vue-jw'
Vue.use(VueJw)

import './directives'
import './filters'
import './components'
import 'assets/iconfont/iconfont.css'
import 'assets/css/overallSituation.css'
import 'assets/css/dialog.css'
import 'assets/css/form.css'
import 'assets/css/grid.css'

window.eventBus = new Vue() // 注册全局事件对象

import store from './store'
import router from './router'
Vue.config.productionTip = false;
/* eslint-disable no-new */
new Vue({
  el: "#app",
  store,
  router,
  components: { App },
  template: "<App/>"
});
