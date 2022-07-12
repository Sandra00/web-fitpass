var app = new Vue({
	el: '#app',
	data: {
		object: null,
		labela: null,
		type: null,
		status: null,
		location: null,
		avgGrade: null,
		trainings: null,
		isCustomer: false,
		customerComments: [],
		commentText: '',
		commentGrade: 0,
		error: '',
		isAdmin: false,
		isManager: false,
		error: '',
		visitedSportsObject: false,
		comments: []
	},
	mounted(){
		var location = window.location.href.toString();
		var params = location.split("?");
		var value = params[1].split("=")[1];
		value = value.replace('%20', ' ');
		this.labela = value.toString();
	
		axios.get('rest/currentUser')
		.then((response) => {
			if(response.data.userType == 'CUSTOMER'){
				this.isCustomer = true;
			}
			if(response.data.userType == 'MANAGER'){
				this.isManager = true;
				axios.get(
				'rest/objects/all-comments',
				{
					params: {
						name: this.labela
				}
				
				}
				)
				.then((response) => {
					this.comments = response.data;
				})
			}
			if(response.data.userType == 'ADMIN'){
				this.isAdmin = true;
				axios.get(
				'rest/objects/all-comments',
				{
					params: {
						name: this.labela
				}
				
				}
				)
				.then((response) => {
					this.comments = response.data;
				})
			}
		});
		
		axios.get('rest/customer/has-visited/' + this.labela)
		.then((response) => {
			this.visitedSportsObject = true;
		})
		.catch((error) => {
			this.visitedSportsObject = false;
		});
		
		axios.get('rest/objects/trainings/' + this.labela)
		.then((response) => {
			this.trainings = response.data;
		});
		
		axios.get('rest/customer/comments/' + this.labela)
		.then((response) => {
			this.customerComments = response.data;
		});
		
		axios.get(
			'rest/objects/currentObject',
			{
				params: {
					name: this.labela
				}
				
			}
		)
		.then((response) => {
			this.object = response.data;
			
			
			//display map
			let lon = this.object.location.longitude;
			let lat = this.object.location.latitude;
			
            let map = new ol.Map({
                layers: [
                    new ol.layer.Tile({
                        source: new ol.source.OSM()
                    })
                ],
                view: new ol.View({
                    center: ol.proj.fromLonLat([lon, lat]),
                    zoom: 18
                })
            });

            setTimeout(() => {
                if (map) {
                    map.setTarget("map-create");
                    let c = document.getElementById("map-create").childNodes;
                    c[0].style.borderRadius = '15px';
                }
            }, 50);
			
			if(this.object.locationType == "GYM"){
				this.type = "teretana";
			}else if (this.object.locationTtype == "POOL"){
				this.type = "bazen";
			}else if(this.object.locationType == "DANCESTUDIO"){
				this.type = "plesni studio";
			}else{
				this.type = "sportski centar";
			}
			
			if(this.object.status == true){
				this.status = "radi";
			}else{
				this.status = "ne radi";
			}
			
			this.location = this.object.location.address.street + " "
				+ this.object.location.address.number + ", "
				+ this.object.location.address.zipcode + " "
				+ this.object.location.address.town
			
			if(this.object.gradesCounter == 0){
				this.avgGrade = "/";
			}else{
				this.avgGrade = this.object.averageGrade;
			}
			
			this.object.content.forEach(async item => {
				item.image = await this.getImage(item.image);
			});
			this.setLogo();
		});
		
		
		
	},
	methods: {
		
		
		async setLogo(){
			this.object.logo = await this.getImage(this.object.logo);
		},
		
		
		async activate(trainingId) {
			await axios.put(
	            'rest/customer/check-in?sportsObjectName=' + this.labela + '&trainingId=' + trainingId, {}
	        )
	        .then( response =>{
	            window.location.href = 'index.html';
	        })
	        .catch( error => {
				if(error.response.status == 401){
	            	this.error = 'Ti nisi kupac, kako si uopšte došao ovde?';
	        	}
	        	else if (error.response.status == 405){
					this.error = 'Nazalost nismo mogli da te prijavimo!';
				}
				else {
					this.error = 'UPS! Nepredviđena greška: ' + error.response.status;
				}
	        });
		},
		
		
		submitComment(){
			if(!this.commentText || this.commentText === '' || this.commentGrade < 1 || this.commentGrade > 5){
				alert("Morate ostaviti tekst komentara i ocenu!");
				return;
			}
			
			axios.post('rest/customer/leave-comment',
			{
				text: this.commentText,
			    grade: this.commentGrade,
			    sportsObjectName: this.labela
			})
			.then( response =>{
	            alert("Tvoj komentar je sačuvan, biće vidljiv kada ga administrator odobri!");
	        })
	        .catch( error => {
				if(error.response.status == 401){
	            	alert('Ti nisi kupac, kako si uopšte došao ovde?');
	        	}
	        	else if (error.response.status == 400){
					alert('Nije moguće postaviti komentar!');
				}
				else {
					alert('UPS! Nepredviđena greška: ' + error.response.status);
				}
	        });
		},
		
		
		getImage(id){
			return new Promise((resolve, reject) => {
				axios.get('rest/image/' + id)
				.then((response) => {
					resolve(response.data);
				});
			});
		},
		
		
		approve(commentId){
			axios.get(
			'rest/objects/approve-comment',
			{
				params:{
					id: commentId
				}
			}
		)
			window.location.reload()
		},
		
		
		reject(commentId){
			axios.get(
						'rest/objects/reject-comment',
						{
							params:{
								id: commentId
							}
						}
					)
			window.location.reload()
		}
		
		
	}
	
});