const vm = new Vue({
	el: '#app',
	data: {
		customersTrainings: null,
		currentUsername: null,
		datesMap: []
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
				for(let i = 0; i < this.customersTrainings.length; i++){
					axios.get(
					'rest/training-history/find-history-for-training',
					{
						params: {
							trainingId: this.customersTrainings[i].trainingId
						}
					}
					)
					.then((response) => {
						//this.dates = response.data.toString();
						returnValue = response.data.toString();
						this.datesMap.push({id: this.customersTrainings[i].trainingId, dates:response.data});
						
					})
				}
			})
		})
		
	},
	methods: {
		findDates(id) {
			return this.datesMap.filter(function(date){
				return date.id == id;
			})
			/*
			return this.datesMap.filter(function(date){
				return date.id == id;
			})*/
		}
		
	}
})