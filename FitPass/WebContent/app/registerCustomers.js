

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