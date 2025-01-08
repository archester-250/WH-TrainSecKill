<template>
    <div class="seckill-app">
        <el-row>
            <el-col :span="24">
                <el-button type="primary" @click="handleAdd">新增秒杀活动</el-button>
            </el-col>
        </el-row>
        <el-table :data="seckillGoodsList" style="width: 100%" border>
            <el-table-column prop="id" label="ID" width="80"></el-table-column>
            <el-table-column prop="goodsId" label="商品ID" width="150"></el-table-column>
            <el-table-column prop="seckillPrice" label="秒杀价格" width="150"></el-table-column>
            <el-table-column prop="stockCount" label="库存数量" width="150"></el-table-column>
            <el-table-column prop="startDate" label="开始时间" width="180"></el-table-column>
            <el-table-column prop="endDate" label="结束时间" width="180"></el-table-column>
            <el-table-column label="操作" width="200">
                <template slot-scope="scope">
                    <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 弹窗表单 -->
        <el-dialog title="编辑秒杀商品" :visible.sync="dialogVisible">
            <el-form :model="form" ref="form" label-width="100px">
                <el-form-item label="商品ID" :label-width="formLabelWidth">
                    <el-input v-model="form.goodsId" :disabled="isEditMode"></el-input>
                </el-form-item>
                <el-form-item label="秒杀价格">
                    <el-input v-model="form.seckillPrice"></el-input>
                </el-form-item>
                <el-form-item label="库存数量">
                    <el-input v-model="form.stockCount"></el-input>
                </el-form-item>
                <el-form-item label="开始时间">
                    <el-date-picker v-model="form.startTime" type="datetime" placeholder="选择开始时间"></el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间">
                    <el-date-picker v-model="form.endTime" type="datetime" placeholder="选择结束时间"></el-date-picker>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit">确认</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            seckillGoodsList: [], // 存放秒杀商品列表
            form: {
                id: null,
                goodsId: '',
                seckillPrice: '',
                stockCount: '',
                startTime: '',
                endTime: ''
            },
            dialogVisible: false, // 控制弹窗显示
            isEditMode: false, // 标志是否是编辑模式
            formLabelWidth: '100px'
        };
    },
    methods: {
        // 获取秒杀商品列表
        fetchSeckillGoods() {
            this.$axios
                .get('/api/user/seckill/secGoods')
                .then((response) => {
                    if (response.status === 200) {
                        this.seckillGoodsList = response.data;
                    } else {
                        this.$message.error(response.statusText);
                    }
                })
                .catch((error) => {
                    console.error(error);
                    this.$message.error('获取秒杀商品列表失败');
                });
        },
        // 打开新增弹窗
        handleAdd() {
            this.isEditMode = false;
            this.form = {
                id: null,
                goodsId: '',
                seckillPrice: '',
                stockCount: '',
                startTime: '',
                endTime: ''
            };
            this.dialogVisible = true;
        },
        // 打开编辑弹窗
        handleEdit(row) {
            this.isEditMode = true;
            this.form = { ...row, startTime: new Date(row.startDate), endTime: new Date(row.endDate) };
            this.dialogVisible = true;
        },
        // 删除秒杀商品
        handleDelete(id) {
            this.$confirm('确认删除此秒杀活动吗?', '警告', {
                type: 'warning'
            }).then(() => {
                this.seckillGoodsList = this.seckillGoodsList.filter(item => item.id !== id);
                this.$message.success('删除成功');
            }).catch(() => { });
        },
        // 提交表单（新增或编辑）
        handleSubmit() {
            if (this.isEditMode) {
                // 编辑
                const index = this.seckillGoodsList.findIndex(item => item.id === this.form.id);
                if (index !== -1) {
                    this.seckillGoodsList[index] = { ...this.form, startDate: this.form.startTime, endDate: this.form.endTime };
                    this.$message.success('更新成功');
                }
            } else {
                // 新增
                this.form.id = this.seckillGoodsList.length + 1; // 模拟生成ID
                this.seckillGoodsList.push({
                    ...this.form,
                    startDate: this.form.startTime,
                    endDate: this.form.endTime
                });
                this.$message.success('创建成功');
            }
            this.dialogVisible = false;
        }
    },
    mounted() {
        this.fetchSeckillGoods(); // 页面加载时获取秒杀商品
    }
};
</script>

<style scoped>
.seckill-app {
    padding: 20px;
}
</style>