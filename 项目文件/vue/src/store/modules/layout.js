export default {
  state: {
    window: {
      width: window.innerWidth, height: window.innerHeight
    },
    top: {
      width: window.innerWidth, height: 0
    },
    left: {
      width: 260, height: 0
    },
    right: {
      width: 0, height: 0
    },
    center: {
      width: 0, height: 0
    },
    bottom: {
      width: window.innerWidth, height: 0
    },
    sideMenu: {
      header: {
        width: 0, height: 0
      },
      body: {
        width: 0, height: 0
      }
    },
    viewTab: {
      header: {
        width: 0, height: 0
      },
      body: {
        width: 0, height: 0
      }
    }
  },
  getters: {},
  mutations: {
    updateLayout (state, layout) {
      for (let k in layout) {
        state[k] = layout[k]
      }
    }
  },
  actions: {}
}
