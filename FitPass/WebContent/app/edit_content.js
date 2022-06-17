var app = new Vue({
	el: '#app',
	data:{
		content: null,
		label:null,
		name: null,
		contentType: null,
		description: null,
		duration: null
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
			this.name = this.content.name;
			this.contentType = this.content.type;
			this.description = this.content.description;
			this.duration = this.content.duration
		})
	}
})