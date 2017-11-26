<template>
<div class="container-fluid text-left" style="width:88%">
<div ref="alert_message" class="alert alert-success" v-if="message!==''">
{{message}}
</div>
</div>
</template>

<script>
export default {
	props: [
	'config',
	'eventBus',
	'session',
	'message'
	],
	beforeDestroy () {
		this.eventBus.$emit('updateMessage', '');
	}
}
</script>

<style>
.alert-success {
color: #5A5A5A;
background-color: #f7f9fc;
border-color: #ddd;
padding: 20px;
font-size: 20px;
text-align: center
}
</style>
