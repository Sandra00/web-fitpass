    const vm = new Vue({
    el: '#app',
    data() {
        return {
            error: "",
            coaches: null,
            name: null,
            trainingType: null,
            length: null,
            coach: null,
            description: null,
            price: 0,
            file: null
        };
    },
    created() {
		axios.get('rest/all-coaches')
		.then( response => {
			this.coaches = response.data;
		})
	},
    methods: {
	
	
        async register() {
			imageId = null;
			if(this.file){
				imageId = await uploadImage(this.file);
			}
			
            await axios.put(
                "rest/manager/add-training",
                {
					trainingId: null,
                    name: this.name,
                    trainingType: this.trainingType,
                    sportsObject: null,
                    length: this.length,
                    coach: this.coach,
                    description: this.description,
                    image: imageId,
                    price: this.price
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
					this.error = 'Postoji trening sa istim imenom!';
				}
				else {
					this.error = 'UPS! Neka nepredviđena greška: ' + error.response.status;
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