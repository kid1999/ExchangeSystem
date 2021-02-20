/**
* @auther: kid1999
* @date: 2021/1/4 19:41
* @desciption:  GoodsDetail
*/
<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <h2>商品详情页</h2>
            </div>
            <el-form :model="goods" status-icon ref="goods" label-width="100px" class="demo-ruleForm">
                <el-form-item label="商品图片" prop="img_url" v-if="canChangeable">
                    <div class="block" >
                        <el-image
                                style="width: 100px; height: 100px"
                                :src="goods.imgUrl"
                                fit="fit"></el-image>
                    </div>
                </el-form-item>
            <el-form-item label="商品图片" prop="img_url" v-else="canChangeable" :rules="[{ required: true, message: '图片不能为空'}]">
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
                <el-input type="text" v-model="goods.goodsName" autocomplete="off" :disabled="canChangeable"></el-input>
            </el-form-item>
            <el-form-item label="商品状况" prop="goodsCondition" :rules="[{ required: true, message: '商品状况不能为空'}]">
                <el-select v-model="goods.goodsCondition" placeholder="请选择商品状况" :disabled="canChangeable">
                    <el-option label="全新" value="全新"></el-option>
                    <el-option label="九成新" value="九成新"></el-option>
                    <el-option label="八成新" value="八成新"></el-option>
                    <el-option label="七成新" value="七成新"></el-option>
                    <el-option label="还能用" value="还能用"></el-option>
                    <el-option label="不能用" value="不能用"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="商品详情" prop="description">
                <el-input type="text" v-model="goods.description" autocomplete="off" :disabled="canChangeable"></el-input>
            </el-form-item>
            <el-form-item label="商品状态">
                <el-input type="text" v-model="goods_status"  disabled autocomplete="off" disabled></el-input>
            </el-form-item>
            <el-form-item label="商品备注" prop="remarks">
                <el-input type="text" v-model="goods.remarks" autocomplete="off" :disabled="canChangeable"></el-input>
            </el-form-item>
            <el-form-item label="交换商品" prop="wantGoodsName" :rules="[{ required: true, message: '期待交换商品不能为空'}]">
                <el-select v-model="goods.wantGoodsName" filterable placeholder="请输入选择" @blur="getExchangeGoods" :disabled="canChangeable">
                    <el-option
                            v-for="goods in remoteGoods"
                            :key="goods.id"
                            :label="goods.goodsName"
                            :value="goods.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="估值" prop="price">
                <el-input type="text" v-model="goods.price" autocomplete="off" :disabled="canChangeable"></el-input>
            </el-form-item>
            <el-form-item label="商品所有者" prop="user_name">
                <el-input type="text" v-model="goods.username" autocomplete="off" disabled></el-input>
            </el-form-item>
            <el-form-item label="上架时间" prop="create_date">
                <el-input type="text" v-model="formatCreateTime" autocomplete="off" disabled></el-input>
            </el-form-item>
            <el-form-item label="商品访问量" prop="number_of_clicked">
                <el-input type="text" v-model="goods.numberOfClicked" autocomplete="off" disabled></el-input>
            </el-form-item>
            <el-form-item>
                <el-button  @click="canChangeable = false" v-if="userId === goods.userId">修改资料</el-button>
                <el-button type="primary" @click="updateGoods" v-if="userId === goods.userId">保存修改</el-button>
            </el-form-item>
            </el-form>
        </el-card>

        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <h2>留言评论</h2>
            </div>
            <el-collapse accordion>
                <div>
                    <el-input
                            type="textarea"
                            placeholder="请输入内容"
                            v-model="newComment"
                            maxlength="50"
                            show-word-limit
                    >
                    </el-input>
                    <el-button type="primary" size="small" @click="pushComment">发布留言</el-button>
                </div>
                <el-collapse-item v-if="goodsComment.length !== 0" v-for="comment in goodsComment">
                    <template slot="title"  >
                        <el-link :underline="false" :href="'/userInfo/' + comment.user1_id"><h3>{{comment.username}}</h3></el-link> - {{comment.date | timeFormat}}<i class="header-icon el-icon-info"></i>
                    </template>
                    <div>{{comment.context}}</div>
                </el-collapse-item>

                <el-collapse-item v-else="goodsComment.length !== 0">
                    <template slot="title"  >
                        <h3>暂无留言</h3><i class="header-icon el-icon-info"></i>
                    </template>
                </el-collapse-item>
            </el-collapse>
        </el-card>


    </div>
</template>

<script>
    import { get, post,put,deleted } from '../../utils/request'
    import moment from 'moment'
    export default {
        name: "GoodsDetail",
        data() {
            return {
                newComment:'',
                userId:'',
                username:'',
                search_context:'',
                canChangeable:true,
                id:'',
                dialogImageUrl: '',
                dialogVisible:false,
                goods:{},
                remoteGoods:[],
                goodsComment:[],
            }
        },
        created() {
            this.id = this.$route.params.id;
            get('/goods/' + this.id, {}).then(res => {
                if(res['status'] === 200) {
                    console.info(res);
                    this.goods = res['data'];
                }
            });
            // 获取user的物品
            this.userId = this.$store.getters.getUser['user']['id'];
            this.username = this.$store.getters.getUser['user']['username'];
            get('/goods/search', {"goodsName" : this.search_context,current_page: 0,page_size: 25})
                .then(res => {
                    this.remoteGoods = res['data']['records'];
                    console.info(this.remoteGoods);
                });
            // 获取goods的评论
            get('/comment/goods/' + this.id, {})
                .then(res => {
                    this.goodsComment = res['data'];
                    console.info(this.goodsComment);
                });
        },
        computed:{
            goods_status(){
                switch (this.goods.goods_status) {
                    case 0 : return "等待交易中"
                    case 1 : return "请求交易中"
                    case 2 : return "交易中"
                    case 3 : return "交易顺利完成"
                    case 10 : return "已下架"
                }
            },
            formatCreateTime(){
                return moment(this.goods.create_date).format("YYYY-MM-DD HH:mm")

            }
        },
        filters:{
            timeFormat(timestr){
                return moment(timestr).format("YYYY-MM-DD HH:mm")
            }
        },
        methods:{
            pushComment(){
                console.info(this.goods['user_id'])
                const data = {user1Id:this.userId,user2Id:this.goods['user_id'],goodsId:this.id,context:this.newComment};
                post('/comment', data)
                    .then(res => {
                        this.$message.success("发布留言成功");
                        this.goodsComment.push(data);
                    });
            },
            updateGoods(){
                put('/goods', this.goods)
                    .then(res => {
                        this.$message.success("修改成功")
                    });
            },
            getExchangeGoods(){
                get('/goods/search', {"goodsName" : this.search_context,current_page: 0,page_size: 25})
                    .then(res => {
                        this.remoteGoods = res['data']['records'];
                        console.info(this.remoteGoods);
                    });
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
            uploadImgSuccess(response, file, fileList){
                this.$message.success("图片上传成功！");
                this.ruleForm.avatarUrl = response['data']['fileName'];
                console.info(this.ruleForm);
            },
        }
    }
</script>

<style scoped>
    .item {
        margin-bottom: 18px;
    }
</style>