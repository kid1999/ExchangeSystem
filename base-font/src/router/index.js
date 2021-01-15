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
    component: () => import('../views/Login')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register')
  },
  {
    path: '/goods/detail/:id',
    name: 'goodsDetail',
    component: () => import('../views/GoodsDetail')
  },
  {
    path: '/userInfo/:id',
    name: 'userInfo',
    component: () => import('../views/UserInfo')
  },
  {
    path: '/transRecord',
    name: 'transRecord',
    component: () => import('../views/TransRecords')
  },
  {
    path: '/test',
    name: 'Test',
    component: () => import('../components/Test')
  },
  {
    path: '/axios', name: 'TestApi', component: () => import('../views/TestApi')
  }
];



const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
