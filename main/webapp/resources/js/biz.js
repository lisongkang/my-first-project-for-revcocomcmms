
/**
 * Custom module for you to write your own javascript functions
 */

var Biz = function() {

    // private functions & variables

    var CacheDatas = {};
    
    var CustInfo = {};
    
    var LOGIN_INFO = {};

    // public functions
    return {
    	
        init : function() {
        	//客户选取
            Biz.setupCustomerProfileSelect();
            Biz.showCustomerInfo();
            //初始化客户信息
            Biz.initCustInfo();
        },
        
        showMenu : function(obj){
        	var siblings =$(obj).parent().siblings('.open');
        	$.each(siblings, function(i, item) {
        		$(item).children().first().click();
            });
        },
        
        getCacheSelectOptionDatas : function(gcode,mcode,c){
        	if(!gcode) {
        		return {"" : ""};
        	}
        	var url = WEB_ROOT + "/prv-sysparam!selectParamList";
        	url = url + "?gcode=" + gcode;
        	
        	var cacheData = Util.getCacheSelectOptionDatas(url,c);
        	
        	if(mcode){
				var j = {"" : ""};
				if(b[mcode]){
					j[mcode]=cacheData[mcode];
				}
				return j;
			}
        	
        	return cacheData;
        },
        
		getCacheParamDatas : function(gcode,mcode, c,mcodeSeparator,isCache) {
        	if(isCache==null || isCache==undefined){
                isCache = true;
			}
			if(gcode==undefined || gcode==""){
				return {"":""};
			}
			
			if (c == undefined) {
				c = $("body");
			}
			
			if (c.data("CacheParamDatas") == undefined) {
				c.data("CacheParamDatas", {});
			}
			var b = c.data("CacheParamDatas")[gcode];
			
			if (b == undefined || !isCache) {
				var paramdata = {};
				paramdata.gcode = gcode;
				paramdata.mcode = mcode;
                paramdata.mcodeSeparator = mcodeSeparator;
				$.ajax({
					async : false,
					type : "GET",
					url : WEB_ROOT + "/prv-sysparam!getSelectData",
					dataType : "json",
					data : paramdata,
					success : function(e) {
						var d = e;
						if (e.content) {
							d = e.content;
						}
						b = {
							"" : ""
						};
						$.each(d, function(f, g) {
							b[f] = g;
						});
						c.data("CacheParamDatas")[gcode] = b;
					}
				});
				
			} 
			
			CacheDatas.cacheParamDatas=b;
			
			return CacheDatas.cacheParamDatas;
		},
        getAllTown : function() {
        	if (CacheDatas.towns == null) {
        		var url = WEB_ROOT + "/prd/catalog!queryAllTown";
                $("body").ajaxJsonSync(url, {}, function(data) {
                	var options = {
                		'' : ''
                    };
                    $.each(data, function(i, item) {
                        options[item.mcode] = item.display;
                    })
                    CacheDatas.towns = options;
                })
        	}
        	
            return CacheDatas.towns;
        },
        
        initCacheCustomerProfileDatas : function(aysnc) {
            var url = WEB_ROOT + "/channel/common/custcenter/cust-center!findCustInfo";
            $.ajax({
                async : aysnc,
                type : "GET",
                url : url,
                dataType : 'json',
                success : function(data) {
                    $.each(data, function(i, item) {
                        item.id = item.id;
                        item.label = item.display;
                        item.value = item.display;
                        item.filterSpell = makePy(item.label);
                        if (item.filterSpell == undefined) {
                            item.filterSpell = "";
                        } else {
                            item.filterSpell = item.filterSpell.join(",");
                        }
                    });
                    CacheDatas.CustomerProfiles = TAFFY(data);
                    CacheDatas.CustomerProfiles.sort("value");
                }
            });
        },
        
        queryCacheCustomerProfileDatas : function(term) {
            if (CacheDatas.CustomerProfiles == undefined) {
                Biz.initCacheCustomerProfileDatas(false);
            }
            var query = [ {
                label : {
                    like : term
                }
            }, {
                filterSpell : {
                    likenocase : term
                }
            } ];
            var result = CacheDatas.CustomerProfiles(query).order("value");
            return result.get();
        },
        
        showCustomerInfo : function(){
        	$("#id_custinfo").click(function(){
        		if(Biz.CustInfo){
        			$('#div_user').popupDialog({
        				url : WEB_ROOT + '/channel/common/custcenter/cust-center!showCustInfo',
                        title : '客户信息',
                        id: 'custinfo',
                        postData : {"custs":JSON.stringify(Biz.CustInfo)}
        			});
        		}else{
        			Global.notify("error", "请先定位客户!");
        		}
        	});
        },

        /**
         * 定位客户
         */
        setupCustomerProfileSelect : function() {
            // 客户元素处理
            $("#id_selection").click(function() {
            	var obj = $('#tab_content_dashboard').nextAll("div:visible");
                $('#div_user').popupDialog({
                    url : WEB_ROOT + '/channel/common/custcenter/cust-center!forward?_to_=custTabs',
                    title : '客户中心',
                    id: 'cust_selection',
                    callback : function(rowdata) {
                        var custs = eval('(' + rowdata.custs + ')');
                        $("#id_custname").html(custs.custname);
                        
                        //处理一下客户地址
                        var custaddrs = Biz.getCustaddrFromCustinfo(custs);
                        custs.custaddrs=custaddrs;
                        Biz.CustInfo = custs;
                        
                        //定位客户后再刷新一下页面
                        if(obj.attr("data-url")){
	                        $('#tab_content_dashboard').hide();
	                        obj.show();
	                        obj.ajaxGetUrl(obj.attr("data-url"));
                        }
                    }
                });
            });
        },
        
        getCustaddrFromCustinfo : function(custinfo){
        	if(!custinfo || custinfo==""){
        		return null;
        	}
        	
        	var servs = custinfo.servs;
        	var tmpaddr={};
        	var addrs=[];
    		$(servs).each(
    				function(key) {
    					if(servs[key].houseid && servs[key].patchid){
    						var tmpkey = servs[key].houseid+"~"+servs[key].patchid;
    						if(!tmpaddr[tmpkey]){
    							tmpaddr[tmpkey]=servs[key].addr;
    							
    							var custaddr={};
    							custaddr.houseid=servs[key].houseid;
    							custaddr.areaid=servs[key].areaid;
    							custaddr.patchid=servs[key].patchid;
    							custaddr.chouseid=servs[key].chouseid;
    							custaddr.whladdr = servs[key].addr;
    							addrs.push(custaddr);
    						}
	    				};
    				}	
    				);
    			return 	addrs;
        	
        },
        
        initCustInfo : function (){
        	if(Biz.CustInfo){
        		$('#id_custname').html(Biz.CustInfo.custname);
        	}
    	},
    	
    	initEquipInfo : function (selector){
    		$(selector+" option").remove();
    		
    		if(!Biz.CustInfo){
    			return;
    		}

    		var servs = Biz.CustInfo.servs;
    		$(servs).each(
				function(key) {
					var value = JSON.stringify(servs[key]);
					var permark = Biz.getPrvParamMname('SYS_PERMARK',servs[key].permark);
					var servstatus = Biz.getPrvParamMname('SYS_SERV_STATUS',servs[key].servstatus);
					var text = permark + " " + servs[key].keyno + " " + servstatus + " " + servs[key].addr;
					var select = "";
					if(key == 0)
						select = "selected='selected'";
					$(selector).append(
							"<option value='" + value + "' "+select+">"
									+ text + "</option>");
			});
    	},
    	
    	initParam : function(elem,gcode,obj) {
    		obj.empty();
    		if (elem.val() == null || elem.val() == "") return;
    		var url = WEB_ROOT + "/prv-sysparam!selectParamList?gcode="+gcode+"&mcode="+ elem.val();
	    	elem.ajaxJsonUrl(url, function(data) {
	    		var option = '';
	    		option = '<option value=""></option>';
    			obj.append(option);
	    		$.each(data, function(i, item) {
	    			option = '<option value="'+item.mcode+'">'+item.display+'</option>';
	    			obj.append(option);
	            })
//	            obj.select2("val", "");
	    	});
    	},

		getPrvParamMname : function(gcode, mcode) {
			var mname = "";
			if (CacheDatas.Gcodes && CacheDatas.Gcodes[gcode + "_" + mcode]) {
				mname = CacheDatas.Gcodes[gcode + "_" + mcode];
			} else {
				var url = WEB_ROOT + "/prv-sysparam!selectParamMname?gcode="
						+ gcode + "&mcode=" + mcode;

				var gcodes = {};
				if(CacheDatas.Gcodes)
					gcodes = CacheDatas.Gcodes;
				$("body").ajaxJsonSync(url, {}, function(data) {
					mname = data.mname;
					gcodes[gcode + "_" + mcode] = data.mname;
				});
				CacheDatas.Gcodes = gcodes;
			}
			return mname;
		},
        
        getPrvParamDatas : function(gcode,mcode) {
            var url = WEB_ROOT + "/prv-sysparam!selectParamList?gcode="+gcode+"&mcode="+mcode;
            var options = {};
            $("body").ajaxJsonUrl(url, function(data) {
                $.each(data.content, function(i, item) {
                    options[item.id] = item.display;
                });
            });
            return options;
        },

		getPrvParamListDatas : function(gcode,mcode) {
        	var url = WEB_ROOT + "/prv-sysparam!selectParamMap?gcode="+gcode+"&mcode="+mcode;
        	var options = {};
            $("body").ajaxJsonSync(url, {}, function(data) {
                $.each(data, function(i, item) {
                	options[item.mcode] = item.display;
                });
            });
            return options;
        },
		//角色配置权限加登录操作员判断就低不就高
        getPrvParamAuthListDatas : function(gcode,mcode) {
            var url = WEB_ROOT + "/prv-sysparam!selectParamAuthMap?gcode="+gcode+"&mcode="+mcode;
            var options = {};
            $("body").ajaxJsonSync(url, {}, function(data) {
                $.each(data, function(i, item) {
                    options[item.mcode] = item.display;
                });
            });
            return options;
        },
        
        initSelect : function(gcode, obj, value, doAction){
    		obj.empty();
    		var url = WEB_ROOT + "/prv-sysparam!selectParamList?gcode="+gcode;
    		obj.ajaxJsonUrl(url, function(data) {
    			obj.append('<option value=""></option>');
	    		var option = '';
	    		$.each(data, function(i, item) {
	    			var isHasValue = (typeof (value) != "undefined") && (value == item.mcode);
					option = '<option value="' + item.mcode + '"'
							+ (isHasValue ? ' selected="selected"' : '') + '>'
							+ item.display + '</option>';
	    			obj.append(option);
	            });
	            obj.select2("val", value); // select2在这里不生效，用selected属性代替
	    		
	    		if(doAction){
	    			doAction();
	    		}
	    	});
    	},

        initSelectRession : function(gcode, obj, value, doAction){
            obj.empty();
            var url = WEB_ROOT + "/prv-sysparam!selectParamList?gcode="+gcode;
            obj.ajaxJsonUrl(url, function(data) {
                obj.append('<option value=""></option>');
                var option = '';
                $.each(data, function(i, item) {
                    //var isHasValue = 0;
                    //console.info(item.mcode)
                    //console.info(item.display);
                    var isHasValue = (typeof (value) != "undefined") && (value == item.mcode);
                    option = '<option value="' + item.mcode + '"'
                        + (isHasValue ? ' selected="selected"' : '') + '>'
                        + item.display + '</option>';
                    obj.append(option);
                });
                if(doAction){
                    doAction();
                }
            });
        },
        //初始化下拉菜单，支持自定义添加一项
        initSelectOpts : function(gcode, obj, value,Opt,doAction){
    		obj.empty();
    		var url = WEB_ROOT + "/prv-sysparam!selectParamList?gcode="+gcode;
    		obj.ajaxJsonUrl(url, function(data) {
    			obj.append('<option value=""></option>');
    			if(Opt){ //自定义的项
    				obj.append('<option value="'+Opt.value+'">'+Opt.text+'</option>');
    			}
	    		var option = '';
	    		$.each(data, function(i, item) {
	    			var isHasValue = (typeof (value) != "undefined") && (value == item.mcode);
					option = '<option value="' + item.mcode + '"'
							+ (isHasValue ? ' selected="selected"' : '') + '>'
							+ item.display + '</option>';
	    			obj.append(option);
	            });
	            obj.select2("val", value); // select2在这里不生效，用selected属性代替
	    		
	    		if(doAction){
	    			doAction();
	    		}
	    	});
    	},
    	initSelectByMcodes : function(gcode, mcodes, obj, value, doAction) {
			obj.empty();
			var url = WEB_ROOT + "/prv-sysparam!selectParamListByMcodes?gcode=" + gcode
					+ ((mcodes && mcodes != "") ? "&mcodes=" + mcodes : "");
			obj.ajaxJsonUrl(url, function(data) {
				obj.append('<option value=""></option>');
				var option = '';
				$.each(data, function(i, item) {
					var isHasValue = (typeof (value) != "undefined") && (value == item.mcode);
					option = '<option value="' + item.mcode + '"'
							+ (isHasValue ? ' selected="selected"' : '') + '>'
							+ item.display + '</option>';
					obj.append(option);
				});
				obj.select2("val", value); // select2在这里不生效，用selected属性代替

				if (doAction) {
					doAction();
				}
			});
		},

        writeObj : function(obj){
			var description = ""; 
		    for(var i in obj){   
		        var property=obj[i];   
		        description+=i+" = "+property+"\n";  
		    }   
		    alert(description);
		},
		
		initPercomb : function(form){
			$form = $(form);
			var opcode = $form.find('#id_opcode').val();
			var data = {};
			data.opcode = opcode;
			var url = WEB_ROOT + '/channel/biz/businessupgrade/business-upgrade!initPercomb';
			$("body").ajaxJsonSync(url,data, function(result) {
				if(!result) return ;
				
				$.each(result, function(i, item) {
					var value = item.percomb;
					var text = item.combname;
					var selected = "";
					if(i == 0)
						selected = "selected";
					$form.find("#id_percomb").append(
							"<option value='" + value + "' "+selected+">"
									+ text + "</option>");
				});
			});
		},
		
		refresh : function(form){
			var $form = $(form);
			var div_obj = $form.parent().parent();
			var url = div_obj.attr("data-url");
			if(url){
				div_obj.ajaxGetUrl(url);
			}
		},
		
		getCustInfo : function(){
			var tmpInfo = Biz.CustInfo;
			if(!tmpInfo){
				return null;
			}
			
			var custInfo = {};
			custInfo.custid = tmpInfo.custid;
			custInfo.custname = tmpInfo.custname;
			custInfo.cardtype = tmpInfo.cardtype;
			custInfo.cardno = tmpInfo.cardno;
			custInfo.mobile = tmpInfo.mobile;
			custInfo.phone = tmpInfo.phone;
			custInfo.areaid = tmpInfo.areaid;
			
			return custInfo;
		},
		
		queryDepartmentCache : function(term) {
	    	if (CacheDatas.depts == undefined) {
	    		var url = WEB_ROOT + "/prv/prv-department!findAllDepartments";
	            $.ajax({
	                async : false,
	                type : "GET",
	                url : url,
	                dataType : 'json',
	                success : function(data) {
	                    $.each(data, function(i, item) {
	                        item.id = item.id;
	                        item.label = item.display;
	                        item.value = item.display;
	                        
	                        item.filterSpell = makePy(item.label);
	                        if (item.filterSpell == undefined) {
	                            item.filterSpell = "";
	                        } else {
	                            item.filterSpell = item.filterSpell.join(",");
	                        }
	                    });
	                    CacheDatas.depts = TAFFY(data);
	                    CacheDatas.depts.sort("value");
	                }
	            });
	        }
	        var query = [ {
	            label : {
	                like : term
	            }
	        }, {
	            filterSpell : {
	                likenocase : term
	            }
	        } ];
	        var result = CacheDatas.depts(query).order("value");
	        return result.get();
    	},
    	
    	queryDepartmentByCityCache : function(term) {
	    	if (CacheDatas.deptsByCity == undefined) {
	    		var url = WEB_ROOT + "/prv/prv-department!findDepartByCity";
	            $.ajax({
	                async : false,
	                type : "GET",
	                url : url,
	                dataType : 'json',
	                success : function(data) {
	                    $.each(data, function(i, item) {
	                        item.id = item.id;
	                        item.label = item.display;
	                        item.value = item.display;
	                        
	                        item.filterSpell = makePy(item.label);
	                        if (item.filterSpell == undefined) {
	                            item.filterSpell = "";
	                        } else {
	                            item.filterSpell = item.filterSpell.join(",");
	                        }
	                    });
	                    CacheDatas.deptsByCity = TAFFY(data);
	                    CacheDatas.deptsByCity.sort("value");
	                }
	            });
	        }
	        var query = [ {
	            label : {
	                like : term
	            }
	        }, {
	            filterSpell : {
	                likenocase : term
	            }
	        } ];
	        var result = CacheDatas.deptsByCity(query).order("value");
	        return result.get();
    	},
    	
    	getDepartmentOptions : function(selectCommodityFunc) {
            return {
                placeholder : '输入名称...',
                dataInit : function(elem) {
                    var $grid = $(this);
                    var $elem = $(elem);

                    $elem.wrap('<div class="input-icon right"/>');
                    $elem.before('<i class="fa fa-ellipsis-horizontal fa-select-commodity"></i>');
                    $elem.before('<i class="fa fa-times fa-clear-commodity"></i>');
                    
                    var selectCommodity = selectCommodityFunc;
                    if (selectCommodity == undefined) {
                        selectCommodity = function(item) {
                            var $curRow = $elem.closest("tr.jqgrow");
                            var rowdata = $grid.jqGrid("getEditingRowdata");
                            // 强制覆盖已有值
                            rowdata['department.id'] = item.id, rowdata['department.name'] = item.display;
                            $grid.jqGrid("setEditingRowdata", rowdata);
                        }
                    }
                    
                    $elem.parent().find(".fa-clear-commodity").click(function() {
                        var rowdata = $grid.jqGrid("getEditingRowdata");
                        // 强制覆盖已有值
                        rowdata['department.id'] = '', rowdata['department.name'] = ''
                        $grid.jqGrid("setEditingRowdata", rowdata);
                    });
                    
                    $elem.parent().find(".fa-select-commodity").click(function() {
                        $(this).popupDialog({
                        	url : WEB_ROOT + '/prv/prv-department!forward?_to_=selection',
        	                title : '选取部门',
                            callback : function(item) {
                                $elem.attr("title", item.name);
                                var $curRow = $elem.closest("tr.jqgrow");
                                var rowdata = $grid.jqGrid("getEditingRowdata");
                                // 强制覆盖已有值
                                rowdata['department.id'] = item.id, rowdata['department.name'] = item.name
                                $grid.jqGrid("setEditingRowdata", rowdata);
                            }
                        })
                    });

                    $elem.autocomplete({
                        autoFocus : true,
                        source : function(request, response) {
                    		var data = Biz.queryDepartmentByCityCache(request.term);
                    		return response(data);
                        },
                        minLength : 2,
                        select : function(event, ui) {
                            var item = ui.item;
                            var $curRow = $elem.closest("tr.jqgrow");
                            var rowdata = $grid.jqGrid("getEditingRowdata");
                            // 强制覆盖已有值
                            rowdata['department.id'] = item.id, rowdata['department.name'] = item.name
                            $grid.jqGrid("setEditingRowdata", rowdata);

                            event.stopPropagation();
                            event.preventDefault();
                            return false;
                        },
                        change : function(event, ui) {
                            if (ui.item == null || ui.item == undefined) {
                                $elem.val("");
                                $elem.focus();
                            }
                        }
                    }).focus(function() {
                        $elem.select();
                    }).dblclick(function() {
                        $elem.parent().find(".fa-select-commodity").click();
                    });
                }
            };
        },
        
        getDepartmentOptionsByOperid : function(operid) {
            return {
                placeholder : '输入名称...',
                dataInit : function(elem) {
                    var $grid = $(this);
                    var $elem = $(elem);

                    $elem.wrap('<div class="input-icon right"/>');
                    $elem.before('<i class="fa fa-ellipsis-horizontal fa-select-commodity"></i>');
                    $elem.before('<i class="fa fa-times fa-clear-commodity"></i>');
                    
                    var selectCommodity = function(item) {
                    	var $curRow = $elem.closest("tr.jqgrow");
                        var rowdata = $grid.jqGrid("getEditingRowdata");
                        // 强制覆盖已有值
                        rowdata['department.id'] = item.id, rowdata['department.name'] = item.display;
                        $grid.jqGrid("setEditingRowdata", rowdata);
                    }
                    
                    $elem.parent().find(".fa-clear-commodity").click(function() {
                        var rowdata = $grid.jqGrid("getEditingRowdata");
                        // 强制覆盖已有值
                        rowdata['department.id'] = '', rowdata['department.name'] = ''
                        $grid.jqGrid("setEditingRowdata", rowdata);
                    });
                    
                    $elem.parent().find(".fa-select-commodity").click(function() {
                        $(this).popupDialog({
                        	url : WEB_ROOT + '/prv/prv-department!forward?_to_=selection-by-oper&operid='+operid,
        	                title : '选取部门',
                            callback : function(item) {
                                $elem.attr("title", item.name);
                                var $curRow = $elem.closest("tr.jqgrow");
                                var rowdata = $grid.jqGrid("getEditingRowdata");
                                // 强制覆盖已有值
                                rowdata['department.id'] = item.id, rowdata['department.name'] = item.name
                                $grid.jqGrid("setEditingRowdata", rowdata);
                            }
                        })
                    });

                    $elem.focus(function() {
                        $elem.select();
                    }).dblclick(function() {
                        $elem.parent().find(".fa-select-commodity").click();
                    });
                }
            };
        },
        
        queryPatchCache : function(term) {
	    	if (CacheDatas.patchs == undefined) {
	    		var url = WEB_ROOT + "/market/res-patch!findAllPatchs";
	            $.ajax({
	                async : false,
	                type : "GET",
	                url : url,
	                dataType : 'json',
	                success : function(data) {
	                    $.each(data, function(i, item) {
	                        item.id = item.id;
	                        item.label = item.display;
	                        item.value = item.display;
	                        
	                        item.filterSpell = makePy(item.label);
	                        if (item.filterSpell == undefined) {
	                            item.filterSpell = "";
	                        } else {
	                            item.filterSpell = item.filterSpell.join(",");
	                        }
	                    });
	                    CacheDatas.patchs = TAFFY(data);
	                    CacheDatas.patchs.sort("value");
	                }
	            });
	        }
	        var query = [ {
	            label : {
	                like : term
	            }
	        }, {
	            filterSpell : {
	                likenocase : term
	            }
	        } ];
	        var result = CacheDatas.patchs(query).order("value");
	        return result.get();
    	},
    	getPatchOptions : function(selectCommodityFunc) {
            return {
                placeholder : '输入小区名称...',
                dataInit : function(elem) {
                    var $grid = $(this);
                    var $elem = $(elem);

                    $elem.wrap('<div class="input-icon right"/>');
                    $elem.before('<i class="fa fa-ellipsis-horizontal fa-select-commodity"></i>');
                    $elem.before('<i class="fa fa-times fa-clear-commodity"></i>');
                    
                    $elem.parent().find(".fa-clear-commodity").click(function() {
                        var rowdata = $grid.jqGrid("getEditingRowdata");
                        // 强制覆盖已有值
                        rowdata['objid'] = '', rowdata['extraAttributes.patchname'] = '',
                        rowdata['extraAttributes.areaname'] = '',
                        rowdata['deptid'] = '';
                        $grid.jqGrid("setEditingRowdata", rowdata);
                    });
                    
                    $elem.parent().find(".fa-select-commodity").click(function() {
                        $(this).popupDialog({
                        	url : WEB_ROOT + '/market/res-patch!forward?_to_=selection',
        	                title : '选取小区',
                            callback : function(item) {
                                $elem.attr("title", item.name);
                                var $curRow = $elem.closest("tr.jqgrow");
                                var rowdata = $grid.jqGrid("getEditingRowdata");
                                // 强制覆盖已有值
                                rowdata['objid'] = item.id, rowdata['extraAttributes.patchname'] = item.patchname;
                                var area = Biz.getAreaObj(item.areaid);
                                if (area != null) {
                                	rowdata['city'] = area.city;
                                	rowdata['extraAttributes.areaname'] = area.name;
                                }
                                $grid.jqGrid("setEditingRowdata", rowdata);
                            }
                        })
                    });

                    $elem.autocomplete({
                        autoFocus : true,
                        source : function(request, response) {
                    		var data = Biz.queryPatchCache(request.term);
                    		return response(data);
                        },
                        minLength : 2,
                        select : function(event, ui) {
                            var item = ui.item;
                            var $curRow = $elem.closest("tr.jqgrow");
                            var rowdata = $grid.jqGrid("getEditingRowdata");
                            
                            rowdata['objid'] = item.id, rowdata['extraAttributes.patchname'] = item.display;
                            var area = Biz.getAreaObj(item.areaid);
                            if (area != null) {
                            	rowdata['city'] = area.city;
                            	rowdata['extraAttributes.areaname'] = area.name;
                            }
                            $grid.jqGrid("setEditingRowdata", rowdata);

                            event.stopPropagation();
                            event.preventDefault();
                            return false;
                        },
                        change : function(event, ui) {
                            if (ui.item == null || ui.item == undefined) {
                                $elem.val("");
                                $elem.focus();
                            }
                        }
                    }).focus(function() {
                        $elem.select();
                    }).dblclick(function() {
                        $elem.parent().find(".fa-select-commodity").click();
                    });
                }
            };
        },
        
        /**
         * 弹出小区多选窗口
         * @parma dialogQueryParams 窗口URL额外参数，固定有singleSelect和tabName；singleSelect为true表示表格只能选择一条记录，false（默认）表示可多选
         * @param listQueryParams 查询窗口表格的URL及参数信息，必须有listUrl属性，表示查询表格的url（传入{}格式的json对象作为参数即可)
         * @param saveBtnAction 多选窗口中的保存按钮的事件，只实现选择表格后的逻辑即可（逻辑中的this为窗口中的表格对象）
         * @param showDialogObj 用于调用弹出窗口的对象，不传入则使用Biz对象
         */
        showPatchMultipleSelect : function(dialogQueryParams, listQueryParams, saveBtnAction, showDialogObj) {
        	if (!listQueryParams.listUrl) {
        		Global.notify("error", "列表查询URL不能为空");
        		return;
        	}
        	
        	var thisShowDialogObj = !showDialogObj ? this : showDialogObj;
        	var rootUrl = '/market/res-patch!forward?_to_=multiple-selection';
        	var dialogUrl = this.createMultipleDialogUrl(dialogQueryParams, listQueryParams, rootUrl);
        	$(thisShowDialogObj).popupDialog({
        		url : dialogUrl,
        		id : 'multiple_patchs',
        		title : '选取小区',
        		callback : saveBtnAction
        	});
        },
        
        queryOperatorCache : function(term) {
        	if (CacheDatas.operators == undefined) {
	    		var url = WEB_ROOT + "/prv/prv-operator!findAllOperators";
	            $.ajax({
	                async : false,
	                type : "GET",
	                url : url,
	                dataType : 'json',
	                success : function(data) {
	                    $.each(data, function(i, item) {
	                        item.id = item.id;
	                        item.label = item.display;
	                        item.value = item.display;
	                        
	                        item.filterSpell = makePy(item.label);
	                        if (item.filterSpell == undefined) {
	                            item.filterSpell = "";
	                        } else {
	                            item.filterSpell = item.filterSpell.join(",");
	                        }
	                    });
	                    CacheDatas.operators = TAFFY(data);
	                    CacheDatas.operators.sort("value");
	                }
	            });
	        }
	        var query = [ {
	            label : {
	                like : term
	            }
	        }, {
	            filterSpell : {
	                likenocase : term
	            }
	        } ];
	        var result = CacheDatas.operators(query).order("value");
	        return result.get();
    	},
    	
    	queryOperatorByCityCache : function(term) {
        	if (CacheDatas.operatorsByCity == undefined) {
	    		var url = WEB_ROOT + "/prv/prv-operator!findOpersByCity";
	            $.ajax({
	                async : false,
	                type : "GET",
	                url : url,
	                dataType : 'json',
	                success : function(data) {
	                    $.each(data, function(i, item) {
	                        item.id = item.id;
	                        item.label = item.display;
	                        item.value = item.display;
	                        
	                        item.filterSpell = makePy(item.label);
	                        if (item.filterSpell == undefined) {
	                            item.filterSpell = "";
	                        } else {
	                            item.filterSpell = item.filterSpell.join(",");
	                        }
	                    });
	                    CacheDatas.operatorsByCity = TAFFY(data);
	                    CacheDatas.operatorsByCity.sort("value");
	                }
	            });
	        }
	        var query = [ {
	            label : {
	                like : term
	            }
	        }, {
	            filterSpell : {
	                likenocase : term
	            }
	        } ];
	        var result = CacheDatas.operatorsByCity(query).order("value");
	        return result.get();
    	},
    	
    	queryKnowCache : function(term) {
        	if (CacheDatas.knows == undefined) {
	    		var url = WEB_ROOT + "/prd/catalog!findAllKnows";
	            $.ajax({
	                async : false,
	                type : "GET",
	                url : url,
	                dataType : 'json',
	                success : function(data) {
	                    $.each(data, function(i, item) {
	                        item.id = item.id;
	                        item.label = item.display;
	                        item.value = item.display;
	                        
	                        item.filterSpell = makePy(item.label);
	                        if (item.filterSpell == undefined) {
	                            item.filterSpell = "";
	                        } else {
	                            item.filterSpell = item.filterSpell.join(",");
	                        }
	                    });
	                    CacheDatas.knows = TAFFY(data);
	                    CacheDatas.knows.sort("value");
	                }
	            });
	        }
	        var query = [ {
	            label : {
	                like : term
	            }
	        }, {
	            filterSpell : {
	                likenocase : term
	            }
	        } ];
	        var result = CacheDatas.knows(query).order("value");
	        return result.get();
    	},
    	
    	queryKnowBiCityCache : function(term) {
        	if (CacheDatas.knowsByCity == undefined) {
	    		var url = WEB_ROOT + "/prd/salespkg-know!findAllKnowsByCity";
	            $.ajax({
	                async : false,
	                type : "GET",
	                url : url,
	                dataType : 'json',
	                success : function(data) {
	                    $.each(data, function(i, item) {
	                        item.id = item.id;
	                        item.label = "(" + item.objcode + ")  " + item.display;
	                        item.value = item.display;
	                        
	                        item.filterSpell = makePy(item.label);
	                        if (item.filterSpell == undefined) {
	                            item.filterSpell = "";
	                        } else {
	                            item.filterSpell = item.filterSpell.join(",");
	                        }
	                    });
	                    CacheDatas.knowsByCity = TAFFY(data);
	                    CacheDatas.knowsByCity.sort("value");
	                }
	            });
	        }
	        var query = [ {
	            label : {
	                like : term
	            }
	        }, {
	            filterSpell : {
	                likenocase : term
	            }
	        } ];
	        var result = CacheDatas.knowsByCity(query).order("value");
	        return result.get();
    	},
        
        getOperatorOptions : function(selectCommodityFunc) {
            return {
                placeholder : '输入登录账号、名称...',
                dataInit : function(elem) {
                    var $grid = $(this);
                    var $elem = $(elem);

                    $elem.wrap('<div class="input-icon right"/>');
                    $elem.before('<i class="fa fa-ellipsis-horizontal fa-select-commodity"></i>');
                    $elem.before('<i class="fa fa-times fa-clear-commodity"></i>');
                    
                    var selectCommodity = selectCommodityFunc;
                    if (selectCommodity == undefined) {
                        selectCommodity = function(item) {
                            var $curRow = $elem.closest("tr.jqgrow");
                            var rowdata = $grid.jqGrid("getEditingRowdata");
                            // 强制覆盖已有值
                            rowdata['areamger'] = item.id, rowdata['extraAttributes.name'] = item.display;
                            $grid.jqGrid("setEditingRowdata", rowdata);
                        }
                    }
                    
                    $elem.parent().find(".fa-clear-commodity").click(function() {
                        var rowdata = $grid.jqGrid("getEditingRowdata");
                        // 强制覆盖已有值
                        rowdata['areamger'] = '', rowdata['extraAttributes.name'] = ''
                        $grid.jqGrid("setEditingRowdata", rowdata);
                    });
                    
                    $elem.parent().find(".fa-select-commodity").click(function() {
                        $(this).popupDialog({
                        	url : WEB_ROOT + '/prv/prv-operator!forward?_to_=selection',
        	                title : '选择操作员',
                            callback : function(item) {
                                $elem.attr("title", item.name);
                                var $curRow = $elem.closest("tr.jqgrow");
                                var rowdata = $grid.jqGrid("getEditingRowdata");
                                // 强制覆盖已有值
                                rowdata['areamger'] = item.id, rowdata['extraAttributes.name'] = item.loginname;
                                rowdata['city'] = Biz.LOGIN_INFO.city;
                                
                                var rowid = $curRow.attr("id");
                                if (rowid > 1) {
                                	rowdata['ismain'] = 'N';
                                }
                                
                                $grid.jqGrid("setEditingRowdata", rowdata);
                            }
                        })
                    });

                    $elem.autocomplete({
                        autoFocus : true,
                        source : function(request, response) {
                    		var data = Biz.queryOperatorByCityCache(request.term);
                    		return response(data);
                        },
                        minLength : 2,
                        select : function(event, ui) {
                            var item = ui.item;
                            var $curRow = $elem.closest("tr.jqgrow");
                            var rowdata = $grid.jqGrid("getEditingRowdata");
                            // 强制覆盖已有值
                            rowdata['areamger'] = item.id, rowdata['extraAttributes.name'] = item.loginname;
                            rowdata['city'] = Biz.LOGIN_INFO.city;
                            
                            if ($grid.getGridParam("reccount") > 1) {
                            	rowdata['ismain'] = 'N';
                            }
                            
                            $grid.jqGrid("setEditingRowdata", rowdata);

                            event.stopPropagation();
                            event.preventDefault();
                            return false;
                        },
                        change : function(event, ui) {
                            if (ui.item == null || ui.item == undefined) {
                                $elem.val("");
                                $elem.focus();
                            }
                        }
                    }).focus(function() {
                        $elem.select();
                    }).dblclick(function() {
                        $elem.parent().find(".fa-select-commodity").click();
                    });
                }
            };
        },
        
        /**
         * 弹出操作员多选窗口
         * @parma dialogQueryParams 窗口URL额外参数，固定有singleSelect和tabName；singleSelect为true表示表格只能选择一条记录，false（默认）表示可多选
         * @param listQueryParams 查询窗口表格的URL及参数信息，必须有listUrl属性，表示查询表格的url（传入{}格式的json对象作为参数即可)
         * @param saveBtnAction 多选窗口中的保存按钮的事件，只实现选择表格后的逻辑即可（逻辑中的this为窗口中的表格对象）
         * @param showDialogObj 用于调用弹出窗口的对象，不传入则使用Biz对象
         */
        showOperatorMultipleSelect : function(dialogQueryParams, listQueryParams, saveBtnAction, showDialogObj) {
        	if (!listQueryParams.listUrl) {
        		Global.notify("error", "列表查询URL不能为空");
        		return;
        	}
        	
        	var thisShowDialogObj = !showDialogObj ? this : showDialogObj;
        	var rootUrl = '/prv/prv-operator!forward?_to_=multiple-selection';
        	var dialogUrl = this.createMultipleDialogUrl(dialogQueryParams, listQueryParams, rootUrl);
        	$(thisShowDialogObj).popupDialog({
        		url : dialogUrl,
        		id : 'multiple_operators',
        		title : '选取操作员',
        		callback : saveBtnAction
        	});
        },
        
        getAreaObj : function(areaid) {
			var areaObj;
			if (CacheDatas.areas == undefined) {
				CacheDatas.areas = {};
				var url = WEB_ROOT + "/market/grid!findAllArea";
				$("body").ajaxJsonSync(url, {}, function(data) {
					$.each(data, function(i, item) {
						var area = {};
						area.id = item.id;
						area.name = item.name;
						area.city = item.city;
						area.town = item.town;
                        
						CacheDatas.areas[area.id] = area;
                    });
				});
				
			}
			return CacheDatas.areas[areaid];
        },
        
        getCatalogCondtionOptions : function() {
            return {
                placeholder : '输入名称...',
                dataInit : function(elem) {
                    var $grid = $(this);
                    var $elem = $(elem);

                    $elem.wrap('<div class="input-icon right"/>');
                    $elem.before('<i class="fa fa-ellipsis-horizontal fa-select-commodity"></i>');
                    $elem.before('<i class="fa fa-times fa-clear-commodity"></i>');
                    
                    $elem.parent().find(".fa-clear-commodity").click(function() {
                        var rowdata = $grid.jqGrid("getEditingRowdata");
                        // 强制覆盖已有值
                        rowdata['contionvalue'] = '', rowdata['extraAttributes.condtionvalue'] = '';
                        $grid.jqGrid("setEditingRowdata", rowdata);
                    });
                    
                    $elem.parent().find(".fa-select-commodity").click(function() {
                    	var rowdata = $grid.jqGrid("getEditingRowdata");
                    	var contiontype=rowdata['contiontype'];
                    	if(contiontype=='2'){
                    	    Biz.selectOpcodes($grid);
                    	}else{
                    	var url = (rowdata['contiontype'] == '0') ?
                    			WEB_ROOT + '/prv/prv-operator!forward?_to_=selection' :
                    			WEB_ROOT + '/prv/prv-department!forward?_to_=selection';
                    	$(this).popupDialog({
                        	url : url,
        	                title : '选择目录条件',
                            callback : function(item) {
                        		$elem.attr("title", item.name);
                                rowdata['contionvalue'] = item.id;
                                if (rowdata['contiontype'] == '0') {
                                	rowdata['extraAttributes.condtionvalue'] = item.loginname;
                                } else {
                                	rowdata['extraAttributes.condtionvalue'] = item.name;
                                }
                                $grid.jqGrid("setEditingRowdata", rowdata);
                            }
                        });
                    	}
                    });

                    $elem.autocomplete({
                        autoFocus : true,
                        source : function(request, response) {
                    		var rowdata = $grid.jqGrid("getEditingRowdata");
                    		var data = (rowdata['contiontype'] == '0') ? 
                    				Biz.queryOperatorByCityCache(request.term) : 
                    				Biz.queryDepartmentByCityCache(request.term);
                    		return response(data);
                        },
                        minLength : 2,
                        select : function(event, ui) {
                            var item = ui.item;
                            var rowdata = $grid.jqGrid("getEditingRowdata");
                            rowdata['contionvalue'] = item.id;
                            if (rowdata['contiontype'] == '0') {
                            	rowdata['extraAttributes.condtionvalue'] = item.loginname;
                            } else {
                            	rowdata['extraAttributes.condtionvalue'] = item.name;
                            }
                            $grid.jqGrid("setEditingRowdata", rowdata);
                            event.stopPropagation();
                            event.preventDefault();
                            return false;
                        },
                        change : function(event, ui) {
                            if (ui.item == null || ui.item == undefined) {
                                $elem.val("");
                                $elem.focus();
                            }
                        }
                    }).focus(function() {
                        $elem.select();
                    }).dblclick(function() {
                        $elem.parent().find(".fa-select-commodity").click();
                    });
                }
            };
        },
        /**
         * 选择业务操作码
         * @returns
         */
        selectOpcodes:function($grid){
        	var url = WEB_ROOT + "/prd/catalog!findBizopcodesMap";
        	$grid.ajaxJsonUrl(url, function(data) {
        		 var tipmsg ="<div style='word-wrap:break-word; word-break:break-all;'>" 
	  		         +"<div class='form-group '>"
							+"<label class='control-label' style='width:120px'>业务操作</label>"
				            +"<div class='f-con'>"
				            +"<ul class='f-con c-name' id='id_catalog_select_bizopcode' >";
			        		 $.each(data, function(i, item) {
			                     option="<li onclick='Biz.itemClick(this);' class='item' value='"+i.trim()+"'><span>"+item+"</span><input type='hidden' value='"+i.trim()+"'></input></li>"
			                     tipmsg+=option;
			                 });
        		           
				 tipmsg+="</ul></div></div>";
				            
		          bootbox.dialog({
		                 message: tipmsg,
		                 title: "目录条件",
		                 buttons: {
		                     Cancel: {
		                         label: "取消",
		                         className: "btn-default",
		                         callback: function () {
		                         }
		                     }
		                     , OK: {
		                         label: "确定",
		                         className: "btn-primary",
		                         callback: function () {
		                        	 var opcode = $("#id_catalog_select_bizopcode").find("li.selected").find("input").val();
		                             var opcodename =  $("#id_catalog_select_bizopcode").find("li.selected").find("span").html();
		                             var rowdata = $grid.jqGrid("getEditingRowdata");
		                             rowdata['contionvalue'] = opcode;
		                             rowdata['extraAttributes.condtionvalue'] =opcodename;
		                             $grid.jqGrid("setEditingRowdata", rowdata);
		                         }
		                      }
		                 }
		   		  });
	    	});
        	 
        	
        },
        getCatalogKnowOptions : function() {
            return {
                placeholder : '输入营销方案名称...',
                dataInit : function(elem) {
                    var $grid = $(this);
                    var $elem = $(elem);

                    $elem.wrap('<div class="input-icon right"/>');
                    $elem.before('<i class="fa fa-ellipsis-horizontal fa-select-commodity"></i>');
                    $elem.before('<i class="fa fa-times fa-clear-commodity"></i>');
                    
                    $elem.parent().find(".fa-clear-commodity").click(function() {
                        var rowdata = $grid.jqGrid("getEditingRowdata");
                        // 强制覆盖已有值
                        rowdata['areamger'] = '', rowdata['extraAttributes.name'] = ''
                        $grid.jqGrid("setEditingRowdata", rowdata);
                    });
                    
                    $elem.parent().find(".fa-select-commodity").click(function() {
                        $(this).popupDialog({
                        	url : WEB_ROOT + '/prd/salespkg-know!forward?_to_=selection',
        	                title : '选择营销方案',
                            callback : function(item) {
                                $elem.attr("title", item.name);
                                var $curRow = $elem.closest("tr.jqgrow");
                                var rowdata = $grid.jqGrid("getEditingRowdata");
                                // 强制覆盖已有值
                                rowdata['knowid'] = item.id, rowdata['extraAttributes.knowname'] = item.knowname;
                                rowdata['city'] = item.city;
                                
                                $grid.jqGrid("setEditingRowdata", rowdata);
                            }
                        })
                    });

                    $elem.autocomplete({
                        autoFocus : true,
                        source : function(request, response) {
                    		var data = Biz.queryKnowBiCityCache(request.term);
                    		return response(data);
                        },
                        minLength : 2,
                        select : function(event, ui) {
                            var item = ui.item;
                            var $curRow = $elem.closest("tr.jqgrow");
                            var rowdata = $grid.jqGrid("getEditingRowdata");
                            // 强制覆盖已有值
                            rowdata['knowid'] = item.id, rowdata['extraAttributes.knowname'] = item.knowname;
                            rowdata['city'] = item.city;
                            
                            $grid.jqGrid("setEditingRowdata", rowdata);

                            event.stopPropagation();
                            event.preventDefault();
                            return false;
                        },
                        change : function(event, ui) {
                            if (ui.item == null || ui.item == undefined) {
                                $elem.val("");
                                $elem.focus();
                            }
                        }
                    }).focus(function() {
                        $elem.select();
                    }).dblclick(function() {
                        $elem.parent().find(".fa-select-commodity").click();
                    });
                }
            };
        },
        
        getOssGridOptions : function() {
            return {
                placeholder : '输入网格名称...',
                dataInit : function(elem) {
                    var $grid = $(this);
                    var $elem = $(elem);

                    $elem.wrap('<div class="input-icon right"/>');
                    $elem.before('<i class="fa fa-ellipsis-horizontal fa-select-commodity"></i>');
                    $elem.before('<i class="fa fa-times fa-clear-commodity"></i>');
                    
                    $elem.parent().find(".fa-clear-commodity").click(function() {
                        var rowdata = $grid.jqGrid("getEditingRowdata");
                        rowdata['gridOssInfo.gridid'] = '', rowdata['gridOssInfo.gridname'] = '',
                        //rowdata['gridOssInfo.status'] = '',
                        rowdata['gridOssInfo.id'] = '';
                        $grid.jqGrid("setEditingRowdata", rowdata);
                    });
                    
                    $elem.parent().find(".fa-select-commodity").click(function() {
                        $(this).popupDialog({
                        	url : WEB_ROOT + '/market/grid-oss-info!forward?_to_=selection',
        	                title : '选取网格',
                            callback : function(item) {
                                var $curRow = $elem.closest("tr.jqgrow");
                                var rowdata = $grid.jqGrid("getEditingRowdata");
                                // 强制覆盖已有值
                                rowdata['gridOssInfo.gridid'] = item.gridid, 
                                rowdata['gridOssInfo.gridname'] = item.gridname,
                                //rowdata['gridOssInfo.status'] = item.status,
                                rowdata['gridOssInfo.id'] = item.id;
                                $grid.jqGrid("setEditingRowdata", rowdata);
                            }
                        });
                    });
                }
            };
        },
        
        /*
         * 刷新jqgrid表格
         * @param gridClass 表格class名，要传入'.'号
         */
        refreshGrid : function(gridClass){
        	this.getGrid(gridClass).trigger('reloadGrid');
        },
        
        isHighlvlOperaotr : function() {
        	return (Biz.LOGIN_INFO.loginname == 'GZCYKFA001' || Biz.LOGIN_INFO.rolelevel=='9');
        },
        
        createMultipleDialogUrl : function(dialogQueryParams, listQueryParams, rootUrl) {
			// 根据listQueryParams重新构造listUrl
			var listUrl = listQueryParams.listUrl;
			delete listQueryParams.listUrl;
			if (listUrl.indexOf('?') == -1) {
				listUrl += '_PARAMtp_EQuseless'; // 临时参数，没实际意义
			} else {
				listUrl = listUrl.replace('?', '_PARAM').replace(
						new RegExp('&', 'g'), '_AND').replace(
						new RegExp('=', 'g'), '_EQ');
			}
			for ( var key in listQueryParams) {
				listUrl += '_AND' + key + '_EQ' + listQueryParams[key];
			}
			
			var defaultDialogParams = {
				tabName : '信息表格',
				singleSelect : false
			};
			$.extend(defaultDialogParams, dialogQueryParams);
			defaultDialogParams.singleSelect = defaultDialogParams.singleSelect ? 1 : 0;

			// 构造弹出窗口URL
			var dialogUrl = WEB_ROOT + rootUrl + '&listUrl=' + listUrl;
			for ( var key in defaultDialogParams) {
				dialogUrl += '&' + key + '=' + defaultDialogParams[key];
			}
			return dialogUrl;
        },
        
		getAdministrator : function() {
			return "GZCYKFA0001";
		},
				
		isCurrentAdmin : function() {
			return Biz.LOGIN_INFO.loginname == Biz.getAdministrator();
		},
		
		/**
		 * 禁用s:select控件
		 * @param select2Obj s:select控件对象
		 */
		disableSelect2 : function(select2Obj) {
			var container = $(select2Obj).prev();
			var selectEle = $(container).find(".select2-choice");
			$(selectEle).css("pointer", "default").unbind(); // 移除点击事件，才能真正禁用s:select控件
			$(container).addClass("select2-container-disabled"); // 增加样式置灰控件
			$(container).find(".select2-search-choice-close").remove(); // 移除取消选中按钮
		},
		
		/**
		 * 当操作员不是系统管理员时禁用s:select控件
		 * @param select2Obj s:select控件对象
		 */
		disableSelect2WhenNotAdmin : function(select2Obj) {
			if (Biz.LOGIN_INFO && !Biz.isCurrentAdmin()) {
				Biz.disableSelect2(select2Obj);
			}
		},
		
		/**
		 * 当新增数据时在表单中先根据操作员的地市填充地市字段的值
		 * @param select2Obj s:select控件对象
		 */
		selectCityWhenAddData : function(cityObj) {
			if (!$(cityObj).val()) {
				if (Biz.LOGIN_INFO) {
					cityObj.select2("val", Biz.LOGIN_INFO.city);
				}
			}
		},
		
		queryRoleByCityCache : function() {
            if (CacheDatas.rolesByCity == undefined) {
	    		var url = WEB_ROOT + "/prv/prv-roleinfo!findRoleByCity";
	    		CacheDatas.rolesByCity = {};
	            $("body").ajaxJsonSync(url, {}, function(data) {
	                $.each(data, function(i, item) {
	                	CacheDatas.rolesByCity[item.id] = item.display;
	                });
	            });
	        }
	        return CacheDatas.rolesByCity;
        },
        
        /**
         * 弹出网格关联地址多选窗口
         * @parma dialogQueryParams 窗口URL额外参数，固定有singleSelect和tabName；singleSelect为true表示表格只能选择一条记录，false（默认）表示可多选
         * @param listQueryParams 查询窗口表格的URL及参数信息，必须有listUrl属性，表示查询表格的url（传入{}格式的json对象作为参数即可)
         * @param saveBtnAction 多选窗口中的保存按钮的事件，只实现选择表格后的逻辑即可（逻辑中的this为窗口中的表格对象）
         * @param showDialogObj 用于调用弹出窗口的对象，不传入则使用Biz对象
         */
        showAddressMultipleSelect : function(dialogQueryParams, listQueryParams, saveBtnAction, showDialogObj) {
        	if (!listQueryParams.listUrl) {
        		Global.notify("error", "列表查询URL不能为空");
        		return;
        	}
        	
        	var thisShowDialogObj = !showDialogObj ? this : showDialogObj;
        	var rootUrl = '/market/grid-info!forward?_to_=gridaddress-multiple-selection';
        	var dialogUrl = this.createMultipleDialogUrl(dialogQueryParams, listQueryParams, rootUrl);
        	$(thisShowDialogObj).popupDialog({
        		url : dialogUrl,
        		id : 'multiple_addresses',
        		title : '选取地址',
        		callback : saveBtnAction
        	});
        },
        
        /**
         * 弹出网格关联地址多选窗口
         * @param dialogQueryParams 窗口URL额外参数，固定有singleSelect和tabName；singleSelect为true表示表格只能选择一条记录，false（默认）表示可多选
         * @param listQueryParams 查询窗口表格的URL及参数信息，必须有listUrl属性，表示查询表格的url（传入{}格式的json对象作为参数即可)
         * @param saveBtnAction 多选窗口中的保存按钮的事件，只实现选择表格后的逻辑即可（逻辑中的this为窗口中的表格对象）
         * @param showDialogObj 用于调用弹出窗口的对象，不传入则使用Biz对象
         */
        showQuestionMultipleSelect : function(dialogQueryParams, listQueryParams, saveBtnAction, showDialogObj) {
        	if (!listQueryParams.listUrl) {
        		Global.notify("error", "列表查询URL不能为空");
        		return;
        	}
        	
        	var thisShowDialogObj = !showDialogObj ? this : showDialogObj;
        	var rootUrl = '/survey/biz-survey-list!forward?_to_=question-multiple-selection';
        	var dialogUrl = this.createMultipleDialogUrl(dialogQueryParams, listQueryParams, rootUrl);
        	$(thisShowDialogObj).popupDialog({
        		url : dialogUrl,
        		id : 'multiple_question',
        		title : '选择题目',
        		callback : saveBtnAction
        	});
        },

        getGrid : function(gridClassOrObj) {
			var grid = null;
			if (typeof gridClassOrObj === "string") {
				grid = $(gridClassOrObj.indexOf(".") > -1 ? gridClassOrObj : ("." + gridClassOrObj));
			} else {
				grid = $(gridClassOrObj);
			}
			return grid;
		},

		/**
		 * 判断多选表格是否已选择记录，是则返回已选记录的id数组，不是则返回false
		 */
		isGridRightlyMultipleSelected : function(gridClassOrObj, singleSelect) {
			var grid = this.getGrid(gridClassOrObj);
			var selectedId = grid.jqGrid("getGridParam", "selrow"); // 获取所选行id
			if (selectedId) {
				var ids = grid.getAtLeastOneSelectedItem();
				if (singleSelect == 1 && ids.length > 1) {
					Global.notify("error", "请只选择一条行项目！");
					return false;
				}
				return ids;
			} else {
				Global.notify("error", "请" + (singleSelect == 1 ? "只" : "至少") + "选择一条行项目！");
				return false;
			}
		},

        /*
		 * 添加自定义按钮到jqgrid表格(可在一列中添加多个按钮，也可在不同列中添加按钮)
		 * @param gridClassOrObj 表格class名或表格对象本身
		 * @param btnInfos 按钮定义数组，每个对象为
		 * 		{
		 * 			btnColName:"不传则默认为self_opt(self_opt1,self_opt2...)",
		 * 			subBtns:[{
		 * 				btnName:"",
		 * 				canAddBtn:function(thisGrid, rowData),
		 * 				btnAction:"只支持字符串方式传入的方法名"
		 * 			},...]
		 * 		}
		 */
		addCustomBtnToGrid : function(gridClassOrObj, btnInfos) {
			var grid = this.getGrid(gridClassOrObj);

			var ids = grid.jqGrid("getDataIDs");
			for (var i = 0, size = ids.length; i < size; i++) {
				var id = ids[i];
				var rowData = grid.jqGrid("getRowData", id);

				for (var j = 0, sizeJ = btnInfos.length; j < sizeJ; j++) {
					var btnInfo = btnInfos[j];
					var btnColName = btnInfo.btnColName ? btnInfo.btnColName : ("self_opt" + (j > 0 ? j : ""));
					var subBtns = btnInfo.subBtns;

					var btnHTML = "";
					for (var k = 0, sizeK = subBtns.length; k < sizeK; k++) {
						var subBtn = subBtns[k];
						var btnName = subBtn.btnName;
						var canAddBtn = subBtn.canAddBtn;
						var btnAction = subBtn.btnAction;

						if (canAddBtn && (typeof canAddBtn === "function")) {
							if (!canAddBtn(grid, rowData)) {
								continue;
							}
						}

						btnHTML += "<a href='javascript:;' style='color:#f60' onclick='" + btnAction + "(\"" + id + "\")' >" + btnName + "</a>&nbsp;&nbsp;";
					}

					var btnObj = {};
					btnObj[btnColName] = btnHTML;
					grid.jqGrid("setRowData", id, btnObj);
				}
			}
		},

		/**
		 * 移除当前编辑tab（代替取消按钮事件，因为会额外弹出提示框）
		 * 
		 * @param gridOutterDivID 表格外层Div(一般是菜单主页面的最外层Div)的ID
		 */
		removeActiveTab : function(gridOutterDivID) {
			var gridOutterDiv = $("#" + gridOutterDivID);
			var activeTab = $(gridOutterDiv).find(".active.tab-closable");
			var tabId = $(activeTab).attr("id");
			$(activeTab).remove(); // 移除当前编辑tab
			$(gridOutterDiv).find("a[href='#" + tabId + "']").remove(); // 移除当前编辑tab标题
			var tabs = $(gridOutterDiv).find("a[data-toggle='tab'][data-url*='inputTabs']");
			if (tabs.length == 0) {
				// 没有编辑tab则返回到表格tab
				$(gridOutterDiv).find(".tab-default").click();
			} else {
				// 有其它编辑tab则选择最后一个编辑tab
				$(tabs[tabs.length - 1]).click();
			}
		}
		,
		
		/**
		 *表格中的保存按钮可以点击
		 * obj :传 $(".grid-biz-prd-salespkg-know-index")
		 */
	    abletoNavSaveBtn : function(obj){
			var topSaveBtnID = "#"+obj.attr("id")+"_toppager_ilsave";
			var bottomSaveBtnID = "#"+obj.attr("id")+"_ilsave";
			$(topSaveBtnID).removeClass('ui-state-disabled');
			$(bottomSaveBtnID).removeClass('ui-state-disabled');
		},
		checkNull : function(objvalue){
			if(objvalue == undefined || objvalue == ""|| objvalue==null 
					||!objvalue){
				return true;
			}
			return false;
		},
		
		// 支持下拉框选中第一个选项
    	initSelectParam2 : function(gcode,obj,param) {
    		if (param == null || param == "") return;
    		var url = WEB_ROOT + "/prv-sysparam!selectParamList?gcode="+gcode+"&mcode="+ param;
    		obj.ajaxJsonUrl(url, function(data) {
	    		var option = '';
	    		var first = '';
	    		$.each(data, function(i, item) {
	    			if (Biz.LOGIN_INFO.areaid==item.mcode) {
	    				first=item.mcode;
	    			}
	    			option = '<option value="'+item.mcode+'">'+item.display+'</option>';
	    			obj.append(option);
	            });
	    		
	    		if (first!=''){
	    			obj.select2("val", first);
	    		}
	    			
	    	});
    	},
    	showMore : function(obj,show){
			var $category = $(obj).siblings(show);
			if($(obj).find('span').text() == "-收起"){
				$category.attr("style","display:none;");
				$(obj).find('span').text('+显示更多');  
			}else{
				$category.attr("style","");
				$(obj).find('span').text('-收起');
			}
		},
		itemClick : function(obj) {
			if (!!$(obj).hasClass("selected")) {
				$(obj).removeClass("selected");
			} else {
				$(obj).addClass("selected").siblings(".item").removeClass(
						"selected");
			}
		},
		itemClick2 : function(obj) {
			$(obj).parent().parent().find("li.item").removeClass("selected");
			if ($(obj).hasClass("selected")) {
				$(obj).removeClass("selected");
			} else {
				$(obj).addClass("selected");
			}
		},
		/**
		 * 
		 * @param gridObj  : 表格元素
		 * @param hideElement ： 表格中需要隐藏的表格元素
		 * @returns
		 */
		hideTableElement: function(gridObj,hideElement){
			var gridBox = $("#gbox_"+ gridObj.attr("id"));
			$(gridBox).find(hideElement).hide();
		}
		,
		/**
		 * 隐藏表格中的添加按钮
		 * @param gridObj
		 * @returns
		 */
		hideAddButton: function(gridObj){
			var gridBox = $("#gbox_"+ gridObj.attr("id"));
			var options = gridBox.find(".jqgrid-options");
			for(var i= 0 ; i < options.length;i++){
				var option = options[i];
				$(option).hide();
			}
		},
		/**
		 * 隐藏表格中的搜索按钮
		 * obj :传 $(".grid-biz-prd-salespkg-know-index")
		 */
		hideSearchBtn: function(obj){
			var topSearchDivID ="#search_" +obj.attr("id")+"_top";
        	var bottomSearchDivID ="#search_" +obj.attr("id");
        	$(topSearchDivID).css("display","none");
        	$(bottomSearchDivID).css("display","none");
		},
		/**
		 * 隐藏表格底部
		 * @returns
		 */
		hideTableBottomPage:function(obj){
			
		},
		
		/** start  图片上传工具方法**/
		/**
		 * limitFileType :限制格式 取值 img，txt,xls
		 * maxSize:文件最大值   默认取值 50
		 * relativePath:相对路径  默认取值 common
		 * tip:提示           
		 * targetElement : 将生成的返回路径给其赋值
		 */
		fileupload:function(limitFileType0,maxSize0,relativePath0,tip,targetObj){
			  var content =  "<div class='upload_file_dialog_content'>"
			                 +"<div class='row'>"
			                 +"  <div class='col-md-8'>"
			                 +" <div class='form-group'> "  
			                 +"  <label class='control-label'>上传文件</label>"
			                 +" <div class='controls'>"
			                 +"   <input id='selectedFileDir' name='' type='text'  class='form-control'>"
			                 +"   <input type='file' name='salePhoto' id='fileToUpload' onchange='Biz.fileSelected();' style='display:none;'>"
							 +" </div>"
							 +"  </div>"
							 +"  </div>"
							 +"   <div >"
							 +"      <button class='btn blue'  name=''  onclick='Biz.fileSelect();' >"
							 +"		<i class='fa '></i> 浏览..."
							 +"	  </button>"
							 +"	 </div>"
							 +"</div>"
							 +"<div class='row'>"
							 +"<div class='col-md-3'>"
							 +"<label class='control-label' style='position: absolute;left: 10px;color: red;width: 350px;margin-left: 15px;font-size:12px;'>"+tip+"</label>"
							 +"</div>"
							 +"</div>"
							 +"</div>";
			 bootbox.dialog({
	              message: content,
	              title: "选择文件",
	              buttons: {
	                  Cancel: {
	                      label: "取消",
	                      className: "btn-default",
	                      callback: function () {
	                      }
	                  }
	                  , OK: {
	                      label: "上传",
	                      className: "btn-primary",
	                      callback: function () {
	                    	var maxSize = Biz.checkNull(maxSize0)?"50":maxSize0;//默认50kb
	                    	var limitType = Biz.getLimitType(limitFileType0);
	                    	var relativePath = Biz.checkNull(relativePath0)?"common":relativePath0;
	                    	
	                    	var urlParams = "maxSize="+maxSize
	                    	                +"&limitType="+limitType
	                    	                +"&relativePath="+relativePath;
	                    	//服务器返回路径
	                    	Biz.ajaxToUploadFile("fileToUpload",urlParams,targetObj);
	                      }
	                  }
	              }
	          });
		},
		ajaxToUploadFile : function(fileElementId,urlParams,targetObj){
			$.ajaxFileUpload({
			    url : WEB_ROOT+'/prd/sale-photo-file-upload!uploadFile?'+urlParams,
			    type : "post",
			    dataType : "json",
			    timeout : 1000,
			    secureuri : false,// 一般设置为false
			    fileElementId : fileElementId,// 文件上传空间的id属性 <input type="file" id="uploadId" />
			    error : function(XMLHttpRequest, textStatus, errorThrown) {
			    	Global.notify("error","文件上传失败，请联系管理员");
			    	return "";
			    },
			    success : function(data) {
			    	if(data.message=="success"){
				    	Global.notify("success", "上传成功");	
				    	targetObj.val(data.imagepath);
			    	}else{
			    		Global.notify("error", data.message);
			    	}
			    }

			});
		},
		/**
		 * 获取文件限制规则
		 *  type: img 图片， txt 文本 ，xls :表格
		 * @returns
		 */
		getLimitType : function(type){
			if(type=="img"){
				return ".jpg,.jpeg,.bmp,.png";
			}else if(type =="txt"){
				return ".txt";
			}else if(type="xls"){
				return ".xlsx,.xls";
			}else{
				return "";
			}
		},
		fileSelected:function(){
			var $parentElement = $(".upload_file_dialog_content");
			var file = $parentElement.find("#fileToUpload").val();
			$parentElement.find("#selectedFileDir").val(file);
		},
		fileSelect : function(){
			var $parentElement = $(".upload_file_dialog_content");
			$parentElement.find("#fileToUpload").click();
		},
		/**end  图片上传工具方法 **/
		/**
		 * 弹出商品库页面
		 */
		showSalespkgKnowDialog:function($form){
			var title = '选择商品';
			var	id = "know-select-salespkg-konw";
			var url = WEB_ROOT + '/prd/salespkg-know!forward?_to_=selection';
			$(this).popupDialog({
                url : url,
                title : title,
				id : id,
                callback : function(rowdata) {
                	$form.find("input[name='extraAttributes.objname']").val(rowdata['knowname']);
        			$form.find("input[name='adobj']").val(rowdata['id']);
                }
            })
		},
		/**
		 * 创建普通表单的按钮
		 * @param isPosition
		 * @param btnTitle ：按钮名称
		 * @param btnClass ：按钮样式
		 * @param btnAction :按钮事件
		 * @returns
		 */
		createCustomBtn : function(isPosition, btnTitle, btnClass, btnAction) {
			var newBtn = $('<li '
					+ (isPosition ? 'data-position="multi"' : '')
					+ 'data-toolbar="show"><a modal-size="modal-wide" href="javascript:;"><i class="fa '
					+ btnClass + '"></i> ' + btnTitle + '</a></li>');

			newBtn.children("a").bind("click", btnAction);
			return newBtn;
		},

		excelUpload : function(url,tip,successCallback,params){
            var content =  "<div class='upload_file_dialog_content'>"
                +"<div class='row'>"
                +"  <div class='col-md-8'>"
                +" <div class='form-group'> "
                +"  <label class='control-label'>上传文件:</label>"
                +" <div class='controls'>"
                +"<input type='file' id='id_importExcel' name='myFile' style='display: block;'" +
                " accept='application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel' />"
                +" </div>"
                +"  </div>"
                +"  </div>"
                +"</div>"
                +"<div class='row'>"
                +"<div class='col-md-3'>"
                +"<label class='control-label' style='position: absolute;left: 10px;color: red;width: 350px;margin-left: 15px;font-size:12px;'>"+tip+"</label>"
                +"</div>"
                +"</div>"
                +"</div>";
            bootbox.dialog({
                message: content,
                title: "选择文件",
                buttons: {
                    Cancel: {
                        label: "取消",
                        className: "btn-default",
                        callback: function () {
                        }
                    }
                    , OK: {
                        label: "上传",
                        className: "btn-primary",
                        callback: function () {
                            var formData = new FormData();
                            formData.append("myFile",$("#id_importExcel")[0].files[0]);
                            if(params!=null && params!=""){
                                params.forEach(function(value,key,map){
                                    formData.append(key,value);
								});
							}
                            if($("#id_importExcel").val()){
                                $.ajax({
                                    url: url,
                                    type: 'POST',
                                    cache: false,
                                    data: formData,
                                    processData: false,
                                    contentType: false,
                                    dataType:'json',
                                    success : function(response) {
                                        if (response.type == "success") {
                                            Global.notify("success",response.message);
                                            successCallback();
                                        } else {
                                            $("#id_importExcel").val("");
                                            Global.notify("error", response.message);
                                        }
                                    }
                                });
                            } else{
                                Global.notify("error", "请选择Excel文件");
                            }
                        }
                    }
                }
            });

        }
		
    };
}();