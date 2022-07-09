var app = new Vue({
	el: '#app',
	data:{
		content: null,
		label:null,
		name: null,
		contentType: null,
		description: null,
		duration: null,
		file: null,
		error: null
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
	},
	methods: {
		async editContent(){
			imageId = null;
			if(this.file){
				imageId = await uploadImage(this.file);
			}
			
			axios.post(
				"rest/manager/edit-content",
				{
					oldName: this.content.oldName,
					name: this.name,
					type: this.contentType,
					image: imageId,
					description: this.description,
					duration: this.duration
				}
			)
			.then(response => {
				this.error = "Sadržaj je uspešno izmenjen";
				window.location.href = 'manager_sports_object.html';
			})
			.catch(error => {
				this.error = "Postoji sadržaj sa istim nazivom";
			})
		},
		async handleFileUpload(event){
			this.file = await convertBase64(event.target.files[0]);
    	},
		getFormValues (submitEvent) {
            this.editContent();
        }
	}
})