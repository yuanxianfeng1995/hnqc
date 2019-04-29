<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .jw-form-body {
    position: static;
  }

  .el-form-login {
    margin-top: 30px;
    display: flex;
    justify-content: center;
  }

  .login-form {
    box-shadow: 1px 1px 5px #333;
    position: absolute;
    top: 8%;
    left: 0;
    right: 0;
    margin: auto;
    height: 450px;
    width: 560px;
    background-color: rgba(248, 248, 248, 0.4);
    border-radius: 8px;
    overflow: hidden
  }

  .el-form-item .el-form-item {
    width: 90%;
  }

  .login-logo {
    height: 44px;
    text-align: center;
    margin-top: 30px;
  }

  .el-content {
    width: 53%;
  }

  .el-item {
    margin-top: 20px;
    border-bottom: solid 1px;
    display: flex;
    position: relative;
  }

  .top-right-corner {
    position: absolute;
    right: 0;
    top: 0;
  }

  .triangle-topright {
    width: 0;
    height: 0;
    border-top: 100px solid #C9EBF2;
    border-left: 100px solid transparent;
    opacity: 0.8;
  }

  .login-person {
    margin: 8px;
    width: 50px;
  }

  .login-phone {
    margin: 10px;
    width: 50px;
  }

  .forget-password {
    float: right;
    margin: 5px 0px 5px 0px;
  }

  .forget-password:hover {
    color: dodgerblue;
    cursor: pointer;
  }

  .el-button + .el-button {
    margin-top: 20px;
    margin-left: 0px;
  }

  .buttonClass {
    text-align: center;
    background: none;
    padding-top: 20px;
  }

  .login-icon {
    position: absolute;
    right: 0px;
    top: 8px;
  }

  .login-footer {
    position: fixed;
    bottom: 0px;
    width: 100%;
    text-align: center;
    color: #fff;
    font-size: 14px;
  }

  .login-footer p {
    text-shadow: 3px 3px 8px #31221e;
    margin: 0px auto;
    font-family: MicrosoftYaHeiUI
  }

  input:-webkit-autofill {
    box-shadow: 0 0 0px 1000px white inset !important;
  }
</style>
<style>
  .login-input .el-input__inner {
    border: 0px solid #bfcbd9;
    background-color: rgba(251, 244, 244, 0.1)
  }
</style>


<template>
  <div :style="{'width':layout.window.width + 'px', 'height': layout.window.height + 'px'}">
    <div class="jw-form jw-form-mini login-form"
         :style="layout.window.width >= 768 ? {} : {'width': '100%'}">
      <div class="jw-form-body">
        <div class="login-logo">
          <img style="width: 60%" :src="systemLogoUrl"/>
        </div>
        <el-form ref="form" :model="entity" :rules="rules" class="el-form-login">
          <el-form-item class="jw-field jw-field-1 el-content" v-if="isPhoneLogin">
            <div class="el-item">
              <img src="~assets/img/icon-phone.png" style="min-width: 12px;"/>
              <el-form-item prop="telephone">
                <el-input class="login-input" v-model="entity.telephone" type="text"
                          placeholder="请输入手机号" @focus="inputFocus" @blur="inputBlur"></el-input>
              </el-form-item>
            </div>
            <div class="el-item">
              <img src="~assets/img/icon-check.png" style="min-width: 12px;"/>
              <el-form-item prop="code">
                <jw-identifying-code v-model="entity.code"
                                     @on-get-code="onGetCode" @keyup.enter.native="onSubmit">
                </jw-identifying-code>
              </el-form-item>
            </div>
            <p class="forget-password" @click="$router.push({name:'forgetPassword'})">忘记密码</p>
            <div class="buttonClass">
              <el-button type="primary" style="width: 100%" @click="onSubmit">快捷登录</el-button>
              <el-button :plain="true" style="width: 100%" @click="$router.push({name:'registerUser'})"
                         v-if="selfRegistrationEnabled">注册
              </el-button>
            </div>
          </el-form-item>

          <el-form-item class="jw-field jw-field-1 el-content" v-if="!isPhoneLogin">
            <div class="el-item">
              <img src="~assets/img/icon-person.png" style="min-width: 12px;"/>
              <el-form-item prop="account">
                <el-input class="login-input" prop="account" v-model="entity.account" type="text"
                          placeholder="请输入用户名" @focus="inputFocus" @blur="inputBlur"></el-input>
                <input style="display: none"/>
              </el-form-item>
            </div>
            <div class="el-item el-item-icon">
              <img src="~assets/img/icon-password.png" style="min-width: 12px;"/>
              <el-form-item prop="password">
                <input style="display: none"/>
                <el-input id="pawwsord" class="login-input" v-model="entity.password" type="password"
                          placeholder="请输入密码"
                          @keyup.enter.native="onSubmit" @focus="inputFocus" @blur="inputBlur"></el-input>
              </el-form-item>
              <i class="fa fa-times-circle login-icon" style="font-size: 16px" aria-hidden="true"
                 v-if="entity.password != ''"
                 @click="clearPawwsord()"></i>
            </div>
            <p class="forget-password" @click="$router.push({name:'forgetPassword'})">忘记密码</p>
            <div clss="buttonClass">
              <el-button type="primary" style="width: 100%" @click="onSubmit">登录</el-button>
              <el-button :plain="true" style="width: 100%" @click="$router.push({name:'registerUser'})"
                         v-if="selfRegistrationEnabled">注册
              </el-button>
            </div>
          </el-form-item>
          <div style="padding: 20px 0px;position: absolute;bottom: 0;" v-if="errorMessage">
            <el-alert type="error" :closable="false" :title="errorMessage"></el-alert>
          </div>
        </el-form>
        <div class="top-right-corner triangle-topright" v-show="phoneLoginEnabled"></div>
        <img class="top-right-corner login-person" src="~assets/img/login-person.png" @click="isPhoneLogin = false"
             v-show="isPhoneLogin && phoneLoginEnabled"/>
        <img class="top-right-corner login-phone" src="~assets/img/login-phone.png" @click="isPhoneLogin = true"
             v-show="!isPhoneLogin && phoneLoginEnabled"/>
      </div>
    </div>
    <div class="login-footer">
      <p v-if="systemCopyright">版板所有：{{systemCopyright}}</p>
      <p v-if="systemAddress">地址：{{systemAddress}}</p>
      <p>Copyright ©{{new Date().getFullYear()}} All Rights Reserved <span v-if="systemRecordation">备案号：{{systemRecordation}}</span>
        <span v-if="systemSupport">技术支持：{{systemSupport}} 网站管理</span></p>
    </div>
    <div style="position: absolute;left: 80px;bottom: 60px;background-color: rgba(50, 50, 50, 0.4);"
         v-if="androidUrl">
      <a style="cursor: pointer;" @click="onDownload">
        <qrcode :value="androidUrl" :options="{size: 120}"></qrcode>
        <div style="text-align: center;">Android</div>
      </a>
    </div>
  </div>

  <!-- <jw-foot slot="bottom" v-if="layout.window.width >= 768"></jw-foot>-->
</template>


<script>
  import VueQrcode from '@xkeshi/vue-qrcode'

  export default {
    name: 'adminLoginView',
    components: {
      qrcode: VueQrcode
    },
    data () {
      return {
        androidUrl: null,
        androidFileName: null,
        errorMessage: null,
        isPhoneLogin: this.$localStorage.get('isPhoneLogin', false, Boolean),
        entity: {
          account: this.$localStorage.get('account', null),
          password: process.env.NODE_ENV === 'development' ? this.$localStorage.get('password', '12345678') : null,
          telephone: this.$localStorage.get('telephone', null),
          code: null
        },
        rules: {
          account: [
            {required: true, message: '请输入账号', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ],
          telephone: [
            {required: true, message: '请输入手机号', trigger: 'blur'}
          ],
          code: [
            {required: true, message: '请输入验证码', trigger: 'blur'}
          ]
        }
      }
    },
    computed: {
      systemLogoUrl () {
        return this.$store.state.settings.systemLogoUrl
      },
      systemAddress () {
        return this.$store.state.settings.systemAddress
      },
      systemCopyright () {
        return this.$store.state.settings.systemCopyright
      },
      systemRecordation () {
        return this.$store.state.settings.systemRecordation
      },
      systemSupport () {
        return this.$store.state.settings.systemSupport
      },
      casLoginEnabled () {
        return this.$store.state.settings.casLoginEnabled === 101
      },
      phoneLoginEnabled () {
        return this.$store.state.settings.phoneLoginEnabled === 101
      },
      selfRegistrationEnabled () {
        return this.$store.state.settings.selfRegistrationEnabled === 101
      },
      originalRoute () {
        return this.$store.state.originalRoute ? this.$store.state.originalRoute : {path: '/'}
      },
      layout () {
        return this.$store.state.layout
      },
      appCode () {
        return this.$store.state.settings.systemId || 'JianyanZhihui'
      }
    },
    watch: {
      appCode (newVal, oldVal) {
        this.updateAndroidUrl(newVal)
      }
    },
    created () {
      this.updateAndroidUrl = this.$lodash.debounce(this._updateAndroidUrl, 500)

      if (this.$store.state.layout.window.width < 768) {
        this.isPhoneLogin = true
      }
      if (!this.phoneLoginEnabled) {
        this.isPhoneLogin = false
      }

      this.$nextTick(() => {
        this.updateAndroidUrl()
        document.getElementById('pawwsord').autocomplete = 'new-password'
      })
    },
    methods: {
      _updateAndroidUrl (appCode) {
        // 获取APP的版本信息，用于显示下载的二维码
        this.androidUrl = null
        this.$http.get('/api/platform/data/version', {
          params: {
            app_code: appCode || this.appCode,
            is_android: 101
          }
        }).then((response) => {
          let version = response.body.data || {}
          this.androidUrl = version.android_url || ''
          let idx = this.androidUrl.indexOf('-internal.aliyuncs.com')
          if (idx > 0) { // 走OSS内网地址，则通过Nginx反向代理下载
            this.androidUrl = location.protocol + '//' + location.host + '/oss' + this.androidUrl.substring(idx + '-internal.aliyuncs.com'.length)
          }

          this.androidFileName = 'jyzh-' + version.no + '.apk'
          if (!this.androidUrl && version.android_attachment_id && version.android_attachment_id.length > 0) {
            this.androidUrl = this.$cfg.apiUrl + '/api/platform/data/version/' + version.id + '/download'
          }
        })
      },
      inputFocus (event) {
        for (let index in event.path) {
          if (event.path[index] && event.path[index].className && event.path[index].className.indexOf('el-item') > -1) {
            event.path[index].style.borderBottom = 'solid 1px #20a0ff'
          }
        }
      },
      inputBlur (event) {
        for (let index in event.path) {
          if (event.path[index] && event.path[index].className && event.path[index].className.indexOf('el-item') > -1) {
            event.path[index].style.borderBottom = 'solid 1px black'
          }
        }
      },
      clearPawwsord () {
        this.entity.password = ''
      },
      passwordChange (e) {
        e.target.type = 'password'
      },
      onDownload () {
        this.$jw.download(this.androidUrl, {name: this.androidFileName})
      },
      onGetCode (vcComponent) {
        this.$refs['form'].validateField('telephone', (errorMsg) => {
          if (errorMsg) {
            return
          }
          vcComponent.sendCode('/login/verification/code?' + 'telephone=' + this.entity.telephone + '&isCasLogin=' + this.$store.state.isCasLogin, {})
        })
      },
      onReset () {
        this.$refs['form'].resetFields()
      },
      onSubmit () {
        this.$refs['form'].validate((valid) => {
          if (!valid) {
            return false
          }

          let loading = this.$loading({text: '处理中……'})
          this.errorMessage = null

          this.$localStorage.set('isPhoneLogin', this.isPhoneLogin || false)
          this.$localStorage.set('account', this.entity.account || '')
          this.$localStorage.set('telephone', this.entity.telephone || '')

          let isCasLogin = this.casLoginEnabled && this.$store.state.isCasLogin
          let isPhoneLogin = this.phoneLoginEnabled && this.isPhoneLogin
          let authorization = this.isPhoneLogin ? (this.entity.telephone + ':' + this.entity.code) : (this.entity.account + ':' + this.entity.password)
          authorization = 'Basic ' + btoa(encodeURIComponent(authorization))
          this.$http.post('/token', {}, {
            showSuccessMessage: false,
            headers: {
              'IS-CAS-LOGIN': isCasLogin + '',
              'IS-PHONE-LOGIN': isPhoneLogin + '',
              'Authorization': authorization
            }
          }).then((response) => {
            loading.close()
            if (response.body.success) {
              this.$store.dispatch('login')
            } else {
              this.errorMessage = response.body.message
            }
          }).catch((e) => {
            console && console.error(e) // 打印一下错误
            this.errorMessage = this.isPhoneLogin ? '手机号、验证码错误，请重新输入！' : '用户名、密码错误，请重新输入！'
            loading.close()
          })

          return true
        })
      }
    }
  }
</script>
