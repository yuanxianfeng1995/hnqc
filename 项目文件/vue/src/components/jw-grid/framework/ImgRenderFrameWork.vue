<template>
  <div v-if="imgUrl">
     <!--<span  @click.stop="showModal">查看签名</span>-->
    <img :src="imgUrl"  @click="showModal" style="width: 40px; height: 30px"/>
    <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel" :zIndex="3000">
      <img style="width: 100%" :src="imgUrl"/>
    </a-modal>
  </div>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        previewVisible: false,
        imgUrl: ''
      }
    },
    created () {
      let field = this.params.colDef.field
      if (this.params.data) {
        this.imgUrl = this.params.data[field]
      }
    },
    methods: {
      handleCancel () {
        this.previewVisible = false
      },
      showModal () {
        this.previewVisible = true
      }
    }
  })
</script>
