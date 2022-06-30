const vm = new Vue({
	el: '#app',
	data: {
		currentUsername: null,
		managerTrainings: [],
		trainingsMap: [],
		nameSportObjectSearch: '',
		startPriceSearch: null,
		endPriceSearch: null,
		startDateSearch: new Date(2000, 1, 1),
		endDateSearch: null,
		sportObjectTypeFilter: "all",
		trainingTypeFilter: "all",
		sortIndex: null,
		sportsObject: null,
		training: null,
		price: null,
		date: null,
		trainingF: null
	},
	created(){
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
	},
	computed:{
		filteredManagerTrainings:function(){
			return this.managerTrainings.filter((trainingH) => {
				keep = true;
				return true;
			})
		}
	}
})