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
    path: '/message',
    name: 'message',
    component: () => import('../views/Message')
  },
  {
    path: '/collection',
    name: 'collection',
    component: () => import('../views/Collection')
  },
  {
    path: '/comments',
    name: 'comments',
    component: () => import('../views/Comments')
  },
  {
    path: '/activity',
    name: 'Activity',
    component: () => import('../views/Activity')
  },
  {
    path: '/MyActivity',
    name: 'MyActivity',
    component: () => import('../views/MyActivity')
  },
  {
    path: '/ChangePassword',
    name: 'ChangePassword',
    component: () => import('../views/ChangePassword')
  },
  {
    path: '/FindYourLove',
    name: 'FindYourLove',
    component: () => import('../views/FindYourLove')
  },
  {
    path: '/MyGoods',
    name: 'MyGoods',
    component: () => import('../views/MyGoods')
  },
  {
    path: '/NewGoods/:id',
    name: 'NewGoods',
    component: () => import('../views/NewGoods')
  },
  {
    path: '/NewGoods',
    name: 'NewGoods',
    component: () => import('../views/NewGoods')
  },
  {
    path: '/UserDetail/:id',
    name: 'UserDetail',
    component: () => import('../views/UserInfo')
  },
  {
    path: '/test',
    name: 'Test',
    component: () => import('../components/Test')
  }
];



const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
