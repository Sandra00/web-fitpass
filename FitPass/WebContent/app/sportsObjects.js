var app = new Vue({
	el: '#app',
	data: {
		sportsObjects: [],
		nameSearch:'',
		typeSearch:'',
		locationSearch:'',
		gradeSearch:'',
		sortIndex : null,  //kolona koja se sortira
		sortDirection: null
	},
	mounted(){
		//axios.get('rest/objects')
		//.then((response) => {
			//this.sportsObjects = response.data;
			
			// sorting sports objects so that the active ones are displayed first
			//this.sportsObjects.sort((x, y) => { return (x.status === y.status)? 0 : x.status? -1 : 1; });
		//});
		
	},
	methods: {
		sortSportObjects(index){
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
					this.sportsObjects = this.sportsObjects.sort(
						(rowA, rowB) => {
						return rowA.name.localeCompare(rowB.name)
						}
					)
				}else{
					this.sportsObjects = this.sportsObjects.sort(
						(rowA, rowB) => {
							return rowB.name.localeCompare(rowA.name)
						}
					)
				}
				
			}else if(this.sortIndex == 2){
				if(this.sortDirection == 'asc'){
					this.sportsObjects = this.sportsObjects.sort(
						(rowA, rowB) => {
						return (rowA.location.address.street + rowA.location.address.number.toString() + rowA.location.address.town + rowA.location.address.zipcode.toString().toLowerCase()).localeCompare(
							(rowB.location.address.street + rowB.location.address.number.toString() + rowB.location.address.town + rowB.location.address.zipcode.toString()).toLowerCase())
						}
					)
				}else{
					this.sportsObjects = this.sportsObjects.sort(
						(rowA, rowB) => {
						return (rowB.location.address.street + rowB.location.address.number.toString() + rowB.location.address.town + rowB.location.address.zipcode.toString().toLowerCase()).localeCompare(
							(rowA.location.address.street + rowA.location.address.number.toString() + rowA.location.address.town + rowA.location.address.zipcode.toString()).toLowerCase())
						}
					)
				}
				
			}else{  //ovde je 3, samo to jos ima
				if(this.sortDirection == 'asc'){
					this.sportsObjects = this.sportsObjects.sort(
						(rowA, rowB) => {
						return (rowA.averageGrade.toString()).localeCompare(rowB.location.address.number.toString())
						}
					)
				}else{
					this.sportsObjects = this.sportsObjects.sort(
						(rowA, rowB) => {
							return (rowB.averageGrade.toString()).localeCompare(rowA.location.address.number.toString())
						}
					)
				}
			}
			
		}
		
	},
	created(){
		axios.get('rest/objects')
		.then((response) => {
			this.sportsObjects = response.data;
			
			// sorting sports objects so that the active ones are displayed first
			this.sportsObjects.sort((x, y) => { return (x.status === y.status)? 0 : x.status? -1 : 1; });
		});
	},
	computed:{
		filteredSportsObjects:function(){
			return this.sportsObjects.filter((sportsObject) => {
				return sportsObject.name.toLowerCase().match(this.nameSearch.toLowerCase()) && sportsObject.locationType.toLowerCase().match(this.typeSearch.toLowerCase()) && sportsObject.averageGrade.toString().toLowerCase().match(this.gradeSearch.toLowerCase())
				&& (sportsObject.location.address.street.toLowerCase().match(this.locationSearch.toLowerCase()) ||  sportsObject.location.address.number.toString().toLowerCase().match(this.locationSearch.toLowerCase()) || sportsObject.location.address.town.toLowerCase().match(this.locationSearch.toLowerCase()) || sportsObject.location.address.zipcode.toString().toLowerCase().match(this.locationSearch.toLowerCase()));
			})
		}
	}
	
});