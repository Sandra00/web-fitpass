window.onload = function() {
	const vm = new Vue ({
		el: '#app1',
		data: {
			currUSer : null,
			username : null,
			name : null,
			surname : null,
			dateOfBirth : null,
			password : null
		},
		mounted(){
			axios.get('rest/currentUser')
		.then((response) => {
			this.currUser = response.data;
			this.username = response.data.username;
			this.name = response.data.name
			this.surname = response.data.surname;
			this.dateOfBirth = response.data.dateOfBirth;
			this.password = response.data.password;
			if(response.data.userType == 'ADMIN'){
				this.isAdmin = true;
			}	
		});
		},
		methods: {
			
		}
	});
}