/**
* @auther: kid1999
* @date: 2021/2/21 9:38
* @desciption:  UserDetail
*/
<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="16">
                <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <h2>用户信息</h2>
                </div>
                <el-form :model="user" status-icon ref="goods" label-width="100px" class="demo-ruleForm">
                    <el-form-item label="用户头像" prop="avatarUrl">
                        <div class="block" >
                            <el-image
                                    style="width: 100px; height: 100px"
                                    :src="user.avatarUrl"
                                    fit="fit"></el-image>
                        </div>
                    </el-form-item>
                    <el-form-item label="用户名" prop="username" :rules="[{ required: true, message: '用户名不能为空'}]">
                        <el-input type="text" v-model="user.username" autocomplete="off" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="签名" prop="signature">
                        <el-input type="text" v-model="user.signature" autocomplete="off" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="登录次数" prop="loginTimes">
                        <el-input type="text" v-model="user.loginTimes" autocomplete="off" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="上次登录时间" prop="lastLoginTime">
                        <el-input type="text" v-model="lastLoginTime" autocomplete="off" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="创建时间" prop="createTime">
                        <el-input type="text" v-model="createTime" autocomplete="off" disabled></el-input>
                    </el-form-item>
                    <el-button @click="changeUserInfo" v-if="canChangeable">修改用户信息</el-button>
                </el-form>
            </el-card>
            </el-col>
            <el-col :span="8">
                <Comment :id="this.id" :user="this.user"></Comment>
            </el-col>
        </el-row>

        <el-dialog title="用户信息" :visible.sync="dialogFormVisible">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
                <el-form-item label="用户名">
                    <el-input v-model="ruleForm.username" autocomplete="off" disabled></el-input>
                </el-form-item>
                <el-form-item label="签名">
                    <el-input v-model="ruleForm.signature" autocomplete="off" maxlength="70"
                              show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="所在地区" prop="address">
                    <el-cascader
                            size="large"
                            :placeholder="address.address"
                            :options="options"
                            v-model="addressOptions">
                    </el-cascader>
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


            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
            </div>
        </el-dialog>




    </div>
</template>

<script>
    import { regionData,CodeToText } from 'element-china-area-data'
    import { get, post,put,deleted } from '../utils/request'
    import moment from 'moment'
    import Comment from "../components/Comment";
    export default {
        name: "UserInfo",
        components: {Comment},
        data(){
            const validatePhone = (rule, value, callback) => {
                const reg = /^1[3|4|5|7|8][0-9]\d{8}$/;
                console.log(reg.test(value));
                if (value === '' || reg.test(value)) {
                    callback();
                } else {
                    return callback(new Error('请输入正确的手机号'));
                }
            };
            const validateQQ = (rule, value, callback) => {
                const reg = /^[1-9][0-9]{4,9}$/;
                console.log(reg.test(value));
                if (value === '' || reg.test(value)) {
                    callback();
                } else {
                    return callback(new Error('请输入正确的QQ号'));
                }
            };
            return{
                id:'',      //当前页面user
                user:{},  //当前访问user
                dialogImageUrl: '',
                dialogVisible:false,
                dialogFormVisible:false,
                options: regionData,
                canChangeable:false,
                addressOptions: [],
                ruleForm:{
                    id:'',
                    username:'',
                    signature:'',
                    email:'',
                    phone:'',
                    qq:'',
                },
                address:{},
                contactWay:{},
                rules: {
                    email: [{
                        required: true,//是否必填
                        message: '请输入邮箱地址',//错误提示信息
                        trigger: 'blur'//检验方式（blur为鼠标点击其他地方，）
                    },
                        {
                            type: 'email',//要检验的类型（number，email，date等）
                            message: '请输入正确的邮箱地址',
                            trigger: ['blur', 'change']
                        }
                    ],
                    phone: [
                        { required: false, validator: validatePhone, trigger: 'blur' }
                    ],
                    qq: [
                        { required: false, validator: validateQQ, trigger: 'blur' }
                    ],
                }
            }
        },
        created() {
            this.id = this.$route.params.id;
            this.user = this.$store.getters.getUser['user'];
            get('/user/' + this.id, {})
                .then(res => {
                    console.info(res)
                    this.user = res['data'];
                    this.ruleForm.username = this.user.username;
                    this.ruleForm.signature = this.user.signature;
                    this.ruleForm.id = this.user.id;
                });
            if(this.id == this.user.id) this.canChangeable = true;
        },
        filters:{
            timeFormat(timestr){
                return moment(timestr).format("YYYY-MM-DD HH:mm")
            }
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
        computed:{
            lastLoginTime(){
                return moment(this.user.lastLoginTime).format("YYYY-MM-DD HH:mm")
            },
            createTime(){
                return moment(this.user.createTime).format("YYYY-MM-DD HH:mm")
            }
        },
        methods:{
            changeUserInfo(){
                this.dialogFormVisible = true;
                this.address = JSON.parse(localStorage.getItem("address"));
                this.contactWay = JSON.parse(localStorage.getItem("contactWay"));
                this.ruleForm['contactWayId'] = this.contactWay['id'];
                this.ruleForm['phone'] = this.contactWay['phone'];
                this.ruleForm['email'] = this.contactWay['email'];
                this.ruleForm['qq'] = this.contactWay['qq'];
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
            submitForm(formName) {
                let data = this.ruleForm;
                if(typeof (data['address']) === 'undefined'){
                    data['addressId'] = this.user.addressId;
                }
                console.info(data);
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        put('/user', data).then(res => {
                            if(res['status'] === 200) {
                                this.$message.success("修改成功！");
                                // 再刷新数据
                                get('/user', {}).then(res => {
                                    if(res['status'] === 200) {
                                        console.info(res);
                                        this.$store.commit('$_setUser', {user: res['data'][0]});
                                        localStorage.setItem("address",JSON.stringify(res['data'][1]));
                                        localStorage.setItem("contactWay",JSON.stringify(res['data'][2]));
                                    }
                                });
                            }
                        });
                    } else {
                        this.$message.error("修改失败!");
                        return false;
                    }
                });
            },

        }
    }
</script>

<style scoped>

</style>