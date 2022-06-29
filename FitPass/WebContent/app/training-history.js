const vm = new Vue({
	el: '#app',
	data: {
		customersTrainings: [],
		currentUsername: null,
		datesMap: [],
		nameSportObjectSearch: '',
		startPriceSearch: null,
		endPriceSearch: null,
		startDateSearch: null,
		endDateSearch: null
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
				let maxPrice = 10000;
				if(this.endPriceSearch != null || this.endPriceSearch == ""){
					maxPrice = this.endPriceSearch;
				}
				if(maxPrice == ""){
					maxPrice = 10000;
				}
				keep = training.sportsObject.toLowerCase().match(this.nameSportObjectSearch.toLowerCase())
				&& (Number(training.price) >= Number(this.startPriceSearch))
				&& (Number(training.price) <= maxPrice); //training.price.toString().match(this.startPriceSearch);
				return keep;
			})
			
		}
	}
})