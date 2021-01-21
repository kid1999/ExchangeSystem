/**
* @auther: kid1999
* @date: 2021/1/16 16:10
* @desciption:  Comments
*/
<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <h2>留言及回复</h2>
            </div>
            <el-table
                    :data="tableData"
                    style="width: 100%">
                <el-table-column
                        label="评论日期"
                        width="180">
                    <template slot-scope="scope">
                        <i class="el-icon-time"></i>
                        <span style="margin-left: 10px">{{ scope.row.date | timeFormat }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="address"
                        label="地址"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="goods_name"
                        label="商品">
                    <template slot-scope="scope">
                        <el-link :href="'/goods/detail/' + scope.row.id" :underline="false">
                            <span>{{ scope.row.goods_name }}</span>
                        </el-link>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="username"
                        label="用户">
                    <template slot-scope="scope">
                        <el-link :href="'/userInfo/' + scope.row.user1_id" :underline="false">
                            <span>{{ scope.row.username }}</span>
                        </el-link>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="context"
                        label="评论">
                </el-table-column>
                <el-table-column
                        prop="status"
                        label="状态"
                        width="100"
                        :filters="[{ text: statusData[0], value: 0 }, { text: statusData[1], value: 1 },{ text: statusData[2], value: 2 }]"
                        :filter-method="filterStatus"
                        filter-placement="bottom-end">
                    <template slot-scope="scope">
                        <el-tag
                                :type="filterTag(scope.row.status)"
                                disable-transitions>{{statusData[scope.row.status]}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="100">
                    <template slot="header" slot-scope="scope">
                        <el-button size="mini" round @click="readAll()">一键全阅</el-button>
                    </template>
                    <template slot-scope="scope">
                        <el-button @click="viewClick(scope.row)" type="text" size="small">查看</el-button>
                        <el-button @click="deleteClick(scope.row)" type="text" size="small">删除</el-button>
                    </template>
                </el-table-column>


            </el-table>
        </el-card>

        <el-dialog title="评论详情" :visible.sync="dialogTableVisible">
            <ul class="mdui-list">
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe8f6;</i>
                    <h4>交易商品</h4>
                    <div class="mdui-list-item-content">{{viewTableData.goods_name}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe87c;</i>
                    <h4>用 户</h4>
                    <div class="mdui-list-item-content">{{viewTableData.username}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe192;</i>
                    <h4>评论时间</h4>
                    <div class="mdui-list-item-content">{{viewTableData.date | timeFormat}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe7ee;</i>
                    <h4>用户地址</h4>
                    <div class="mdui-list-item-content">{{viewTableData.address}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe0cb;</i>
                    <h4>评 论</h4>
                    <div class="mdui-list-item-content">{{viewTableData.context}}</div>
                </li>
            </ul>
            <div style="margin-top: 15px;">
                <el-input placeholder="请输入内容" v-model="replyComment"  maxlength="50"
                          show-word-limit>
                    <el-button slot="append" @click="reply">回复</el-button>
                </el-input>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    import {get,post, put,deleted} from "../utils/request";
    import moment from 'moment'
    export default {
        name: "Comments",
        data(){
            return{
                dialogTableVisible:false,
                userId:'',
                tableData:[],
                statusData:['未读','已阅','已删'],
                viewTableData:{},
                replyComment:'',
            }
        },
        filters:{
            timeFormat(timestr){
                return moment(timestr).format("YYYY-MM-DD HH:mm")
            }
        },
        created() {
            this.userId = this.$store.getters.getUser['user']['id'];
            get('/comment/user/' + this.userId, {})
                .then(res => {
                    this.tableData = res['data'];
                    console.info(res)
                });

        },
        methods:{
            reply(){
                let d = {user1Id:this.userId,user2Id:this.viewTableData['user1_id'],goodsId:this.viewTableData['goods_id'],context:this.replyComment};
                post('/comment', d)
                    .then(res => {
                        this.$notify.success({
                            title: '成功',
                            message: '消息发送成功！'
                        });
                    });
            },
            readAll(){
                let res = [];
                for(var obj of this.tableData){
                    if(obj['status'] === 0){
                        res.push(obj['id']);
                        obj['status'] = 1;
                    }
                }
                console.info(res);
                if(res.length != 0){
                    put('/comment/read', {ids:res})
                        .then(res => {
                            this.$notify.success({
                                title: '成功',
                                message: '消息已全阅！'
                            });
                        });
                }else{
                    this.$notify.error({
                        title: '失败',
                        message: '暂无未阅消息!'
                    });
                }
            },
            filterStatus(value, row) {
                return row.status === value;
            },
            filterTag(value) {
                switch (value) {
                    case 0: return 'info';
                    case 1: return 'success';
                    case 2: return 'danger';
                    default: return 'danger';
                }
            },
            viewClick(data){
                this.dialogTableVisible = true;
                this.viewTableData = data;
                if(data['status'] === 0){
                    data['status'] = 1;
                    put('/comment/read/' + data['id'], {})
                        .then(res => {
                            this.$notify.success({
                                title: '成功',
                                message: '此评论已阅！'
                            });
                        });
                }
            },
            deleteClick(data){
                if(data['status'] === 2 ){
                    this.$notify.error({
                        title: '错误',
                        message: '此评论已删除，请勿重复操作！'
                    });
                    return;
                }
                data['status'] = 2;
                deleted('/comment/' + data.id, data)
                    .then(res => {
                        this.$notify.success({
                            title: '成功',
                            message: '此评论已删除！'
                        });
                    });
            }
        }
    }
</script>

<style scoped>

</style>