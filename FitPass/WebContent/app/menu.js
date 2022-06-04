var menu = new Vue({
	el: '#menu',
	data: {
		username: '',
		isAdmin: false
	},
	mounted(){
		axios.get('rest/currentUser')
		.then((response) => {
			this.username = response.data.username;
			if(response.data.userType == 'ADMIN'){
				this.isAdmin = true;
			}	
		});
	},
	methods:{
		
		logout() {
			this.username = '';
			axios.post('rest/logout');
		}
		
	}
});