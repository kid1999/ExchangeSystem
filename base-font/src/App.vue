<template>
  <div id="app">
    <div id="header">
      <Header></Header>
    </div>
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link> |
      <router-link to="/axios">Axios</router-link>
    </div>
    <router-view/>
  </div>
</template>




<script>
  import Header from '@/views/Header.vue'
  export default {
    name: 'Home',
    components: {
      Header,
    }
  }
</script>





<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
