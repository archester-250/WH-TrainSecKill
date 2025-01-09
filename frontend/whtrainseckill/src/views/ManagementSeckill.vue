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
            <el-table-column 
                prop="startDate" 
                label="开始时间" 
                width="180"
                :formatter="formatDate">
            </el-table-column>
            <el-table-column 
                prop="endDate" 
                label="结束时间" 
                width="180"
                :formatter="formatDate">
            </el-table-column>
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
                    <el-date-picker v-model="form.startTime" type="datetime" placeholder="选择开始时间" format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间">
                    <el-date-picker v-model="form.endTime" type="datetime" placeholder="选择结束时间" format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
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
                goodsId: 0,
                seckillPrice: null,
                stockCount: 0,
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

        // 格式化日期
        formatDate(row, column, cellValue, index) {
            if (!cellValue) return '';
            const date = this.convertToDate(cellValue);
            return date.toLocaleString(); // 或者使用自定义格式
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
            this.form = { ...row, startTime: this.convertToDate(row.startDate), endTime: this.convertToDate(row.endDate) };
            this.dialogVisible = true;
        },

        // 删除秒杀商品
        handleDelete(id) {
            this.$confirm('确认删除此秒杀活动吗?', '警告', {
                type: 'warning'
            }).then(() => {
                this.$axios
                    .delete('/api/admin/admin/delete', { data: { id } })  // 向后端发送删除请求
                    .then((response) => {
                        if (response.status === 200) {
                            this.seckillGoodsList = this.seckillGoodsList.filter(item => item.id !== id);
                            this.$message.success('删除成功');
                        } else {
                            this.$message.error(response.data.message);
                        }
                    })
                    .catch((error) => {
                        console.error(error);
                        this.$message.error('删除失败');
                    });
            }).catch(() => { });
        },

        // 提交表单（新增或编辑）
        handleSubmit() {
            const payload = {
                goodsId: this.form.goodsId,
                seckillPrice: parseFloat(this.form.seckillPrice),
                stockCount: this.form.stockCount,
                startTime: this.form.startTime,
                endTime: this.form.endTime
            };

            if (this.isEditMode) {
                // 编辑
                payload.id = this.form.id;  // 需要传递 id 进行更新

                this.$axios
                    .put('/api/admin/admin/update', { seckillGoods: payload })  // 更新秒杀商品
                    .then((response) => {
                        if (response.status === 200) {
                            const index = this.seckillGoodsList.findIndex(item => item.id === this.form.id);
                            if (index !== -1) {
                                this.seckillGoodsList[index] = { ...this.form, startDate: this.form.startTime, endDate: this.form.endTime };
                            }
                            this.$message.success('更新成功');
                            this.dialogVisible = false;
                        } else {
                            this.$message.error(response.data.message);
                        }
                    })
                    .catch((error) => {
                        console.error(error);
                        this.$message.error('更新失败');
                    });

            } else {
                // 新增
                this.$axios
                    .post('/api/admin/admin/create', { seckillGoods: payload })  // 创建秒杀商品
                    .then((response) => {
                        if (response.status === 200) {
                            this.form.id = response.data.message.split('：')[1]; // 假设返回消息包含创建的ID
                            this.seckillGoodsList.push({
                                ...this.form,
                                startDate: this.form.startTime,
                                endDate: this.form.endTime
                            });
                            this.$message.success('创建成功');
                            this.dialogVisible = false;
                        } else {
                            this.$message.error(response.data.message);
                        }
                    })
                    .catch((error) => {
                        console.error(error);
                        this.$message.error('创建失败');
                    });
            }
        },
        // 将后端传来的日期数组转换为 JavaScript 可解析的日期格式
        convertToDate(dateArray) {
            // 假设 dateArray 是一个 [year, month, day, hour, minute] 格式
            // 注意：JavaScript 中的月份是 0-11 所以需要减 1
            const [year, month, day, hour, minute] = dateArray;
            return new Date(year, month - 1, day, hour, minute);
        },
    },

    mounted() {
        this.fetchSeckillGoods(); // 页面加载时获取秒杀商品
    }
};
</script>
