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
		endDateSearch: null,
		startDateSearch: null,
		endDateSearch: null,
		sportObjectTypeFilter: "all",
		trainingTypeFilter: "all",
		sortIndex: null,
		sportsObject: null
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
		},
		sortTrainings(index){
			if(this.sortIndex === index){
				switch(this.sortDirection){
					case null:
					this.sortDirection = 'asc';
					break;
					case 'asc':
					this.sortDirection = 'desc';
					break;
					case 'desc':
					this.sortDirection = null;
					break;
				}
			}else{
				this.sortDirection = 'asc';
			}
			
			this.sortIndex = index;
			
			if(this.sortIndex == 1){
				if(this.sortDirection == 'asc'){
					this.customersTrainings = this.customersTrainings.sort(
						(rowA, rowB) => {
						return rowA.sportsObject.localeCompare(rowB.sportsObject)
						}
					)
				}else{
					this.cusomersTrainings = this.customersTrainings.sort(
						(rowA, rowB) => {
							return rowB.sportsObject.localeCompare(rowA.sportsObject)
						}
					)
				}
				
			}else if(this.sortIndex == 2){
				if(this.sortDirection == 'asc'){
					this.cusomersTrainings = this.customersTrainings.sort(
						(rowA, rowB) => {
						return rowA.price.toString().localeCompare(rowB.price.toString());
						}
					)
				}else{
					this.cusomersTrainings = this.customersTrainings.sort(
						(rowA, rowB) => {
						return rowB.price.toString().localeCompare(rowA.price.toString());
						}
					)
				}
				
			}
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
				//trainingDates = findDates(training.id);
				if(this.trainingTypeFilter != "all"){
					keep = keep && (training.trainingType == this.trainingTypeFilter);
				}
				let sportsObject;
				if(this.sportObjectTypeFilter != "all"){
					
					axios.get(
						'rest/objects/currentObject',
						{
							params:{
								name: training.sportsObject
							}	
						}
					)
					.then(response => {
						this.sportsObject = response.data;
						//keep = keep && (this.sportObjectTypeFilter == sportsObject.locationType);
						//if(keep == false) return keep;
					})
					keep = keep && (this.sportObjectTypeFilter == this.sportsObject.locationType);
				}
				//keep = keep && (this.sportObjectTypeFilter == sportsObject.locationType);
				
				
				return keep;
			})
			
		}
	}
})