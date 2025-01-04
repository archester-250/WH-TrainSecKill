<template>
    <div class="seckill-detail">
        <el-card>
            <img v-lazy="product.img" class="product-image" />
            <div class="product-info">
                <h2>{{ product.name }}</h2>
                <p>原价：<del>￥{{ product.originalPrice }}</del></p>
                <p>秒杀价：<span class="seckill-price">￥{{ product.seckillPrice }}</span></p>
                <p>库存：{{ product.stock }}</p>
            </div>
            <el-button type="danger" :disabled="!product.stock" @click="handlePurchase">
                立即抢购
            </el-button>
        </el-card>
    </div>
</template>

<script>
export default {
    name: 'SeckillDetail',
    data() {
        return {
            product: {}, // 秒杀商品数据
        };
    },
    mounted() {
        const productId = this.$route.params.id;
        this.fetchProductDetail(productId);
    },
    methods: {
        fetchProductDetail(productId) {
            this.$axios
                .get(`/api/seckill/products/${productId}`)
                .then((response) => {
                    if (response.status === 200) {
                        this.product = response.data;
                    } else {
                        this.$message.error(response.statusText);
                    }
                })
                .catch((error) => {
                    console.error(error);
                    this.$message.error('获取商品详情失败');
                });
        },
        handlePurchase() {
            this.$axios
                .post(`/api/seckill/purchase/${this.product.id}`)
                .then((response) => {
                    if (response.status === 200) {
                        this.$message.success('抢购成功！');
                        this.fetchProductDetail(this.product.id); // 更新库存
                    } else {
                        this.$message.error(response.statusText);
                    }
                })
                .catch((error) => {
                    console.error(error);
                    this.$message.error('抢购失败');
                });
        },
    },
};
</script>

<style scoped>
.seckill-detail {
    padding: 20px;
}

.product-image {
    width: 100%;
    height: 300px;
    object-fit: cover;
}

.product-info {
    margin: 20px 0;
}

.seckill-price {
    color: red;
    font-size: 24px;
}
</style>