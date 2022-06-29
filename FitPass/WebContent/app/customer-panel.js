const vm = new Vue ({
	el: '#app',
	data: {
		memberships: []
	},
	mounted(){
		
		axios.get('rest/memberships')
		.then((response) => {
			this.memberships= response.data;
			/*this.memberships.forEach(async item => {
				item.expirationDate = await this.formatDateTime(item.expirationDate);
			});*/
		});
		
		
	},
	methods: {
		
		
	}
});
