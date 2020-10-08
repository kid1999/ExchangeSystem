<template>
  <div class="test">
    <h1>This is an about page</h1>
    <a-button type="primary" loading> Loading </a-button>
    <a-button v-on:click="test">test</a-button>
  </div>
</template>

<script>
	export default {
		name: "Test",
    methods:{
			test(){
				this.$ajax.get('/getPath')
					.then(function (response) {
						console.log(response);
					})
					.catch(function (error) {
						console.log(error);
					});
      }
    }
	}
</script>

<style scoped>

</style>
