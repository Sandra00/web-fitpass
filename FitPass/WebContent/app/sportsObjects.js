var app = new Vue({
	el: '#app',
	data: {
		sportsObjects: null
	},
	mounted(){
		axios.get('rest/objects')
		.then((response) => {
			this.sportsObjects = response.data;
			
			// sorting sports objects so that the active ones are displayed first
			this.sportsObjects.sort((x, y) => { return (x.status === y.status)? 0 : x.status? -1 : 1; });
		});
		
	},
	methods: {
		
		
	}
	
});