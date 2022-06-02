var app = new Vue({
	el: '#app',
	data: {
		sportsObjects: [],
		nameSearch:'',
		typeSearch:'',
		locationSearch:'',
		gradeSearch:''
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
				return sportsObject.name.match(this.nameSearch);
			})
		}
	}
	
});