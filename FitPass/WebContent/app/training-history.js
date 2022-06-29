const vm = new Vue({
	el: '#app',
	data: {
		customersTrainings: [],
		currentUsername: null,
		datesMap: [],
		nameSportObjectSearch: '',
		priceSearch: ''
	},
	created(){
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
		
	},
	computed:{
		filteredCustomersTrainings:function(){
			
			return this.customersTrainings.filter((training) => {
				let keep = true;
				keep = training.sportsObject.toLowerCase().match(this.nameSportObjectSearch.toLowerCase())
				&& training.price.toString().match(this.priceSearch);
				return keep;
			})
			
		}
	}
})