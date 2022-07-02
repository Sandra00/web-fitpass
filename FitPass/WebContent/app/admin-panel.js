const vm = new Vue ({
	el: '#app',
	data: {
		allUsers: [],
		promoCodes: [],
		usernameSearch: '',
		firstNameSearch: '',
		lastNameSearch: '',
		roleFilter: '',
		typeFilter: ''
	},
	created(){
		axios.get('rest/admin/all-users')
		.then((response) => {
			this.allUsers= response.data;
		});
		
		axios.get('rest/admin/all-promo-codes')
		.then((response) => {
			this.promoCodes= response.data;
			this.promoCodes.forEach(async item => {
				item.expirationDate = await this.formatDateTime(item.expirationDate);
			});
		});	
	},
	methods: {
		
		/*
		filterType(type){
			axios.get('rest/admin/all-users')
			.then((response) => {
				this.allUsers = response.data;
				this.allUsers.filter((allUsers)=>{
					return allUsers.userType.match(type);
				});
			});
		},
		*/
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
				let show = true;
				show &= user.username.toLowerCase().search(this.usernameSearch.toLowerCase()) >= 0;
				show &= user.name.toLowerCase().search(this.firstNameSearch.toLowerCase()) >= 0;
				show &= user.surname.toLowerCase().search(this.lastNameSearch.toLowerCase()) >= 0;
				show &= user.userType.search(this.roleFilter) >= 0 || this.roleFilter == 'ALL' || this.roleFilter == '';
				//show &= user.lastName.toLowerCase().search(this.lastNameSearch.toLowerCase()) >= 0;
				
				return show;
			});
		}
	}
});
