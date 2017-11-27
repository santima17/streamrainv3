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
          <h2>Shared with me</h2>
          <ul v-if="counter === 0" class="list">
            <li v-for="(sharedContent, index) in sharedContents" :key="index">
              <p>contentId: {{ sharedContent.contentId }}</p>
              <p>usersByDestinationUserId: {{ sharedContent.usersByDestinationUserId }}</p>
              <p>usersByUserId: {{ sharedContent.usersByUserId }}</p>
              <p>date: {{ sharedContent.date }}</p>
              <p>CONTENT JSON: {{ contents[sharedContent.contentId] }}</p>
            </li>
          </ul>
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
            i.addContent(response2.body);
            i.decCounter();
          });
        });
      }).catch((response1) => {
        i.$refs.errorshelper.processHttpResponse(response1);
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
      },
      getDate: function(UNIX_timestamp){
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
