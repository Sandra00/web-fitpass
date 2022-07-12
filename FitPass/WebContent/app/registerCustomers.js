

window.onload = function () {
    const vm = new Vue({
    el: '#app',
    data: {
        error: null,
        gender: false
    },
    methods: {
	
	
        async register() {
			var date = new Date(this.dateOfBirth)
			var splittedDate = this.dateOfBirth.split('-');
			date = new Date(splittedDate[2], splittedDate[1], splittedDate[0]);
            
            if(/\s/.test(this.username)){
				this.error = 'Korisničko ime ne sme sadržati razmake';
				return;
			}
			if(/\s/.test(this.password)){
				this.error = 'Lozinka ne sme sadržati razmake';
				return;
			}
			if(this.name[0].toUpperCase() != this.name[0]){
				this.error = 'Ime mora početi velikim slovom';
				return;
			}
			if(this.surname[0].toUpperCase() != this.surname[0]){
				this.error = 'Prezime mora početi velikim slovom';
				return;
			}
			if(this.name.trim().length === 0){
				this.error = 'Ime ne sme biti prazno';
				return;
			}
			if(this.surname.trim().length === 0){
				this.error = 'Prezime ne sme biti prazno';
				return;
			}
			if(splittedDate[0] > 2004){
				this.error = 'Morate biti punoletni';
				return;
			}
            await axios.post(
                "rest/register",
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
                this.error = 'Postoji korisnik sa unetim korisničkim imenom';
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
}