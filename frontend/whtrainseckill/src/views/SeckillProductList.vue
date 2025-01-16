<template>
  <div class="seckill-list">
    <h2>秒杀活动</h2>
    <el-row :gutter="20">
      <el-col :span="6" v-for="product in seckillProducts" :key="product.id">
        <el-card :body-style="{ padding: '10px' }" class="product-card">
          <!-- <img v-lazy="product.img" class="product-image" /> -->
          <div class="product-info">
            <h3 class="product-name">{{ product.name }}</h3>
            <p>秒杀价：<span class="seckill-price">￥{{ product.seckillPrice }}</span></p>
            <p>库存：<span>{{ adjustedStockCount(product.stockCount) }}</span></p>
            <p>倒计时：<span>{{ getCountdown(product.startDate, product.endDate) }}</span></p>
            <el-button type="danger"
                       :disabled="!isSeckillAvailable(product.startDate, product.endDate) || buttonLoading[product.id]"
                       :loading="buttonLoading[product.goodsId]" @click="handlePurchase(product.goodsId)">
              {{ isSeckillAvailable(product.startDate, product.endDate) ? '立即抢购' : '不可抢购' }}
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <div v-if="seckillProducts.length === 0">
      <p>当前没有进行中的秒杀活动。</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SeckillList',
  data() {
    return {
      seckillProducts: [], // 秒杀商品数据
      buttonLoading: {}, // 按钮加载状态
    };
  },
  mounted() {
    this.fetchSeckillProducts();
    this.startCountdownUpdate();
  },
  beforeDestroy() {
    clearInterval(this.countdownTimer);
  },
  computed: {
    adjustedStockCount() {
      return (stockCount) => {
        // 检查库存是否小于 0，如果小于 0 则返回 0，否则返回实际库存
        return stockCount < 0? 0 : stockCount;
      };
    }
  },
  methods: {
    // 获取秒杀商品数据
    fetchSeckillProducts() {
      this.$axios
          .get('/api/user/seckill/secGoods')
          .then((response) => {
            if (response.status === 200) {
              this.seckillProducts = response.data;
            } else {
              this.$message.error(response.statusText);
            }
          })
          .catch((error) => {
            console.error(error);
            this.$message.error('获取秒杀商品列表失败');
          });
    },
    // 获取倒计时
    getCountdown(startDate, endDate) {
      startDate = this.convertToDate(startDate);
      endDate = this.convertToDate(endDate);
      const now = new Date().getTime();
      const startDiff = startDate - now;
      const endDiff = endDate - now;

      if (startDiff > 0) {
        return this.formatTime(startDiff); // 未开始
      } else if (endDiff > 0) {
        return '秒杀进行中'; // 已开始
      } else {
        return '活动已结束'; // 已结束
      }
    },
    // 格式化时间
    formatTime(ms) {
      const seconds = Math.floor(ms / 1000) % 60;
      const minutes = Math.floor(ms / (1000 * 60)) % 60;
      const hours = Math.floor(ms / (1000 * 60 * 60)) % 24;
      const days = Math.floor(ms / (1000 * 60 * 60 * 24));
      return `${days > 0 ? days + '天 ' : ''}${hours}小时 ${minutes}分钟 ${seconds}秒`;
    },
    // 判断秒杀是否可用
    isSeckillAvailable(startDate, endDate) {
      startDate = this.convertToDate(startDate);
      endDate = this.convertToDate(endDate);
      const now = new Date().getTime();
      return now >= startDate && now <= endDate;
    },

    // 将后端传来的日期数组转换为 JavaScript 可解析的日期格式
    convertToDate(dateArray) {
      // 假设 dateArray 是一个 [year, month, day, hour, minute] 格式
      // 注意：JavaScript 中的月份是 0-11 所以需要减 1
      const [year, month, day, hour, minute] = dateArray;
      return new Date(year, month - 1, day, hour, minute).getTime();
    },
    // 更新倒计时定时器
    startCountdownUpdate() {
      this.countdownTimer = setInterval(() => {
        this.seckillProducts = [...this.seckillProducts]; // 触发界面重新渲染
      }, 1000); // 每秒更新一次
    },
    // 立即抢购操作
    handlePurchase(productId) {
      // 防止重复点击
      if (this.buttonLoading[productId]) return;

      this.$set(this.buttonLoading, productId, true); // 设置按钮加载状态
      const url = `/api/user/seckill/sec?goodsId=${productId}`; // 使用GET请求并传递参数

      this.$axios
          .get(url) // 使用 GET 请求
          .then((response) => {
            if (response.status === 200) {
              this.$message.success(`秒杀商品${productId}成功`);
              this.fetchSeckillProducts(); // 更新商品列表
            } else if (response.status === 400) {
              this.$message.error('商品的库存量没有剩余或商品不存在,秒杀结束');
            } else if (response.status === 404) {
              this.$message.error('商品不存在');
            } else {
              this.$message.error(response.statusText);
            }
          })
          .catch((error) => {
            console.error(error);
            this.$message.error(error.response.data);
          })
          .finally(() => {
            this.$set(this.buttonLoading, productId, false); // 恢复按钮状态
          });
    },
  },
};
</script>

<style scoped>
.seckill-list {
  padding: 20px;
}

.product-card {
  cursor: pointer;
  margin-bottom: 20px;
}

.product-card:hover {
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.2);
}

.product-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.product-info {
  text-align: center;
}

.product-name {
  font-size: 16px;
  margin: 10px 0;
}

.seckill-price {
  color: red;
  font-size: 18px;
}
</style>