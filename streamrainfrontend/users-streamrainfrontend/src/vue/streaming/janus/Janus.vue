<template>
</template>

<script>
  import { Janus } from '../../../assets/janus/janus.js';
  export default {
    props: [
      'config',
      'eventBus'
    ],
    created () {
      const i = this;

      this.callBackend(() => {
        this.janusInit();
      });      

      this.eventBus.$on('JanusReady?', function () {
        if (i.streaming.ready && i.textroom.ready) {
          i.eventBus.$emit('JanusReady', null);
        }
      });

      this.eventBus.$on('getJanusStreamsList', function () {
        i.janusUpdateStreamsList(function (result) {
          i.eventBus.$emit('setJanusStreamsList', result);
        });
      });

      this.eventBus.$on('janusSendPublicMessage', function (messageToSend) {
        const message = {
          textroom: 'message',
          transaction: Janus.randomString(12),
          room: i.streamId,
          text: messageToSend,
        };
        i.textroom.handle.data({
          text: JSON.stringify(message),
          error: function(reason) {
            Janus.error(JSON.stringify(reason));
          },
          success: function() {
          }
        });
      });

      this.eventBus.$on('janusSendPrivateMessage', function (obj) {
        let sended = [];
        let notSended = [];
        obj.receivers.forEach((to) => {
          const message = {
            textroom: 'message',
            transaction: Janus.randomString(12),
            room: i.streamId,
            to,
            text: obj.messageToSend
          };
          i.textroom.handle.data({
            text: JSON.stringify(message),
            error: function(reason) {
              Janus.error(JSON.stringify(reason));
              notSended.push(to);
            },
            success: function() {
              sended.push(to);
            }
          });
        });
        i.eventBus.$emit('janusSendPrivateMessageResult', {
          messageToSend: obj.messageToSend,
          sended,
          notSended
        });
      });

      this.eventBus.$on('leaveStreaming', function () {
        i.leaveStreaming();
      });

      this.eventBus.$on('getJanusLiveContent', function (streamId) {
        i.updateStreamId(streamId); // TODO: HACERLO SÓLO SI TODO SALE OK
        i.janusGetLiveContent(streamId);
      });
    },
    data () {
      return {
        server: null,
        janus: null,
        opaqueId: `STREAMRAIN:${Janus.randomString(12)}`,
        started: false,
        userToken: null,
        streamId: null,
        // Streaming
        streaming: {
          ready: true, // TODO: false
          handle: null
        },
        // TextRoom
        textroom: {
          ready: false,
          handle: null,
          myusername: null,
          myid: null,
          currentTextroom: null,
          participants: {},
          transactions: {}
        },
      }
    },
    methods: {
      callBackend: function (callback) {
        // TODO: definir bien las rutas y parámetros.
        const i = this;
        this.$http.get(`${this.config.backend}/backend-mock/${this.config.tenant.id}/janus-servers`)
        .then((result) => { 
          i.updateJanusInf(result.body);
          return callback (null);
        });
      },
      updateJanusInf: function (inf) {
        this.server = inf.janus;
        this.userToken = inf.token;
        // HARDCODEADO
        this.textroom.myusername = `USER_${Janus.randomString(3)}`;
      },
      leaveStreaming: function () {
        const request = {
          textroom: 'leave',
          transaction: Janus.randomString(12),
          room: this.streamId,
          username: this.textroom.myusername,
        };
        this.textroom.handle.data({
          text: JSON.stringify(request),
          error: function(reason) {
            Janus.error(reason);
          }
        });
      },
      updateStreamId: function (streamId) {
        this.streamId = parseInt(streamId);
      },
      updateStarted: function (started) {
        this.started = started;
      },
      updateTextroomTransactions: function (transactions) {
        this.textroom.transactions = transactions;
      },
      setTextroomHandle: function (handle) {
        this.textroom.handle = handle;
      },
      setTextroomReady: function () {
        this.textroom.ready = true;
        if (this.streaming.ready) {
          this.eventBus.$emit('JanusReady', null);
        }
      },
      setStreamingHandle: function (handle) {
        this.streaming.handle = handle;
      },
      setStreamingReady: function () {
        this.textroom.ready = true;
        if (this.streaming.ready) {
          this.eventBus.$emit('JanusReady', null);
        }
      },
      // JANUS
      janusInit: function () {
        const i = this;
        const eventBus = i.eventBus;
        Janus.init({
          debug: true,
          callback: function () {
            // Use a button to start the demo
            if (i.started) return;
            i.updateStarted(true);
            // Make sure the browser supports WebRTC
            if (!Janus.isWebrtcSupported()) {
              Janus.log('No WebRTC support...');
              return;
            }
            // Create session
            Janus.log(i.server);
            i.janus = new Janus({
              token: i.userToken,
              server: i.server,
              dependencies: Janus.useOldDependencies,
              success: function () {
                // Attach to streaming plugin
                i.janus.attach({
                  plugin: 'janus.plugin.streaming',
                  opaqueId: i.opaqueId,
                  success: function (pluginHandle) {
                    i.setStreamingHandle(pluginHandle);
                    Janus.log('Plugin attached! (' + pluginHandle.getPlugin() + ', id=' + pluginHandle.getId() + ')');
                  },
                  error: function (error) {
                    Janus.error('Error attaching plugin... ', error);
                  },
                  onmessage: function (msg, jsep) {
                    const result = msg['result'];
                    if (result !== null && result !== undefined) {
                      if (result['status'] !== undefined && result['status'] !== null) {
                        const status = result['status'];
                        if (status === 'starting')
                          eventBus.$emit('janusStartingStream', null);
                        else if (status === 'started')
                          eventBus.$emit('janusStartedStream', null);                          
                        else if (status === 'stopped')
                          eventBus.$emit('janusStoppedStream', null); 
                      }
                    } else if (msg['error'] !== undefined && msg['error'] !== null) {
                      Janus.error(JSON.stringify(msg['error']));
                      // TODO: Stop Stream.
                      return;
                    }
                    if (jsep !== undefined && jsep !== null) {
                      Janus.debug('Handling SDP as well...');
                      Janus.debug(jsep);
                      // Answer
                      i.streaming.handle.createAnswer({
                        jsep: jsep,
                        media: {
                          audioSend: false,
                          videoSend: false
                        }, // We want recvonly audio/video
                        success: function(jsep) {
                          Janus.debug('Got SDP!');
                          Janus.debug(jsep);
                          var body = {
                            request: 'start'
                          };
                          i.streaming.handle.send({
                            message: body,
                            jsep: jsep
                          });
                          // $('#watch').html("Stop").removeAttr('disabled').click(stopStream);
                        },
                        error: function(error) {
                          Janus.error('WebRTC error: ', error);
                        }
                      });
                    }
                  },
                  onremotestream: function (stream) {
                    Janus.error(JSON.stringify(stream));
                    eventBus.$emit('janusRemoteStream', {
                      stream,
                      browser: Janus.browser
                    });
                    // if($('#remotevideo').length > 0) {
                    //   // Been here already: let's see if anything changed
                    //   const videoTracks = stream.getVideoTracks();
                    //   if(videoTracks && videoTracks.length > 0 && !videoTracks[0].muted) {
                    //     $('#novideo').remove();
                    //     if($("#remotevideo").get(0).videoWidth)
                    //       $('#remotevideo').show();
                    //   }
                    //   return;
                    // }
                    // $('#stream').append('<video class="rounded centered hide" id="remotevideo" width=320 height=240 autoplay/>');
                    // // Show the stream and hide the spinner when we get a playing event
                    // $("#remotevideo").bind("playing", function () {
                    //   $('#waitingvideo').remove();
                    //   if(this.videoWidth)
                    //     $('#remotevideo').removeClass('hide').show();
                    //   if(spinner !== null && spinner !== undefined)
                    //     spinner.stop();
                    //   spinner = null;
                    //   var videoTracks = stream.getVideoTracks();
                    //   if(videoTracks === null || videoTracks === undefined || videoTracks.length === 0)
                    //     return;
                    //   var width = this.videoWidth;
                    //   var height = this.videoHeight;
                    //   $('#curres').removeClass('hide').text(width+'x'+height).show();
                    //   if(adapter.browserDetails.browser === "firefox") {
                    //     // Firefox Stable has a bug: width and height are not immediately available after a playing
                    //     setTimeout(function() {
                    //       var width = $("#remotevideo").get(0).videoWidth;
                    //       var height = $("#remotevideo").get(0).videoHeight;
                    //       $('#curres').removeClass('hide').text(width+'x'+height).show();
                    //     }, 2000);
                    //   }
                    // });
                    // var videoTracks = stream.getVideoTracks();
                    // if(videoTracks && videoTracks.length &&
                    //     (adapter.browserDetails.browser === "chrome" ||
                    //       adapter.browserDetails.browser === "firefox" ||
                    //       adapter.browserDetails.browser === "safari")) {
                    //   $('#curbitrate').removeClass('hide').show();
                    //   bitrateTimer = setInterval(function() {
                    //     // Display updated bitrate, if supported
                    //     var bitrate = streaming.getBitrate();
                    //     //~ Janus.debug("Current bitrate is " + streaming.getBitrate());
                    //     $('#curbitrate').text(bitrate);
                    //     // Check if the resolution changed too
                    //     var width = $("#remotevideo").get(0).videoWidth;
                    //     var height = $("#remotevideo").get(0).videoHeight;
                    //     if(width > 0 && height > 0)
                    //       $('#curres').removeClass('hide').text(width+'x'+height).show();
                    //   }, 1000);
                    // }
                    // Janus.attachMediaStream($('#remotevideo').get(0), stream);
                    // var videoTracks = stream.getVideoTracks();
                    // if(videoTracks === null || videoTracks === undefined || videoTracks.length === 0 || videoTracks[0].muted) {
                    //   // No remote video
                    //   $('#remotevideo').hide();
                    //   $('#stream').append(
                    //     '<div id="novideo" class="no-video-container">' +
                    //       '<i class="fa fa-video-camera fa-5 no-video-icon"></i>' +
                    //       '<span class="no-video-text">No remote video available</span>' +
                    //     '</div>');
                    // }
                  },
                  oncleanup: function () {
                  }
                });
                i.janus.attach({
                  plugin: 'janus.plugin.textroom',
                  opaqueId: i.opaqueId,
                  success: function (pluginHandle) {
                    i.setTextroomHandle(pluginHandle);
                    // updateTextroomHandle(pluginHandle);
                    Janus.log('Plugin attached! (' + pluginHandle.getPlugin() + ', id=' + pluginHandle.getId() + ')');
                    // Setup the DataChannel
                    let body = {
                      request: 'setup'
                    };
                    Janus.debug('Sending message (' + JSON.stringify(body) + ')');
                    pluginHandle.send({
                      message: body
                    });
                  },
                  error: function(error) {
                    Janus.error('Error attaching plugin...', error);
                  },
                  webrtcState: function(on) {
                    // Indica el estado de la conexión webRTC
                  },
                  onmessage: function(msg, jsep) {
                    Janus.debug('::: Got a message :::');
                    Janus.debug(msg);
                    if (msg['error'] !== undefined && msg['error'] !== null) {
                      Janus.error(msg['error']);
                    }
                    if (jsep !== undefined && jsep !== null) {
                      // Answer
                      i.textroom.handle.createAnswer({
                        jsep: jsep,
                        media: {
                          audio: false,
                          video: false,
                          data: true
                        },
                        success: function(jsep) {
                          Janus.debug('Got SDP!');
                          Janus.debug(jsep);
                          var body = {
                            request: 'ack'
                          };
                          i.textroom.handle.send({
                            message: body, 
                            jsep: jsep
                          });
                        },
                        error: function(error) {
                          Janus.error('WebRTC error:', error);
                        }
                      });
                    }
                  },
                  ondataopen: function(data) {
                    i.setTextroomReady();
                    Janus.log('The DataChannel is available!');
                  },
                  ondata: function(data) {
                    Janus.debug('We got data from the DataChannel! ' + data);
                    let json = JSON.parse(data);
                    let transaction = json['transaction'];
                    if (i.textroom.transactions[transaction]) {
                      // Someone was waiting for this
                      i.textroom.transactions[transaction](json);
                      delete i.textroom.transactions[transaction];
                      // updateTextroomTransactions(textroom.transactions);
                      return;
                    }
                    let what = json['textroom'];
                    if (what === 'message') {
                      // Incoming message: public or private?
                      let msg = json['text'];
                      msg = msg.replace(new RegExp('<', 'g'), '&lt');
                      msg = msg.replace(new RegExp('>', 'g'), '&gt');
                      let from = json['from'];
                      let dateString = getDateString(json['date']);
                      let whisper = json['whisper'];
                      if (whisper === true) {
                        // Private message
                        Janus.error(JSON.stringify(json));
                        eventBus.$emit('janusTextroomNewPrivateMessage', {
                          from,
                          text: msg
                        });
                      } else {
                        // Public message
                        Janus.error(JSON.stringify(json));
                        eventBus.$emit('janusTextroomNewPublicMessage', {
                          from,
                          text: msg
                        });
                      }
                    } else if (what === 'join') {
                      if (json['username'] === i.textroom.myusername ) {
                        eventBus.$emit('janusTextroomYouJoined', i.textroom.myusername);
                      } else {
                        eventBus.$emit('janusTextroomSomeoneJoined', json['username']);
                      }
                    } else if (what === 'leave') {
                      eventBus.$emit('janusTextroomSomeoneLeaved', json['username']);
                    } else if (what === 'kicked') {
                      // TODO
                    } else if (what === 'destroyed') {
                      // TODO
                    }
                  },
                  oncleanup: function() {
                    // TODO
                  }
                });
              },
              error: function(error) {
                Janus.error(error);
                eventBus.$emit('setJanusAlert', {
                  message: `Janus ${error}`
                });
              },
              destroyed: function() {
                window.location.reload();
              }
            });
          }
        });
      },
      janusGetLiveContent: function (streamId) {
        if (this.textroom.ready &&  this.streaming.ready) {
          this.janusJoinTextroom(streamId);
          this.janusWatchStream(streamId);
        }
      },
      janusUpdateStreamsList: function (callback) {
        const streaming = this.streaming;
        if (streaming.handle) {
          Janus.debug('Sending message (List Streams)');
          streaming.handle.send({
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
        } else {
          return callback(null);
        }
      },
      janusWatchStream: function (streamId) {
        const body = {
          request: 'watch',
          id: parseInt(streamId)
        };
	      this.streaming.handle.send({
          message: body
        });
      },
      janusJoinTextroom: function (textroomId) {
        const eventBus = this.eventBus;
        this.textroom.myid = Janus.randomString(12);
        const transaction = Janus.randomString(12);
        const register = {
          textroom: 'join',
          transaction: transaction,
          room: parseInt(textroomId),
          username: this.textroom.myusername,
          display: this.textroom.myusername
        };
        this.textroom.transactions[transaction] = function (response) {
          if (response['textroom'] === 'error') {
            Janus.error(response['error']);
            return;
          }
          const participants = [];
          response.participants.forEach((p) => {
            participants.push(p.username);
          });
          eventBus.$emit('janusTextroomParticipants', participants);
        };
        this.textroom.handle.data({
          text: JSON.stringify(register),
          error: function(reason) {
            Janus.error(reason);
          }
        });
      }
    }
  }

  function getDateString(jsonDate) {
    var when = new Date();
    if(jsonDate) {
      when = new Date(Date.parse(jsonDate));
    }
    var dateString =
        ("0" + when.getUTCHours()).slice(-2) + ":" +
        ("0" + when.getUTCMinutes()).slice(-2) + ":" +
        ("0" + when.getUTCSeconds()).slice(-2);
    return dateString;
  }
</script>
