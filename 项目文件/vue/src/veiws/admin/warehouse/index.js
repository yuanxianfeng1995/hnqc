export default [{
  path: 'outbound',
  component: r => require.ensure([], () => r(require('./outbound/View')), 'warehouse-outbound')
}, {
  path: 'billentry',
  component: r => require.ensure([], () => r(require('./billentry/View')), 'warehouse-billentry')
}, {
  path: 'commodity',
  component: r => require.ensure([], () => r(require('./commodity/View')), 'warehouse-commodity')
}]
