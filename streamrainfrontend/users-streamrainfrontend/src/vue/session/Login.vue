<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left">
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
              <button v-on:click="login" class="btn btn-primary btn-lg">Log In</button>
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
    'eventBus'
  ],
  data () {
    return {
      username: null,
      password: null
    }
  },
  methods: {
    login: function () {
      const username = this.username;
      const password = this.password;
      if (username.trim() !== '' && password.trim() !== '') {
        this.$http.post(`${this.config.backendPOSTA}/user/login`,
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
          },
          ).then((response) => {
            console.log(response.headers.get('Authorization'));
            console.log(JSON.stringify(response));
          });
        }
      }
    }
  }
</script>
