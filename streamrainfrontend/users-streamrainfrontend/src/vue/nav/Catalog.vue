<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left"> 
        <h1>{{ tenant }} Catalog</h1>
        <hr>
        <streamrain-janus></streamrain-janus>
      </div>
      <div class="col-sm-2 sidenav">
      </div>
    </div>
  </div>
</template>

<script>
  import Janus from '../streaming/janus/Janus.vue';
  export default {
    props: [
      'tenant'
    ],
    components: {
      'streamrain-janus': Janus
    }
  }
</script>
