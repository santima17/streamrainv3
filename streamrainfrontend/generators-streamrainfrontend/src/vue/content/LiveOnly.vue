<template>
<div class="container-fluid text-left" style="width:88%">
	<h2 class="heading"> Crear Contenido LIVE</h2>
	
	<form enctype="multipart/form-data" @submit.prevent="crearContenido()">

	<div id="basic-form" >
		<div class="form-group">
			<label>Nombre</label>
			<input type="text" class="form-control" required>
		</div>
	</div>

	<div class="form-group">
		<label>Descripcion</label>
		<textarea class="form-control" rows="5" cols="30" required></textarea>
	</div>

	<div class="form-group">
		<label>Tipo</label>
		<br />
		<label class="fancy-radio">
			<input type="radio" name="tipo" value="1">
			<span><i></i>Pelicula</span>
		</label>

		<label class="fancy-radio">
			<input type="radio" name="tipo" value="2">
			<span><i></i>Serie</span>
		</label>

		<label class="fancy-radio">
			<input type="radio" name="tipo" value="3">
			<span><i></i>Evento Deportivo</span>
		</label>

		<label class="fancy-radio">
			<input type="radio" name="tipo" value="4">
			<span><i></i>Evento Espectaculo</span>
		</label>
	</div>

	<div class="form-group">
            <label>Fecha y hora de inicio (LIVE)</label>
            <div class='input-group date' id='datetimepicker1'>
                <input type='text' class="form-control" />
                <span class="input-group-addon">
                    <i class="fa fa-calendar"></i></span>
                </span>
            </div>
        </div>
	<div class="form-group">
		<label>Duracion Estimada (minutos)</label>
		<input type="number" class="form-control" required>
	</div>

	<div class="form-group">
		<label for="picture" class="control-label">Picture</label>
		<input type="file" @change="subirArchivo('picture', $event.target.files)" class="input-file">
	</div>  

	<div class="form-group">
		<label>Categorias</label>
		<div class="fancy-checkbox">
			<label><input type="checkbox"><span>Cat 1</span></label>
		</div>
		<div class="fancy-checkbox">
		<label><input type="checkbox" ><span>Cat 2</span></label>
		</div>
		<div class="fancy-checkbox">
		<label><input type="checkbox"><span>Cat 3</span></label>
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
		<ul v-for="d in directores" class="list-group">
			<li class="list-group-item">
				<a class="fa fa-times" aria-hidden="true"  @click="eliminarDirector(d)"></a>
				{{d.nombre}}  {{d.apellido}}
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
		<ul v-for="a in actores" class="list-group">
			<li class="list-group-item">
				<a class="fa fa-times" aria-hidden="true"  @click="eliminarActor(a)"></a>
				{{a.nombre}}  {{a.apellido}}
			</li>
		</ul> 
	</div>

	<div class="form-group">
		<label>Pay Per View (PPV)</label>
		<br />
		<label class="fancy-radio">
			<input type="radio" name="ppv" value="1">
			<span><i></i>Es PPV</span>
		</label>
	</div>

	<div class="form-group">
		<label>Destacado</label>
		<br />
		<label class="fancy-radio">
			<input type="radio" name="destacado" value="1" >
			<span><i></i>Es destacado</span>
		</label> 
	</div>

	<div class="form-group">
		<label>Desde</label>
		<div class='input-group date' id='datetimepicker2'>
			<input type='text' class="form-control" />
			<span class="input-group-addon">
				<i class="fa fa-calendar"></i>
			</span>
		</div>
		<label>Hasta</label>
		<div class='input-group date' id='datetimepicker3'>
			<input type='text' class="form-control" />
			<span class="input-group-addon">
				<i class="fa fa-calendar"></i>
			</span>
		</div>
	</div>
	
	<div style="text-align:right">
		<button type="submit" class="btn btn-primary">Guardar</button>
	</div>

	</form>

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
			nombreDirector: '',
			apellidoDirector: '',
			nombreActor: '',
			apellidoActor: '',
			categorias: [
				{id: '1', nombre: 'cat 1'},
				{id: '2', nombre: 'cat 2'},
				{id: '3', nombre: 'cat 3'}
			],
			directores: [],
			actores: [],
			similar: [],
			formData: null
		}
	},
	mounted () {
		this.efectosForm();
	},
	updated () {
		this.efectosForm();
	},
	methods: {
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
			$('#datetimepicker1').datetimepicker();
			$('#datetimepicker2').datetimepicker();
			$('#datetimepicker3').datetimepicker();
		},
		guardarDirector: function (nombre,apellido) {
			if (nombre.trim() !== '' && apellido.trim() !== '') {
				var idd = this.directores.length + 1
				this.directores.push(
					{id: idd, nombre: nombre, apellido:apellido}
				)
				this.nombreDirector = ''
				this.apellidoDirector = ''
			} 
		},
		eliminarDirector: function (d) {
			var index = this.directores.indexOf(d)
			this.directores.splice(index, 1)
		},
		guardarActor: function (nombre,apellido) {
			if (nombre.trim() !== '' && apellido.trim() !== '') {
				var ida = this.actores.length + 1
				this.actores.push(
					{id: ida, nombre: nombre, apellido:apellido}
				)
				this.nombreActor = ''
				this.apellidoActor = ''
			} 
		},
		eliminarActor: function (a) {
			var index = this.actores.indexOf(a)
			this.actores.splice(index, 1)
		}
	}
}
</script>
