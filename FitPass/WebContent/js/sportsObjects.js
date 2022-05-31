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
		.then((response) => (this.sportsObjects = response.data));
	},
	methods: {
		
		
		
		
	}
	
});