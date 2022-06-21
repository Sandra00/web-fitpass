    const vm = new Vue({
    el: '#app',
    data() {
        return {
            error: ""
        };
    },
    methods: {
        async register() {
			var date = new Date(this.dateOfBirth)
			var splittedDate = this.dateOfBirth.split('-');
			date = new Date(splittedDate[2], splittedDate[1], splittedDate[0]);
            await axios.post(
                "rest/admin/new-coach",
                {
                    username: this.username,
                    name: this.name,
                    surname: this.surname,
                    gender: this.gender,
                    dateOfBirth: this.dateOfBirth,
                    password: this.password
                }
            )
            .then( response =>{
                window.location.href = 'index.html';
            })
            .catch( error => {
				if(error.response.status == 401){
                	this.error = 'Ti nisi admin, kako si uopšte došao ovde?';
            	}
            	else if (error.response.status == 409){
					this.error = 'Postoji korisnik sa unetim korisničkim imenom';
				}
				else {
					this.error = 'UPS! Neka nepredviđena greška!';
				}
            })
        },
        onChange(event) {
              let gender = event.target.value;
          },
        getFormValues (submitEvent) {
            this.register();
        }
    }
});