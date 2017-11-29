<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left"> 
        <h1>Subscription</h1>
        <hr>
        <div class="form-group">
          <label for="exampleSelect1">Example select</label>
          <select class="form-control" v-model="subscriptionType">
            <option>Week</option>
            <option>Month</option>
            <option>Year</option>
          </select>
        </div>
        <br>
        <div>
          <button v-on:click="newSubscription" class="btn btn-primary">
            Get Subscription
          </button>
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
        subscriptionType: null
      }
    },
    methods: {
      getUserInfo: function(newSession) {
        const router = this.$router;
        const eventBus = this.eventBus;
        const config = this.config;
        this.$http.get(`${this.config.backend}/user/accesinformation/${newSession.nickname}`,
        {
          headers: {
            'Authorization': newSession.token
          }
        }).then((response) => {
          newSession.janusToken = response.body.janusToken;
          newSession.banned = response.body.banned;
          newSession.janusPins = response.body.janusPins;
          localStorage.setItem(`streamrain-${config.tenant.name.replace(/\s/g, '')}-session`, JSON.stringify(newSession));
          eventBus.$emit('setVueSession', newSession);
          router.push('/');
        }).catch((response) => {
          console.log(JSON.stringify(response));
        });
      },
      newSubscription: function () {
        const i = this;
        const now = new Date();
        let days = 7;
        if (this.subscriptionType === 'Month') {
          days = 31;
        } else if (this.subscriptionType === 'Year') {
          days = 365;
        }
        this.$http.post(`${this.config.backend}/user/payment/subscription`,
        {
          idPaymentMethod: 1,
          userNickName: i.session.nickname,
          datePayment: now,
          dateStart: now,
          dateFinish: addDays(now, days)
        },
        {
          headers: {
            'Authorization': i.session.token
          }
        }).then((response) => {
          let newSession = {};
          newSession.nickname = i.session.nickname;
          newSession.token = i.session.token;
          i.getUserInfo(newSession);
        }).catch((response) => {
          i.$refs.errorshelper.processHttpResponse(response);
        });
      }
    }
  }
  function addDays(date, days) {
    var result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
  }
</script>
