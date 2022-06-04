window.onload = function() {
	const vm = new Vue ({
		el: '#app',
		data: {
			allUsers: []
		},
		mounted(){
			axios.get('rest/all')
			.then((response) => {
				this.allUsers= response.data;
			});
		},
		methods: {
			
		}
	});
}