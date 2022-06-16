var app = new Vue({
	el: '#app',
	data: {
		object: null,
		labela: null
	},
	mounted(){
		var location = window.location.href.toString();
		var params = location.split("?");
		var value = params[1].split("=")[1];
		value = value.replace('%20', ' ');
		this.labela = value.toString()
		axios.get(
			'rest/objects/currentObject',
			{
				params: {
					name: this.labela
				}
				
			}
		)
		.then((response) => {
			this.object = response.data;
		});
		
	},

	
});