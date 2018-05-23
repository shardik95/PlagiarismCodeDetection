$(document).ready(function(){
    $("#login").click(function (event) {
    	document.getElementById("loginErrLbl").style.display="none";
        var pwd = $("#password123").val();
        var uname = $("#uname").val();
        event.preventDefault();
        if(pwd =="" ||  uname =="")
        {
        	document.getElementById("loginErrLbl").style.display="block";
        	document.getElementById("loginErrLbl").innerHTML = "*Please fill in the details*"
        }
        else
        {
        	$.ajax({
                type: "POST",
                url: "/checkpassword/",
                data: 'pwd='+ pwd + '&uname='+ uname ,
                success: function(){
                    window.location="../upload.html?username="+uname;
                },
                error: function(jqXHR, textStatus, errorThrown){
                	document.getElementById("loginErrLbl").style.display="block";
                	document.getElementById("loginErrLbl").innerHTML = "*Either username or password is incorrect, Please correct it*"
                    //window.location="../index.html";
                }
            });
        }  
        
    });


});


