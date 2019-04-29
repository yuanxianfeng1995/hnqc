<style>
  .jw-menu-item-text a{
    color: #bfcbd9;
    text-decoration: none;
  }
</style>
<template>
  <el-menu-item :index="menu.id" :route="{path: menu.route_path, params: menu}">
    <div class="jw-menu-item-text">
      <i :class="menu.icon || 'iconfont icon-yiqishebeiliuzhuan'" style="min-width:20px;"></i>
      <span slot="title">
        <router-link slot="title" :to="menu.route_path || ''">{{menu.name}}</router-link>
      </span>
    </div>
  </el-menu-item>
</template>


<script>
  export default {
    name: 'jwMenuItem',
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
