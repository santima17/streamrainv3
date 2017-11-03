<template>
</template>

<script>
  import { Janus } from '../../../assets/janus.js';
  export default {
    props: [
      'backend',
      'eventBus'
    ],
    created () {
      this.callBackend(() => {
        console.log('Backend called!');
        this.janusInit();
      });
      const eventBus = this.eventBus;
      const janusUpdateStreamsList = this.janusUpdateStreamsList;
      const started = this.started;
      eventBus.$on('getJanusStreamsList', function () {
        if (started) {
          janusUpdateStreamsList(function (result) {
            eventBus.$emit('setJanusStreamsList', result);
          });
        }
      });
    },
    data () {
      return {
        server: null,
        janus: null,
        streamingHandle: null,
        textroomHandle: null,
        opaqueId: `STREAMRAIN:${Janus.randomString(12)}`,
        started: false,
        userToken: null,
      }
    },
    methods: {
      callBackend: function (callback) {
        // HARDCODEADO
        // HARDCODEADO
        // HARDCODEADO
        this.server = 'http://localhost:8088/janus',
        this.userToken = 'userToken1'
        console.log(this.opaqueId);
        return callback(null);
        // HARDCODEADO
        // HARDCODEADO
        // HARDCODEADO
      },
      updateStarted: function (started) {
        this.started = started;
      },
      updateStreamingHandle: function (handle) {
        this.streamingHandle = handle;
        const eventBus = this.eventBus;
        eventBus.$emit('setJanusReady', null);
      },
      // JANUS
      janusInit: function () {
        let eventBus = this.eventBus;
        let started = this.started;
        let server = this.server;
        let userToken = this.userToken;
        let janus = this.janus;
        let opaqueId = this.opaqueId;
        let updateStarted = this.updateStarted;
        let updateStreamingHandle = this.updateStreamingHandle;
        let janusUpdateStreamsList = this.janusUpdateStreamsList;
        Janus.init({
          debug: "all",
          callback: function() {
            // Use a button to start the demo
            if(started)
              return;
            started = true;
            updateStarted(started);
            // Make sure the browser supports WebRTC
            if(!Janus.isWebrtcSupported()) {
              Janus.log("No WebRTC support... ");
              return;
            }
            // Create session
            Janus.log(server);
            janus = new Janus({
              token: userToken,
              server: server,
              dependencies: Janus.useOldDependencies,
              success: function() {
                // Attach to streaming plugin
                janus.attach({
                  plugin: "janus.plugin.streaming",
                  opaqueId: this.opaqueId,
                  success: function(pluginHandle) {
                    updateStreamingHandle(pluginHandle);
                    Janus.log("Plugin attached! (" + pluginHandle.getPlugin() + ", id=" + pluginHandle.getId() + ")");
                    janusUpdateStreamsList(function (result) {
                      eventBus.$emit('setJanusStreamsList', result);
                    });
                  },
                  error: function(error) {
                    Janus.error("Error attaching plugin... ", error);
                  },
                  onmessage: function(msg, jsep) {
                  },
                  onremotestream: function(stream) {
                  },
                  oncleanup: function() {
                  }
                });
              },
              error: function(error) {
                Janus.error(error);
              },
              destroyed: function() {
                window.location.reload();
              }
            });
          }
        });
      },
      janusUpdateStreamsList: function (callback) {
        let streamingHandle = this.streamingHandle;
        Janus.debug('Sending message (List Streams)');
        streamingHandle.send({
          message: {
            request: 'list'
          },
          success: (result) => {
            if (result === null || result === undefined) {
              Janus.error('Got no response to our query for available streams');
              return callback(null);
            }
            if (result['list'] !== undefined && result['list'] !== null) {
              Janus.log('Got a list of available streams');
              return callback(result['list']);
            }
          }
        });
      },
    }
  }
</script>
