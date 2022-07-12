const vm = new Vue ({
	el: '#app',
	data: {
		price: 0,
		promoCode: '',
		membershipId: ''
	},
	mounted(){
		var location = window.location.href;
		uri = decodeURI(location);
		this.membershipId = uri.split("=")[1];
		
		this.resetPrice();
	},
	methods: {
		
		async checkPromoCode(){
			
			await this.resetPrice();
			axios.get('rest/customer/promo-code/' + this.promoCode)
			.then((response) => {
				promoCodeData = response.data;
				this.price = this.price * ((100 - promoCodeData.discountPercentage) / 100);
			});
		},
		
		
		async resetPrice(){
			
			await axios.get('rest/membership/' + this.membershipId)
			.then((response) => {
				this.price= response.data.price;
			});
		},
		
		async confirm(){
			
			await axios.put('rest/customer/new-membership?membershipId=' + this.membershipId + '&promoId=' + this.promoCode)
			.then((response) => {
				window.location.href = 'index.html';
			});
		}
		
	}
});
