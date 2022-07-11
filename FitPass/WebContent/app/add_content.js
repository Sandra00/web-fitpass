const vm = new Vue({
	el: '#app',
	data() {
	    return {
	        error: "",
	        file: null,
	        name: null,
	        type: null,
	        content: null,
	        duration: null,
	        description: null
	    };
	},
	methods: {
	    async register() {
			if(!this.type){
				this.error = "Molimo Vas unesite tip sadržaja!";
				return ;
			}
			
			imageId = null;
			if(this.file){
				imageId = await uploadImage(this.file);
			}
			
	        await axios.put(
	            "rest/manager/add-content",
	            {
	                name: this.name,
	                type: this.type,
	                image: imageId,
	                description: this.description,
	                duration: this.duration
	            }
	        )
	        .then( response =>{
	            window.location.href = 'manager_sports_object.html';
	        })
	        .catch( error => {
				if(error.response.status == 401){
	            	this.error = 'Ti nisi menadžer, kako si uopšte došao ovde?';
	        	}
	        	else if (error.response.status == 400){
					this.error = 'Sadržaj sa istim imenom već postoji!';
				}
				else {
					this.error = 'UPS! Nepredviđena greška: ' + error.response.status;
				}
	        })
	    },
		async handleFileUpload(event){
			this.file = await convertBase64(event.target.files[0]);
    	},
	    getFormValues (submitEvent) {
	        this.register();
	    }
	}
});

