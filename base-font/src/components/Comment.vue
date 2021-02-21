/**
* @auther: kid1999
* @date: 2021/2/20 17:13
* @desciption:  Comment
*/
<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <h2>留言</h2>
            </div>
            <div>
                <el-input placeholder="发表留言" v-model="newComment" maxlength="70"
                          show-word-limit>
                    <el-button slot="append" @click="pushComment">提交</el-button>
                </el-input>
            </div>
            <div v-if="goodsComment.length === 0">
                <p>暂无留言！</p>
            </div>
            <div v-else="goodsComment.length === 0">
                <ul class="mdui-list mdui-list-dense" v-for="comment in goodsComment">
                    <li class="mdui-list-item mdui-ripple">
                        <div class="mdui-list-item-avatar">
                            <router-link :to="'/userInfo/' + comment.user1_id">
                                <el-tooltip :content="comment.username" placement="top">
                                    <img :src="comment.avatar_url"/>
                                </el-tooltip>
                            </router-link>
                        </div>
                        <div class="mdui-list-item-content">
                            <div class="mdui-list-item-title"><i class="mdui-icon-left material-icons">&#xe01b;</i>{{comment.date | timeFormat}}</div>
                            <div class="mdui-list-item-text mdui-list-item-two-line">{{comment.context}}</div>
                        </div>
                    </li>
                    <li class="mdui-divider-inset mdui-m-y-0"></li>
                </ul>
            </div>

        </el-card>
    </div>
</template>

<script>
    import { globalBus } from '@/utils/globalBus';
    import moment from 'moment'
    import { get, post,put,deleted } from '../utils/request'
    export default {
        name: "Comment",
        data() {
            return {
                newComment:'',
                goodsComment:[],
                user2Id: '',
            }
        },
        props:['id','user'],
        filters:{
            timeFormat(timestr){
                return moment(timestr).format("YYYY-MM-DD HH:mm")
            }
        },
        methods:{
            pushComment(){
                const data = {user1Id:this.user.id,user2Id:this.user2Id,goodsId:this.id,context:this.newComment};
                post('/comment', data)
                    .then(res => {
                        this.$message.success("留言成功");
                        data['username'] = this.user.username;
                        data['date'] = new Date();
                        data['user1_id'] = this.user.id;
                        data['avatar_url'] = this.user.avatarUrl;
                        this.goodsComment.push(data);
                    });
            }
        },
        mounted() {
            // 获取goods的评论
            get('/comment/goods/' + this.id, {})
                .then(res => {
                    this.goodsComment = res['data'];
                    console.info(this.goodsComment);
                });
            globalBus.$on("PushGoodsInfo", (goods) => {
                this.user2Id = goods.userId;
            });
        }

    }
</script>

<style scoped>

</style>