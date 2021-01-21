/**
* @auther: kid1999
* @date: 2021/1/21 22:52
* @desciption:  Test
*/
<template>
    <div>
        test
        <el-butun
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