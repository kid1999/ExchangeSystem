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

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
