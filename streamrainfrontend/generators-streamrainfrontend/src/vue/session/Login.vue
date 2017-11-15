<template>
 	<div id="wrapper" >
		<div class="vertical-align-wrap" v-bind:style="{ backgroundImage: 'url(dist/fondo.jpg)' }">
			<div class="vertical-align-middle">
				<div class="auth-box">
					<div class="content">
						<div class="header">
						<div class="logo text-center"><img src="../../assets/img/foccsmint.png" alt="sonnny"></div>
						<p class="lead">Portal de Administracion</p>
						</div>
						<form class="form-auth-small" v-on:submit.prevent="login">
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user"></i></span>
								<input class="form-control" placeholder="Username" type="text" v-model="username" required="">
							</div>
							</div>
							<div class="form-group">
								<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-key"></i></span>
								<input class="form-control" placeholder="Password" type="password" v-model="password"  required="">
							</div>
							</div>
							<button type="submit" class="btn btn-primary btn-lg btn-block">INICIAR SESION</button>
						</form>
					</div>
					<div class="logo text-right"><img src="../../assets/img/logomint.png" alt="sr"></div>
          <div class="logo text-center" style="color:red;padding:10"><span class="lead">{{errores}}</span></div> 
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
      username: null,
      password: null,
      errores: ''
    }
  },
  methods: {
    login: function () {
      const i = this;
      const username = this.username;
      const password = this.password;
      if (username.trim() !== '' && password.trim() !== '') {
        this.$http.post(`${this.config.backend}/generator/login`,
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
            i.eventBus.$emit('setToken', response.headers.get('Authorization'));
            this.$router.push("/");
          }).catch((response) => {
            this.errores = 'Error durante la verificación. Las credenciales aportadas no son correctas.'
          });
          //sacar esto inicio
          i.eventBus.$emit('setToken','adsfasdfasddsfadf');
           this.$router.push("/");
          //sacar esto fin
          
        } 
      }
    }
  }
</script>

