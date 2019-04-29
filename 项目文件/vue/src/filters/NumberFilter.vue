<script>
  import Vue from 'vue'

  Vue.filter('number', function (value, precision, separator, point) {
    return Vue.prototype.formatNumber(value, precision, separator, point)
  })
</script>
