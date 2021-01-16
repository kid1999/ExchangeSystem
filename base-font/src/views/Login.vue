/**
* @auther: kid1999
* @date: 2021/1/4 19:50
* @desciption:  Login
*/
<template>
    <div>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="账户" prop="userName">
                <el-input type="text" v-model="ruleForm.userName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="userPwd">
                <el-input type="password" v-model="ruleForm.userPwd" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
                <el-link @click="postGithub" :underline="false">
                    <svg t="1610765696151" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="947" width="32" height="32"><path d="M512 64C264.6 64 64 269.8 64 523.4c0 203 128.4 375 306.4 435.8 2.8 0.6 5.2 0.8 7.6 0.8 16.6 0 23-12.2 23-22.8 0-11-0.4-39.8-0.6-78.2-16.8 3.8-31.8 5.4-45.2 5.4-86.2 0-105.8-67-105.8-67-20.4-53-49.8-67.2-49.8-67.2-39-27.4-0.2-28.2 2.8-28.2h0.2c45 4 68.6 47.6 68.6 47.6 22.4 39.2 52.4 50.2 79.2 50.2 21 0 40-6.8 51.2-12 4-29.6 15.6-49.8 28.4-61.4-99.4-11.6-204-51-204-227 0-50.2 17.4-91.2 46-123.2-4.6-11.6-20-58.4 4.4-121.6 0 0 3.2-1 10-1 16.2 0 52.8 6.2 113.2 48.2 35.8-10.2 74-15.2 112.2-15.4 38 0.2 76.4 5.2 112.2 15.4 60.4-42 97-48.2 113.2-48.2 6.8 0 10 1 10 1 24.4 63.2 9 110 4.4 121.6 28.6 32.2 46 73.2 46 123.2 0 176.4-104.8 215.2-204.6 226.6 16 14.2 30.4 42.2 30.4 85 0 61.4-0.6 111-0.6 126 0 10.8 6.2 23 22.8 23 2.4 0 5.2-0.2 8-0.8C831.8 898.4 960 726.2 960 523.4 960 269.8 759.4 64 512 64z" p-id="948"></path></svg>
                </el-link>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    import { get, post,put,deleted } from '@/utils/request'
    export default {
        name: "Login",
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
                ruleForm: {
                    userPwd: '1111',
                    userName: '111'
                },
                rules: {
                    userPwd: [
                        { required: true, validator: validatePass, trigger: 'blur' }
                    ],
                    userName: [
                        { required: true, validator: checkName, trigger: 'blur' }
                    ]
                }
            };
        },
        methods: {
            postGithub(){
                post('/oauth/login/github', this.ruleForm).then(res => {
                    if(res['status'] === 200) {
                        this.$message.success("登录成功！");
                        console.info(res['data']);
                        this.$store.commit('$_setUser', {user: res['data']});
                        this.$router.push({name: 'GoodsList'});
                        console.info(this.$store.getters.getUser['user']);
                    }
                });
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        post('/user/login', this.ruleForm).then(res => {
                            if(res['status'] === 200) {
                                this.$message.success("登录成功！");
                                console.info(res['data']);
                                this.$store.commit('$_setUser', {user: res['data']});
                                this.$router.push({name: 'GoodsList'});
                                console.info(this.$store.getters.getUser['user']);
                            }
                        });
                    } else {
                        this.$message.error("登录失败，请重试！");
                        return false;
                    }
                });
            },
        }
    }
</script>