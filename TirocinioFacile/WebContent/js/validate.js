function ValidateEmailStudente(email,txt) {
	var emailFormat = /^[a-z]{1}[.]{1}[a-z]*[0-9]{2}$/;
	
	if(email.value.match(emailFormat)) {
		$(txt).html("Email Istituzionale inserire solo cio' che viene prima di @studenti.unisa.it)");
		email.style.border = "2px solid green";
		return true;
	}
	$(txt).html("Email errata!");
	email.style.border = "2px solid red";
	return false;
}

function ValidatePasswordUguali(pass,conferma,txt) {
		if(pass.value.localeCompare(conferma.value) == 0) {
			$(txt).html("");
			return true;
		}
		$(txt).html("Le Password non sono uguali");
		return false;
}

function ValidateMatricola(matricola, txt) {
	var matr = /^[0-9]{5}$/;
	
	if (matricola.value.match(matr)) {
		$(txt).html("Matricola");
		matricola.style.border = "2px solid green";
		return true;
	}
	$(txt).html("Matricola errata!");
	matricola.style.border = "2px solid red";
	return false;
}

function ValidatePassword(pass, txt) {
	var passwordFormat = /^[A-Za-z0-9_@./#&+-]{8,200}$/;
	if (pass.value.match(passwordFormat)) {
		$(txt).html("Password");
		pass.style.border = "2px solid green";
		return true;
	}
	$(txt).html("Min 8 caratteri");
	pass.style.border = "2px solid red";
	return false;

}
function ValidateEmail(mail, txt) {

	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	if (mail.value.match(mailformat)) {
		$(txt).html("Email");
		mail.style.border = "2px solid green";
		return true;
	}
	$(txt).html("Email non valida");
	mail.style.border = "2px solid red";
	return false;
}
function ValidateAlfa(str, txt) {
	var letters = /^[-\w\s,;.()]+$/;
	if (!(str.value.match(letters))) {
		str.style.border = "2px solid red";
		$(txt).html("Campo non valido");
		return false;
	}
	$(txt).html("");
	str.style.border = "2px solid green";
	return true;
}
function ValidateLetter(str, txt) {
	var letters = /^([a-zA-Z\xE0\xE8\xE9\xF9\xF2\xEC\x27]\s?)+$/;
	if (!(str.value.match(letters))) {
		$(txt).html("Campo non valido");
		str.style.border = "2px solid red";
		return false;
	}
	$(txt).html("");
	str.style.border = "2px solid green";
	return true;
}

function ValidateNumber(num, txt) {
	var valid = isNaN(num.value);

	if (valid || num.value <= 0) {
		$(txt).html("Campo non valido");
		num.style.border = "2px solid red";
		return false;
	}
	$(txt).html("");
	num.style.border = "2px solid green";
	return true;
}

function ValidateFile(file) {
	if (file.files.length == 0) {
		file.style.border = "2px solid red";
		return false;
	}
	return true;
}

function ValidatePartitaIva(str, txt) {
	var valid = isNaN(str.value);
	if (valid) {
		str.style.border = "2px solid red";
		$(txt).html("Partita Iva non valida");
		return false;
		
	}
	if(str.value.length == 11) {
		str.style.border = "2px solid green";
		return true;
	}
	else {
		str.style.border = "2px solid red";
	    $(txt).html("Partita Iva non valida");
	    return false;
	}	
}
function ValidateAmbSki(str , txt){
	var letters = /^[-\w\s,+.]+$/;
	if (!(str.value.match(letters))) {
		str.style.border = "2px solid red";
		$(txt).html("Campo non valido");
		return false;
	}
	$(txt).html("");
	str.style.border = "2px solid green";
	return true; 
}	
function ValidateAnno(str , txt){
	var date =/[2]{1}[0-9]{3}/;
	if (!(str.value.match(date))) {
		str.style.border = "2px solid red";
		$(txt).html("Data non valida");
		return false;
	}
	$(txt).html("");
	str.style.border = "2px solid green";
	return true; 
}
function ValidateAnnoMagg(str1 , txt ,str2 , txt){
	
	if (str2.value > str1.value) {
		if((str2.value - str1.value) ==1){
			str.style.border = "2px solid green";
			$(txt).html("Data non valida");
			return true;
		}
		
	}
	$(txt).html("");
	str.style.border = "2px solid red";
	return false; 
}	
