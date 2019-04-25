import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

Vue.http.interceptors.push(function (request, next) {
  // 在请求之前可以进行一些预处理和配置
  let vm = this || {} // 此处this为请求所在页面的Vue实例
  vm.loading = true // 正在加载
  if (Vue.store && request.showLoading !== false) {
    Vue.store.commit('loading', 1)
  }
  vm.errorMessage = null

  let isMock = Vue.localStorage.get('isMock', false, Boolean)
  // modify request
  if (isMock) {
    request.method = 'GET'
    request.url = 'mock/' + request.url + '.json'
    console && console.debug('mock request', request)
  } else {
    if (!(request.url.startsWith('http') || request.url.startsWith('blob:http'))) {
      // request.url = Vue.cfg.apiUrl + (request.url.startsWith('/') ? '' : '/') + request.url
      request.url = Vue.cfg.apiUrl + request.url
    }
    Vue.http.headers.common['X-REST-TOKEN'] = Vue.cookie.get('X-REST-TOKEN')
  }

  // continue to next interceptor
  next((response) => { // 在响应之后传给then之前对response进行修改和逻辑判断。对于token是否已过期的判断，就添加在此处，页面中任何一次http请求都会先调用此处方法
    vm.loading = false // 加载结束
    if (Vue.store && request.showLoading !== false) {
      Vue.store.commit('loading', -1)
    }
    vm.errorMessage = null

    if (isMock) {
      console && console.debug('mock response', response)
    } else {
      var token = response.headers.get('X-REST-TOKEN') // 登录的时候会返回token
      if (token) {
        Vue.cookie.set('X-REST-TOKEN', token)
      }
    }

    if (response.status === 200) {
      if (response.body.success === true) {
        if (request.showSuccessMessage !== false && (request.method === 'POST' || request.method === 'PUT' || request.method === 'DELETE')) {
          Vue.prototype.$message({type: 'success', duration: 1500, message: response.body.message})
        }
      }
    } else if (response.status === 201) {
      if (request.showSuccessMessage !== false) {
        Vue.prototype.$message({type: 'success', duration: 1500, message: response.body.message})
      }
    } else if (response.status === 400) {
      vm.errorMessage = response.body.message
    } else if (response.status === 401) {
      if (vm.$route && vm.$route.path === '/admin/login') {
        console && console.error('账号密码错误，请重新输入！')
      } else {
        console && console.warn('请先登录验证！')
        Vue.store.dispatch('toLogin', vm.$route)
      }
    } else if (response.status === 403) {
      vm.errorMessage = response.body.message || '无访问 [' + request.method + '][' + response.url + '] 的权限，请联系系统管理人员！'
    } else if (response.status === 404) {
      vm.errorMessage = 'URL [' + request.method + '][' + response.url + '] 不存在，请联系系统技术支持人员！'
    } else if (response.status === 500) {
      vm.errorMessage = response.body.message
    } else if (response.status === 504) {
      vm.errorMessage = '请求超时，请稍后重试！'
    } else {
      console && console.error('request', request, 'response', response)
      vm.errorMessage = '访问 [' + request.method + '][' + response.url + '] 失败，请联系系统技术支持人员！'
    }

    if (request.showErrorMessage !== false && vm.errorMessage) {
      Vue.prototype.$alert(vm.errorMessage, '错误', {
        confirmButtonText: '关闭',
        type: 'error'
      })
    }

    return response
  })
})
