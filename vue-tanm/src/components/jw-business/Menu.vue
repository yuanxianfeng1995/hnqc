<template>
  <el-menu :default-active="defaultActive" :router="router" :collapse="collapse" @select="onSelectMenu" :theme="theme">
    <template v-for="(menu, index) in menuList">
      <jw-submenu :menu="menu" v-if="menu.type === 1"></jw-submenu>
      <jw-menu-item :menu="menu" v-else-if="menu.type === 2"></jw-menu-item>
    </template>
  </el-menu>
</template>


<script>
  export default {
    name: 'jwMenu',
    props: {
      router: {
        type: Boolean,
        default: false
      },
      collapse: {
        type: Boolean,
        default: false
      },
      theme: {
        type: String,
        default: 'dark'
      },
      defaultActive: {
        type: String,
        default: 'AdminHome'
      },
      menuList: {
        type: Array,
        default: function () {
          return []
        }
      }
    },
    methods: {
      onSelectMenu (index, indexPath, menuItem) {
        this.$emit('select', index, indexPath, menuItem.route)
      }
    }
  }
</script>
