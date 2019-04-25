<style>
  .contentDiv {
    display: flex;
  }

  .input .el-input__inner {
    background-color: rgba(251, 244, 244, 0.1) !important;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    border-radius: 4px;
    border: 0px solid #bfcbd9;
    box-sizing: border-box;
    color: #1f2d3d;
    font-size: inherit;
    height: 36px;
    line-height: 1;
    outline: 0;
    padding: 3px 10px;
  }
</style>
<template>
  <div class="contentDiv">
    <el-input class="input" v-model="model" type="text"
           :size="size"
           :clearable="clearable"
           :disabled="disabled"
           :placeholder="placeholder"
              @change="onChange">
    </el-input>
    <div clsaa="identy-button">
    <el-button size="mini" style="color: #20a0ff;width: 102px;background: none; border: 1px solid rgb(32, 160, 255)"
               :disabled="disabled || countdownTime !== countdown"
               @click="onSendCode">
      {{appendText}}
    </el-button>
    </div>
  </div>
</template>


<script>
  export default {
    name: 'jwIdentifyingCode',
    props: {
      value: {type: String, default: null},
      countdown: {type: Number, default: 60},
      type: {type: String, default: 'mobile'}, // mobile, email
      prepend: {type: Boolean, default: true},
      disabled: {type: Boolean, default: false},
      clearable: {type: Boolean, default: false},
      size: {type: String, default: null}
    },
    data () {
      return {
        model: null,
        countdownTime: this.countdown,
        appendText: '获取验证码',
        placeholder: '请先获取验证码'
      }
    },
    watch: {
      value (newval, oldval) {
        if (newval || newval === 0) {
          this.model = newval + ''
        } else {
          this.model = null
        }
      }
    },
    mounted () {
      let countdownTime = this.$localStorage.get('jwVerificationCodeTime', null, Number)
      if (countdownTime) {
        this.countdownTime = countdownTime
        this.timeout = setTimeout(this._timer, 100)
      }
    },
    methods: {
      _timer () {
        this.timeout && clearTimeout(this.timeout)
        if (this.countdownTime > 0) {
          this.countdownTime--
          this.appendText = this.countdownTime + '秒后重试'
          this.$localStorage.set('jwVerificationCodeTime', this.countdownTime)
          this.timeout = setTimeout(this._timer, 1000)
        } else {
          this.countdownTime = this.countdown
          this.appendText = '获取验证码'
          this.$localStorage.remove('jwVerificationCodeTime')
        }
      },
      prepareCountdown () {
        this.countdownTime--
        this.appendText = '处理中……'
      },
      startCountdown (countdown) {
        this.countdownTime++
        this.placeholder = this.type === 'email' ? '请填写邮箱中的验证码' : '请填写短信中的验证码'
        this.$message({
          message: this.type === 'email' ? '请在邮箱中查收验证码。' : '请在手机上查收验证码。',
          type: 'success'
        })
        this._timer()
      },
      endCountdown () {
        this.countdownTime = 0
        this._timer()
      },
      onSendCode (value) {
        this.$emit('on-get-code', this)
      },
      sendCode (url, params) {
        this.prepareCountdown()
        this.$localStorage.set('isPhoneLogin', this.isPhoneLogin)
        this.$http.post(url, params, {showSuccessMessage: false}).then((response) => {
          if (response.body.success) {
            this.startCountdown()
          }
        }).catch(response => {
          this.endCountdown()
          let responseBody = response.body || {}
          this.$alert(responseBody.message || '发送验证码失败，请联系系统技术支持人员！', '错误', {
            confirmButtonText: '关闭',
            type: 'error'
          })
        })
      },
      onChange (value) {
        this.$emit('input', this.model)
        this.$emit('on-change', this.model)
      }
    }
  }
</script>
