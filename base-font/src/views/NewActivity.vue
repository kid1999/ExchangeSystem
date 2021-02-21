/**
* @auther: kid1999
* @date: 2021/2/21 14:02
* @desciption:  NewActivity
*/
<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <h2>{{title}}</h2>
            </div>
            <el-form :model="activity" status-icon ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="活动名" prop="activityTitle" :rules="[{ required: true, message: '活动名不能为空'}]">
                    <el-input type="text" v-model="activity.activityTitle" autocomplete="off" maxlength="30"
                              show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="活动详情" prop="activityContext">
                    <el-input type="text" v-model="activity.activityContext" autocomplete="off" maxlength="120"
                              show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="所在地区" prop="address">
                    <el-input type="text" v-model="address.address" autocomplete="off" disabled ></el-input>
                </el-form-item>
                <el-form-item label="详细地址" prop="detailAddress" :rules="[{ required: true, message: '详细地址不能为空'}]">
                    <el-input type="text" v-model="activity.detailAddress" autocomplete="off" maxlength="70"
                              show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="活动时间">
                    <el-date-picker
                            v-model="dateTime"
                            type="datetimerange"
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期">
                    </el-date-picker>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="updateGoods('ruleForm')" >保存</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script>
    import { get, post,put,deleted } from '../utils/request'
    export default {
        name: "NewActivity",
        data() {
            return {
                title:'发起活动',
                user: {},
                canChangeable: true,
                id: '',
                dialogVisible: false,
                activity: {},
                address:{},
                dateTime:[new Date(),new Date()],
            }
        },
        created() {
            this.user = this.$store.getters.getUser['user'];
            get('/address/' + this.user.addressId, {}).then(res => {
                if(res['status'] === 200) {
                    console.info(res);
                    this.address = res['data'];
                }
            });
            this.activity.createUserId = this.user.id;
            this.activity.addressId = this.user.addressId;
        },
        methods: {
            updateGoods(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if(this.dateTime[1] <= new Date()){
                            this.$message.error("请填写正确的活动时间！");
                            return;
                        }else {
                            this.activity.startTime = this.dateTime[0];
                            this.activity.endTime = this.dateTime[1];
                        }
                        post('/activity', this.activity)
                            .then(res => {
                                this.$notify.success("保存成功！")
                            });
                    }else{
                        this.$message.error("请完善各种信息！");
                    }
                })
            }
        },
    }
</script>

<style scoped>

</style>