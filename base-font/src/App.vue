<template>
  <div id="app">
    <el-backtop>
      <div
              style="{
        height: 100%;
        width: 100%;
        background-color: #f2f5f6;
        box-shadow: 0 0 6px rgba(0,0,0, .12);
        text-align: center;
        line-height: 40px;
        color: #1989fa;
      }"
      >
        UP
      </div>
    </el-backtop>

    <el-row :gutter="24">
      <el-col :span="18" :offset="3">
        <el-container id="container">
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

html,body,#app {height: 100%;}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height:100%;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}


#container{
  /*保证footer是相对于container位置绝对定位*/
  position:relative;
  width:100%;
  min-height:100%;
  height:100%;
  /*设置padding-bottom值大于等于footer的height值，以保证main的内容能够全部显示出来而不被footer遮盖；*/
  padding-bottom: 100px;
  box-sizing: border-box;
}

</style>
