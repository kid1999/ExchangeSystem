/**
* @auther: kid1999
* @date: 2020/10/7 23:48
* @desciption:  Header
*/
<template>
    <div>
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect" router>
            <el-menu-item index="/">Esystem</el-menu-item>
            <el-menu-item index="/message"><i class="el-icon-document"></i>交易审批</el-menu-item>
            <el-menu-item index="/comments">
                <i class="el-icon-chat-line-square"></i> 留言评论<el-badge :value="commentNum" :max="99" class="item" :hidden="commentNum === 0"></el-badge>
            </el-menu-item>
            <el-menu-item index="/activity"><i class="el-icon-office-building"></i>同城活动</el-menu-item>
            <el-menu-item index="/FindYourLove"><i class="el-icon-milk-tea"></i>猜你喜欢</el-menu-item>
            <el-menu-item index="/NewGoods"><i class="el-icon-sell"></i>上架商品</el-menu-item>
            <el-menu-item index="/test"><i class="el-icon-loading"></i>测试ing</el-menu-item>
            <el-submenu index="#" style="float: right; padding: 3px 0" v-if="isLogin">
                <template slot="title">
                    <el-avatar :src="userInfo.avatarUrl"></el-avatar>
                </template>
                <el-menu-item>用户名： <strong>{{userInfo.username}}</strong></el-menu-item>
                <el-menu-item :index="'/userInfo/' + this.userInfo['id']"><i class="el-icon-user"></i>用户信息</el-menu-item>
                <el-menu-item index="/NewActivity"><i class="el-icon-sunny"></i>发起活动</el-menu-item>
                <el-menu-item index="/MyActivity"><i class="el-icon-wind-power"></i>我的活动</el-menu-item>
                <el-menu-item index="/MyGoods"><i class="el-icon-goods"></i>我的商品</el-menu-item>
                <el-menu-item index="/transRecord"><i class="el-icon-loading"></i>我的交易</el-menu-item>
                <el-menu-item index="/collection"><i class="el-icon-shopping-cart-full"></i>我的收藏</el-menu-item>
                <el-menu-item  @click="logout"><i class="el-icon-s-promotion"></i>退出登录</el-menu-item>
            </el-submenu>

            <el-menu-item index="/register" style="float: right; padding: 3px 0"  v-if="!isLogin">
                <i class="el-icon-user-solid"></i>注册</el-menu-item>
            <el-menu-item index="/login" style="float: right; padding: 3px 0" v-if="!isLogin">
                <i class="el-icon-user"></i>登录</el-menu-item>

        </el-menu>
    </div>
</template>

<script>
    import axios from 'axios';
    import { get, post,put,deleted } from '../utils/request'
    import { globalBus } from '@/utils/globalBus';
    export default {
        name: "Header",
        data(){
            return{
                userInfo:{},
                isLogin:false,
                activeIndex:'/',
                search_context:'',
                current:['main'],
                isLogin:false,//登录状态
                btnState:'',//判断点击登录还是注册
                notice:0,//通知数
                commentNum:0,
            }
        },
        created() {
            this.userInfo = this.$store.getters.getUser['user'];
            if(Object.keys(this.userInfo).length === 0) this.isLogin = false;
            else this.isLogin = true;
        },
        methods:{
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            },
            logout(){
                this.$store.commit('$_removeUser', {});
                localStorage.removeItem("access_token");
                localStorage.removeItem("refresh_token");
                localStorage.removeItem('address')
                localStorage.removeItem('contactWay')
                localStorage.removeItem('user')
                this.$router.push({name: 'Login'});
                this.isLogin = false;
            },
            // del
            RefreshUser(){
                this.userInfo = this.$store.getters.getUser['user'];
                if(Object.keys(this.userInfo).length === 0) this.isLogin = false;
                else this.isLogin = true;
                this.$forceUpdate();
            },
            getCommentNum(){
                let token = localStorage.getItem("access_token");
                axios.get('/comment/num/' + this.userInfo.id,{
                    headers: {'Authorization': 'bearer ' + token}})
                    .then(res =>{
                        console.info('获得未读消息数量为： ' + res['data']['data']);
                        this.commentNum = res['data']['data']
                    })
            }
        },
        mounted() {
            // login 刷新状态
            globalBus.$on("RefreshUser", (msg) => {
                // A发送来的消息
                console.info(msg);
                this.userInfo = this.$store.getters.getUser['user'];
                if(Object.keys(this.userInfo).length === 0) this.isLogin = false;
                else this.isLogin = true;
            });

            // 评论消息已读
            this.getCommentNum();
            globalBus.$on("delCommentMsg", (num) => {
                // A发送来的消息
                console.info(num);
                this.commentNum -= num;
            });

            // 轮询评论消息数量 60s查询一下
            setInterval(this.getCommentNum, 60000);

        }
    }
</script>

<style>


    #components-form-demo-normal-login .login-form {
        max-width: 300px;
    }
    #components-form-demo-normal-login .login-form-forgot {
        float: right;
    }
    #components-form-demo-normal-login .login-form-button {
        width: 100%;
    }
</style>
