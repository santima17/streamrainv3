<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left">
        <h1>{{ config.tenant.name }} Catalog <i v-if="!catalog && !alert.show" class="fa fa-spinner fa-spin" style="font-size"></i></h1>
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
        <div class="row">
          <div class="col-sm-12">
            <div v-if="catalog != null">
              <h2 class="text-info">Live</h2>
              <ul class="list-inline text-center">
                <li v-for="(streaming, index) in catalog" :key="index">
                  <div v-if="!streaming.alwaysAvailable" class="panel panel-info">
                    <div class="panel-heading">
                      <h2 class="panel-title">{{ streaming.name }}</h2>
                      <span v-if="streaming.isPayPerView" class="label label-danger">Pay Per View</span>
                      <br v-if="!streaming.isPayPerView">
                    </div>
                    <div class="panel-body text-center">
                      <img v-bind:src="streaming.coverPictureUrl" height="150">
                      <hr> 
                      <div class="text-info">
                        {{ streaming.type }}  
                      </div>
                      <div>
                        {{ streaming.dateStartLiveOnly }}
                      </div>
                      <hr>
                      <router-link :to="`/live/${streaming.id}`"><div class="btn btn-info">Watch now!</div></router-link>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12">
            <div v-if="catalog != null">
              <h2 class="text-primary">VoD</h2>
              <ul class="list-inline text-center">
                <li v-for="(streaming, index) in catalog" :key="index">
                  <div v-if="streaming.alwaysAvailable" class="panel panel-primary">
                    <div class="panel-heading">
                      <h2 class="panel-title">{{ streaming.name }}</h2>
                      <span v-if="streaming.isPayPerView" class="label label-danger">Pay Per View</span>
                      <br v-if="!streaming.isPayPerView">
                    </div>
                    <div class="panel-body text-center">
                      <img v-bind:src="streaming.coverPictureUrl" height="150">
                      <hr> 
                      <div class="text-info">
                        {{ streaming.type }}  
                      </div>
                      <hr>
                      <router-link :to="`/vod/${streaming.id}`"><div class="btn btn-primary">Watch now!</div></router-link>
                    </div>
                  </div>
                </li>
              </ul>
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
        catalog: null
      }
    },
    created () {
      const eventBus = this.eventBus;
      const session = this.session;
      const i = this;

      this.$http.get(`${this.config.backend}/user/content`,
      {
        headers: {
          'Authorization': session.token
        }
      }).then((response) => {
        i.updateCatalog(response.body);
      }).catch((response) => {
        i.$refs.errorshelper.processHttpResponse(response);
      });
    },
    methods: {
      updateCatalog: function (catalog) {
        this.catalog = catalog;
      },
      updateAlert: function (alert) {
        this.alert = alert;
      }
    }
  }
</script>
