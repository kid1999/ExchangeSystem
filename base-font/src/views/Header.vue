/**
* @auther: kid1999
* @date: 2020/10/7 23:48
* @desciption:  Header
*/
<template>
    <div>
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect" router>
            <el-menu-item index="/">Esystem</el-menu-item>
            <el-menu-item index="/message">消息中心</el-menu-item>
            <el-menu-item index="/trans_record">交易记录</el-menu-item>
            <el-menu-item index="/demand">收藏夹</el-menu-item>
            <el-menu-item index="/leaving_comment">留言板块</el-menu-item>
            <el-menu-item index="/comment">评论板块</el-menu-item>
            <el-menu-item index="#">
                <el-input v-model="search_context" placeholder="请输入内容"></el-input>
            </el-menu-item>
            <el-submenu index="#">
                <template slot="title">
                    <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
                </template>
                <el-menu-item index="user">用户信息</el-menu-item>
                <el-menu-item index="login">登录</el-menu-item>
                <el-menu-item index="register">注册</el-menu-item>
                <el-menu-item index="userInfo">信息中心</el-menu-item>
                <el-menu-item index="logout">退出</el-menu-item>
            </el-submenu>
        </el-menu>
    </div>
</template>

<script>
    export default {
        name: "Header",
        data(){
            return{
                activeIndex:'/',
                search_context:'',
                current:['main'],
                isLogin:false,//登录状态
                btnState:'',//判断点击登录还是注册
                notice:0,//通知数
                url:'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'
            }
        },
        methods:{
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            }
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
