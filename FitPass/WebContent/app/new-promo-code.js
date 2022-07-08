const vm = new Vue({
	el: '#app',
	data() {
	    return {
	        error: "",
	        code: null,
	        expirationDate: null,
	        usesLeft: null,
	        discountPercentage: null,
	        expirationTime: null
	    };
	},
	methods: {
		
		
	    async register() {
			
			dateTokens = this.expirationDate.split('-');
			year = parseInt(dateTokens[0]);
			month = parseInt(dateTokens[1]);
			day = parseInt(dateTokens[2]);
			
			timeTokens = this.expirationTime.split(':');
			hour = parseInt(timeTokens[0]);
			minute = parseInt(timeTokens[1]);
			
			date = [year, month, day, hour, minute];
			
	        await axios.post(
	            "rest/admin/new-promo-code",
	            {
	                code: this.code,
	                expirationDate: date, 
					usesLeft: this.usesLeft,
					discountPercentage: this.discountPercentage
	            }
	        )
	        .then( response =>{
	            window.location.href = 'index.html';
	        })
	        .catch( error => {
				if(error.response.status == 401){
	            	this.error = 'Ti nisi admin, kako si uopšte došao ovde?';
	        	}
	        	else if (error.response.status == 400){
					this.error = 'Dati promo kod već postoji!!';
				}
				else {
					this.error = 'UPS! Nepredviđena greška: ' + error.response.status;
				}
	        })
	    },
	    
	    
	    getFormValues (submitEvent) {
	        this.register();
	    }
	    
	    
	}
});

