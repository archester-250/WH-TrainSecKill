<template>
    <div class="cart-page">
        <h2>购物车</h2>
        <!-- 空购物车提示 -->
        <div v-if="cartItems.length === 0" class="empty-cart">
            <p>购物车为空，去 <router-link to="/products">逛逛</router-link> 吧！</p>
        </div>
        <!-- 购物车商品列表 -->
        <el-table v-if="cartItems.length > 0" :data="cartItems" stripe>
            <!-- <el-table-column label="商品图片" width="120">
                <template #default="{ row }">
                    <img :src="row.img" class="cart-item-image" />
                </template>
            </el-table-column> -->
            <el-table-column label="商品名称" prop="productName" />
            <el-table-column label="单价" prop="price" />
            <el-table-column label="数量">
                <template #default="{ row }">
                    <el-input-number v-model="row.quantity" :min="1" :max="row.stock"
                        @change="updateCart(row.id, row.quantity)"></el-input-number>
                </template>
            </el-table-column>
            <el-table-column label="小计">
                <template #default="{ row }">
                    ￥{{ (row.price * row.quantity).toFixed(2) }}
                </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button type="danger" size="small" @click="removeFromCart(row.id)">
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 购物车结算 -->
        <div class="cart-footer" v-if="cartItems.length > 0">
            <p>总计：<span class="total-price">￥{{ totalPrice }}</span></p>
            <el-button type="primary" size="large" @click="checkout">
                结算
            </el-button>
        </div>
    </div>
</template>

<script>
export default {
    name: "Cart",
    data() {
        return {
            cartItems: [],
        };
    },
    computed: {
        totalPrice() {
            return this.cartItems.reduce(
                (total, item) => total + item.price * item.quantity,
                0
            );
        },
    },
    mounted() {
        this.fetchCartItems();
    },
    methods: {
        fetchCartItems() {
            this.$axios.get("/api/user/cart")
                .then(response => {
                    if (response.status === 200) {
                        this.cartItems = response.data;
                    } else {
                        this.$message.error(response.statusText);
                    }
                })
                .catch(error => {
                    console.error(error);
                    this.$message.error("获取购物车失败");
                });
        },
        updateCart(productId, quantity) {
            this.$axios.put(`/api/user/cart/update?productId=${productId}&newQuantity=${quantity}`)
                .then(response => {
                    if (response.status === 200) {
                        this.$message.success("购物车已更新");
                        this.fetchCartItems();
                    } else {
                        this.$message.error(response.statusText);
                    }
                })
                .catch(error => {
                    console.error(error);
                    this.$message.error("更新购物车失败");
                });
        },
        removeFromCart(productId) {
            this.$axios.delete(`/api/user/cart/remove?productId=${productId}`)
                .then(response => {
                    if (response.status === 200) {
                        this.$message.success("商品已移出购物车");
                        this.fetchCartItems();
                    } else {
                        this.$message.error(response.statusText);
                    }
                })
                .catch(error => {
                    console.error(error);
                    this.$message.error("删除商品失败");
                });
        },
        checkout() {
            this.$axios.post("/api/user/cart/checkout")
                .then(response => {
                    if (response.status === 200) {
                        this.$message.success("结算成功！订单已生成");
                        this.cartItems = []; // 清空购物车
                        this.$router.push("/order-success"); // 跳转到订单成功页
                    } else {
                        this.$message.error(response.statusText);
                    }
                })
                .catch(error => {
                    console.error(error);
                    this.$message.error("结算失败，请稍后再试");
                });
        },

    },
};
</script>

<style scoped>
.cart-page {
    padding: 20px;
}

.cart-item-image {
    width: 100px;
    height: 100px;
    object-fit: cover;
}

.cart-footer {
    margin-top: 20px;
    text-align: right;
}

.total-price {
    font-size: 18px;
    color: red;
    margin-right: 20px;
}

.empty-cart {
    text-align: center;
    margin-top: 50px;
    font-size: 16px;
}
</style>