import IndexView from './IndexView'
import HomeView from './HomeView'
import LoginView from './LoginView'

import warehouse from './warehouse'
import customer from './customer'
export default [{
  path: '',
  component: IndexView,
  children: [{
    path: '',
    component: r => require.ensure([], () => r(require('./warehouse/outbound/View')), 'warehouse-outbound')
  }, {
    path: 'warehouse',
    component: {
      template: '<router-view></router-view>'
    },
    children: warehouse
  }, {
    path: 'customer',
    component: {
      template: '<router-view></router-view>'
    },
    children: customer
  }]
},
  {
    path: 'login',
    component: LoginView
  }]
