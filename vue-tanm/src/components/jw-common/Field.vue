<script>
  import Vue from 'vue'
  export default {
    name: 'jwField',
    functional: true,
    props: {
      value: {
        type: String | Number,
        default: null
      },
      options: {
        type: Object,
        default () {
          return {
            tag: 'el-input',
            data: {},
            children: []
          }
        }
      }
    },
    render (createElement, context) {
      let options = Vue.lodash.merge({}, {data: context.data}, context.props.options)
      // console.log('jwField render', context, options)
      return createElement(options.tag, options.data, options.children)
    }
  }
</script>
