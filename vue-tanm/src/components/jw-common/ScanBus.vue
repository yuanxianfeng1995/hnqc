<template></template>
<script>
  // import Vue from 'vue'
  export default {
    name: 'scanBus',
    data () {
      return {
        events: {
          '1': 'equipmentScan'
        }
      }
    },
    created () {
      let vm = this
      document.onkeydown = function (e) {
        let nextCode = e.which
        let nextTime = new Date().getTime()
        if (vm.lastCode == null && vm.lastTime == null && e.key && e.key.length === 1) {
          vm.code = e.key
          // 根据时间间隔获取扫描信息
        } else if (vm.lastCode != null && vm.lastTime != null && nextTime - vm.lastTime <= 200 && e.key.length === 1) {
          vm.code += e.key
        } else if (vm.lastCode != null && vm.lastTime != null && nextTime - vm.lastTime > 1000) {
          vm.code = ''
          vm.lastCode = null
          vm.lastTime = null
        }
        vm.lastCode = nextCode
        vm.lastTime = nextTime
        if (e.which === 13 && vm.code.length > 0) {
          let locationParams = ''
          try {
            locationParams = vm.code.split('?')[1].split('&')
          } catch (e) {
            console.warn('二维码不合法')
            return
          }
          let params = []
          locationParams.forEach((item, idx, list) => {
            let param = item.split('=')
            params[param[0]] = param[1]
          })
          let scanCallBack = vm.$store.state.qrcodeScannedListener
          if (scanCallBack && typeof (scanCallBack) === 'function') {
            try {
              scanCallBack(params)
            } catch (e) {
              console.error(e)
            }
          }
        }
      }
    },
    methods: {
    }
  }
</script>
