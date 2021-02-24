import Vue from 'vue'
import VueRouter from 'vue-router'
import GoodsList from "../views/GoodsList";

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'GoodsList',
    meta: {
      title:'首页',
    },
    component: GoodsList
  },
  {
    path: '/login',
    name: 'Login',
    meta: {
      title:'登录',
    },
    component: () => import('../views/Login')
  },
  {
    path: '/register',
    name: 'Register',
    meta: {
      title:'注册',
    },
    component: () => import('../views/Register')
  },
  {
    path: '/goods/detail/:id',
    name: 'goodsDetail',
    meta: {
      title:'商品信息',
    },
    component: () => import('../views/GoodsDetail')
  },
  {
    path: '/userInfo/:id',
    name: 'userInfo',
    meta: {
      title:'用户信息',
    },
    component: () => import('../views/UserInfo')
  },
  {
    path: '/transRecord',
    name: 'transRecord',
    meta: {
      title:'交易记录',
    },
    component: () => import('../views/TransRecords')
  },
  {
    path: '/message',
    name: 'message',
    meta: {
      title:'交易审批信息',
    },
    component: () => import('../views/Message')
  },
  {
    path: '/collection',
    name: 'collection',
    meta: {
      title:'收藏夹',
    },
    component: () => import('../views/Collection')
  },
  {
    path: '/comments',
    name: 'comments',
    meta: {
      title:'留言评论',
    },
    component: () => import('../views/Comments')
  },
  {
    path: '/activity',
    name: 'Activity',
    meta: {
      title:'同城活动',
    },
    component: () => import('../views/Activity')
  },
  {
    path: '/MyActivity',
    name: 'MyActivity',
    meta: {
      title:'我的活动',
    },
    component: () => import('../views/MyActivity')
  },
  {
    path: '/ChangePassword',
    name: 'ChangePassword',
    meta: {
      title:'修改密码',
    },
    component: () => import('../views/ChangePassword')
  },
  {
    path: '/FindYourLove',
    name: 'FindYourLove',
    meta: {
      title:'猜你喜欢',
    },
    component: () => import('../views/FindYourLove')
  },
  {
    path: '/MyGoods',
    name: 'MyGoods',
    meta: {
      title:'我的商品',
    },
    component: () => import('../views/MyGoods')
  },
  {
    path: '/NewGoods/:id',
    name: 'NewGoods',
    meta: {
      title:'编辑商品',
    },
    component: () => import('../views/NewGoods')
  },
  {
    path: '/NewGoods',
    name: 'NewGoods',
    meta: {
      title:'上架商品',
    },
    component: () => import('../views/NewGoods')
  },
  {
    path: '/NewActivity',
    name: 'NewActivity',
    meta: {
      title:'发起活动',
    },
    component: () => import('../views/NewActivity')
  },
  {
    path: '/test',
    name: 'Test',
    meta: {
      title:'测试页面',
    },
    component: () => import('../views/Test')
  }
];



const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
