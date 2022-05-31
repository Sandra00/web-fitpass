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
                this.error = 'Wrong username or password!';
            })
        },
        getFormValues (submitEvent) {
            if(this.username && this.password){
                this.login();
            }
            else{
                this.error = "Please fill the form";
            }
        }
    }
});