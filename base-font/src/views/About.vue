<template>
  <div class="about">
    <h1>This is an about page</h1>
    <p>login</p>
    <Verify></Verify>
  </div>
</template>

<script>
  // @ is an alias to /src
  import Verify from '@/components/Verify.vue'
  export default {
    name: 'Home',
    components: {
      Verify
    }
  }
</script>
