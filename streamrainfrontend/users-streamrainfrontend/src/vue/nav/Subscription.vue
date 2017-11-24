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
        <!-- <div>
          <form name="aa" action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post">                     
            <input type="hidden" name="currency_code" value="USD">
              <input type="hidden" name="charset" value="utf-8">
              <input type="hidden" name="business" value="santiagomarquezmusic@gmail.com">
              <input type="hidden" name="paymentaction" value="sale">
              <input type="hidden" name="item_name" value="StreamRain Suscription - Aca va variable de tipo">
              <input type="hidden" name="item_number" value="5">
              <input type="hidden" name="amount" value="10">
              <input type="hidden" name="first_name" value="Becky">
              <input type="hidden" name="last_name" value="gif">
              <input type="hidden" name="address_override" value="0">
              <input type='hidden' name='return' value='http://hbo.127.0.0.1.nip.io:8080/streamrainRESTUI/user/payment/subscription/paypal/1/kaque/mensual'>
              <input type='hidden' name='rm' value='2'>
              <input type='hidden' name='cancel_return' value='https://'>
              <input type="hidden" name="cmd" value="_xclick">
              <input type="image" src="https://www.paypalobjects.com/es_XC/i/btn/btn_buynowCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
              <img alt="" border="0" src="https://www.paypalobjects.com/es_XC/i/scr/pixel.gif" width="1" height="1">    
          </form>
        </div> -->
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
        //const session = this.session;

        const i = this;
        const now = new Date();
        this.$http.post(`${this.config.backend}/user/payment/subscription`,
        {
          idPaymentMethod: 1,
          userNickName: i.session.nickname,
          datePayment: now,
          dateStart: now,
          dateFinish: addDays(now, 7)
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
              i.updateAlert({
                show: true,
                message: 'An error has occurred'
              });
          }
        });


        // localStorage.removeItem(`streamrain-${this.config.tenant.name.replace(/\s/g, '')}-session`);
        // this.eventBus.$emit('removeVueSession', null);
        // this.$router.push('/');
      }
    }
  }
  function addDays(date, days) {
    var result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
  }
</script>
