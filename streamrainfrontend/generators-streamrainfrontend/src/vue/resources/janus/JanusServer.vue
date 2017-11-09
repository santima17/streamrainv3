<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left"> 
        <h1>Janus Server {{ id }}</h1>
        <ul class="nav nav-tabs">
          <!-- <li><a href="#streamrain-info" data-toggle="tab" aria-expanded="true">Streamrain Info</a></li> -->
          <li class="active"><a href="#janus-info" data-toggle="tab" aria-expanded="true">Janus Info</a></li>
          <li><a href="#auth-tokens" data-toggle="tab" aria-expanded="true">Auth Tokens</a></li>
        </ul>
        <div class="tab-content">
          <!-- <div class="tab-pane fade" id="streamrain-info">
            <br>
            <b>Server Name</b>
            <p>{{ selectedServer.name }}</p>
            <br>
            <b>Last Update Request</b>
            <p>{{ selectedServer.date_last_update_request }}</p>
            <br>
            <b>Janus URL</b>
            <p>{{ selectedServer.janus_url }}</p>
          </div> -->
          <div class="tab-pane fade active in" id="janus-info">
            <h2 class="text-left text-info">Server Info</h2>
            <br>
            <table class="table table-striped table-hover">
              <tbody v-if="janusInfo !== null">
                <tr>
                  <td><b>Name</b></td>
                  <td>{{ janusInfo.name }}</td>
                </tr>
                <tr>
                  <td><b>Version</b></td>
                  <td>{{ janusInfo.version }}</td>
                </tr>
                <tr>
                  <td><b>Commit hash</b></td>
                  <td>{{ janusInfo['commit-hash'] }}</td>
                </tr>
                <tr>
                  <td><b>Log to stdout</b></td>
                  <td>{{ janusInfo['log-to-stdout'] }}</td>
                </tr>
                <tr>
                  <td><b>Log to file </b></td>
                  <td>{{ janusInfo['log-to-file'] }}</td>
                </tr>
                <tr>
                  <td><b>Data chennels</b></td>
                  <td>{{ janusInfo['data_channels'] }}</td>
                </tr>
                <tr>
                  <td><b>Session timeout</b></td>
                  <td>{{ janusInfo['session-timeout'] }}</td>
                </tr>
                <tr>
                  <td><b>Local IP</b></td>
                  <td>{{ janusInfo['local-ip'] }}</td>
                </tr>
                <tr>
                  <td><b>Public IP</b></td>
                  <td>{{ janusInfo['public-ip'] }}</td>
                </tr>
                <tr>
                  <td><b>STUN server</b></td>
                  <td>{{ janusInfo['stun-server'] }}</td>
                </tr>
              </tbody>
            </table>
            <h2 class="text-left text-info">Plugins</h2>
            <br>
            <div v-if="janusInfo !== null && janusInfo.plugins !== null">
              <div v-for="(plugin, index) in janusInfo.plugins" :key="index">
                <p><b>{{ janusInfo.plugins[index].name }}</b>, version {{ janusInfo.plugins[index].version }}</p>
                <br>
              </div>
            </div>
            <h2 class="text-left text-info">Transports</h2>
            <br>
            <div v-if="janusInfo !== null && janusInfo.transports !== null">
              <div v-for="(plugin, index) in janusInfo.transports" :key="index">
                <p><b>{{ janusInfo.transports[index].name }}</b>, version {{ janusInfo.transports[index].version }}</p>
                <br>
              </div>
            </div>
          </div>
          <div class="tab-pane fade" id="auth-tokens">
            <h2 class="text-left text-info">Auth Tokens</h2>
            <div class="text-right">
              <button v-on:click="eventBus.$emit('getJanusAuthTokens', null)" class="btn btn-primary">
                Refresh
              </button>
            </div>
            <br>
            <div v-if="authTokens !== null">
              <div v-for="(authToken, index) in authTokens" :key="index">
                <table class="table table-striped table-hover">
                  <tbody v-if="janusInfo !== null">
                    <tr>
                      <td><b>Token</b></td>
                      <td><div class="text-info">{{ authToken.token }}</div></td>
                    </tr>
                    <tr>
                      <td><b>Allowed Plugins</b></td>
                      <td>
                        <div v-for="(plugin, index) in authToken.allowed_plugins" :key="index">
                        {{ plugin }}
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
        <hr>
      </div>
      <janus-connector :eventBus="eventBus" :selectedServer="selectedServer"></janus-connector>
      <div class="col-sm-2 sidenav">
      </div>
    </div>
  </div>
</template>

<script>
  import JanusConnector from './JanusConnector.vue';
  export default {
    props: [
      'config',
      'eventBus',
    ],
    components: {
      'janus-connector': JanusConnector
    },
    data () {
      return {
        id: this.$route.params.id,
        selectedServer: null,
        janusInfo: null,
        authTokens: null
      }
    },
    created () {
      // Acá iría algo así:
      // this.$http.get(`${this.config.backend}/janus_servers/${this.id}`)
      // .then((result) => { 
      //   console.error(JSON.stringify(result));
      // });
      // HARDCODEADO
      this.selectedServer = {
        id: 1,
        name: 'Streamrain Live Only Janus Server 1',
        is_enable: true,
        date_last_update_request: '2017-11-09',
        janus_url: 'http://localhost:8088/janus',
        admin_url: 'http://localhost:7088/admin',
        admin_secret: 'strn123'
      };
      // HARDCODEADO
      this.eventBus.$on('setJanusServerInfo', (result) => {
        this.janusInfo = result;
      }, this);
      this.eventBus.$on('setJanusAuthTokens', (result) => {
        this.authTokens = result;
      }, this);
    },
    mounted () {
      this.eventBus.$emit('getJanusServerInfo', null);
      this.eventBus.$emit('getJanusAuthTokens', null);
    }
  }
</script>
