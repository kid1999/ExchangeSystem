/**
* @auther: kid1999
* @date: 2020/10/1 9:00
* @desciption:  验证码模块
* doc : https://github.com/mizuka-wu/vue2-verify
*/
<template>
    <!--type = 1 常规验证码-->
    <!--type = 2 算数-->
    <!--type = 3 滑动-->
    <!--type = 4 滑动拼图-->
    <!--type = 5 选择汉字-->
    <Verify @success="alert('success')" @error="alert('error')" :showButton="false" :type="3"></Verify>
</template>

<script>
    import Verify from 'vue2-verify'

    export default {
        name: "verify",
        methods: {
            alert(text) {
                console.log(text+"99")
            }
        },
        components: {
            Verify
        }
    }
</script>

<style scoped>

</style>