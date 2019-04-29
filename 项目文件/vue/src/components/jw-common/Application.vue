<style>
  .application-item {
    text-align: center;
    cursor: pointer;
    color: #1f2d3d;
    width: 150px;
    display: flex;
    flex-direction: column;
    align-items: center;
    min-width: 150px;
  }

  .application-items {
    display: flex;
    align-items: center;
    background: #fff;
    padding-top: 10px;
    padding-bottom: 10px;
    border-radius: inherit;
    overflow: hidden;
  }

  .application-item-icon {
    padding: 10px;
    color: #fff;
    border-color: #50bfff;
    border-radius: 15px;
    width: 60px;
  }

  .application-item:hover {
    color: #50bfff;
  }

  .application-item:hover .application-item-icon {
    background: #73ccff;
    border-color: #73ccff;
  }

  .application-item:active {
    color: #48ace6;
  }

  .application-item:active .application-item-icon {
    background: #48ace6;
    border-color: #48ace6;
  }

  .application-item-icon i {
    font-size: 30px;
    width: 30px;
    height: 30px;
  }

  .application-item-title {
    padding-top: 5px;
  }
</style>
<template>
  <div class="application-items">
    <div draggable="true" class="application-item"
         v-for="(application, idx) in applicationList"
         :key="application.id"
         :id="application.id"
         @click="onApplicationClicked(application)">
      <div class="application-item-icon" :style="{'background-color': $store.state.shortcut.color[idx % 3]}"  @contextmenu="showAddMenu(idx)">
        <jw-vue-context-menu :contextMenuData="contextMenuData"
                          :transferIndex="transferIndex"
                          @addData="addData(application)" @deleteData="deleteData(application)"></jw-vue-context-menu>
        <i :class="application.icon" style="font-size: 40px; line-height: 40px"></i>
      </div>
      <div class="application-item-title">
        {{application.title}}
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'application',
    data () {
      return {
        transferIndex: null,
        contextMenuData: {
          menuName: 'addTask',
          axis: {
            x: null,
            y: null
          },
          menulists: [
            {
              fnHandler: 'addData',
              btnName: '置顶'
            }, {
              fnHandler: 'deleteData',
              btnName: '移除'
            }
          ]
        }
      }
    },
    computed: {
      applicationList () {
        // return this.$store.getters.getShortCut().slice(0, Math.floor((this.$store.state.layout.right.width - 20) / 150))
        return this.$store.state.shortcut.shortcutList.slice(0, Math.floor((this.$store.state.layout.right.width - 20) / 150))
      }
    },
    methods: {
      showAddMenu (index) {
        if (this.applicationList.length === 1) {
          this.contextMenuData.menulists = [{
            fnHandler: 'addData',
            btnName: '置顶'
          }]
        } else {
          this.contextMenuData.menulists = [
            {
              fnHandler: 'addData',
              btnName: '置顶'
            }, {
              fnHandler: 'deleteData',
              btnName: '移除'
            }
          ]
        }
        this.transferIndex = index // tranfer index to child component
        event.preventDefault()
        var x = event.clientX
        var y = event.clientY
        this.contextMenuData.axis = {
          x, y
        }
      },
      addData (application) {
        this.$store.dispatch('addTaskBar', application)
      },
      deleteData (application) {
        this.$store.dispatch('deleteShutCut', application)
      },
      onApplicationClicked (application) {
        this.$store.dispatch('openTab', {path: application.route_path, params: application})
      }
    }
  }
</script>
