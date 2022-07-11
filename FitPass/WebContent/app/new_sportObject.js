
    const vm = new Vue({	
    el: '#app',
    data: {
		name:null,
		objectType: "GYM",
		works:false,
		longitude:null,
		latitude:null,
		street:null,
		number:null,
		town:null,
		postNumber:null,
		startTime:null,
		endTime:null,
        error:null,
        noChecked:null,
        managers:[],
        contents:[],
        managerValue:null,
        file: null
    },
    mounted(){
	this.displayMap();
		axios.get('rest/freeManagers')
		.then((response) => {
			this.managers = response.data;
		})
	},
    methods: {
	
	
		displayMap: function () {

            let lat = 45.2396;
            let lon = 19.8227;
            let map = new ol.Map({
                layers: [
                    new ol.layer.Tile({
                        source: new ol.source.OSM()
                    })
                ],
                view: new ol.View({
                    center: ol.proj.fromLonLat([lon, lat]),
                    zoom: 10
                })
            });

            setTimeout(() => {
                if (map) {
                    map.setTarget("map-create");
                    let c = document.getElementById("map-create").childNodes;
                    c[0].style.borderRadius = '15px';
                }
            }, 50);

            map.on('click', evt => {
                let coord = ol.proj.toLonLat(evt.coordinate);
                this.reverseGeocode(coord);
                this.mapClicked = true;
            })
        },
        
        
        reverseGeocode: function (coords) {
            fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
                .then(function (response) {
                    return response.json();
                }).then(json => {
                console.log(coords);
                this.longitude = coords[0];
                this.latitude = coords[1];
                console.log(json.address);
                if (json.address.city) {
                    this.town = json.address.city;
                } else if (json.address.city_district) {
                    this.town = json.address.city_district;
                }

                if (json.address.road) {
                    this.street = json.address.road;
                }

                if (json.address.house_number) {
                    this.number = json.address.house_number;
                }

                if (json.address.postcode) {
                    this.postNumber = json.address.postcode;
                }

            });
        },
        
        
        geocode: async function (street, city) {
            await fetch('http://nominatim.openstreetmap.org/search?format=json&street=' + street + '&city=' + city + '&country=srbija')
                .then(function (response) {
                    return response.json();
                }).then(json => {
                    this.location.longitude = json[0].lon;
                    this.location.latitude = json[0].lat;
                });
        },
        async register() {
			imageId = null;
			if(this.file){
				imageId = await uploadImage(this.file);
			}
			
			addToManager = true;
            await axios.post(
                "rest/admin/register-sports-object",
                {
                    name: this.name,
                    locationType: this.objectType,
                    status:this.works,
                    location:{
						longitude:this.longitude,
						latitude:this.latitude,
						address:{
							street:this.street,
							number:this.number,
							town:this.town,
							zipcode:this.postNumber
						}
					},
					logo: imageId,
                    startWorkingHour: this.startTime.toLocaleString(),
                    endWorkingHour: this.endTime.toLocaleString()
                }
            )
            .then( response =>{
				if (!this.managerValue) {
					window.location.href = 'new_manager.html?sportsObjectName=' + this.name;
				}
				else {
                	window.location.href = 'index.html';
                }
            })
            .catch( error => {
                this.error = 'Postoji objekat sa unetim nazivom';
                addToManager = false;
            })
            
            
            if(addToManager){
				await axios.post(
				"rest/addSportsObject",
				{
					username: this.managerValue,
					sportsObject: this.name
				});
			}
        },
        
        
        isManagersEmpty: function(){
			if(this.managers.length == 0){
				return true;
			}else{
				return false;
			}
		},
        
        
        async handleFileUpload(event){
			this.file = await convertBase64(event.target.files[0]);
    	},
    	
    	
        getFormValues (submitEvent) {
            this.register();
        }
        
        
    }
});