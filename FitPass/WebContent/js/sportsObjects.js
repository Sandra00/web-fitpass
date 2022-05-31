var app = new Vue({
	el: '#app',
	data: {
		sportsObjects: null
	},
	mounted(){
		axios.get('rest/objects')
		.then((response) => (this.sportsObjects = response.data));
	},
	methods: {
		
		
		
		
	}
	
});