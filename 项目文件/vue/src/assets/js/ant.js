import Vue from 'vue'
import moment from 'moment'
import 'moment/locale/zh-cn'

const navType = navigator.appName
let lang = null
if (navType === 'Netscape') {
  lang = navigator.language
} else {
  lang = navigator.userLanguage
}
lang = lang.substr(0, 2)
if (lang === 'zh') {
  moment.locale('zh-cn')
}


import {
  Pagination,
  Button,
  Tooltip,
  Checkbox,
  Select,
  DatePicker,
  Input,
  InputNumber,
  Form,
  Modal,
  Collapse,
  Radio,
  Upload,
  Icon,
  Row,
  Col,
  Popover,
  LocaleProvider,
  Tree,
  Menu,
  Tabs,
  AutoComplete,
  Cascader,
  Spin
} from 'ant-design-vue'

Vue.use(Pagination)
Vue.use(Button)
Vue.use(Tooltip)
Vue.use(Checkbox)
Vue.use(DatePicker)
Vue.use(Select)
Vue.use(Input)
Vue.use(InputNumber)
Vue.use(Form)
Vue.use(Modal)
Vue.use(Collapse)
Vue.use(Radio)
Vue.use(Upload)
Vue.use(Icon)
Vue.use(Row)
Vue.use(Col)
Vue.use(Popover)
Vue.use(LocaleProvider)
Vue.use(Tree)
Vue.use(Menu)
Vue.use(Tabs)
Vue.use(AutoComplete)
Vue.use(Cascader)
Vue.use(Spin)
const IconFont = Icon.createFromIconfontCN({
  scriptUrl: '//at.alicdn.com/t/font_907001_ccgojl4tca.js'
})

Vue.component('IconFont', IconFont)
