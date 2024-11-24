# 使用 Node.js 官方镜像进行构建
FROM node:16-alpine AS build-stage

# 设置工作目录
WORKDIR /app

# 复制 package.json 和 package-lock.json
COPY package*.json ./

# 安装依赖
RUN npm install

# 复制源代码
COPY . .

# 构建前端应用
RUN npm run build

# 使用 Nginx 作为服务器
FROM nginx:stable-alpine

# 复制nginx.conf文件
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
# 复制构建的文件到 Nginx 默认目录
COPY --from=build-stage /app/dist/ /usr/share/nginx/html

# 暴露 Nginx 运行的端口（默认 80）
EXPOSE 80

# 运行 Nginx
CMD ["nginx", "-g", "daemon off;"]
