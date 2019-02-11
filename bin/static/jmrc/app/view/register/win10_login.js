//$(function() {
  //var $dynamicGrid = $("#dynamic-grid");
  //var $staticGrid = $("#static-grid");
  
  
  
var $dynamicGrid = $("#dynamic-grid");
var $staticGrid = $("#static-grid");
  //calculate page dom's size begin
  $(window).resize(function() {
    initContentSize();
    $dynamicGrid.gridList('reflow');
    $staticGrid.gridList('reflow');
  });
  

  function initContentSize() {
    var bodyHeight = $("body").height();
    var desktopHeader = $(".desktop-header").height();
    var desktopContentHeight = bodyHeight - desktopHeader - 50;
    desktopContentHeight = (desktopContentHeight < 650) ? "650" : desktopContentHeight;
    $(".desktop-content").height(desktopContentHeight);
    var mainHeight = $(".desktop-main").height();
    //主显示区域的宽度由其高度决定
    $(".desktop-main").css("min-width", mainHeight * 1.5 + 10);

    //header 的宽度也要随之改变，以保证宽度content的一致
    //$(".desktop-header").css("min-width", mainHeight * 1.5 + 10 + 150);
    if ($(".static-grid-container").css("display") === "inline-block") {
      $("#mainFrameZone").width($(".desktop-main").width() - 10 - mainHeight / 2 - 5);
    } else {
      $("#mainFrameZone").width($(".desktop-main").width() - 10 - 5);
    }
    $(".dynamic-grid-container").width(mainHeight);

    $(".static-grid-container").width(mainHeight / 2);
  }
  //calculate page dom's size end

  // init grid begin
  initContentSize();
 

//显示中间内容函数
  function showFrame(appId, url, isWorkBenchApp) {
    hideFrame();
    $(".dynamic-grid-container").hide();
    $("#mainFrameZone").css("display", "inline-block");
    var $frame = $("#mainFrameZone iframe[id=" + appId + "]");
    if ($frame && $frame.length > 0) {
      if ($frame.attr("src") !== url) {
        $frame.attr("src", url);
      }
      $frame.show();
    } else {
      $frame = $("<a href='javascript:;' class='close_btn'>关闭</a><iframe id='" + appId + "' src='" + url + "' allowTransparency='true' style='height: 100%; width: 100%' frameborder='0'></iframe>")
      $("#mainFrameZone").append($frame);
    }
    if (isWorkBenchApp) {
      $(".desktop-startbutton").show();
      $(".desktop-menu").show();
      $(".static-grid-container").show();
      //if($(this).attr("menuId") === "workbench"){
        

    	  //$appTab.addClass("active");
        $(".desktop-main").css("margin-left","");
        $("#mainFrameZone").css("background-color","");
    	  $("#mainFrameZone").width($(".desktop-main").width() - 10 - $(".desktop-main").height() / 2 - 5);
        
      /*}else{
    	  $(".static-grid-container").hide();
    	  $(".desktop-menu").hide();
    	  $(".desktop-startbutton").hide();
    	  $("#mainFrameZone").width($(".desktop-content").width());
      }*/
           
    } else {
//      $(".static-grid-container").hide();
//      $("#mainFrameZone").width($(".desktop-main").width() - 10 - 5);
    	  /*$(".static-grid-container").hide();
	   	  $(".desktop-menu").hide();
	   	  $(".desktop-startbutton").hide();
	   	  $("#mainFrameZone").width($(".desktop-content").width());*/
        $(".desktop-main").css("margin-left","0");
        $("#mainFrameZone").css("background-color","transparent");
        $(".static-grid-container").hide();
        $(".desktop-menu").hide();
        $(".desktop-startbutton").hide();
        $("#mainFrameZone").width($(".desktop-content").width()-10);
    }
  }

//隐藏中间内容函数
  function hideFrame() {
    $("#mainFrameZone").hide();
    $("#mainFrameZone iframe").hide();
  }

//显示回瓷砖页面
  function showDynamicGrid() {
    hideFrame();
    $(".desktop-menu").show();
    $(".dynamic-grid-container").show();
    $(".static-grid-container").show();
  }

//隐藏回中间界面函数
  function removeFrame(appId) {
    $("#mainFrameZone .close_btn").remove();
    $("#mainFrameZone iframe[id=" + appId + "]").remove();
  }

  //登录获取焦点触发
  $("#static-grid").on("focus","#login .login_input input",function  () {
    
    $(this).next().hide();
    
  })
  //登录获取焦点触发
  $("#static-grid").on("click","#login .login_input .login_tips",function  () {
    /*if ($(this).find("input").val() == '') {
      $(this).val('');
    };*/
    $(this).hide();
    $(this).prev().focus();
    
  }).on("blur","#login .login_input input",function  () { //失去焦点触发
    if ($(this).val() == '') {
      $(this).next().show();
    };
    
  })
  
  //栏目内容关闭按钮事件
  $(".desktop-content").on("click",".close_btn",function  () {
    showDynamicGrid();
    removeFrame('a')
  })
  

 //中间版块点击事件
  $("#dynamic-grid").on("click",".dialog_box",function  (e) {
   
      var dataHref = $(this).attr("href");
      var id = $(this).attr("id");
      
      if(id != "new_list"){//禁止新闻栏目跳转
    	  if (dataHref != 'undefined' && typeof dataHref != 'undefined' ) {//不存在链接禁止跳转
    	       
	        showFrame('a',dataHref,true);
	      };
      }
      
      
  });

//新闻栏目幻灯片
  function slideBox (newLength,obj,slideWidth) {
    var that = this;
    //幻灯片id
    that.objHtml = obj;
    
    
    
    
    that.newNum = 0;
    that.autoTime = null;
    $("#"+that.objHtml+".dialog_box .new_list a").eq(0).attr("bgisfirst",true);
  
    
    function slideAuto (thisIndex) {
      if (that.autoTime) {
    	  clearInterval(that.autoTime)  
      }
      var _thisIndex = +thisIndex 
      if (typeof _thisIndex == 'number' && !isNaN(_thisIndex)) {
    	  slideAnimate(thisIndex);
      }
      that.newNum = thisIndex || that.newNum;

      that.autoTime = setInterval(function () {
        if (that.newNum < newLength -1) {
          that.newNum ++;
        }else{
          that.newNum  = 0;
        }
        slideAnimate(that.newNum);
      },3000);
    }
    slideAuto();
    function slideAnimate (iNum) {
      that.newWidth = $("#"+that.objHtml+".dialog_box").width(); 
      var junli = 0;
      junli = -iNum*that.newWidth;
      //幻灯片宽度
     
      $("#"+that.objHtml+">.new_list>a").width(that.newWidth);
      $("#"+that.objHtml+".dialog_box .new_page a").removeClass("active").eq(iNum).addClass("active");
      $("#"+that.objHtml+".dialog_box .new_list").stop(true, true).animate({'margin-left':junli},700);

      var bgIsFirst=$("#"+that.objHtml+".dialog_box .new_list a").eq(iNum).attr("bgisfirst");
      if (!bgIsFirst) {
        $("#"+that.objHtml+".dialog_box .new_list a").eq(iNum).attr("bgisfirst",true);
      }
    }

    return slideAuto;
  }
  
  //右侧板块跳转
  $("#static-grid").on("click",".dialog_box",function  (e) {
    e.stopPropagation();
    var dataHref = $(this).attr("href");
    var menuBoxId = $(this).attr("id");

    if(menuBoxId == 'treat_work' || menuBoxId == 'login' || menuBoxId == 'new_small_list'){//点击登录、新闻栏目、待办禁止跳转
 	   return;
    }
    if (dataHref != 'undefined' && typeof dataHref != 'undefined' ) {//不存在链接禁止跳转
    	
    	
    	showFrame('a',dataHref,true);
        
      
    }
    
       
     
  })
  
   function newUrl(dataHref){
	  showFrame('a',dataHref,true);
  }

$(".win10_alert_box .button_link").click(function(){
	$(".win10_alert_box").removeClass("active");
})
$(document).keyup(function(e){
	if(e.keyCode == "27"){
		$(".win10_alert_box").removeClass("active");
	}
});

var alertObj;
var nui = {};
nui.alert = function(text,tit,obj){
	$("#alert_box .content").text(text);
	$("#alert_box").addClass("active");
	alertObj = obj;
}
$("#alert_box .button_link").click(function(){
	$("#alert_box").removeClass("active");
	if(typeof alertObj == 'function'){
		alertObj();
	}
})


//});