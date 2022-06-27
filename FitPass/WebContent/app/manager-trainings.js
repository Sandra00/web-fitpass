const vm = new Vue({
	el: '#app',
	data: {
		currentUsername: null,
		managerTrainings: [],
		trainingsMap: []
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
				for(let i = 0; i < this.managerTrainings.length; i++){
					axios.get(
						'rest/objects/training',
						{
							params:{
								trainingId: this.managerTrainings[i].trainingId
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
							this.trainingsMap.push({id: this.managerTrainings[i].trainingId, training:response.data})
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
		}
	}
})