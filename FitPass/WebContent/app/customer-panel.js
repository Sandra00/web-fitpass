const vm = new Vue ({
	el: '#app',
	data: {
		memberships: []
	},
	mounted(){
		axios.get('rest/memberships')
		.then((response) => {
			this.memberships= response.data;
		});
	},
	methods: {
		
		
	}
});
