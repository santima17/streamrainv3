<template>
  <div id="streamrain">
    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand">Streamrain {{ config.tenant.name }}</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            <li><router-link to="/">Home</router-link></li>
            <li><router-link to="/categories">Categories</router-link></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><router-link to="/login">Log In</router-link></li>
            <li><router-link to="/signup"><span class="text-danger">Sign Up!</span></router-link></li>
          </ul>
        </div>
      </div>
    </nav>
    <router-view 
      :config="config"
      :eventBus="eventBus"
      :token="token"
    >
    </router-view>
    <footer class="container-fluid text-center">
      <p>Streamrain {{ config.tenant.name }} footer</p>
    </footer>
  </div>
</template>

<script>
  export default {
    name: 'streamrain',
    props: [
      'config',
      'eventBus'
    ],
    data () {
      return {
        token: null
      }
    },
    created () {
      // const updateJanusAlert = this.updateJanusAlert;
      const i = this;
      i.eventBus.$on('setToken', function (token) {
        i.token = token;
      });
    },
    // methods: {
    //   updateJanusAlert: function (janusAlert) {
    //     this.janusAlert = janusAlert;
    //   }
    // }
  }
</script>
