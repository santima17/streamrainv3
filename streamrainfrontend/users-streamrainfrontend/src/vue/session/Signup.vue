<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left"> 
        <h1>Sign Up</h1>
        <div class="form-group">
          <label class="control-label" for="focusedInput">Nickname</label>
          <input v-model="nickname" class="form-control input-sm" id="nickname" type="text">
        </div>
        <div class="form-group">
          <label class="control-label" for="focusedInput">Email</label>
          <input v-model="email" class="form-control input-sm" id="email" type="text">
        </div>
        <div class="form-group">
          <label class="control-label" for="focusedInput">Password</label>
          <input v-model="password" class="form-control input-sm" id="password" type="password">
        </div>
        <div class="form-group">
          <label class="control-label" for="focusedInput">Confirm Password</label>
          <input v-model="confirmPassword" class="form-control input-sm" id="confirmPassword" type="password">
        </div>
        <div class="form-group">
          <label for="country" class="control-label">Country</label>
          <div>
            <select v-model="country" class="form-control input-sm" id="country">
              <option>Argentina</option>
              <option>Brasil</option>
              <option>Chile</option>
              <option>Paraguay</option>
              <option>Uruguay</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label" for="focusedInput">City</label>
          <input v-model="city" class="form-control input-sm" id="city" type="text">
        </div>
        <div class="form-group">
          <button v-on:click="signup" class="btn btn-primary">Sign Up!</button>
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
        email: '',
        password: '',
        confirmPassword: '',
        country: '',
        city: ''
      }
    },
    methods: {
      signup: function () {
        const router = this.$router;
        this.$http.post(`${this.config.backend}/user`,
        {
          nickname: this.nickname,
          email: this.email,
          password: this.password,
          confirmPass: this.confirmPassword,
          country: this.country,
          city: this.city,
          facebookUserId: null,
          twitterUserId: null,
          integratorId: null,
          isBanned: false,
          blocked: false
        }).then((response) => {
          // TODO: poner un cartelito o algo
          router.push('/login');
        }).catch((error) => {
          // updateLoginError('Connection error!');
          // updateButtonEnable(true);
          console.log(JSON.stringify(error));            
        });
      }
    }
  }
</script>
