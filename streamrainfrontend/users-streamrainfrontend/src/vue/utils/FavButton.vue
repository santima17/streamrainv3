<template>
  <div>
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
      <i class="fa fa-spinner fa-spin" style="font-size"></i>
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
      sendFav: function () {
        this.localFav = true;
      },
      sendUnfav: function () {
        this.localFav = false;
      }
    }
  }
</script>
