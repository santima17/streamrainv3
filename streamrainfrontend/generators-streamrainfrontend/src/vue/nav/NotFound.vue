<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left"> 
        <h1 class="text-center">404</h1>
        <h2 class="text-center">Sorry, page not found</h2>
        <hr>
      </div>
      <div class="col-sm-2 sidenav">
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    props: [
      'config'
    ],
    created () {
      document.title = `${this.config.tenant.name} | Page Not Found`;
    },
  }
</script>