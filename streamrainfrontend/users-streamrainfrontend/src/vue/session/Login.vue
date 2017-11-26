<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left">
        <h1>{{ config.tenant.name }}, Log In</h1>
        <hr>
        <div class="row">
          <div class="col-sm-12" v-if="loginError.show">
            <div class="alert alert-dismissible alert-warning">
              <button class="close" v-on:click="loginError.show = false">&times;</button>
              <p>Oops!</p>
              <p>{{ loginError.message }}</p>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 text-left">
            <div class="form-group">
              <p>Nickname</p>
              <input v-model="nickname" class="form-control input-sm" type="text" value="Nickname">
            </div>
            <div class="form-group">
              <p>Password</p>
              <input v-model="password" class="form-control input-sm" type="password" value="Password">
            </div>
            <br>
            <div class="form-group">
              <button v-on:click="login" class="btn btn-default" :disabled="!buttonEnable">{{ buttonText }} <i v-if="!buttonEnable" class="fa fa-spinner fa-spin" style="font-size"></i></button>
            </div>
            <br>
          </div>
          <div class="col-sm-6 text-right">
            <!-- <div class="form-group">
              <p>Nickname</p>
              <input v-model="nickname" class="form-control input-sm" type="text" value="Nickname">
            </div>
            <div class="form-group">
              <p>Password</p>
              <input v-model="password" class="form-control input-sm" type="password" value="Password">
            </div>
            <br> -->
            <!-- <div class="form-group">
              <button v-on:click="twitterLogin" class="btn btn-primary" :disabled="!buttonEnable">Twitter <i v-if="!buttonEnable" class="fa fa-spinner fa-spin" style="font-size"></i></button>
            </div> -->
          <br>
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
    'eventBus'
  ],
  data () {
    return {
      nickname: '',
      password: '',
      buttonEnable: true,
      buttonText: 'Log In',
      loginError: {
        show: false,
        message: null
      }
    }
  },
  methods: {
    validation: function () {
      if (this.nickname.length < 1) {
        this.loginError.message = 'Invalid nickname'
        this.loginError.show = true;
        return false;
      }
      if (this.password.length < 1) {
        this.loginError.message = 'Invalid password'
        this.loginError.show = true;
        return false;
      }
      return true;
    },
    twitterLogin: function () {
      const router = this.$router;
      this.$http.get(`${this.config.backend}/auth/twitter`)
      .then((response) => { 
        // consol.error(JSON.stringify(response));
        const redirect = response.body.pathTokenVOD;
        window.location.href = redirect;
        // router.go(redirect);
      })
      .catch((response) => {
        consol.error(JSON.stringify(response));
      });
    },
    login: function () {
      if (!this.buttonEnable) return;
      if (!this.validation()) return;
      const eventBus = this.eventBus;
      const router = this.$router;
      const nickname = this.nickname;
      const password = CryptoJS.SHA1(this.password).toString();
      const updateLoginError = this.updateLoginError;
      const updateButtonEnable = this.updateButtonEnable;
      const updateButtonText = this.updateButtonText;
      const config = this.config;
      const getUserInfo = this.getUserInfo;
      if (nickname.trim() !== '' && password.trim() !== '') {
        if (this.buttonEnable) {
          updateButtonEnable(false);
          updateButtonText('Please wait... ');
          this.$http.post(`${this.config.backend}/user/login`,
            {
              username: nickname,
              password,
              twitter: false,
              twitterID: ''
            },
            {
              headers: {
                'Access-Control-Expose-Headers': 'Authorization'
              }
            }).then((response) => {
              let newSession = {};
              newSession.nickname = nickname;
              newSession.token = response.headers.get('Authorization');
              getUserInfo(newSession);
            }).catch((response) => {
              updateButtonText('Log In');
              switch(response.status) {
                case 500:
                  updateLoginError({
                    show: true,
                    message: 'Internal server error'
                  });
                  break;
                case 400:
                  updateLoginError({
                    show: true,
                    message: 'Bad Request'
                  });
                  break;
                case 401:
                  updateLoginError({
                    show: true,
                    message: 'Login error, please check nickname and password'
                  });
                  break;
                default:
                  updateLoginError({
                    show: true,
                    message: 'An error has occurred'
                  });
              }
              updateButtonEnable(true);              
            });
          }
        }
      },
      updateLoginError: function (loginError) {
        this.loginError = loginError;
      },
      updateButtonEnable: function (buttonEnable) {
        this.buttonEnable = buttonEnable;
      },
      updateButtonText: function (buttonText) {
        this.buttonText = buttonText;
      },
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
          newSession.isBanned = response.body.isBanned;
          newSession.janusPins = response.body.janusPins;
          let pins = {};
          newSession.janusPins.forEach((entry) => {
            const regexMatch = entry.match(/CID=(.*)PIN=(.*)/);
            pins[`cid${regexMatch[1]}`] = regexMatch[2];
          });
          newSession.janusPins = pins;
          console.log(JSON.stringify(newSession));
          localStorage.setItem(`streamrain-${config.tenant.name.replace(/\s/g, '')}-session`, JSON.stringify(newSession));
          eventBus.$emit('setVueSession', newSession);
          router.push('/');
        }).catch((response) => {
          console.log(JSON.stringify(response));
        });
      }
    }
  }
</script>
