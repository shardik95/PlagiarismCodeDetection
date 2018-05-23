$(document).ready(function(){
    $("#register").click(function (event) {
    	event.preventDefault();
        var pwd = $("#pwd").val();
        var uname = $("#uname").val();
        var confirmPswd = $("#confirmpwd").val();
        var regexname=/^([a-zA-Z]{3,16})$/;
        $("#pswdLbl").hide();
        $("#unameLbl").hide();
        $("#cpswdLbl").hide();
        $("#pwdMatchLbl").hide();
        $("#unameInvalidLbl").hide();
        $("#unameExistsLbl").hide();
        $("#emailInvalidLbl").hide();
        
        
    	var email = document.getElementById("emailAddrTxt").value;
    	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        
        if(uname == "")
        {
        	$("#unameLbl").show();
        }
        else if (!uname.match(regexname))
        {
        	$("#unameInvalidLbl").show();
        }
        else if (pwd == "")
        {
        	$("#pswdLbl").show();
        }
        else if (confirmPswd == "")
        {
        	$("#cpswdLbl").show();
        }
        else if (pwd != confirmPswd)
        {
        	$("#pwdMatchLbl").show()
        }
        else if(!email.match(regex))
  	  	{
  		  	document.getElementById(emailInvalidLbl).style.display="block";
  		  	document.getElementById(emailInvalidLbl).innerHTML ="* Enter valid e-mail address *"; 
  	  	}
        else
        {
        	
        	$.ajax({
                type: "POST",
                url: "/adduser/",
                data: 'pwd='+ pwd + '&uname='+ uname + '&email='+email,
                success: function(){
                    window.location="../index.html";
                },
                error: function(jqXHR, textStatus, errorThrown){
                	$("#unameExistsLbl").show();
                }
            });
            
        }
    });  

});