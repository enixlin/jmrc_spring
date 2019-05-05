/**
 * 
 */

Ext.define('jmrc.store.TreeStore', {
    extend: 'Ext.data.TreeStore',

    alias: 'store.treeStore',
    autoLoad: true,
    
    root: {
        expanded: true,
        text: "My Root",
   
    },

    fields: [
        'name', 'url', 'icon', 'leaf','panel','parent_id','id'
    ],
    proxy: {
        type: 'ajax',
        url: '/feature/getAllFeature',
       
    },
 
    
});