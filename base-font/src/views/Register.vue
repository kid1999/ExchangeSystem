/**
* @auther: kid1999
* @date: 2021/1/4 19:50
* @desciption:  Register
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
            <el-form-item label="确认密码" prop="userPwd2">
                <el-input type="password" v-model="ruleForm.userPwd2" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="userPwd2">
                <el-input type="password" v-model="ruleForm.userPwd2" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="所在地区">
                <el-col :span="6">
                <el-cascader
                        size="large"
                        :options="options"
                        v-model="selectedOptions">
                </el-cascader>
                </el-col>
            </el-form-item>


            <el-form-item label="手机号" prop="phone">
                <el-input type="text" v-model="ruleForm.phone" autocomplete="off"></el-input>
            </el-form-item>


            <el-form-item label="邮箱" prop="email">
                <el-input type="text" v-model="ruleForm.email" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="QQ" prop="qq">
                <el-input type="text" v-model="ruleForm.qq" autocomplete="off"></el-input>
            </el-form-item>



            <el-form-item label="用户头像" prop="avatarUrl">
                <el-upload
                        action="#"
                        list-type="picture-card"
                        :limit="1"
                        :auto-upload="false"
                        :on-preview="handlePictureCardPreview"
                        :on-remove="handleRemove">
                    <i class="el-icon-plus"></i>
                </el-upload>
                <el-dialog :visible.sync="dialogVisible">
                    <img width="100%" :src="dialogImageUrl" alt="">
                </el-dialog>

            </el-form-item>




            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    import { get, post,put,deleted } from '../utils/request'
    import { regionData } from 'element-china-area-data'
    export default {
        name: "Register",
        data() {
            const checkName = async (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('用户名不能为空'));
                }
                if (value.length <= 2) {
                    callback(new Error('用户名长度必须大于2'));
                }
                await post('/user/checkName', {userName : value})
                    .then(res => {
                        if(res['data'] != null){
                            callback(new Error('用户名已存在'));
                        }
                    });
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
                    callback(new Error('请再次输入密码'));
                } else if(value !== this.ruleForm.userPwd){
                    callback(new Error('前后密码不相同'));
                }else{
                    callback();
                }
            };
            const validatePhone = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入手机号'));
                } else{
                    const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
                    console.log(reg.test(value));
                    if (reg.test(value)) {
                        callback();
                    } else {
                        return callback(new Error('请输入正确的手机号'));
                    }
                }
            };
            return {
                dialogImageUrl: '',
                dialogVisible: false,

                options: regionData,
                selectedOptions: [],
                email:'',
                ruleForm: {
                    userName: '',
                    userPwd: '',
                    userPwd2:'',
                    avatarUrl:'',
                    phone:'',
                    email:'',
                    qq:'',

                },
                rules: {
                    userName: [
                        { required: true, validator: checkName, trigger: 'blur' }
                    ],
                    userPwd: [
                        { required: true, validator: validatePass, trigger: 'blur' }
                    ],
                    userPwd2: [
                        { required: true, validator: validatePass2, trigger: 'blur' }
                    ],
                    phone: [
                        { required: true, validator: validatePhone, trigger: 'blur' }
                    ],
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        post('/user/register', this.ruleForm).then(res => {
                            if(res['status'] === 200) {
                                this.$message.success("注册成功！");
                            }
                        });
                    } else {
                        this.$message.error("注册失败!");
                        return false;
                    }
                });
            },
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            }
        }
    }
</script>


<style>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>