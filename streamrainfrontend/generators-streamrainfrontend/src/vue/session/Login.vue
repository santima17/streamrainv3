<template>
 	<div id="wrapper" >
		<div class="vertical-align-wrap" v-bind:style="{ backgroundImage: `url(${require('../../assets/img/fondo.jpg')})` }">
			<div class="vertical-align-middle">
				<div class="auth-box">
					<div class="content">
						<div class="header">
						<div class="logo text-center"><img src="../../assets/img/tenantLogo.png" alt="..."></div>
						<p class="lead">Portal de Administracion</p>
						</div>
						<form class="form-auth-small" v-on:submit.prevent="login">
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user"></i></span>
								<input class="form-control" placeholder="Username" type="text" v-model="nickname" required>
							</div>
							</div>
							<div class="form-group">
								<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-key"></i></span>
								<input class="form-control" placeholder="Password" type="password" v-model="password"  required>
							</div>
							</div>
              <button type="submit" class="btn btn-primary btn-lg btn-block" :disabled="!buttonEnable">{{ buttonText }} <i v-if="!buttonEnable" class="fa fa-spinner fa-spin" style="font-size"></i></button>
						</form>
					</div>
					<div class="logo text-right"><img src="../../assets/img/streamrainLogo.png" alt="sr"></div>
          <div class="logo text-center" style="color:red;padding:10"><span class="lead">{{ loginError.message }}</span></div> 
				</div>
        
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
    login: function () {
      if (!this.buttonEnable) return;
      if (!this.validation()) return;
      const eventBus = this.eventBus;
      const router = this.$router;
      const nickname = this.nickname;
      const password = this.password;
      const updateLoginError = this.updateLoginError;
      const updateButtonEnable = this.updateButtonEnable;
      const updateButtonText = this.updateButtonText;
      const config = this.config;
      if (nickname.trim() !== '' && password.trim() !== '') {
        if (this.buttonEnable) {
          updateButtonEnable(false);
          updateButtonText('Please wait... ');
          this.$http.post(`${this.config.backend}/generator/login`,
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
              localStorage.setItem(`streamrain-${config.tenant.name.replace(/\s/g, '')}-session`, JSON.stringify(newSession));
              eventBus.$emit('setVueSession', newSession);
              eventBus.$emit('updateMessage', 'BIENVENIDO '+nickname);
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
      },
    logintrucho: function () {
        if (!this.buttonEnable) return;
        if (!this.validation()) return;
        const eventBus = this.eventBus;
        const router = this.$router;
        const nickname = this.nickname;
        const password = this.password;
        const updateLoginError = this.updateLoginError;
        const updateButtonEnable = this.updateButtonEnable;
        const updateButtonText = this.updateButtonText;
        const config = this.config;
        if (nickname.trim() !== '' && password.trim() !== '') {
          if (this.buttonEnable) {
            updateButtonEnable(false);
            updateButtonText('Please wait... ');
          let newSession = {};
          newSession.nickname = nickname;
          newSession.token = 'papasfritas';
          localStorage.setItem(`streamrain-${config.tenant.name.replace(/\s/g, '')}-session`, JSON.stringify(newSession));
          eventBus.$emit('setVueSession', newSession);
          eventBus.$emit('updateMessage', 'BIENVENIDO '+nickname);
          router.push('/');
          }
        }
     }
    }
  }
</script>

