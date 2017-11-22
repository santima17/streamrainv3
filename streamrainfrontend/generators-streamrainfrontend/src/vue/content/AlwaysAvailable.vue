<template>
<div class="container-fluid text-left" style="width:95%">	
	<form enctype="multipart/form-data" @submit.prevent="crearContenido()">

	<div id="basic-form" >
		<div class="form-group">
			<label>Nombre</label>
			<input type="text" class="form-control" v-model="name" required>
		</div>
	</div>

	<div class="form-group">
		<label>Descripcion</label>
		<textarea class="form-control" rows="5" cols="30" v-model="description" required></textarea>
	</div>

	<div class="form-group">
		<label>Tipo</label>
		<br />
		<label class="fancy-radio">
			<input type="radio" name="tipo" value="1" v-model="type">
			<span><i></i>Pelicula</span>
		</label>

		<label class="fancy-radio">
			<input type="radio" name="tipo" value="2" v-model="type">
			<span><i></i>Serie</span>
		</label>


	</div>

	<div class="form-group">
		<label>Duracion (minutos)</label>
		<input type="number" class="form-control" v-model="duration" required>
	</div>

	<div class="form-group">
		<label for="picture" class="control-label">Picture</label>
		<input type="file" @change="subirArchivo('picture', $event.target.files)" class="input-file">
	</div>  

	
	<div class="form-group">
		<label for="video" class="control-label">Video</label>
		<input type="file" @change="subirArchivo('video', $event.target.files)" class="input-file" >
	</div>
	
	<div class="form-group">
		<label>Categorias</label>
		<div v-for = "c in categorias">
		<label  class="fancy-checkbox">
			<input type="checkbox" :value="c.id" v-model="idCategories">
			<span> {{ c.name }} </span>
		</label>
		</div>
	</div>

	<div class="form-group">
		<label for="directores" class="control-label">Directores</label>
		<br>
		<input type="text"  v-model="nombreDirector"  placeholder="Nombre">
		<input type="text"  v-model="apellidoDirector"  placeholder="Apellido">
		<button type="button" @click="guardarDirector(nombreDirector,apellidoDirector)"> Agregar </button>
	</div>

	<div class="form-group">
		<ul v-for="d in directors" class="list-group">
			<li class="list-group-item">
				<a class="fa fa-times" aria-hidden="true"  @click="eliminarDirector(d)"></a>
				{{d.firstName}}  {{d.lastName}}
			</li>
		</ul> 
	</div>

	<div class="form-group">
		<label for="actores" class="control-label">Actores</label>
		<br>
		<input type="text"   v-model="nombreActor"  placeholder="Nombre">
		<input type="text"  v-model="apellidoActor"  placeholder="Apellido">
		<button type="button" @click="guardarActor(nombreActor,apellidoActor)"> Agregar </button>
	</div>

	<div class="form-group">
		<ul v-for="a in actors" class="list-group">
			<li class="list-group-item">
				<a class="fa fa-times" aria-hidden="true"  @click="eliminarActor(a)"></a>
				{{a.firstName}}  {{a.lastName}}
			</li>
		</ul> 
	</div>

	<div class="form-group">
		<label>Contenidos Similares</label>
		<multi-select :options="options"
			:selected-options="idSimilarContents"
			placeholder="seleccionar contenidos similares"
			@select="onSelect">
		</multi-select>
	</div>		

<div class="form-group">
<label class="fancy-checkbox">
	<input type="checkbox" v-model="isPayPerView">
	<span>Pay Per View (PPV)</span>
</label>
</div>

	<div class="form-group">
<label class="fancy-checkbox">
	<input type="checkbox" v-model="featured">
	<span>Destacado</span>
</label>
</div>


	<div v-if="featured">

		<label for="start-picker" class="col-sm-3 control-label">Desde: </label>
		<div class='input-group date'>
			<datetime v-model="featuredDateStart" name="start-picker"
				type="datetime"
				input-format="DD-MM-YYYY HH:mm"
				placeholder=""
				locale="es"
				required ></datetime>
		</div>

<br>

		<label for="end-picker" class="col-sm-3 control-label">	Hasta: </label>
		<div class='input-group date'>
			
			<datetime v-model="featuredDateFinish" name="end-picker" 
			type="datetime"
			input-format="DD-MM-YYYY HH:mm"
			placeholder=""
			locale="es"
			required ></datetime>
    	</div>
	</div>
	

	<div style="text-align:right">
		<button type="submit" class="btn btn-primary">Guardar VOD</button>
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
    'eventBus'
  ],
	data () {
		return {
			// json datos
			name:'',
			description: '',
			type: '',
			duration: 0,
			idCategories: [],
			directors: [],
			actors: [],
			idSimilarContents: [],
			idSimilarContentsIDS: [],
			isPayPerView: false,
			featured: false,
			featuredDateStart: '',  
			featuredDateFinish: '',
			//picture, video
			formData: new FormData(),

			// datos auxiliares
			categorias: [],
			nombreDirector: '',
			apellidoDirector: '',
			nombreActor: '',
			apellidoActor: '',
			options: [],
			searchText: '', 
			lastSelectItem: {},

			token: ''
		}
	},
	created () {
			var token = localStorage.getItem("token");
    		this.token = token;
			this.getCategories();
			this.getContents();
    },
	mounted () {
		//this.efectosForm();
	},
	updated () {
		//this.efectosForm();
	},
	methods: {
		getContents () {
			const i = this;
			i.$http.get(`${i.config.backend}/generator/createContent`,
			{
				headers: { 
					'Authorization': i.token
					}
			}).then((result) => {
				// [{"id":1,"name":"Pelicula 1",...},{"id":2,"name":"Pelicula 2",..}]	
			for (var i = 0; i < result.body.length; i++){
						var content = result.body[i];
						var idC;
						var nameC;
						for (var key in content){
							var value = content[key];
							if (key === 'id') {
									idC = value;
							}
							if (key === 'name') {
									nameC = value;
							}
						}
						this.options.push({value: idC, text: nameC})	
				}
			})
		},		
		getCategories () {
			const i = this;
			i.$http.get(`${i.config.backend}/generator/category`,
			{
				headers: {
					'Authorization': i.token
				}
			}
			)
			.then((result) => {
				// [{"id":1,"name":"Terror","description":"Me asusto...","coverPictureUrl":null},{"id":2,"name":"Futbol","description":"Copa Mundial de Rusia en vivo","coverPictureUrl":null}]	
			for (var i = 0; i < result.body.length; i++){
						var cat = result.body[i];
						var idC;
						var nameC;
						for (var key in cat){
							var value = cat[key];
							if (key === 'id') {
									idC = value;
							}
							if (key === 'name') {
									nameC = value;
							}
						}
						this.categorias.push({id: idC, name: nameC})
				}
			})
		},
		onSelect (idSimilarContents, lastSelectItem) {
        	this.idSimilarContents = idSimilarContents
        	this.lastSelectItem = lastSelectItem
      	},
      // deselect option
      reset () {
        this.idSimilarContents = [] // reset
      },
      // select option from parent component
      selectOption () {
        this.idSimilarContents = _.unionWith(this.idSimilarContents, [this.options[0]], _.isEqual)
      },
		subirArchivo(fieldName, fileList) {	    
        	this.formData.append(fieldName, fileList[0], fileList[0].name);
      	},
		crearContenido() {

			for (var i = 0; i < this.idSimilarContents.length; i++){
						var idsc = this.idSimilarContents[i];
						var idc;
						for (var key in idsc){
							var id = idsc[key];
							if (key === 'value') {
									this.idSimilarContentsIDS.push(id)
							}
						}
						
				}

			const datos = JSON.parse('{'
					+`"name":"${this.name}",`
					+`"description":"${this.description}",`
					+`"type":"${this.type}",`
					+`"duration":${this.duration},`
					+`"idCategories":${JSON.stringify(this.idCategories)},`
					+`"directors":${JSON.stringify(this.directors)},`
					+`"actors":${JSON.stringify(this.actors)},`
					+`"idSimilarContents":${JSON.stringify(this.idSimilarContentsIDS)},`
					+`"isPayPerView":${this.isPayPerView},`
					+`"featured":${this.featured},`
					+`"featuredDateStart":"${this.featuredDateStart}",`
					+`"featuredDateFinish":"${this.featuredDateFinish}"`
					+'}');
					console.log(datos);
			this.formData.append('datos', new Blob([JSON.stringify(datos)],{type: "application/json"}));
			const url = `${this.config.backend}/generator/createContent`;
			this.$http.post(url, this.formData,
				{
				headers: {
					Authorization : this.token
					}
				}).then((response) => {	
					// exito
					//mostrar resultado
					// ir a home o crear nuevo
					this.$router.push("/");
				}).catch((error) => {						
					// error
					//for (var item of this.formData.entries()) {
					//		console.log(item[0]+ ', ' +item[1]); 
					//}	
			});
		},
		guardarDirector: function (nombre,apellido) {
			if (nombre.trim() !== '' && apellido.trim() !== '') {
				var idd = this.directors.length + 1
				this.directors.push(
					{firstName:nombre, lastName:apellido, isActor:false, isDirector:true}
				)
				this.nombreDirector = ''
				this.apellidoDirector = ''
			} 
		},
		eliminarDirector: function (d) {
			var index = this.directors.indexOf(d)
			this.directors.splice(index, 1)
		},
		guardarActor: function (nombre,apellido) {
			if (nombre.trim() !== '' && apellido.trim() !== '') {
				var ida = this.actors.length + 1
				this.actors.push(
					{firstName:nombre, lastName:apellido, isActor:true, isDirector:false}
				)
				this.nombreActor = ''
				this.apellidoActor = ''
			} 
		},
		eliminarActor: function (a) {
			var index = this.actors.indexOf(a)
			this.actors.splice(index, 1)
		}
	}
}
</script>
