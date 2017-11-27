<template>
  <div class="container-fluid text-center">
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left">
        <h1>
          {{ stream.name }} <i v-if="titleSpinner && !alert.show" class="fa fa-spinner fa-spin" style="font-size"></i>
        </h1>
        <div>
          <div class="col-md-6 text-right">
            <streamrain-sharebutton v-if="stream.id"  ref="sharebutton"
              :session="session"
              :contentId="$route.params.streamId"
              :eventBus="eventBus"
              :config="config"
            >
            </streamrain-sharebutton>            
          </div>
          <div class="col-md-6 text-left">
            <streamrain-favbutton v-if="myFav !== null" ref="favbutton"
              :session="session"
              :contentId="$route.params.streamId"
              :myFav="myFav"
              :eventBus="eventBus"
              :config="config"
            >
            </streamrain-favbutton>            
          </div>
        </div>
        <br>
        <!-- alert -->
        <div class="row">
          <div class="col-sm-12" v-if="alert.show">
            <div v-if="alert.show" class="alert alert-dismissible alert-warning">
              <button class="close" v-on:click="eventBus.$emit('setAlert', {show: false, message: null})">&times;</button>
              <p>Oops!</p>
              <p>{{ alert.message }}</p>
            </div>
          </div>
        </div>
        <!-- alert -->
        <div class="row" id="room">
          <div class="col-sm-7">
            <video v-bind:hidden="!stream.ready" ref="video" width="100%" v-on:playing="playingVideo" controls />
          </div>
          <div class="col-sm-5">
            <div class="panel panel-info" v-if="chatroom.ready && !session.isBanned">
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
            <div v-if="session.isBanned">
              <p class="text-right text-danger">[Chat] You are banned...</p> 
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 text-left">
            <div>
              <span v-if="stream.type" class="label label-info">{{ stream.type }}</span>
              <span v-if="stream.isPayPerView" class="label label-danger">Pay Per View</span>
            </div>
            <div v-if="stream.dateStart">
              <br>
              {{ getDate(stream.dateStart) }}
            </div>
          </div>
          <div class="col-sm-6 text-right">
            <streamrain-fivestarsrating v-if="stream.id" ref="fivestarsrating"
              :session="session"
              :stream="stream"
              :myRank="myRank"
              :postRank="`${config.backend}/user/content/rank/${$route.params.streamId}/${session.nickname}`"
              :getRank="`${config.backend}/user/content/rank/${$route.params.streamId}`"
              :eventBus="eventBus"
              :config="config"
            >
            </streamrain-fivestarsrating>
          </div>
        </div>
        <div v-if="stream.description">
          <br>
          <b>Description</b>
          <p>{{ stream.description }}</p>
        </div>
        <div class="panel-group">
          <div class="panel panel-default">
            <div class="panel-heading text-center">
              <a data-toggle="collapse" href="#collapse1">More</a>
            </div>
            <div id="collapse1" class="panel-collapse collapse">
              <div class="panel-body">
                <div v-if="stream.directors">
                  <b>Directors</b>
                  <ul class="list-inline">
                    <li v-for="(director, index) in stream.directors" :key="index">
                      {{ director.lastName }}, {{ director.firstName }}
                    </li>
                  </ul>
                </div>
                <div v-if="stream.actors && stream.actors.length > 0">
                  <b>Actors</b>
                  <ul class="list-inline">
                    <li v-for="(actor, index) in stream.actors" :key="index">
                      {{ actor.lastName }}, {{ actor.firstName }}
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
        <streamrain-errorshelper ref="errorshelper"
          :eventBus="eventBus"
          :config="config"
        >
        </streamrain-errorshelper>
        <hr>
      </div>
      <div class="col-sm-2 sidenav">
      </div>
    </div>
  </div>
</template>

<script>
  import FiveStarsRating from '../utils/FiveStarsRating.vue';
  import FavButton from '../utils/FavButton.vue';
  import ShareButton from '../utils/ShareButton.vue';
  import ErrorsHelper from '../utils/ErrorsHelper.vue';
  export default {
    props: [
      'config',
      'eventBus',
      'session',
      'alert'
    ],
    components: {
      'streamrain-fivestarsrating': FiveStarsRating,
      'streamrain-favbutton': FavButton,
      'streamrain-errorshelper': ErrorsHelper,
      'streamrain-sharebutton': ShareButton
    },
    data () {
      return {
        myRank: 0,
        myFav: null,
        sendingRank: false,
        //
        currentStream: null,
        pin: null,
        stream: {
          ready: false,
          name: 'Live Stream'
        },
        //
        chatroom: {
          ready: false,
          myId: this.session.nickname,
          messages: [],
          participants: [],
          participantsSelected: [],
          privateMessageMode: false
        },
        messageToSend: null,
        titleSpinner: true,
      }
    },
    created () {
      const i = this;
      const streamId = this.$route.params.streamId;
      const session = this.session;

      this.eventBus.$on('JanusReady', function (result) {
        i.eventBus.$emit('getJanusLiveContent', {
          streamId,
          pin: i.pin
        });
      });

      this.eventBus.$on('janusStartingStream', function () {
      });

      this.eventBus.$on('janusStartedStream', function () {
        i.stream.ready = true;
        this.$http.get(`${i.config.backend}/user/content/view/${streamId}/${session.nickname}`,
        {
          headers: {
            'Authorization': session.token
          }
        }).catch((response) => {
          i.$refs.errorshelper.processHttpResponse(response);
        });
      });

      this.eventBus.$on('janusRemoteStream', function (obj) {
        const stream = obj.stream;
        const browser = obj.browser;
        this.currentStream = stream;
        if (i.$refs.video && i.$refs.video.length > 0) {
          const videoTracks = stream.getVideoTracks();
          if (videoTracks && videoTracks.length > 0 && !videoTracks[0].muted) {
            if (i.$refs.video.videoWidth) i.$refs.video.show();
          }
          return;
        }
        if (i.$refs.video === undefined || i.$refs.video === null) return;
        if (browser.name === 'chrome') {
          const chromever = browser.version;
          if (chromever >= 43) {
            i.$refs.video.srcObject = stream;
          } else if (typeof element.src !== 'undefined') {
            i.$refs.video.src = URL.createObjectURL(stream);
          } else {
            console.error("Error attaching stream to element");
          }
        } else {
          i.$refs.video.srcObject = stream;
        }
      });

      this.eventBus.$on('janusStoppedStream', function () {
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

      this.$http.get(`${this.config.backend}/user/content/${streamId}`,
      {
        headers: {
          'Authorization': session.token
        }
      }).then((response) => {
        const newStream = response.body;
        if (newStream.isPayPerView) {
          if (!session.janusPins[`cid${streamId}`]) {
            return i.$router.push(`/buyPPVContent/${streamId}`);
          } else {
            i.updatePin(session.janusPins[`cid${streamId}`]);
          }
        }
        newStream.ready = false;
        i.updateStream(newStream);
        this.eventBus.$emit('JanusReady?', null);
        i.getMyRank();
        i.getMyFav();
      }).catch((response) => {
        i.$refs.errorshelper.processHttpResponse(response);
      });
    },
    beforeDestroy () {
      this.eventBus.$emit('leaveStreaming', null);
    },
    methods: {
      getMyRank: function () {
        const i = this;
        const streamId = i.$route.params.streamId;
        i.$http.get(`${i.config.backend}/user/content/rank/${streamId}/${i.session.nickname}`,
        {
          headers: {
            'Authorization': i.session.token
          }
        }).then((response) => {
          i.setMyRank(response.body.pathTokenVOD);
        }).catch((response) => {
          i.$refs.errorshelper.processHttpResponse(response);
        });
      },
      getMyFav: function () {
        const i = this;
        const streamId = i.$route.params.streamId;
        i.$http.get(`${i.config.backend}/user/content/fav/${streamId}/${i.session.nickname}`,
        {
          headers: {
            'Authorization': i.session.token
          }
        }).then((response) => {
          i.setMyFav(response.body.pathTokenVOD);
        }).catch((response) => {
          i.$refs.errorshelper.processHttpResponse(response);
        });
      },
      setMyRank: function (myRank) {
        this.myRank = myRank;
        this.$refs.fivestarsrating.paintStars(myRank);
      },
      setMyFav: function (myFav) {
        this.myFav = myFav;
      },
      updatePin: function (pin) {
        this.pin = pin;
      },
      updateStream: function (stream) {
        this.titleSpinner = false;
        this.stream = stream;
      },
      updateAlert: function (alert) {
        this.alert = alert;
      },
      playingVideo: function () {
        if (!this.currentStream) return;
        const videoTracks = this.currentStream.getVideoTracks();
        if (videoTracks === null || videoTracks === undefined || videoTracks.length === 0) return;
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
      },
      getDate: function(UNIX_timestamp){
        // TODO: arreglar.
        let a = new Date(UNIX_timestamp * 1000);
        let months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
        let year = a.getFullYear();
        let month = months[a.getMonth()];
        let date = a.getDate();
        let hour = a.getHours();
        let min = a.getMinutes();
        let sec = a.getSeconds();
        let time = date + ' ' + month + ' ' + year + ' ' + hour + ':' + min ;
        return time;
      }
    }
  }
</script>

<style>
  .chat-body {
    height:300px;
    overflow-y:scroll;
    width:100%;
  }
</style>
