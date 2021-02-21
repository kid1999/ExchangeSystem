/**
* @auther: kid1999
* @date: 2021/1/15 10:59
* @desciption:  UserInfo
*/
<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <h2>用户信息</h2>
            </div>
            <el-form :model="user" status-icon ref="goods" label-width="100px" class="demo-ruleForm">
                <el-form-item label="用户头像" prop="avatarUrl" v-if="canChangeable">
                    <div class="block" >
                        <el-image
                                style="width: 100px; height: 100px"
                                :src="user.avatarUrl"
                                fit="fit"></el-image>
                    </div>
                </el-form-item>
                <el-form-item label="用户头像" prop="avatarUrl" v-else="canChangeable" :rules="[{ required: true, message: '头像不能为空'}]">
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

                <el-form-item label="用户名" prop="username" :rules="[{ required: true, message: '用户名不能为空'}]">
                    <el-input type="text" v-model="user.username" autocomplete="off" :disabled="canChangeable"></el-input>
                </el-form-item>
                <el-form-item label="签名" prop="signature">
                    <el-input type="text" v-model="user.signature" autocomplete="off" :disabled="canChangeable"></el-input>
                </el-form-item>
                <el-form-item label="登录次数" prop="loginTimes">
                    <el-input type="text" v-model="user.loginTimes" autocomplete="off" disabled></el-input>
                </el-form-item>
                <el-form-item label="上次登录时间" prop="lastLoginTime">
                    <el-input type="text" v-model="lastLoginTime" autocomplete="off" :disabled="canChangeable"></el-input>
                </el-form-item>
                <el-form-item label="创建时间" prop="createTime">
                    <el-input type="text" v-model="createTime" autocomplete="off" :disabled="canChangeable"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button  @click="canChangeable = false" v-if="id === user.id">修改资料</el-button>
                    <el-button type="primary" @click="updateUser" v-if="id === user.id">保存修改</el-button>
                </el-form-item>
            </el-form>
        </el-card>

        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <h2>留言评论</h2>
            </div>
            <el-collapse accordion>
                <div>
                    <el-input
                            type="textarea"
                            placeholder="请输入内容"
                            v-model="newComment"
                            maxlength="50"
                            show-word-limit
                    >
                    </el-input>
                    <el-button type="primary" size="small" @click="pushComment">发布留言</el-button>
                </div>
                <el-collapse-item v-if="leavingComment.length !== 0" v-for="comment in leavingComment">
                    <template slot="title"  >
                        <el-link :underline="false" :href="'/user/' + comment.user_id"><h3>{{comment.username}}</h3></el-link> - {{comment.date | timeFormat}}<i class="header-icon el-icon-info"></i>
                    </template>
                    <div>{{comment.context}}</div>
                </el-collapse-item>

                <el-collapse-item v-else="goodsComment.length !== 0">
                    <template slot="title"  >
                        <h3>暂无留言</h3><i class="header-icon el-icon-info"></i>
                    </template>
                </el-collapse-item>
            </el-collapse>
        </el-card>
    </div>
</template>

<script>
    import { get, post,put,deleted } from '../../utils/request'
    import moment from 'moment'
    export default {
        name: "UserInfo",
        data(){
            return{
                id:'',      //当前页面user
                userId:'',  //当前访问user
                canChangeable:true,
                dialogImageUrl: '',
                dialogVisible:false,
                user:{},
                leavingComment:[],
                newComment:'',
            }
        },
        created() {
            this.id = this.$route.params.id;
            this.userId = this.$store.getters.getUser['user']['id'];
            get('/user/' + this.id, {})
                .then(res => {
                    this.user = res['data'];
                });

            // 获取user的评论
            get('/leavingComment/othersToMe/' + this.id, {})
                .then(res => {
                    this.leavingComment = res['data'];
                    console.info(this.leavingComment);
                });
        },
        filters:{
            timeFormat(timestr){
                return moment(timestr).format("YYYY-MM-DD HH:mm")
            }
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
            pushComment(){
                const data = {user1Id:this.userId,user2Id:this.id,context:this.newComment}
                post('/comment', data)
                    .then(res => {
                        this.$message.success("发布留言成功");
                        res['username'] = this.goodsComment.push(data);
                    });
            },
            updateUser(){
                put('/user', this.user)
                    .then(res => {
                        this.$message.success("修改成功")
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

        }
    }
</script>

<style scoped>

</style>