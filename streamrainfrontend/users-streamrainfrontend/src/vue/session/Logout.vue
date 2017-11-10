<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left"> 
        <h1>Log Out</h1>
        <br>
        <div class="form-group">
          <button v-on:click="logout" class="btn btn-primary">Log Out</button>
        </div>
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
      'config',
      'eventBus'
    ],
    methods: {
      logout: function () {
        this.eventBus.$emit('removeSession', null);
        this.$router.push('/');
      }
    }
  }
</script>
