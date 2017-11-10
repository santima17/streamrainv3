<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left">
        <div class="row content" v-if="loginError">
          <div class="alert alert-dismissible alert-warning">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <p><strong>Oops!</strong></p>
            <p>{{ loginError }}</p>
          </div>
        </div>
        <h1>Log In</h1>
        <div class="row">
          <div class="col-sm-4 text-left">
            <div class="form-group">
              <label class="control-label" for="focusedInput">Username</label>
              <input v-model="username" class="form-control" type="text" value="Username">
            </div>
            <div class="form-group">
              <label class="control-label">Password</label>
              <input v-model="password" class="form-control" type="password" value="Password">
            </div>
            <div class="form-group">
              <button v-on:click="login" class="btn btn-primary" :disabled="!buttonEnable">Log In</button>
            </div>
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
      loginError: null
    }
  },
  methods: {
    login: function () {
      const eventBus = this.eventBus;
      const router = this.$router;
      const username = this.username;
      const password = this.password;
      const updateLoginError = this.updateLoginError;
      const updateButtonEnable = this.updateButtonEnable;
      if (username.trim() !== '' && password.trim() !== '') {
        if (this.buttonEnable) {
          updateButtonEnable(false);
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
            }).catch((error) => {
              updateLoginError('Connection error!');
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
      }
    }
  }
</script>
