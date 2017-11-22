<template>
<div class="container-fluid text-left" style="width:95%">
	<!-- Input buscar -->
	<div class="input-group">
		<label class="fancy-checkbox"> 
	<input type="checkbox" v-model="isPayPerView">
	<span>PayPerView</span>
</label>
<label class="fancy-checkbox"> 
	<input type="checkbox" v-model="isFeatured">
	<span>Destacado</span>
</label>
<label class="fancy-checkbox"> 
	<input type="checkbox" v-model="isVOD">
	<span>VOD</span>
</label>
<label class="fancy-checkbox"> 
	<input type="checkbox" v-model="isLIVE">
	<span>LIVE</span>
</label>


</div>
	<div class="input-group">
		
		<input type="text" class="form-control" placeholder="Buscar por Nombre o Descripcion..."  v-model="inputBuscar">
		<span class="input-group-btn">
			<button class="btn btn-default" type="button" @click="getContents()">Buscar</button>
		</span>
	</div><br>
	<!-- Resultados -->
	<div class="row">
		<transition-group name="jump" appear >
			<!-- Contenido -->
			<div class="col-md-4" v-if="c.mostrar" v-for="c in contentsresult" :key="c">
				<div class="thumbnail">
					<!-- Etiqueta PPV -->
					<div v-if="c.isPayPerView ==='PayPerView'" style="padding-right:5px;top:0px;left:15px;color:white;position:absolute;background-color:green;font-weigth:bold">
						PayPerView
					</div>
					<!-- Etiqueta Destacado -->
					<div v-if="c.featured" style="top:0px;right:15px;color:white;position:absolute;background-color:red;font-weigth:bold;">
						Destacado
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
						<div class="text-right" @click="seleccionado(c)">
							<a data-toggle="modal" data-target="#largeModal" style="font-weight:bold;font-color:blue;cursor:pointer">Mas Info</a>
						</div>
					</div>
				</div>
			</div>
		</transition-group>
	</div>
		<!-- Modal con mas info del contenido -->
	<div class="modal fade" id="largeModal" tabindex="-1" role="dialog" aria-labelledby="largeModal" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- header -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">
						<i class="fa fa-film" aria-hidden="true" v-if="selected.alwaysAvailable==='VOD'"></i>
						<i class="fa fa-podcast" aria-hidden="true" v-else></i> &nbsp;
						{{selected.name}} 
						<span v-for="r in 5">
							<i class="fa fa-star" aria-hidden="true" style="color:yellow;" v-if="r <= selected.ranking"></i>
							<i class="fa fa-star-o" aria-hidden="true" v-else></i>
						</span>
						</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<!-- poster -->
						<div class="col-sm-5">
							<div style="thumbnail">
								<img :src="selected.coverPictureUrl" style="width:350;height:450" alt="...">
							</div>
						</div>
						<!-- info del contenido -->
						<div class="col-sm-7">
							<div class="caption">
								<div style="text-align:right;font-weight:bold;">
								 	<span v-if="selected.isPayPerView ==='PayPerView'" style="padding:5px;color:white;background-color:green;font-weigth:bold">PayPerView</span>
									<span v-if="selected.featured" style="padding:5px;color:white;background-color:red;font-weigth:bold;">Destacado</span>
								</div>
								<p>
								<b>Tipo: </b>{{selected.type}}<br>
								<b>Categoria: </b>{{selected.categoriesName}}<br><br>
								<span  v-if="selected.alwaysAvailable==='VOD'"><b>Duracion: </b>{{selected.duration}}minutos<br><br></span>
								<span  v-if="selected.alwaysAvailable==='LIVE'"><b>Inicio: </b>{{selected.dateStartLiveOnly}}<br>
								<b>Datos para Emision:</b><br> audiopt:111, audiortpmap:opus/48000/2, videopt:100, videortpmap: VP8/90000<br><br>
								</span>
								<b>Director(es): </b>{{selected.directorsName}}<br>
								<b>Actor(es): </b>{{selected.actorsName}}<br><br>
								<b>Descripcion: </b><br>
								{{selected.description}}<br><br>
								<b>Contenidos Similares: </b><br>{{selected.similarContentsName}}
								</p>
							</div>
						</div>
					</div>
				</div>
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
			selected: '', 
			contentsresult:[],
			contents: [
			{ "id": 1, "name": "Batman Batman22 Batman2 Batman2 Batman2", "description": "vive n una cueva mas grandevive en una cueva mas grandevive en una cueva mas grandevive en una cueva mas grandevive en una cueva mas grandevive en una cueva mas grande", "type": "Evento Espectaculo", "alwaysAvailable": false, "ranking": 3, "coverPictureUrl": "images_022.jpg", "storageUrl": "bbb.mp4", "isPayPerView": true, "mostrar": true, "actorsName":"Kim Bas, JAck Nic", "directorsName": "JAmes Cameron","picture": null, "video": null, "duration": 170, "dateStartLiveOnly": "20-12-17 12:30", "estimatedDuraction": null, "categoriesName": "Terror, Futbol", "similarContentsName": "Batman 1, La historia sin fin", "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 3, "name": "BaRman 3", "description": "vive en una cueva mas grande", "type": "Pelicula", "alwaysAvailable": true, "ranking": 1, "coverPictureUrl": "images_023.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false }, { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [1,2], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 5, "name": "Los 7 magnificos", "description": "vive en una cueva", "type": "Serie", "alwaysAvailable": true, "ranking": null, "coverPictureUrl": "images_024.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false }, { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 1, "name": "Batman2", "description": "vive en una cueva mas grande", "type": "Pelicula", "alwaysAvailable": true, "ranking": null, "coverPictureUrl": "images_025.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false }, { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 3, "name": "BaRman 3", "description": "vive en una cueva mas grande", "type": "Pelicula", "alwaysAvailable": true, "ranking": null, "coverPictureUrl": "images_026.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false }, { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 5, "name": "Los 7 magnificos", "description": "vive en una cueva", "type": "Serie", "alwaysAvailable": true, "ranking": null, "coverPictureUrl": "images_027.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false }, { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 1, "name": "Batman2", "description": "vive en una cueva mas grande", "type": "Pelicula", "alwaysAvailable": true, "ranking": null, "coverPictureUrl": "images_028.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false }, { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 3, "name": "BaRman 3", "description": "vive en una cueva mas grande", "type": "Pelicula", "alwaysAvailable": true, "ranking": 1, "coverPictureUrl": "images_029.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false }, { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 5, "name": "Los 7 magnificos", "description": "vive en una cueva", "type": "Serie", "alwaysAvailable": true, "ranking": null, "coverPictureUrl": "images_030.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false }, { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 1, "name": "Batman2", "description": "vive en una cueva mas grande", "type": "Pelicula", "alwaysAvailable": true, "ranking": 2, "coverPictureUrl": "images_031.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false }, { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 3, "name": "BaRman 3", "description": "vive en una cueva mas grande", "type": "Pelicula", "alwaysAvailable": true, "ranking": 4, "coverPictureUrl": "images_032.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false }, { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 5, "name": "Los 7 magnificos", "description": "vive en una cueva", "type": "Serie", "alwaysAvailable": true, "ranking": 3, "coverPictureUrl": "images_033.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false }, { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStartLiveOnly": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }, { "id": 5, "name": "AAALos 7 magnificos", "description": "vive en una cueva", "type": "Serie", "alwaysAvailable": true, "ranking": 5, "coverPictureUrl": "images_034.jpg", "storageUrl": "bbb.mp4", "isPayPerView": false, "mostrar": true, "actorsName": [ { "firstName": "JAck", "lastName": "Nic", "isActor": true, "isDirector": false }, { "firstName": "Kim", "lastName": "Bas", "isActor": true, "isDirector": false } ], "directorsName": [ { "firstName": "JAmes", "lastName": "Cameron", "isActor": false, "isDirector": true } ], "picture": null, "video": null, "duration": 170, "dateStart": null, "estimatedDuraction": null, "categoriesName": [], "similarContentsName": null, "featured": true, "featuredDateStart": 1509877210000, "featuredDateFinish": 1509963610000, "tenantId": null, "pathTokenVOD": null }	
			],
			token: '',
			inputBuscar:'',
			isPayPerView:false,
			isFeatured:false,
			isVOD:false,
			isLIVE:false
		}
	},
	mounted () {
		var token = localStorage.getItem("token");
		this.token = token;
		//this.contentsresult = this.contents;//comentar
		this.getContents();
		//this.buscar();
	},
	methods: {
		seleccionado (c) {
			this.selected=c
		},
		buscar() {
			//this.getContents();
			var input, filter;
			this.contentsresult = [];
			input = this.inputBuscar;
			filter = input.toUpperCase();
			for (var i = 0; i <this.contents.length; i++){
				var content = this.contents[i];
				var name; var description;
				var type; var alwaysAvailable;
				var isPayPerView; var featured;
				var coverPictureUrl; var duration;
				var actorsName; var directorsName;
				var categoriesName;	var similarContentsName
				var ranking;
				var namemin;
				var dateStartLiveOnly;
				var mostrar = true;
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
					if (key === 'description') {
						description = value;
					}
					if (key === 'type') {
						type = value;
						console.log(type);
						console.log(this.isVOD);
						if (this.isVOD  && (type !== 'Pelicula' && type !== 'Serie')) {
							mostrar = false
							console.log('mostrar false peli');
						}
						if (this.isLIVE  && (type !== 'Evento Deportivo' && type !== 'Evento Espectaculo')) {
							mostrar = false
							console.log('mostrar false edepor');
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
							console.log('mostrar false ppv');
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
							console.log('mostrar false featru');
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
				}
				
				if ((name.toUpperCase().indexOf(filter) > -1 || description.toUpperCase().indexOf(filter)> -1)
				//|| type.toUpperCase().indexOf(filter)> -1 || isPayPerView.toUpperCase() === filter
				//|| featured.toUpperCase()=== filter || alwaysAvailable.toUpperCase()=== filter){
				&& mostrar){
					mostrar = true;
				} else{
					mostrar = false;
				}
					this.contentsresult.push({
						name,namemin,description,type,isPayPerView,featured,mostrar,coverPictureUrl,duration,ranking,
						similarContentsName, categoriesName, actorsName, directorsName,alwaysAvailable,dateStartLiveOnly
						})
			}
		},
		getContents () {
			const i = this;
			/*i.$http.get(`${i.config.backend}/generator/createContent`,{
				headers: { 
					'Authorization': i.token
				}
			}).then((result) => {
				// [{"id":1,"name":"Pelicula 1",...},{"id":2,"name":"Pelicula 2",..}]	
				for (var i = 0; i < result.body.length; i++){
				var content = result.body[i];
				var name; var description;
				var type; var alwaysAvailable;
				var isPayPerView; var featured;
				var coverPictureUrl; var duration;
				var actorsName; var directorsName;
				var categoriesName;	var similarContentsName
				var ranking;
				var namemin;
				var dateStartLiveOnly;
				for (var key in content){
					var value = content[key];
					if (key === 'name') {
						name = value;
					}
					if (key === 'description') {
						description = value;
					}
					if (key === 'type') {
						type = value;
					}
					if (key === 'isPayPerView') {
						isPayPerView = value;
					}
					if (key === 'featured') {
						featured= value;
					}
					if (key === 'coverPictureUrl') {
						coverPictureUrl = value;
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
						ranking = value;
					}
				 if (key === 'alwaysAvailable') {
					 alwaysAvailable = value;
					}
				}
					this.contents.push({
						name,description,type,isPayPerView,featured,coverPictureUrl,duration,ranking,
						similarContentsName, categoriesName, actorsName, directorsName,alwaysAvailable,dateStartLiveOnly})	
				}*/
			//}).then((result) =>{
				this.buscar();
			//})
		}
	}
}
</script>
<style>
.top-right {top: 8px;right: 8px;position: absolute;text-align: right;color: red;}
.fade-enter-active,.fade-leave-active{transition:opacity .3s}.fade-enter,.fade-leave-active,.fade-leave-to{opacity:0}.jump-enter-active{animation:jump .2s}.jump-leave-active{animation:jump .2s reverse}@keyframes jump{0%{transform:translateY(10px)}50%{transform:translateY(-10px)}100%{transform:translateY(0)}}.long-form-enter-active{transition:all .2s ease}.long-form-leave-active{transition:all .5s cubic-bezier(1,.5,.8,1)}.long-form-enter,.long-form-leave-to{border-right:solid 1px #ddd!important;opacity:0;transform:scaleX(0)}.scale-up-enter-active{animation:scaleUp .2s}.scale-up-leave-active{animation:scaleUp .2s reverse}@keyframes scaleUp{0%{transform:scale3D(1,1,1)}50%{transform:scale3D(1.2,1.2,1.2)}100%{transform:scale3D(1,1,1)}}.scale-down-enter-active{animation:scaleDown .2s}.scale-down-leave-active{animation:scaleDown .2s reverse}@keyframes scaleDown{0%{transform:scale3D(1.2,1.2,1.2)}50%{transform:scale3D(.8,.8,.8)}100%{transform:scale3D(1,1,1)}}.slide-left-enter-active{animation:slideLeft .2s}.slide-left-leave-active{animation:slideLeft .2s reverse}@keyframes slideLeft{0%{transform:translate3d(30px,0,0)}70%{transform:translate3d(-5px,0,0)}100%{transform:translate3d(0,0,0)}}.slide-right-enter-active{animation:slideRight .2s}.slide-right-leave-active{animation:slideRight .2s reverse}@keyframes slideRight{0%{transform:translate3d(-30px,0,0)}70%{transform:translate3d(5px,0,0)}100%{transform:translate3d(0,0,0)}}
</style>
