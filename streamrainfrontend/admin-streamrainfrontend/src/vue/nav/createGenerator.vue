<template>
<div class="container-fluid text-left" style="width:95%">	
	<form enctype="multipart/form-data" @submit.prevent="crearGenerador()">

<div class="container-fluid text-left" style="width:95%">
	
  <div class="col-md-12">
		<div class="form-group">
			<label>Nombre</label>
			<input type="text" class="form-control" v-model="name" required>
		</div>
</div>

<div class="col-md-6">
	<div class="form-group">
		<label>DataBase IP</label>
		<input type="text" class="form-control" v-model="dbIp" placeholder="127.0.0.1" required>
	</div>
	</div>

  <div class="col-md-6">
	<div class="form-group">
		<label>DataBase PORT</label>
		<input type="text" class="form-control" v-model="dbPort" placeholder="3306" required>
	</div>
	</div>
	
   <div class="col-md-6">
	<div class="form-group">
	<label>DataBase USER</label>
		<input type="text" class="form-control" v-model="dbUser" required>
	</div>  
</div> 

  <div class="col-md-6">
	<div class="form-group">
		<label>DataBase Password</label>
		<input  class="form-control" v-model="dbPassword" type="password" required data-toggle="password">
	</div>  
</div> 

   <div class="col-md-6">
	<div class="form-group">
		<label>Generador USER</label>
		<input type="text" class="form-control" v-model="generatorUser" required>
	</div>  
</div> 

  <div class="col-md-6">
	<div class="form-group">
	<label>Generador Password</label>
		<input  class="form-control" v-model="generatorPassword" type="password" data-toggle="password" required>
	</div>  
</div> 

<div class="col-md-6">
	<div class="form-group">
		<label for="picture" class="control-label">Logo</label>
		<input type="file" @change="subirArchivo('picture', $event.target.files)" class="input-file">
	</div>  
</div> 
	
  <div class="col-md-12">
	<div style="text-align:right">
		<button type="submit" class="btn btn-primary" :disabled="!buttonEnable">{{ buttonText }} <i v-if="!buttonEnable" class="fa fa-spinner fa-spin" style="font-size"></i></button>
	</div>
  </div> 

</div>
	</form>

</div>
</template>

<script>
import _ from 'lodash';
import { MultiSelect } from 'vue-search-select';

export default {
	components: {
     MultiSelect
  },
	props: [
    'config',
	'eventBus',
	'session'
  ],
	data () {
		return {
			// json datos
      name:'',
			dbIp: '',
			dbPort: '',
			dbUser: '',
			dbPassword: '',
			generatorUser: '',
			generatorPassword: '',
			//picture, video
			formData: new FormData(),

			buttonEnable: true,
      buttonText: 'Guardar GENERADOR'
		}
	},
	methods: {
		cargarDatosPrueba () {

		},
		subirArchivo(fieldName, fileList) {	    
        	this.formData.append(fieldName, fileList[0], fileList[0].name);
      	},
		crearGenerador() {
      
			if (!this.buttonEnable) return;
			const updateButtonEnable = this.updateButtonEnable;
			const updateButtonText = this.updateButtonText;
			
			if (this.buttonEnable) {
          		updateButtonEnable(false);
          		updateButtonText('Please wait... ');  
				const generatorPassword = CryptoJS.SHA1(this.generatorPassword).toString();
				const datos = JSON.parse('{'
					+`"name":"${this.name}",`
					+`"dbIp":"${this.dbIp}",`
					+`"dbPort":${this.dbPort},`
					+`"dbUser":"${this.dbUser}",`
					+`"dbPassword":"${this.dbPassword}",`
					+`"generatorUser":"${this.generatorUser}",`
					+`"generatorPassword":"${generatorPassword}"`
					+'}');
					console.log(JSON.stringify(datos));
				this.formData.append('datos', new Blob([JSON.stringify(datos)],{type: "application/json"}));
				const url = `${this.config.backend}/administrator/createGenerator`;
				this.$http.post(url, this.formData,
					{
					headers: {
						Authorization : this.session.token
						}
					}).then((response) => {	
						// exito
						this.eventBus.$emit('updateMessage', 'Generador Creado!');
						// ir a home o crear nuevo
						this.$router.push("/");
					}).catch((error) => {				
						updateButtonText('Guardar GENERADOR');
						updateButtonEnable(true);
				});
			}	
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
