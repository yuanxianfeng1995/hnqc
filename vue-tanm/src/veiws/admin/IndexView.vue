<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .jw-menu {
    color: #bfcbd9;
    background-color: #2E2C32;
  }

  .jw-top-menu {

  }

  .jw-side-menu {

  }

  .jw-menu-switch {
    font-size: 24px;
    padding: 5px;
    color: #bfcbd9;
  }

  .jw-menu-header {
    padding: 5px 20px;
    border-bottom: 1px solid #5d5d5d;
    height: 42px;
    font-size: 16px;
  }

  .jw-top-menu-header {
    text-align: center;
  }

  .jw-side-menu-header {
  }

  .jw-menu-body {
  }

  .jw-top-menu-body {
    border-radius: 0;
    z-index: 999999;
  }

  .jw-side-menu-body {
    height:100%;
    border-radius: 0;
  }
  .jw-side-menu-body.el-menu--collapse {
    overflow: unset;
  }

  .jw-view-tab-body {
    overflow: auto;
  }

  .jw-head-items {
    display: flex;
    justify-content: flex-end;
    padding-right: 20px;
    height: 100%;
    align-items: center;
    border-left: 1px solid #bdb3b3;
  }

  .tabMenuClass {
    border-top: 0px solid !important;
    border-right: 1px solid #c8c1c1 !important;
  }
  .jw-menu-item-text a{
    color: #bfcbd9;
    text-decoration: none;
  }
</style>


<template>
  <div style="height: 100%;" :style="{'background-color': '#2E2C32'}">


    <jw-layout :left-width="collapseMenu ? 64 : 240" class="clearfix box">
      <!-- 顶部 -->
      <jw-head slot="top"></jw-head>

      <!-- 中部 -->
      <template>
        <!-- 左部 -->
        <div slot="left" style="height: 100%">
          <!--<h2 style="margin: 10px 0px;text-align: center;color: #fff;">宏能汽车</h2>-->
          <div id="sideMenu" class="ad-menu ad-side-menu" style="height: 100%;background-color: #2e2c32;">
            <el-menu class="jw-menu-body jw-side-menu-body jw-menu-item-text"  :default-openeds="['1-1','1-2']" theme="dark" :collapse="collapseMenu">
                <el-submenu index="1-1">
                  <div  slot="title" class="jw-menu-item-text">
                    <i class="iconfont icon-yiqishebeiliuzhuan"></i>
                    <span slot="title">
                      仓库管理
                    </span>
                  </div>
                  <el-menu-item index="1-1-1">
                    <div slot="title" class="jw-menu-item-text">
                      <i class="iconfont icon-yiqishebeiliuzhuan" ></i>
                      <span slot="title">
                        <router-link slot="title" :to="'/admin/warehouse/billentry'">入库单</router-link>
                      </span>
                    </div>
                  </el-menu-item>
                  <el-menu-item index="1-1-2">
                    <div slot="title" class="jw-menu-item-text">
                      <i class="iconfont icon-yiqishebeiliuzhuan" ></i>
                      <span slot="title">
                        <router-link slot="title" :to="'/admin/warehouse/commodity'">商品信息</router-link>
                      </span>
                    </div>
                  </el-menu-item>
                  <el-menu-item index="1-1-3">
                    <div slot="title" class="jw-menu-item-text">
                      <i class="iconfont icon-yiqishebeiliuzhuan" ></i>
                      <span slot="title">
                        <router-link slot="title" :to="'/admin/warehouse/outbound'">出库单</router-link>
                      </span>
                    </div>
                  </el-menu-item>
                </el-submenu>
              <el-submenu index="1-2">
                <div  slot="title" class="jw-menu-item-text">
                  <i class="iconfont icon-yiqishebeiliuzhuan"></i>
                  <span slot="title">
                      客户管理
                    </span>
                </div>
                <el-menu-item index="1-2-1">
                  <div slot="title" class="jw-menu-item-text">
                    <i class="iconfont icon-yiqishebeiliuzhuan" ></i>
                    <span slot="title">
                        <router-link slot="title" :to="'/admin/customer/information'">客户信息</router-link>
                      </span>
                  </div>
                </el-menu-item>
                <!--<el-menu-item index="1-2-2">-->
                  <!--<div slot="title" class="jw-menu-item-text">-->
                    <!--<i class="iconfont icon-yiqishebeiliuzhuan" ></i>-->
                    <!--<span slot="title">-->
                        <!--<router-link slot="title" :to="'/admin/customer/maintain'">保养信息</router-link>-->
                      <!--</span>-->
                  <!--</div>-->
                <!--</el-menu-item>-->
              </el-submenu>
            </el-menu>
          </div>
        </div>

        <!-- 右部 -->
        <div slot="center" style="background-color: #fff;">
          <div id="viewTab" class="ad-view-tab">
            <div class="ad-view-tab-body">
              <keep-alive>
                <router-view ref="routerView"></router-view>
              </keep-alive>
            </div>
          </div>
        </div>
      </template>

      <!-- 底部 -->
      <!--<jw-foot slot="bottom"></jw-foot>-->
    </jw-layout>
  </div>
</template>


<script>
  import Vue from 'vue'

  export default {
    name: 'jwIndex',
    data() {
      return {
        showMenu: false,
        keysListsId: [],
        transferIndex: 0,
        targetTab: null,
        contextMenuData: {
          className: 'tabMenuClass',
          menuName: 'deleteTab',
          axis: {
            x: null,
            y: null
          }
        }
      }
    },
    computed: {
      menuList() {
        return this.$store.state.menu.menuList
      },
      collapseMenu() {
        return this.$store.state.menu.collapseMenu === 'true'
      }
    },
    created() {
    }
  }
</script>
