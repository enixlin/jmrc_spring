Ext.define('jmrc.view.policydocument.file.fileController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.policydocument-file-file',
    
    afterRender:function(){
         let me=this;
         let view=me.getView();
         let record1=view.record;
         let container=me.getView().query("container")[1];
         
         //container.insert(0,"->");
         container.insert(0,{xtype:"textfield",fieldLabel:"文号",width:500,value:record1.data["docNum"]})
         console.log(container);
      
         let windowTitle="  ***【原文】****  "+record1.data["title"];
         let winWidth=view.getWidth();
         let winHeight=view.getHeight()*0.98;
          me.getView().setTitle(windowTitle);
          
          me.getHtmlContentBydId(record1.data["dId"],function(result){
        	  let response=eval("("+result.responseText+")");
        	  let htmlContent=response[0]["htmlContent"];
        	
        	  		
        	  let htmlEditor=Ext.create({
        		  xtype:"htmleditor",
        		  modal:true,
        		
        		  height:window.innerHeight*0.75,
        		  width:winWidth,
        		  scrollable:true,
        		  
        		  });
        	  htmlEditor.setValue(htmlContent,true);
        	  view.add(htmlEditor);
 
         });

    },
 
    getHtmlContentBydId:function(dId,cb){
        Ext.Ajax.request({
           url:"/policydocument/queryBydId",
           params:{"dId":dId},
           success:function(result){
            cb(result);
           }
        });
    },
    
    openNoteBook:function(){
    	let me=this;
    	let view=me.getView();
    	let view1=view.up();
    	let record=view.record;
    	console.log("file view");
		console.log(view1);
    	//构造一个查询对象
    	let note={
    			userId:CFG.getUserInfo().id,
    			dId:record.data["dId"],
    			overWrite:false,
    			};

        Ext.Ajax.request({
            url:"/policydocument/openNotes",
            method:"post",
            params:{
            	"dId":note.dId,
            	"userId":note.userId,
            	"overWrite":note.overWrite,
            	},
            success:function(result){
            	
            	let parentWidth=view1.getWidth()*0.7;
				let winHeight=view1.getHeight();
            	let note=eval("("+result.responseText+")");
            	record.data['note']=note;
            	let noteWindow=Ext.create({xtype:"notes",record:record,width:parentWidth,height:window.innerHeight*0.9,});
            	view1.add(noteWindow);
            	noteWindow.show();
            
            }
         });
    	// let noteContent=this
    },
    
    
    printFile(){
    	// var printHtml = document.getElementById(myDiv).innerHTML;
    	let me=this;
    	let view=me.getView();
    	let record=view.record;
    
    	let content=view.query("htmleditor")[0].value;

    	var wind = window.open("",'newwindow', 'height=300, width=700, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');

    	wind.document.body.innerHTML = content;

    	wind.print();
    	return false; 
    }

});
