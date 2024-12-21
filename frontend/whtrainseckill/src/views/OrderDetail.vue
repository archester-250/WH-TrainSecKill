<template>
    <div class="order-detail">
        <el-card>
            <h2>订单编号：{{ order.orderNo }}</h2>
            <p>订单状态：{{ order.status }}</p>
            <p>下单时间：{{ order.createdAt }}</p>
            <p>支付金额：￥{{ order.totalPrice }}</p>
        </el-card>
        <el-table :data="order.products" border style="margin-top: 20px;">
            <el-table-column prop="name" label="商品名称" />
            <el-table-column prop="quantity" label="数量" />
            <el-table-column prop="price" label="单价" />
        </el-table>
    </div>
</template>

<script>
export default {
    name: 'OrderDetail',
    data() {
        return {
            order: {}, // 订单详情
        };
    },
    mounted() {
        this.fetchOrderDetail();
    },
    methods: {
        fetchOrderDetail() {
            const orderId = this.$route.params.id;
            this.$axios
                .get(`/api/user/orders/${orderId}`)
                .then((response) => {
                    if (response.status === 200) {
                        this.order = response.data;
                    } else {
                        this.$message.error(response.statusText);
                    }
                })
                .catch((error) => {
                    console.error(error);
                    this.$message.error('获取订单详情失败');
                });
        },
    },
};
</script>

<style scoped>
.order-detail {
    padding: 20px;
}
</style>