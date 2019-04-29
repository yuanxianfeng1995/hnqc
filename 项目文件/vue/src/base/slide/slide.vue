<template>
  <div class="wrapper" ref="wrapper">
    <div class="slider" ref="slider">
      <div class="slider-group" ref="sliderGroup">
        <slot>
        </slot>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import Bscroll from 'better-scroll'
  import {addClass} from "assets/js/dom";

  export default {
    name: "slide",
    props: {
      loop: {
        type: Boolean,
        default: true
      },
      autoPlay: {
        type: Boolean,
        default: true
      },
      interval: {
        type: Number,
        default: 4000
      }
    },
    mounted () {
      setTimeout(() => {
        this._setSliderWidth()
        this._initSlider()
      }, 20)
    },
    methods: {
      _setSliderWidth () {
        let children = this.$refs.sliderGroup.children
        let width = 0
        let sliderWidth = this.$refs.slider.clientWidth
        console.log('_setSliderWidth', sliderWidth)
        for (let i = 0; i < children.length; i++) {
          let child = children[i]
          console.log('child', child)
          addClass(child, 'slider-item')
          child.style.width = sliderWidth + 'px'
          width += sliderWidth
          if (this.loop) {
            width += 2*sliderWidth
          }
          console.log('sliderGroupwidth', width)
          this.$refs.sliderGroup.style.width = width + 'px'
        }
      },
      _initSlider () {
        new Bscroll(this.$refs.wrapper, {
          probeType: this.probeType,
          click: this.click
        })
        new Bscroll(this.$refs.slider, {
          scrollX: true,
          scrollY: false,
          momentum: false,
          snap: true,
          loop: true,
          snapLoop: this.loop,
          snapThreshold: 0.3,
          snapSpeed: 400
        })
      }
    }
  }
</script>

<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~assets/stylus/variable"
  .slider
    min-height: 1px

    .slider-group
      position: relative
      overflow: hidden
      white-space: nowrap

      .slider-item
        float: left
        box-sizing: border-box
        overflow: hidden
        text-align: center

        a
          display: block
          width: 100%
          overflow: hidden
          text-decoration: none

        img
          display: block
          width: 100%
</style>
