const vm = new Vue ({
	el: '#app',
	data: {
		sportsObject: null,
		coaches: null,
		customers: null,
		contents: null
	},
	created (){
		axios.get('rest/objects/managed')
		.then((response) => {
			this.sportsObject= response.data;
			axios.get('rest/objects/content/' + this.sportsObject.name)
			.then((response) => {
				this.contents= response.data;
			});
		});

		axios.get('rest/objects/coaches')
		.then((response) => {
			this.coaches= response.data;
		});

		axios.get('rest/objects/visited')
		.then((response) => {
			this.customers= response.data;
		});
		

	},
	methods: {
		
	}
});
