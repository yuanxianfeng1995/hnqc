export default [{
  path: 'information',
  component: r => require.ensure([], () => r(require('./information/View')), 'customer')
}, {
  path: 'maintain',
  component: r => require.ensure([], () => r(require('./maintain/View')), 'customer-maintain')
}]
