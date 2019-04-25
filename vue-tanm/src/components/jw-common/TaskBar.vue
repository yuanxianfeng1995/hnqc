<style>
  .taskBarContent {
    height: 100%;
    display: flex;
    font-size: 12px;
    padding: 6px 10px;
    min-width: 150px;
  }

  .taskBatContent:hover {
  }

  .taskBar-item {
    text-align: center;
    cursor: pointer;
    color: #1f2d3d;
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100px;
  }
  .taskBar-item:hover {
    background-color: lightblue;
  }
  .jw-head-user {
    padding: 0 0;
  }

  .taskBar-item-icon {
    padding: 10px;
    color: #fff;
    border-color: #50bfff;
    border-radius: 50%;
  }

  .taskBar-item-icon {
    background: #73ccff;
    border-color: #73ccff;
    width:35px;
    height:35px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .taskBar-item-icon .iconfont{
    font-size: 20px;
  }

  .taskBar-item-title {
    padding-top: 5px;
    font-size: 12px;
  }

  .iconClass{
    display: inline-block;
    line-height: 20px;
    font-size:20px;
  }
</style>
<template>
  <div class="taskBarContent">
    <div class="taskBar-item"
         v-for="(application, index) in taskBar"
         :key="application.key"
         :id="application.id + '-BAR'"
         @click="onClicked(application)"
         @contextmenu="showDeleteMenu(index)"
         style="height: 100%;">
      <div :id="application.id + '-iconItem'"
           class="taskBar-item-icon"
           :style="{'background-color': application.background}"
           >
        <jw-vue-context-menu :contextMenuData="contextMenuData"
                          :transferIndex="transferIndex"
                          @deleteTask="deleteTask(application)"></jw-vue-context-menu>
        <i :id="application.id + '-icon'" :class="application.icon" class="iconClass" style="font-size: 24px;width: 24px;height: 24px;max-width: 24px;max-height: 24px;"></i>
      </div>
      <div class="taskBar-item-title" :id="application.id + '-title'">
        {{application.title}}
      </div>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'

  export default {
    name: 'taskBar',
    data () {
      return {
        hoverElement: null,
        transferIndex: null,
        contextMenuData: {
          menuName: 'deleteTask',
          axis: {
            x: null,
            y: null
          },
          menulists: [
            {
              fnHandler: 'deleteTask',
              btnName: '移除'
            }
          ]
        }
      }
    },
    computed: {
      taskBar () {
        return this.$store.state.shortcut.taskBarList
      }
    },
    created () {
      Vue.store.dispatch('loadTaskBar', event)
    },
    methods: {
      showDeleteMenu (index) {
        this.transferIndex = index // tranfer index to child component
        event.preventDefault()
        var x = event.clientX
        var y = event.clientY
        this.contextMenuData.axis = {
          x, y
        }
      },
      deleteTask (application) {
        this.$store.dispatch('deleteTaskBar', application)
      },
      onClicked (application) {
        this.$store.dispatch('openTab', {path: application.route_path, params: application})
      }
    }
  }
</script>
