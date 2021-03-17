/**
* @auther: kid1999
* @date: 2021/1/4 19:50
* @desciption:  Register
*/
<template>
    <el-row :gutter="20">
        <el-col :span="14" :offset="3">
            <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <h2>注 册</h2>
                </div>
    <div>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm">
            <el-form-item label="用户名" prop="username">
                <el-input type="text" v-model="ruleForm.username" autocomplete="off" maxlength="15"
                          show-word-limit></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="userPwd">
                <el-input type="password" v-model="ruleForm.userPwd" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="userPwd2">
                <el-input type="password" v-model="ruleForm.userPwd2" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="签名" prop="signature">
                <el-input type="text" v-model="ruleForm.signature" autocomplete="off" maxlength="70"
                          show-word-limit></el-input>
            </el-form-item>

            <el-form-item label="所在地区" prop="address">
                <el-cascader
                        size="large"
                        :options="options"
                        v-model="addressOptions">
                </el-cascader>
            </el-form-item>


            <el-form-item label="手机号" prop="phone">
                <el-input type="text" v-model="ruleForm.phone" autocomplete="off"></el-input>
            </el-form-item>


            <el-form-item label="邮箱" prop="email">
                <el-input type="text" v-model="ruleForm.email" autocomplete="off" @blur="checkEmail"></el-input>
            </el-form-item>

            <el-form-item label="QQ" prop="qq">
                <el-input type="text" v-model="ruleForm.qq" autocomplete="off"></el-input>
            </el-form-item>


            <el-form-item label="用户头像" prop="avatarUrl">
                <el-upload
                        action="/api/file"
                        list-type="picture-card"
                        :limit="1"
                        :auto-upload="true"
                        :on-success="uploadImgSuccess"
                        :before-upload="beforeAvatarUpload"
                        :on-preview="handlePictureCardPreview"
                        :on-remove="handleRemove">
                    <i class="el-icon-plus"></i>
                </el-upload>
                <el-dialog :visible.sync="dialogVisible">
                    <img width="100%" :src="dialogImageUrl" alt="">
                </el-dialog>

            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">注 册</el-button>
            </el-form-item>
        </el-form>
    </div>
            </el-card>
        </el-col>
    </el-row>
</template>
<script>
    import { get, post,put,deleted } from '../utils/request'
    import { regionData,CodeToText } from 'element-china-area-data'
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
                await post('/user/checkName', {username : value})
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
            const validateAddr = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入所在区域'));
                } callback();
            };
            const validateEmail = (rule, value, callback) => {
                const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
                if (value === '') {
                    callback(new Error('请输入邮箱'));
                }
                if (mailReg.test(value)) {
                    callback()
                } else {
                    callback(new Error('请输入正确的邮箱格式'))
                }
                callback();
            };
            return {
                dialogImageUrl: '',
                dialogVisible: false,
                checkemail:false,
                checkCode:'asd3wz',
                lastTime: new Date(1111),
                ourCode:'',
                options: regionData,
                addressOptions: [],
                email:'',
                ruleForm: {
                    username: '',
                    userPwd: '',
                    userPwd2:'',
                    signature:'',
                    avatarUrl:'',
                    phone:'',
                    email:'',
                    qq:'',
                    address:'',
                    province:'',
                    city:'',
                    area:'',
                },
                rules: {
                    username: [
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
                    address: [
                        { required: true, validator: validateAddr, trigger: 'blur' }
                    ],
                    email: [
                        { required: true, validator: validateEmail,trigger: ['blur', 'change']}
                    ],
                }
            };
        },
        watch : {
            addressOptions:function(val) {
                this.ruleForm.province = CodeToText[val[0]];
                this.ruleForm.city = CodeToText[val[1]];
                this.ruleForm.area = CodeToText[val[2]];
                this.ruleForm.address = this.ruleForm.province + this.ruleForm.city +this.ruleForm.area;
                console.info(this.ruleForm);
            },
        },
        methods: {
            submitForm(formName) {
                let data = this.ruleForm;
                console.info(data);
                if(this.ourCode !== this.checkCode) {
                    this.$notify.error({
                        title: '错误',
                        message: '验证码错误！'
                    });
                    return;
                }
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        post('/user/register', this.ruleForm).then(res => {
                            if(res['status'] === 200) {
                                this.$message.success("注册成功！");
                            }
                            this.$router.push({name: 'Login'});
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
            },
            beforeAvatarUpload(file) {
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isLt2M;
            },
            uploadImgSuccess(response, file, fileList){
                this.$message.success("图片上传成功！");
                this.ruleForm.avatarUrl = response['data']['fileName'];
                console.info(this.ruleForm);
            },
            checkEmail(){
                const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
                if (this.ruleForm.email === '') {
                    this.$message.error('请输入邮箱！');
                    return;
                }
                if (!mailReg.test(this.ruleForm.email)) {
                    this.$message.error('邮箱格式不对！');
                    return;
                }
                if(new Date().valueOf() - this.lastTime.valueOf() > 100000){
                    this.lastTime = new Date();
                    post('/user/sendCode', this.ruleForm).then(res => {
                        this.checkCode = res.data;
                        this.$notify.success({
                            title: '成功',
                            message: '验证码已发送本账号绑定的邮箱中，请注意查收！'
                        });
                    }).catch(e =>{
                        this.$notify.error({
                            title: '错误',
                            message: '邮件发送失败！'
                        });
                    })
                }
                this.$prompt('请输入邮箱验证码', '提示', {
                    confirmButtonText: '确定',
                    showCancelButton: true,
                    distinguishCancelAndClose: false,
                    showClose: false,
                    closeOnClickModal:false,
                    closeOnPressEscape:false,
                    cancelButtonText: '取消',
                    inputErrorMessage: '邮箱格式不正确'
                }).then(({ value }) => {
                    this.ourCode = value;
                    if(value !== this.checkCode) {
                        this.$notify.error({
                            title: '错误',
                            message: '验证码错误！'
                        });
                    }else{
                        this.$notify.success({
                            title: '恭喜',
                            message: '验证成功！'
                        });
                    }
                })
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