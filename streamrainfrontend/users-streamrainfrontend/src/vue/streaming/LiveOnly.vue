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
                <h3 class="panel-title">Chatroom</span></h3>
              </div>
              <div class="panel-body chat-body relative" id="chatroom">
                <div v-for="(message, index) in chatroom.messages" :key="index">
                  <p :class="message.p_class">{{ message.preamble }}:</p><p class="small">{{ message.text }}</p>
                  <hr>
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
      'tenant',
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
          messages: []
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
        i.janusTextroomNewPublicMessage(message);
      });

      this.eventBus.$once('janusTextroomYouJoined', function (myId) {
        i.janusTextroomYouJoined(myId);
      });

      this.eventBus.$on('janusTextroomSomeoneJoined', function (Id) {
        i.janusTextroomSomeoneJoined(Id);
      });

      this.eventBus.$on('janusTextroomSomeoneLeaved', function (Id) {
        i.janusTextroomSomeoneLeaved(Id);
      });

      this.eventBus.$emit('JanusReady?', null);
    },
    beforeDestroy () {
      this.eventBus.$emit('leaveStreaming', null);
    },
    methods: {
      sendMessage: function () {
        this.eventBus.$emit('janusSendMessage', this.messageToSend);
      },
      janusTextroomNewPublicMessage: function (message) {
        this.chatroom.messages.push({
          p_class: 'text-primary',
          preamble: `[${message.from}]`,
          text: message.text
        });
      },
      janusTextroomYouJoined: function (myId) {
        this.chatroom.ready = true;
        this.myId = myId;
        this.chatroom.messages.push({
          p_class: 'text-success',
          preamble: '[Welcome]',
          text: `You just joined the chatroom as ${myId}`
        });
      },
      janusTextroomSomeoneJoined: function (Id) {
        this.chatroom.messages.push({
          p_class: 'text-info',
          preamble: '[Info]',
          text: `${Id} just joined the chatroom`
        });
      },
      janusTextroomSomeoneLeaved: function (Id) {
        this.chatroom.messages.push({
          p_class: 'text-info',
          preamble: '[Info]',
          text: `${Id} just left the chatroom`
        });
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

