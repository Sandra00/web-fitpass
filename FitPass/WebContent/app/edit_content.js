var app = new Vue({
	el: '#app',
	data:{
		content: null
	},
	mounted(){
		var location = window.location.href.toString();
		var params = location.split("?");
		var value = params[1].split("=")[1];
		value = value.replace('%20', ' ');
	}
})