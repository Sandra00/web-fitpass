const vm = new Vue({
	el: '#app',
	data: {
		currentUsername: null,
		coachTrainings: [],
		trainingsMap: []
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
				for(let i = 0; i < this.coachTrainings.length; i++){
					axios.get(
						'rest/objects/training',
						{
							params:{
								trainingId: this.coachTrainings[i].trainingId
							}
						}
					)
					.then((response) => {
						
						let add = true;
						for(let i = 0; i < this.trainingsMap.length; i++){
							if(this.trainingsMap[i].id == response.data.trainingId){
								add = false;
							}
						}
						if(add){
							this.trainingsMap.push({id: this.coachTrainings[i].trainingId, training:response.data})
						}
						
						//this.trainingsMap.push({id: this.coachTrainings[i].trainingId, training:response.data})
					})
					
				}
			})
		})
		
	},
	methods: {
		findTraining(id) {
			return this.trainingsMap.filter(function(trainingId){
				return trainingId.id == id;
			})
			/*
			axios.get(
				'rest/objects/training',
				{
					params: {
						trainingId: id
					}
				}
				)
				.then((response) => {
					//this.dates = response.data;
					return response.data;
				})
				*/
		}
	}
})