import Vue from 'vue';
import Router from 'vue-router';
import Register from '@/views/Register.vue';
import Login from '@/views/Login.vue';
import ProductList from '@/views/ProductList.vue';
import Welcome from '@/views/Welcome.vue';
import ProductDetail from '@/views/ProductDetail.vue'; 
import OrderDetail from '@/views/OrderDetail.vue';
import OrderList from '@/views/OrderList.vue';


Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Welcome',
      component: Welcome
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/products',
      name: 'ProductList',
      component: ProductList
    },
    {
      path: '/product/:id', // 动态路由，匹配商品详情
      name: 'ProductDetail',
      component: ProductDetail,
    },
    {
      path: '/orders',
      name: 'OrderList',
      component: OrderList, // 订单列表页面
    },
    {
      path: '/order/:id',
      name: 'OrderDetail',
      component: OrderDetail, // 订单详情页面
    },
  ]
});