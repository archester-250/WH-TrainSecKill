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
import { register } from '@/api/register'

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
    registerUser() {
      register(this.form.username, this.form.password).then(response => {
        console.log(response)
        this.message = response
      }).catch(error => {
        console.error('获取数据失败:', error)
      })
    }
  }
}
</script>

<style scoped>
.register {
  max-width: 400px;
  margin: 0 auto;
}
</style>
