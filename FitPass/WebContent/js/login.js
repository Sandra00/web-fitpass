const vm = new Vue({
    el: '#app',
    data() {
        return {
            error: ""
        };
    },
    methods: {
        async login() {
            await axios.post(
                "rest/login",
                {
                    username: this.username,
                    password: this.password
                }
            )
            .then( response =>{
                window.location.href = 'index.html';
            })
            .catch( error => {
                this.error = 'Pogrešno korisničko ime i/ili lozinka!';
            })
        },
        getFormValues (submitEvent) {
            if(this.username && this.password){
                this.login();
            }
            else{
                this.error = "Molimo Vas popunite formu u celosti!";
            }
        }
    }
});