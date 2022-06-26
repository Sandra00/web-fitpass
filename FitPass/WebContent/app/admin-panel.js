const vm = new Vue ({
	el: '#app',
	data: {
		allUsers: [],
		promoCodes: []
	},
	mounted(){
		axios.get('rest/admin/all-users')
		.then((response) => {
			this.allUsers= response.data;
		});
		
		axios.get('rest/admin/all-promo-codes')
		.then((response) => {
			this.promoCodes= response.data;
			this.promoCodes.forEach(async item => {
				item.expirationDate = await this.formatDateTime(item.expirationDate);
			});
		});
		
		
	},
	methods: {
		
		
		formatDateTime(date){
		if(date[3] < 10){
			date[3] = '0' + date[3];
		}
		if(date[4] < 10){
			date[4] = '0' + date[4];
		}
			return date[2] + '/' + date[1] + '/' + date[0] + ' ' + date[3] + ':' + date[4];
		}
		
		
	}
});
