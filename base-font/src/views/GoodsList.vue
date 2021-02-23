/**
* @auther: kid1999
* @date: 2021/1/4 19:39
* @desciption:  GoodsList
*
* 期望交换 ： goods_id ->
* 收藏情况 ： goods_id -> collection_id -> user_id
*/
<template>
    <div>
        <h1>商品列表</h1>

        <div id="search">
            <el-row>
                <el-input placeholder="请输入内容搜索" v-model="search_context" class="input-with-select">
                    <el-button slot="append" icon="el-icon-search" @click="search(search_context)"></el-button>
                </el-input>
            </el-row>
        </div>

        <div id="hotwords">
            <HotWords></HotWords>
        </div>


        <div id="list">
            <el-row>
                <div v-for="goods in goods_list">
                    <el-col :span="6">

                        <div class="grid-content bg-purple" id="goods">
                            <el-card class="box-card" >
                                <div slot="header" id="header-font">
                                    <span>
                                        <router-link :to="'/goods/detail/' + goods.id">
                                            <el-link :underline="false">
                                            <h2>{{goods.goodsName}}</h2>
                                            </el-link>
                                        </router-link>
                                    </span>
                                </div>

                                <div class="mdui-card-media">
                                    <img :src="goods.imgUrl" style="height: 230px"/>

                                    <div class="mdui-card-menu" v-if="goods.goodsStatus === 0">
                                        <el-tooltip class="item" effect="dark" content="收藏" placement="top">
                                            <button class="mdui-btn mdui-btn-icon mdui-text-color-white" @click="collectionGoods(goods.id,goods.goodsStatus)">
                                                <i class="mdui-icon material-icons">&#xe87e;</i>
                                            </button>
                                        </el-tooltip>
                                    </div>
                                    <div class="mdui-card-menu" v-else="goods.goodsStatus === 0">
                                        <el-tooltip class="item" effect="dark" content="已收藏" placement="top">
                                            <button class="mdui-btn mdui-btn-icon mdui-text-color-white" @click="collectionGoods(goods.id,goods.goodsStatus)">
                                                <i class="mdui-icon material-icons">&#xe87d;</i>
                                            </button>
                                        </el-tooltip>
                                    </div>



                                    <div class="mdui-grid-tile-actions">
                                        <div class="mdui-grid-tile-text">
                                            <div class="mdui-grid-tile-title">{{goods.address}}</div>
                                            <div class="mdui-grid-tile-subtitle"><i class="mdui-icon material-icons">grid_on</i>{{goods.createDate | formatDate}}</div>
                                        </div>
                                    </div>
                                </div>

                                <div id="goods_context">
                                    <p>
                                        期望交换<i class="el-icon-s-goods el-icon--right"></i>：
                                        <router-link :to="'/goods/detail/' + goods.wantGoodsId">
                                            <el-link :underline="false"  >
                                            <strong>{{goods.wantGoodsName }}</strong>
                                        </el-link>
                                        </router-link>

                                    </p>
                                    <p>
                                        <span>点击量 </span><i class="el-icon-s-promotion el-icon--left"></i>：  <strong>{{goods.numberOfClicked }}</strong>
                                    </p>
                                    <p>
                                        所有者<i class="el-icon-s-custom el-icon--right"></i>：
                                        <router-link :to="'/userInfo/' + goods.userId">
                                            <el-link :underline="false"  >
                                            <strong>{{goods.username }}</strong>
                                            </el-link>
                                        </router-link>
                                    </p>
                                </div>
                                <div id="buy">
                                    <el-button type="success" size="mini" round  @click="applyExchange(goods.id)">申请交易</el-button>

                                    <router-link :to="'/goods/detail/' + goods.id">
                                        <el-button type="info" size="mini" round >查看详情</el-button>
                                    </router-link>

                                </div>
                            </el-card>
                        </div>

                    </el-col>
                </div>
            </el-row>

            <div id="pagination">
                <el-pagination
                        background
                        layout="prev, pager, next"
                        @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        :page-size="pageSize"
                        :total="total">
                </el-pagination>
            </div>

        </div>

        <!--    购买商品弹窗    -->
        <el-dialog title="申请交易" :visible.sync="buyVisible">
            <el-form :model="applyValidateForm" ref="applyValidateForm" class="demo-ruleForm">
                <el-form-item
                        label="交换物品"
                        prop="exchangeGoodsId"
                        :rules="[{ required: true, message: '交换物品不能为空'}]">
                    <el-select v-model="applyValidateForm.exchangeGoodsId" filterable type="exchangeGoodsId"  placeholder="请选择交换物品">
                        <el-option
                                v-for="goods in userGoods"
                                :key="goods.id"
                                :label="goods.goodsName"
                                :value="goods.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item
                        label="补差价"
                        prop="price"
                        :rules="[{pattern: /^-?\d+\.?\d*$/, message: '请输入正确数据', trigger: 'blur'}]">
                    <el-input type="price" v-model="applyValidateForm.price" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item
                        label="交易地址"
                        prop="detailedAddress"
                        :rules="[{ required: true, message: '详细地址不能为空'}]">
                    <el-input type="detailedAddress" v-model="applyValidateForm.detailedAddress" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item
                        label="交易时间"
                        prop="detailedDatetime"
                        :rules="[{ required: true, message: '交易时间不能为空'}]">
                    <el-date-picker
                            v-model="applyValidateForm.detailedDatetime"
                            type="datetime"
                            placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item
                        label="联系方式"
                        prop="phone"
                        :rules="[{ required: true, message: '请输入手机号', trigger: 'blur' }, { pattern: /^((0\d{2,3}-\d{7,8})|(1[3456789]\d{9}))$/, message: '请输入合法手机号/电话号', trigger: 'blur' }]">
                    <el-input type="phone" v-model="applyValidateForm.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item
                        label="备注"
                        prop="remark">
                    <el-input type="remark" v-model="applyValidateForm.remark" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm('applyValidateForm')">确 定</el-button>
            <el-button @click="buyVisible = false">取 消</el-button>
        </div>
        </el-dialog>

    </div>
</template>

<script>
    import { get, post,put,deleted } from '../utils/request'
    import HotWords from "../components/HotWords";
    let moment = require('moment');
    export default {
        name: "GoodsList",
        components:{'HotWords':HotWords},
        data() {
            return {
                buyVisible: false,
                search_context:'',
                userGoods:[{
                    id: '1',
                    goodsName: '黄金糕'
                },{
                    id: '2',
                    goodsName: '索尼爱立信'
                },{
                    id: '3',
                    goodsName: 'iphone'
                }],
                goods_list:[{
                    "goodsStatus": 0,
                    "goodsName": "手机",
                    "address": "北京市市辖区东城区",
                    "wantGoodsName": "手机",
                    "wantGoodsId": 1,
                    "username": "kid",
                    "numberOfClicked": 666,
                    "addressId": 1,
                    "description": "sad",
                    "deleted": 0,
                    "userId": 1,
                    "imgUrl": "http://kid1999.top:9000/default/avatar.png",
                    "price": 11111,
                    "id": 1,
                    "createDate": "2021-01-20T22:59:58",
                    "remarks": "sd",
                    "goodsCondition": "全新"
                }],
                currentPage:0,
                pageSize:20,
                total:100,
                currentDate: new Date(),
                transaction_goods_id:1,
                applyValidateForm:{
                    user1Id:'',
                    user2Id:'',
                    remark:'',
                    goodsId:'',
                    price:'',
                    phone:'',
                    exchangeGoodsId:'',
                    detailedAddress:'',
                    detailedDatetime:'',
                },
                user:{},
            }
        },
        filters: {
            formatDate: function (time) {
                return moment(time).format('YYYY-MM-DD');
            },
        },
        created() {
            this.user = this.$store.getters.getUser['user'];
            const data = {"current_page":this.currentPage,"page_size":this.pageSize,userId:this.user['id']};
            get('/goods', data)
                .then(res => {
                    this.goods_list = res['data']['records'];
                    this.total = res['data']['total'];
                    this.currentPage = res['data']['current'];
                    this.pageSize = res['data']['size'];
                    console.info(this.goods_list);
                });

            // 获取user的物品
            get('/goods/user/' + this.user['id'],{})
                .then(res => {
                    this.userGoods = res['data'];
                });
        },
        methods:{
            // 收藏
            collectionGoods(goods_id,status){
                if(status !== 0){
                    this.$notify.info("你已收藏该商品，请前往收藏夹查看！");
                    return;
                }
                post('/collection', {goodsId:goods_id,userId:this.user['id']}).then(res => {
                    if(res['status'] === 200) {
                        this.$message.success('收藏成功！');
                        for (var goods of this.goods_list) {
                            if(goods.id === goods_id){
                                goods.goodsStatus = 1;
                            }
                        }
                    }else{
                        this.$message.error('收藏失败！');
                    }
                }).catch(error => {
                    this.$message.error('收藏失败！');
                });
            },
            // 申请交换
            applyExchange(goods_id){
                this.buyVisible = true;
                this.transaction_goods_id = goods_id;
            },
            // 申请交换 到 远程
            submitForm(formName) {
                this.buyVisible = false;
                this.applyValidateForm.user2Id = this.$store.getters.getUser['user']['userId'];
                this.applyValidateForm.user1Id = this.goods_list[this.transaction_goods_id]['user_id'];
                this.applyValidateForm.goodsId = this.transaction_goods_id;
                console.info(this.applyValidateForm);
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        post('/transRecord', this.applyValidateForm).then(res => {
                            if(res['status'] === 200) {
                                this.$message.success("申请成功！");
                                console.info(res);
                            }else if(res['status'] === 201){
                                this.$message.warning("交易时间已过");
                            }
                        });
                    } else {
                        this.$message.error("申请失败，请重试！");
                        return false;
                    }
                });
            },
            // 搜索
            search(context){
                this.search_context = context;
                console.info(context)
                get('/goods/search', {"goodsName" : this.search_context,current_page: this.currentPage,page_size: this.pageSize})
                    .then(res => {
                        console.info(res)
                        this.goods_list = res['data']['content'];
                        this.total = res['data']['totalPages'];
                        this.currentPage = res['data']['number'];
                        this.pageSize = res['data']['size'];
                        console.info(this.goods_list);
                    });
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                let _this = this;
                let user = this.$store.getters.getUser['user'];
                const data = {"current_page":val,"page_size":_this.pageSize,userId:user['id']};
                get('/goods', data)
                    .then(res => {
                        this.goods_list = res['data']['records'];
                        this.total = res['data']['total'];
                        this.currentPage = res['data']['current'];
                        this.pageSize = res['data']['size'];
                        console.info(this.goods_list);
                    });
            }
        }

    }
</script>

<style>

    #pagination{
        margin-top: 18px;
    }

    .el-card__header{
        padding: 0 !important;
    }

    .text{
        font-size: 13px;
    }

    #list{
        margin-top: 5px;
    }

    #buy{
        margin-top: 18px;
    }

    #goods{
        margin-top: 18px;
        margin-left: 10px;
    }

</style>
