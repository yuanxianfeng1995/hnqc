<template>
  <div class="vue-contextmenu-listWrapper" :class="'vue-contextmenuName-' + contextMenuData.menuName + ' ' + contextMenuData.className"
       :style="{'width':contextMenuData.width + 'px'}">
    <div v-for="item in contextMenuData.menulists"
         :style="{'width':contextMenuData.width + 'px'}"
         class="context-menu-list"
         :key="item.btnName"
         @click.stop="fnHandler(item)">
      <span class="vue-contextmenu-span">{{item.btnName}}</span>
    </div>
  </div>
</template>
<script>
  export default {
    name: 'jwVueContextMenu',
    props: {
      triggerHideFn: () => {},
      contextMenuData: {
        type: Object,
        requred: false,
        default () {
          return {
            menuName: null,
            axis: {
              x: null,
              y: null
            },
            menulists: [
              {
                fnHandler: 'adddata',
                btnName: 'New'
              },
              {
                fnHandler: 'savedata',
                btnName: 'Save'
              }
            ]
          }
        }
      },
      transferIndex: {
        type: Number,
        default: 0
      }
    },
    watch: {
      'contextMenuData.axis' (val) {
        var x = val.x
        var y = val.y
        var _this = this
        var index = _this.transferIndex
        var menuName = 'vue-contextmenuName-' + _this.contextMenuData.menuName
        var menu = document.getElementsByClassName(menuName)[index]
        menu.style.display = 'block'
        menu.style.left = x + 'px'
        menu.style.top = y + 'px'
        // 判断menu距离浏览器可视窗口底部距离,以免距离底部太近的时候，会导致menu被遮挡
        var menuHeight = this.contextMenuData.menulists.length // 不能用scrollHeight,获取到的menu是上一次的menu宽高
        var menuWidth = 100 // 不能用scrollWidth,同理
        var distanceToBottm = document.body.clientHeight - menu.offsetTop - menuHeight
        // 同理判断距离右侧距离
        var distanceToRight = document.body.clientWidth - menu.offsetLeft - menuWidth
        if (distanceToBottm < menuHeight) {
          menu.style.top = y - menuHeight + 'px'
        }
        if (distanceToRight < menuWidth) {
          menu.style.top = x - menuWidth + 'px'
        }
        document.addEventListener('mouseup', function (e) {
          // 解决mac电脑在鼠标右键后会执行mouseup事件
          if (e.button === 0) {
            menu.style.display = 'none'
          }
        }, false)
        document.addEventListener('mousedown', function (e) {
          if (e.which === 3) {
            menu.style.display = 'none'
          }
        }, false)
      }
    },
    methods: {
      fnHandler (item) {
        this.$emit(item.fnHandler)
      }
    }
  }
</script>
<style>
  .vue-contextmenu-listWrapper {
    box-shadow:0 6px 12px rgba(0,0,0,.175);
    display: none;
    position: fixed;
    z-index: 9999;
    top: 0;
    left: 0;
    border: 1px solid #c8c1c1;
  }

  .vue-contextmenu-listWrapper .context-menu-list {
    width: 80px;
    height: 32px;
    padding: 0 10px;
    background: #FFF;
    filter: alpha(opacity=90);
    opacity: 0.9;
  }
  .tabMenuClass .context-menu-list {
    border-right: 1px solid #c8c1c1;
  }

  .context-menu-list:hover{
    background-color: dodgerblue;
  }

  .vue-contextmenu-listWrapper .context-menu-list button {
    cursor: pointer;
    width: 100%;
    height: 100%;
    display: block;
    outline: 0;
    border: 0;
  }

  .vue-contextmenu-listWrapper .context-menu-list button i, .vue-contextmenu-listWrapper .context-menu-list button span {
    float: left;
  }

  .vue-contextmenu-listWrapper .context-menu-list button i {
    padding: 0 10px 0 10px;
  }

  .vue-contextmenu-listWrapper .context-menu-list button:hover {
    box-shadow: 0px 1px 3px rgba(34, 25, 25, 0.2);
    color: #ffffff;
    background: -webkit-linear-gradient(bottom, #5a6a76, #2e3940); /* Safari 5.1 - 6.0 */
    background: -o-linear-gradient(bottom, #5a6a76, #2e3940); /* Opera 11.1 - 12.0 */
    background: -moz-linear-gradient(bottom, #5a6a76, #2e3940); /* Firefox 3.6 - 15 */
    background: linear-gradient(to bottom, #5a6a76, #2e3940);
  }

  .vue-contextmenu-span {
    line-height: 32px;
    color: black;
  }
</style>
