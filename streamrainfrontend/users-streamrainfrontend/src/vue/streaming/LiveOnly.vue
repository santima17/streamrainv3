<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left">
        <div class="row content" v-if="janusAlert">
          <div class="alert alert-dismissible alert-warning">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <p><strong>Oops!</strong></p>
            <p>{{ janusAlert.message }}</p>
          </div>
        </div>
        <h1>Live Stream {{ stream.description }}</h1>
        <h2>{{ stream.description }}</h2>
        <div class="row" id="room">
          <div class="col-sm-7">
            <div v-if="!stream.ready && !janusAlert">
              <p class="text-danger">Connecting...</p>
            </div>
            <video ref="video" width="100%" v-on:playing="playingVideo" autoplay />
          </div>
          <div class="col-sm-5">
            <div v-if="!chatroom.ready && !janusAlert">
              <p class="text-danger">Connecting...</p>
            </div>
            <div class="panel panel-info" v-if="chatroom.ready">
              <div class="panel-heading">
                <div class="btn-group pull-right">
                  <button class="btn btn-info dropdown-toggle btn-xs" data-toggle="dropdown" aria-expanded="false">
                    <span class="glyphicon glyphicon-menu-down"></span>
                  </button>
                  <ul class="dropdown-menu">
                    <li v-if="!chatroom.privateMessageMode">
                      <a v-on:click="activePrivateMessageMode" role="button">Private message... <i class="glyphicon glyphicon-send"></i></a>
                    </li>
                    <li v-if="chatroom.privateMessageMode">
                      <a v-on:click="chatroom.privateMessageMode = false" role="button">Back to the chat room</a>
                    </li>
                  </ul>
                </div>
                <h3 class="panel-title">Chatroom</span></h3>
              </div>
              <div v-if="!chatroom.privateMessageMode" class="panel-body chat-body relative" id="chatroom">
                <div v-for="(message, index) in chatroom.messages" :key="index">
                  <p :class="message.p_class">{{ message.preamble }}:</p><p class="small">{{ message.text }}</p>
                  <hr>
                </div>
              </div>
              <div v-if="chatroom.privateMessageMode" class="panel-body chat-body relative" id="chatroom">
                <p class="text-info">Send to:</p>
                <div v-for="(participant, index) in chatroom.participants" :key="index">
                  <div class="checkbox">
                    <label><input type="checkbox" v-on:click="clickCheck" :value="participant">{{ participant }}</label>
                  </div>
                </div>
              </div>
              <div class="panel-footer">
                <div class="input-group margin-bottom-sm">
                  <input v-model="messageToSend" class="form-control" type="text" placeholder="Write a chatroom message" autocomplete="off" id="datasend"></input>
                  <span v-on:click="sendMessage" class="input-group-addon btn btn-info">Send</span>
                </div>
              </div>
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
      'eventBus',
      'janusAlert'
    ],
    data () {
      return {
        stream: {
          ready: false
        },
        chatroom: {
          ready: false,
          myId: null,
          messages: [],
          participants: [],
          participantsSelected: [],
          privateMessageMode: false
        },
        messageToSend: null,
        currentStream: null
      }
    },
    created () {
      const i = this;
      const streamId = this.$route.params.streamId;

      this.eventBus.$once('JanusReady', function (result) {
        i.eventBus.$emit('getJanusLiveContent', streamId);
      });

      this.eventBus.$on('janusStartingStream', function () {
        console.error('janusStartingStream');
      });

      this.eventBus.$on('janusStartedStream', function () {
        console.error('janusStartedStream');
        i.stream.ready = true;
      });

      this.eventBus.$on('janusRemoteStream', function (obj) {
        console.error('janusRemoteStream');
        const stream = obj.stream;
        const browser = obj.browser;
        this.currentStream = stream;
        if (i.$refs.video.length > 0) {
          // Been here already: let's see if anything changed
          const videoTracks = stream.getVideoTracks();
          if (videoTracks && videoTracks.length > 0 && !videoTracks[0].muted) {
            // $('#novideo').remove();
            if (i.$refs.video.videoWidth) i.$refs.video.show();
          }
          return;
        }

        // $('#stream').append('<video class="rounded centered hide" id="remotevideo" width=320 height=240 autoplay/>');
        // Show the stream and hide the spinner when we get a playing event
        // i.$refs.video.bind('playing', function () {
        //   // $('#waitingvideo').remove();
        //   // if (this.videoWidth) $('#remotevideo').removeClass('hide').show();
        //   // if (spinner !== null && spinner !== undefined) spinner.stop();
        //   // spinner = null;
        //   const videoTracks = stream.getVideoTracks();
        //   if (videoTracks === null || videoTracks === undefined || videoTracks.length === 0) return;
        //   const width = this.videoWidth;
        //   const height = this.videoHeight;
        //   // $('#curres').removeClass('hide').text(width+'x'+height).show();
        //   if (browser.name === 'firefox') {
        //     // Firefox Stable has a bug: width and height are not immediately available after a playing
        //     // setTimeout(function() {
        //     //   const width = $("#remotevideo").get(0).videoWidth;
        //     //   const height = $("#remotevideo").get(0).videoHeight;
        //     //   $('#curres').removeClass('hide').text(width+'x'+height).show();
        //     // }, 2000);
        //   }
        // });
        const videoTracks = stream.getVideoTracks();
        if (videoTracks && videoTracks.length &&
          (
            browser.name === "chrome" ||
            browser.name === "firefox" ||
            browser.name === "safari"
          )
        ){
          // $('#curbitrate').removeClass('hide').show();
          // bitrateTimer = setInterval(function() {
          //   // Display updated bitrate, if supported
          //   var bitrate = streaming.getBitrate();
          //   //~ Janus.debug("Current bitrate is " + streaming.getBitrate());
          //   $('#curbitrate').text(bitrate);
          //   // Check if the resolution changed too
          //   var width = $("#remotevideo").get(0).videoWidth;
          //   var height = $("#remotevideo").get(0).videoHeight;
          //   if(width > 0 && height > 0)
          //     $('#curres').removeClass('hide').text(width+'x'+height).show();
          // }, 1000);
        }

        //Janus.attachMediaStream($('#remotevideo').get(0), stream);
        // A ver si funciona de esta forma lo que está en la línea anterior
        if(browser.name === 'chrome') {
          var chromever = browser.version;
          if(chromever >= 43) {
            i.$refs.video.srcObject = stream;
          } else if(typeof element.src !== 'undefined') {
            i.$refs.video.src = URL.createObjectURL(stream);
          } else {
            console.error("Error attaching stream to element");
          }
        } else {
          i.$refs.video.srcObject = stream;
        }

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

      });

      this.eventBus.$on('janusStoppedStream', function () {
        console.error('janusStoppedStream');
      });

      this.eventBus.$on('janusTextroomNewPublicMessage', function (message) {
        i.chatroom.messages.push({
          p_class: 'text-primary',
          preamble: `[${message.from}]`,
          text: message.text
        });
      });

      this.eventBus.$on('janusTextroomNewPrivateMessage', function (message) {
        i.chatroom.messages.push({
          p_class: 'text-danger',
          preamble: `[Private Message] from [${message.from}]`,
          text: message.text
        });
      });

      this.eventBus.$on('janusSendPrivateMessageResult', function (result) {
        if (result.notSended.length > 0) {
          i.chatroom.messages.push({
            p_class: 'text-danger',
            preamble: `[Private Message] error when trying to send to ${result.notSended}`,
            text: result.messageToSend
          });
        }
        if (result.sended.length > 0) {
          i.chatroom.messages.push({
            p_class: 'text-danger',
            preamble: `[Private Message] sended to ${result.sended}`,
            text: result.messageToSend
          });
        }
      });

      this.eventBus.$once('janusTextroomParticipants', function (participants) {
        i.chatroom.participants = participants;
        i.chatroom.participantsSelected = [];
      });

      this.eventBus.$once('janusTextroomYouJoined', function (myId) {
        i.chatroom.ready = true;
        i.myId = myId;
        i.chatroom.messages.push({
          p_class: 'text-success',
          preamble: '[Welcome]',
          text: `You just joined the chatroom as ${myId}`
        });
      });

      this.eventBus.$on('janusTextroomSomeoneJoined', function (Id) {
        i.chatroom.messages.push({
          p_class: 'text-info',
          preamble: '[Info]',
          text: `${Id} just joined the chatroom`
        });
        i.chatroom.participants.push(Id);
      });

      this.eventBus.$on('janusTextroomSomeoneLeaved', function (Id) {
        i.chatroom.messages.push({
          p_class: 'text-info',
          preamble: '[Info]',
          text: `${Id} just left the chatroom`
        });
        let index = i.chatroom.participants.indexOf(Id);
        if (index > -1) {
          i.chatroom.participants.splice(index, 1);
        }
        index = i.chatroom.participantsSelected.indexOf(Id);
        if (index > -1) {
          i.chatroom.participantsSelected.splice(index, 1);
        }
      });

      this.eventBus.$emit('JanusReady?', null);
    },
    beforeDestroy () {
      this.eventBus.$emit('leaveStreaming', null);
    },
    methods: {
      playingVideo: function () {
        // $('#waitingvideo').remove();
        // if (this.videoWidth) $('#remotevideo').removeClass('hide').show();
        // if (spinner !== null && spinner !== undefined) spinner.stop();
        // spinner = null;
        const videoTracks = this.currentStream.getVideoTracks();
        if (videoTracks === null || videoTracks === undefined || videoTracks.length === 0) return;
        // const width = this.videoWidth;
        // const height = this.videoHeight;
        // $('#curres').removeClass('hide').text(width+'x'+height).show();
        if (browser.name === 'firefox') {
          // Firefox Stable has a bug: width and height are not immediately available after a playing
          // setTimeout(function() {
          //   const width = $("#remotevideo").get(0).videoWidth;
          //   const height = $("#remotevideo").get(0).videoHeight;
          //   $('#curres').removeClass('hide').text(width+'x'+height).show();
          // }, 2000);
        }
      },
      activePrivateMessageMode: function () {
        this.chatroom.participantsSelected = [];
        this.chatroom.privateMessageMode = true;
      },
      clickCheck: function (event) {
        const username = event.target.value;
        const checked = event.target.checked;
        if (checked) {
          let index = this.chatroom.participantsSelected.indexOf(username);
          if (index < 0) {
            this.chatroom.participantsSelected.push(username);
          }
        } else {
          let index = this.chatroom.participantsSelected.indexOf(username);
          if (index > -1) {
            this.chatroom.participantsSelected.splice(index, 1);
          }
        }
      },
      sendMessage: function () {
        if (this.chatroom.privateMessageMode) {
          if (this.chatroom.participantsSelected.length > 0) {
            this.eventBus.$emit('janusSendPrivateMessage', {
              messageToSend: this.messageToSend,
              receivers: this.chatroom.participantsSelected
            });
            this.messageToSend = '';
            this.chatroom.privateMessageMode = false;
          }  
        } else {
          this.eventBus.$emit('janusSendPublicMessage', this.messageToSend);
          this.messageToSend = ''
        }
      }
    }
  }
</script>

<style>
  .chat-body{
    height:300px;
    overflow-y:scroll;
    width:100%;
  }
</style>
