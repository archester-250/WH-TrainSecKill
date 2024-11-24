<template>
    <el-card class="login">
      <h2>用户登录</h2>
      <el-form @submit.prevent="loginUser" :model="form" label-width="100px">
        <el-form-item label="用户名" required>
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码" required>
          <el-input type="password" v-model="form.password" />
        </el-form-item>
        <el-button type="primary" native-type="submit">登录</el-button>
      </el-form>
      <p v-if="message">{{ message }}</p>
    </el-card>
  </template>
  
  <script>
  export default {
    data() {
      return {
        form: {
          username: '',
          password: ''
        },
        message: ''
      };
    },
    methods: {
      async loginUser() {
        // 与之前相同的登录逻辑
      }
    }
  };
  </script>
  
  <style scoped>
  .login {
    max-width: 400px;
    margin: 0 auto;
  }
  </style>
  