const vm = new Vue ({
	el: '#app',
	data: {
		price: 0,
		promoCode: ''
	},
	mounted(){
		
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
			var location = window.location.href;
			uri = decodeURI(location);
			membershipId = uri.split("=")[1];
			
			await axios.get('rest/membership/' + membershipId)
			.then((response) => {
				this.price= response.data.price;
			});
		}
		
	}
});
