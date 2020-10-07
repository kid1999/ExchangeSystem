/**
* @auther: kid1999
* @date: 2020/10/8 0:15
* @desciption:  Login
*/

<template>
    <a-form-model layout="inline" :model="formInline" @submit="handleSubmit" @submit.native.prevent>
        <a-form-model-item>
            <a-input v-model="formInline.user" placeholder="Username">
                <a-icon slot="prefix" type="user" style="color:rgba(0,0,0,.25)" />
            </a-input>
        </a-form-model-item>
        <a-form-model-item>
            <a-input v-model="formInline.password" type="password" placeholder="Password">
                <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
            </a-input>
        </a-form-model-item>
        <a-form-model-item>
            <a-button
                    type="primary"
                    html-type="submit"
                    :disabled="formInline.user === '' || formInline.password === ''"
            >
                Log in
            </a-button>
        </a-form-model-item>
    </a-form-model>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                formInline: {
                    name: '',
                    password: '',
                },
            };
        },
        methods: {
            handleSubmit(e) {
                console.log(this.formInline);
            },
        },
    }
</script>

<style scoped>

</style>