<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left"> 
        <h1>Welcome to Streamrain {{ config.tenant.name }}</h1>
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
          <div class="text-info lead">Featured contents</div>
          <carousel>
            <slide v-for="(content, index) in featured" :key="index">
              <div class="text-center">
                <div class="lead text-center">{{ content.name }}</div>
                <p class="text-center">
                  {{ content.description }}
                </p>
                <img v-bind:src="config.imgPath + '/' + content.coverPictureUrl"  height="150">
                <br><br>
                <div v-if="content.alwaysAvailable">
                  <router-link :to="`/vod/${content.id}`"><div class="btn btn-info btn-sm">Watch now!</div></router-link>
                </div>
                <div v-if="!content.alwaysAvailable">
                  <router-link :to="`/live/${content.id}`"><div class="btn btn-info btn-sm">Watch now!</div></router-link>
                </div>
              </div>
            </slide>
          </carousel>
        </div>
        <streamrain-errorshelper ref="errorshelper"
          :eventBus="eventBus"
          :config="config"
        >
        </streamrain-errorshelper>
      </div>
      <div class="col-sm-2 sidenav">
      </div>
    </div>
  </div>
</template>

<script>
  import ErrorsHelper from '../utils/ErrorsHelper.vue';
  import { Carousel, Slide } from 'vue-carousel';
  export default {
    props: [
      'eventBus',
      'config',
      'session',
      'alert'
    ],
    components: {
      Carousel,
      Slide,
      'streamrain-errorshelper': ErrorsHelper
    },
    data () {
      return {
        featured: []
      }
    },
    created () {
      const session = this.session;
      const i = this;
      this.$http.get(`${this.config.backend}/user/content`,
      {
        headers: {
          'Authorization': session.token
        }
      }).then((response) => {
        const now = new Date();
        response.body.forEach((element) => {
          if (element.featured && (element.featuredDateStart < now && now < element.featuredDateFinish)) {
            i.pushFeatured(element);
          }
        });
      }).catch((response) => {
        i.$refs.errorshelper.processHttpResponse(response);
      });
    },
    methods: {
      pushFeatured: function (content) {
        this.featured.push(content);
      },
      updateAlert: function (alert) {
        this.alert = alert;
      }
    }
  }
</script>
