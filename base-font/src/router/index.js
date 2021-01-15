import Vue from 'vue'
import VueRouter from 'vue-router'
import GoodsList from "../views/GoodsList";

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'GoodsList',
    component: GoodsList
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/goods/detail/:id',
    name: 'goodsDetail',
    component: () => import('../views/GoodsDetail.vue')
  },
  {
    path: '/test',
    name: 'Test',
    component: () => import('../components/Test.vue')
  },
  {
    path: '/axios', name: 'TestApi', component: () => import('../views/TestApi.vue')
  }
];



const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
