import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);
const key = 'user_id';

const store = new Vuex.Store({
  state () {
    return {
      user_id: null
    }
  },
  getters: {
    getUserId: function (state) {
      if (!state.user_id) {
        state.user_id = JSON.parse(localStorage.getItem(key))
      }
      return state.user_id
    }
  },
  mutations: {
    $_setStorage (state, value) {
      state.user_id = value;
      localStorage.setItem(key, JSON.stringify(value))
    },
    $_removeStorage (state) {
      state.user_id = null;
      localStorage.removeItem(key)
    }
  }
})

export default store