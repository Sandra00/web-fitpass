	const vm = new Vue ({
		el: '#app1',
		data: {
			currUSer : null,
			username : null,
			name : null,
			surname : null,
			dateOfBirth : null,
			password : null,
			gender: null,
			error: null
		},
		mounted(){
			axios.get('rest/currentUser')
		.then((response) => {
			this.currUser = response.data;
			this.username = response.data.username;
			this.name = response.data.name
			this.surname = response.data.surname;
			this.gender = response.data.gender;
			this.dateOfBirth = response.data.dateOfBirth;
			this.password = response.data.password;
			if(response.data.userType == 'ADMIN'){
				this.isAdmin = true;
			}	
		});
		},
		methods: {
			
			
			async editProfile(){
				axios.post(
					"rest/editUser",
					{
						oldUsername:this.currUser.oldUsername,
						username: this.username,
                    	name: this.name,
                    	surname: this.surname,
                    	gender: this.gender,
                    	dateOfBirth: this.dateOfBirth,
                    	password: this.password
					}
					)
					.then( response =>{
                this.error = 'Profil je uspešno izmenjen';
                window.location.href = 'index.html';
            })
					.catch( error => {
                this.error = 'Postoji korisnik sa unetim korisničkim imenom';
            })
			},
			
			
			getFormValues (submitEvent) {
            	this.editProfile();
        	}
        	
        	
		}
	});
