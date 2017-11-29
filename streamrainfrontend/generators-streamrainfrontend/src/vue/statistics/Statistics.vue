<template>
<div class="container-fluid text-left" style="width:88%">
	
	<div class="panel-content" >
	<h3 class="heading margin-bottom-50"></i>Top 10 por Ranking</h3>
		<table class="table table-inverse">
		<thead>
			<tr>
			<th>#</th>
			<th>Nombre</th>
			<th>Tipo</th>
			<th>Ranking</th>
			</tr>
		</thead>
		<tbody>
		
			<tr v-for="c in contents">
			<th scope="row">{{c.pos}}</th>
			<td>{{c.name}}</td>
			<td>{{c.type}}</td>
			<td>
				<span v-for="r in 5">
							<i class="fa fa-star" aria-hidden="true" style="color:yellow;" v-if="r <= c.rank"></i>
							<i class="fa fa-star-o" aria-hidden="true" v-else></i>
				</span>
			</td>
			</tr>

		</tbody>	
		</table>
		</div>
	
		<div class="panel-content">
			<h3 class="heading margin-bottom-50">Usuarios por pais</h3>
			<div id="demo-pie-chart" class="ct-chart"></div>
		</div>
	

		<div class="panel-content" >
			<h3 class="heading margin-bottom-50"></i>Cantidad de Favoritos por Contenido</h3>
			<div id="demo-line-chart" class="ct-chart" style="height:300px"></div>
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
			labelUserByCountry: [],
			seriesUserByCountry: [],
			labelgetFavByMovie: [],
			seriesgetFavByMovie: [],
			contents: []
		}
	},
	created () {
		this.getUserByCountry ()
	},
	methods:  {
		/*getContentByID (contentID,callback) {
			const i = this; 
			i.$http.get(`${i.config.backend}/generator/createContent/${contentID}`,{
				headers: { 	'Authorization': this.session.token}
			}).then((result) => {	
				for (var index = 0; index < result.body.length; index++){ //result.body.length;
					var ubc = result.body[index]; //result.body[index];//
					var name; var type;
					for (var key in ubc){
						var value = ubc[key];
						if (key === 'name') {
							name = value;
						}
						if (key === 'type') {
							type = value;
						}
          			}
					this.name = name;
					this.type = type
					return callback (name,type)
				}
			})
		},*/
		getUserByCountry () {
			const i = this; 
			i.$http.get(`${i.config.backend}/report/getUserByCountry`,{
				headers: { 	'Authorization': this.session.token}
			}).then((result) => {	
				for (var index = 0; index < result.body.length; index++){ //result.body.length;
					var ubc = result.body[index]; //result.body[index];//
					var country; var users;
					for (var key in ubc){
						var value = ubc[key];
						if (key === 'country') {
							country = value;
						}
						if (key === 'cant') {
							users = value;
						}
          			}
					this.labelUserByCountry.push(country);
					this.seriesUserByCountry.push(users)
				}
			}).then(() =>{
					this.getFavByMovie ()
			})
		},	
		getFavByMovie () {
			const i = this; 
			i.$http.post(`${i.config.backend}/report/getFavByMovie`,{
				headers: { 	'Authorization': this.session.token}
			}).then((result) => {	
				for (var index = 0; index < result.body.length; index++){ //result.body.length;
					var ubc = result.body[index]; //result.body[index];//
					var country; var cant;
					for (var key in ubc){
						var value = ubc[key];
						if (key === 'country') {
							country = value;
						}
						if (key === 'cant') {
							cant = value;
						}
          			}
					this.labelgetFavByMovie.push(country);
					this.seriesgetFavByMovie.push(cant)
				}
			}).then(() =>{
					this.getTopFive ()
			})
		},
		getTopFive () {
			const i = this; 
			i.$http.get(`${i.config.backend}/report/getTopFive`,{
				headers: { 	'Authorization': this.session.token}
			}).then((result) => {	
				for (var index = 0; index < result.body.length; index++){ //result.body.length;
					var ubc = result.body[index]; //result.body[index];//
					var rank;	var name; var type;
					var pos;
					for (var key in ubc){
						var value = ubc[key];
						if (key === 'name') {
							name = value;
						}
						if (key === 'rank') {
							rank = value;
						}
						if (key === 'type') {
							type = value;
						}
					  }
					pos = index + 1;
					if (pos === 10) {
							index = result.body.length;
					}
					this.contents.push({pos:pos,name:name,type:type,rank:rank});
					//this.seriesgetTopFive.push(rank)
				
				}
			}).then(() =>{
					this.armarGraficas ()
			})
		},
		armarGraficas () {
			/*var options;
			var data = {
				labels: this.labelUserByCountry,// ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
				series: [
					this.seriesUserByCountry,
				//[200, 380, 350, 320, 410, 450, 570, 400, 555, 620, 750, 900],
				]
			};
			// line chart
			options = {
				height: "300px",
				showPoint: true,
				axisX: {
					showGrid: false
				},
				lineSmooth: false,
				plugins: [
					Chartist.plugins.tooltip({
						appendToBody: true
					}),
				]
			};
			new Chartist.Line('#demo-line-chart', data, options);
*/
			// data Pie 

			var dataPie = {
				series: this.seriesUserByCountry.slice()//
			};

			var labels = this.labelUserByCountry.slice();//['Peliculas', 'Series', 'Espectaculos','cosas'];
			var sum = function(a, b) {
				return a + b;
			};

			new Chartist.Pie('#demo-pie-chart', dataPie, {
				height: "300px",
				chartPadding: 10,
				labelOffset: 50,
				labelDirection: 'explode',
				labelInterpolationFnc: function(value, idx) {
					var percentage = Math.round(value / dataPie.series.reduce(sum) * 100) + '%';
					return labels[idx] + ' (' + percentage + ')';
				}
			});

			new Chartist.Bar('#demo-line-chart', {
			labels: this.labelgetFavByMovie.slice(),//['XS', 'S', 'M', 'L', 'XL', 'XXL', 'XXXL'],
			series: this.seriesgetFavByMovie.slice(),//[20, 60, 120, 200, 180, 20, 10]
			}, {
			distributeSeries: true
			});
			
		}			
	}
}
</script>

<style>
</style>
