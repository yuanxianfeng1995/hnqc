import Vue from 'vue'

// 注册全局组件
import Dialog from './Dialog'
Vue.component(Dialog.name, Dialog)

import Report from './Report'
Vue.component(Report.name, Report)
import ReportDialog from './ReportDialog'
Vue.component(ReportDialog.name, ReportDialog)

import Selector from './Selector'
Vue.component(Selector.name, Selector)
import SelectorDialog from './SelectorDialog'
Vue.component(SelectorDialog.name, SelectorDialog)

import FormItem from './FormItem'
Vue.component(FormItem.name, FormItem)
import Form from './Form'
Vue.component(Form.name, Form)
import FormDialog from './FormDialog'
Vue.component(FormDialog.name, FormDialog)

import Audit from './Audit'
Vue.component(Audit.name, Audit)
import AuditDialog from './AuditDialog'
Vue.component(AuditDialog.name, AuditDialog)

import PrintView from './PrintView'
Vue.component(PrintView.name, PrintView)

