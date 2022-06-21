const vm = new Vue({
	el: '#app',
	data: {
		customersTrainingHistory: null,
		currentUsername: null
	},
	mounted(){
		axios.get('rest/currentUser')
			.then((response) => {
				this.currentUsername = response.data.username;
				axios.get(
				'rest/training-history/find-customer-training-history',
				{
					params: {
						username: this.currentUsername
					}
				}
				)	
			.then((response) => {
				this.customersTrainingHistory = response.data;
			})
		})
		
	}
})