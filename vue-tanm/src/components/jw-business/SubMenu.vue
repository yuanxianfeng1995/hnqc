<template>
  <el-submenu :index="menu.id">
    <div slot="title" class="jw-menu-item-text">
      <i :class="menu.icon || 'iconfont icon-renwuguanli'" style="min-width:20px;"></i>
      <span slot="title">{{menu.name}}</span>
    </div>
    <template v-for="(submenu, index) in menu.children">
      <jw-submenu :menu="submenu" v-if="submenu.type === 1"></jw-submenu>
      <jw-menu-item :menu="submenu" v-else-if="submenu.type === 2"></jw-menu-item>
    </template>
  </el-submenu>
</template>


<script>
  export default {
    name: 'jwSubmenu',
    props: {
      menu: {
        type: Object,
        default: function () {
          return {}
        }
      }
    }
  }
</script>
