const vm = new Vue ({
	el: '#app',
	data: {
		sportsObject: null,
		trainers: null,
		customers: null
	},
	created (){
		axios.get('rest/objects/managed')
		.then((response) => {
			this.sportsObject= response.data;
		});

		axios.get('rest/objects/trainers')
		.then((response) => {
			this.trainers= response.data;
		});

		axios.get('rest/objects/visited')
		.then((response) => {
			this.customers= response.data;
		});

	},
	methods: {
		
	}
});
