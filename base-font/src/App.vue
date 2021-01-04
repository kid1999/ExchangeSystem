<template>
  <div id="app">
    <el-row :gutter="20">
      <el-col :span="16" :offset="3">
        <el-container>
          <el-header>
            <Header></Header>
          </el-header>
          <el-main>
            <router-view/>
          </el-main>
          <el-footer>
            <Footer></Footer>
          </el-footer>
        </el-container>
      </el-col>
    </el-row>

  </div>
</template>




<script>
  import Header from '@/views/Header.vue'
  import Footer from '@/views/Footer.vue'
  export default {
    name: 'Home',
    components: {
      Header,
      Footer,
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
