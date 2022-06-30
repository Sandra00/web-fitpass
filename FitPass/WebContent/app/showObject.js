var app = new Vue({
	el: '#app',
	data: {
		object: null,
		labela: null,
		type: null,
		status: null,
		location: null,
		avgGrade: null,
		trainings: null,
		isCustomer: false,
		error: ''
	},
	mounted(){
		var location = window.location.href.toString();
		var params = location.split("?");
		var value = params[1].split("=")[1];
		value = value.replace('%20', ' ');
		this.labela = value.toString()
		
		
		axios.get('rest/currentUser')
		.then((response) => {
			if(response.data.userType == 'CUSTOMER'){
				this.isCustomer = true;
			}
		});
		
		axios.get('rest/objects/trainings/' + this.labela)
		.then((response) => {
			this.trainings = response.data;
		});
		
		
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
			if(this.object.locationType == "GYM"){
				this.type = "teretana";
			}else if (this.object.locationTtype == "POOL"){
				this.type = "bazen";
			}else if(this.object.locationType == "DANCESTUDIO"){
				this.type = "plesni studio";
			}else{
				this.type = "sportski centar";
			}
			
			if(this.object.status == true){
				this.status = "radi";
			}else{
				this.status = "ne radi";
			}
			
			this.location = this.object.location.address.street + " "
				+ this.object.location.address.number + ", "
				+ this.object.location.address.zipcode + " "
				+ this.object.location.address.town
			
			if(this.object.gradesCounter == 0){
				this.avgGrade = "/";
			}else{
				this.avgGrade = this.object.averageGrade;
			}
		});
		
	},
	methods: {
		
		async activate(trainingId) {
			await axios.put(
	            'rest/customer/check-in?sportsObjectName=' + this.labela + '&trainingId=' + trainingId, {}
	        )
	        .then( response =>{
	            window.location.href = 'index.html';
	        })
	        .catch( error => {
				if(error.response.status == 401){
	            	this.error = 'Ti nisi kupac, kako si uopšte došao ovde?';
	        	}
	        	else if (error.response.status == 405){
					this.error = 'Nazalost nismo mogli da te prijavimo!';
				}
				else {
					this.error = 'UPS! Nepredviđena greška: ' + error.response.status;
				}
	        })
		}
		
	}
	
});