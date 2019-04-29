<style scoped>
  .jw-header {
    background-color: #D6D6D6;
    display: flex;
    border-bottom: 1px solid #bdb3b3;
    position: relative;
  }
  .header-left {
    display: flex;
    justify-content: flex-start;
    flex-grow: 1;
    flex-wrap: wrap;
  }

  .jw-head-title {
    height: 64px;
    padding: 6px 20px;
  }

  .jw-head-title-logo {
    display: inline;
    height: 48px;
    line-height: 48px;
    vertical-align: top;
  }

  .jw-head-title-text {
    display: inline;
    height: 48px;
    line-height: 48px;
    vertical-align: top;
    color: #fff;
    font-size: 22px;
    padding-left: 10px;
  }

  .jw-head-user {
    height: 64px;
  }

  .jw-head-bar {

  }

  .jw-head-items {
    display: flex;
    justify-content: flex-end;
    height: 100%;
    align-items: center;
    padding-right: 20px;
    float: right;
  }

  .jw-head-item {
    padding: 5px 20px 0px 30px;
    line-height: 36px;
    border-left: 1px solid #bdb3b3
  }

  /* small 762 - 1116 */
  @media (max-width: 1116px) {
    .jw-head-title-text {
      font-size: 18px;
    }
  }

  /* mini 0 - 762 */
  @media (max-width: 762px) {
    .jw-head-title-text {
      font-size: 18px;
    }
  }
  @media (max-width:950px) {
    .jw-head-item_box {
     height: 60px;
      position: absolute;
      right: 0;
      top: 50px;
    }

  }
  .icon-geren {
    color:#8BE000;
    font-size: 36px;
  }
  .icon-xiugai1{
    color:#1D87E8;
    font-size: 36px;
  }
  .icon-tuichu {
    color:#FF6E05;
    font-size: 36px;
  }
  .jw-head-title{
    padding-left: 5%;
    color: #333;
  }
</style>

<template>
  <div class="jw-header">
    <div style="width: 100%">
      <div style="min-width: 500px;margin: auto 0;">
        <h2 style="margin: 10px 0px;padding-left: 5%;color: #333;">宏能汽车</h2>
      </div>
    </div>
    <div style="padding: 6px; min-width: 250px" class="jw-head-item_box">
      <div class="jw-head-item">
        <el-button class="head-el-button">
          <i class="iconfont icon-geren" title="我的信息"></i>
        </el-button>
        <el-button class="head-el-button">
          <i class="iconfont icon-xiugai1" title="修改密码"></i>
        </el-button>
        <el-button class="head-el-button">
          <i class="iconfont icon-tuichu" title="退出系统"></i>
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'

  export default {
    name: 'jwHead',
    components: {
    },
    props: {
      showButton: {
        type: Boolean,
        default: false
      }
    },
    data () {
      return {
        collapseMenu: this.$localStorage.get('collapseMenu', false, Boolean),
        userDetailOptions: {
          title: '修改个人信息',
          operation: 'edit',
          subOperation: 'change',
          context: {
            featureComponent: this
          }
        },
        changePasswordOptions: {
          size: 'mini',
          title: '修改密码',
          operation: 'edit',
          context: {
            featureComponent: this
          }
        }
      }
    },
    methods: {
      onCollapseMenu () {
        this.collapseMenu = !this.collapseMenu
        Vue.store.dispatch('collapseMenu', this.collapseMenu)
      }
    }
  }
</script>
