/**
* @auther: kid1999
* @date: 2021/1/15 16:47
* @desciption:  TransRecords
*/
<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <h2>交易请求记录</h2>
            </div>
        <el-table
                :data="tableData"
                style="width: 100%">
            <el-table-column
                    label="交易日期"
                    width="180">
                <template slot-scope="scope">
                    <i class="el-icon-time"></i>
                    <span style="margin-left: 10px">{{ scope.row.detailed_datetime | timeFormat }}</span>
                </template>
            </el-table-column>
            <el-table-column
                    prop="detailed_address"
                    label="交易详细地址"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="goods_name"
                    label="商品">
            </el-table-column>
            <el-table-column
                    prop="user_name"
                    label="卖家">
            </el-table-column>
            <el-table-column
                    prop="remark"
                    label="备注">
            </el-table-column>
            <el-table-column
                    prop="status"
                    label="状态"
                    width="100"
                    :filters="[{ text: '申请中', value: 0 }, { text: '交易中', value: 1 }, { text: '交易完成', value: 2 }, { text: '交易失败', value: 3 }, { text: '取消', value: 4 }]"
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
                    label="操作"
                    width="100">
                <template slot-scope="scope">
                    <el-button @click="viewClick(scope.row)" type="text" size="small">查看</el-button>
                    <el-button @click="deleteClick(scope.row)" type="text" size="small">取消</el-button>
                </template>
            </el-table-column>
        </el-table>
        </el-card>

        <el-dialog title="交易详情" :visible.sync="dialogTableVisible">
            <ul class="mdui-list">
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe8f6;</i>
                    <h4>交易商品</h4>
                    <div class="mdui-list-item-content">{{viewTableData.goods_name}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe87c;</i>
                    <h4>卖 家</h4>
                    <div class="mdui-list-item-content">{{viewTableData.user_name}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe863;</i>
                    <h4>置换商品</h4>
                    <div class="mdui-list-item-content">{{viewTableData.exchange_goods_name}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe227;</i>
                    <h4>差 价</h4>
                    <div class="mdui-list-item-content">{{viewTableData.price}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe192;</i>
                    <h4>交易时间</h4>
                    <div class="mdui-list-item-content">{{viewTableData.detailed_datetime | timeFormat}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe7ee;</i>
                    <h4>交易地点</h4>
                    <div class="mdui-list-item-content">{{viewTableData.detailed_address}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe551;</i>
                    <h4>联系方式</h4>
                    <div class="mdui-list-item-content">{{viewTableData.phone}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe5ca;</i>
                    <h4>交易状态</h4>
                    <div class="mdui-list-item-content">{{statusData[viewTableData.status]}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe0cb;</i>
                    <h4>备 注</h4>
                    <div class="mdui-list-item-content">{{viewTableData.remark}}</div>
                </li>
                <li class="mdui-list-item mdui-ripple">
                    <i class="mdui-icon material-icons">&#xe192;</i>
                    <h4>申请时间</h4>
                    <div class="mdui-list-item-content">{{viewTableData.create_time | timeFormat}}</div>
                </li>
            </ul>
        </el-dialog>


    </div>
</template>

<script>
    import {get, put} from "../utils/request";
    import moment from 'moment'
    export default {
        name: "TransRecords",
        data(){
            return{
                dialogTableVisible:false,
                userId:'',
                tableData:[],
                statusData:['申请中','交易中','交易完成','交易失败','交易取消'],
                viewTableData:{},
            }
        },
        filters:{
            timeFormat(timestr){
                return moment(timestr).format("YYYY-MM-DD HH:mm")
            }
        },
        created() {
            this.userId = this.$store.getters.getUser['user']['userId'];
            get('/transRecord/me/' + this.userId, {})
                .then(res => {
                    this.tableData = res['data'];
                    console.info(res)
                });
        },
        methods:{
            filterStatus(value, row) {
                return row.tag === value;
            },
            filterTag(value) {
                switch (value) {
                    case 0: return 'primary';
                    case 1: return 'info';
                    case 2: return 'success';
                    case 3: return 'danger';
                    default: return 'danger';
                }
            },
            viewClick(data){
                this.dialogTableVisible = true;
                this.viewTableData = data;
            },
            deleteClick(data){
                if(data['status'] >= 3){
                    this.$notify.error({
                        title: '错误',
                        message: '此交易已取消，请勿重复操作！'
                    });
                    return;
                }
                data['status'] = 4;
                put('/transRecord', data)
                    .then(res => {
                        this.$notify.success({
                            title: '成功',
                            message: '此交易已取消！'
                        });
                    });
            }
        }
    }
</script>

<style scoped>

</style>