<template>
<div class="container-fluid text-left" style="width:95%">
	<!-- Input buscar -->
	<div class="input-group">
		<input type="text" class="form-control" placeholder="Buscar por Nombre"  v-model="inputBuscar">
		<span class="input-group-btn">
			<button class="btn btn-default" type="button" @click="buscar()">Buscar</button>
		</span>
	</div><br>
	<!-- Resultados -->
	<div class="row">
		<transition-group name="jump" appear >
			<!-- Contenido -->
			<div class="col-md-4" v-if="g.mostrar" v-for="g in generators" :key="g">
				<div class="thumbnail">
		
					
					<!-- Datos del contenido -->
					<div class="caption">
						<h3>{{g.name}}</h3>
						<b>Data Base: </b>{{g.dbIp}}:{{g.dbPort}}<br>
            <b>Data Base USER: </b>{{g.dbUser}} <br>
						<b>Generator USER: </b>{{g.generatorUser}}<br>

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
			generators: [],
			inputBuscar:''
		}
	},
	mounted () {
		this.buscar();
	},
  methods: {
		buscar() {
			this.generators = [];
			//var contentsaux = [{ "id": 1, "name": "Batman Batman22 Batman2 Batman2 Batman2", "description": "vive n una cueva mas grandevive en una cueva mas grandevive en una cueva mas grandevive en una cueva mas grandevive en una cueva mas grandevive en una cueva mas grande", "type": "Evento Espectaculo", "alwaysAvailable": false, "ranking": 3, "coverPictureUrl": "images_022.jpg", "storageUrl": "bbb.mp4", "isPayPerView": true, "mostrar": true, "actorsName":"Kim Bas, JAck Nic", "directorsName": "JAmes Cameron","picture": null, "video": null, "duration": 170, "dateStartLiveOnly": "20-12-17 12:30", "estimatedDuraction": null, "categoriesName": "Terror, Futbol", "similarContentsName": "Batman 1, La historia sin fin", "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 3, "name": "BaRman 3", "description": "vive en una cueva mas grande", "type": "Pelicula", "alwaysAvailable": true, "ranking": 1, "coverPictureUrl": "images_023.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false }, { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [1,2], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 5, "name": "Los 7 magnificos", "description": "vive en una cueva", "type": "Serie", "alwaysAvailable": true, "ranking": null, "coverPictureUrl": "images_024.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false }, { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 1, "name": "Batman2", "description": "vive en una cueva mas grande", "type": "Pelicula", "alwaysAvailable": true, "ranking": null, "coverPictureUrl": "images_025.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false }, { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }];
			//console.log(this.contents);
			const i = this; 
			i.$http.get(`${i.config.backend}/administrator`,{
				headers: { 	'Authorization': this.session.token}
			}).then((result) => {	
				var input, filter;
				input = this.inputBuscar;
				filter = input.toUpperCase();
				for (var index = 0; index < result.body.length; index++){ //result.body.length;
					var generator = result.body[index]; //result.body[index];//
					var name; var dbIp;
					var dbPort; var dbUser;
          var generatorUser; var logo;
          var mostrar;
					for (var key in generator){
						var value = generator[key];
						if (key === 'name') {
							name = value;
						}
						if (key === 'dbIp') {
							dbIp = value;
						}
						if (key === 'dbPort') {
							dbPort = value;
						}
						if (key === 'dbUser') {
							dbUser= value;
						}
						if (key === 'generatorUser') {
							generatorUser= value;
						}
           
            logo = '';//`${this.config.vod}/logo_`+name+'.png';
          }
          if (name.toUpperCase().indexOf(filter) > -1 ){
              mostrar = true;
            } else{
              mostrar = false;
            }
					this.generators.push({
						name,dbIp,dbPort,dbUser,generatorUser,logo, mostrar
					})
				}
			}) 
		}
	}
}
</script>
<style>
.top-right {top: 8px;right: 8px;position: absolute;text-align: right;color: red;}
.fade-enter-active,.fade-leave-active{transition:opacity .3s}.fade-enter,.fade-leave-active,.fade-leave-to{opacity:0}.jump-enter-active{animation:jump .2s}.jump-leave-active{animation:jump .2s reverse}@keyframes jump{0%{transform:translateY(10px)}50%{transform:translateY(-10px)}100%{transform:translateY(0)}}.long-form-enter-active{transition:all .2s ease}.long-form-leave-active{transition:all .5s cubic-bezier(1,.5,.8,1)}.long-form-enter,.long-form-leave-to{border-right:solid 1px #ddd!important;opacity:0;transform:scaleX(0)}.scale-up-enter-active{animation:scaleUp .2s}.scale-up-leave-active{animation:scaleUp .2s reverse}@keyframes scaleUp{0%{transform:scale3D(1,1,1)}50%{transform:scale3D(1.2,1.2,1.2)}100%{transform:scale3D(1,1,1)}}.scale-down-enter-active{animation:scaleDown .2s}.scale-down-leave-active{animation:scaleDown .2s reverse}@keyframes scaleDown{0%{transform:scale3D(1.2,1.2,1.2)}50%{transform:scale3D(.8,.8,.8)}100%{transform:scale3D(1,1,1)}}.slide-left-enter-active{animation:slideLeft .2s}.slide-left-leave-active{animation:slideLeft .2s reverse}@keyframes slideLeft{0%{transform:translate3d(30px,0,0)}70%{transform:translate3d(-5px,0,0)}100%{transform:translate3d(0,0,0)}}.slide-right-enter-active{animation:slideRight .2s}.slide-right-leave-active{animation:slideRight .2s reverse}@keyframes slideRight{0%{transform:translate3d(-30px,0,0)}70%{transform:translate3d(5px,0,0)}100%{transform:translate3d(0,0,0)}}
</style>
