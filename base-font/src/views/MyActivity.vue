/**
* @auther: kid1999
* @date: 2021/1/18 10:53
* @desciption:  MyActivity
*/
<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <h2>我的同城活动</h2>
            </div>
            <el-table
                    :data="tableData"
                    style="width: 100%">
                <el-table-column
                        label="开始时间"
                        width="160">
                    <template slot-scope="scope">
                        <i class="el-icon-time"></i>
                        <span style="margin-left: 10px">{{ scope.row.startTime | timeFormat }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="结束时间"
                        width="160">
                    <template slot-scope="scope">
                        <i class="el-icon-time"></i>
                        <span style="margin-left: 10px">{{ scope.row.endTime | timeFormat }}</span>
                    </template>
                </el-table-column>
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
                                :type="filterTag(scope.row.status)"
                                disable-transitions>{{statusData[scope.row.status]}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="150">
                    <template slot="header" slot-scope="scope">
                        <el-button size="mini" round @click="createOneActivity()">创建活动</el-button>
                    </template>
                    <template slot-scope="scope">
                        <el-button @click="viewClick(scope.row)" type="text" size="small">查看</el-button>
                        <el-button @click="changeClick(scope.row)" type="text" size="small">修改</el-button>
                        <el-button @click="sureToDelete(scope.row)" type="text" size="small">删除</el-button>
                    </template>
                </el-table-column>

            </el-table>
        </el-card>

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
                    <div class="mdui-list-item-content">{{viewTableData.userName}}</div>
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



        <!-- Form -->
        <el-dialog title="活动信息" :visible.sync="dialogFormVisible">
            <el-form :model="FormData" ref="FormData">
                <el-form-item label="活动名" prop="activityTitle" :rules="[{ required: true, message: '活动名不能为空'}]">
                    <el-input v-model="FormData.activityTitle" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="活动详情" prop="activityContext" :rules="[{ required: true, message: '活动详情不能为空'}]">
                    <el-input v-model="FormData.activityContext" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="详细地址" prop="detailAddress" :rules="[{ required: true, message: '详细地址不能为空'}]">
                    <el-input v-model="FormData.detailAddress" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="活动时间">
                    <el-col :span="8">
                        <el-date-picker  type="datetime" placeholder="选择日期" v-model="FormData.startTime" style="width: 100%;"></el-date-picker>
                    </el-col>
                    <el-col class="line" :span="1">-</el-col>
                    <el-col :span="8">
                        <el-date-picker  type="datetime" placeholder="选择时间" v-model="FormData.endTime" style="width: 100%;"></el-date-picker>
                    </el-col>
                </el-form-item>
                <el-form-item label="所在地区" prop="addressId" @change="this.FormData.addressId = 1" :rules="[{ required: true, message: '所在地区不能为空',trigger: 'blur'}]">
                    <el-col :span="10">
                        <el-cascader
                                size="large"
                                :options="options"
                                v-model="addressOptions">
                        </el-cascader>
                    </el-col>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm('FormData')">确 定</el-button>
                <el-button @click="dialogFormVisible = false">取 消</el-button>
            </div>
        </el-dialog>


    </div>
</template>

<script>
    import {get, put,post,deleted} from "../utils/request";
    import moment from 'moment'
    import { regionData,CodeToText } from 'element-china-area-data'
    export default {
        name: "MyActivity",
        data(){
            return{
                isCreateNewActivity:false,
                dialogTableVisible:false,
                dialogFormVisible:false,
                userId:'',
                addressId:'',
                addressData:{},
                tableData:[],
                addressOptions:[],
                options: regionData,
                statusData:['筹备中','进行中','已结束'],
                viewTableData:{},
                FormData:{
                    id:'',
                    activityContext:'',
                    activityTitle:'',
                    endTime:'',
                    startTime:'',
                    detailAddress:'',
                    addressId:'',
                    createUserId:'',
                },
            }
        },
        filters:{
            timeFormat(timestr){
                return moment(timestr).format("YYYY-MM-DD HH:mm")
            }
        },
        watch : {
            addressOptions:function(val) {
                this.addressData.province = CodeToText[val[0]];
                this.addressData.city = CodeToText[val[1]];
                this.addressData.area = CodeToText[val[2]];
                this.addressData.address = this.addressData.province + this.addressData.city +this.addressData.area;
                post('/address',this.addressData)
                    .then(res => {
                        this.FormData.addressId = res['data'];
                    });
            },
        },
        created() {
            this.userId = this.$store.getters.getUser['user']['id'];
            this.addressId = this.$store.getters.getUser['user']['addressId'];
            get('/activity/createUser/' + this.userId, {})
                .then(res => {
                    this.tableData = res['data'];
                    console.info(res)
                });
        },
        methods:{
            filterStatus(value, row) {
                return row.status === value;
            },
            filterTag(value) {
                switch (value) {
                    case 0: return 'primary';
                    case 1: return 'success';
                    case 2: return 'info';
                    case 3: return 'danger';
                    default: return 'danger';
                }
            },
            viewClick(data){
                this.viewTableData = data;
                let flag = 0;
                get('/user/' + data.createUserId, {})
                    .then( res => {
                        this.viewTableData.userName = res['data']['userName'];
                        get('/address/' + data.addressId, {})
                            .then( res => {
                                this.viewTableData.address  = res['data']['address'];
                                this.dialogTableVisible = true;
                            });
                    });
            },
            sureToDelete(data) {
                this.$confirm('此操作将永久删除该活动, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleted('/activity/' + data.id , {})
                        .then(res => {
                            this.$notify.success({
                                title: '成功',
                                message: '该活动已取消！'
                            });
                            this.tableData = this.tableData.filter(({id}) => id !== data.id);
                        });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            changeClick(data){
                this.dialogFormVisible = true;
                this.FormData.activityContext = data.activityContext;
                this.FormData.activityTitle = data.activityTitle;
                this.FormData.startTime = data.startTime;
                this.FormData.endTime = data.endTime;
                this.FormData.detailAddress = data.detailAddress;
                this.FormData.address = data.address;
                this.FormData.id = data.id;
            },
            createOneActivity(){
                this.dialogFormVisible = true;
                this.isCreateNewActivity = true;
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.FormData.createUserId = this.userId;
                        if(this.isCreateNewActivity){
                            post('/activity', this.FormData).then(res => {
                                this.$notify.success({
                                    title: '成功',
                                    message: '活动创建成功！'
                                });
                            });
                        }else{
                            put('/activity', this.FormData).then(res => {
                                this.$notify.success({
                                    title: '成功',
                                    message: '活动修改成功！'
                                });
                            });
                        }
                    } else {
                        this.$message.error("信息填写错误，请重试！");
                        return false;
                    }
                });
            },

        }
    }
</script>

<style scoped>

</style>