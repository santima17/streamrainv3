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
              <input v-model="username" class="form-control input-sm" type="text" value="Username">
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
        </div>
        <hr>
      </div>
      <div class="col-sm-2 sidenav">
      </div>
    </div>
  </div>
</template><script>
  export default {
  props: [
    'config',
    'eventBus'
  ],
  data () {
    return {
      username: '',
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
      if (this.username.length < 1) {
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
    login: function () {
      if (!this.buttonEnable) return;
      if (!this.validation()) return;
      const eventBus = this.eventBus;
      const router = this.$router;
      const username = this.username;
      const password = this.password;
      const updateLoginError = this.updateLoginError;
      const updateButtonEnable = this.updateButtonEnable;
      const updateButtonText = this.updateButtonText;
      if (username.trim() !== '' && password.trim() !== '') {
        if (this.buttonEnable) {
          updateButtonEnable(false);
          updateButtonText('Please wait... ');
          this.$http.post(`${this.config.backend}/user/login`,
            {
              username,
              password,
              twitter: false,
              twitterID: ''
            },
            {
              headers: {
                'Access-Control-Expose-Headers': 'Authorization'
              }
            }).then((response) => {
              eventBus.$emit('setSession', {
                username,
                userToken: response.headers.get('Authorization')
              });
              router.push('/');
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
      }
    }
  }
</script>
