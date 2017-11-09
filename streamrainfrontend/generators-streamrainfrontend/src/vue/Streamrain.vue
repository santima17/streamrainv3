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
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="true">Content <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li><router-link to="/content/alwaysAvailable">Always Available</router-link></li>
                <li><router-link to="/content/liveOnly">Live Only</router-link></li>
                <li class="divider"></li>
                <li><router-link to="/content/similar">Similar</router-link></li>
                <li><router-link to="/content/featured">Featured</router-link></li>
                <li class="divider"></li>
                <li><router-link to="/content/categories">Categories</router-link></li>
                <li><router-link to="/content/casts">Casts</router-link></li>
              </ul>
            </li>
            <li><router-link to="/">Users</router-link></li>
            <li><router-link to="/">Payments</router-link></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="true">Resources <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li><router-link to="/">Nginx</router-link></li>
                <li><router-link to="/resources/janus">Janus</router-link></li>
              </ul>
            </li>
            <li><router-link to="/"><span class="text-info">Statistics</span></router-link></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><router-link to="/login"><span class="text-danger">Log In</span></router-link></li>
          </ul>
        </div>
      </div>
    </nav>
    <router-view 
      :config="config"
      :eventBus="eventBus"
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
      this.eventBus.$on('setToken', (token) => {
        this.token = token;
      }, this);
    }
  }
</script>

<style>
body    {overflow-y:scroll;}
</style>
