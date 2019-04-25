<script>
  export default {
    name: 'jwFormItem',
    functional: true,
    props: {
      tag: {type: String, default: 'el-input'},
      col: {type: String | Number, default: 1},
      labelMiddle: {type: Boolean, default: false},
      labelHtml: {type: Boolean, default: false}
    },
    render (createElement, context) {
      // console.log('render context:', context)
      let data = {
        staticClass: 'jw-field jw-field-' + (context.props.col || 1),
        class: [],
        // style: {},
        // attrs: {},
        props: ['prop', 'label', 'rules'].reduce((props, key) => {
          props[key] = context.data.attrs[key]
          return props
        }, {})
      }
      if (context.props.labelMiddle) {
        data.staticClass += ' jw-middle-label'
      }
      if (context.data.staticClass) {
        data.staticClass += ' ' + context.data.staticClass
        delete context.data.staticClass
      }
      if (context.data.class) {
        data.class.push(context.data.class)
        delete context.data.class
      }
      if (!data.props.rules || data.props.rules.length === 0) {
        delete data.props.rules
      }

      let arr = ['prop', 'label', 'labelWidth', 'required', 'rules', 'error', 'showMessage']
      arr.forEach(p => delete context.data.attrs[p])

      let children = []
      if (context.props.labelHtml) {
        children.push(createElement('div', {
          slot: 'label',
          style: {display: 'inline'},
          domProps: {innerHTML: data.props.label}
        }))
        delete data.props.label
      }
      children.push(createElement(context.props.tag || 'el-input', context.data, context.children))

      return createElement('el-form-item', data, children)
    }
  }
</script>
