import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);
const key = 'user_id';

const store = new Vuex.Store({
  state () {
    return {
      user: null,
    }
  },
  getters: {
    getUser: function (state) {
      if (!state.user) {
        state.user = JSON.parse(localStorage.getItem(key))
      }
      return state.user
    }
  },
  mutations: {
    $_setUser (state, value) {
      state.user = value;
      localStorage.setItem(key, JSON.stringify(value))
    },
    $_removeUser (state) {
      state.user = null;
      localStorage.removeItem(key)
    },
  }
})

export default store