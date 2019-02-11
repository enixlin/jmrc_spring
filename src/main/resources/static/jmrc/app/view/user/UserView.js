Ext.define('jmrc.view.user.UserView', {
    extend: 'Ext.panel.Panel',
    xtype: 'user',

    requires: [
        'jmrc.view.user.UserViewController',
        'jmrc.view.user.UserViewModel'
    ],
    
 


    controller: 'user-userview',
    viewModel: {
        type: 'user-userview'
    },

    items: [{
        xtype: 'grid',
        title: '用户管理',
        border:true,
        reference:"usergrid",
        store:null,
        tbar: [{ text: '新增', handler: 'showAddUser' }, { text: '刷新', handler: 'refresh' }, { text: 'hidden', handler: 'hidderAllWin' }, { text: 'post', handler: 'post' }, { text: 'post', handler: 'post' }],


        default: { align: "center" },
        columns: [
            { text: '用户编号', dataIndex: 'id', align: 'center' },
            { text: '用户姓名', dataIndex: 'name', flex: 1, align: 'center' },
            { text: '密码', dataIndex: 'password', flex: 1, align: 'center' },
            { text: '角色管理', flex: 1, align: 'center' ,layout:'fit',
            	renderer:function(value,cellmeta){
                   return    "<INPUT type='button' value='管理角色'>";
                  }
            },
            { text: '状态(0-停用 1-正常)', dataIndex: 'status', flex: 1, align: 'center' }
            ,
            { text: '用户维护',  flex: 1, align: 'center' ,renderer:function(value,cellmeta){
                return    "<INPUT type='button' value='编辑'><INPUT type='button' value='删除'>";
            }}
        ],
        //弹出[管理角色]的窗口
        listeners:{
            cellClick: function(thisTab, td, cellIndex,record,tr,rowIndex,event,eventObj) {
            	//因为这个监听函数定义在grid中,所以this就是指向grid,grid的父容器是UserView ,所以要先up()
            	//取得UserView后,再调用UserController里的showRoelrSelect函数
            	if(cellIndex==3){            		
            		this.up().controller.showRolerSelect(event,record)
            	}
            	if(cellIndex==5 && event.target.value=="删除"){            		
            		this.up().controller.deleteUser(event,record);
            	}

            }
        },

    }]
});