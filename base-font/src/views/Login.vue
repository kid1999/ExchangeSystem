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