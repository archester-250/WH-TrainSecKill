<template>
    <div class="order-success">
        <h2>订单已生成</h2>
        <p>感谢您的购买！我们会尽快发货。</p>
        <router-link to="/products">
            <el-button type="primary">继续购物</el-button>
        </router-link>
    </div>
</template>

<style scoped>
.order-success {
    text-align: center;
    margin-top: 50px;
}
</style>
