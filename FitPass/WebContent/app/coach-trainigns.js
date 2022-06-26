const vm = new Vue({
	el: '#app',
	data: {
		currentUsername: null,
		coachTrainings: []
	},
	mounted(){
		axios.get('rest/currentUser')
			.then((response) => {
				this.currentUsername = response.data.username;
				axios.get(
				'rest/training-history/trainings/coach',
				{
					params: {
						username: this.currentUsername
					}
				}
				)		
			.then((response) => {
				this.coachTrainings = response.data;
				
			})
		})
		
	},
	methods: {
		findTraining(trainingId) {
			axios.get(
				'rest/objects/training',
				{
					params: {
						trainingId: "1"
					}
				}
				)
				.then((response) => {
					//this.dates = response.data;
					return response.data;
				})
		}
	}
})