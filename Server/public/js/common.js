/*import { loggers } from "winston";*/

var daysOfWeek= ["일","월","화","수","목","금","토"];

function deleteUser(id){
    $.ajax(
        {
          url: "/auth/user/"+id+"",
          type: "GET",
          data: {id:  id},
          success: function(result){
            $('input[name=d_id]').attr('value',result[0].ID);
            $('input[name=d_name]').attr('value',result[0].NAME);
            $('input[name=d_empno]').attr('value',result[0].EMPNO);
            $('input[name=d_telid]').attr('value',result[0].TELID);
            $('input[name=d_center_value]').attr('value',result[0].CENTER_VALUE);
            $('input[name=d_aut_value]').attr('value',result[0].AUT_VALUE);
            $('input[name=d_lockyn]').attr('value',result[0].LOCKYN);                        
          },
          error: function(error) {
              alert('Error!');
          }
    });
}
function modifyUser(id){
    $.ajax(
        {
          url: "/auth/user/"+id+"",
          type: "GET",
          data: {id:  id},
          success: function(result){
            $('input[name=m_id]').attr('value',result[0].ID);
            $('input[name=m_name]').attr('value',result[0].NAME);
            $('input[name=m_empno]').attr('value',result[0].EMPNO);
            $('input[name=m_telid]').attr('value',result[0].TELID);
            $('textarea#m_remark').html(result[0].REMARK);
            $('#m_center_value').val(result[0].CENTER_VALUE);  /* SELECTED */
            $('#m_aut_value').val(result[0].AUT_VALUE);  /* SELECTED */
            $('#m_lockyn').val(result[0].LOCKYN);   /* SELECTED */
        },
          error: function(error) {
              alert('Error!');
          }
    });
}
function comparePassword(op) {
    if (op == 'm') {
        pass1 = document.getElementById('m_new_password').value;
        pass2 = document.getElementById('m_confirm_password').value;
        if (pass1 == '' || pass2 == '') return;
        if (pass1 == '' && pass2 == '') {
            document.getElementById("m_warning").innerHTML = "";
            return;
        }
    }
    else if (op == 'a') {
        pass1 = document.getElementById('a_new_password').value;
        pass2 = document.getElementById('a_confirm_password').value;
        if (pass1 == '' || pass2 == '') return;
    }
    if (pass1 == pass2) {
        if(op=='m') {
            document.getElementById("m_warning").innerHTML = "";
            document.getElementById("m_submit").removeAttribute('disabled');
            //$('input[name=m_submit]').removeAttr('disabled');
        }
        else if(op=='a') {
            document.getElementById("a_warning").innerHTML = "";
            //$('input[name=a_submit]').removeAttr('disabled');
            document.getElementById("a_submit").removeAttribute('disabled');
        }
    }
    else {
        if(op=='m') {
            alert('비밀번호가 일치하지 않습니다!');
            document.getElementById("m_warning").innerHTML = "<div class='alert alert-danger' role='alert'>비밀번호가 일치하지 않습니다!</div>";
            //$('input[name=m_submit]').attr('disabled','true');
            document.getElementById("m_submit").setAttribute('disabled', 'true');
        }
        else if(op=='a') {
            document.getElementById("a_warning").innerHTML = "<div class='alert alert-danger' role='alert'>비밀번호가 일치하지 않습니다!</div>";
            //$('input[name=a_submit]').attr('disabled','true');
            document.getElementById("a_submit").setAttribute('disabled', 'true');
        }
    }
}
function validateID(id){
    if (id == '') { return; }
    var cond = `UserID='${id}'`;
    var fields = ['UserID'];
    a2s_getDBSomeByCond('USER_INFO', fields, cond, function(res) {
        if (res.length > 0) {
            document.getElementById("a_warning").innerHTML = "<div class='alert alert-danger' role='alert'>중복되는 아이디가 존재합니다!</div>";
            document.getElementById("a_submit").setAttribute('disabled', 'true');
        }
        else {
            document.getElementById("a_warning").innerHTML = "";
            document.getElementById("a_submit").removeAttribute('disabled');
        }
    });
}
function validateEmployeeCode(code){
    if (code == '') { return; }
    var cond = `EmployeeCode='${code}'`;
    var fields = ['EmployeeCode'];
    a2s_getDBSomeByCond('EMPLOYEE_INFO', fields, cond, function(res) {
        if (res.length > 0) {
            document.getElementById("a_warning").innerHTML = "<div class='alert alert-danger' role='alert'>중복되는 사원번호가 존재합니다!</div>";
            document.getElementById("a_submit").setAttribute('disabled', 'true');
        }
        else {
            document.getElementById("a_warning").innerHTML = "";
            document.getElementById("a_submit").removeAttribute('disabled');
        }
    });
}
function a2s_changePassword(id, a, b, callback) 
{
    $.ajax(
        {
            url: "/auth/password",
            type: "PUT",
            data: {UserID: id, cur_password: a, new_password: b},
            success: function(result){
                document.getElementById("m_cur_password").innerHTML = "";
                document.getElementById("m_new_password").innerHTML = "";
                document.getElementById("m_confirm_password").innerHTML = "";
                callback(null, "변경 완료");
                
            },
            error: function(error) {
                //console.log(error.responseText)
                callback(error);
            }
    });
}
function a2s_changePassword(id, a, b, callback) 
{
    $.ajax(
        {
            url: "/auth/password",
            type: "PUT",
            data: {UserID: id, cur_password: a, new_password: b},
            success: function(result){
                document.getElementById("m_cur_password").innerHTML = "";
                document.getElementById("m_new_password").innerHTML = "";
                document.getElementById("m_confirm_password").innerHTML = "";
                callback(null, "변경 완료");
                
            },
            error: function(error) {
                //console.log(error.responseText)
                callback(error);
            }
    });
}

function a2s_getNewPassword(password, callback) 
{
    $.ajax(
        {
            url: "/auth/password",
            type: "GET",
            data: {password: password},
            success: function(result){
                callback(result);
            },
            error: function(error) {
                //console.log(error.responseText)
                callback(error);
            }
    });
}

function createMenu(ul, menu) {
    for (var i=0;i<menu.length;i++) {
        var li=$(ul).append('<li id="'+menu[i].ID+'"><a href="'+menu[i].LINK+'">'+menu[i].VALUE+'</a></li>');
    }
    var subul=$('<ul id="submenu'+menu[i].LINK+'"></ul>');
}
function getMenu() {
    $.ajax(
        {
          url: "/menu/",
          type: "GET",
          success: function(result) {
            createMenu('#sidemenu', result);
        },
          error: function(error) {
              alert('Error!');
          }
    });
}
function getDate() 
{
	var today = new Date();
	return today;
}
function drawTable()
{
    $.ajax(
        {
          url: "/api/db/user/",
          type: "GET",
          success: function(result){
            var table = new Tabulator("#example-table", {
                height:"311px",
                data:result,
                autoColumns:true,
            });
          },
          error: function(error) {
              alert('Error!');
          }
    });
}
var _format = {
    opens: 'left',
    drops: 'up',
    //singleDatePicker: true,
	startDate: getDate(),
	endDate: getDate(),	
	locale: {
		"format": "YYYY-MM-DD",
		"separator": " ~ ",
		"daysOfWeek": [
            "일","월","화","수",
            "목","금","토"
		],
		"monthNames": [
			"1월","2월","3월","4월","5월","6월",
			"7월","8월","9월","10월","11월","12월"
		],
	}
};
function getDateFormat(posionx, posiony, format, rangedate) {
    if (rangedate === "no") {
        _format.singleDatePicker = true;
    }
    _format.opens = posionx;
    _format.drops = posiony;
    _format.locale.format = format;
    return _format;
}
function getCalendar(table, callback) 
{
    var res;
    $.ajax({
        url: "/api/db/all",
        type: "GET",
        data: {table: table},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}

function reportCreate(fileName, xml, callback){
    $.ajax({
        url: "/api/report",
        type: "POST",
        data: {fileName: fileName, xml: xml},
        success: function(result){
            callback(result);
        },
        error: function(error) {
            if(error)
                throw error;
        }
    });
}

// function XMLCreate(fileName, xml, callback) {
//     $.ajax({
//         url: "/api/fileWrite",
//         type: "POST",
//         data: { fileName: fileName, data: xml },
//         success: function (result) {
//             callback(result);
//         },
//         error: function (error) {
//             callback(error);
//         }
//     });
// }

// function exportPDF(file, callback){
//     $.ajax({
//         url: "/api/exportPDF",
//         type: "GET",
//         data: {file: file},
//         success: function(result){
//             callback(result);
//         },
//         error: function(error) {
//             if(error)
//                 throw error;
//         }
//     });
// }

function a2s_getDBAll(table, callback) {
    var res;
    $.ajax({
        url: "/api/db/all",
        type: "GET",
        data: {table: table},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(null, result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error, null);
        }
    });
}
function a2s_getDBAllOrderBy(table, ofield, order, callback) {
    var res;
    $.ajax({
        url: "/api/db/allOrderBy",
        type: "GET",
        data: {table: table, ofield: ofield, order: order},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}

function a2s_getDBSome(table, fields, callback) {
    var res;
    $.ajax({
        url: "/api/db/some",
        type: "GET",
        data: {table: table, fields: fields},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_getDBSomeOrderBy(table, fields, ofield, order, callback) {
    var res;
    $.ajax({
        url: "/api/db/someOrderBy",
        type: "GET",
        data: {table: table, fields: fields, ofield: ofield, order: order},
        success: function(result, textStatus, jqXHR){
           //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
           //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_getDBByCond(table, cond, callback) {
    var res;
    $.ajax({
        url: "/api/db/byCond",
        type: "GET",
        data: {table: table, cond: cond},
        success: function(result, textStatus, jqXHR){
           //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
           //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_getDBSomeByCond(table, fields, cond, callback) {
    var res;
    $.ajax({
        url: "/api/db/someByCond",
        type: "GET",
        data: {table: table, fields: fields, cond: cond},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_getDBField(table, callback) {
    var res;
    $.ajax({
        url: "/api/db/field",
        type: "GET",
        data: {table: table},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_addDB(table, values, callback) {
    var res;
    $.ajax({
        url: "/api/db",
        type: "POST",
        dataType: 'json',
        data: jQuery.param({table: table, values: values}),
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}

function a2s_addMulitipleDB(table, values, callback) {
    $.ajax({
        url: "/api/db/multiple",
        type: "POST",
        dataType: 'json',
        data: jQuery.param({table: table, values: values}),
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}

function a2s_addorder(table1, values1, cond1,values2,values3,table2,values4,table3,values5,table4,values6,table5,values7,callback) {
    $.ajax({
        url: "/api/db/order",
        type: "POST",
        dataType: 'json',
        data: jQuery.param({table1:table1, values1:values1, cond1:cond1, table2:table2,values2 :values2,table3:table3, values3:values3, table4:table4, values4:values4,table5:table5, values5: values5, values6:values6,values7:values7}),
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}

function a2s_modifyorder(table1,values1,cond2,values2,cond3,values3,callback) {
    $.ajax({
        url: "/api/db/order2",
        type: "POST",
        dataType: 'json',
        data: jQuery.param({table1:table1, values1:values1, cond2:cond2,values2 :values2,cond3:cond3,values3:values3}),
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}

function a2s_deleteDBByCond(table, cond, callback) {
    var res;
    $.ajax({
        url: "/api/db/byCond",
        type: "DELETE",
        data: {table: table, cond: cond},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}

function a2s_ioDeleteInfoMod(table1,cond1,table2,cond2,values,callback){
    var res;
    $.ajax({
        url: "/api/db/ioDelinfoMod",
        type: "DELETE",
        data: {
            table1: table1, 
            cond1: cond1, 
            table2: table2, 
            values: values, 
            cond2: cond2
        },
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_deleteMaterial(table1, cond1, table2, values, cond2, callback) {
    var res;
    $.ajax({
        url: "/api/db/material",
        type: "DELETE",
        data: {
            table1: table1, 
            cond1: cond1, 
            table2: table2, 
            values: values, 
            cond2: cond2
        },
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_addFaulty(table1, values1, table2, cond ,values2, callback) {
    $.ajax({
        url: "/api/db/Faulty",
        type: "POST",
        data: {
            table1: table1, 
            values1: values1, 
            table2: table2,
            cond: cond, 
            values2: values2,
        },
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_addAbuFaulty(
    table_f,values_f,table_p,values_p,cond_p,
    table_pi,values_pi,cond_pi,table_io,values_io,cond_io,callback)
{
    $.ajax({
        url: "/api/db/AbuFaulty",
        type: "POST",
        data: {
            table_f : table_f,
            values_f : values_f,
            table_p : table_p,
            values_p : values_p,
            cond_p : cond_p,
            table_pi : table_pi,
            values_pi : values_pi,
            cond_pi : cond_pi,
            table_io : table_io,
            values_io : values_io,
            cond_io : cond_io
        },
        success : function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    })
}
function a2s_addMiddlecheck(table1, values1, table2, cond ,values2, callback) {
    $.ajax({
        url: "/api/db/middlecheck",
        type: "POST",
        data: {table1: table1, values1: values1, table2: table2, values2: values2, cond: cond},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_FaultaddIOAddInfomod(table_main,value_main,table_sub,value_sub,table_mod,value_mod,cond_mod,callback){
    $.ajax({
        url : "/api/db/FaultaddIOAddInfomod",
        type: "POST",
        data : {
            table_main : table_main,
            value_main : value_main,
            table_sub : table_sub,
            value_sub : value_sub,
            table_mod : table_mod,
            value_mod : value_mod,
            cond_mod : cond_mod
        },
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    })
}
function a2s_2del1mod(table_main,cond_main,table_sub,cond_sub,table_mod,value_mod,cond_mod,callback){
    $.ajax({
        url : "/api/db/twoDeloneMod",
        type: "DELETE",
        data : {
            table_main: table_main,
            cond_main: cond_main,
            table_sub: table_sub,
            cond_sub: cond_sub,
            table_mod: table_mod,
            value_mod: value_mod,
            cond_mod: cond_mod
        },
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    })
}
function a2s_2del1mod_arr(table_main,cond_main,table_sub,cond_sub,table_mod,value_mod,cond_mod,callback){
    $.ajax({
        url : "/api/db/2del1mod_arr",
        type: "DELETE",
        data : {
            table_main : table_main,
            cond_main : cond_main,
            table_sub : table_sub,
            cond_sub : cond_sub,
            table_mod : table_mod,
            value_mod : value_mod,
            cond_mod : cond_mod
        },
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    })
}
function a2s_addPacking2(table1, values1, table2, cond ,values2, table3, values3, callback) {
    $.ajax({
        url: "/api/db/packing2",
        type: "POST",
        data: {table1: table1, values1: values1, table2: table2, values2: values2, cond: cond, table3: table3, values3: values3},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_addProductinfo(table1, values1, table2,values2, callback) {
    $.ajax({
        url: "/api/db/productinfo",
        type: "POST",
        data: {table1: table1, values1: values1, table2: table2, values2: values2},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_doubleMod(table1,values1,cond1,table2,values2,table3,values3,callback){
    $.ajax({
        url: "/api/db/doubleMod",
        type: "POST",
        data: {table1: table1, values1: values1, table2: table2, values2: values2,cond1: cond1,table3:table3,values3:values3},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}

function a2s_doubleMod2(table1,values1,cond1,table2,values2,table3,values3,callback){
    $.ajax({
        url: "/api/db/doubleMod2",
        type: "POST",
        data: {table1: table1, values1: values1, table2: table2, values2: values2,cond1: cond1,table3:table3,values3:values3},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}

function a2s_addFinalcheck1(table4,value4,table1, values1, table2,table3,cond2,cond3,values2,values3 ,callback) {
    $.ajax({
        url: "/api/db/finalcheck1",
        type: "POST",
        data: {table1: table1, values1: values1, table2: table2, values2: values2, cond2: cond2,table3:table3,cond3:cond3,values3:values3,table4:table4,values4:value4},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}

function a2s_addPacking(table1, values1, table2, cond2, values2, table3, cond3, values3, table4, values4, table5, values5, callback) {
    $.ajax({
        url: "/api/db/packing",
        type: "POST",
        data: { table1: table1, values1: values1, table2: table2, values2: values2, cond2: cond2, table3: table3, cond3: cond3, values3: values3, table4: table4, values4: values4, table5: table5, values5: values5 },
        success: function (result, textStatus, jqXHR) {
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function (jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}

function a2s_addProduction(table1, values1, table2, values2, table3, values3, cond3, table_io,values_io,callback) {
    $.ajax({
        url: "/api/db/production",
        type: "POST",
        data: {table1: table1, 
            values1: values1, 
            table2: table2, 
            values2: values2, 
            table3: table3, 
            values3: values3, 
            cond3: cond3,
            table_io : table_io,
            values_io : values_io
        },
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_MaterialAddInOutAdd(table1,values1,table2,values2,callback){
    $.ajax({
        url: "/api/db/MaterialAddInOutAdd",
        type: "POST",
        data: {
            table1: table1, 
            values1: values1, 
            table2: table2, 
            values2: values2 
        },
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_doubleAdd(table1,values1,table2,values2,callback){
    $.ajax({
        url: "/api/db/doubleAdd",
        type: "POST",
        data: {
            table1: table1, 
            values1: values1, 
            table2: table2, 
            values2: values2 
        },
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}

function a2s_deleteModifyDelete(table1, cond1, table2, values, cond2, table_io,cond_io,callback) {
    $.ajax({
        url: "/api/db/delModDel",
        type: "DELETE",
        data: {table1: table1, 
            cond1: cond1, 
            table2: table2, 
            values: values, 
            cond2: cond2,
            table_io : table_io,
            cond_io : cond_io
        },
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_deleteProduction(table1, cond1, table2, values, cond2,callback) {
    $.ajax({
        url: "/api/db/production",
        type: "DELETE",
        data: {table1: table1, 
            cond1: cond1, 
            table2: table2, 
            values: values, 
            cond2: cond2,
        },
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}

function a2s_modifyDBByCond(table, values, cond, callback) {
    var res;
    $.ajax({
        url: "/api/db/byCond",
        type: "put",
        dataType: 'json',
        data: {table: table, values: values, cond: cond},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_searchDBByCond(table, field, value, callback) {
    var res;
    $.ajax({
        url: "/api/db/searchByCond",
        type: "GET",
        data: {table: table, field: field, value: value},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(null, result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error, null);
        }
    });
}

function a2s_getDBSomeGroupBy(table, fields, group, callback) {
    var res;
    $.ajax({
        url: "/api/db/someGroupBy",
        type: "GET",
        data: {table: table, fields: fields, group: group},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error, null);
        }
    });
}
function a2s_getCache(key, callback) {
    var res;
    $.ajax({
        url: "/api/cache",
        type: "GET",
        data: {key: key},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(null, result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error, null);
        }
    });
}
function a2s_addCache(key, values, callback) {
    var res;
    $.ajax({
        url: "/api/cache",
        type: "POST",
        data: {key: key, values: values},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_modifyCache(key, values, callback) {
    var res;
    $.ajax({
        url: "/api/cache",
        type: "PUT",
        data: {key: key, values: values},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_deleteCache(key, subkey, callback) {
    var res;
    $.ajax({
        url: "/api/cache",
        type: "DELETE",
        data: {key: key, subkey: subkey},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_deleteSomeCache(keys, subkeys, callback) {
    var res;
    $.ajax({
        url: "/api/cacheSome",
        type: "DELETE",
        data: {keys: keys, subkeys: subkeys},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_deleteCacheAll(key, callback) {
    var res;
    $.ajax({
        url: "/api/cache/all",
        type: "DELETE",
        data: {key: key},
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function a2s_getCenterById(id, callback) {
    var res;
    $.ajax({
        url: "/api/center/"+id+"",
        type: "GET",
        success: function(result, textStatus, jqXHR){
            //console.log(`result1`, result);
            //console.log(`status1`, jqXHR.status);
            callback(result);
        },
        error: function(jqXHR, error) {
            //console.log(`error1`, error);
            //console.log(`status1`, jqXHR.status);
            callback(error);
        }
    });
}
function getProductionDailyData(result, length)
{
    var data = [];
    for (var i=0; i<length; i++) {    
        data[i] = 0;
    }
    for (var i=0; i<result.length; i++) {
        var str = result[i].ProductionDate.split('-');
        var index = parseInt(str[2])-1;
        data[index] = parseInt(result[i].Sum);
    }
    return data;
}
function getProductionMonthlyData(result, length)
{
    var data = [];
    for (var i=0; i<12; i++) {    
        data[i] = 0;
    }
    for (var i=0; i<result.length; i++) {
        var str = result[i].ProductionDate.split('-');
        var index = parseInt(str[1])-1;
        data[index] = parseInt(result[i].Sum);
    }
    return data;
}
function getProductionAnualData(result, sYear, eYear)
{
    length = eYear - sYear + 1;
    var data = [];
    for (var i=0; i<length; i++) {    
        data[i] = 0;
    }
    for (var i=0; i<result.length; i++) {
        var str = result[i].ProductionDate.split('-');
        var index = parseInt(str[0] - sYear);
        data[index] = parseInt(result[i].Sum);
    }
    return data;
}
function getDailyData(result, length)
{
    var data = [];
    for (var i=0; i<length; i++) {    
        data[i] = 0;
    }
    for (var i=0; i<result.length; i++) {
        var str = result[i].ReleaseDate.split('-');
        var index = parseInt(str[2])-1;
        data[index] = parseInt(result[i].Sum);
    }
    return data;
}
function getDailyTwoData(result, length, field)
{
    var data = [];
    for (var i=0; i<length; i++) {    
        data[i] = 0;
    }
    for (var i=0; i<result.length; i++) {
        var str = result[i].OrderDate.split('-');
        var index = parseInt(str[2])-1;
        data[index] = parseInt(result[i][field]);
    }
    return data;
}
function getMonthlyTwoData(result, length, field)
{
    var data = [];
    for (var i=0; i<12; i++) {    
        data[i] = 0;
    }
    for (var i=0; i<result.length; i++) {
        var str = result[i].OrderDate.split('-');
        var index = parseInt(str[1])-1;
        data[index] = parseInt(result[i][field]);
    }
    return data;
}
function getMonthlyData(result, length)
{
    var data = [];
    for (var i=0; i<12; i++) {    
        data[i] = 0;
    }
    for (var i=0; i<result.length; i++) {
        var str = result[i].ReleaseDate.split('-');
        var index = parseInt(str[1])-1;
        data[index] = parseInt(result[i].Sum);
    }
    return data;
}
function getAnualData(result, sYear, eYear)
{
    length = eYear - sYear + 1;
    var data = [];
    for (var i=0; i<length; i++) {    
        data[i] = 0;
    }
    for (var i=0; i<result.length; i++) {
        var str = result[i].ReleaseDate.split('-');
        var index = parseInt(str[0] - sYear);
        data[index] = parseInt(result[i].Sum);
    }
    return data;
}
function getPlanData(result, sDate)
{
    //var s = moment(sDate, 'YYYY-MM-DD');
    var data = [];
    for (var i=0; i<7; i++) {
        data[i] = 0;
    }
    for (var i=0; i<result.length; i++) {
        var t = moment(result[i].ProductDate, 'YYYY-MM-DD');
        var index = t.diff(sDate, 'days');
        data[index] = parseInt(result[i].Sum);
    }
    return data;
}
function getEndOfDate(year, month) {
    var endOfDate = 31;
    switch(month) {
        case 4: 
        case 6: 
        case 9: 
        case 11:
            endOfDate = 30;
        break;
        case 2:
            if ((year%4 == 0 && year%100 !=0) || year%400 == 0) { endOfDate = 29; }
            else                                                { endOfDate = 28; }
        break;
        default:
            endOfDate = 31;
        break;
    }
    return endOfDate;
}
function getDailyLabels(year, month) 
{
    var endOfDate = getEndOfDate(year, month);
    var labels = [];
    for (var i=0; i<endOfDate; i++) {
        labels[i] = i+1;
    }
    return labels;
}
function getDaysOfWeekLabels(start) 
{
    var labels = [];
    for (var i=0; i<7; i++) {
        var date = start.split('-');
        labels[i] = `${date[1]}.${date[2]}(${daysOfWeek[i]})`;
        start = GbFunc.GetNextDay(start);
    }
    return labels;
}

function drawProductionDailyChart(id, type, date, p_name) {
    var strDate = date.split('-');
    var year = strDate[0];
    var month = strDate[1];
    var labels = getDailyLabels(parseInt(year), parseInt(month));
    var start = `${year}-${month}-01`;
    var end = `${year}-${month}-${String(labels[labels.length-1]).padStart(2, '0')}`;
    $.ajax({
        url: "/api/productionDaily/",
        type: "GET",
        data: {start: start, end: end, p_name: p_name},
        success: function(result, textStatus, jqXHR){
            var ctx = document.getElementById(id).getContext('2d');
            var data = getProductionDailyData(result, labels.length);
            var myChart = new Chart(ctx, {
                type: type,
                data: {
                    labels: labels,
                    datasets: [{
                        label: `${year}년 ${month}월 일별 생산 현황`,
                        data: data,
                        fillColor: "rgba(125,22,108,0.2)",
                        strokeColor: "rgba(125,22,108,1)",
                        pointColor: "rgba(125,22,108,1)",
                        pointStrokeColor: "#000",
                        pointHighlightFill: "#000",
                        pointHighlightStroke: "rgba(125,22,108,1)",
                        backgroundColor: "rgba(125, 22, 108, 0.6)",
                        borderColor: "rgba(0, 0, 0, 0)",
                    }]
                },
                options: {
                    maintainAspectRatio: true, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero:true
                            }
                        }]
                    },
                    scaleFontColor: "#fff",
                    // font family
                    defaultFontFamily: "'Roboto', sans-serif",
                    // background grid lines color
                    scaleGridLineColor: "rgba(255,255,255,.1)",
                }
            });            
        },
        error: function(jqXHR, error) {
            //console.log(`error2`, error);
            //console.log(`status2`, jqXHR.status);
        }
    });
}

function drawProductionMonthlyChart(id, type, date, p_name) {
    var strDate = date.split('-');
    var year = strDate[0];
    var start = `${year}-01-01`;
    var end = `${year}-12-31`;

    $.ajax({
        url: "/api/productionMonthly/",
        type: "GET",
        data: {start: start, end: end, p_name: p_name},
        success: function(result, textStatus, jqXHR){
            var ctx = document.getElementById(id).getContext('2d');
            var data = getProductionMonthlyData(result, 12);
            var myChart = new Chart(ctx, {
                type: type,
                data: {
                    labels: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                    datasets: [{
                        label: `${year}년 월별 생산 현황`,
                        data: data,
                        fillColor: "rgba(125,22,108,0.2)",
                        strokeColor: "rgba(125,22,108,1)",
                        pointColor: "rgba(125,22,108,1)",
                        pointStrokeColor: "#000",
                        pointHighlightFill: "#000",
                        pointHighlightStroke: "rgba(125,22,108,1)",
                        backgroundColor: "rgba(125, 22, 108, 0.6)",
                        borderColor: "rgba(0, 0, 0, 0)",
                    }]
                },
                options: {
                    maintainAspectRatio: true, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero:true
                            }
                        }]
                    },
                    scaleFontColor: "#fff",
                    // font family
                    defaultFontFamily: "'Roboto', sans-serif",
                    // background grid lines color
                    scaleGridLineColor: "rgba(255,255,255,.1)",
                }
            });  
        },
        error: function(jqXHR, error) {
            //console.log(`error2`, error);
            //console.log(`status2`, jqXHR.status);
        }
    });
}
function drawProductionAnualChart(id, type, s_period, e_period, p_name) {
    var strSDate = s_period.split('-');
    var strEDate = e_period.split('-');
    var sYear = strSDate[0];
    var eYear = strEDate[0];
    var start = `${sYear}-01-01`;
    var end = `${eYear}-12-31`;
    
    var labels = [];
    for (var i=sYear, j=0; i<=eYear; j++, i++) {
        labels[j] = i;
    }
    $.ajax({
        url: "/api/productionAnual/",
        type: "GET",
        data: {start: start, end: end, p_name: p_name},
        success: function(result, textStatus, jqXHR){
            var ctx = document.getElementById(id).getContext('2d');
            var data = getProductionAnualData(result, sYear, eYear);
           // //console.log(data);
            var myChart = new Chart(ctx, {
                type: type,
                data: {
                    labels: labels,
                    datasets: [{
                        label: `${sYear}-${eYear}년 생산 현황`,
                        data: data,
                        fillColor: "rgba(125,22,108,0.2)",
                        strokeColor: "rgba(125,22,108,1)",
                        pointColor: "rgba(125,22,108,1)",
                        pointStrokeColor: "#000",
                        pointHighlightFill: "#000",
                        pointHighlightStroke: "rgba(125,22,108,1)",
                        backgroundColor: "rgba(125, 22, 108, 0.6)",
                        borderColor: "rgba(0, 0, 0, 0)",
                    }]
                },
                options: {
                    maintainAspectRatio: true, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero:true
                            }
                        }]
                    },
                    scaleFontColor: "#fff",
                    // font family
                    defaultFontFamily: "'Roboto', sans-serif",
                    // background grid lines color
                    scaleGridLineColor: "rgba(255,255,255,.1)",
                }
            });  
        },
        error: function(jqXHR, error) {
            //console.log(`error2`, error);
            //console.log(`status2`, jqXHR.status);
        }
    });
}

function drawDailyChart(id, type, date, c_name, p_name) {
    var strDate = date.split('-');
    var year = strDate[0];
    var month = strDate[1];
    var labels = getDailyLabels(parseInt(year), parseInt(month));
    var start = `${year}-${month}-01`;
    var end = `${year}-${month}-${String(labels[labels.length-1]).padStart(2, '0')}`;
    $.ajax({
        url: "/api/productOutDaily/",
        type: "GET",
        data: {start: start, end: end, c_name: c_name, p_name: p_name},
        success: function(result, textStatus, jqXHR){
            var ctx = document.getElementById(id).getContext('2d');
            var data = getDailyData(result, labels.length);
            var myChart = new Chart(ctx, {
                type: type,
                data: {
                    labels: labels,
                    datasets: [{
                        label: `${year}년 ${month}월 일별 출하 현황`,
                        data: data,
                        fillColor: "rgba(125,22,108,0.2)",
                        strokeColor: "rgba(125,22,108,1)",
                        pointColor: "rgba(125,22,108,1)",
                        pointStrokeColor: "#000",
                        pointHighlightFill: "#000",
                        pointHighlightStroke: "rgba(125,22,108,1)",
                        backgroundColor: "rgba(125, 22, 108, 0.6)",
                        borderColor: "rgba(0, 0, 0, 0)",
                    }]
                },
                options: {
                    maintainAspectRatio: true, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero:true
                            }
                        }]
                    },
                    scaleFontColor: "#fff",
                    // font family
                    defaultFontFamily: "'Roboto', sans-serif",
                    // background grid lines color
                    scaleGridLineColor: "rgba(255,255,255,.1)",
                }
            });            
        },
        error: function(jqXHR, error) {
            //console.log(`error2`, error);
            //console.log(`status2`, jqXHR.status);
        }
    });
}
function drawMonthlyChart(id, type, date, c_name, p_name) {
    var strDate = date.split('-');
    var year = strDate[0];
    var start = `${year}-01-01`;
    var end = `${year}-12-31`;

    $.ajax({
        url: "/api/productOutMonthly/",
        type: "GET",
        data: {start: start, end: end, c_name: c_name, p_name: p_name},
        success: function(result, textStatus, jqXHR){
            var ctx = document.getElementById(id).getContext('2d');
            var data = getMonthlyData(result, 12);
            var myChart = new Chart(ctx, {
                type: type,
                data: {
                    labels: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                    datasets: [{
                        label: `${year}년 월별 출하 현황`,
                        data: data,
                        fillColor: "rgba(125,22,108,0.2)",
                        strokeColor: "rgba(125,22,108,1)",
                        pointColor: "rgba(125,22,108,1)",
                        pointStrokeColor: "#000",
                        pointHighlightFill: "#000",
                        pointHighlightStroke: "rgba(125,22,108,1)",
                        backgroundColor: "rgba(125, 22, 108, 0.6)",
                        borderColor: "rgba(0, 0, 0, 0)",
                    }]
                },
                options: {
                    maintainAspectRatio: true, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero:true
                            }
                        }]
                    },
                    scaleFontColor: "#fff",
                    // font family
                    defaultFontFamily: "'Roboto', sans-serif",
                    // background grid lines color
                    scaleGridLineColor: "rgba(255,255,255,.1)",
                }
            });  
        },
        error: function(jqXHR, error) {
            //console.log(`error2`, error);
            //console.log(`status2`, jqXHR.status);
        }
    });
}
function drawAnualChart(id, type, s_period, e_period, c_name, p_name) {
    var strSDate = s_period.split('-');
    var strEDate = e_period.split('-');
    var sYear = strSDate[0];
    var eYear = strEDate[0];
    var start = `${sYear}-01-01`;
    var end = `${eYear}-12-31`;
    
    var labels = [];
    for (var i=sYear, j=0; i<=eYear; j++, i++) {
        labels[j] = i;
    }
    $.ajax({
        url: "/api/productOutAnual/",
        type: "GET",
        data: {start: start, end: end, c_name: c_name, p_name: p_name},
        success: function(result, textStatus, jqXHR){
            var ctx = document.getElementById(id).getContext('2d');
            var data = getAnualData(result, sYear, eYear);
            ////console.log(data);
            var myChart = new Chart(ctx, {
                type: type,
                data: {
                    labels: labels,
                    datasets: [{
                        label: `${sYear}-${eYear}년 출하 현황`,
                        data: data,
                        fillColor: "rgba(125,22,108,0.2)",
                        strokeColor: "rgba(125,22,108,1)",
                        pointColor: "rgba(125,22,108,1)",
                        pointStrokeColor: "#000",
                        pointHighlightFill: "#000",
                        pointHighlightStroke: "rgba(125,22,108,1)",
                        backgroundColor: "rgba(125, 22, 108, 0.6)",
                        borderColor: "rgba(0, 0, 0, 0)",
                    }]
                },
                options: {
                    maintainAspectRatio: true, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero:true
                            }
                        }]
                    },
                    scaleFontColor: "#fff",
                    // font family
                    defaultFontFamily: "'Roboto', sans-serif",
                    // background grid lines color
                    scaleGridLineColor: "rgba(255,255,255,.1)",
                }
            });  
        },
        error: function(jqXHR, error) {
            //console.log(`error2`, error);
            //console.log(`status2`, jqXHR.status);
        }
    });
}
function drawOrderChart(id, type, period, s_date, e_date) {
    var strDate = e_date.split('-');
    var year = strDate[0];
    var month = strDate[1];
    var labels;
    var start;
    var end;

    if (period==1) {
        url = "/api/orderDaily/";
        labels = getDailyLabels(parseInt(year), parseInt(month));
        start = `${year}-${month}-01`;
        end = `${year}-${month}-${String(labels[labels.length-1]).padStart(2, '0')}`;
        title = `${year}년 ${month}월 수주 대비 출하 현황`;
    }
    else {
        url = "/api/orderMonthly/";
        labels = ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'];
        start = `${year}-01-01`;
        end = `${year}-12-31`;
        title = `${year}년 수주 대비 출하 현황`;
    }
    $.ajax({
        url: url,
        type: "GET",
        data: {start: start, end: end},
        success: function(result, textStatus, jqXHR){
            var ctx = document.getElementById(id).getContext('2d');
            if (period == 1) {
                var data1 = getDailyTwoData(result, labels.length, 'Sum1');
                var data2 = getDailyTwoData(result, labels.length, 'Sum2');
            }
            else {
                var data1 = getMonthlyTwoData(result, labels.length, 'Sum1');
                var data2 = getMonthlyTwoData(result, labels.length, 'Sum2');                
            }
            var myChart = new Chart(ctx, {
                type: type,
                data: {
                    labels: labels,
                    datasets: [{
                        label: `수주`,
                        data: data1,
                        fillColor: "rgba(125,22,108,0.2)",
                        strokeColor: "rgba(125,22,108,1)",
                        pointColor: "rgba(125,22,108,1)",
                        pointStrokeColor: "#000",
                        pointHighlightFill: "#000",
                        pointHighlightStroke: "rgba(125,22,108,1)",
                        backgroundColor: "rgba(125, 22, 108, 0.6)",
                        borderColor: "rgba(0, 0, 0, 0)",
                    },
                    {
                        label: `출하`,
                        data: data2,
                        fillColor: "rgba(125,22,108,0.2)",
                        strokeColor: "rgba(125,22,108,1)",
                        pointColor: "rgba(125,22,108,1)",
                        pointStrokeColor: "#000",
                        pointHighlightFill: "#000",
                        pointHighlightStroke: "rgba(125,22,108,1)",
                        backgroundColor: "rgba(1, 22, 108, 0.6)",
                        borderColor: "rgba(0, 0, 0, 0)",
                    }
                    ]
                },
                options: {
                    maintainAspectRatio: true, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero:true
                            }
                        }]
                    },
                    title: {
                        display: true,
                        text: `${year}년 ${month}월 수주 대비 출하 현황`,
                        fontSize: 14,
                        fontColor: '#55166c',
                    },
                    scaleFontColor: "#fff",
                    // font family
                    defaultFontFamily: "'Roboto', sans-serif",
                    // background grid lines color
                    scaleGridLineColor: "rgba(255,255,255,.1)",
                }
            });            
        },
        error: function(jqXHR, error) {
            //console.log(`error2`, error);
            //console.log(`status2`, jqXHR.status);
        }
    });
}
function drawProductChart(id, type, date, c_name, p_name) {
    var strDate = date.split('-');
    var year = strDate[0];
    var month = strDate[1];
    var labels = getDailyLabels(parseInt(year), parseInt(month));
    var start = `${year}-${month}-01`;
    var end = `${year}-${month}-${String(labels[labels.length-1]).padStart(2, '0')}`;
    $.ajax({
        url: "/api/productOutDaily/",
        type: "GET",
        data: {start: start, end: end, c_name: c_name, p_name: p_name},
        success: function(result, textStatus, jqXHR){
            var ctx = document.getElementById(id).getContext('2d');
            var data = getDailyData(result, labels.length);
            var myChart = new Chart(ctx, {
                type: type,
                data: {
                    labels: labels,
                    datasets: [{
                        label: `${year}년 ${month}월 일별 출하 현황`,
                        data: data,
                        fillColor: "rgba(125,22,108,0.2)",
                        strokeColor: "rgba(125,22,108,1)",
                        pointColor: "rgba(125,22,108,1)",
                        pointStrokeColor: "#000",
                        pointHighlightFill: "#000",
                        pointHighlightStroke: "rgba(125,22,108,1)",
                        backgroundColor: "rgba(125, 22, 108, 0.6)",
                        borderColor: "rgba(0, 0, 0, 0)",
                    }]
                },
                options: {
                    maintainAspectRatio: true, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero:true
                            }
                        }]
                    },
                    scaleFontColor: "#fff",
                    // font family
                    defaultFontFamily: "'Roboto', sans-serif",
                    // background grid lines color
                    scaleGridLineColor: "rgba(255,255,255,.1)",
                }
            });            
        },
        error: function(jqXHR, error) {
            //console.log(`error2`, error);
            //console.log(`status2`, jqXHR.status);
        }
    });
}
function drawPlanChart(id, type, period, s_date, e_date, p_name) {
    var start, end, url, labels;
    if (period == 1) {
        /* 해당 주의 시작(일요일)과 끝(토요일) 날짜 */
        start = GbFunc.GetFirstWeekDay(e_date);
        end = GbFunc.GetLastWeekDay(e_date);
        url = "/api/productPlan",
        labels = getDaysOfWeekLabels(start);
    }
    $.ajax({
        url: url,
        type: "GET",
        data: {start: start, end: end, p_name: p_name},
        success: function(result, textStatus, jqXHR){
            var data = getPlanData(result, start);
            var ctx = document.getElementById(id).getContext('2d');
            var myChart = new Chart(ctx, {
                type: type,
                data: {
                    labels: labels,
                    datasets: [{
                        label: `${start} - ${end} 생산 계획`,
                        data: data,
                        fillColor: "rgba(125,22,108,0.2)",
                        strokeColor: "rgba(125,22,108,1)",
                        pointColor: "rgba(125,22,108,1)",
                        pointStrokeColor: "#000",
                        pointHighlightFill: "#000",
                        pointHighlightStroke: "rgba(125,22,108,1)",
                        backgroundColor: "rgba(125, 22, 108, 0.6)",
                        borderColor: "rgba(0, 0, 0, 0)",
                    }]
                },
                options: {
                    maintainAspectRatio: true, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
                    scales: {
                        xAxes: [{
                            ticks: {
                                beginAtZero:true,
                                precision: 0,
                            }
                        }]
                    },
                    title: {
                        display: false,
                        text: `${start} - ${end} 생산 계획`,
                        fontSize: 14,
                        fontColor: '#55166c',
                    },
                    scaleFontColor: "#fff",
                    // font family
                    defaultFontFamily: "'Roboto', sans-serif",
                    // background grid lines color
                    scaleGridLineColor: "rgba(255,255,255,.1)",
                }
            });            
        },
        error: function(jqXHR, error) {
            //console.log(`error2`, error);
            //console.log(`status2`, jqXHR.status);
        }
    });
}
function getProductPlan(id, s_date, e_date, p_name) {
    var res;
    var e = moment(e_date, 'YYYY-MM-DD');
    $.ajax({
        url: "/api/productPlan/",
        type: "GET",
        data: {start: s_date, end: e_date, p_name: p_name},
        success: function(result, textStatus, jqXHR){
            res = getPlanData(result, s_date);
            document.querySelector(`#${id}`).innerHTML = '';
            var html="";
            for (var i=0; i<7; i++) {
                s_date = GbFunc.GetNextDaySimple(s_date);

                html += `
                <div class="progress mb-3 mt-1 mr-2" style="height: 28px;">
                    <div font-weight-bold">
                        <font size="3">${s_date}</font>
                    </div>
                    <div class="ml-2 progress-bar gray accent-2" role="progressbar" style="width: 50%" aria-valuenow="50"
                        aria-valuemin="0" aria-valuemax="100">
                        <div class="plan1 font-weight-bold mr-1">
                            ${res[i]}
                        </div>
                    </div>
                </div>
                `;
            }
            document.querySelector(`#${id}`).innerHTML = html;




        },
        error: function(jqXHR, error) {
            //console.log(`error2`, error);
            //console.log(`status2`, jqXHR.status);
            return null;
        }
    });
}
function getDailyTwoData1(result, length, dateField, valueField)
{
    var data = [];
    for (var i=0; i<length; i++) {    
        data[i] = 0;
    }
    for (var i=0; i<result.length; i++) {
        var str = result[i][dateField].split('-');
        var index = parseInt(str[2])-1;
        data[index] = parseInt(result[i][valueField]);
    }
    return data;
}
function drawProductFaultyChart(id, type, date, p_name) {
    var strDate = date.split('-');
    var year = strDate[0];
    var month = strDate[1];
    var labels = getDailyLabels(parseInt(year), parseInt(month));
    var start = `${year}-${month}-01`;
    var end = `${year}-${month}-${String(labels[labels.length-1]).padStart(2, '0')}`;
    $.ajax({
        url: "/api/productFaultyDaily/",
        type: "GET",
        data: {start: start, end: end, p_name: p_name},
        success: function(result, textStatus, jqXHR){
            var ctx = document.getElementById(id).getContext('2d');
            var data1 = getDailyTwoData1(result, labels.length, 'ProductionDate', 'Sum');
            var data2 = getDailyTwoData1(result, labels.length, 'ProductionDate', 'SumFirst');
            var faultyRatio = [];
            for (var i=0; i<labels.length; i++) {
                if(data2[i] == 0) {
                    faultyRatio[i] = 0;    
                }
                else if(data1[i] == 0) {
                    faultyRatio[i] = 0;
                }
                else {
                    
                    faultyRatio[i] = ((data2[i]-data1[i])/data2[i]*100).toFixed(2);
                }
               // //console.log(i, data2[i], data1[i], faultyRatio[i]);
            }
            var myChart = new Chart(ctx, {
                plugins: [ChartDataLabels],
                type: type,
                data: {
                    labels: labels,
                    datasets: [{
                        label: `${year}년 ${month}월 생산 대비 불량률(%)`,
                        data: faultyRatio,
                        fillColor: "rgba(125,22,108,0.2)",
                        strokeColor: "rgba(125,22,108,1)",
                        pointColor: "rgba(125,22,108,1)",
                        pointStrokeColor: "#000",
                        pointHighlightFill: "#000",
                        pointHighlightStroke: "rgba(125,22,108,1)",
                        backgroundColor: "rgba(125, 22, 108, 0.6)",
                        borderColor: "rgba(0, 0, 0, 0)",
                    },
                    ]
                },
                options: {
                    maintainAspectRatio: true, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero:true
                            }
                        }]
                    },
                    scaleFontColor: "#fff",
                    // font family
                    defaultFontFamily: "'Roboto', sans-serif",
                    // background grid lines color
                    scaleGridLineColor: "rgba(255,255,255,.1)",
                    plugins: {
                        datalabels: {
                          formatter: (value, ctxv) => {
                              if(value != 0) {
                                return value;
                              }
                              else {
                                  return '';
                              }
                          },
                          color: 'black',
                          labels: {
                            title: {
                              font: {
                                size: '14'
                              }
                            }
                          }
                        }
                    },
                },
            });            
        },
        error: function(jqXHR, error) {
            //console.log(`error2`, error);
            //console.log(`status2`, jqXHR.status);
        }
    });
}
function drawProductTrackingChart1(id, type, date) {
    $.ajax({
        url: "/api/productTracking1/",
        type: "GET",
        data: {date: date},
        success: function(result, textStatus, jqXHR){
            var ctx = document.getElementById(id).getContext('2d');
            var myChart = new Chart(ctx, {
                plugins: [ChartDataLabels],
                type: type,
                data: {
                    labels: ['생산시작', '1차세척','중간검사','2차세척','최종검사','제품포장'],
                    datasets: [{
                        label: `어버트먼트 생산 현황`,
                        data: result.data1,
                        fillColor: "rgba(125,22,108,0.2)",
                        strokeColor: "rgba(125,22,108,1)",
                        pointColor: "rgba(125,22,108,1)",
                        pointStrokeColor: "#000",
                        pointHighlightFill: "#000",
                        pointHighlightStroke: "rgba(125,22,108,1)",
                        backgroundColor: "rgba(125, 22, 108, 0.6)",
                        borderColor: "rgba(0, 0, 0, 0)",
                    },
                    ]
                },
                options: {
                    maintainAspectRatio: true, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero:true
                            }
                        }]
                    },
                    scaleFontColor: "#fff",
                    // font family
                    defaultFontFamily: "'Roboto', sans-serif",
                    // background grid lines color
                    scaleGridLineColor: "rgba(255,255,255,.1)",
                    plugins: {
                        datalabels: {
                          formatter: (value, ctxv) => {
                              if(value != 0) {
                                return value;
                              }
                              else {
                                  return '';
                              }
                          },
                          color: 'black',
                          labels: {
                            title: {
                              font: {
                                size: '14'
                              }
                            }
                          }
                        }
                    },
                },
            });            
        },
        error: function(jqXHR, error) {
            //console.log(`error2`, error);
            //console.log(`status2`, jqXHR.status);
        }
    });
}
function drawProductTrackingChart2(id, type, date) {
    $.ajax({
        url: "/api/productTracking2/",
        type: "GET",
        data: {date: date},
        success: function(result, textStatus, jqXHR){
            var ctx = document.getElementById(id).getContext('2d');
            var myChart = new Chart(ctx, {
                plugins: [ChartDataLabels],
                type: type,
                data: {
                    labels: ['생산시작', '1차세척','표면처리','2차세척','3차세척','제품포장','공정위탁','최종검사'],
                    datasets: [{
                        label: `픽스쳐 생산 현황`,
                        data: result.data1,
                        fillColor: "rgba(125,22,108,0.2)",
                        strokeColor: "rgba(125,22,108,1)",
                        pointColor: "rgba(125,22,108,1)",
                        pointStrokeColor: "#000",
                        pointHighlightFill: "#000",
                        pointHighlightStroke: "rgba(125,22,108,1)",
                        backgroundColor: "rgba(125, 22, 108, 0.6)",
                        borderColor: "rgba(0, 0, 0, 0)",
                    },
                    ]
                },
                options: {
                    maintainAspectRatio: true, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero:true
                            }
                        }]
                    },
                    scaleFontColor: "#fff",
                    // font family
                    defaultFontFamily: "'Roboto', sans-serif",
                    // background grid lines color
                    scaleGridLineColor: "rgba(255,255,255,.1)",
                    plugins: {
                        datalabels: {
                          formatter: (value, ctxv) => {
                              if(value != 0) {
                                return value;
                              }
                              else {
                                  return '';
                              }
                          },
                          color: 'black',
                          labels: {
                            title: {
                              font: {
                                size: '14'
                              }
                            }
                          }
                        }
                    },
                },
            });            
        },
        error: function(jqXHR, error) {
            //console.log(`error2`, error);
            //console.log(`status2`, jqXHR.status);
        }
    });
}