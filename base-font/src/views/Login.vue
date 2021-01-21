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
                    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="70px" class="demo-ruleForm">
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
                            <el-button @click="postGithub">Github登录</el-button> |
                            <el-link href="/forgetPass" :underline="false">
                                忘记密码？
                            </el-link>
                        </el-form-item>
                    </el-form>
                </div>
            </el-card>
        </el-col>
    </el-row>

</template>
<script>
    import { get, post,put,deleted } from '@/utils/request'
    import Verify from 'vue2-verify'
    import qs from 'qs';
    import jwt from 'jsonwebtoken'
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
                        { required: true, validator: validatePass, trigger: 'blur' }
                    ],
                    password: [
                        { required: true, validator: checkName, trigger: 'blur' }
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
                post('/oauth/login/github', this.ruleForm).then(res => {
                    if(res['status'] === 200) {
                        this.$message.success("登录成功！");
                        console.info(res['data']);
                        this.$store.commit('$_setUser', {user: res['data']});
                        this.$router.push({name: 'GoodsList'});
                    }
                });
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if(!this.isVerify){
                            this.$message.error("请输入验证码重试！");
                            return false;
                        }
                        let store = this.$store.getters.getUser;
                        let token = localStorage.getItem("access_token");
                        if(token == null || store == null){
                            // 'Content-Type': 'application/x-www-form-urlencoded'
                            // 先获取Token
                            post('/oauth/token', qs.stringify(this.ruleForm)).then(res => {
                                localStorage.setItem("access_token",res.access_token);
                                localStorage.setItem("refresh_token",res.refresh_token);
                                let name = jwt.decode(res.access_token).user_name;
                                // 再请求数据
                                get('/user/name/' + name, {}).then(res => {
                                    if(res['status'] === 200) {
                                        this.$message.success("登录成功！");
                                        console.info(res['data']);
                                        this.$store.commit('$_setUser', {user: res['data']});
                                        this.$router.push({name: 'GoodsList'});
                                        location.reload();
                                    }
                                });
                            }).catch(e =>{
                                console.info(e);
                                this.$message.error("登录失败，请重试！");
                            });
                        } else {
                            let name = jwt.decode(token).user_name;
                            if(store.user.username === name){
                                this.$message.error("该用户已登录，请勿再次尝试！");
                                return false;
                            }
                            post('/oauth/token', qs.stringify(this.ruleForm)).then(res => {
                                localStorage.setItem("access_token",res.access_token);
                                localStorage.setItem("refresh_token",res.refresh_token);
                                let name = jwt.decode(token).user_name;
                                console.info(name);
                                // 再请求数据
                                get('/user/name/' + name, {}).then(res => {
                                    if(res['status'] === 200) {
                                        this.$message.success("登录成功！");
                                        console.info(res['data']);
                                        this.$store.commit('$_setUser', {user: res['data']});
                                        this.$router.push({name: 'GoodsList'});
                                        location.reload();
                                    }
                                });
                            }).catch(e =>{
                                console.info(e);
                                this.$message.error("登录失败，请重试！");
                            });
                        }

                    } else {
                        this.$message.error("登录失败，请重试！");
                        return false;
                    }
                });
            },
        }
    }
</script>