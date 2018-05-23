/**
 * Java script code for Upladd.html
 */

var allFileArr=[];
var fileIndxMap = {};
var studentNmeArr = [];
var fileNameArr = [];
var userRole;

window.onload = function()
{
	var uname = location.search.split("username=")[1];
	var roleCheck;
	if(uname.indexOf("%") > 0)
	{
		$("#sliderContainer").hide();
		roleCheck = false;
	}
	else
	{
		roleCheck = true;
	}
	$.ajax({
		type : "POST",
		url : "/cleanUploadedFiles/",
		data: 'roleCheck'+ roleCheck,
		success : function(data)
		{
			if(roleCheck)
			{
				document.getElementById("demo").innerHTML = data;
				document.getElementById("myRange").value = data;
			}
			$.ajax({
				type : "GET",
				url : "/health",
				dataType: "json",
				success : function(data1)
				{
					document.getElementById("systemStatus").innerHTML = "STATUS:"+data1.status;
					document.getElementById("systemStatus").style.color = "green";

					$.ajax({
						type : "GET",
						url : "/getUsageCount",
						data: 'uname='+ uname,
						success : function(data2)
						{
							document.getElementById("usageCnt").innerHTML ="Usage Count: "+data2;
						}});

				},
				error: function (result) {
					document.getElementById("systemStatus").innerHTML = "UP";
					document.getElementById("systemStatus").style.color = "green";

				}
			});

		},
		error: function (result) {
		}
	});
	if(roleCheck)
	{
		$.ajax({
			type: "GET",
			url: "/getRole/",
			data: 'uname='+ uname,
			success: function(data){

				if(data == "professor")
				{
					userRole = data;
					$("#sliderContainer").show();
				}
				else
				{
					userRole = data;
					$("#sliderContainer").hide();
				}
			}
		});
	}


	$.ajax({
		type: "GET",
		url: "/checksession/",
		cache : false,
		contentType : false,
		processData : false,
		success: function(){
		},
		error: function(jqXHR, textStatus, errorThrown){
			window.location="../index.html";
		}
	});

	$.ajax({
		type: "GET",
		url: "/getThreshold/",
		success: function(data){
			threshold = data;
		}
	});

};





function displayGuage(score)
{
	var scoreres = score.toFixed(2);
	document.getElementById("similarityScoreLbl").innerHTML = 'Similarity score: '+ scoreres + ' %';
	//	JS code for Guage
	var opts = {
			angle: 0.15, // The span of the gauge arc
			lineWidth: 0.44, // The line thickness
			radiusScale: 1, // Relative radius
			pointer: {
				length: 0.6, // // Relative to gauge radius
				strokeWidth: 0.035, // The thickness
				color: '#000000' // Fill color
			},
			limitMax: false,     // If false, max value increases automatically if value > maxValue
			limitMin: false,     // If true, the min value of the gauge will be fixed
			colorStart: '#6FADCF',   // Colors
			colorStop: '#8FC0DA',    // just experiment with them
			strokeColor: '#E0E0E0',  // to see which ones work best for you
			generateGradient: true,
			highDpiSupport: true,     // High resolution support

	};
	var target = document.getElementById('foo'); // your canvas element
	var gauge = new Gauge(target).setOptions(opts); // create gauge!
	gauge.maxValue = 100; // set max gauge value
	gauge.setMinValue(0);  // Prefer setter over gauge.minValue = 0
	gauge.animationSpeed = 32; // set animation speed (32 is default value)
	gauge.set(score); // set actual value
	$("#graphDiv").show();
	$("#graphDiag").show();
}

function reloadPage()
{
	location.reload(true);

}



var threshold;
function setThreshold() {
	var slider= document.getElementById("myRange")
	document.getElementById("demo").innerHTML = slider.value;
	threshold = slider.value;
	$.ajax({
		type: "POST",
		url: "/setThreshold/",
		data: 'threshold='+ threshold ,
		success: function(){
		}
	});
}


var printStudent1;
var printStudent2;
var arr;
var frmdata;
var lcsResult=[];
var ldResult =[];
var astResult =[];
var mossResult =[]
var avgScore =[];
var algorithmType;
var studentFileNamesArr = [];
var studentFilesMap={};
function addNewFilesToMap(studentName,fileNames, versionNo)
{
	var newStudentFileLst = [];
	for(var i=0; i< fileNames.length; i++)
	{

		newStudentFileLst.push(fileNames[i]);
	}
	studentFileNamesArr.push(newStudentFileLst);
	studentFilesMap[studentName+"_version_"+versionNo]= studentFileNamesArr.length-1;
	addNewUploadedFileToPanel(studentName, versionNo);

}

var collapseCounter = 0;
function addNewUploadedFileToPanel(studentName, versionNo)
{
	var newdiv = document.createElement('div');
	var studentFileListIndx = studentFilesMap[studentName+"_version_"+versionNo];
	var studentFileLst = studentFileNamesArr[studentFileListIndx];
	var liStr='';
	$.each(studentFileLst, function (index, value) {

		liStr +='<li id="'+studentName+'-'+value+'_version_'+versionNo+'" class="list-group-item">'+value+'</li>';


	});
	liStr += '</div></div></div></ul>';
	newdiv.innerHTML = '<div class="panel-group">'+
	'<div class="panel panel-default">'+
	'<div class="panel-heading">'+
	'<h4 class="panel-title">'+
	'<a data-toggle="collapse" href="#collapse'+collapseCounter+'" >'+studentName+' (version '+versionNo +')</a>'+
	'</h4>'+
	'</div>'+
	'<div id="collapse' + collapseCounter + '" class="panel-collapse collapse">'+
	'<ul class="list-group" id="ul-'+studentName+'_version_'+versionNo+'">'+ liStr;
	document.getElementById('divPanel').appendChild(newdiv);
	displaystudentFiles(studentName,collapseCounter,versionNo)
	collapseCounter = collapseCounter + 1;

}




function displaystudentFiles(studentName, collapseCounter, versionNo){
	var studentFileListIndx = studentFilesMap[studentName+"_version_"+versionNo];
	var studentFileLst = studentFileNamesArr[studentFileListIndx];
	var fileNamesWithNoDupl= studentFileLst.filter(function(elem, pos) {
		return studentFileLst.indexOf(elem) == pos;
	});
	var node = document.createElement("LI");
	$.each(fileNamesWithNoDupl, function (index, value) {

		node.innertHTML ='<li id="'+studentName+'-'+value+ '_version_'+versionNo+'" class="list-group-item">'+value+'</li>';
	});
	document.getElementById('ul-'+studentName+'_version_'+versionNo).appendChild(node);
}






function printFunction()
{


	$('#studentPanel').hide();
	$('#compResDiv').hide();
	$('#checkBoxDiv').hide();
	$('#uploadDiv').hide();
	$("#sliderContainer").hide();
	$("#resHdrdiv").hide();
	$('#backBtnDiv').show();
	if(printStudent1 != undefined && printStudent2 != undefined )
	{
		document.getElementById('similarityText').innerHTML = 'Comparison between '+ printStudent1 + ' files and '+ printStudent2 +'files';
		document.getElementById('similarityText').style.display="block";
	}
	window.print();

}

function onCheckBoxSelection()
{
	var algoSelected = $('.algoSelec:checked').val();
	if(algoSelected == "Moss")
	{
		document.getElementById("selAlgoBtn").innerHTML = "Validate"
	}
	else
	{
		document.getElementById("selAlgoBtn").innerHTML = "Compare"
	}
}

function showAllDiv()
{
	$('#studentPanel').show();
	$('#compResDiv').show();
	$('#checkBoxDiv').show();
	$('#uploadDiv').show();

	if(userRole == "professor")
	{
		$("#sliderContainer").show();
	}

	$("#resHdrdiv").show();
	$('#backBtnDiv').hide();
	$('#similarityText').hide();
}

function compareFiles()
{
	document.getElementById("emailSuccLbl").style.display ="none";
	document.getElementById("emailFailLbl").style.display ="none";
	var activeLblId;
	for(var i=1; i<=5; i++)
	{
		if(document.getElementById("checkBtnLbl"+i).className =="btn btn-secondary active")
		{
			activeLblId = "checkBtnLbl"+i;
			document.getElementById("checkBtnLbl"+i).className ="btn btn-secondary"
		}
	}
	document.getElementById("studentNameText").className ='';
	document.getElementById("myNav").style.width = "100%";
	document.getElementById("loaderId").style.display='block'
		var uname = location.search.split("username=")[1];
	$('input[type="submit"]').attr("disabled","disabled");
	$("button").attr("disabled","disabled")
	$('#compResDiv').hide();
	$('#resHdrdiv').hide();
	$('#resDiv').hide();
	$('#similarityScoreLbl').hide();
//	$('#lcdChart').hide();
	//$("#graphDiag").hide();
	displayGuage(0);
	algorithmType = $('.algoSelec:checked').val();
	$("#noAlgoLbl").hide();
	$.ajax({
		type : "POST",
		url : "/compareFiles/",
		data: "algorithmType="+ algorithmType,
		success : function(data)
		{

			if(data.length>0)
			{

				var plagiarisedFilesCount =0
				var compTable = document.getElementById("compResTbl").getElementsByTagName('tbody')[0];
				$("#cmpTblBdy").empty();
				for(var i =0; i< data.length; i++)
				{
					if((data[i][0] == 0) && (data[i][1] == 0) && (data[i][2] == 0) )
					{
						break;
					}
					var row = compTable.insertRow(compTable.rows.length);
					row.id="compTbl"+i;
					var cell1 = row.insertCell(0);
					var cell2 = row.insertCell(1);
					var cell3 = row.insertCell(2);
					var cell4 = row.insertCell(3);
					cell1.innerHTML = i;
					cell2.innerHTML = studentNmeArr[data[i][0]].toUpperCase()+"  ( version no: " + versionNumberArr[data[i][0]]+ ")" ;
					cell3.innerHTML = studentNmeArr[data[i][1]].toUpperCase()+" ( version no: " + versionNumberArr[data[i][1]]+ ")";
					cell4.innerHTML = '<button type="button" class="btn btn-primary"  onclick="generateReport('+i+')">report</button>';
					cell4.id= data[i][0]+'-'+data[i][1]+'-'+i;

					if( data[i][2]>= threshold)
					{
						cell1.bgColor = "lightcoral";
						cell2.bgColor = "lightcoral";
						cell3.bgColor = "lightcoral";
						cell4.bgColor = "lightcoral";
					}


					plagiarisedFilesCount =plagiarisedFilesCount +1;
					if(algorithmType == "LCS" )
					{
						lcsResult[i] = data[i][2];
					}
					else if (algorithmType == "Levenshtein Distance" )
					{
						ldResult[i] = data[i][2];
					}
					else if (algorithmType == "AST Comparison" )
					{
						astResult[i] = data[i][2];
					}
					else if(algorithmType == "Run All")
					{

						avgScore[i] = data[i][2];

						lcsResult[i] = data[i][3];

						ldResult[i] = data[i][4];

						astResult[i] = data[i][5];

					}
					else
					{
						mossResult[i] = data[i][2]
					}


				}
				if(plagiarisedFilesCount >0)
				{
					$("#comparisonDropDown").show();
					$("#plagiarisedFileDiv").show();
				}
				else
				{
					$("#noFileLbl").show();
					$("#comparisonDropDown").hide();
					$("#plagiarisedFileDiv").hide();
				}

				$('#compResDiv').show();
//				$('#graphDiv').show();
//				$('#resHdrdiv').show();
//				$('#resDiv').show();
//				$("#graphDiv").hide();

				$('input[type="submit"]').removeAttr('disabled')
				$("button").removeAttr("disabled");
				document.getElementById(activeLblId).className ="btn btn-secondary active"
					document.getElementById("loaderId").style.display='none';
				document.getElementById("myNav").style.width = "0%";
				document.getElementById("studentNameText").className ='form-control divTransparent';


			}
			else
			{
				document.getElementById("loaderId").style.display='none';
				document.getElementById("myNav").style.width = "0%";
				document.getElementById("studentNameText").className ='form-control divTransparent';
				$('input[type="submit"]').removeAttr('disabled')
				$("button").removeAttr("disabled");
				$("#noFileSelecLbl").show();
				document.getElementById("errorSpeechBubl").innerHTML = "Please upload two or more files to compare";
				modal.style.display = "block";


			}

			sleep(20);
			$('html,body').stop().animate({
				scrollTop: $("#compare").offset().top},
				900, 'swing');
		}

	});

	$.ajax({
		type : "POST",
		url : "/setUsageCount",
		data: 'uname='+ uname,
		success : function()
		{

		}
	});

	$.ajax({
		type : "GET",
		url : "/getUsageCount",
		data: 'uname='+ uname,
		success : function(data2)
		{
			document.getElementById("usageCnt").innerHTML ="Usage Count: "+data2;
		}});
}

function generateReport(rowNo)
{
	document.getElementById("emailSuccLbl").style.display ="none";
	document.getElementById("emailFailLbl").style.display ="none";
	var activeLblId;
	for(var i=1; i<=5; i++)
	{
		if(document.getElementById("checkBtnLbl"+i).className =="btn btn-secondary active")
		{
			activeLblId = "checkBtnLbl"+i;
			document.getElementById("checkBtnLbl"+i).className ="btn btn-secondary"
		}
	}
	var tblRow1 = document.getElementById('compTbl'+rowNo);
	var indices1 = tblRow1.getElementsByTagName('td')[3].id;
	plagarisedFileIndices1 = indices1.split("-");
	var graphIndex1= plagarisedFileIndices1[2];
	printStudent1= document.getElementById("compTbl"+graphIndex1).getElementsByTagName("td")[1].innerHTML;
	printStudent2 = document.getElementById("compTbl"+graphIndex1).getElementsByTagName("td")[2].innerHTML;
	document.getElementById("studentNameText").className ='';
	document.getElementById("myNav").style.width = "100%";
	document.getElementById("loaderId").style.display='block';
	$('input[type="submit"]').attr("disabled","disabled");
	$("button").attr("disabled","disabled")
	if(algorithmType == "AST Comparison" || algorithmType == "Moss")
	{

		$("#lcsDiv").hide();
		$("#ldDiv").hide();
		$("#astDiv").hide();
		$('#resHdrdiv').hide();
		$('#resDiv').hide();
		$("#resTable").children().remove();
		$("#graphDiag").hide();

		var tblRow = document.getElementById('compTbl'+rowNo);
		var indices = tblRow.getElementsByTagName('td')[3].id;
		plagarisedFileIndices = indices.split("-");
		var graphIndex= plagarisedFileIndices[2];
		if(algorithmType == "AST Comparison")
		{

			displayGuage(astResult[graphIndex]);
			$("#similarityScoreLbl").show();
			//$('#lcdChart').show();
			$("#graphDiv").show();
			$("#graphDiag").show();

		}
		else
		{

			displayGuage(mossResult[graphIndex]);
			$("#similarityScoreLbl").show();
			//$('#lcdChart').show();
			$("#graphDiv").show();
			$("#graphDiag").show();
		}

		$('input[type="submit"]').removeAttr('disabled');
		$("button").removeAttr("disabled");
		document.getElementById("loaderId").style.display='none';
		document.getElementById(activeLblId).className ="btn btn-secondary active";
		document.getElementById("myNav").style.width = "0%";
		document.getElementById("studentNameText").className ='form-control divTransparent';

	}
	else
	{
		var plagarisedFileIndices=[];
		$("#lcsDiv").hide();
		$("#ldDiv").hide();
		$("#astDiv").hide();
		$('#resHdrdiv').hide();
		$('#resDiv').hide();
		$("#resTable").children().remove();
		var tblRow = document.getElementById('compTbl'+rowNo);
		var indices = tblRow.getElementsByTagName('td')[3].id;
		plagarisedFileIndices = indices.split("-");
		var graphIndex= plagarisedFileIndices[2];
		var fileindex1 =parseInt(plagarisedFileIndices[0]);
		var fileindex2 =parseInt(plagarisedFileIndices[1]);
		document.getElementById('colFileStudName1').innerHTML = document.getElementById("compTbl"+graphIndex).getElementsByTagName("td")[1].innerHTML;
		document.getElementById('colFileStudName2').innerHTML = document.getElementById("compTbl"+graphIndex).getElementsByTagName("td")[2].innerHTML;

		$.ajax({
			type: "POST",
			url: "/fetchPlagiarismReport/",
			data: 'fileindex1='+ fileindex1 + '&fileindex2='+ fileindex2 ,
			success : function(data)
			{

				displayComparison(data,parseInt(plagarisedFileIndices[0]),parseInt(plagarisedFileIndices[1]),graphIndex);
				$("#tblInset").show();
				$("#tableDiv").show();
				$('#resHdrdiv').show();
				$('#resDiv').show();
				$('input[type="submit"]').removeAttr('disabled')
				$("button").removeAttr("disabled");
				$("#similarityScoreLbl").show();
				//$('#lcdChart').show();
				$("#graphDiv").show();
				$("#graphDiag").show();


				document.getElementById("loaderId").style.display='none';
				document.getElementById(activeLblId).className ="btn btn-secondary active";
				document.getElementById("myNav").style.width = "0%";
				document.getElementById("studentNameText").className ='form-control divTransparent';

			},
			error : function(xhr, status, error) {
				$('input[type="submit"]').removeAttr('disabled')
				$("button").removeAttr("disabled");
				document.getElementById("loaderId").style.display='none';
				document.getElementById(activeLblId).className ="btn btn-secondary active"
					document.getElementById("myNav").style.width = "0%";
				document.getElementById("studentNameText").className ='form-control divTransparent';

			}
		});

	}


	setTimeout(function(){
		$('html,body').stop().animate({
			scrollTop: $("#report").offset().top},
			900, 'swing');
	}, 500);

}


function displayComparison(dataArray, fileIndex1,fileIndex2, graphIndex)
{
	var lineArray2 = {};
	var lineArray1 = {};
	//var lineArray2 =[];
	//var lineArray1 = [];
	for(var k=0; k< dataArray.length; k++)
	{
		if(dataArray[k] !=-1)
		{
			lineArray1[k] = 1;
			lineArray2[dataArray[k]] =1;
			//lineArray1.push(k);
			//lineArray2.push(dataArray[k]);
		}
	}
	//var k1 = 0;
	//var k2 = 0;
	var file1 = allFileArr[fileIndex1];
	var file2 = allFileArr[fileIndex2];
	var table = document.getElementById("resTable");
	var minLength = Math.min(file1.length,file2.length);

	for(var i = 0; i < minLength; i++)
	{
		var row = table.insertRow(i);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var cell4 = row.insertCell(3);
		cell1.innerHTML = i;
		cell2.innerHTML = file1[i];
		cell3.innerHTML = file2[i];
		cell4.innerHTML = i;
		cell1.id = "id1";
		cell2.id = "id2";
		cell3.id = "id3";
		cell4.id = "id4";
		cell1.bgColor ="#D3D3D3";
		cell4.bgColor ="#D3D3D3";
		//if(lineArray2[k2] == i)
		if(lineArray2[i])
		{

			cell3.bgColor=  "#dff0d8";
			//k2++;
		}
		//if(lineArray1[k2] == i)
		if(lineArray1[i])
		{
			cell2.bgColor=  "#dff0d8";
			//k1++;
		}

	}
	for(var j = minLength; j < file1.length; j++)
	{
		var row = table.insertRow(j);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var cell4 = row.insertCell(3);
		cell1.innerHTML = j;
		cell2.innerHTML = file1[j];
		cell3.innerHTML = " ";
		cell4.innerHTML = j;
		cell1.id = "id1";
		cell2.id = "id2";
		cell3.id = "id3";
		cell4.id = "id4";
		cell1.bgColor ="#D3D3D3";
		cell4.bgColor ="#D3D3D3";

		if(lineArray1[i])
		{
			cell2.bgColor = "#dff0d8";
			//k1++;
		}
	}
	for(var j1 = minLength; j1 < file2.length; j1++)
	{
		var row = table.insertRow(j1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var cell4 = row.insertCell(3);
		cell1.innerHTML = j1;
		cell2.innerHTML = " ";
		cell3.innerHTML = file2[j1];;
		cell4.innerHTML = j1;
		cell1.id = "id1";
		cell2.id = "id2";
		cell3.id = "id3";
		cell4.id = "id4";
		cell1.bgColor ="grey";
		cell4.bgColor ="grey";


		if(lineArray2[i])
		{
			cell3.bgColor = "#dff0d8";
			//k2++;
		}
	}
	algorithmType = $('.algoSelec:checked').val();
	if(algorithmType == "LCS")
	{
		displayGuage(lcsResult[graphIndex])
		$("#similarityScoreLbl").show();
		$("#graphDiag").show();

		//$('#lcdChart').show();
	}
	else if (algorithmType == "Levenshtein Distance" )
	{
		displayGuage(ldResult[graphIndex]);
		$("#similarityScoreLbl").show();
		$("#graphDiag").show();

		//$('#lcdChart').show();
	}
	else if (algorithmType == "Run All")
	{
		displayGuage(avgScore[graphIndex]);
		$("#similarityScoreLbl").show();
		$("#graphDiag").show();

		//$('#lcdChart').show();

	}
}

function sleep(milliseconds) {
	var start = new Date().getTime();
	for (var i = 0; i < 1e7; i++) {
		if ((new Date().getTime() - start) > milliseconds){
			break;
		}
	}
}

function emailReport() {
	document.getElementById("emailSuccLbl").style.display ="none";
	document.getElementById("emailFailLbl").style.display ="none";
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var toAddress = document.getElementById("emailAddrTxt").value;
	if(!toAddress.match(regex))
	{
		document.getElementById(emailInvalidLbl).style.display="block";
		document.getElementById(emailInvalidLbl).innerHTML ="* Enter valid e-mail address *"; 
	}
	else
	{
		$.ajax({
			type : "POST",
			url : "/emailReport",
			data: 'toAddress='+ toAddress,
			success : function(data)
			{
				document.getElementById("emailSuccLbl").style.display ="block";
				document.getElementById("emailSuccLbl").innerHTML ="Email sent successfully";
			},
			error : function(xhr, status, error) {
				document.getElementById("emailFailLbl").style.display ="block";
				document.getElementById("emailFailLbl").innerHTML ="*Something went wrong, Please try again*";
			}
		});


	}
}
