<template>
  <el-dialog top="40%" :visible.sync="visible" title="打印">
    <div id="printJS-iframe">
      <h2>商品信息</h2>
      <div>
        <span style="width: 250px">编号:{{prints.no}}</span><span style="width: 250px">商品名称:{{prints.name}}</span>
      </div>
      <div>
        <span style="width: 250px">单位:{{prints.manufacturer}}</span>
        <span style="width: 250px">数量:{{prints.number}}</span>
      </div>
      <div>
        <span style="width: 250px">价格:{{prints.price}}</span>
        <span style="width: 250px">金额:{{prints.money}}</span>
      </div>
      <div>
        <span style="width: 250px">出库日期:{{$moment(prints.purchasedDate).format('YYYY年MM月DD日')}}</span>
      </div>
      <div>
        <p>备注:{{prints.remark}}</p>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关闭</el-button>
      <el-button type="primary" @click="onAdd">打印</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import printJS from 'print-js'
    export default {
      name: 'commodityMd' ,
      data() {
        return {
          visible: false
        }
      },
      prop: {
        prints: Object
      },
      methods: {
        open(orderDomain) {
          this.orderDomain = this.$lodash.merge({}, orderDomain)
          this.visible = true
        },
        onAdd () {
          setTimeout(() => {
            printJS({ printable: 'printJS-iframe', type: 'html',
              scanStyles: false,
              style: '#printJS-iframe{width: 600px;margin: 0 auto;font-size: 18px;}#printJS-iframe header{\n' +
                '  text-align: center;}#printJS-iframe>div>span{  display: inline-block;\n' +
                '  width: 250px;margin-right: 30px;height: 40px;\n' +
                '  line-height: 40px;overflow: hidden;}#printJS-iframe h2 {\n' +
                '  text-align: center;}'
            })
          },600)
        }
      }
    }
</script>

<style scoped>

</style>
