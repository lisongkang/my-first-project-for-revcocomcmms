$(function() {
    $(".grid-sys-pub-post-index").data("gridOptions", {
        url : WEB_ROOT + "/common/pubpost/pub-post!findByPage",
        colNames : [ '阅读人数', '标题', '外部链接', '前端显示', '后端显示', '排序号', '发布人', '生效时间', '到期时间'],
        colModel : [ {
            name : 'readUserCount',
            width : 60,
            fixed : true,
            align : 'center'
        }, {
            name : 'htmlTitle',
            editable : true,
            align : 'left'
        }, {
            name : 'externalLink',
            editable : true,
            align : 'left'
        }, {
            name : 'frontendShow',
            width : 60,
            editable : true,
            edittype : 'checkbox'
        }, {
            name : 'backendShow',
            width : 60,
            editable : true,
            edittype : 'checkbox'
        }, {
            name : 'orderRank',
            width : 60,
            sorttype : 'number',
            editable : true
        }, {
            name : 'extraAttributes.opername',
            align : 'center',
            sortable : false,
            editable : false,
            width : 80
        }, {
            name : 'publishTime',
            formatter : 'timestamp',
            editable : true,
            editoptions : {
                time : true
            },
            align : 'center'
        }, {
            name : 'expireTime',
            formatter : 'timestamp',
            editable : true,
            editoptions : {
                time : true
            },
            align : 'center'
        } ],
        sortname : "publishTime",
        editcol : 'htmlTitle',
        editurl : WEB_ROOT + "/common/pubpost/pub-post!doSave",
        delurl : WEB_ROOT + "/common/pubpost/pub-post!doDelete",
        //fullediturl : WEB_ROOT + "/common/pubpost/pub-post!edit",
        fullediturl : WEB_ROOT + '/common/pubpost/pub-post!inputTabs',
        subGrid : true,
        subGridRowExpanded : function(subgrid_id, row_id) {
            Grid.initSubGrid(subgrid_id, row_id, {
                url : WEB_ROOT + "/common/pubpost/pub-post-read!findByPage?search['EQ_pubPost.id']=" + row_id,
                colNames : [ '阅读用户', '首次阅读时间', '最后阅读时间', '总计阅读次数' ],
                colModel : [ {
                    name : 'readUserLabel',
                    width : 150
                }, {
                    name : 'firstReadTime',
                    formatter : 'timestamp'
                }, {
                    name : 'lastReadTime',
                    formatter : 'timestamp'
                }, {
                    name : 'readTotalCount',
                    formatter : 'number'
                } ],
                sortname : "lastReadTime"
            });
        }
    });
});