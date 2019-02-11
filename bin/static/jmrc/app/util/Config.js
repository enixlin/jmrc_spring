/**
 * 全局配置类，用于保存用户信息，用户喜好主题等信息
 * 
 */
Ext.define('jmrc.util.Config', {
    singleton: true, //单例类型
    alternateClassName: "CFG",
    config: {
        userInfo:null, //用户信息
        rolerInfo:null,//选择的角色
        roler_list: [],
        appName: 'JMRCB',
        titleBanner:"国际业务信息系统",
        welcomeMessage: '欢迎使用JMRCB国际业务信息系统',
        version:"V1.0",
        theme: null
    },
    constructor: function(config) {
        this.initConfig(config);
        this.callParent(arguments);

    }
});