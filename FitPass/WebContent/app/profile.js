window.onload = function() {
	const vm = new Vue ({
		el: '#app1',
		data: {
			currUSer : null,
			username : null,
			name : null,
			surname : null,
			dateOfBirth : null,
			password : null,
			error: null
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
			async editProfile(){
				await axios.post(
					"rest/checkExisting",
					{
						username: this.username,
                    	name: this.name,
                    	surname: this.surname,
                    	dateOfBirth: this.dateOfBirth,
                    	password: this.password
					}
				)
				.then(error =>{
					this.error = "jej";
				})
				.catch(error => {
					this.error = "Korisničko ime je zauzeto";
				})
				
			},
			getFormValues (submitEvent) {
            	this.editProfile();
        }
		}
	});
}