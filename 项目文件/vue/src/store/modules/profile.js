/**
 * 当前登录用户的信息和权限配置
 */
export default {
  state: {
    userInfo: null,
    menuList: [],
    permissionList: []
  },
  getters: {
    currentUser: state => {
      return state.userInfo
    },
    hasPermission: (state, getters) => (menuId) => {
      return state.permissionList.indexOf(menuId) > -1
    },
    findMenuById: (state, getters) => (menuId, menuList) => {
      let menus = menuList || state.menuList
      for (let menu of menus) {
        if (menu.id === menuId) {
          return menu
        }

        if (menu.children && menu.children.length) {
          let subMenu = getters.findMenuById(menuId, menu.children)
          if (subMenu) {
            return subMenu
          }
        }
      }

      return null
    },
    findMenuByRoutePath: (state, getters) => (routePath, menuList) => {
      let menus = menuList || state.menuList
      for (let menu of menus) {
        if (menu.route_path === routePath) {
          return menu
        }

        if (menu.children && menu.children.length) {
          let subMenu = getters.findMenuByRoutePath(routePath, menu.children)
          if (subMenu) {
            return subMenu
          }
        }
      }

      return null
    },
    _dealMenuList: (state, getters) => (menuList, permissionList) => {
      menuList.forEach((menu) => {
        if (menu.type > 2) { // 只有按钮和令牌才需要在这里判断，根、目录、页面不需要进行权限判断
          permissionList.push(menu.id)
        }

        if (menu.children && menu.children.length) {
          getters._dealMenuList(menu.children, permissionList)
          if (menu.type === 2) { // 删除页面下的children
            menu.children = []
          }
        }
      })
    }
  },
  mutations: {
    updateUserInfo (state, userInfo) {
      state.userInfo = userInfo
    },
    updateMenuList (state, menuList) {
      state.menuList = menuList
    },
    updatePermissionList (state, permissionList) {
      state.permissionList = permissionList
    }
  },
  actions: {
    updateProfile ({dispatch, commit, state, getters}, {userInfo, menuList}) {
      // console.log('updateProfile', userInfo, menuList)
      let permissionList = []
      getters._dealMenuList(menuList, permissionList)

      commit('updatePermissionList', permissionList)
      commit('updateMenuList', menuList)
      commit('updateUserInfo', userInfo)
    }
  }
}
