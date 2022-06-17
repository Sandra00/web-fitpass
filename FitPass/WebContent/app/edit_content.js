var app = new Vue({
	el: '#app',
	data:{
		content: null,
		label:null
	},
	mounted(){
		var location = window.location.href.toString();
		var params = location.split("?");
		var value = params[1].split("=")[1];
		value = value.replace('%20', ' ');
		this.label = value.toString();
		axios.get(
			'rest/objects/currentContent',
			{
				params: {
					name: this.label
				}
			}
		)
		.then((response) => {
			this.content = response.data;
		})
	}
})