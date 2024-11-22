import Vue from 'vue';
import Router from 'vue-router';
import Register from '@/views/Register.vue';
import Login from '@/views/Login.vue';
import ProductList from '@/views/ProductList.vue';
import Welcome from '@/views/Welcome.vue'

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
    
  ]
});