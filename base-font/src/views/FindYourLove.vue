/**
* @auther: kid1999
* @date: 2021/2/17 16:14
* @desciption:  FindYourLove
*/
<template>
    <div>
        <h1>猜你喜欢</h1>

        <div id="list" v-loading="loading">
            <el-row>
                <div v-for="goods in goods_list">
                    <el-col :span="6">

                        <div class="grid-content bg-purple" id="goods">
                            <el-card class="box-card">
                                <div slot="header" id="header-font">
                                    <span>
                                        <router-link :to="'/goods/detail/' + goods.id">
                                        <el-link :underline="false">
                                            <h2>{{goods.goodsName | filterTitle}}</h2>
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
                                        商品状况<i class="el-icon-s-goods el-icon--right"></i>：
                                        <strong>{{goods.goodsCondition }}</strong>
                                    </p>
                                    <p>
                                        <span>点击量 </span><i class="el-icon-s-promotion el-icon--left"></i>：  <strong>{{goods.numberOfClicked }}</strong>
                                    </p>
                                    <p>
                                        详情<i class="el-icon-s-goods el-icon--right"></i>：
                                        {{goods.description | filterTitleMini}}
                                    </p>
                                </div>
                                <div id="buy">
                                    <el-button type="success" size="mini" round  @click="applyExchange(goods.id)">申请交易</el-button>
                                    <router-link :to="'/goods/detail/' + goods.id">
                                        <el-button type="info" size="mini" round style="margin-left: 10px">了解详情</el-button>
                                    </router-link>
                                </div>
                            </el-card>
                        </div>

                    </el-col>
                </div>
            </el-row>

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
    let moment = require('moment');
    export default {
        name: "FindYourLove",
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
                    "goods_status": 0,
                    "goods_name": "手机",
                    "address": "北京市市辖区东城区",
                    "want_goods_name": "手机",
                    "want_goods_id": 1,
                    "username": "kid",
                    "number_of_clicked": 666,
                    "address_id": 1,
                    "description": "sad",
                    "deleted": 0,
                    "user_id": 1,
                    "img_url": "http://kid1999.top:9000/default/avatar.png",
                    "price": 11111,
                    "id": 1,
                    "create_date": "2021-01-20T22:59:58.000+00:00",
                    "remarks": "sd",
                    "goods_condition": "全新"
                }],
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
                loading:true,

            }
        },
        filters: {
            formatDate: function (time) {
                return moment(time).format('YYYY-MM-DD');
            },
            filterTitle(title){
                if(typeof title === 'undefined'){
                    return "不想让你知道哦"
                }
                if(title.length < 12){
                    return title
                }else{
                    return title.slice(0,10) + '...';
                }
            },
            filterTitleMini(title){
                if(typeof title === 'undefined'){
                    return "不想让你知道哦"
                }
                if(title.length < 9){
                    return title
                }else{
                    return title.slice(0,8) + '..';
                }
            }
        },
        created() {
            this.user = this.$store.getters.getUser['user'];
            this.$notify.info({
                title: '提示',
                message: '由于服务器运算性能问题，初次加载可能会失败！'
            });
            // 获取为user推荐的所有的物品
            get('/goods/recommendation/' + this.user.id,{})
                .then(res => {
                    console.info(res);
                    this.goods_list = res['data'];
                    this.loading = false;
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
                this.applyValidateForm.user2Id = this.user.id;
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
        }
    }
</script>

<style scoped>

</style>