export default {
  state: {
    user: null
  },
  mutations: {
    updataUser (state, val) {
      state.user = val
    }
  },
  actions: {
    SetUser ({commit, state, getters}, context) {
      commit('updataUser', context)
    }
  }
}
