Ext.define("jmrc.store.LoginUser", {
  extend: "Ext.data.Store",

  alias: "store.loginuserStore",
  autoLoad: true,

  fields: ["name", "id"],
  proxy: {
    type: "ajax",
    url: "/auth/getLoginedUser"
  }
});
