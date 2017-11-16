<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left">
        <h1>{{ config.tenant.name }} Catalog <i v-if="!catalog && !alert.show" class="fa fa-spinner fa-spin" style="font-size"></i></h1>
        <hr>
        <div class="row">
          <div class="col-sm-12" v-if="alert.show">
            <div class="alert alert-dismissible alert-warning">
              <button type="button" class="close" data-dismiss="alert">&times;</button>
              <p><strong>Oops!</strong></p>
              <p>{{ alert.message }}</p>
            </div>
          </div>
        </div>
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
                        {{ getDate(streaming.dateStart) }}
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
      'session'
    ],
    data () {
      return {
        catalog: null,
        alert: {
          show: false,
          message: null
        }
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
        switch(response.status) {
          case 500:
            i.updateAlert({
              show: true,
              message: 'Internal server error'
            });
            break;
          case 404:
            i.updateAlert({
              show: true,
              message: 'Not found'
            });
            break;
          case 401:
          case 403:
            localStorage.removeItem(`streamrain-${i.config.tenant.name.replace(/\s/g, '')}-session`);
            i.eventBus.$emit('removeVueSession', null);
            i.updateAlert({
              show: true,
              message: 'The session has expired, please log in again'
            });
            break;
          default:
            updateAlert({
              show: true,
              message: 'An error has occurred'
            });
        }
      });
    },
    methods: {
      updateCatalog: function (catalog) {
        this.catalog = catalog;
      },
      updateAlert: function (alert) {
        this.alert = alert;
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
