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
import JSEncrypt from "jsencrypt"; // 引入 RSA 加密库
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
            // 动态获取公钥并进行登录
            this.getPublicKey()
                .then(publicKey => {
                    const encrypt = new JSEncrypt();
                    encrypt.setPublicKey(publicKey);
                    const encryptedPassword = encrypt.encrypt(this.form.password);
                    const submitData = {
                        username: this.form.username,
                        password: encryptedPassword
                    };

                    // 发送登录请求
                    this.$axios.post('/api/admin/admin/login', submitData)
                        .then(response => {
                            // 假设后端返回的 token 在 response.data 中
                            const token = response.data;
                            if (token) {
                                // 存储 token 到 localStorage
                                localStorage.setItem('authToken', token);

                                // 提示用户登录成功
                                this.$message.success('登录成功！');

                                // 跳转到产品页面
                                setTimeout(() => {
                                    this.$router.push('/products'); // 假设目标页面为 /products
                                }, 1500);
                            } else {
                                this.$message.error('未接收到 token，登录失败！');
                            }
                        })
                        .catch(error => {
                            // 处理错误响应
                            console.error('登录失败', error);
                            this.$message.error('登录失败，请重试！');
                        });
                })
                .catch(error => {
                    console.error('获取公钥失败', error);
                });
        },
        // 动态获取公钥
        getPublicKey() {
            return new Promise((resolve, reject) => {
                this.$axios.get('/api/public-key')
                    .then(response => {
                        if (response.data && response.data.publicKey) {
                            resolve(response.data.publicKey);
                        } else {
                            reject(new Error('未获取到有效的公钥'));
                        }
                    })
                    .catch(error => {
                        reject(error);
                    });
            });
        },
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