import Vue from 'vue'

// 注册全局组件
import Authorize from './Authorize'
Vue.component(Authorize.name, Authorize)

import Autocomplete from './Autocomplete'
Vue.component(Autocomplete.name, Autocomplete)

import City from './City'
Vue.component(City.name, City)

import CompanyDropdown from './CompanyDropdown'
Vue.component(CompanyDropdown.name, CompanyDropdown)

import DepartmentSelector from './DepartmentSelector'
Vue.component(DepartmentSelector.name, DepartmentSelector)

import PersonSelector from './PersonSelector'
Vue.component(PersonSelector.name, PersonSelector)

import EmployeeSelector from './EmployeeSelector'
Vue.component(EmployeeSelector.name, EmployeeSelector)

import PostSelector from './PostSelector'
Vue.component(PostSelector.name, PostSelector)

import Menu from './Menu'
Vue.component(Menu.name, Menu)

import MenuSelector from './MenuSelector'
Vue.component(MenuSelector.name, MenuSelector)

import MenuItem from './MenuItem'
Vue.component(MenuItem.name, MenuItem)

import SubMenu from './SubMenu'
Vue.component(SubMenu.name, SubMenu)

import Standard from './Standard'
Vue.component(Standard.name, Standard)
