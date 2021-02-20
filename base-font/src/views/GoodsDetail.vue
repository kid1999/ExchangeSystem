/**
* @auther: kid1999
* @date: 2021/2/20 16:29
* @desciption:  GoodsInfo
*/
<template>
    <div>
        <div>
            <el-row :gutter="20">
                <el-col :span="16">
                    <el-card class="box-card">
                        <div slot="header" class="clearfix">
                            <h2>{{goods.goodsName}}</h2>
                        </div>
                        <img class="mdui-img-fluid" :src="goods.imgUrl"/>
                        <div class="mdui-table-fluid">
                            <table class="mdui-table mdui-table-hoverable">
                                <tbody>
                                <tr>
                                    <td>卖家</td>
                                    <td>
                                        <router-link :to="'/userInfo/' + goods.userId">
                                            {{goods.username}}
                                        </router-link>
                                    </td>
                                </tr>
                                <tr>
                                    <td>所在地区</td>
                                    <td>{{goods.address}}</td>
                                </tr>
                                <tr>
                                    <td>商品成色</td>
                                    <td>{{goods.goodsCondition}}</td>
                                </tr>
                                <tr>
                                    <td>想要交换的商品</td>
                                    <td>{{goods.wantGoodsName}}</td>
                                </tr>
                                <tr>
                                    <td>商品描述</td>
                                    <td>{{goods.description}}</td>
                                </tr>
                                <tr>
                                    <td>浏览人数</td>
                                    <td>{{goods.numberOfClicked}}</td>
                                </tr>
                                <tr>
                                    <td>上架时间</td>
                                    <td>{{goods.createDate | timeFormat}}</td>
                                </tr>
                                <tr>
                                    <td>备注</td>
                                    <td>{{goods.remarks}}</td>
                                </tr>
                                <tr>
                                    <td>估价</td>
                                    <td>{{goods.price}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                    </el-card>
                </el-col>
                <el-col :span="8">
                    <Comment :id="this.id" :user="this.user"></Comment>
                </el-col>
            </el-row>

        </div>
    </div>
</template>

<script>
    import { globalBus } from '@/utils/globalBus';
    import { get, post,put,deleted } from '../utils/request'
    import moment from 'moment'
    import Comment from "../components/Comment";
    export default {
        name: "GoodsDetail",
        components: {Comment},
        comments:{'Comment':Comment},
        data() {
            return {
                newComment:'',
                userId:'',
                user:{},
                id:'',
                goods:{},
                goodsDetail:[],
                goodsComment:[],
            }
        },
        filters:{
            timeFormat(timestr){
                return moment(timestr).format("YYYY-MM-DD HH:mm")
            }
        },
        created() {
            this.id = this.$route.params.id;
            this.user = this.$store.getters.getUser['user'];
            this.userId = this.$store.getters.getUser['user']['id'];
            get('/goods/' + this.id, {}).then(res => {
                if(res['status'] === 200) {
                    console.info(res);
                    this.goods = res['data'];

                    globalBus.$emit("PushGoodsInfo", this.goods);
                }
            });
            console.info(this.goods)
        }
    }
</script>

<style scoped>
    img {
        display:block;
        height:150px;
        margin:0 auto;/*图片居中*/
        margin-bottom: 10px;
    }
</style>