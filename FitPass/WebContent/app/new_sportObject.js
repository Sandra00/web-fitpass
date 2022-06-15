

window.onload = function () {
    const vm = new Vue({
    el: '#app',
    data: {
		name:null,
		objectType:"GYM",
		works:false,
		longitude:null,
		latitude:null,
		street:null,
		number:null,
		town:null,
		postNumber:null,
		startTime:null,
		endTime:null,
        error:null,
        noChecked:null,
        managers:[],
        contents:[],
        managerValue:null
    },
    mounted(){
		axios.get('rest/freeManagers')
		.then((response) => {
			this.managers = response.data;
			//this.managerValue = this.managers[0].username;
		})
	},
    methods: {
		
        async register() {
			//hour = this.startTime.hour
			//minute = this.startTime.minute
			//stringFormat = hour.toString() + ":" + minute.toString()
			if(this.contents.length == 0){
				this.noChecked = "Morate odabrati bar jedan sadržaj";
				return;
			}
			
			await axios.post(
				"rest/addSportsObject",
				{
					username:this.managerValue,
					sportsObject: this.name
				}
			)
			
            await axios.post(
                "rest/objects/register",
                {
                    name: this.name,
                    locationType: this.objectType,
                    contentTypes: this.contents,
                    status:this.works,
                    location:{
						longitude:this.longitude,
						latitude:this.latitude,
						address:{
							street:this.street,
							number:this.number,
							town:this.town,
							zipcode:this.postNumber
						}
					},
                    startWorkingHour: this.startTime.toLocaleString(),
                    endWorkingHour: this.endTime.toLocaleString()
                }
            )
            .then( response =>{
                window.location.href = 'index.html';
            })
            .catch( error => {
                this.error = 'Postoji objekat sa unetim nazivom';
            })
            
            
        },
        
        isManagersEmpty: function(){
			if(this.managers.length == 0){
				return true;
			}else{
				return false;
			}
		},
        
        getFormValues (submitEvent) {
            this.register();
        }
    }
});
}