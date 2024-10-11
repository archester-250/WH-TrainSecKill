<template>
  <el-card class="register">
    <h2>用户注册</h2>
    <el-form @submit.prevent="registerUser" :model="form" label-width="100px">
      <el-form-item label="用户名" required>
        <el-input v-model="form.username" />
      </el-form-item>
      <el-form-item label="邮箱" required>
        <el-input v-model="form.email" />
      </el-form-item>
      <el-form-item label="密码" required>
        <el-input type="password" v-model="form.password" />
      </el-form-item>
      <el-button type="primary" native-type="submit">注册</el-button>
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
        email: '',
        password: ''
      },
      message: ''
    };
  },
  methods: {
    async registerUser() {
      // 与之前相同的注册逻辑
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
