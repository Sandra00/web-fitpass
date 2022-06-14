

window.onload = function () {
    const vm = new Vue({
    el: '#app',
    data: {
		name:null,
		objectType:null,
		works:false,
		longitude:null,
		latitude:null,
		street:null,
		number:null,
		town:null,
		postNumber:null,
		avgGrade:null,
		startTime:null,
		endTime:null,
        error:null,
        managers:[],
        contents:[]
    },
    methods: {
        async register() {
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
                    averageGrade: this.avgGrade,
                    startWorkingHour: this.startTime,
                    endWorkingHour: this.endTime
                }
            )
            .then( response =>{
                window.location.href = 'index.html';
            })
            .catch( error => {
                this.error = 'Postoji objekat sa unetim nazivom';
            })
        },
        
        getFormValues (submitEvent) {
            this.register();
        }
    }
});
}