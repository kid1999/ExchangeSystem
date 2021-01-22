/**
* @auther: kid1999
* @date: 2021/1/22 22:27
* @desciption:  ChangePassword
*/
<template>
    <el-row :gutter="20">
        <el-col :span="12" :offset="4">
            <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <h2>找回密码</h2>
                </div>
                <div>
                    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm">
                        <el-form-item label="账户" prop="username">
                            <el-input type="text" v-model="ruleForm.username" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="新密码" prop="password">
                            <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="再次输入密码" prop="password2">
                            <el-input type="password" v-model="ruleForm.password2" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="获取验证码" label-width="100px">
                            <Verify :type="3" :showButton="false" @success="verifyed"></Verify>
                        </el-form-item>
                        <el-form-item label="验证码" prop="checkCode" label-width="70px">
                            <el-input type="text" v-model="ruleForm.checkCode" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="submitForm('ruleForm')">修改密码</el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </el-card>
        </el-col>
    </el-row>
</template>

<script>
    import { get, post,put,getToken } from '@/utils/request'
    import Verify from 'vue2-verify'
    import qs from 'qs';
    export default {
        name: "ChangePassword",
        components: {Verify},
        data() {
            const checkName = async (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('用户名不能为空'));
                }
                if (value.length <= 2) {
                    callback(new Error('用户名长度必须大于2'));
                }
                callback();
            };
            const validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else if(value.length < 4){
                    callback(new Error('密码长度不能少于4'));
                }else{
                    callback();
                }
            };
            const validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else if(value !== this.ruleForm.password){
                    callback(new Error('前后密码不一致！'));
                }else{
                    callback();
                }
            };
            const validateCheckCode = (rule, value, callback) => {
                if(value.length !== 6){
                    callback(new Error('正确的验证码长度为6'));
                }else{
                    callback();
                }
            };

            return {
                isVerify:false,
                ruleForm: {
                    username: '1111',
                    password: '1111',
                    password2:'1111',
                    checkCode:''
                },
                rules: {
                    username: [
                        { required: true, validator: checkName, trigger: 'blur' }
                    ],
                    password: [
                        { required: true, validator: validatePass, trigger: 'blur' }
                    ],
                    password2: [
                        { required: true, validator: validatePass2, trigger: 'blur' }
                    ],
                    checkCode: [
                        { required: true, validator: validateCheckCode, trigger: 'blur' }
                    ]
                }
            };
        },
        methods: {
            verifyed() {
                this.isVerify = true;
                post('/user/sendCheckCode', this.ruleForm).then(res => {
                    this.$notify.success({
                        title: '成功',
                        message: '验证码已发送本账号绑定的邮箱中，请注意查收！'
                    });
                }).catch(e =>{
                    this.$notify.error({
                        title: '失败',
                        message: '该账户未绑定邮箱，无法修改密码！'
                    });
                })
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (!this.isVerify) {
                            this.$message.error("请输入验证码重试！");
                            return false;
                        }
                        post('/user/changePassword', this.ruleForm).then(res => {
                            this.$notify.success({
                                title: '成功',
                                message: '密码修改成功！'
                            });
                        }).catch(e =>{
                            this.$notify.error({
                                title: '失败',
                                message: '验证码错误！'
                            });
                        })
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>