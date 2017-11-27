<template>
<div class="container-fluid text-left" style="width:95%">
	
		<!--<div class="panel-content">
			<h2 class="heading margin-bottom-50"></i>Usuarios por pais</h2>
			<div id="demo-line-chart" class="ct-chart"></div>
		</div>  -->
	
		<div class="panel-content">
			<h2 class="heading margin-bottom-50">Usuarios por pais</h2>
			<div id="demo-pie-chart" class="ct-chart"></div>
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
			seriesUserByCountry: []
		}
	},
	created () {
		this.getUserByCountry ()
	},
	methods:  {
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
				height: "380px",
				chartPadding: 10,
				labelOffset: 50,
				labelDirection: 'explode',
				labelInterpolationFnc: function(value, idx) {
					var percentage = Math.round(value / dataPie.series.reduce(sum) * 100) + '%';
					return labels[idx] + ' (' + percentage + ')';
				}
			});
			
		}			
	}
}
</script>

<style>
</style>
