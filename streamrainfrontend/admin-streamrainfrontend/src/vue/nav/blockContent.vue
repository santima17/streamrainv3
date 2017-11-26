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
		<input type="text" class="form-control" placeholder="Buscar por Nombre o Descripcion..."  v-model="inputBuscar">
		<span class="input-group-btn">
			<button class="btn btn-default" type="button" @click="buscar()"  :disabled="selected===''">Buscar</button>
		</span>
	</div><br>
	<!-- Resultados -->
	<div class="row">
		<transition-group name="jump" appear >
			<!-- Contenido -->
			<div class="col-md-4" v-if="c.mostrar" v-for="c in contents" :key="c">
				<div class="thumbnail">
					<!-- Etiqueta PPV -->
					<div v-if="c.isPayPerView ==='PayPerView'" style="padding:2px;top:0px;left:15px;color:white;position:absolute;background-color:green;font-weigth:bold">
					<b>	PayPerView</b>
					</div>
					<!-- Etiqueta Destacado -->
					<div v-if="c.featured" style="padding:2px;top:0px;right:15px;color:white;position:absolute;background-color:red;font-weigth:bold">
					<b>	Destacado</b>
					</div>

          <div v-if="c.blocked" style="padding:9px;top:0px;right:120px;color:white;position:absolute;background-color:grey;font-weigth:bold">
						<b>Bloqueado</b>
					</div>
					<!-- Portada -->
					<img style="width:280;height:200" :src="c.coverPictureUrl" alt="...">
					<b><i class="fa fa-film" aria-hidden="true" v-if="c.alwaysAvailable==='VOD'"></i>
							<i class="fa fa-podcast" aria-hidden="true" v-if="c.alwaysAvailable==='LIVE'"></i> {{c.type}}</b> 
							<span  v-if="c.alwaysAvailable==='VOD'">{{c.duration}}minutos</span>
								<span  v-if="c.alwaysAvailable==='LIVE'">Inicio {{c.dateStartLiveOnly}}</span>
					<!-- Ranking -->
					<div style="font-weigth:bold;text-align:right">
						<span v-for="r in 5">
							<i class="fa fa-star" aria-hidden="true" style="color:yellow;" v-if="r <= c.ranking"></i>
							<i class="fa fa-star-o" aria-hidden="true" v-else></i>
						</span>
					</div>
					<!-- Datos del contenido -->
					<div class="caption">
						<h3>{{c.namemin}}</h3>
						<!-- Mas info - muestra modal con toda la info -->
						<div class="text-right" >
              <button type="submit" class="btn btn-primary" @click="bloquear(c.contentId)" v-if="!c.blocked">Bloquear </button>
		          <button type="submit" class="btn btn-default" @click="unbloquear(c.contentId)" v-if="c.blocked">Desbloquear </button>
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
      contents: [],
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
    bloquear(contentId) {
      var tenantId = this.selected;
          this.$http.post(`${this.config.backend}/administrator/blockContent?tenantId=${tenantId}&contentId=${contentId}`,
            {},
            {headers: { 	'Authorization': this.session.token}}).then((response) => {
              this.buscar();
            }).catch((response) => {            
            });
    },
    unbloquear(contentId) {
      var tenantId = this.selected;
          this.$http.post(`${this.config.backend}/administrator/unblockContent?tenantId=${tenantId}&contentId=${contentId}`,
            {},
            {headers: { 	'Authorization': this.session.token}}).then((response) => {
              this.buscar();
            }).catch((response) => {            
            });
    },
		buscar() {
			this.contents = [];
		//	var contentsaux = [{ "id": 1, "name": "Batman Batman22 Batman2 Batman2 Batman2", "description": "vive n una cueva mas grandevive en una cueva mas grandevive en una cueva mas grandevive en una cueva mas grandevive en una cueva mas grandevive en una cueva mas grande", "type": "Evento Espectaculo", "alwaysAvailable": false, "ranking": 3, "coverPictureUrl": "images_022.jpg", "storageUrl": "bbb.mp4", "isPayPerView": true, "mostrar": true, "actorsName":"Kim Bas, JAck Nic", "directorsName": "JAmes Cameron","picture": null, "video": null, "duration": 170, "dateStartLiveOnly": "20-12-17 12:30", "estimatedDuraction": null, "categoriesName": "Terror, Futbol", "similarContentsName": "Batman 1, La historia sin fin", "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 3, "name": "BaRman 3", "description": "vive en una cueva mas grande", "type": "Pelicula", "alwaysAvailable": true, "ranking": 1, "coverPictureUrl": "images_023.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false }, { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [1,2], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 5, "name": "Los 7 magnificos", "description": "vive en una cueva", "type": "Serie", "alwaysAvailable": true, "ranking": null, "coverPictureUrl": "images_024.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false }, { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 1, "name": "Batman2", "description": "vive en una cueva mas grande", "type": "Pelicula", "alwaysAvailable": true, "ranking": null, "coverPictureUrl": "images_025.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false }, { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }];
			//console.log(this.contents);
      const i = this; 
			i.$http.get(`${i.config.backend}/administrator/content/all/${i.selected}`,{
				headers: { 'Authorization': this.session.token}
			}).then((result) => {	
				var input, filter;
				input = this.inputBuscar;
				filter = input.toUpperCase();
				for (var index = 0; index < result.body.length; index++){ //result.body.length;
					var content = result.body[index]; //result.body[index];//
					var name; var description; var contentId;
					var type; var alwaysAvailable;
					var isPayPerView; var featured;
					var coverPictureUrl; var duration;
					var actorsName; var directorsName;
					var categoriesName;	var similarContentsName
					var ranking;var namemin;var blocked;
					var dateStartLiveOnly;var mostrar = true;
					var janus_audio_port; var janus_video_port;
					for (var key in content){
						var value = content[key];
						if (key === 'name') {
							name = value;
							if (value.length>=15) {
								namemin= value.substring(0,12)+'...';
							}
							else {
								namemin=value+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
								namemin= value.substring(0,12)+'...';
							}
            }
            if (key === 'id') {
							contentId = value;
						}
						if (key === 'description') {
							description = value;
						}
						if (key === 'type') {
							type = value;
							if (this.isVOD  && (type !== 'Pelicula' && type !== 'Serie')) {
								mostrar = false
							}
							if (this.isLIVE  && (type !== 'Evento Deportivo' && type !== 'Evento Espectaculo')) {
								mostrar = false
							}
						}
						if (key === 'isPayPerView') {
							if (value ) {
								isPayPerView = 'PayPerView'
							} else {
								isPayPerView = ''
							}
							if (this.isPayPerView && !value) {
								mostrar = false
							}	
						}
						if (key === 'featured') {
							if (value ) {
								featured = 'featured'
							} else {
								featured = ''
							}
							if (this.isFeatured && !value) {
								mostrar = false
							}
						}
						if (key === 'coverPictureUrl') {
							coverPictureUrl = `${this.config.vod}`+value;
						}
						if (key === 'duration') {
							duration = value;
						}
						if (key === 'categoriesName') {
							categoriesName= value;
						}
						if (key === 'actorsName') {
							actorsName= value;
						}
						if (key === 'directorsName') {
							directorsName= value;
						}
						if (key === 'similarContentsName') {
							similarContentsName= value;
						}
						if (key === 'dateStartLiveOnly') {
							dateStartLiveOnly= value;
						}	
						if (key === 'ranking') {
							if (value !== null) {
								ranking = value;
							} else {
								ranking = 0;
							}
						}
						if (key === 'alwaysAvailable') {
							if (value) {
								alwaysAvailable = 'VOD'
							} else {
								alwaysAvailable = 'LIVE'
							}
						}
						if (key === 'blocked') {
							blocked= value;
            }
            if (key === 'janus_audio_port') {
							janus_audio_port= value;
						}
						if (key === 'janus_video_port') {
							janus_video_port= value;
						} 
					}
					if ((name.toUpperCase().indexOf(filter) > -1 || description.toUpperCase().indexOf(filter)> -1)
					&& mostrar){
						mostrar = true;
					} else{
						mostrar = false;
					}
					this.contents.push({
						name,namemin,description,type,isPayPerView,featured,mostrar,coverPictureUrl,duration,
						ranking,similarContentsName, categoriesName, actorsName, directorsName,
						alwaysAvailable,dateStartLiveOnly, janus_audio_port, janus_video_port, blocked,contentId
					})
				}
			}) 
		}
	}
}
</script>
<style>
.dropdown-toggle .glyphicon { margin-left: 20px; margin-right: -40px }
</style>
