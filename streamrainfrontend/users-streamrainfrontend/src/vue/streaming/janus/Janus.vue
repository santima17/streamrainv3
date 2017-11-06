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
            },
            success: function() {
            }
          });
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
                    // i.janusUpdateStreamsList(function (result) {
                    //   eventBus.$emit('setJanusStreamsList', result);
                    // });
                  },
                  error: function (error) {
                    Janus.error('Error attaching plugin... ', error);
                  },
                  onmessage: function (msg, jsep) {
                  },
                  onremotestream: function (stream) {
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
                    console.error('Error attaching plugin...', error);
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
                        // ACÁ PUBLICAMOS LOS MENSAJES
                        Janus.error(JSON.stringify(json));
                        eventBus.$emit('janusTextroomNewPrivateMessage', {
                          from,
                          text: msg
                        });
                        // $('#chatroom').append('<p style="color: purple;">[' + dateString + '] <b>[whisper from ' + participants[from] + ']</b> ' + msg);
                        // $('#chatroom').get(0).scrollTop = $('#chatroom').get(0).scrollHeight;
                      } else {
                        // Public message
                        // ACÁ PUBLICAMOS LOS MENSAJES
                        Janus.error(JSON.stringify(json));
                        eventBus.$emit('janusTextroomNewPublicMessage', {
                          from,
                          text: msg
                        });
                        // $('#chatroom').append('<p>[' + dateString + '] <b>' + participants[from] + ':</b> ' + msg);
                        // $('#chatroom').get(0).scrollTop = $('#chatroom').get(0).scrollHeight;
                      }
                    } else if (what === 'join') {
                      if (json['username'] === i.textroom.myusername ) {
                        eventBus.$emit('janusTextroomYouJoined', i.textroom.myusername);
                      } else {
                        eventBus.$emit('janusTextroomSomeoneJoined', json['username']);
                      }
                      // Somebody joined
                      // let username = json["username"];
                      // let display = json["display"];
                      // participants[username] = display ? display : username;
                      // if (username !== myid && $('#rp' + username).length === 0) {
                      //   // Add to the participants list
                      //   $('#list').append('<li id="rp' + username + '" class="list-group-item">' + participants[username] + '</li>');
                      //   $('#rp' + username).css('cursor', 'pointer').click(function() {
                      //     var username = $(this).attr('id').split("rp")[1];
                      //     sendPrivateMsg(username);
                      //   });
                      // }
                      // $('#chatroom').append('<p style="color: green;">[' + getDateString() + '] <i>' + participants[username] + ' joined</i></p>');
                      // $('#chatroom').get(0).scrollTop = $('#chatroom').get(0).scrollHeight;
                    } else if (what === 'leave') {
                      eventBus.$emit('janusTextroomSomeoneLeaved', json['username']);
                      // Somebody left
                      // var username = json["username"];
                      // var when = new Date();
                      // $('#rp' + username).remove();
                      // $('#chatroom').append('<p style="color: green;">[' + getDateString() + '] <i>' + participants[username] + ' left</i></p>');
                      // $('#chatroom').get(0).scrollTop = $('#chatroom').get(0).scrollHeight;
                      // delete participants[username];
                    } else if (what === 'kicked') {
                      // TODO
                      // Somebody was kicked
                      // var username = json["username"];
                      // var when = new Date();
                      // $('#rp' + username).remove();
                      // $('#chatroom').append('<p style="color: green;">[' + getDateString() + '] <i>' + participants[username] + ' was kicked from the room</i></p>');
                      // $('#chatroom').get(0).scrollTop = $('#chatroom').get(0).scrollHeight;
                      // delete participants[username];
                      // if (username === myid) {
                      //   bootbox.alert("You have been kicked from the room", function() {
                      //     window.location.reload();
                      //   });
                      // }
                    } else if (what === 'destroyed') {
                      // if (json["room"] !== myroom)
                      //   return;
                      // // Room was destroyed, goodbye!
                      // Janus.warn("The room has been destroyed!");
                      // bootbox.alert("The room has been destroyed", function() {
                      //   window.location.reload();
                      // });
                    }
                  },
                  oncleanup: function() {
                    // Janus.log(" ::: Got a cleanup notification :::");
                    // $('#datasend').attr('disabled', true);
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
            // Something went wrong
            // if(response["error_code"] === 417) {
            //   // This is a "no such room" error: give a more meaningful description
            //   bootbox.alert(
            //     "<p>Apparently room <code>" + myroom + "</code> (the one this demo uses as a test room) " +
            //     "does not exist...</p><p>Do you have an updated <code>janus.plugin.textroom.cfg</code> " +
            //     "configuration file? If not, make sure you copy the details of room <code>" + myroom + "</code> " +
            //     "from that sample in your current configuration file, then restart Janus and try again."
            //   );
            // } else {
            //   bootbox.alert(response["error"]);
            // }
            // $('#username').removeAttr('disabled').val("");
            // $('#register').removeAttr('disabled').click(registerUsername);
            Janus.error(response['error']);
            return;
          }
          // We're in
          // $('#roomjoin').hide();
          // $('#room').removeClass('hide').show();
          // $('#participant').removeClass('hide').html(this.myusername).show();
          // $('#chatroom').css('height', ($(window).height()-420)+"px");
          // $('#datasend').removeAttr('disabled');
          // Any participants already in?
          const participants = [];
          response.participants.forEach((p) => {
            participants.push(p.username);
          });
          eventBus.$emit('janusTextroomParticipants', participants);
          // if(response.participants && response.participants.length > 0) {
          //   for(var i in response.participants) {
          //     var p = response.participants[i];
          //     participants[p.username] = p.display ? p.display : p.username;
          //     if(p.username !== this.myid && $('#rp' + p.username).length === 0) {
          //       // Add to the participants list
          //       $('#list').append('<li id="rp' + p.username + '" class="list-group-item">' + participants[p.username] + '</li>');
          //       $('#rp' + p.username).css('cursor', 'pointer').click(function() {
          //         var username = $(this).attr('id').split("rp")[1];
          //         sendPrivateMsg(username);
          //       });
          //     }
          //     $('#chatroom').append('<p style="color: green;">[' + getDateString() + '] <i>' + participants[p.username] + ' joined</i></p>');
          //     $('#chatroom').get(0).scrollTop = $('#chatroom').get(0).scrollHeight;
          //   }
          // }
        };
        this.textroom.handle.data({
          text: JSON.stringify(register),
          error: function(reason) {
            Janus.error(reason);
            // $('#username').removeAttr('disabled').val("");
            // $('#register').removeAttr('disabled').click(registerUsername);
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
