<style scoped>
  .jw-panel {
    position: relative;
    border: 1px solid #d1dbe5;
    border-radius: 4px;
  }

  .jw-panel-head {
    background-color: #f5f7f9;
    border-bottom: 1px solid #eee;
    border-top-left-radius: inherit;
    border-top-right-radius: inherit;
    padding: 0 15px;
  }

  .jw-panel-title {
    height: 43px;
    line-height: 43px;
  }

  .jw-panel-content {
    height: 100%;
  }

  .jw-panel-foot {
    background-color: #f5f7f9;
    /*border-top: 1px solid #eee;*/
    border-bottom-left-radius: inherit;
    border-bottom-right-radius: inherit;
    padding: 0 15px;
  }
</style>

<template>
  <div ref="panel" class="jw-panel">
    <div ref="head" class="jw-panel-head">
      <slot name="head">
        <div class="jw-panel-title">{{title}}</div>
      </slot>
    </div>
    <div ref="content" class="jw-panel-content" :style="contentStyle">
      <slot></slot>
    </div>
    <div ref="foot" class="jw-panel-foot" :style="footStyle">
      <slot name="foot"></slot>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'jwPanel',
    props: {
      title: {
        type: String,
        default: null
      }
    },
    data () {
      return {
        headHeight: 43,
        footHeight: 0
      }
    },
    computed: {
      contentStyle () {
        return {
          'margin-top': -this.headHeight + 'px',
          'padding-top': this.headHeight + 'px',
          'border-bottom-left-radius': this.footHeight ? '0px' : 'inherit',
          'border-bottom-right-radius': this.footHeight ? '0px' : 'inherit'
        }
      },
      footStyle () {
        return {'border-top': this.footHeight ? '1px solid #eee' : 'none'}
      }
    },
    mounted () {
      this.headHeight = this.$refs.head.offsetHeight
      this.footHeight = this.$refs.foot.offsetHeight
    }
  }
</script>
