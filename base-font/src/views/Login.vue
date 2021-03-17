/**
* @auther: kid1999
* @date: 2021/1/4 19:50
* @desciption:  Login
*/
<template>
    <el-row :gutter="20">
        <el-col :span="12" :offset="4">
            <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <h2>登 录</h2>
                </div>
                <div>
                    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="60px" class="demo-ruleForm">
                        <el-form-item label="账户" prop="username">
                            <el-input type="text" v-model="ruleForm.username" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="密码" prop="password">
                            <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="验证码" >
                            <Verify :type="3" :showButton="false" @success="verifyed"></Verify>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button> |
                            <router-link to="https://github.com/login/oauth/authorize?client_id=Iv1.72e9ce18fb4a7044&redirect_uri=http://localhost:12000/api/oauth/callback&scope=user&state=1">
                            <el-button>Github登录</el-button>
                            </router-link> |
                            <router-link to="ChangePassword">
                            <el-link href="/ChangePassword" :underline="false">
                                忘记密码？
                            </el-link>
                            </router-link>
                        </el-form-item>
                    </el-form>
                </div>
            </el-card>
        </el-col>
    </el-row>
</template>
<script>
    import { get, post,put,getToken,postGithub } from '@/utils/request'
    import Verify from 'vue2-verify'
    import qs from 'qs';
    import jwt from 'jsonwebtoken';
    import { globalBus } from '@/utils/globalBus';
    export default {
        name: "Login",
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
            return {
                isVerify:false,
                ruleForm: {
                    username: '1111',
                    password: '1111',
                    grant_type:'password',
                    scope:'all'
                },
                rules: {
                    username: [
                        { required: true, validator: checkName, trigger: 'blur' }
                    ],
                    password: [
                        { required: true, validator: validatePass, trigger: 'blur' }
                    ]
                }
            };
        },
        methods: {
           verifyed() {
                this.isVerify = true;
                this.$message.success("验证成功")
            },
            postGithub(){
                postGithub(this.ruleForm).then(res =>{
                    this.$message.success("登录成功！");
                    console.info(res['data']);
                    this.$store.commit('$_setUser', {user: res['data']});
                    this.$router.push({name: 'GoodsList'});
                })
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (!this.isVerify) {
                            this.$message.error("请输入验证码重试！");
                            return false;
                        }
                        localStorage.removeItem("access_token");
                        localStorage.removeItem("refresh_token");
                        getToken(qs.stringify(this.ruleForm)).then(res => {
                            localStorage.setItem("access_token",res.access_token);
                            localStorage.setItem("refresh_token",res.refresh_token);
                            // 再请求数据
                            get('/user', {}).then(res => {
                                console.info( res['data'])
                                this.$message.success("登录成功！");
                                this.$store.commit('$_setUser', {user: res['data'][0]});
                                localStorage.setItem("address",JSON.stringify(res['data'][1]));
                                localStorage.setItem("contactWay",JSON.stringify(res['data'][2]));
                                this.$router.push({name: 'GoodsList'});
                                this.$options.methods.RefreshUser(res['data']);
                            }).catch(e =>{
                                console.error("获取个人信息失败！")
                            })
                        }).catch(e =>{
                            this.$message.error("登录失败，账号密码错误！");
                        });
                    }
                })
            },
            RefreshUser(data) {
                globalBus.$emit("RefreshUser", data);
            },
        }
    }
</script>