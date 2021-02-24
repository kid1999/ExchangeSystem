/**
* @auther: kid1999
* @date: 2021/1/21 22:52
* @desciption:  Test
*/
<template>
    <div>
        <div class="mdui-list">
            <a href="#" class="mdui-list-item mdui-ripple">同城活动人数统计</a>
            <a href="#" class="mdui-list-item mdui-ripple">交易逻辑</a>
            <a href="#" class="mdui-list-item mdui-ripple">整合安全验证，处理header逻辑</a>
            <a href="#" class="mdui-list-item mdui-ripple">消息中心，消息推送</a>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Test",
        data(){
            return{
                strs:['a','ds','csd'],
            }
        },
        methods:{
            func(data){
                let res = '';
                for (s of data){
                    res += s + ',';
                }
                return res;
            },
            test(){

            }
        }
    }
</script>

<style scoped>

</style>