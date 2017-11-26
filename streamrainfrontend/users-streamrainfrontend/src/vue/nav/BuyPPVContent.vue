<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left"> 
        <h1>Buy PPV Content</h1>
        <hr>
        <h2>Content Info <i v-if="!contentInfo" class="fa fa-spinner fa-spin" style="font-size"></i></h2>
        <div v-if="contentInfo">
          <h3>{{ contentInfo.name }}</h3>
          <div>
            <p v-if="contentInfo.type" class="label label-info">{{ contentInfo.type }}</p>
            <br>
            <p v-if="contentInfo.isPayPerView" class="label label-danger">Pay Per View</p>
          </div>
          <div v-if="contentInfo.dateStart">
            <br>
            {{ getDate(contentInfo.dateStart) }}
          </div>
          <br>
          <b>Description</b>
          <p>{{ contentInfo.description }}</p>
        </div>
        <div class="text-center">
          <br>
          <button v-if="contentInfo" v-on:click="buy" class="btn btn-primary" :disabled="!buttonEnable">
            {{ buttonText }} <i v-if="buttonSpinner" class="fa fa-spinner fa-spin" style="font-size">
            </i>
          </button>
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
      'session'
    ],
    components: {
      'streamrain-errorshelper': ErrorsHelper
    },
    data () {
      return {
        contentInfo: null,
        buttonText: 'Buy it now!',
        buttonSpinner: false,
        buttonEnable: false
      }
    },
    created () {
      const i = this;
      const contentId = this.$route.params.contentId;

      this.$http.get(`${this.config.backend}/user/content/${contentId}`,
      {
        headers: {
          'Authorization': i.session.token
        }
      }).then((response) => {
        let contentInfo = response.body;
        // Si no es PPV
        if (!contentInfo.isPayPerView) {
          return i.$router.push('/notFound');
        }
        if (contentInfo.isPayPerView && i.session.janusPins[`cid${contentId}`]) {
          contentInfo.bought = true;
          i.updatebuttonText('Acquired!');
        } else {
          contentInfo.bought = false;
          i.updatebuttonEnable(true);
        }
        i.updateContentInfo(contentInfo);
      }).catch((response) => {
        this.$refs.errorshelper.processHttpResponse(response);
      });

    },
    methods: {
      updatebuttonEnable: function (buttonEnable) {
        this.buttonEnable = buttonEnable;
      },
      updateContentInfo: function (contentInfo) {
        this.contentInfo = contentInfo;
      },
      updatebuttonText: function (buttonText) {
        this.buttonText = buttonText;
      },
      buy: function () {
        // TODO.
      },
      getDate: function(UNIX_timestamp){
        // TODO: arreglar.
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
  function addDays(date, days) {
    var result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
  }
</script>
