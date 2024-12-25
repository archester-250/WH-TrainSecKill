<template>
    <div class="order-list">
        <h2>我的订单</h2>
        <el-table :data="orders" border style="width: 100%">
            <el-table-column prop="id" label="订单编号" width="200" />
            <el-table-column prop="goodsName" label="商品信息" />
            <el-table-column label="订单状态">
                <template #default="scope">
                    <el-tag :type="getTagType(scope.row.status)">
                        {{ getStatusText(scope.row.status) }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="createDate" label="下单时间" width="200" />
            <el-table-column label="操作" width="150">
                <template #default="{ row }">
                    <el-button v-if="row.status == 1 || row.status == 2" type="danger" size="small"
                        @click="cancelOrder(row.id)">
                        取消订单
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
            tableData: [
                { id: 1, status: 1 },
                { id: 2, status: 2 },
                { id: 3, status: 3 },
                { id: 4, status: 4 },
            ],
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
        getStatusText(status) {
            const statusMap = {
                1: "待付款",
                2: "待发货",
                3: "正在运送",
                4: "取消",
            };
            return statusMap[status] || "未知状态";
        },
        getTagType(status) {
            const tagTypeMap = {
                1: "warning", // 黄色标签
                2: "info",    // 蓝色标签
                3: "success", // 绿色标签
                4: "danger",  // 红色标签
            };
            return tagTypeMap[status] || "default"; // 默认标签颜色
        },
        fetchOrders() {
            const params = {
                page: this.currentPage,
                size: this.pageSize,
            };
            this.$axios
                .get('/api/user/order', { params })
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
                        .post(`/api/user/order/cancel/${orderId}`)
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
        // viewDetail(orderId) {
        //     this.$router.push(`/order/${orderId}`);
        // },
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