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
          <div class="col-sm-12">
            <video ref="video" width="100%" controls />
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 text-left">
            <div>
              <span v-if="stream.type" class="label label-info">{{ stream.type }}</span>
              <span v-if="stream.isPayPerView" class="label label-danger">Pay Per View</span>
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
        <a role="button" v-on:click="updateSecond(0)">Send Second 0</a>
        <a role="button" v-on:click="updateSecond(5)">Send Second 5</a>
        <a role="button" v-on:click="updateSecond(10)">Send Second 10</a>
        <a role="button" v-on:click="updateSecond(25)">Send Second 25</a>
        <a role="button" v-on:click="updateSecond(-1)">Send Second -1</a>
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
        url: null,
        myRank: 0,
        myFav: null,
        sendingRank: false,
        //
        currentStream: null,
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

      this.$http.get(`${this.config.backend}/user/content/${streamId}`,
      {
        headers: {
          'Authorization': session.token
        }
      }).then((response) => {
        const newStream = response.body;
        i.updateStream(newStream);
        i.getVodContent();
        i.getMyRank();
        i.getMyFav();
      }).catch((response) => {
        this.$refs.errorshelper.processHttpResponse(response);
      });
    },
    mounted () {
    },
    beforeDestroy () {
      this.vtime = null;
      clearInterval(this.vtime);
    },
    methods: {
      updateSecond: function (second) {
        const i = this;
        const streamId = i.$route.params.streamId;
        i.$http.put(`${i.config.backend}/user/content/updateDuration`,
        {
          contentID: streamId,
          userNickname: i.session.nickname,
          second
        },
        {
          headers: {
            'Authorization': i.session.token
          }
        }).then((response) => {
          // i.setMyRank(response.body.pathTokenVOD);
          console.log('en el catch')
        }).catch((response) => {
          console.log('en el catch')
          // this.$refs.errorshelper.processHttpResponse(response);
        });
      },
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
          this.$refs.errorshelper.processHttpResponse(response);
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
          this.$refs.errorshelper.processHttpResponse(response);
        });
      },
      setMyRank: function (myRank) {
        this.myRank = myRank;
        this.$refs.fivestarsrating.paintStars(myRank);
      },
      setMyFav: function (myFav) {
        this.myFav = myFav;
      },
      updateStream: function (stream) {
        this.titleSpinner = false;
        this.stream = stream;
      },
      updateAlert: function (alert) {
        this.alert = alert;
      },
      getVodContent: function() {
        const i = this;
        const config = this.config;
        const streamId = this.$route.params.streamId;
        this.$http.get(`${config.backend}/user/content/view/${streamId}/${i.session.nickname}`,
        {
          headers: {
            'Authorization': i.session.token
          }
        }).then((response) => {
          console.log(JSON.stringify(response));
          return;
          const url = response.body.pathTokenVOD;
          const player = dashjs.MediaPlayer().create();
          player.initialize(i.$refs.video, url, false);
          // player.seek(0);

          i.$refs.current.innerHTML =i.$refs.video.load();
          i.$refs.current.innerHTML =i.$refs.video.play();
          i.vtime = setInterval(function(){i.$refs.current.innerHTML = i.$refs.video.currentTime;},5000)
        }).catch((response) => {
          console.log(JSON.stringify(response));
        });
      }
    }
  }
</script>
