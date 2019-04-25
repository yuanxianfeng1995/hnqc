<style scoped>
</style>

<template>
  <el-row style="background-color: #2E2C32;color: #bfcbd9;">
    <el-col :span="24" style="padding: 5px 20px;">
      <div style="text-align: center;">
        <div style="display: inline-block; margin-bottom: 0; line-height: 22px; height: 22px;">
          {{layout.window.width}} &times; {{layout.window.height}}
        </div>
        <div class="pull-right" v-if="$store.state.devMode">
          <el-checkbox style="color: #bfcbd9;" v-model="isCasLogin" @change="onCasLoginChanged" v-if="casLoginEnabled">启用单点登录</el-checkbox>
          <el-checkbox style="color: #bfcbd9;" v-model="isMock" @change="onMockChanged">模拟后台调用</el-checkbox>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    name: 'jwFoot',
    data () {
      return {
        isMock: this.$localStorage.get('isMock', false, Boolean),
        isCasLogin: this.$localStorage.get('isCasLogin', false, Boolean)
      }
    },
    computed: {
      casLoginEnabled () {
        return this.$store.state.settings.casLoginEnabled === 101
      },
      layout () {
        return this.$store.state.layout
      }
    },
    created () {
      if (!this.casLoginEnabled) {
        this.isCasLogin = false
        this.onCasLoginChanged()
      }
    },
    methods: {
      onMockChanged () {
        this.$localStorage.set('isMock', this.isMock)
      },
      onCasLoginChanged () {
        this.$localStorage.set('isCasLogin', this.isCasLogin)
      }
    }
  }
</script>
