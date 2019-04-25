<style scoped>
</style>

<template>
  <div>
    <slot name="middle">
      <div id="layoutLeft" style="float: left;"
           :style="{'width':layout.left.width+'px', 'height':layout.left.height+'px'}" class="scroll_style">
        <slot name="left"></slot>
      </div>

      <div id="layoutRight">
        <slot name="right">
          <div id="layoutTop">
            <slot name="top"></slot>
          </div>
          <div id="layoutCenter" style="position: absolute;"
               :style="{'width':layout.center.width+'px', 'height':layout.center.height+'px', 'margin-left':layout.left.width+'px'}">
            <slot name="center"></slot>
          </div>
          <div id="layoutBottom">
            <slot name="bottom"></slot>
          </div>
        </slot>
      </div>

    </slot>
  </div>
</template>

<script>
  export default {
    name: 'jwLayout',
    props: {
      leftWidth: {
        type: Number,
        default: 240
      }
    },
    computed: {
      layout: {
        get () {
          return this.$store.state.layout
        },
        set (val) {
          this.$store.commit('updateLayout', val)
          window.eventBus.$emit('layoutUpdated')
        }
      }
    },
    watch: {
      leftWidth (newval, oldval) {
        this._updateLayout()
      }
    },
    created () {
      this.onWindowResize = this.$lodash.debounce(this._updateLayout, 50)
      window.addEventListener('resize', this.onWindowResize)
      this.$nextTick(() => {
        this.onWindowResize()
      })
    },
    beforeDestroy () {
      window.removeEventListener('resize', this.onWindowResize)
    },
    methods: {
      _updateLayout () {
        // console.log('_updateLayout', document.getElementById('layoutTop').clientHeight)
        let top = document.getElementById('layoutTop')
        let bottom = document.getElementById('layoutBottom')
        if (!top) {
          return
        }
        let topHeight = top.clientHeight
        let bottomHeight = bottom.clientHeight
        let leftWidth = window.innerWidth < 768 ? 0 : this.leftWidth
        let rightWidth = window.innerWidth - leftWidth
        let centerHeight = window.innerHeight - topHeight - bottomHeight - 1
        let viewTabHeaderHeight = 32 + 1
        let viewTabBodyHeight = centerHeight - viewTabHeaderHeight
        this.layout = {
          window: {
            width: window.innerWidth, height: window.innerHeight
          },
          top: {
            width: window.innerWidth, height: topHeight
          },
          left: {
            width: leftWidth, height: window.innerHeight
          },
          right: {
            width: rightWidth, height: window.innerHeight
          },
          center: {
            width: rightWidth, height: centerHeight
          },
          bottom: {
            width: window.innerWidth, height: bottomHeight
          },
          sideMenu: {
            header: {
              width: leftWidth, height: 0
            },
            body: {
              width: leftWidth, height: window.innerHeight
            }
          },
          viewTab: {
            header: {
              width: rightWidth, height: viewTabHeaderHeight
            },
            body: {
              width: rightWidth, height: viewTabBodyHeight
            }
          }
        }
      }
    }
  }
</script>
