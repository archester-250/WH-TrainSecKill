<template>
  <div class="login">
    <h2>用户登录</h2>
    <el-form :model="form" :rules="rules" ref="loginForm" label-width="100px">
      <!-- 用户名 -->
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <!-- 密码 -->
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="form.password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <!-- 记住我 -->
      <el-form-item>
        <el-checkbox v-model="form.rememberMe">记住我</el-checkbox>
        <router-link to="/forgot-password" class="forgot-password">忘记密码？</router-link>
      </el-form-item>
      <!-- 登录按钮 -->
      <el-form-item>
        <el-button type="primary" @click="submitForm">登录</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
      <!-- 注册链接 -->
      <p>没有账户？<router-link to="/register">立即注册</router-link></p>
    </el-form>
  </div>
</template>

<script>
import JSEncrypt from "jsencrypt"; // 引入 RSA 加密库
export default {
  name: 'Login',
  data() {
    return {
      form: {
        username: '',
        password: '',
        rememberMe: false
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }

    };
  },
  mounted() {
    // 页面加载时检查是否有存储的用户名
    const savedUsername = localStorage.getItem('rememberedUsername');
    if (savedUsername) {
      this.form.username = savedUsername;
      this.form.rememberMe = true;
    }
  },
  methods: {
    submitForm() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.login();
        } else {
          console.log('表单验证失败');
          return false;
        }
      });
    },
    // 登录方法
    login() {
      // 登录成功后
      if (this.form.rememberMe) {
        localStorage.setItem('rememberedUsername', this.form.username);
      } else {
        localStorage.removeItem('rememberedUsername');
      }
      // 后续处理
      const encrypt = new JSEncrypt();
      // 后端提供的公钥，需要在组件中获取或在请求时动态获取
      encrypt.setPublicKey(this.publicKey);
      const encryptedPassword = encrypt.encrypt(this.form.password);
      // 准备提交的数据
      const submitData = {
        username: this.form.username,
        password: encryptedPassword
      };
      // 发送请求
      this.$axios.post('/api/user/user/login', submitData)
        .then(response => {
          // 处理成功响应
        })
        .catch(error => {
          // 处理错误响应
        });
    },
    resetForm() {
      this.$refs.loginForm.resetFields();
    }
  }

};
</script>

<style scoped>
/* 添加样式 */
.forgot-password {
  float: right;
}
</style>
