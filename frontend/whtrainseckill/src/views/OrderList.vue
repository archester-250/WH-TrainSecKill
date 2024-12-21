<template>
    <div class="order-list">
        <h2>我的订单</h2>
        <el-table :data="orders" border style="width: 100%">
            <el-table-column prop="orderNo" label="订单编号" width="200" />
            <el-table-column prop="productName" label="商品信息" />
            <el-table-column prop="status" label="订单状态" />
            <el-table-column prop="createdAt" label="下单时间" width="200" />
            <el-table-column label="操作" width="150">
                <template #default="{ row }">
                    <el-button v-if="row.status === '待发货'" type="danger" size="small" @click="cancelOrder(row.id)">
                        取消订单
                    </el-button>
                    <el-button type="primary" size="small" @click="viewDetail(row.id)">
                        查看详情
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination @current-change="handlePageChange" :current-page="currentPage" :page-size="pageSize"
                layout="prev, pager, next" :total="total" />
        </div>
    </div>
</template>

<script>
export default {
    name: 'OrderList',
    data() {
        return {
            orders: [], // 订单数据
            currentPage: 1,
            pageSize: 10,
            total: 0, // 总订单数
        };
    },
    mounted() {
        this.fetchOrders();
    },
    methods: {
        fetchOrders() {
            const params = {
                page: this.currentPage,
                size: this.pageSize,
            };
            this.$axios
                .get('/api/user/orders', { params })
                .then((response) => {
                    if (response.status === 200) {
                        this.orders = response.data.content;
                        this.total = response.data.totalElements;
                    } else {
                        this.$message.error(response.statusText);
                    }
                })
                .catch((error) => {
                    console.error(error);
                    this.$message.error('获取订单列表失败');
                });
        },
        handlePageChange(page) {
            this.currentPage = page;
            this.fetchOrders();
        },
        cancelOrder(orderId) {
            this.$confirm('确认取消订单吗？', '提示', {
                type: 'warning',
            })
                .then(() => {
                    this.$axios
                        .post(`/api/user/orders/${orderId}/cancel`)
                        .then((response) => {
                            if (response.status === 200) {
                                this.$message.success('订单取消成功');
                                this.fetchOrders();
                            } else {
                                this.$message.error(response.statusText);
                            }
                        })
                        .catch((error) => {
                            console.error(error);
                            this.$message.error('取消订单失败');
                        });
                })
                .catch(() => {
                    this.$message.info('已取消操作');
                });
        },
        viewDetail(orderId) {
            this.$router.push(`/order/${orderId}`);
        },
    },
};
</script>

<style scoped>
.order-list {
    padding: 20px;
}

.pagination {
    text-align: center;
    margin-top: 20px;
}
</style>