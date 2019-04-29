<style>
  * {
    box-sizing: border-box;
    -moz-box-sizing: border-box; /* Firefox */
    -webkit-box-sizing: border-box; /* Safari */
  }

  body {
    font-size: 14px;
    /*background-color: #324157;*/
    margin: 0px;
    padding: 0px;
    height: 100%;
    overflow: hidden;
  }
  .jw-input-selector .el-dialog__wrapper{
    overflow: inherit!important;
  }
  fieldset {
    padding: 0;
    margin: 0;
    border: 0;
    min-width: 0;
  }

  .el-loading-mask {
    border-radius: 4px;
    background-color: rgba(255, 255, 255, 0.5)
  }

  a {
    color: #20a0ff;
  }

  a:hover {
    color: #4db3ff
  }

  #app {
    height: 100%;
  }

  .bg-img {
    background-image: url('~assets/img/login-bg.jpg');
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
  }

  .pull-right {
    float: right !important;
  }

  .fa {
    text-align: center;
  }

  #sideMenu .el-menu-item {
    background-color: #2E2C32
  }

  #sideMenu .el-menu-item:hover {
    background-color: #48576a;
  }

  #sideMenu .el-submenu__title {
    background-color: #2E2C32
  }

  .jw-menu-body .el-menu-item, .el-submenu__title {
    height: 46px;
    line-height: 46px;
  }

  .jw-menu-item-text {
    font-size: 16px;
  }

  .jw-menu-item-text > i {
    font-size: 32px;
    margin-right: 10px;
    width: 20px;
    height: 20px;
  }

  .ag-pinned-left-cols-viewport .iconfont {
    font-size: 20px;
  }

  .ag-cell > a {
    line-height: 18px;
    display: flex;
  }

  .ag-cell.ag-cell-not-inline-editing.ag-cell-with-height.ag-cell-no-focus.ag-cell-value {
    padding: 4px;
  }

  .jw-view-tab > .jw-card-tabs {
    background: #D6D6D6;
  }

  .jw-view-tab .el-tabs--card > .el-tabs__header {
    margin: 0px;
  }

  .jw-view-tab .el-tabs--card > .el-tabs__header .el-tabs__nav-wrap {
    margin-left: 10px;
    margin-right: 20px;
  }

  .jw-view-tab .el-tabs--card > .el-tabs__header .el-tabs__nav-next, .jw-view-tab .el-tabs--card > .el-tabs__header .el-tabs__nav-prev {
    line-height: 32px;
    font-size: 14px;
  }

  .is-disabled ul + div {
    display: none;
  }

  .jw-view-tab .el-tabs--card > .el-tabs__header .el-tabs__item {
    padding: 0 8px;
    height: 32px;
    line-height: 32px;
    background-color: #C8C8C8;
    border-right: 1px solid #d1dbe5;
    border-radius: 0 0 0 0;
    font-size: 14px;
    /*font-weight: bold;*/
  }

  .jw-view-tab .el-tabs--card > .el-tabs__header .el-tabs__item.is-active {
    background-color: #1D87E8;
    border-bottom-color: #fff;
    color: #fff;
  }

  .el-tabs--card > .el-tabs__header .el-tabs__item.is-active {
    border: 1px solid #1D87E8;
    border-bottom: 0px solid #1D87E8;
  }

  .jw-view-tab .is-closable {
    color: darkslategray;
  }

  .jw-view-tab .jw-view-tab-body {
    background-color: #EEEEEE;
  }

  .jw-view-page {
    padding: 10px;
    height: 100%;
    width: 100%;
  }

  /********************************************************************************************************************/
  /* jw-grid */
  /********************************************************************************************************************/
  .jw-grid {
    width: 100%;
    height: 100%;
    border: 1px solid #ddd;
    border-radius: 4px;
    background: #f5f7f9;
  }

  .jw-grid .ag-overlay-no-rows-center {
    background-color: #f5f7f9;
    border: 1px solid #eee;
    border-radius: 4px;
    padding: 6px;
  }

  .jw-grid .ag-paging-panel {
    border-top: 1px solid #eee;
  }

  .jw-grid .ag-root {
    border: none;
    -moz-user-select: text;
    -webkit-user-select: text;
    -ms-user-select: text;
  }

  .jw-grid .ag-header {
    background: #f5f7f9;
    border-bottom: 1px solid #eee;
  }

  .jw-grid .ag-body {
    background-color: #fff;
  }

  .jw-grid .ag-row-odd {
    background-color: #f6f8ff;
  }

  .jw-grid .ag-row-selected {
    background-color: #b0e0e6;
  }

  .jw-grid .ag-menu {
    border: 1px solid #ddd;
    border-radius: 4px;
  }

  .jw-grid .jw-floating-filter-cell {
    padding: 0px 1px;
  }

  .jw-grid .jw-floating-filter-cell .el-input {
  }

  .jw-grid .jw-floating-filter-cell .el-select {
    width: 100%;
    min-width: 20px;
  }

  .jw-grid .jw-floating-filter-cell .el-input__icon {
    width: 25px;
  }

  .jw-grid .jw-floating-filter-cell .el-input__icon + .el-input__inner {
    padding-right: 20px;
  }

  .jw-grid .jw-floating-filter-cell .el-tag {
    height: 20px;
    line-height: 20px;
    margin: 1px 0 1px 4px;
  }

  .jw-grid .jw-floating-filter-cell .el-date-editor--daterange.el-input {
    width: 100%;
  }

  .jw-grid .ag-floating-filter-button button {
    border: 1px solid #d1dbe5;
    border-left: none;
    border-top-right-radius: 2px;
    border-bottom-right-radius: 2px;
  }

  .jw-grid .ag-ltr .ag-header-group-cell {
    border-right: 1px solid #eee;
    text-align: center;
  }

  .jw-grid .ag-ltr .ag-header-group-cell-with-group {
    border-bottom: 1px solid #eee;
  }

  .jw-grid .ag-ltr .ag-header-cell {
    border-right: 1px solid #eee;
    text-align: center;
  }

  .jw-grid .ag-ltr .ag-header-cell .ag-header-cell-label {
    display: block;
  }

  .jw-grid .ag-ltr .ag-header-cell .ag-header-cell-label > span {
    float: none;
  }

  .jw-grid .ag-ltr .ag-header-viewport .ag-header-cell:last-child {
    border-right: 1px solid transparent;
  }

  .jw-grid .ag-ltr .ag-pinned-right-header {
    border-left: 1px solid #eee;
  }

  .jw-grid .ag-ltr .ag-cell-no-focus {
    border-right: 1px solid transparent;
    border-left: 1px solid transparent;
    outline-width: 0;
  }

  .jw-form-view .jw-grid .ag-cell .el-select .el-input__inner:hover {
    border-bottom: none;
  }

  .jw-grid .ag-ltr .ag-pinned-left-cols-viewport {
    border-right: 1px solid #eee;
  }

  .jw-grid .ag-ltr .ag-pinned-right-cols-viewport {
    border-left: 1px solid #eee;
  }

  .jw-grid .ag-group-cell {
    font-style: normal;
  }

  .jw-grid .ag-paging-panel {
    text-align: right;
  }

  .jw-grid .ag-paging-row-summary-panel {
    width: auto;
    margin-right: 20px;
  }

  /********************************************************************************************************************/
  /* jy-dialog */
  /********************************************************************************************************************/
  .jy-dialog {
    margin-bottom: 0px;
    width: auto;
    border: 1px solid #cacaca;
    border-radius: 6px;
  }

  .jy-dialog.jy-dialog-full {
    top: 0px;
    width: 100%;
    border: none;
    border-radius: 0;
  }

  .jy-dialog .el-dialog__header {
    padding: 18px 20px;
    background-color: #f1f1f1;
    height: 56px;
    line-height: initial;
    border-top-left-radius: inherit;
    border-top-right-radius: inherit;
  }

  .jy-dialog .el-dialog__footer {
    padding: 10px 20px;
    background-color: #f1f1f1;
    height: 56px;
    line-height: initial;
    border-bottom-right-radius: inherit;
    border-bottom-left-radius: inherit;
  }

  .jy-dialog .el-dialog__body {
    padding: 0px;
  }

  .jy-dialog .jy-dialog-body {
    overflow-y: auto;
  }

  .jw-define-footer {
    position: absolute;
    bottom: 0px;
    width: 100%;
  }

  .jy-dialog-define-footer {
    padding-right: 20px;
    line-height: 56px
  }

  /********************************************************************************************************************/
  /* jw-form */
  /********************************************************************************************************************/
  .jw-form .jw-form-body {
    padding: 20px;
  }

  .jw-form.jw-search-form {
    padding: 0px;
  }

  .jw-form.jw-search-form > .el-form-item {
    margin-bottom: 5px;
  }

  .jw-form.jw-search-form {
    padding: 0px;
  }

  .jw-form .jw-form-footer {
    padding: 20px;
  }

  .jw-form .el-form--inline fieldset {
    margin-bottom: -10px;
  }

  .jw-form .el-form--inline .jw-field {
    margin-right: 0px;
    margin-bottom: 10px;
    min-height: 36px;
  }

  .jw-form .el-form--inline .jw-field .el-form-item__label {
    width: 100px;
    padding: 10px 5px 0px 0;
  }

  .jw-form .el-form--inline .jw-field.jw-middle-label .el-form-item__label {
    padding-top: 0px;
    line-height: 18px;
  }

  /* large 1470 -  */
  .jw-form-mini {
    width: 391px; /* 20 + 350 + 20 + 1 */
  }

  .jw-form-mini.jw-form-collapse {
    width: 422px; /* 20 + 350 + 20 + 1 + 31*/
  }

  .jw-form-small {
    width: 745px; /* 20 + 350 + 4 + 350 + 20 + 1 */
  }

  .jw-form-small.jw-form-collapse {
    width: 776px; /* 20 + 350 + 4 + 350 + 20 + 1 + 31 */
  }

  .jw-form-middle {
    width: 1099px; /* 20 + 350 + 4 + 350 + 4 + 350 + 20 + 1 */
  }

  .jw-form-middle.jw-form-collapse {
    width: 1130px; /* 20 + 350 + 4 + 350 + 4 + 350 + 20 + 1 + 31 */
  }

  .jw-form-large {
    width: 1453px; /* 20 + 350 + 4 + 350 + 4 + 350 + 4 + 350 + 20 + 1 */
  }

  .jw-form-large.jw-form-collapse {
    width: 1484px; /* 20 + 350 + 4 + 350 + 4 + 350 + 4 + 350 + 20 + 1 + 31 */
  }

  .jw-form-full {
    width: 100%;
  }

  .jw-form .el-form--inline .jw-field.jw-field-1 .el-form-item__content,
  .jw-form.jw-form-full .el-form--inline .jw-field.jw-field-1 .el-form-item__content,
  .jw-form.jw-form-mini .el-form--inline .jw-field.jw-field-1 .el-form-item__content,
  .jw-form.jw-form-mini .el-form--inline .jw-field.jw-field-2 .el-form-item__content,
  .jw-form.jw-form-mini .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
  .jw-form.jw-form-mini .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
    width: 250px;
  }

  .jw-form .el-form--inline .jw-field.jw-field-2 .el-form-item__content,
  .jw-form.jw-form-full .el-form--inline .jw-field.jw-field-2 .el-form-item__content,
  .jw-form.jw-form-small .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
  .jw-form.jw-form-small .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
    width: 604px; /* 250 + 4 + 350 */
  }

  .jw-form .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
  .jw-form.jw-form-full .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
  .jw-form.jw-form-middle .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
    width: 958px; /* 250 + 4 + 350 + 4 + 350 */
  }

  .jw-form .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
    width: 1312px; /* 250 + 4 + 350 + 4 + 350 + 4 + 350 */
  }

  /* middle 1116 -  1470 */
  @media (max-width: 1470px) {
    .jw-form-large {
      width: 1099px; /* 20 + 350 + 4 + 350 + 4 + 350 + 4 + 350 + 20 + 1 */
    }

    .jw-form-large.jw-form-collapse {
      width: 1130px; /* 20 + 350 + 4 + 350 + 4 + 350 + 4 + 350 + 20 + 1 + 31 */
    }

    .jw-form .el-form--inline .jw-field.jw-field-2.jw-list .el-form-item__content {
      width: 958px; /* 250 + 4 + 350 + 4 + 350 */
    }

    .jw-form .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
      width: 958px; /* 250 + 4 + 350 + 4 + 350 */
    }
  }

  /* small 762 - 1116 */
  @media (max-width: 1116px) {
    .jw-form-middle,
    .jw-form-large {
      width: 745px; /* 20 + 350 + 4 + 350 + 4 + 350 + 20 + 1 */
    }

    .jw-form-middle.jw-form-collapse,
    .jw-form-large.jw-form-collapse {
      width: 776px; /* 20 + 350 + 4 + 350 + 4 + 350 + 20 + 1 + 31 */
    }

    .jw-form .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
    .jw-form.jw-form-full .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
    .jw-form.jw-form-middle .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
      width: 604px; /* 250 + 4 + 350 + 4 + 350 */
    }

    .jw-form .el-form--inline .jw-field.jw-field-2.jw-list .el-form-item__content {
      width: 604px; /* 250 + 4 + 350 + 4 + 350 + 4 + 350 */
    }

    .jw-form .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
      width: 604px; /* 250 + 4 + 350 + 4 + 350 + 4 + 350 */
    }
  }

  /* mini 0 - 762 */
  @media (max-width: 762px) {
    .jw-form-small,
    .jw-form-middle,
    .jw-form-large {
      width: 391px; /* 20 + 350 + 4 + 350 + 20 + 1 */
    }

    .jw-form-small.jw-form-collapse,
    .jw-form-middle.jw-form-collapse,
    .jw-form-large.jw-form-collapse {
      width: 422px; /* 20 + 350 + 4 + 350 + 20 + 1 + 31 */
    }

    .jw-form .el-form--inline .jw-field.jw-field-2 .el-form-item__content,
    .jw-form.jw-form-full .el-form--inline .jw-field.jw-field-2 .el-form-item__content,
    .jw-form.jw-form-small .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
    .jw-form.jw-form-small .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
      width: 250px; /* 250 + 4 + 350 */
    }

    .jw-form .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
    .jw-form.jw-form-full .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
    .jw-form.jw-form-middle .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
      width: 250px; /* 250 + 4 + 350 + 4 + 350 */
    }

    .jw-form .el-form--inline .jw-field.jw-field-2.jw-list .el-form-item__content {
      width: 250px; /* 250 + 4 + 350 + 4 + 350 + 4 + 350 */
    }

    .jw-form .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
      width: 250px; /* 250 + 4 + 350 + 4 + 350 + 4 + 350 */
    }
  }

  .jw-form .el-form--inline .jw-field .el-form-item__content .jw-grid {
    border: 1px solid #d1dbe5;
  }

  .jw-form .el-input-number {
    width: 100%;
  }

  .jw-form .el-autocomplete {
    width: 100%;
  }

  .jw-form .el-select {
    width: 100%;
  }

  .jw-form .el-radio-group {
    width: 100%;
  }

  .jw-form .el-date-editor {
    width: 100%!important;
  }

  .jw-form .el-cascader {
    width: 100%;
  }

  .jw-form .el-textarea__inner {
    min-height: 36px;
    padding: 6px 10px;
  }

  .jw-textarea-nowrap .el-textarea__inner {
    white-space: nowrap;
  }

  .jw-form .el-form-item__error {
    background: #fff;
    z-index: 1000;
  }

  .el-form-item.is-error .jw-grid .jw-floating-filter-cell .el-input__inner,
  .el-form-item.is-error .jw-grid .jw-floating-filter-cell .el-textarea__inner {
    border-color: #bfcbd9;
  }

  .jw-form .el-collapse-item__header,
  .jw-collapse .el-collapse-item__header {
    background-color: #fbfdff;
  }

  .jw-form .el-collapse-item__wrap,
  .jw-collapse .el-collapse-item__wrap {
    background-color: #fff;
  }

  .jw-input-selector-input.el-select .el-input .el-input__icon,
  .jw-input-selector-input.el-select .el-input .el-input__icon.is-reverse {
    transform: translateY(-50%);
  }

  .jw-input-selector-input .el-icon-caret-top:before {
    content: "\E61D";
  }

  .jw-input-selector-popper {
    display: none;
  }

  .jw-input-selector-input .el-select__tags {
    height: 30px;
  }

  .jw-input-selector-input .el-tag {
    max-width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    position: relative;
    padding-right: 20px;
  }

  .jw-input-selector-input .el-tag .el-tag__close {
    position: absolute;
    top: 5px;
    right: 2px;
  }

  /********************************************************************************************************************/
  /* jw-form-view */
  /********************************************************************************************************************/
  .jw-form-view .el-input__inner,
  .jw-form-view .el-input.is-disabled .el-input__inner,
  .jw-form-view .el-textarea__inner,
  .jw-form-view .el-textarea.is-disabled .el-textarea__inner {
    background: inherit;
    border: none;
    border-bottom: 1px solid #bfcbd9;
    border-radius: 0;
    color: #1f2d3d;
    cursor: not-allowed;
  }

  .jw-form-view .el-radio__input.is-disabled.is-checked .el-radio__inner {
    border-color: #20a0ff;
    background: #20a0ff;
  }

  .jw-form-view .el-radio__input.is-disabled .el-radio__inner {
    background: inherit;
  }

  .jw-form-view .el-cascader.is-disabled .el-cascader__label,
  .jw-form-view .el-radio__input.is-disabled + .el-radio__label {
    color: #1f2d3d;
  }

  .jw-form-view .el-input-number__decrease,
  .jw-form-view .el-input-number__increase,
  .jw-form-view .el-autocomplete .el-input__icon,
  .jw-form-view .el-cascader .el-input__icon,
  .jw-form-view .el-select .el-input__icon,
  .jw-form-view .el-select .el-tag .el-icon-close,
  .jw-form-view .el-date-editor .el-input__icon {
    display: none;
  }

  .jw-form-view input::placeholder,
  .jw-form-view input::-webkit-input-placeholder {
    color: transparent !important;
  }

  .jw-form-view .el-checkbox,
  .jw-form-view .el-checkbox .el-checkbox__inner,
  .jw-form-view .el-radio,
  .jw-form-view .el-radio .el-radio__inner,
  .jw-form-view .el-select .el-tag,
  .jw-form-view .el-cascader .el-cascader__label {
    cursor: not-allowed;
  }

  .jw-form-view .el-checkbox .el-checkbox__inner:hover,
  .jw-form-view .el-radio .el-radio__inner:hover {
    border: 1px solid #bfcbd9;
  }

  .jw-form-view .el-select .el-input__inner:hover {
    border-bottom: 1px solid #bfcbd9;
  }

  .jw-form-view .jw-grid .el-input__inner,
  .jw-form-view .jw-grid .el-input.is-disabled .el-input__inner,
  .jw-form-view .jw-grid .el-textarea__inner {
    background: inherit;
    border: none;
    border-radius: 0;
    color: #1f2d3d;
    cursor: default;
  }

  .jw-form-report-A4 {
    width: 22cm; /*21 + 1*/
    height: 31.5cm; /*29.7 + 1.8*/
  }

  .jw-form-report-A4portrait {
    width: 30.7cm; /*29.7 + 1*/
    height: 22.8cm; /*21 + 1.8*/
  }

  /********************************************************************************************************************/
  /* jy-selector */
  /********************************************************************************************************************/
  .jy-dialog-selector {
    width: 80%;
    margin: 0 auto;
  }

  .jy-dialog-selector .jy-selector {
    height: 100%;
  }

  /********************************************************************************************************************/
  /* jw-upload */
  /********************************************************************************************************************/
  .jw-upload {
    width: 100%;
    min-height: 36px;
  }

  .jw-upload .el-upload {
    width: 100%;
    display: block;
  }

  .jw-upload-picture {
    display: flex;
  }

  .el-upload-list--picture-card {
    display: flex;
  }

  .jw-form-view .jw-upload .el-upload-list__item:hover {
    background: none;
  }

  .jw-form-view .jw-upload .el-upload-list__item .el-icon-close,
  .jw-form-view .jw-upload .el-upload-list__item-status-label,
  .jw-form-view .jw-upload .el-upload,
  .jw-upload-view .el-upload {
    display: none !important;
  }

  .jw-form-visible {
    visibility: visible;
  }

  .jw-form-hidden {
    visibility: hidden;
  }

  .jw-upload .el-upload .el-upload-dragger {
    width: 100%;
  }

  .jw-upload .el-upload .el-upload-dragger .el-upload__text {
    line-height: normal;
  }

  .jw-upload .el-upload .el-upload-dragger i {
    margin: 16px 0px;
  }

  .jw-upload .el-upload__tip {
    margin-top: 0px;
    line-height: initial;
  }

  .jw-upload .el-upload-list__item:first-child {
    margin-top: 5px;
  }

  .jw-upload .el-upload--picture-card {
    border: none;
    width: 100%;
    height: auto;
    line-height: inherit;
  }

  .jw-upload .el-upload-list--picture-card .el-upload-list__item {
    border: none;
    width: 120px;
    height: 120px;
    margin: 0;
    display: block;
  }

  .jw-upload .el-upload-list--picture-card .el-upload-list__item:first-child {
    margin-top: 0px;
  }

  .jw-form-view .jw-upload .el-upload-list--picture-card .el-upload-list__item-actions .el-upload-list__item-delete {
    display: none;
  }

  .el-button-group .el-button:first-child {
    border-top-left-radius: 4px;
    border-bottom-left-radius: 4px;
  }

  .el-button-group .el-button:last-child {
    border-top-right-radius: 4px;
    border-bottom-right-radius: 4px;
  }

  .scroll_style ::-webkit-scrollbar {
    width: 3px; /*滚动条宽度*/
    height: 15px; /*滚动条高度*/
  }

  .scroll_style ::-webkit-scrollbar-track {
    border-radius: 2px; /*滚动条的背景区域的圆角*/
    background-color: #1F2D3D; /*滚动条的背景区域的背景颜色*/
  }

  .scroll_style ::-webkit-scrollbar-thumb {
    border-radius: 5px; /*滚动条的圆角*/
    background-color: #48576A; /*滚动条的背景颜色*/
  }

  .el-dialog__wrapper {
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    position: fixed;
    overflow: initial;
    margin: 0
  }

  .head-item-icon {
    margin-left: 10px;
  }

  .el-menu--dark {
    background-color: #2e2c32!important;
  }

  .head-el-button {
    margin: 0;
    border-radius: 50%;
    padding: 0;
    width: 36px;
    height: 36px;
    border: 0.6px solid #c4c4c4;
  }

  .collapseMenu {
    background-image: url("~assets/img/collapseMenu.png");
  }

  /********************************************************************************************************************/
  /* jw-operation */
  /********************************************************************************************************************/
  .jw-button-group .el-button--info {
    color: #50bfff;
    background-color: #EEF1F6;
    border-color: #EEF1F6;
  }

  .jw-button-group .el-button--primary {
    color: #20a0ff;
    background-color: #EEF1F6;
    border-color: #EEF1F6;
  }

  .jw-button-group .el-button--success {
    color: #13ce66;
    background-color: #EEF1F6;
    border-color: #EEF1F6;
  }

  .jw-button-group .el-button--danger {
    color: #ff4949;
    background-color: #EEF1F6;
    border-color: #EEF1F6;
  }

  .jw-button-group .el-button--info:hover {
    background: #50bfff;
    color: #fff;
  }

  .jw-button-group .el-button--primary:hover {
    background: #4db3ff;
    color: #fff;
  }

  .jw-button-group .el-button--success:hover {
    background: #13ce66;
    color: #fff;
  }

  .jw-button-group .el-button--danger:hover {
    background-color: #ff4949;
    color: #fff;
  }

  .jw-button-group .el-button.is-disabled,
  .jw-button-group .el-button.is-disabled:focus,
  .jw-button-group .el-button.is-disabled:hover {
    color: #bfcbd9;
    background-color: #EEF1F6;
    cursor: not-allowed;
    border-color: #EEF1F6;
  }

  .ag-body-viewport-wrapper.ag-layout-auto-height, .ag-body-viewport-wrapper.ag-layout-normal {
    overflow: hidden;
  }

  .ag-fresh .ag-ltr .ag-group-expanded {
    padding-right: 4px;
  }

  .ag-fresh .ag-ltr .ag-group-contracted {
    padding-right: 4px;
  }

  .ag-fresh .ag-cell-not-inline-editing {
    padding: 3px;
    padding-left: 1px;
  }

  .ag-fresh .ag-header-cell {
    padding: 2px;
    padding-top: 4px;
  }

  .ag-fresh {
    line-height: 1.4;
  }

  .ag-header-cell .jw-floating-filter-cell {
    max-height: 32px;
    overflow: hidden;
  }

  .el-message-box {
    min-width: 300px;
    width: auto;
    max-width: 50%;
  }

  @media (max-width: 762px) {
    .el-message-box {
      width: auto;
      max-width: 90%;
    }
  }
  .ag-root.ag-layout-normal {
    color: #333;
  }
  .adSpin {
    height: 100%;
    width: 100%;
  }
</style>
<template>
    <div id="app" class="bg-img">
      <router-view></router-view>
    </div>
</template>
<script>

  export default {
    name: 'app',
    data () {
      return {
        locale: null
      }
    }
  }
</script>

