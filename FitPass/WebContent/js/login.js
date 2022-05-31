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
                alert("succesful login!");
            })
            .catch( error => {
                alert("failed login");
            })
        },
        async getFormValues (submitEvent) {
            if(this.username && this.password){
                await this.login();
                window.location.href = 'landing-page.html';
            }
            else{
                this.error = "Please fill the form";
            }
        }
    }
});