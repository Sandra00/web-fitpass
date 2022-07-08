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
			
			if(this.sortIndex == 3){
				if(this.sortDirection == 'asc'){
					this.managerTrainings = this.managerTrainings.sort(
						(rowA, rowB) => {
							var a;
							var b;
							for(let i = 0; i < this.trainingsMap.length; i++){
								if(this.trainingsMap[i].id == rowA.trainingId){
									a = this.trainingsMap[i].training.price;
								}
								if(this.trainingsMap[i].id == rowB.trainingId){
									b = this.trainingsMap[i].training.price;
								}
							}
						return a.toString().localeCompare(b.toString())
						}
					)
				}else{
					this.managerTrainings = this.managerTrainings.sort(
						(rowA, rowB) => {
							var a;
							var b;
							for(let i = 0; i < this.trainingsMap.length; i++){
								if(this.trainingsMap[i].id == rowA.trainingId){
									a = this.trainingsMap[i].training.price;
								}
								if(this.trainingsMap[i].id == rowB.trainingId){
									b = this.trainingsMap[i].training.price;
								}
							}
						return b.toString().localeCompare(a.toString())
						}
					)
				}
				
			}else{
				if(this.sortDirection == 'asc'){
					this.managerTrainings = this.managerTrainings.sort(
						(rowA, rowB) => {
							var a;
							var b;
							a = rowA.startDate;
							b = rowB.startDate;
						return a.toString().localeCompare(b.toString())
						}
					)
				}else{
					this.managerTrainings = this.managerTrainings.sort(
						(rowA, rowB) => {
							var a;
							var b;
							a = rowA.startDate;
							b = rowB.startDate;
						return b.toString().localeCompare(a.toString())
						}
					)
				}
			}
		}
	},
	computed:{
		filteredManagerTrainings:function(){
			return this.managerTrainings.filter((trainingH) => {
				let keep = true;
				let maxPrice = 10000;
				if(this.endPriceSearch != null || this.endPriceSearch == ""){
					maxPrice = this.endPriceSearch;
				}
				if(maxPrice == ""){
					maxPrice = 10000;
				}
				for(let i = 0; i < this.trainingsMap.length; ++i){
						if(this.trainingsMap[i].id == trainingH.trainingId){
							this.training = this.trainingsMap[i].training;
							this.price = this.trainingsMap[i].training.price;
							var yearTraining = trainingH.startDate[0];
							var monthTraining = trainingH.startDate[1];
							var dayTraining = trainingH.startDate[2];
							this.date = new Date(yearTraining, monthTraining, dayTraining);
							
							if(this.startDateSearch != null){
								var d = new Date(this.startDateSearch);
								startDate = new Date(d.getFullYear(), d.getMonth() + 1, d.getDate()); 
							}else{
								startDate = new Date(2000, 1, 1);
							}
							
							if(this.endDateSearch != null){
								var d = new Date(this.endDateSearch);
								endDate = new Date(d.getFullYear(), d.getMonth() + 1, d.getDate()); 
							}else{
								endDate = new Date(2100, 1, 1);
							}
							
							keep = keep && (Number(this.training.price) >= Number(this.startPriceSearch))
								 && (Number(this.training.price) <= maxPrice)
								 && this.date >= startDate
								 && this.date <= endDate;
							if(this.trainingTypeFilter != "all"){
								keep = keep && (this.trainingTypeFilter == this.trainingsMap[i].training.trainingType);
							}
							
							break;
						}
					}
				return keep;
			})
		}
	}
})