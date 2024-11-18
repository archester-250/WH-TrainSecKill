<template>
  <el-card class="register">
    <h2>用户注册</h2>
    <el-form :model="form" label-width="100px">
      <el-form-item label="用户名" required>
        <el-input v-model="form.username" />
      </el-form-item>
      <el-form-item label="密码" required>
        <el-input type="password" v-model="form.password" />
      </el-form-item>
      <el-button type="primary" native-type="submit" @click="registerUser">注册</el-button>
    </el-form>
    <p v-if="message">{{ message }}</p>
  </el-card>
</template>

<script>
import axios from 'axios';

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
    async registerUser() {
      console.log("Register button clicked");  // 检查点击是否触发
      try {
        const response = await axios.post('http://localhost:8080/user/user/register', this.form, {
          headers:{
            'Content-Type': 'application/json'
          }
        });
        console.log(response);  // 检查是否有返回结果
        if (response.status === 200) {
          this.message = '注册成功！';
          // this.$router.push('/login');
        } else {
          this.message = response.data.message || '注册失败，请重试。';
        }
      } catch (error) {
        console.error('Error:', error);  // 捕获错误
        this.message = '网络错误，请稍后再试。';
      }
    }
  }
};
</script>

<style scoped>
.register {
  max-width: 400px;
  margin: 0 auto;
}
</style>
