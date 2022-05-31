var menu = new Vue({
	el: '#menu',
	data: {
		username: ''
	},
	mounted(){
		axios.get('rest/currentUser')
		.then((response) => (this.username = response.data.username));
	},
	methods:{
		
		logout() {
			this.username = '';
			axios.post('rest/logout');
		}
		
	}
});



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