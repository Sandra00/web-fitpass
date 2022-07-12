const vm = new Vue ({
	el: '#app',
	data: {
		allUsers: [],
		promoCodes: [],
		usernameSearch: '',
		firstNameSearch: '',
		lastNameSearch: '',
		roleFilter: '',
		typeFilter: '',
		isAsc: true,
		memberships: [],
		trainings: []
	},
	created(){
		axios.get('rest/admin/all-users')
		.then((response) => {
			this.allUsers = response.data;
		});
		
		axios.get('rest/admin/all-promo-codes')
		.then((response) => {
			this.promoCodes = response.data;
			this.promoCodes.forEach(async item => {
				item.expirationDate = await this.formatDateTime(item.expirationDate);
			});
		});
		
		axios.get('rest/admin/all-memberships')
		.then((response) => {
			this.memberships = response.data;
		});
		
		axios.get('rest/admin/all-trainings')
		.then((response) => {
			this.trainings = response.data;
		});
	},
	methods: {
		
		sort(index){
			this.isAsc = !this.isAsc;
			
			if(index == 1){
				if(this.isAsc){
					this.allUsers.sort((a, b) => {
						return a.username.localeCompare(b.username);
					});
				} else{
					this.allUsers.sort((a, b) => {
						return b.username.localeCompare(a.username);
					});
				}
			}
			else if(index == 2){
				if(this.isAsc){
					this.allUsers.sort((a, b) => {
						return a.name.localeCompare(b.name);
					});
				} else{
					this.allUsers.sort((a, b) => {
						return b.name.localeCompare(a.name);
					});
				}
			}
			else if(index == 3){
				if(this.isAsc){
					this.allUsers.sort((a, b) => {
						return a.surname.localeCompare(b.surname);
					});
				} else{
					this.allUsers.sort((a, b) => {
						return b.surname.localeCompare(a.surname);
					});
				}
			}
			else if(index == 4){
				if(this.isAsc){
					this.allUsers.sort((a, b) => {
						return a.points > b.points ? -1 : 1;
					});
				} else{
					this.allUsers.sort((a, b) => {
						return a.points < b.points ? -1 : 1;
					});
				}
			}
		},
		
		
		deleteUser(username) {
			axios.delete('rest/admin/delete/user/' + username)
			.then( (response) => {
				window.location.href = 'admin-panel.html';
			})
			.catch( (error) => {
				alert("Nije bilo moguće obrisati korisnika!");
			});
		},
		
		
		deletePromoCode(code) {
			axios.delete('rest/admin/delete/promo-code/' + code)
			.then( (response) => {
				window.location.href = 'admin-panel.html';
			})
			.catch( (error) => {
				alert("Nije bilo moguće obrisati promo kod!");
			});
		},
		
		
		deleteMembership(id) {
			axios.delete('rest/admin/delete/membership/' + id)
			.then( (response) => {
				window.location.href = 'admin-panel.html';
			})
			.catch( (error) => {
				alert("Nije bilo moguće obrisati članarinu!");
			});
		},
		
		
		deleteTraining(id) {
			axios.delete('rest/admin/delete/training/' + id)
			.then( (response) => {
				window.location.href = 'admin-panel.html';
			})
			.catch( (error) => {
				alert("Nije bilo moguće obrisati trening!");
			});
		},
		
		
		formatDateTime(date) { 
			if(date[3] < 10){
				date[3] = '0' + date[3];
			}
			if(date[4] < 10){
				date[4] = '0' + date[4];
			}
			return date[2] + '/' + date[1] + '/' + date[0] + ' ' + date[3] + ':' + date[4];
		}
		
		
	},

	computed: {

		filteredUsers: function(){
			return this.allUsers.filter((user) => {
				if(!user.username){
					user.username = '';
				}
				if(!user.name){
					user.name = '';
				}
				if(!user.surname){
					user.surname = '';
				}
				if(!user.userType){
					user.userType = '';
				}
				if(!user.customerType){
					user.customerType = '';
				}
				let show = true;
				show &= user.username.toLowerCase().search(this.usernameSearch.toLowerCase()) >= 0;
				show &= user.name.toLowerCase().search(this.firstNameSearch.toLowerCase()) >= 0;
				show &= user.surname.toLowerCase().search(this.lastNameSearch.toLowerCase()) >= 0;
				show &= user.userType.search(this.roleFilter) >= 0 || this.roleFilter == 'ALL' || this.roleFilter == '';
				show &= user.customerType.search(this.typeFilter) >= 0 || this.typeFilter == 'ALL' || this.typeFilter == '';
				
				return show;
			});
		}
		
		
	}
});
