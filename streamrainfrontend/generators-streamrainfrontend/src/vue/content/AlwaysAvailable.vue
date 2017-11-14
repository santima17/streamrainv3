<template>
<div class="container-fluid text-left" style="width:88%">
	<h2 class="heading"> Crear Contenido VOD</h2>
	
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

		<label class="fancy-radio">
			<input type="radio" name="tipo" value="3" v-model="type">
			<span><i></i>Evento Deportivo</span>
		</label>

		<label class="fancy-radio">
			<input type="radio" name="tipo" value="4" v-model="type">
			<span><i></i>Evento Espectaculo</span>
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
		<input type="file" @change="subirArchivo('video', $event.target.files)" class="input-file">
	</div>

	<div class="form-group">
		<label>Categorias</label>
		<div v-for = "c in categorias">
			<label><input type="checkbox" :value="c.id" v-model="idCategories"><span> {{ c.name }}</span></label>
		</div>
	</div>

	<div class="form-group">
		<label for="directores" class="control-label">Directores</label>
		<br>
		<input type="text"  v-model="nombreDirector"  placeholder="Nombre">
		<input type="text"  v-model="apellidoDirector"  placeholder="Apellido">
		<button  @click="guardarDirector(nombreDirector,apellidoDirector)"> Agregar </button>
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
		<button  @click="guardarActor(nombreActor,apellidoActor)"> Agregar </button>
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
		<label>Pay Per View (PPV)  </label>
		<input type="checkbox" v-model="isPayPerView">
	</div>

	<div class="form-group">
		<label>Destacado </label>
		<input type="checkbox"  v-model="featured" >
	</div>

	<div class="form-group" v-if="featured">
		<label>Desde</label>
		<div class='input-group date' id='featuredDateStart'>
			<input type='text' class="form-control" v-model="featuredDateStart" />
			<span class="input-group-addon">
				<i class="fa fa-calendar"></i>
			</span>
		</div>
		<label>Hasta</label>
		<div class='input-group date' id='featuredDateFinish'>
			<input type='text' class="form-control" :value="featuredDateFinish" />
			<span class="input-group-addon">
				<i class="fa fa-calendar"></i>
			</span>
		</div>
	</div>
	
		name {{name}} <br><br>
		description	{{description}} <br><br>
		type	{{type}} <br><br>
		duration	{{duration}} <br><br>
		idCategories	{{idCategories}} <br><br>
    directors  {{directors}} <br><br>
    actors  {{actors}} <br><br>
     idSimilarContents {{idSimilarContents}} <br><br>
     isPayPerView {{isPayPerView}} <br><br>
     featured {{featured}} <br><br>
    featuredDateStart  {{featuredDateStart}} <br><br>
		featuredDateFinish	{{featuredDateFinish}} <br><br>


	<div style="text-align:right">
		<button type="submit" class="btn btn-primary">Guardar</button>
	</div>

	</form>

</div>
</template>

<script>
import _ from 'lodash'
import { MultiSelect } from 'vue-search-select'
export default {
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
      isPayPerView: false,
      featured: false,
      featuredDateStart: new Date(),  
			featuredDateFinish: new Date(),
			//formatoFecha: {
      //    format: 'YYYY-MM-DDTHH:MM:SS',
      //    useCurrent: false,
      //  },
			//picture, video
			formData: null,

			// datos auxiliares
			categorias: [],
			nombreDirector: '',
			apellidoDirector: '',
			nombreActor: '',
			apellidoActor: '',
			options: [],
			searchText: '', 
			lastSelectItem: {}
		}
	},
	created () {
			this.getCategories();
			this.getContents();
    },
	mounted () {
		this.efectosForm();
	},
	updated () {
		this.efectosForm();
	},
	methods: {
		getContents () {
			const i = this;
			i.$http.get(`${i.config.backendPOSTA}/generator/createContent`,
			{
				headers: {
					'Authorization': i.token
				}
			}
			)
			.then((result) => {
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
			i.$http.get(`${i.config.backendPOSTA}/generator/category`,
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
				if (this.formData === null ) {
					this.formData = new FormData();
				}		    
        this.formData.append(fieldName, fileList[0], fileList[0].name);
      	},
		crearContenido() {
			const datos = JSON.parse('{'
					+'"name":"Batman2",'
					+'"description":"vive en una cueva mas grande",'
					+'"type":"1",'
					+'"duration":170,'
					+'"idCategories":[1,2],'
					+'"directors": [{"firstName": "JAmes", "lastName": "Cameron", "isActor":false, "isDirector":true}],'
					+'"actors": [{"firstName": "JAck", "lastName": "Nic", "isActor":true, "isDirector":false},{"firstName": "Kim", "lastName": "Bas", "isActor":true, "isDirector":false}],'
					+'"idSimilarContents": [1,2],'
					+'"isPayPerView":false,'
					+'"featured": true,'
					+'"featuredDateStart":"2017-11-05T10:20:10",'
					+'"featuredDateFinish":"2017-11-06T10:20:10"'
					+'}');
			this.formData.append('datos', new Blob([JSON.stringify(datos)],{type: "application/json"}));
			const url = `${this.config.backendPOSTA}/generator/createContent`;
			this.$http.post(url, this.formData,
				{
				headers: {
					Authorization : 'Barer TOKEN:eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2dHYiLCJleHAiOjE1MTAyNzQwMjh9.thCY9Ik_dkfb3GdxcGnsUKZSkSgT5Ry-gdi-CWi668dDbqaEM6qBWhFAh2rxY5npNmUWN7jNgnPGzsmiJAOPrQ'
					}
				}).then((response) => {	
					// exito
				}).catch((error) => {						
					// error
					for (var item of this.formData.entries()) {
							console.log(item[0]+ ', ' +item[1]); 
					}	
			});
		},
		efectosForm: function () {
			$('#featuredDateStart').datetimepicker();
			$('#featuredDateFinish').datetimepicker();
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
	},
	components: {
     MultiSelect
  }
}
</script>
