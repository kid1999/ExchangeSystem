/**
* @auther: kid1999
* @date: 2021/2/17 19:16
* @desciption:  NewGoods
*/
<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <h2>{{title}}</h2>
            </div>
            <el-form :model="goods" status-icon ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="商品图片" prop="goodsImg">
                    <el-upload
                            action="/api/file"
                            list-type="picture-card"
                            :limit="1"
                            :auto-upload="true"
                            :on-success="uploadImgSuccess"
                            :before-upload="beforeAvatarUpload"
                            :on-preview="handlePictureCardPreview"
                            :on-remove="handleRemove">
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible">
                        <img width="100%" :src="dialogImageUrl" alt="">
                    </el-dialog>
                </el-form-item>

                <el-form-item label="商品名" prop="goodsName" :rules="[{ required: true, message: '商品名不能为空'}]">
                    <el-input type="text" v-model="goods.goodsName" autocomplete="off" maxlength="30"
                              show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="商品状况" prop="goodsCondition" :rules="[{ required: true, message: '商品状况不能为空'}]">
                    <el-select v-model="goods.goodsCondition" placeholder="请选择商品状况" >
                        <el-option label="全新" value="全新"></el-option>
                        <el-option label="九成新" value="九成新"></el-option>
                        <el-option label="八成新" value="八成新"></el-option>
                        <el-option label="七成新" value="七成新"></el-option>
                        <el-option label="还能用" value="还能用"></el-option>
                        <el-option label="不能用" value="不能用"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="商品详情" prop="description">
                    <el-input type="text" v-model="goods.description" autocomplete="off" maxlength="120"
                              show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="商品备注" prop="remarks">
                    <el-input type="text" v-model="goods.remarks" autocomplete="off" maxlength="70"
                              show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="交换商品">
                    <el-autocomplete
                            v-model="search_context"
                            :fetch-suggestions="querySearchAsync"
                            :placeholder="goods.wantGoodsName"
                            @select="handleSelect"
                    ></el-autocomplete>
                </el-form-item>
                <el-form-item label="估值" prop="price">
                    <el-input type="number" v-model="goods.price" autocomplete="off" ></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="updateGoods('ruleForm')" >保存</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script>
    import { get, post,put,deleted } from '../utils/request'
    import moment from 'moment'
    export default {
        name: "NewGoods",
        data() {
            return {
                title:'上架商品',
                userId: '',
                username: '',
                search_context: '',
                canChangeable: true,
                id: '',
                dialogImageUrl: '',
                dialogVisible: false,
                goods: {},
                goodsImg:'',
                restaurants: [],
                timeout:  null

            }
        },
        created() {
            this.id = this.$route.params.id;
            if(typeof (this.id) !== "undefined"){
                get('/goods/' + this.id, {}).then(res => {
                    this.title =  '修改商品信息';
                    if(res['status'] === 200) {
                        console.info(res);
                        this.goods = res['data'];
                    }
                });
            }
            // 获取user的物品
            this.userId = this.$store.getters.getUser['user']['id'];
            this.username = this.$store.getters.getUser['user']['username'];
            this.goods.userId = this.userId;
            this.goods.addressId = this.$store.getters.getUser['user']['addressId'];
            this.goods.address = '玉蟾国际城'
            this.loadRemoteGoods();
        },
        methods: {
            updateGoods(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if(typeof(this.goods.wantGoodsName) == "undefined"){
                            this.$message.error("交换商品不能为空！");
                            return;
                        }
                        if(typeof (this.id) !== "undefined"){
                            put('/goods', this.goods)
                                .then(res => {
                                    this.$message.success("保存成功！")
                                });
                        }else{
                            post('/goods', this.goods)
                                .then(res => {
                                    this.$notify.success("保存成功！")
                                });
                        }

                    }else{
                        this.$message.error("请完善各种信息！");
                    }
                })
            },
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },
            beforeAvatarUpload(file) {
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isLt2M) {
                    this.$message.error('上传图片大小不能超过 2MB!');
                }
                return isLt2M;
            },
            uploadImgSuccess(response, file, fileList) {
                this.$message.success("图片上传成功！");
                this.goods.imgUrl = response['data']['fileName'];
            },
            loadRemoteGoods(){
                get('/goods/search', {"goodsName" : this.search_context,current_page: 0,page_size: 25})
                    .then(res => {
                        this.restaurants = res['data']['content'].map((obj) => {
                            return {
                                value: obj.goodsName,
                                name: obj.id,
                            };
                        });
                        console.info(this.restaurants);
                    });
            },
            handleSelect(item){
                this.goods.wantGoodsId = item.name;
                this.goods.wantGoodsName = item.value;
            },
            //输入框获取焦点时调用的方法
            querySearchAsync(queryString, cb) {
                get('/goods/search', {"goodsName" : queryString,current_page: 0,page_size: 25})
                    .then(res => {
                        this.restaurants = res['data']['content'].map((obj) => {
                            return {
                                value: obj.goodsName,
                                name: obj.id,
                            };
                        });
                        cb(this.restaurants);
                        console.info(this.restaurants);
                    });
            },
        },
    }
</script>

<style scoped>

</style>