<template>
  <div class="lead text-left">
    <div v-if="localFav !== null && !sending">
      <a v-if="localFav" v-on:click="sendUnfav()" class="text-info" role="button">
        <i class="glyphicon glyphicon-heart">
        </i>
      </a>
      <a v-if="!localFav" v-on:click="sendFav()" class="text-info" role="button">
        <i class="glyphicon glyphicon-heart-empty">
        </i>
      </a>
    </div>
    <div v-if="localFav !== null && sending">
      <i class="fa fa-spinner fa-spin text-info" style="font-size"></i>
    </div>
    <streamrain-errorshelper ref="errorshelper"
      :eventBus="eventBus"
      :config="config"
    >
    </streamrain-errorshelper>
  </div>
</template>

<script>
  import ErrorsHelper from '../utils/ErrorsHelper.vue';
  export default {
    props: [
      'session',
      'contentId',
      'myFav',
      'postFav',
      'eventBus',
      'config'
    ],
    components: {
      'streamrain-errorshelper': ErrorsHelper
    },
    data () {
      return {
        sending: false,
        localFav: null
      }
    },
    created () {
      this.localFav = this.myFav;
    },
    methods: {
      updateSending: function (sending) {
        this.sending = sending;
      },
      updateLocalFav: function (localFav) {
        this.localFav = localFav;
      },
      sendFav: function () {
        const i = this;
        i.updateSending(true);
        i.$http.post(`${i.config.backend}/user/content/fav`,
        {
          fav: true
        },
        {
          headers: {
            'Authorization': i.session.token
          }
        }).then((response) => {
          i.updateLocalFav(true);
          i.updateSending(false);
        }).catch((response) => {
          i.updateSending(false);
          console.error(JSON.stringify(response));
          i.$refs.errorshelper.processHttpResponse(response);
        });
      },
      sendUnfav: function () {
        const i = this;
        i.updateSending(true);
        i.$http.post(`${i.config.backend}/user/content/fav`,
        {
          fav: false
        },
        {
          headers: {
            'Authorization': i.session.token
          }
        }).then((response) => {
          i.updateLocalFav(false);
          i.updateSending(false);
        }).catch((response) => {
          i.updateSending(false);
          console.error(JSON.stringify(response));
          i.$refs.errorshelper.processHttpResponse(response);
        });
      }
    }
  }
</script>
