export default {
  state: {
    menuList: [],
    collapseMenu: true
  },
  mutations: {
    updataUser (state, val) {
      state.menuList = val
    }
  },
  actions: {
    SetMenu ({commit, state, getters}, context) {
      console.log(context)
      commit('updataUser', context)
    },
    collapseMenu ({commit, state}, isCollapseMenu) {
      localStorage.setItem('collapseMenu', isCollapseMenu)
      state.collapseMenu = localStorage.getItem('collapseMenu', isCollapseMenu, Boolean)
    },
  }
}
