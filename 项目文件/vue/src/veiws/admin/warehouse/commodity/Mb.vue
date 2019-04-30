<template>
  <div id="printJS-iframe">
    <h2>出库单</h2>
    <div>
      <span style="width: 250px">设备名称:{{prints.name}}</span><span style="width: 250px">厂家:{{prints.manufacturer}}</span>
    </div>
    <div>
      <span style="width: 250px">价格(元):{{prints.price}}</span>
      <span style="width: 250px">出库日期:{{$moment(prints.purchasedDate).format('YYYY年MM月DD日')}}</span>
    </div>
    <div>
      <p>备注:{{prints.remark}}</p>
    </div>
  </div>
</template>

<script>
    export default {
        name: "mb"
    }
</script>

<style scoped>

</style>
