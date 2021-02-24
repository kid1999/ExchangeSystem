import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/index'
import axios from 'axios'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'mdui/dist/css/mdui.css'; // 注意修改 css 文件的路径
import mdui from 'mdui';

Vue.use(ElementUI);

Vue.prototype.$ajax = axios;
axios.defaults.baseURL = '/api';
Vue.config.productionTip = false;

router.beforeEach((to, from, next) => {
  // title 显示
  if(to.meta.title){
    document.title = to.meta.title;
  }
  // 注册放行
  if(to.path === "/register" || to.path === "/login") {
    next();
  }
  //vuex里面的user是否已经存在，不然就直接跳转到登录页面
  else if( localStorage.getItem("access_token") == null || store.state.user == null) {
    next({
      path: "/login"
    })
  }else {
    next();
  }
});


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
