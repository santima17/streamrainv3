<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left"> 
        <h1>{{ config.tenant.name }}, Sign Up!</h1>
        <hr>
        <div v-if="success.show">
          <div class="alert alert-dismissible alert-success">
            <p>Success!</p>
            <p>{{ success.message }}</p>
          </div>
          <router-link to="/login">Go to Log In page!</router-link></li>
        </div>
        <div v-if="alert.show && !success.show" class="alert alert-dismissible alert-warning">
          <button class="close" v-on:click="alert.show = false">&times;</button>
          <p>Oops!</p>
          <p>{{ alert.message }}</p>
        </div>
        <div v-if="!success.show" class="row">
          <div class="col-sm-4 text-left">
            <div class="form-group">
              <p>Nickname</p>
              <input :disabled="!buttonEnable" v-model="nickname" class="form-control input-sm" id="nickname" type="text">
            </div>
          </div>
          <div class="col-sm-8 text-left">
            <div class="form-group">
              <p>Email</p>
              <input :disabled="!buttonEnable" v-model="email" class="form-control input-sm" id="email" type="text">
            </div>
          </div>
        </div>
        <div v-if="!success.show" class="form-group">
          <p>Password</p>
          <input :disabled="!buttonEnable" v-model="password" class="form-control input-sm" id="password" type="password">
        </div>
        <div v-if="!success.show" class="form-group">
          <p>Password Confirmation</p>
          <input :disabled="!buttonEnable" v-model="confirmPassword" class="form-control input-sm" id="confirmPassword" type="password">
        </div>
        <div v-if="!success.show" class="row">
          <div class="col-sm-4 text-left">
            <div class="form-group">
              <p>Country</p>
              <input :disabled="!buttonEnable" v-model="country" class="form-control input-sm" id="country" type="text">
            </div>
          </div>
          <div class="col-sm-8 text-left">
            <div class="form-group">
              <p>City</p>
              <input :disabled="!buttonEnable" v-model="city" class="form-control input-sm" id="city" type="text">
            </div>
          </div>
        </div>
        <br>
        <div v-if="!success.show" class="form-group">
          <button v-on:click="signup" class="btn btn-default" :disabled="!buttonEnable">{{ buttonText }} <i v-if="!buttonEnable" class="fa fa-spinner fa-spin" style="font-size"></i></button>
        </div>
        <br>
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
        city: '',
        alert: {
          show: false,
          message: null
        },
        buttonEnable: true,
        buttonText: 'Sign Up',
        success: {
          show: false,
          message: null
        }
      }
    },
    methods: {
      validation: function () {
        if (this.nickname.length < 1) {
          this.alert.message = 'Invalid nickname'
          this.alert.show = true;
          return false;
        }
        const emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (!emailRegex.test(this.email)) {
          this.alert.message = 'Invalid email'
          this.alert.show = true;
          return false;
        }
        if (this.password.length < 1) {
          this.alert.message = 'Invalid password'
          this.alert.show = true;
          return false;
        }
        if (this.password != this.confirmPassword ) {
          this.alert.message = 'Password and password confirmation do not match'
          this.alert.show = true;
          return false;
        }
        if (this.country.length < 1) {
          this.alert.message = 'Invalid country'
          this.alert.show = true;
          return false;
        }
        if (this.city.length < 1) {
          this.alert.message = 'Invalid city'
          this.alert.show = true;
          return false;
        }
        return true;
      },
      signup: function () {
        if (!this.buttonEnable) return;
        if (!this.validation()) return;
        this.buttonEnable = false;
        this.buttonText = 'Please wait... ';
        const router = this.$router;
        const updateAlert = this.updateAlert;
        const updateSuccess = this.updateSuccess;
        const updateButtonEnable = this.updateButtonEnable;
        const updateButtonText = this.updateButtonText;
        const tenantName = this.config.tenant.name;
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
          updateSuccess({
            show: true,
            message: `Welcome to ${tenantName}!`
          })
        }).catch((response) => {
          updateButtonEnable(true);
          updateButtonText('Sign Up');
          // TODO: Manejar los casos en que el nickname ya está usado, etćetera.
          switch(response.status) {
            case 500:
              updateAlert({
                show: true,
                message: 'Internal server error'
              });
              break;
            case 400:
              updateAlert({
                show: true,
                message: 'Bad Request'
              });
              break;
            default:
              updateAlert({
                show: true,
                message: 'An error has occurred'
              });
          } 
          console.log(JSON.stringify(error));
          updateAlert({
            show: true,
            message: 'An error has occurred'
          });
        });
      },
      updateSuccess: function (success) {
        this.success = success;
      },
      updateAlert: function (alert) {
        this.alert = alert;
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
