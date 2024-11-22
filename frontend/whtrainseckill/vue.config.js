const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 80,
    host: '0.0.0.0', // 监听所有网络地址（必要，否则 Docker 内无法访问）
    allowedHosts: "all", // 允许所有主机访问，避免访问受限问题
    proxy: {
      "/api": {
        target: 'http://localhost:28080',
        changeOrigin: true,
        pathRewrite: {
          "^/api": ''
        }
      }
    }
  }
})
