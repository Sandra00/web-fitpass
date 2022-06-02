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