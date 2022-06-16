var app = new Vue({
	el: '#app',
	data: {
		object: null,
		labela: null,
		type: null,
		status: null,
		location: null,
		avgGrade: null
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

	
});