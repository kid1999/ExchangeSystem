/**
* @auther: kid1999
* @date: 2020/10/7 23:07
* @desciption:  Axios
*/
<template>
    <div>
        <el-button v-on:click="getFunc()">get</el-button>
        <el-button v-on:click="postFunc()">post</el-button>
    </div>

</template>

<script>
    import { getTest,postTest } from '@/apis/axios'
    export default {
        name: "Axios",
        data(){
            return{
                params : { name: 'admin', password: '123456'},
            }
        },
        methods:{
            getFunc:function (event) {
                getTest(this.params).then(res => {
                    console.log(res, '这是get响应的结果')
                })
            },
            postFunc:function (event) {
                postTest(this.params).then(res => {
                    console.log(res, '这是post响应的结果')
                })
            },
        },
        // mounted() {
        //     let params = { name: 'admin', password: '123456'}
        //     getTest(params).then(res => {
        //         console.log(res, '这是get响应的结果')
        //     })
        //
        //     postTest(params).then(res => {
        //         console.log(res, '这是post响应的结果')
        //     })
        // }
    }
</script>

<style scoped>

</style>