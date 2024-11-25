import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
Vue.prototype.$axios = axios;


Vue.use(ElementUI);

// 添加请求拦截器
axios.interceptors.request.use(config => {
  // 从 localStorage 中获取 token
  const token = localStorage.getItem('authToken');
  
  if (token) {
    // 在请求头中添加 token 字段
    config.headers['token'] = `${token}`;
  }

  return config;
}, error => {
  // 处理请求错误
  return Promise.reject(error);
});


new Vue({
  router,
  render: h => h(App)
}).$mount('#app');
