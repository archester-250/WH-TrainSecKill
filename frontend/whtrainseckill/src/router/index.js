import Vue from 'vue';
import Router from 'vue-router';
import Register from '@/views/Register.vue';
import Login from '@/views/Login.vue';
import ProductList from '@/views/ProductList.vue';
import Welcome from '@/views/Welcome.vue';
import ProductDetail from '@/views/ProductDetail.vue'; 
import OrderList from '@/views/OrderList.vue';
import SeckillProductList from '../views/SeckillProductList.vue';
import AdminLogin from '../views/AdminLogin.vue';
import ManagementSeckill from '../views/ManagementSeckill.vue';
import Cart from '../views/Cart.vue';
import OrderSuccess from '../views/OrderSuccess.vue';


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
      path: '/seckill',
      name: 'SeckillProductList',
      component: SeckillProductList,
    },
    {
      path: '/admin/login',
      name: 'AdminLogin',
      component: AdminLogin,
    },
    {
      path: '/management/seckill',
      name: 'ManagementSeckill',
      component: ManagementSeckill,
    },
    {
      path: '/cart',
      name: 'Cart',
      component: Cart,
    },
    {
      path: '/order/success',
      name: 'OrderSuccess',
      component: OrderSuccess,
    }
  ]
});