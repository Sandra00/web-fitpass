const vm = new Vue({
	el: '#app',
	data: {
		customersTrainings: null,
		currentUsername: null,
		dates: []
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
				this.customersTrainings = response.data;
				
			})
		})
		
	},
	methods: {
		findDates(id) {
			axios.get(
				'rest/training-history/find-history-for-training',
				{
					params: {
						trainingId: id
					}
				}
				)
				.then((response) => {
					this.dates = response.data;
					return response.data;
				})
		}
	}
})