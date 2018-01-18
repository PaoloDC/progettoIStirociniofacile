function ValidateMatricola(matriola,txt) {
	if(matricola.value != 5) {
		$(txt).html("Errore codice");
		code.style.border = "2px solid red";
		return false;
	} 
	$(txt).html("");
	code.style.border="2px solid green";
	return true;
}

function ValidateCode(code,txt) {
	var codeFormat = /^\d{3}$/;
	if(code.value.match(codeFormat)) {
		
		$(txt).html("");
		code.style.border="2px solid green";
		return true;
	}
	$(txt).html("Errore codice");
	code.style.border = "2px solid red";
	return false;
}


function ValidateCard(card,txt) {
	
	var cardFormat = /^\d{16}$/;
	if(card.value.match(cardFormat)) {
		$(txt).html("");
		card.style.border="2px solid green";
		return true;
	}
	$(txt).html("Card number non valido");
	card.style.border = "2px solid red";
	return false;
}

function ValidatePhone(phn,txt) {
	var phone = /^\d{10}$/;
	if(phn.value.match(phone)) {
		$(txt).html("");
		phn.style.border = "2px solid green";
		return true;
	}
	$(txt).html("Numero non valido");
	phn.style.border="2px solid red";
	return false;
}

function ValidateCap(cap,txt) {
	var capFormat = /^\d{5}$/;
	if(cap.value.match(capFormat)){
		$(txt).html("");
		cap.style.border = "2px solid green";
		return true;
		
	}
	$(txt).html("Cap non valido (solo numeri e lunghezza 5");
	cap.style.border="2px solid red";
	return false;
}

function ValidatePassword(pass,txt) {
	var passwordFormat = /^[A-Za-z0-9]{8,200}$/;
	if(pass.value.match(passwordFormat)){
		$(txt).html("Password");
		pass.style.border = "2px solid green";
		return true;
	}
	$(txt).html("Min 8 caratteri");
	pass.style.border="2px solid red";
	return false;

}
function ValidateEmail(mail,txt)   {  

	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;       
	if(mail.value.match(mailformat)) {
		$(txt).html("Email");
		mail.style.border = "2px solid green";
		return true;       
	}       
	$(txt).html("Email non valida");	
	mail.style.border = "2px solid red";
	return false;
}
function ValidateAlfa(str,txt) {
	var letters =  /^[-\w\s,;.()]+$/; 
	if(!(str.value.match(letters))) {
		str.style.border = "2px solid red";
		$(txt).html("Campo non valido");
		return false;
	}
	$(txt).html("");
	str.style.border = "2px solid green";
	return true;
}
function ValidateLetter(str,txt) {
	var letters =  /^([a-zA-Z\xE0\xE8\xE9\xF9\xF2\xEC\x27]\s?)+$/;
	if(!(str.value.match(letters))) {
		$(txt).html("Campo non valido");
		str.style.border = "2px solid red";
		return false;
	}
	$(txt).html("");
	str.style.border = "2px solid green";
	return true;
}

function ValidateNumber(num,txt) {
	var valid = isNaN(num.value);
	
	if(valid || num.value <= 0) {
		$(txt).html("Campo non valido");
		num.style.border = "2px solid red";
		return false;
	}
	$(txt).html("");
	num.style.border = "2px solid green";
	return true;
}

function ValidateFile(file) {
	if(file.files.length == 0 ) {
		file.style.border = "2px solid red";
		return false;
	}
	return true;
}
