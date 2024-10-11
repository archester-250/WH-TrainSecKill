import Vue from 'vue';
import Router from 'vue-router';
import Register from '../components/Register.vue';
import Login from '../components/Login.vue';
import Welcome from '../components/Welcome.vue';

Vue.use(Router);

const routes = [
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
  }
];

const router = new Router({
  mode: 'history',
  routes
});

export default router;
