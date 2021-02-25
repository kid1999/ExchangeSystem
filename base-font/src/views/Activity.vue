/**
* @auther: kid1999
* @date: 2021/1/18 10:09
* @desciption:  Activity
*/
<template>
    <div>

        <el-row :gutter="20">
            <el-col :span="16"><div class="grid-content bg-purple">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <h2>{{address.address}}的活动</h2>
                    </div>
                    <el-table
                            :data="tableData"
                            style="width: 100%">
                        <el-table-column
                                prop="detailAddress"
                                label="详细地址"
                                width="180">
                        </el-table-column>
                        <el-table-column
                                prop="activityTitle"
                                label="活动名">
                        </el-table-column>
                        <el-table-column
                                prop="numberOfJoin"
                                label="参与人数">
                        </el-table-column>
                        <el-table-column
                                prop="status"
                                label="状态"
                                width="100"
                                :filters="[{ text: '筹备中', value: 0 }, { text: '进行中', value: 1 }, { text: '已结束', value: 2 }]"
                                :filter-method="filterStatus"
                                filter-placement="bottom-end">
                            <template slot-scope="scope">
                                <el-tag
                                        :type="filterTag(scope.row)"
                                        disable-transitions>{{statusData[scope.row.status]}}</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column
                                fixed="right"
                                label="操作"
                                width="100">
                            <template slot-scope="scope">
                                <el-button @click="viewClick(scope.row)" type="text" size="small">查看</el-button>
                                <el-button @click="joinClick(scope.row)" type="text" size="small">参加</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </div>
            </el-col>

            <el-col :span="8"><div class="grid-content bg-purple">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <h2>已参加的活动</h2>
                    </div>
                    <el-table
                            :data="MytableData"
                            style="width: 100%">
                        <el-table-column
                                prop="activityTitle"
                                label="活动名">
                        </el-table-column>
                        <el-table-column
                                prop="status"
                                label="状态"
                                width="100"
                                :filters="[{ text: '筹备中', value: 0 }, { text: '进行中', value: 1 }, { text: '已结束', value: 2 }]"
                                :filter-method="filterStatus"
                                filter-placement="bottom-end">
                            <template slot-scope="scope">
                                <el-tag
                                        :type="filterTag(scope.row)"
                                        disable-transitions>{{statusData[scope.row.status]}}</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column
                                fixed="right"
                                label="操作"
                                width="90">
                            <template slot-scope="scope">
                                <el-button @click="viewClick(scope.row)" type="text" size="small">查看</el-button>
                                <el-button @click="deleteClick(scope.row)" type="text" size="small">退出</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </div></el-col>
        </el-row>

        <el-dialog title="活动详情" :visible.sync="dialogTableVisible">
            <ul class="mdui-list">
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe8f6;</i>
                    <h4>活动名称</h4>
                    <div class="mdui-list-item-content">{{viewTableData.activityTitle}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe0cb;</i>
                    <h4>活动详情</h4>
                    <div class="mdui-list-item-content">{{viewTableData.activityContext}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe5ca;</i>
                    <h4>活动状态</h4>
                    <div class="mdui-list-item-content">{{statusData[viewTableData.status]}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe87c;</i>
                    <h4>活动发起人</h4>
                    <div class="mdui-list-item-content">{{viewTableData.username}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe7ee;</i>
                    <h4>所在地区</h4>
                    <div class="mdui-list-item-content">{{this.viewTableData['address']}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe7ee;</i>
                    <h4>详细地址</h4>
                    <div class="mdui-list-item-content">{{viewTableData.detailAddress}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe192;</i>
                    <h4>开始时间</h4>
                    <div class="mdui-list-item-content">{{viewTableData.startTime | timeFormat}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe192;</i>
                    <h4>结束时间</h4>
                    <div class="mdui-list-item-content">{{viewTableData.endTime | timeFormat}}</div>
                </li>

            </ul>
        </el-dialog>



    </div>
</template>

<script>
    import {get, put,deleted,post} from "../utils/request";
    import moment from 'moment'
    export default {
        name: "Activity",
        data(){
            return{
                dialogTableVisible:false,
                userId:'',
                addressId:'',
                tableData:[],
                MytableData:[],
                statusData:['筹备中','进行中','已结束'],
                viewTableData:{},
                address:{},

            }
        },
        filters:{
            timeFormat(timestr){
                return moment(timestr).format("YYYY-MM-DD HH:mm")
            }
        },
        created() {
            this.userId = this.$store.getters.getUser['user']['id'];
            this.addressId = this.$store.getters.getUser['user']['addressId'];
            this.address = JSON.parse(localStorage.getItem("address"));
            get('/activity/addr/' + this.addressId, {})
                .then(res => {
                    this.tableData = res['data'];
                    console.info(res)
                });
            get('/activity/joinUser/' + this.userId, {})
                .then(res => {
                    this.MytableData = res['data'];
                    console.info(res)
                });

        },
        methods:{
            filterStatus(value, row) {
                return row.status === value;
            },
            filterTag(row) {
                let now = new Date().getTime();
                let s = new Date(row.startTime).getTime();
                let e = new Date(row.endTime).getTime();
                if(s > now){
                    row.status = 0;
                }else if(s <= now && e>= now){
                    row.status = 1;
                }else {
                    row.status = 2;
                }
                switch (row.status) {
                    case 0: return 'primary';
                    case 1: return 'success';
                    case 2: return 'info';
                    case 3: return 'danger';
                    default: return 'danger';
                }
            },
            viewClick(data){
                this.viewTableData = data;
                get('/user/' + data.createUserId, {})
                    .then( res => {
                        this.viewTableData.username = res['data']['username'];
                        get('/address/' + data.addressId, {})
                            .then( res => {
                                this.viewTableData.address  = res['data']['address'];
                                this.dialogTableVisible = true;
                            });
                    });
            },
            deleteClick(data){
                deleted('/joinActivity/' + data.jid, {})
                    .then(res => {
                        this.MytableData = this.MytableData.filter(({id}) => id !== data.id);
                        this.$notify.success({
                            title: '成功',
                            message: '取消报名成功！'
                        });
                    });
            },
            joinClick(data){
                if(data['status'] >= 2){
                    this.$notify.error({
                        title: '错误',
                        message: '此活动已结束，无法加入！'
                    });
                    return;
                }
                let f = false;
                for(var obj of this.MytableData) {
                    if(obj.id === data.id) {
                        f = true;
                    }
                }
                if(!f){
                    post('/joinActivity', {userId:this.userId,activityId:data.id})
                        .then(res => {
                            this.MytableData.push(data);
                            this.$notify.success({
                                title: '成功',
                                message: '报名成功！'
                            });
                        });
                }else{
                    this.$notify.error({
                        title: '错误',
                        message: '已加入此活动，无需再次加入！'
                    });
                }
            }
        }

    }
</script>

<style scoped>

</style>