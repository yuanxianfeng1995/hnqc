webpackJsonp([2],{bIvR:function(e,t,i){var n=i("VU/8")(i("hg4x"),i("vW53"),null,null);e.exports=n.exports},cn3P:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("jy-form-dialog",{ref:"form",attrs:{"form-options":e.options,entity:e.entity,rules:e.rules}},[i("template",{slot:"fieldset"},[i("el-form-item",{staticClass:"jw-field jw-field-1",attrs:{label:"姓名",prop:"name"}},[i("el-input",{model:{value:e.entity.name,callback:function(t){e.$set(e.entity,"name",t)},expression:"entity.name"}})],1),e._v(" "),i("el-form-item",{staticClass:"jw-field jw-field-1",attrs:{label:"电话",prop:"sex"}},[i("el-input",{attrs:{controls:!1},model:{value:e.entity.phone,callback:function(t){e.$set(e.entity,"phone",t)},expression:"entity.phone"}})],1),e._v(" "),i("el-form-item",{staticClass:"jw-field jw-field-1",attrs:{label:"性别",prop:"sex"}},[i("el-radio-group",{model:{value:e.entity.sex,callback:function(t){e.$set(e.entity,"sex",t)},expression:"entity.sex"}},[i("el-radio",{attrs:{label:"男"}},[e._v("男")]),e._v(" "),i("el-radio",{attrs:{label:"女"}},[e._v("女")])],1)],1),e._v(" "),i("el-form-item",{staticClass:"jw-field jw-field-1",attrs:{label:"年龄",prop:"age"}},[i("el-input",{attrs:{controls:!1},model:{value:e.entity.age,callback:function(t){e.$set(e.entity,"age",t)},expression:"entity.age"}})],1),e._v(" "),i("el-form-item",{staticClass:"jw-field jw-field-1",attrs:{label:"生日",prop:"birthday"}},[i("el-date-picker",{attrs:{type:"date"},model:{value:e.entity.birthday,callback:function(t){e.$set(e.entity,"birthday",t)},expression:"entity.birthday"}})],1),e._v(" "),i("el-form-item",{staticClass:"jw-field jw-field-1",attrs:{label:"会员等级",prop:"level"}},[i("jw-dict",{attrs:{clearable:"","allow-create":"","data-type":"string","dict-item-list":["普通","银牌","铜牌","金牌","钻石"]},model:{value:e.entity.level,callback:function(t){e.$set(e.entity,"level",t)},expression:"entity.level"}})],1),e._v(" "),i("el-form-item",{staticClass:"jw-field jw-field-2",attrs:{label:"备注",prop:"remark"}},[i("el-input",{attrs:{type:"textarea",autosize:{maxRows:6}},model:{value:e.entity.remark,callback:function(t){e.$set(e.entity,"remark",t)},expression:"entity.remark"}})],1)],1)],2)},staticRenderFns:[]}},hg4x:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=i("msXN");t.default={name:"customerView",mixins:[n.b],components:{Detail:function(e){return new Promise(function(e){e()}).then(function(){return e(i("ng56"))}.bind(null,i)).catch(i.oe)}},data:function(){return{prints:{},detailOptions:{context:{featureComponent:this,getGridComponent:function(e){return e.context.featureComponent.$refs.grid}}},gridOptions:{toolbarOptions:{operations:[{id:"refresh"},{id:"add"},{id:"export"}]},context:{name:"出库单",url:"/hoNeng/UserAction.action",featureComponent:this,getPermissions:function(e,t){return e.context.featureComponent.permission},getDetailComponent:function(e,t){return e.context.featureComponent.$refs.detail},params:{orderBy:"id"}}}}},computed:{permission:function(){return{add:!0,edit:!0,remove:!0,print:!0,export:!0}}},created:function(){this.gridOptions.columnDefs=[{hide:"selector"!==this.mode,type:"Checkbox"},{type:["IndexRender"]},{headerName:"名称",field:"name",tooltipField:"name",type:["ViewRender","LikeFilter"],width:120},{headerName:"电话",field:"phone",tooltipField:"phone",type:["LikeFilter"],width:120},{headerName:"性别",field:"sex",tooltipField:"sex",type:["LikeFilter"],width:120},{headerName:"年龄",field:"age",tooltipField:"age",type:["LikeFilter"],width:120},{headerName:"会员等级",field:"level",tooltipField:"level",type:["LikeFilter"],width:120},{headerName:"生日",field:"birthday",type:["TimestampRender"],cellRendererParams:{options:{format:"YYYY-MM-DD"}},width:120},{headerName:"备注",field:"remark",tooltipField:"remark",width:150,suppressSizeToFit:!1},{hide:"selector"===this.mode,type:"OperationRender",cellRendererParams:{operations:[{id:"edit",permission:"edit"},{id:"remove",permission:"remove"}]}}]},activated:function(){this.registQrcodeScannedListener()},methods:{registQrcodeScannedListener:function(){this.$store.commit("registQrcodeScannedListener",this.equipmentScan)},equipmentScan:function(e){this.$refs.grid.api.setFilterModel({no:{filter:e.no,init:!0}}),this.$refs.grid.reload()}}}},ng56:function(e,t,i){var n=i("VU/8")(i("xhNb"),i("cn3P"),null,null);e.exports=n.exports},vW53:function(e,t){e.exports={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"jw-view-page"},[t("jw-grid",{ref:"grid",attrs:{"grid-options":this.gridOptions}},[t("detail",{ref:"detail",attrs:{"detail-options":this.detailOptions}})],1)],1)},staticRenderFns:[]}},xhNb:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=i("msXN");t.default={name:"customerDetail",mixins:[n.a],data:function(){return{options:{size:"small",context:{name:"出库单",url:"/hoNeng/UserAction.action",detailComponent:this},createEntity:function(e,t){t({id:"",sex:"男",phone:"",age:"",birthday:this.$moment().toDate(),remark:"",level:""})}},entity:{},rules:{name:[{required:!0,message:"请输入设备名称",trigger:"blur"},{max:128,message:"长度在128字符以内",trigger:"blur"}]}}}}}});
//# sourceMappingURL=2.47f9667736c3f8e9020f.js.map