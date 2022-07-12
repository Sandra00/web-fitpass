var app = new Vue({
	el: '#app',
	data: {
		sportsObjects: [],
		nameSearch:'',
		typeSearch:'',
		locationSearch:'',
		gradeSearch:'',
		sortIndex : null,  //kolona koja se sortira
		sortDirection: null,
		typeFilter: 'allTypes',
		openFilter: 'allOpenClosed',
		isAdmin: false
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
			
		},
		
		
		filterType(type){
			axios.get('rest/objects')
			.then((response) => {
				this.sportsObjects = response.data;
				this.sportsObjects.filter((sportsObject)=>{
					return sportsObject.locationType.match(type);
				});
			});
		},
		
		
		showObject(selectedName) {
			 axios.get(
                "rest/objects/showObject",
                {
                    name: selectedName
                }
            )
			axios.get('rest/objects/showObject');
			window.location.href = 'showObject.html';
		},
		
		
		deleteSportsObject(name) {
			axios.delete('rest/admin/delete/sports-object/' + name)
			.then( (response) => {
				window.location.href = 'index.html';
			})
			.catch( (error) => {
				alert("Nije bilo moguće obrisati objekat!");
			});
		},
		
		
		getImage(id){
			return new Promise((resolve, reject) => {
				axios.get('rest/image/' + id)
				.then((response) => {
					resolve(response.data);
				})
			});
		}
		
		
	},
	created(){
		axios.get('rest/currentUser')
		.then((response) => this.isAdmin = response.data.userType === 'ADMIN');
		
		axios.get('rest/objects')
		.then((response) => {
			this.sportsObjects = response.data;
			
			this.sportsObjects.sort((x, y) => { return (x.status === y.status)? 0 : x.status? -1 : 1; });
			this.sportsObjects.forEach(async item => {
				item.logo = await this.getImage(item.logo);
			});
		});
	},
	computed:{
		filteredSportsObjects:function(){
			
			return this.sportsObjects.filter((sportsObject) => {
				let keep = true;
				keep = sportsObject.name.toLowerCase().match(this.nameSearch.toLowerCase()) && sportsObject.locationType.toLowerCase().match(this.typeSearch.toLowerCase()) && sportsObject.averageGrade.toString().toLowerCase().match(this.gradeSearch.toLowerCase())
				&& (sportsObject.location.address.street.toLowerCase().match(this.locationSearch.toLowerCase()) ||  sportsObject.location.address.number.toString().toLowerCase().match(this.locationSearch.toLowerCase()) || sportsObject.location.address.town.toLowerCase().match(this.locationSearch.toLowerCase()) || sportsObject.location.address.zipcode.toString().toLowerCase().match(this.locationSearch.toLowerCase()))
				&& (sportsObject.locationType.match(this.typeFilter) || this.typeFilter==='allTypes');
				if(this.openFilter == 'opened'){
					keep = keep && (sportsObject.status==true);
				}
				return keep;
			})
			
		}
	}
	
});