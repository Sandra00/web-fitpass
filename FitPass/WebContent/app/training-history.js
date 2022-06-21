const vm = new Vue({
	el: '#app',
	data: {
		customersTrainingHistory: null,
		currentUser = null
	},
	mounted(){
		axios.get('rest/currentUser')
		.then((response) => {
			this.currentUser = response.data
		})
		xios.get(
			'rest/training-history/find-customer-training-history',
			{
				params: {
					user: this.currentUser
				}
			}
		)
		.then((response) => {
			this.customersTraininghistory = response.data;
		})
	}
})