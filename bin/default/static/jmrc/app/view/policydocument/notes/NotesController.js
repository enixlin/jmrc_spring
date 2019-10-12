Ext.define('jmrc.view.policydocument.notes.NotesController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.policydocument-notes-notes',
    afterRender:function(){
      
         let me=this;
         let view=me.getView();
         console.log("note view");
 			console.log(view);
         let record=view.record;
         let windowTitle="  ***【笔记】****  "+record.data["title"];
         let winWidth=view.getWidth();
         let winHeight=view.getHeight()*0.95;
          me.getView().setTitle(windowTitle);
          let htmlContent=record.data["note"]["content"];
         
        	  let htmlEditor=Ext.create({
        		  xtype:"htmleditor",
        		  fixed:true,
        		  enableLinks:true,
        		  height:window.innerHeight*0.75,
        		  width:winWidth,
        		  scrollable:true,
        		  
        		  });
        	 
        	  htmlEditor.setValue(htmlContent);
        	  view.add(htmlEditor);
 
         

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
    
    
    getHtmlContentById:function(Id,cb){
        Ext.Ajax.request({
           url:"/policydocument/queryNoteById",
           params:{"Id":Id},
           success:function(result){
            cb(result);
           }
        });
    },
    
    
    
    saveCustomerNote(){
    	let me=this;
    	let view=me.getView();
    	let record=view.record;
    	let content=view.query("htmleditor")[0].value;
    	let userId=record.data["note"]["userId"];
    	let dId=record.data["note"]["dId"];
    	console.log(content);
    	
    	Ext.Msg.show({
		    title:'是否要覆盖原来的笔记?',
		    message: '你已经在系统上对这篇文件保存了笔记，如果用当前的内容覆盖原来原来的笔记，请选择【是】，请选择【否】?',
		    buttons: Ext.Msg.YESNOCANCEL,
		    icon: Ext.Msg.QUESTION,
		    fn: function(btn) {
		        if (btn === 'yes') {
		            console.log('Yes pressed');
		            if(confirm("再次确认，你真的要覆盖原来的笔记？旧笔记被覆盖就没有办法恢复了！")){   	
		            	 Ext.Ajax.request({
		                     url:"/policydocument/saveNotes",
		                     method:"post",
		                     params:{
		                    	 'id':record.data["note"]["id"],
		                     	"dId":record.data["dId"],
		                     	"content":content,
		                     	"userId":CFG.getUserInfo().id,
		                     	"modify":Date.now(),
		                     	"overWrite":record.data["overWrite"]
		                     	},
		                     success:function(result){
		                    	 // let note=eval("("+result.responseText+")");	
		                    	// alert("保存成功");
		                    	 Ext.Msg.alert("保存成功");
		                    	 me.loadNote(userId,dId);
		                     }
		                  });
		            
		            }
		        } else if (btn === 'no') {
		            console.log('No pressed');

		        } else {
		        	
		        }
		    }
		});

    	// let noteContent=this
    },
    
    loadNote:function(userId,dId){
    	let me=this;
    	let view=me.getView();
    	let record=view.record;
    	 Ext.Ajax.request({
             url:"/policydocument/queryUserNoteBydId",
             method:"post",
             params:{
             	"dId":dId,
             	"userId":userId
             	},
             success:function(result){
             	let note=eval("("+result.responseText+")");
             
             	me.getView().query("htmleditor")[0].setValue(note.content);
             	me.getView().record.data["note"]
             }
          });
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
