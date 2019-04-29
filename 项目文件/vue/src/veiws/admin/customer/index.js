export default [{
  path: 'information',
  component: r => require.ensure([], () => r(require('./information/View')), 'customer-information')
}, {
  path: 'maintain',
  component: r => require.ensure([], () => r(require('./maintain/View')), 'customer-maintain')
}, {
  path: 'repair',
  component: r => require.ensure([], () => r(require('./repair/View')), 'customer-repair')
}]
