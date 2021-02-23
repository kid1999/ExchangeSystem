/**
* @auther: kid1999
* @date: 2021/2/23 17:20
* @desciption:  HotWords
*/
<template>
    <div id="hot">
        <span>热门搜索： </span>
        <div class="mdui-chip" @click="click2Search(hotWords[0])">
            <span class="mdui-chip-icon mdui-color-deep-orange-500">1</span>
            <span class="mdui-chip-title">{{hotWords[0]}}</span>
        </div>
        <div class="mdui-chip" @click="click2Search(hotWords[1])">
            <span class="mdui-chip-icon mdui-color-deep-orange-300">2</span>
            <span class="mdui-chip-title">{{hotWords[1]}}</span>
        </div>
        <div class="mdui-chip" @click="click2Search(hotWords[2])">
            <span class="mdui-chip-icon mdui-color-deep-orange-100">3</span>
            <span class="mdui-chip-title">{{hotWords[2]}}</span>
        </div>
        <div class="mdui-chip" @click="click2Search(hotWords[3])">
            <span class="mdui-chip-title">{{hotWords[3]}}</span>
        </div>
        <div class="mdui-chip" @click="click2Search(hotWords[4])">
            <span class="mdui-chip-title">{{hotWords[4]}}</span>
        </div>
    </div>

</template>

<script>
    import { get, post,put,deleted } from '../utils/request'
    export default {
        name: "HotWords",
        data(){
            return{
                title:'热搜',
                hotWords:['Java','Python',"JS",'C++','Golang'],
                search_context:'',
            }
        },
        created() {
            get('/goods/hotwords',{}).then(res =>{
                console.info(res.data);
                for (var i = 0; i < res.data.length ; i++) {
                    this.hotWords[i] = res.data[i];
                    this.$forceUpdate();
                }
            })
        },
        methods:{
            click2Search(context){
                this.$parent.search(context);
            }
        }

    }
</script>

<style scoped>
    #hot{
        margin-top: 5px;
    }
    .mdui-chip{
        margin-left: 10px;
    }
</style>