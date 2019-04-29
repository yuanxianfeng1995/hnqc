<script type="text/ecmascript-6">
  import Vue from 'vue'

  Vue.filter('timestamp', function (value, format) {
    let timestamp = value
    if (typeof value === 'number') {
      timestamp = parseInt(value)
    }

    if (!timestamp) {
      return ''
    } else if (format) {
      return Vue.moment(timestamp).format(format)
    } else {
      return Vue.moment(timestamp).format('YYYY-MM-DD HH:mm:ss')
    }
  })
</script>
