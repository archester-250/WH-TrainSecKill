<template>
  <div class="welcome">
    <el-card class="box-card">
      <h1>欢迎来到在线秒杀系统</h1>
      <p>请注册或登录以继续。</p>
      <div>
        <el-button type="primary" @click="$router.push('/register')">注册</el-button>
        <el-button type="success" @click="$router.push('/login')">登录</el-button>
      </div>
      <p class="admin-tip">仅管理员访问：</p>
      <el-button type="warning" @click="$router.push('/admin/login')">管理端入口</el-button>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Welcome'
};
</script>

<style scoped>
.welcome {
  text-align: center;
  margin-top: 50px;
}

.box-card {
  width: 400px;
  margin: 0 auto;
}

.admin-tip {
  margin-top: 20px;
  color: gray;
  font-size: 14px;
}
</style>
