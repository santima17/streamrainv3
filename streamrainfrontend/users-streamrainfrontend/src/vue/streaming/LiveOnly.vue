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
            <video width="100%" v-if="stream.ready">
            </video>
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
        messageToSend: null
      }
    },
    created () {
      const i = this;
      const streamId = this.$route.params.streamId;

      this.eventBus.$once('JanusReady', function (result) {
        i.eventBus.$emit('getJanusLiveContent', streamId);
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

