const vm = new Vue ({
	el: '#app',
	data: {
		sportsObject: null,
		coaches: null,
		customers: null,
		contents: null
	},
	created (){
		axios.get('rest/manager/managed')
		.then((response) => {
			this.sportsObject = response.data;
			axios.get('rest/objects/content/' + this.sportsObject.name)
			.then((response) => {
				this.contents = response.data;
				this.contents.forEach(async item => {
					item.image = await this.getImage(item.image);
				});
				
			});
		});

		axios.get('rest/manager/coaches')
		.then((response) => {
			this.coaches= response.data;
		});

		axios.get('rest/manager/visited')
		.then((response) => {
			this.customers= response.data;
		});
		

	},
	methods: {
		
		
		getImage(id){
			return new Promise((resolve, reject) => {
				axios.get('rest/image/' + id)
				.then((response) => {
					resolve(response.data);
				})
			});
		}
		
		
	}
});
