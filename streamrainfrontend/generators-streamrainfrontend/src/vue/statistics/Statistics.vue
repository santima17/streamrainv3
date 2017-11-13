<template>
<div class="row">
	<div class="col-md-6">
		<div class="panel-content">
			<h2 class="heading margin-bottom-50"></i> Usuarios activos</h2>
			<div id="demo-line-chart" class="ct-chart"></div>
		</div>
	</div>
	<div class="col-md-6">
		<div class="panel-content">
			<h2 class="heading margin-bottom-50"> Favoritos por Tipo</h2>
			<div id="demo-pie-chart" class="ct-chart"></div>
		</div>
	</div>
</div>
</template>

<script>
export default {
	mounted () {
		this.armarGraficas();
	},
	updated () {
		this.armarGraficas();
	},
	methods:  {
		armarGraficas () {
			var options;
			var data = {
				labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
				series: [
				[200, 380, 350, 320, 410, 450, 570, 400, 555, 620, 750, 900],
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

			// data Pie 
			var dataPie = {
				series: [5, 3, 4]
			};

			var labels = ['Peliculas', 'Series', 'Espectaculos'];
			var sum = function(a, b) {
				return a + b;
			};

			new Chartist.Pie('#demo-pie-chart', dataPie, {
				height: "270px",
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
