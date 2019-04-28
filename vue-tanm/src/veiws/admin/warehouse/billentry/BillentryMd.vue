<style scoped>
</style>

<template>
  <el-dialog :visible.sync="visible" title="打印">
    <div id="printJS-iframe">
      <div class="head">
        <h3 style="text-align: center">湖南鑫亿德 销售入货单</h3>
        <div style="display: flex;justify-content: space-between">
          <span>客户名称:{{orderDomain ? orderDomain.name : ''}}</span>
          <span>录单日期:{{orderDomain ? orderDomain.purchasedDate : ''}}</span>
          <span>客户名称:{{orderDomain ? orderDomain.no : ''}}</span>
        </div>
      </div>
      <table class="center" border="1px" cellpadding="0" cellspacing="0" style="min-width: 100%" v-if="orderDomain" >
        <tr>
          <td>编号</td>
          <td>商品名称</td>
          <td>单位</td>
          <td>数量</td>
          <td>单价</td>
          <td>金额</td>
          <td>备注</td>
        </tr>
        <tr v-for="commodity in orderDomain.equipmentDetailList">
          <td>{{commodity.no}}</td>
          <td>{{commodity.name}}</td>
          <td>{{commodity.manufacturer}}</td>
          <td>{{commodity.number}}</td>
          <td>{{commodity.price}}</td>
          <td>{{commodity.money}}</td>
          <td>{{commodity.remark}}</td>
        </tr>
        <tr>
          <td>总计大写</td>
          <td>{{swapper(orderDomain.money)}}</td>
          <td>总计数量</td>
          <td>{{orderDomain ? orderDomain.number : ''}}</td>
          <td>页小计</td>
          <td colspan="2">{{orderDomain ? orderDomain.money : ''}}</td>
        </tr>
        <tr>
          <td>销货单位</td>
          <td>{{orderDomain ? orderDomain.manufacturer : ''}}</td>
          <td>地址电话</td>
          <td colspan="4">{{orderDomain ? orderDomain.addr : ''}}</td>
        </tr>
      </table>
      <div class="footer">
        <span style="margin-right: 20px">制单人:{{orderDomain ? orderDomain.making : ''}}</span>
        <span style="margin-right: 20px">经手人:{{orderDomain ? orderDomain.handle : ''}}</span>
        <span style="margin-right: 20px">打印日期:{{this.$moment(new Date()).format('YYYY/MM/DD HH:mm:ss')}}</span>
        <span>客户签名:</span>
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
    name: 'OutboundMd',
    data () {
      return {
        visible: false,
        orderDomain: null
      }
    },
    methods: {
      open (orderDomain) {
        this.orderDomain = this.$lodash.merge({}, orderDomain)
        this.visible = true
        this.$http.get('api/BillentryAction.action?orderBy=id&totalCount=&pageSize=30&pageNo=0&id=' + orderDomain.id).then((response) => {
          this.orderDomain = response.body.success ? response.body.data : {}
        })
      },
      onAdd () {
        let _this = this
        let promise = new Promise(function(resolve, reject) {
          printJS({
            printable: 'printJS-iframe', type: 'html',
            scanStyles: false
          })
          resolve()
        })
        promise.then(function() {
          setTimeout(() => {
            _this.visible = false
            console.log(_this.visible)
            _this.orderDomain = null
          }, 500)
        })
      },
      swapper (n) {
        if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
          return "数据非法";
        var unit = "千百拾亿千百拾万千百拾元角分", str = "";
        n += "00";
        var p = n.indexOf('.');
        if (p >= 0)
          n = n.substring(0, p) + n.substr(p + 1, 2);
        unit = unit.substr(unit.length - n.length);
        for (var i = 0; i < n.length; i++)
          str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
        return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
      }
    }
  }
</script>
