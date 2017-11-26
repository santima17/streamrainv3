<template>
<div class="container-fluid text-left" style="width:95%">

  	<div  class="form-group">
		<select v-model="selected" style="padding:10px">
      <option disabled value="">Seleccionar GENERADOR</option>
      <option v-for="g in generators" >{{g.name}}</option>
    </select>
	</div>
	<!-- Input buscar -->
	<div class="input-group">
		<input type="text" class="form-control" placeholder="Buscar por Nombre, Pais, Ciudad..."  v-model="inputBuscar">
		<span class="input-group-btn">
			<button class="btn btn-default" type="button" @click="buscar()"  :disabled="selected===''">Buscar</button>
		</span>
	</div><br>
	<!-- Resultados -->
	<div class="row">
		<transition-group name="jump" appear >
			<!-- Contenido -->
			<div class="col-md-4" v-if="u.mostrar" v-for="u in users" :key="u">
				<div class="thumbnail">

          <div v-if="u.blocked" style="padding:9px;top:0px;right:20px;color:white;position:absolute;background-color:grey;font-weigth:bold">
						<b>Bloqueado</b>
					</div>
					<!-- Datos del contenido -->
					<div class="caption">
						<h3><i class="fa fa-user-o" aria-hidden="true"></i> {{u.nickname}}</h3>
						<b>Email: </b>{{u.email}}<br>
            <b>Country: </b>{{u.country}} <br>
						<b>City: </b>{{u.city}}<br>
						<div class="text-right" >
              <button type="submit" class="btn btn-primary" @click="bloquear(u.nickname)" v-if="!u.blocked">Bloquear </button>
		          <button type="submit" class="btn btn-default" @click="unbloquear(u.nickname)" v-if="u.blocked">Desbloquear </button>
						</div>
					</div>
				</div>
			</div>
		</transition-group>
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
			selected: '', 
      users: [],
      selected: '',
			inputBuscar:'',
      generators: []
		}
	},
	mounted () {
		this.getGenerators();
	},
	methods: {
    getGenerators () {
			this.generators = [];
			//var contentsaux = [{ "id": 1, "name": "Batman Batman22 Batman2 Batman2 Batman2", "description": "vive n una cueva mas grandevive en una cueva mas grandevive en una cueva mas grandevive en una cueva mas grandevive en una cueva mas grandevive en una cueva mas grande", "type": "Evento Espectaculo", "alwaysAvailable": false, "ranking": 3, "coverPictureUrl": "images_022.jpg", "storageUrl": "bbb.mp4", "isPayPerView": true, "mostrar": true, "actorsName":"Kim Bas, JAck Nic", "directorsName": "JAmes Cameron","picture": null, "video": null, "duration": 170, "dateStartLiveOnly": "20-12-17 12:30", "estimatedDuraction": null, "categoriesName": "Terror, Futbol", "similarContentsName": "Batman 1, La historia sin fin", "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 3, "name": "BaRman 3", "description": "vive en una cueva mas grande", "type": "Pelicula", "alwaysAvailable": true, "ranking": 1, "coverPictureUrl": "images_023.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false }, { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [1,2], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 5, "name": "Los 7 magnificos", "description": "vive en una cueva", "type": "Serie", "alwaysAvailable": true, "ranking": null, "coverPictureUrl": "images_024.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false }, { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 1, "name": "Batman2", "description": "vive en una cueva mas grande", "type": "Pelicula", "alwaysAvailable": true, "ranking": null, "coverPictureUrl": "images_025.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false }, { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }];
			//console.log(this.contents);
			const i = this; 
			i.$http.get(`${i.config.backend}/administrator`,{
				headers: { 	'Authorization': this.session.token}
			}).then((result) => {	
				for (var index = 0; index < result.body.length; index++){ //result.body.length;
					var generator = result.body[index]; //result.body[index];//
					var name; var dbIp;
					var dbPort; var dbUser;
          var generatorUser; var logo;
					for (var key in generator){
						var value = generator[key];
						if (key === 'name') {
							name = value;
						}
          }
					this.generators.push({name})
				}
			}) 
    },
    bloquear(nickname) {
      var tenantId = this.selected;
          this.$http.post(`${this.config.backend}/administrator/blockUser?tenantId=${tenantId}&userNickName=${nickname}`,
            {},
            {headers: { 'Authorization': this.session.token}}).then((response) => {
              this.buscar();
            }).catch((response) => {            
            });
    },
    unbloquear(nickname) {
      var tenantId = this.selected;
          this.$http.post(`${this.config.backend}/administrator/unblockUser?tenantId=${tenantId}&userNickName=${nickname}`,
            {},
            {headers: { 	'Authorization': this.session.token}}).then((response) => {
              this.buscar();
            }).catch((response) => {            
            });
    },
		buscar() {
			this.users = [];
      const i = this; 
			i.$http.get(`${i.config.backend}/administrator/user/all/${i.selected}`,{
				headers: { 'Authorization': this.session.token}
			}).then((result) => {	
				var input, filter;
				input = this.inputBuscar;
				filter = input.toUpperCase();
				for (var index = 0; index < result.body.length; index++){ //result.body.length;
					var user = result.body[index]; //result.body[index];//
					var  email;  var nickname;
          var country; var city;
          var blocked; var mostrar;
					for (var key in user){
						var value = user[key];
            if (key === 'email') {
							email = value;
						}
						if (key === 'nickname') {
							nickname = value;
						}
						if (key === 'country') {
							country = value;
						}
						if (key === 'city') {
							city = value;
						}
						if (key === 'blocked') {
							blocked= value;
						}
					}
          if ((nickname.toUpperCase().indexOf(filter) > -1 || country.toUpperCase().indexOf(filter) > -1
           || city.toUpperCase().indexOf(filter) > -1)){
						mostrar = true;
					} else{
						mostrar = false;
					}
					this.users.push({blocked,mostrar,email,nickname,country,city})
				}
			}) 
		}
	}
}
</script>
<style>
.dropdown-toggle .glyphicon { margin-left: 20px; margin-right: -40px }
</style>
