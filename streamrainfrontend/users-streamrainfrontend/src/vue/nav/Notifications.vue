<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left">
        <h1>Notifications <i v-if="!ready" class="fa fa-spinner fa-spin" style="font-size"></i></h1>
        <hr>
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
        <div>
          <h3>Shared with me</h3>
          <div v-if="counter === 0" class="list-group">
            <a v-for="(sharedContent, index) in sharedContents" :key="index" class="list-group-item">
              <div class="row">
                <div class="col-sm-9">
                  <h4 class="list-group-item-heading">{{ contents[sharedContent.contentId].name }}</h4>
                  <p class="list-group-item-text">
                    <div v-if="contents[sharedContent.contentId].ranking">Ranking: {{ contents[sharedContent.contentId].ranking }}</div>
                    <div v-if="!contents[sharedContent.contentId].ranking">Ranking: Unranked</div>
                    <br>
                    <div class="text-info">Shared by {{ contents[sharedContent.contentId].sharedFromNickname }}</div>
                    <div class="">{{ sharedContent.dateShared }}</div>
                    <br>
                    <div v-if="contents[sharedContent.contentId].alwaysAvailable">
                      <router-link :to="`/vod/${sharedContent.contentId}`"><div class="btn btn-info btn-sm">Watch now!</div></router-link>
                    </div>
                    <div v-if="!contents[sharedContent.contentId].alwaysAvailable">
                      <router-link :to="`/live/${sharedContent.contentId}`"><div class="btn btn-info btn-sm">Watch now!</div></router-link>
                    </div>
                  </p>
                </div>
                <div class="col-sm-3 text-right">
                  <div class="thumbnail">
                    <img v-bind:src="contents[sharedContent.contentId].coverPictureUrl">
                  </div>
                </div>
              </div>
            </a>
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
  import ErrorsHelper from '../utils/ErrorsHelper.vue';
  export default {
    props: [
      'config',
      'eventBus',
      'session',
      'alert'
    ],
    components: {
      'streamrain-errorshelper': ErrorsHelper
    },
    data () {
      return {
        ready: false,
        sharedContents: null,
        contents: {},
        counter: -1
      }
    },
    created () {
      const i = this;
      this.$http.get(`${this.config.backend}/user/content/getShareContent/${this.session.nickname}/1`,
      {
        headers: {
          'Authorization': i.session.token
        }
      }).then((response1) => {
        i.updateSharedContents(response1.body);
        i.updateCounter(response1.body.length);
        i.sharedContents.forEach((entry) => {
          this.$http.get(`${this.config.backend}/user/content/${entry.contentId}`,
          {
            headers: {
              'Authorization': i.session.token
            }
          }).then((response2) => {
            let iContent = response2.body;
            iContent.sharedFromNickname = entry.usersByUserId;
            i.addContent(iContent);
            i.decCounter();
          });
        });
      }).catch((response1) => {
        if (response1.status === 400) {
          i.updateCounter(0);
          i.updateReady(true);
        } else {
          i.$refs.errorshelper.processHttpResponse(response1);
        }
      });
    },
    methods: {
      updateCounter: function (counter) {
        this.counter = counter;
      },
      decCounter: function () {
        this.counter = this.counter - 1;
        // Por las dudas.
        if (this.counter < 0) {
          this.counter = 0;
        }
      },
      updateAlert: function (alert) {
        this.alert = alert;
      },
      updateSharedContents: function (sharedContents) {
        this.sharedContents = sharedContents;
      },
      addContent: function (content) {
        this.contents[content.id] = content;
        this.ready = true;
        console.log(JSON.stringify(content));
      },
      updateReady: function (ready) {
        this.ready = ready;
      }
    }
  }
</script>

<style>
  .thumbnail img {
    max-height: 100%;
    max-width: 100%;
  }
</style>
