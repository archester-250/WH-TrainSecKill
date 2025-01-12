<template>
  <div class="product-detail">
    <!-- 面包屑导航（可选） -->
    <div class="breadcrumb">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><router-link to="/">首页</router-link></el-breadcrumb-item>
        <el-breadcrumb-item>商品详情</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 商品信息区域 -->
    <div class="product-info">
      <!-- 左侧图片展示 -->
      <div class="product-images">
        <el-image :src="mainImage" :preview-src-list="imageList" fit="cover" class="main-image"></el-image>
        <div class="thumbnail-list">
          <el-image v-for="(img, index) in imageList" :key="index" :src="img" fit="cover" class="thumbnail-image"
            @click="changeMainImage(img)"></el-image>
        </div>
      </div>
      <!-- 右侧商品信息 -->
      <div class="product-details">
        <h1 class="product-name">{{ product.name }}</h1>
        <p class="product-title">{{ product.title }}</p>
        <div class="product-price">
          <span>价格：</span>
          <span class="price">￥{{ product.price }}</span>
        </div>
        <div class="product-stock">
          库存：<span>{{ product.stock > 0 ? product.stock : '缺货' }}</span>
        </div>
        <!-- 购买数量 -->
        <div class="product-quantity">
          <span>数量：</span>
          <el-input-number v-model="quantity" :min="1" :max="product.stock"
            @change="handleQuantityChange"></el-input-number>
        </div>
        <!-- 购买按钮 -->
        <div class="purchase-buttons">
          <el-button type="primary" size="large" @click="buyNow">立即购买</el-button>
          <el-button type="warning" size="large" @click="addToCart">加入购物车</el-button>
        </div>
      </div>
    </div>
    <!-- 商品详情区域 -->
    <div class="product-description">
      <el-tabs>
        <el-tab-pane label="商品详情">
          <div v-html="product.detail"></div>
        </el-tab-pane>
        <!-- 可添加其他选项卡，如规格参数、用户评价等 -->
      </el-tabs>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProductDetail',
  data() {
    return {
      productId: this.$route.params.id,
      product: {},
      mainImage: '',
      imageList: [],
      quantity: 1
    };
  },
  mounted() {
    this.fetchProductDetail();
  },
  methods: {
    fetchProductDetail() {
      this.$axios.get(`/api/user/products/${this.productId}`)
        .then(response => {
          if (response.status === 200) {
            this.product = response.data;
            this.imageList = [this.product.img]; // 如果有多张图片，可调整此处
            this.mainImage = this.product.img;
          } else {
            this.$message.error(response.statusText);
          }
        })
        .catch(error => {
          console.error(error);
          this.$message.error('获取商品详情失败');
        });
    },
    changeMainImage(img) {
      this.mainImage = img;
    },
    handleQuantityChange(value) {
      if (value > this.product.stock) {
        this.$message.warning('超出库存数量');
        this.quantity = this.product.stock;
      }
    },
    buyNow() {
      const submitData = {
        'goodsId': this.$data.productId,
        'goodsCount': this.$data.quantity
      }
      this.$axios.post(`/api/user/order/add`, submitData)
        .then(response => {
          if (response.status === 200) {
            this.$message.success('下单成功');
            this.fetchProductDetail();
          } else {
            this.$message.error(response.statusText);
          }
        })
        .catch(error => {
          console.error(error);
          this.$message.error('下单失败');
        });
    },
    addToCart() {
      const submitData = {
        goodsId: this.productId,
        goodsCount: this.quantity,
      };
      this.$axios.post('/api/user/cart/add', submitData)
        .then(response => {
          if (response.status === 200) {
            this.$message.success('商品已加入购物车');
          } else {
            this.$message.error(response.statusText);
          }
        })
        .catch(error => {
          console.error(error);
          this.$message.error('加入购物车失败');
        });
    },

  }
};
</script>

<style scoped>
.product-detail {
  padding: 20px;
}

.breadcrumb {
  margin-bottom: 20px;
}

.product-info {
  display: flex;
}

.product-images {
  width: 400px;
  margin-right: 20px;
}

.main-image {
  width: 400px;
  height: 400px;
}

.thumbnail-list {
  display: flex;
  margin-top: 10px;
}

.thumbnail-image {
  width: 60px;
  height: 60px;
  margin-right: 10px;
  cursor: pointer;
}

.product-details {
  flex: 1;
}

.product-name {
  font-size: 24px;
  font-weight: bold;
}

.product-title {
  font-size: 16px;
  color: #666;
  margin-bottom: 20px;
}

.product-price {
  font-size: 20px;
  color: red;
  margin-bottom: 10px;
}

.product-stock {
  margin-bottom: 10px;
}

.product-quantity {
  margin-bottom: 20px;
}

.purchase-buttons .el-button {
  margin-right: 20px;
}

.product-description {
  margin-top: 40px;
}

.purchase-buttons {
  display: flex;
  gap: 10px;
}

</style>
