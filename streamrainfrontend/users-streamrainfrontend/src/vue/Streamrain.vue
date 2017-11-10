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
            <li><router-link to="/catalog">Catalog</router-link></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li v-if="!session"><router-link to="/login">Log In</router-link></li>
            <li v-if="session"><router-link to="/logout">Log Out</router-link></li>
            <li><router-link to="/signup"><span class="text-danger">Sign Up!</span></router-link></li>
          </ul>
        </div>
      </div>
    </nav>
    <router-view 
      :config="config"
      :eventBus="eventBus"
      :janusAlert="janusAlert"
      :session="session"
    >
    </router-view>
    <!-- <streamrain-janus 
      :config="config"
      :eventBus="eventBus"
    >
    </streamrain-janus> -->
    <footer class="container-fluid text-center">
      <p>Streamrain {{ config.tenant.name }} footer</p>
    </footer>
  </div>
</template>

<script>
  import Janus from './streaming/janus/Janus.vue';
  export default {
    name: 'streamrain',
    props: [
      'config',
      'eventBus'
    ],
    components: {
      'streamrain-janus': Janus
    },
    data () {
      return {
        janusAlert: null,
        session: null
      }
    },
    created () {
      const updateSession = this.updateSession;
      const removeSession = this.removeSession;
      this.eventBus.$on('setJanusAlert', function (janusAlert) {
        this.janusAlert = janusAlert;
      }, this);
      this.eventBus.$on('setSession', function (session) {
        updateSession(session);
      }, this);
      this.eventBus.$on('removeSession', function () {
        removeSession();
      }, this);
    },
    methods: {
      updateSession: function (session) {
        this.session = session;
      },
      removeSession: function () {
        this.session = null;
      }
    }
  }
</script>
