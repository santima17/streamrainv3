<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left"> 
        <h1>{{ config.tenant.name }}, Log Out</h1>
        <hr>
        <div class="form-group">
          <button v-on:click="logout" class="btn btn-default">Log Out</button>
        </div>
        <br>
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
        localStorage.removeItem(`streamrain-${this.config.tenant.name.replace(/\s/g, '')}-session`);
        this.eventBus.$emit('removeVueSession', null);
        this.$router.push('/');
      }
    }
  }
</script>
