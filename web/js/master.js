// Author: Gisela, Andika, Ferdinandus Richard

// variabel global
var unameflag;
var emailflag;

// Mengecek diisi tidaknya tiap field
function isFormEmpty(formNode) {
	var inputs = formNode.getElementsByTagName("input");
	inputs.length;
	flag = true;
	for(var i = 0; i < inputs.length; i++) {
		if(!inputs[i].value) {
			flag = false;
			inputs[i].parentElement.getElementsByClassName("errormsg")[0].innerText= "Field can't be empty!";
		} else {
			inputs[i].parentElement.getElementsByClassName("errormsg")[0].innerHTML = "&nbsp;";
		}
	}
	return flag;
}

function validateTripForm(formNode) {
	var inputs = formNode.getElementsByTagName("input");
	var isValid = true;
	var diffPlace = true;
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].value == "") {
			if (inputs[i].name != "pref-driver") {
				isValid = false;
			}
		}
	}
	if (inputs[1].value == inputs[2].value) {
		diffPlace = false;
		alert("Picking point and destination can't be the same place");
	}
	if (!isValid) {
		alert("Non-optional field should be filled");	
	}
	return (isValid && diffPlace);
}

// Mengecek diisi tidaknya field untuk keseluruhan form
function isSignUpValid(formNode) { 
	console.log("masuk");
	var inputs = formNode.getElementsByTagName("input");
	inputs.length;
	flag = true;
	console.log(inputs.length);
	for(var i = 0; i < inputs.length; i++) {
		console.log(inputs[i].value);
		if(!inputs[i].value) {
			flag = false;
		}
	}
	console.log(inputs[inputs.length-1].checked);
	if (!flag) {
		formNode.getElementsByClassName("errormsg")[0].innerHTML= "All field can't be empty!";
	} else {
		if (inputs[3].value != inputs[4].value) {
			formNode.getElementsByClassName("pass-not-same")[0].innerHTML= "Passwords not match!";
			flag = false;
		} else {
			formNode.getElementsByClassName("pass-not-same")[0].innerHTML= "&nbsp;";
		}
		if (!(isNameValid(inputs[0].value))) {
			formNode.getElementsByClassName("errormsg")[0].innerHTML= "Name can't be more than 20 characters!";
			return false;
		} else {
			formNode.getElementsByClassName("errormsg")[0].innerHTML= "&nbsp;";
		}
		if (!(isPhoneValid(inputs[5].value))) {
			formNode.getElementsByClassName("errormsg")[0].innerHTML= "It's not a phone number!";
			return false;
		} else {
			formNode.getElementsByClassName("errormsg")[0].innerHTML= "&nbsp;";
		}
		if (!unameflag) {
			formNode.getElementsByClassName("errormsg")[0].innerHTML= "Please change your username.";
			return false;
		} else {
			formNode.getElementsByClassName("errormsg")[0].innerHTML= "&nbsp;";
		}
		if (!emailflag) {
			formNode.getElementsByClassName("errormsg")[0].innerHTML= "Please change your email.";
			return false;
		} else {
			formNode.getElementsByClassName("errormsg")[0].innerHTML= "&nbsp;";
		}
	}
	return flag;
}

// Mengecek apakah semua karakter berupa angka
function isAllDigit(string) {
	for (var i = string.length - 1; i >= 0; i--) {
		if (isNaN(string[i])) {
			return false;
		}
	}
	return true;
}

function isNameValid(string) {
	return (string.length <= 20);
}

function isPhoneValid(string) {
	if (isAllDigit(string)) {
		return ((string.length >= 9) && (string.length <= 12) && (string[0] == 0));
	}
	return false;
}

function validateDelete(location) {
	return confirm("Are you sure want to delete " + location + " from your preferred location?");
}
function editLocation(row) {
	var forms = document.getElementsByTagName("form");
	var imgs = document.getElementsByClassName("edit-location-icon");
	var tds = forms[row-1].parentElement.getElementsByTagName("td");
	var flag = true;
	for(var i = 0; i < forms.length-1; i++) {
		if (i != row-1) {
			var td = forms[i].parentElement.getElementsByTagName("td");
			td[1].hidden = false;
			td[2].hidden = true;
			imgs[i].src = "img/edit.png";
		}
	}
	console.log("test0");
	if (tds[2].hidden) {
		flag = false;
		console.log("test1");
	}
	if (!flag) {
		imgs[row-1].src = "img/save.png";
		tds[1].hidden = true;
		tds[2].hidden = false;
		console.log("test");
		return false;
	} else {
		if (tds[2].getElementsByTagName("input")[0].value == "") {
			alert("Location cannot be empty");
			return false;
		}
		data = {};
		var new_location = tds[2].getElementsByTagName("input")[0].value;
		var old_location = tds[1].innerText;
		console.log("editing");
		// sendRequest("POST", "/updatelocation?new_location=" + new_location + "&old_location=" + old_location, JSON.stringify(data), null);
		return true;
	}
	return flag;
}
function sendRequest(method, url, data, callbackFunction) {
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = callbackFunction;
	xmlhttp.open(method, url, true);
	xmlhttp.send(data);
}
function deleteLocation(preferred_location) {
	if (validateDelete(preferred_location)) {
		data = {};
		sendRequest("POST", "/deletelocation?location=" + preferred_location, JSON.stringify(data), refresh);
	}
}

function refresh() {
	setTimeout(function (){
		location.reload();
	}, 200);
}

function isUnameValid(input) {
	var xhtr = new XMLHttpRequest();
	var vicontainer = input.parentElement.querySelector(".validation-icon");
	xhtr.onreadystatechange = function() {
		if(this.readyState == 4) {
			if (input.value == "") { // jika belum ada input, tidak akan dikeluarkan icon validasi
				vicontainer.innerHTML = "&nbsp";
			} else {
				if(this.responseText == "true") {
					if(this.responseText) { // mengecek kevalidan username yaitu tidak ada spasi dengan menggunakan regex
						var regex = /^\S*$/;
						unameflag = regex.test(input.value);
					}
				} else {
					unameflag = false; // TODO: change to false, this is only for testing
				}
				if (unameflag) {
					vicontainer.innerHTML = "<img class='add-brightness' src='img/checked.svg'>";
				} else {
					vicontainer.innerHTML = "<img class='dec-brightness' src='img/cancel.svg'>";
				}
			}
		}
	}
	xhtr.open("GET","/usernameajax?uname=" + input.value, true);
	xhtr.send();
}

function isEmailValid(input) {
	var xhtr = new XMLHttpRequest();
	var vicontainer = input.parentElement.querySelector(".validation-icon");
	xhtr.onreadystatechange = function() {
		if(this.readyState == 4) {
			if (input.value == "") { // jika belum ada input, tidak akan dikeluarkan icon validasi
				vicontainer.innerHTML = "&nbsp";
			} else {
				if(this.responseText == "true") {
					if(this.responseText) { // mengecek kevalidan format email dengan menggunakan regex
						var regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
						emailflag = regex.test(input.value);
					}
				} else {
					emailflag = false; // TODO : change to false, this is only for testing
				}
				if (emailflag) {
					vicontainer.innerHTML = "<img class='add-brightness' src='img/checked.svg'>";
				} else {
					vicontainer.innerHTML = "<img class='dec-brightness' src='img/cancel.svg'>";
				}
			}
		}
	}
    xhtr.open("GET","/emailajax?email=" + input.value, true);
	xhtr.send();
}

function hideHistory(orderId, isUser) {
	data = {};
	sendRequest("POST", "/hidehistory?orderId="+orderId+"&isUser="+isUser, JSON.stringify(data), refresh);
}

function chooseNavbar(number) {
	var navbar = document.getElementsByClassName("navbar-list");
	var item = navbar[0].getElementsByTagName("li")[number];
	console.log(navbar[0].getElementsByTagName("li")[number]);
	item.className = "content-active";
	item.getElementsByTagName("a")[0].className = "whitetext";
}

function changeRating(number) {
	var ratingIcons = document.getElementsByClassName("rating-icon");
	var inputs = document.getElementsByName("rating");
	for (var i = 0; i < 5; i++) {
		if (i < number) {
			ratingIcons[i].src = "img/filled-star.png";
		} else {
			ratingIcons[i].src = "img/empty-star.png";
		}
	}
	inputs[0].value = number;
	console.log(inputs[0].value);
}

function validateOrder(formNode) {
	var rating = document.getElementsByName("rating")[0].value;
	var comment = document.getElementsByName("comment")[0].value;
	if (rating == 0) {
		alert("Please rate your driver");
		return false;
	}
	if (comment == "") {
		alert("Please give a comment for your driver");
		return false;
	}
	return true;
}

function putDefaultImage(node) {
	node.src = "img/blackjek.png";
}

function isEditProfileValid(node) {
	var inputs = node.getElementsByTagName("input");
	// check for name input and phone input not to be empty
	if (inputs[1].value == "" || inputs[2].value == "") {
		alert('Form cannot have empty fields');
		return false;
	}
	if (!isNameValid(inputs[1].value)) {
		alert('Name cannot have more than 20 characters');
		return false;
	}
	if (!isPhoneValid(inputs[2].value)) {
		alert('It is not a valid phone number');
		return false;
	}
	return true;
}

function isLocationValid(node) {
	var inputs = node.getElementsByTagName("input");
	// check to see the input empty
	if (inputs[0].value == "") {
		alert('Location cannot be empty');
		return false;
	}
	return true;
}