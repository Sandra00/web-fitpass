window.onload = function() {
	var menu = new Vue({
		el: '#menu',
		data: {
			username: '',
			isLoggedIn: false,
			isAdmin: false,
			isManager: false
		},
		created(){
			axios.get('rest/currentUser')
			.then((response) => {
				this.username = response.data.username;
				if(this.username){
					this.isLoggedIn = true;
				}
				if(response.data.userType == 'ADMIN'){
					this.isAdmin = true;
				} else if(response.data.userType == 'MANAGER'){
					this.isManager = true;
				}
			});
		},
		methods:{
			
			logout() {
				this.username = '';
				axios.post('rest/logout');
				this.isLoggedIn = false;
				this.isAdmin = false;
				this.isManager = false;
			}
			
		}
	});
	
	
	var menu = new Vue({
		el: '#menuBar',
		data: {
			username: '',
			isLoggedIn: false,
			isAdmin: false,
			isManager: false
		},
		mounted(){
			axios.get('rest/currentUser')
			.then((response) => {
				this.username = response.data.username;
				if(this.username){
					this.isLoggedIn = true;
				}
				if(response.data.userType == 'ADMIN'){
					this.isAdmin = true;
				} else if(response.data.userType == 'MANAGER'){
					this.isManager = true;
				}
			});
		},
		methods:{
			
			logout() {
				this.username = '';
				axios.post('rest/logout');
				this.isLoggedIn = false;
				this.isAdmin = false;
				this.isManager = false;
			}
			
		}
	});
}