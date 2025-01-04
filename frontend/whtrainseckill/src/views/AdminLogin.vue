<template>
    <div class="admin-login">
        <el-card class="login-card">
            <h2>管理员登录</h2>
            <el-form :model="form" ref="loginForm" :rules="rules" label-width="80px">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="form.username" placeholder="请输入用户名" />
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="form.password" type="password" placeholder="请输入密码" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="handleLogin">登录</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script>
export default {
    name: 'AdminLogin',
    data() {
        return {
            form: {
                username: '',
                password: ''
            },
            rules: {
                username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
            }
        };
    },
    methods: {
        handleLogin() {
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                    this.$axios
                        .post('/api/admin/login', this.form)
                        .then((response) => {
                            if (response.status === 200) {
                                this.$message.success('登录成功');
                                this.$router.push('/admin/home'); // 跳转到管理端首页
                            } else {
                                this.$message.error(response.statusText);
                            }
                        })
                        .catch((error) => {
                            console.error(error);
                            this.$message.error('用户名或密码错误');
                        });
                } else {
                    this.$message.error('请填写完整的表单信息');
                }
            });
        }
    }
};
</script>

<style scoped>
.admin-login {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f5f5f5;
}

.login-card {
    width: 400px;
    padding: 20px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.2);
    text-align: center;
}
</style>