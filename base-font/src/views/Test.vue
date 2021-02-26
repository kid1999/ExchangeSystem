/**
* @auther: kid1999
* @date: 2021/1/21 22:52
* @desciption:  Test
*/
<template>
    <div>
        <el-input placeholder="请留下你的宝贵意见" v-model="newComment" class="input-with-select" show-word-limit maxlength="70">
            <el-button slot="append" @click="addComment()">提交</el-button>
        </el-input>
        <div class="mdui-list">
            <a href="#" class="mdui-list-item mdui-ripple"><s>同城活动人数统计</s></a>
            <a href="#" class="mdui-list-item mdui-ripple"><s>交易逻辑</s></a>
            <a href="#" class="mdui-list-item mdui-ripple">整合安全验证，处理header逻辑</a>
            <a href="#" class="mdui-list-item mdui-ripple">消息中心，消息推送</a>

            <a href="#" class="mdui-list-item mdui-ripple" v-for="comment in comments">{{comment.context}}</a>
        </div>
    </div>
</template>

<script>
    import {get, put,deleted,post} from "../utils/request";
    import moment from 'moment'
    export default {
        name: "Test",
        data(){
            return{
                strs:['a','ds','csd'],
                comments:[],
                newComment:'',
                lastTime: new Date(1111)
            }
        },
        filters:{
            timeFormat(timestr){
                return moment(timestr).format("YYYY-MM-DD HH:mm")
            }
        },
        created() {
            get('/leavingComment',{}).then(res =>{
                console.info(res);
                this.comments = res['data']
            })
        },
        methods:{
            func(data){
                let res = '';
                for (s of data){
                    res += s + ',';
                }
                return res;
            },
            addComment(){
                if(this.newComment === ''){
                    this.$message.error("你的宝贵意见不应该是nothing!")
                    return;
                }
                if(new Date().getSeconds() - this.lastTime.getSeconds() < 30){
                    this.$message.error("请你休息一会儿再发言： " + (30 - (new Date().getSeconds() - this.lastTime.getSeconds())) + 's')
                    return;
                }
                this.comments.push({'context':this.newComment});
                this.lastTime = new Date();
                // post('/leavingComment',{'context':this.newComment}).then(res =>{
                //     this.$notify.success("提交成功！")
                //
                // })
            }
        }
    }
</script>

<style scoped>

</style>