<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<style>
html, body {
	width: 100%; /*provides the correct work of a full-screen layout*/
	height: 100%; /*provides the correct work of a full-screen layout*/
	overflow: hidden; /*hides the default body's space*/
	margin: 0px; /*hides the body's scrolls*/
}

div.controls {
	margin: 0px 10px;
	font-size: 14px;
	font-family: Tahoma;
	color: #404040;
	height: 80px;
}

.my_ftr {
	background-color: white;
	padding-top: 9px;
	
}

.my_ftr .text {
	font-family: Roboto, Arial, Helvetica;
	font-size: 14px;
	color: #404040;
	padding: 5px 10px;
	height: 70px;
	border: 1px solid #dfdfdf;
	overflow: scroll;
}

</style>
<script> 

var bodyLayout, dbTree,winF,popW; 
var aLay, bLay, cLay;
var bTabs, bTab1, bTab2, bTab3;
var cTabs;
var tableInfoGrid;
function columnListCB(res){
	if(res.list){
		tableInfoGrid = bTabs.tabs("tableInfo").attachGrid();
		var columns = res.list[0];
		var headerStr = "";
		var colTypeStr = "";
		for(var key in columns){
			if(key=="id") continue;
			headerStr += key + ",";
			colTypeStr += "ro,";
		}
		headerStr = headerStr.substr(0, headerStr.length-1);
		colTypeStr = colTypeStr.substr(0, colTypeStr.length-1);
        tableInfoGrid.setColumnIds(headerStr);
		tableInfoGrid.setHeader(headerStr);
		tableInfoGrid.setColTypes(colTypeStr);
        tableInfoGrid.init();
		tableInfoGrid.parse({data:res.list},"js");		
	}
	if(res.tDList){
		console.log(res.tDList);
		tableDataGrid = bTabs.tabs("tableData").attachGrid();
		var columns = res.tDList[0];
		var headerStr = "";
		var colTypeStr = "";
		for(var key in columns){
			if(key=="id") continue;
			
			headerStr += key + ",";
			colTypeStr += "ro,";
		}
		headerStr = headerStr.substr(0, headerStr.length-1);
		colTypeStr = colTypeStr.substr(0, colTypeStr.length-1);
		tableDataGrid.setColumnIds(headerStr);
		tableDataGrid.setHeader(headerStr);
		tableDataGrid.setColTypes(colTypeStr);
		tableDataGrid.init();
		tableDataGrid.parse({data:res.tDList},"js");
			
		
	}		
}

function connectionListCB(res){
	dbTree = aLay.attachTreeView({
	    items: res.list 
	});
	dbTree.attachEvent("onDblClick",function(id){
		var level = dbTree.getLevel(id);
		if(level==2){
			var text = dbTree.getItemText(id);
			var au = new AjaxUtil("${root}/connection/tables/" + text + "/" + id,null,"get");
			au.send(tableListCB); 
			$("div.text").append("<br>"+"use " + "'" + text + "'" + ";");
		}else if(level==3){
			var pId= dbTree.getParentId(id);
			var dbName = dbTree.getItemText(pId);			
			var tableName = dbTree.getUserData(id,"orgText");			
			var au = new AjaxUtil("${root}/connection/columns/" + dbName + "/" + tableName,null,"get");
			au.send(columnListCB);
		} 
	});
}
function tableListCB(res){
	var parentId = res.parentId;
	var i=1;
	for(var table of res.list){
		var id = parentId + "_" + i++;
		var text = table.tableName;
		if(table.tableComment!=""){
			text += "[" + table.tableComment + "]";
		}
		text += ":"+ table.tableSize + "KB"; 
		dbTree.addItem(id, text, parentId);
		dbTree.setUserData(id,"orgText",table.tableName);
	}
	dbTree.openItem(parentId);
}
function addConnectionCB(res){
	console.log(res);
}
function dbListCB(res){
	console.log(res);
	if(res.error){
		alert(res.error);
		return;
	}
	var parentId = res.parentId;
	for(var db of res.list){
		var id = db.id;
		var text = db.text;
		dbTree.addItem(id, text, parentId);
	}
	dbTree.openItem(parentId);
}
dhtmlxEvent(window,"load",function(){
	bodyLayout = new dhtmlXLayoutObject(document.body,"3L");
	bodyLayout.attachFooter("footDiv");
	aLay = bodyLayout.cells("a");
	aLay.setWidth(300);
	aLay.setText("Connection Info List");
	var aToolbar = aLay.attachToolbar();
	aToolbar.addButton("addcon",1,"add Connector");
	aToolbar.addButton("condb",2,"Connection");
	aToolbar.attachEvent("onClick",function(id){
		if(id=="condb"){
			var rowId =dbTree.getSelectedId();
			if(!rowId){
				alert("접속할 커넥션을 선택해주세요.");
				return;
			}
			var au = new AjaxUtil("${root}/connection/db_list/" + rowId,null,"get");
			au.send(dbListCB); 
		}else if(id=="addcon"){
			popW.show();
		}
	})
	var au = new AjaxUtil("${root}/connection/list",null,"get");
	au.send(connectionListCB); 
	

	bLay = bodyLayout.cells("b");
	bTabs = bLay.attachTabbar({
		align:"left",
		tabs:[
			{id:"tableInfo", text:"Table Info"},
			{id:"tableData", text:"Table Datas"},
			{id:"sql", text:"Run Sql", active:true},
			{id:"translate", text:"Run Translate"}
		]
	});
	
	
	var sqlFormObj = [
		{type: "block", blockOffset: 10, list: [
			{type: "button", name:"runBtn",value: "실행"},
			{type: "newcolumn"},
			{type: "button", name:"cancelBtn",value: "취소"} 
		]},
		{type:"label",name:"label", label:"", list:[
			{type:"input",name:"sqlTa",label:"",required:true,rows:12,style:"border:1px solid #39c"}
		]}
	];
		
	var sqlForm = bTabs.tabs("sql").attachForm(sqlFormObj);	
	
	var w = bLay.getWidth();
	var stylePlusWidth = w - 45;
	var Names = document.getElementsByName("sqlTa");
	for(var name of Names){
		name.style.width = stylePlusWidth + "px";	
	}
	
	sqlForm.attachEvent("onButtonClick", function(name){		
		if(name=="runBtn"){
			var sql = sqlForm.getItemValue("sqlTa").trim();			
			var sqlLength = sql.length;
			var semiColNum = sql.lastIndexOf(";");				
			if(semiColNum != -1 && sqlLength == (semiColNum+1)){
				sql = sql.substr(0, (sqlLength-1));		
			}	
			
			var sqlArr = [];			
			splArr = sql.split(";");	
			
			if(splArr.length == 1){
				if(sql.indexOf("select") == 0){
					var au = new AjaxUtil("${root}/sql/query/"+sql,null,"post");			
					function queryCB(res){
						if(res.errorMsg){
							alert(res.errorMsg);
							
						}else{
							var cLayGrid = cLay.attachGrid();
							var headerStr = "";
							var colTypeStr = "";				
							if(res.list[0] != null){
								for(var key in res.list[0]){						
									headerStr += key + ",";
									colTypeStr += "ro,";
								} 					
							}
							headerStr = headerStr.substr(0, headerStr.length-1);
							colTypeStr = colTypeStr.substr(0, colTypeStr.length-1);				
							cLayGrid.setColumnIds(headerStr);
							cLayGrid.setHeader(headerStr);
							cLayGrid.setColTypes(colTypeStr);
							cLayGrid.init();  					
							cLayGrid.parse({data:res.list},"js");
													
							var aRows = 0;
							var dRows = 0;
							if(res.dRows){
								dRows = res.dRows;
							}
							if(res.result){
								aRows = res.result;
							}						
							$("div.text").append("<br>"+"Affected rows : " + aRows + "  Discoverd rows : " + dRows);
						}					
					}			
					au.send(queryCB); 
				}			
				else{				
					var au = new AjaxUtil("${root}/sql/update/"+sql,null,"post");
					function updateCB(res){
						if(res.errorMsg){
							alert(res.errorMsg);						
						}else{
							if(res.result != 0 ){						
								var aRows = 0;
								var dRows = 0;
								if(res.dRows){
									dRows = res.dRows;
								}
								if(res.result){
									aRows = res.result;
								}						
								$("div.text").append("<br>"+"Affected rows : " + aRows + "  Discoverd rows : " + dRows);
								cLay.detachObject();
							}else{
								alert("실패");	
							}					
						}
					}				
					au.send(updateCB);
				}
			}
			
			
			else{	
				
				$.ajax({ 
					url : "${root}/sql/multi",				  
					dataType : "json", 				   
					data     : sql,
					type	: "POST",					
				    beforeSend:function(xhr){
				    	xhr.setRequestHeader("Accept","application/json");
				    	xhr.setRequestHeader("Content-Type","application/json; charset=UTF-8");
				    },					
				   	success : function(res){
				   		
				   		cTabs = cLay.attachTabbar();	
				   		var aRows = 0;
						var dRows = 0;
						
				   		for(var key in res){
				   			if(key!="dRows" && key!="result"){
				   				cTabs.addTab(key, "Result", null, null, true, true);
				   				var cTGrid = cTabs.tabs(key).attachGrid();
					   			var headerStr = "";
								var colTypeStr = "";
								for(var listCol in (res[key])[0]){						
									headerStr += listCol + ",";
									colTypeStr += "ro,";
								}
								headerStr = headerStr.substr(0, headerStr.length-1);
								colTypeStr = colTypeStr.substr(0, colTypeStr.length-1);				
								cTGrid.setColumnIds(headerStr);
								cTGrid.setHeader(headerStr);
								cTGrid.setColTypes(colTypeStr);
								cTGrid.init();  					
								cTGrid.parse({data:res[key]},"js"); 
							}
				   			else{
				   				
				   				if(res.dRows){
									dRows = res.dRows;
								}
								if(res.result){
									aRows = res.result;
								}		
				   			}
				   		}
				   		$("div.text").append("<br>"+"Affected rows : " + aRows + "  Discoverd rows : " + dRows);
				   		
				   	},
				    error : function(xhr, status, e) {
					    	alert("에러 : "+e);
					}					
				});		
				
			}
			
		}else if(name=="cancelBtn"){			
			sqlForm.clear();
			
		}
	});
	
	
	var transFormObj = [
		{type: "block", blockOffset: 10, list: [
			{type: "button", name:"runBtn",value: "실행"},
			{type: "newcolumn"},
			{type: "button", name:"cancelBtn",value: "취소"} 
		]},
		{type:"label",name:"label", label:"", list:[
			{type:"input",name:"transTa",label:"",required:true,rows:10,style:"background-color:#ecf3f9;border:1px solid #39c;width:800"}
		]}
	];
	var transForm = bTabs.tabs("translate").attachForm(transFormObj);
	
	transForm.attachEvent("onButtonClick",function(id){
		if(id=="runBtn"){			
			var translate = transForm.getItemValue("transTa").trim();
			alert(translate);
			var au = new AjaxUtil("${root}/sql/trans/" + translate,null,"get");
			function translateCB(res){					
				var transEditor = cLay.attachEditor({content:res.str});									
			}			
			
			au.send(translateCB);						
			
		}else if(id=="cancelBtn"){
			
		}		
	});
	
	
	
	cLay = bodyLayout.cells("c");
	winF = new dhtmlXWindows();
	popW = winF.createWindow("win1",20,30,320,300);
	//popW.hide(); 
	popW.setText("Add Connection Info"); 
	var formObj = [
		        {type:"settings", offsetTop:12,name:"connectionInfo",labelAlign:"left"},
				{type:"input",name:"ciName", label:"커넥션이름",required:true},
				{type:"input",name:"ciUrl", label:"접속URL",required:true},
				{type:"input",name:"ciPort", label:"PORT번호",validate:"ValidInteger",required:true},
				{type:"input",name:"ciDatabase", label:"데이터베이스",required:true},
				{type:"input",name:"ciUser", label:"유저ID",required:true},
				{type:"password",name:"ciPwd", label:"비밀번호",required:true},
				{type:"input",name:"ciEtc", label:"설명"},
				{type: "block", blockOffset: 0, list: [
					{type: "button", name:"saveBtn",value: "저장"},
					{type: "newcolumn"},
					{type: "button", name:"cancelBtn",value: "취소"}
				]}
		];
	var form = popW.attachForm(formObj,true);
	popW.hide();
	
	form.attachEvent("onButtonClick",function(id){
		if(id=="saveBtn"){
			if(form.validate()){
				form.send("${root}/connection/insert", "post",addConnectionCB);
			}
		}else if(id=="cancelBtn"){
			form.clear();
			popW.hide();
		}
	});	
	
	
	var waitForFinalEvent = (function() {
		var timers = {};
		return function(callback, ms, uniqueId) {
			if (!uniqueId) {
				uniqueId = "Don't call this twice without a uniqueId";
			}
			if (timers[uniqueId]) {
				clearTimeout(timers[uniqueId]);
			}
			timers[uniqueId] = setTimeout(callback, ms);
		};
	})();


	$(window).resize(function() {  
		waitForFinalEvent(function() {
			var w = bLay.getWidth();
			var stylePlusWidth = w - 45;
			var Names = document.getElementsByName("sqlTa");
			for(var name of Names){
				name.style.width = stylePlusWidth + "px";	
			}
		}, 300, "some unique string");
	});
	
	
})










	



/* $(window).unload(function() {
      var au = new AjaxUtil("${root}/user/logout", null, "get");
      au.send();
}); */

</script>
<body>
	<div id="footDiv" class="my_ftr">
		<div class="text">log</div>
	</div>
</body>
</html>