/**
* @auther: kid1999
* @date: 2021/2/17 18:46
* @desciption:  MyGoods
*/
<template>
    <div>
        <h1>我的商品</h1>
    <div id="list">
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
                                <router-link :to="'/goods/detail/' + goods.id">
                                    <el-button type="info" size="mini" round >查 看</el-button>
                                </router-link>
                                <router-link :to="'/NewGoods/' + goods.id">
                                    <el-button type="success" size="mini" round style="margin-left: 10px">修 改</el-button>
                                </router-link>
                            </div>
                        </el-card>
                    </div>

                </el-col>
            </div>
        </el-row>

    </div>
    </div>
</template>

<script>
    import { get, post,put,deleted } from '../utils/request'
    let moment = require('moment');
    export default {
        name: "MyGoods",
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
            let _this = this;
            let user = this.$store.getters.getUser['user'];
            // 获取user收藏的所有的物品
            get('/goods/user/' + user.id,{})
                .then(res => {
                    console.info(res);
                    this.goods_list = res['data'];
                });

            // 获取user的物品
            get('/goods/user/' + user['id'],{})
                .then(res => {
                    this.userGoods = res['data'];
                });
        },
        methods:{
            // 收藏
            collectionGoods(goods_id,cdeleted){
                post('/collection', {goodsId:goods_id,userId:this.$store.getters.getUser['user']['id']}).then(res => {
                    if(res['status'] === 200) {
                        this.$message.success(res['message']);
                        let goods_cdeleted = cdeleted == 0 ? 1 : 0;
                        for (var goods of this.goods_list) {
                            if(goods.id === goods_id){
                                goods.cdeleted = goods_cdeleted;
                            }
                        }
                    }
                }).catch(error => {
                    this.$message.error('收藏失败');
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
        }
    }
</script>

<style scoped>
    #goods_context{
        text-align: left;
    }
</style>