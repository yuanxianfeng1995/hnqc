webpackJsonp([5],{ALDU:function(e,t,i){var n=i("VU/8")(i("zaZY"),i("WTKH"),null,null);e.exports=n.exports},WTKH:function(e,t){e.exports={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"jw-view-page"},[t("jw-grid",{ref:"grid",attrs:{"grid-options":this.gridOptions}},[t("billentry-md",{ref:"md",attrs:{mode:"dialog"}}),this._v(" "),t("equipment-detail",{ref:"detail",attrs:{"detail-options":this.detailOptions}})],1)],1)},staticRenderFns:[]}},zaZY:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=i("msXN");t.default={name:"outboundView",mixins:[n.b],components:{EquipmentDetail:function(e){return i.e(0).then(function(){return e(i("xYrv"))}.bind(null,i)).catch(i.oe)},BillentryMd:function(e){return i.e(0).then(function(){return e(i("k8WD"))}.bind(null,i)).catch(i.oe)}},data:function(){return{detailOptions:{context:{featureComponent:this,getGridComponent:function(e){return e.context.featureComponent.$refs.grid}}},gridOptions:{toolbarOptions:{operations:[{id:"refresh"},{id:"add"},{id:"export"}]},context:{name:"入库单",url:"/hoNeng/BillentryAction.action",featureComponent:this,getPermissions:function(e,t){return e.context.featureComponent.permission},getDetailComponent:function(e,t){return e.context.featureComponent.$refs.detail},params:{orderBy:"id desc"}}}}},computed:{permission:function(){return{add:!0,edit:!0,remove:!0,print:!0,export:!0}}},created:function(){this.gridOptions.columnDefs=[{hide:"selector"!==this.mode,type:"Checkbox"},{headerName:"单据编号",field:"no",tooltipField:"no",type:["ViewRender","LikeFilter"],width:120},{headerName:"客户名称",field:"name",tooltipField:"name",type:["LikeFilter"],width:120},{headerName:"销货单位",field:"manufacturer",tooltipField:"manufacturer",type:["LikeFilter"],width:120},{headerName:"地址电话",field:"addr",tooltipField:"addr",type:["LikeFilter"],width:120},{headerName:"总计数量",field:"number",type:["NumberRender"],width:120},{headerName:"小计金额",field:"money",type:["NumberRender"],width:120},{headerName:"录单日期",field:"purchasedDate",type:["TimestampRender"],cellRendererParams:{options:{format:"YYYY-MM-DD"}},width:120},{headerName:"制单人",field:"making",tooltipField:"making",width:120},{headerName:"经手人",field:"Handle",tooltipField:"Handle",width:120},{headerName:"备注",field:"remark",tooltipField:"remark",width:150},{hide:"selector"===this.mode,type:"OperationRender",cellRendererParams:{operations:[{id:"edit",permission:"edit"},{id:"print",onClick:function(e,t){e.context.featureComponent.$refs.md.open(t)}},{id:"remove",permission:"remove"}]}}]},activated:function(){this.registQrcodeScannedListener()},methods:{registQrcodeScannedListener:function(){this.$store.commit("registQrcodeScannedListener",this.equipmentScan)},equipmentScan:function(e){this.$refs.grid.api.setFilterModel({no:{filter:e.no,init:!0}}),this.$refs.grid.reload()}}}}});
//# sourceMappingURL=5.7cc70444eb66f00728ed.js.map