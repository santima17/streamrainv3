<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left"> 
        <h1 class="text-left">Janus Resources</h1>
        <ul class="nav nav-tabs">
          <li class="active"><a href="#servers" data-toggle="tab" aria-expanded="true">Servers</a></li>
          <li><a href="#creation-tokens" data-toggle="tab" aria-expanded="true">Creation Tokens</a></li>
        </ul>
        <div class="tab-content">
          <div class="tab-pane fade active in" id="servers">
            <h2 class="text-left text-info">Servers</h2>
            <br>
            <div v-if="janusServers != []">
              <div v-for="(server, index) in janusServers" :key="index">
                <table class="table table-striped table-hover ">
                  <tbody>
                    <tr>
                      <td><b>ID</b></td>
                      <td>{{ server.id }}</td>
                    </tr>
                    <tr>
                      <td><b>Enable</b></td>
                      <td>
                        <span v-if="!server.is_enable" class="text-danger">
                          {{ (server.is_enable).toString().toUpperCase() }}
                        </span>
                        <span v-if="server.is_enable" class="text-success">
                          {{ (server.is_enable).toString().toUpperCase() }}
                        </span>
                      </td>
                    </tr>
                    <tr>
                      <td><b>Name</b></td>
                      <td>{{ server.name }}</td>
                    </tr>
                    <tr>
                      <td><b>Last Update Request</b></td>
                      <td>{{ server.date_last_update_request }}</td>
                    </tr>
                    <tr>
                      <td><b>Janus URL</b></td>
                      <td>{{ server.janus_url }}</td>
                    </tr>
                    <tr class="text-right">
                      <td></td>
                      <td v-if="server.is_enable">
                        <router-link :to="`/resources/janus/${server.id}`">
                          <div><b>Show more...</b></div>
                        </router-link>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
          <div class="tab-pane fade" id="creation-tokens">
            <h2 class="text-left text-info">Creation Tokens</h2>
            <div class="text-right">
              <button v-on:click="eventBus.$emit('getJanusAuthTokens', null)" class="btn btn-primary">
                New Token
              </button>
            </div>
            <br>
            <div v-for="(token, index) in janusCreationTokens" :key="index">
              <table class="table table-striped table-hover ">
                <tbody>
                  <tr>
                    <td><b>ID</b></td>
                    <td>{{ token.id }}</td>
                  </tr>
                  <tr>
                    <td><b>Token</b></td>
                    <td>{{ token.janus_token }}</td>
                  </tr>
                  <tr>
                    <td><b>Backend</b></td>
                    <td>{{ token.streamrain_rest_backend }}</td>
                  </tr>
                  <tr>
                    <td><b>Creation</b></td>
                    <td>{{ token.date_creation }}</td>
                  </tr>
                  <tr>
                    <td><b>Expiration</b></td>
                    <td>{{ token.date_expiration }}</td>
                  </tr>
                  <tr v-if="token.date_usage != null">
                    <td><b>Usage</b></td>
                    <td>{{ token.date_usage }}</td>
                  </tr>
                </tbody>
              </table>
              <br>
            </div>
          </div>
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
    data () {
      return {
        janusServers: [],
        janusCreationTokens: [],
      }
    },
    created () {
      this.getJanusServers();
    },
    methods: {
      getJanusServers: function () {
        // Obtenemos los servidores Janus del Generator
        this.$http.get(`${this.config.backend}/generator/janus_servers`)
        .then((result) => { 
          this.janusServers.push(result.body);
        }, this);
        // Obtenemos los Janus Creation Token del Generator
        this.$http.get(`${this.config.backend}/generator/janus_creation_tokens`)
        .then((result) => { 
          this.janusCreationTokens = result.body;
        }, this);
      },
      getJanusServerInfo: function () {
        this.eventBus.$emit('getJanusServerInfo', null);
      }
    }
  }
</script>
