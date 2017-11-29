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
            <li><router-link to="/subscription">Subscription</router-link></li>
            <li>
              <router-link to="/notifications">
                <i class="glyphicon glyphicon-bell"></i>
              </router-link>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li v-if="!session"><router-link to="/login">Log In</router-link></li>
            <li v-if="session"><router-link to="/logout">Welcome, {{ session.nickname }}</router-link></li>
            <li><router-link to="/signup"><span class="text-danger">Sign Up!</span></router-link></li>
          </ul>
        </div>
      </div>
    </nav>
    <router-view
      :config="config"
      :eventBus="eventBus"
      :session="session"
      :alert="alert"
    >
    </router-view>
    <streamrain-janus v-if="session"
      :config="config"
      :eventBus="eventBus"
      :session="session"
    >
    </streamrain-janus>
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
        session: null,
        alert: {
          show: false,
          message: null
        }
      }
    },
    created () {
      const localStorageSession = JSON.parse(localStorage.getItem(`streamrain-${this.config.tenant.name.replace(/\s/g, '')}-session`)) || {};
      if (Object.keys(localStorageSession).length) this.session = localStorageSession;

      console.error(JSON.stringify(localStorageSession));

      const updateSession = this.updateSession;
      const removeSession = this.removeSession;
      const updateAlert = this.updateAlert;

      this.eventBus.$on('setAlert', function (alert) {
        updateAlert(alert);
      });

      this.eventBus.$on('setVueSession', function (session) {
        updateSession(session);
      });

      this.eventBus.$on('removeVueSession', function () {
        removeSession();
      });
    },
    methods: {
      updateAlert: function (alert) {
        this.alert = alert;
      },
      updateSession: function (session) {
        this.session = session;
      },
      removeSession: function () {
        this.session = null;
      }
    }
  }
</script>

<style>
  body {
    overflow-y: scroll;
  }
</style>
