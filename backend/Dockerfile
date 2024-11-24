<<<<<<< HEAD
FROM eclipse-temurin:17-jre

RUN mkdir -p /ssadmin-server
WORKDIR /ssadmin-server

## 将Jar 文件复制到镜像中
COPY ./target/WH_Seckill-0.0.1-SNAPSHOT.jar app.jar

## 设置 TZ 时区
ENV TZ=Asia/Shanghai

## 设置 JAVA_OPTS 环境变量，可通过 docker run -e "JAVA_OPTS=" 进行覆盖
ENV JAVA_OPTS="-Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

## 应用参数
ENV ARGS=""

EXPOSE 28080

## 启动后端项目
=======
FROM eclipse-temurin:17-jre

RUN mkdir -p /ssadmin-server
WORKDIR /ssadmin-server

## 将Jar 文件复制到镜像中
COPY ./target/WH_Seckill-0.0.1-SNAPSHOT.jar app.jar

## 设置 TZ 时区
ENV TZ=Asia/Shanghai

## 设置 JAVA_OPTS 环境变量，可通过 docker run -e "JAVA_OPTS=" 进行覆盖
ENV JAVA_OPTS="-Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

## 应用参数
ENV ARGS=""

EXPOSE 28080

## 启动后端项目
>>>>>>> seckill
CMD java ${JAVA_OPTS} -jar app.jar $ARGS