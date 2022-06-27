const vm = new Vue({
	el: '#app',
	data: {
		currentUsername: null,
		managerTrainings: []
	},
	mounted(){
		axios.get('rest/currentUser')
		.then((response) => {
			this.currentUsername = response.data.username;
			axios.get(
				'rest/objects/training-history-manager',
				{
					params: {
						objectName: response.data.sportsObject
					}
				}
			)
			.then((response) => {
				this.managerTrainings = response.data;
			})
		})
	}
})