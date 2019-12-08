Ext.define("jmrc.view.performance.tf.tf", {
    extend: "Ext.panel.Panel",
    xtype: "tf",
    requires: [
        "jmrc.view.performance.tf.tfController",
        "jmrc.view.performance.tf.tfModel"
    ],

    controller: "performance-tf-tf",
    viewModel: {
        type: "performance-tf-tf"
    },

});