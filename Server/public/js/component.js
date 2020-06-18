
var today = new Date();
var subTotal_flag = false;
var addCount = 0;

function formatDate(date, connection){
    var d = new Date(date),
        day = '' + d.getDate(),
        month = '' + (d.getMonth() + 1),
        year = d.getFullYear(),
        nowTime = moment(d).format("YYYY-MM-DD HH:mm:ss");
    if (day.length < 2)
        day = '0' + day;

    if (month.length < 2)
        month = '0' + month;

    if(connection === '' || connection === undefined){  
        return year + '년 ' + month + '월 ' + day + '일';
    }else{
        return [year, month, day].join(connection);
    }
}

var GbFunc = {
    SetValue: function (id, text) {
        $('#' + id).val(text).addClass('active').siblings().addClass('active');
    },
    GetValue: function (id) {
        return $('#' + id).val();
    },
    Length: function(id){
        return $('#' + id).val().length;
    },
    IsNull: function (id) { 
        if($('#' + id).val() === null || $('#' + id).val() === undefined){
            return true;
        } else {
            return false;
        }
    },
    IsBlank: function (id) { 
        if($('#' + id).val() === ''){
            return true;
        } else {
            return false;
        }
    },
    SetStartValue: function (id, date) { 
        var dateArray = $('#' + id).val().split('~');
        $('#' + id).val(date + ' ~' + dateArray[1]);             
    },
    GetStartValue: function (id) {
        var dateArray = $('#' + id).val().split('~');
        var startdate = dateArray[0].substring(0, dateArray[0].length-1);
        return startdate;
    },
    SetEndValue: function (id, date) { 
        var dateArray = $('#' + id).val().split('~');
        $('#' + id).val(dateArray[0] + '~ ' + date);
    },
    GetEndValue: function (id) { 
        var dateArray = $('#' + id).val().split('~');
        var enddate = dateArray[1].substring(1, dateArray[1].length);
        return enddate;
    },
    GetToday: function(connection){
        return formatDate(today, connection);
    },
    GetTime: function(){
        var nowTime = Date();
        return moment(nowTime).format("YYYY-MM-DD HH:mm:ss");
    },
    GetYesterday: function(connection){
        var yesterday = new Date();
        yesterday.setDate(today.getDate() - 1);
        return formatDate(yesterday, connection);
    },
    GetTomorrow: function(connection){
        var tomorrow = new Date();
        tomorrow.setDate(today.getDate() + 1);
        return formatDate(tomorrow, connection);
    },
    GetFirstWeekDay: function(date){
        return moment(date).startOf('week').format('YYYY-MM-DD');
    },    
    GetLastWeekDay: function(date){
        return moment(date).endOf('week').format('YYYY-MM-DD');
    },        
    GetPreviousDay: function(date){
        return moment(date).subtract(1, 'days').format('YYYY-MM-DD');
    },
    GetNextDay: function(date){
        return moment(date).add(1, 'days').format('YYYY-MM-DD');
    },    
    GetNextDaySimple: function(date){
        return moment(date).add(1, 'days').format('MM.DD');
    },    
    GetPreviousWeek: function(date){
        return moment(date).subtract(7, 'days').format('YYYY-MM-DD');
    },
    GetNextWeek: function(date){
        return moment(date).add(7, 'days').format('YYYY-MM-DD');
    },        
    GetPreviousMonth: function(date){
        return moment(date).subtract(1, 'months').format('YYYY-MM-DD');
    },
    GetNextMonth: function(date){
        return moment(date).add(1, 'months').format('YYYY-MM-DD');
    },    
    GetPreviousYear: function(date){
        return moment(date).subtract(1, 'years').format('YYYY-MM-DD');
    },
    GetSpecificMonth: function(date, diff){
        return moment(date).subtract(diff, 'days').format('YYYY-MM-DD');
    },        
    GetSpecificMonth: function(date, diff){
        return moment(date).subtract(diff, 'months').format('YYYY-MM-DD');
    },        
    GetSpecificYear: function(date, diff){
        return moment(date).subtract(diff, 'years').format('YYYY-MM-DD');
    },    
    GetNextYear: function(date){
        return moment(date).add(1, 'years').format('YYYY-MM-DD');
    },        
    GetCalDate: function(date, day, connection){
        var caldate = new Date();
        var d = new Date(date);
        caldate.setDate(d.getDate() + (day * 1));
        return formatDate(caldate, connection);
    },
    SetComboDisabled: function(id, bool){
        $('[data-activates="select-options-' + id + '"]').attr('disabled', bool);
    },
    SetComboValue: function(id, value){
        //  $('#' + id).val(value);

        //var text =  $('#' + id + ' option[value="' + value +'"]').text();
        var index =  $('#' + id + ' option[value="' + value +'"]').index();
        //$(`select#${id}`).find("[selected]").attr("selected", false);
        //$(`select#${id}`).find(`option:contains(${text})`).attr("selected", true);
        $(`#select-options-${id} li:eq(${index})`).trigger('click');
    },
    SetComboValueByText: function(id,text){
        var index = $('#' + id + ' option:contains(' + text + ')').index();
        if(index >= 0) 
            $(`#select-options-${id} li:eq(${index})`).trigger('click');
        else $('#'+id).val('Not Data');
    },
    SetComboValueByIndex: function(id, index){
        // $('#' + id + ' option:eq(' + index + ')').prop('selected', 'selected');

        // var value = $('#' + id + ' option:eq(' + index + ')').text();
        // $(`#select-options-${id} li:contains("${value}")`).trigger('click');
        $(`#select-options-${id} li:eq(${index})`).trigger('click');
    },
    SetValueByIndex: function(id, index){
        $('#' + id + ' option:eq(' + index + ')').prop('selected', 'selected');
        //$('#' + id + ' option:eq(' + index + ')').trigger('change');
    },
    GetValueByIndex: function(id, index){
        return $('#' + id + ' option:eq(' + index + ')').val();
    },
    SetValueByText: function(id, text){
        $('#' + id + ' option:contains(' + text + ')').prop('selected', 'selected');
    },
    GetValueByText: function(id, text){
        return $('#' + id + ' option:contains(' + text + ')').val();
    },
    SetText: function(id, text){
        $('#' + id + ' option:selected').text(text);
        $('#' + id).val(text).prop("selected", true);
    },
    GetText: function(id){
        return $('#' + id + ' option:selected').text();
    },
    SetTextByValue: function(id, value, text){
        $('#' + id + ' option[value="' + value +'"]').text(text);
    },
    GetTextByValue: function(id, value){
        return $('#' + id + ' option[value="' + value +'"]').text();
    },
    SetTextByIndex: function(id, index, text){
        $('#' + id + ' option:eq(' + index + ')').text(text);
    },
    GetTextByIndex: function(id, index){
        return $('#' + id + ' option:eq(' + index + ')').text();
    },
    GetIndex: function(id){
        return $('#' + id + ' option:selected').index();
    },
    GetIndexByValue: function(id, value){
        return $('#' + id + ' option[value="' + value +'"]').index();
    },
    GetIndexByText: function(id, text){
        return $('#' + id + ' option:contains(' + text + ')').index();
    },
    Clear: function(id){
        $('#' + id + ' option').remove();
    },
    /* //TODO!! DB연동 필요!
    Remove: function(id, index){
        $('#' + id + ' option:eq(' + index + ')').remove();
    },
    AddOption: function(id, value, text){
        if(add_flag == true){
            $('#' + id + ' option:eq(' + $('#' + id + ' option[value="addValue"]').index() + ')').before('<option value=' + value + '>' + text + '</option>');
        }
    },
    ReplaceOption: function(id, index, value, text){
        $('#' + id + ' option:eq(' + index + ')').replaceWith('<option value=' + value + '>' + text + '</option>');
    },
    */
    SetBackground: function (id, color) { 
        $('#' + id).css('background-color', color);
    },
    SetForeground: function (id, color) { 
        $('#' + id).css('color', color);
    },
    SetVisibility: function (id, bool) { 
        if (bool === false) {
            $('#div_' + id).css('visibility', 'hidden');
        } else if (bool === true) {
            $('#div_' + id).css('visibility', 'visible');
        }
    },
    ToggleVisibility: function(id){ 
        if( $('#div_' + id).css('visibility') == 'hidden'){
            $('#div_' + id).css('visibility', 'visible');
        }else{
            $('#div_' + id).css('visibility', 'hidden');
        }
    },
    SetView: function (id, bool) {
        if (bool === false) {
            $('#div_' + id).css('display', 'none');
        } else if (bool === true) {
            $('#div_' + id).css('display', 'block');
        }
    },
    ToggleView: function(id){
        if( $('#div_' + id).css('display') == 'block'){
            $('#div_' + id).css('display', 'none');
        }else{
            $('#div_' + id).css('display', 'block');
        }
    },
    SetDisabled: function (id, bool) {
        $('#' + id).attr('disabled', bool);
    },
    ToggleDisabled: function(id){ 
        if( $('#' + id).attr('disabled') == 'disabled'){
            $('#' + id).attr('disabled', false);
        }else{
            $('#' + id).attr('disabled', true);
        }
    },
    SetShowTab: function (id) { 
        $('#' + id).tab('show');
    },
    GetSelect: function (id) {
        //return $('input:checkbox[id=' + id + ']').is(':checked');
        if($('input:checkbox[id=' + id + ']').is(':checked')){
            return 'Y';
        }else{
            return 'N';
        }
    },
    SetSelect: function (id, bool) {
        if(bool === 'N'){
            bool = false;
        }else if(bool === 'Y'){
            bool = true;
        }
        
        $('input:checkbox[id=' + id + ']').prop('checked', bool);
    },
    SetSelectAll: function (id, bool) {
        $('input:checkbox[name=' + id + ']').prop('checked', bool);
    },
    ToggleSelect: function (id, target) {
        if($('input:checkbox[name=' + id + ']').is(":checked") == true) {
            $('input:checkbox[name=' + target + ']').prop('checked', true);
        }
        else {
            $('input:checkbox[name=' + target + ']').prop('checked', false);
        }
        //$('input:checkbox[name=' + id + ']').prop('checked', bool);
    },
    GetCheck: function (id, bool) {
        //var result = [{id:'', value:''}];
        var result = [];
        $('input:checkbox[name=' + id + ']:checked').each(function(index) {
            result.push({id:$(this).attr('id'), value: $(this).val()});
        });
        return result;
    },
    GetIndexByValue: function(id, value){
        return $('#' + id + ' option[value="' + value +'"]').index();
    },
    AddOption: function(target, id, value, data, callback){
        $.ajax({
            url: "/api/db/"+data+"", 
            type: "POST",
            data: {id: id, value: value},
            cache: false,
            acync: true,
            success: function(result) {
                //console.log(`success!, ${result}`);
                alert(`추가 성공`);
                callback(null);
            },
            error: function(error) {
                console.log(`error`, error);
                callback(error);
            }
        });
        $(`#${target} option:last`).before(`<option value='${id}' data-subtext='| ${id}'> ${value} </option>`);
    },
    Search: function (id, table, source) {
        value = $('#' + id).val();
        field = $('#' + source).val();
        $.ajax({
            url: "/api/db/search/"+table+"", 
            type: "GET",
            data: {mode: 'search', field: field, id: id, value: value},
            success: function(result) {
                var length = result.length;
                if (length <= 0) {
                    console.log(`Not Found`);
                }
                for(var i=0; i<result.length; i++) {
                    console.log(result[i].ID, result[i].VALUE);
                }
            },
            error: function(error) {
                console.log(`error`, error);
            }
        });
    },
    Barcode: function(){
        var d = new Date(),
        day = '' + d.getDate(),
        month = '' + (d.getMonth() + 1),
        year = '' + d.getFullYear();
        second = '' + d.getSeconds();

        var ranNum = '' + Math.floor(Math.random()*(1000)) + 1;
        
        if (day.length < 2)
            day = '0' + day;

        if (month.length < 2)
            month = '0' + month;

        if (second.length < 2)
            second = '0' + second;

        for(var i=0; i<4; i++){
            if (ranNum.length < 4)
                ranNum = '0' + ranNum;
            else
                break;
        }

        return second + ranNum + year.substring(2,4) + month + day;
    },
    ShowNotification: function(mode, str) {
        toastr.options = {
            "closeButton": true,
            "positionClass": "md-toast-top-center",
            "timeOut": 2000,
        }
        if (mode == 'success') {
            toastr.success(str);
        }
        else if (mode == 'info') {
            toastr.info(str);
        }
        else {
            toastr.error(str);
        }
    },
    EmptyValidate: function(id) {
        var form = document.getElementById(id);
        var inputs = form.getElementsByTagName("input");
        for (var i=0; i<inputs.length; i++) {
            //console.log(inputs[i].required);
        }
    },
    SetFocus: function(id) {
        $(`#${id}`).focus();
    },
    SendFile: function (file, editor, welEditable) {
        var data = new FormData();
        data.append('file', file);
        //console.log(data);
        $.ajax({
            url: "/api/files",
            type: "POST",
            data: data,
            ache: false,
            contentType: false,
            enctype: 'multipart/form-data',
            processData: false,
            success: function(url) {
                //console.log(url);
                var image = $('<img width="100%">').attr('src', `/uploads/${url}`);
                $('.summernote').summernote("insertNode", image[0]);
                //console.log(image[0]);
                //$(editor).summernote("insertNode", url);
                //$('#imageBoard > ul').append('<li><img src="'+url+'" width="480" height="auto"/></li>');
            },
            error: function(error) {
                console.log(`error`, error);
            }
        });
    },
    DBtoXML: function (obj) {
        var xml = '<?xml version="1.0"?>\n';
        xml += "<DATAS>\n";
        for (var prop in obj) {
            xml += "<DATA>\n";
            //xml += obj[prop] instanceof Array ? '' : "<" + prop + ">";
            if (obj[prop] instanceof Array) {
                for (var array in obj[prop]) {
                    xml += "<" + prop + ">";
                    xml += this.XMLwrite(new Object(obj[prop][array]));
                    xml += "</" + prop + ">\n";
                }
            } else if (typeof obj[prop] == "object") {
                xml += this.XMLwrite(new Object(obj[prop]));
            } else {
                xml += obj[prop];
            }
            //xml += obj[prop] instanceof Array ? '' : "</" + prop + ">\n";
            xml += "</DATA>\n";
        }
        xml += "</DATAS>\n";
        var xml = xml.replace(/<\/?[0-9]{1,}>/g, '');
        return xml
    },
    XMLwrite: function (obj) {
        var xml = '';
        for (var prop in obj) {
            xml += "<" + prop + ">";
            xml += obj[prop];
            xml += "</" + prop + ">\n";
        }
        var xml = xml.replace(/<\/?[0-9]{1,}>/g, '');
        return xml
    },        
}

var GbGrid = {
    Create : function (id, height, field, table, cond, order, param, button) { 
		var names = new Array;
		var fields = new Array;
		var widths = new Array;
		var searchables = new Array;
		var orderables = new Array;
        var visibles = new Array;
        var buttons = [];
        if (button != 'N') {
            buttons = [
                { extend: 'copy', text: '복사'},
                { extend: 'excel', text: '엑셀' },
                { extend: 'print', text: '출력'  }
            ]
        }
		for(var i=0; i<param.length; i++){
			if(param[i].name != undefined){
				names[i] = param[i].name;
			}else{
				alert('name error');
				return;
			}

			if(param[i].field != undefined){
				fields[i] = param[i].field;
			}else{
				alert('field error');
				return;
			}

			if(param[i].width != undefined){
				widths[i] = param[i].width;
			}else{
				widths[i] = '';
			}

			if(param[i].searchable != undefined){
				searchables[i] = param[i].searchable;
			}else{
				searchables[i] = true;
			}

			if(param[i].orderable != undefined){
				orderables[i] = param[i].orderable;
			}else{
				orderables[i] = true;
			}

			if(param[i].visible != undefined){
				visibles[i] = param[i].visible;
			}else{
				visibles[i] = true;
			}
		}

		var html = '<tr id="tr_"'+id+'>\n<th></th>\n';

		for(var i=0; i<param.length; i++){
			html += `<th>${names[i]}</th>\n`;	
		}
		html += `</tr>`;
		  
		$('#tr_' + id).replaceWith(html);

		var colObj = new Object;
		var colArr = new Array;

		colObj.data = 'index';
		colArr.push(colObj);

		for(var i=0; i<param.length; i++){
			var colObj = new Object;
			colObj.data = fields[i];
			colArr.push(colObj); 
		}

		var colDefObj = new Object;
		var colDefArr = new Array;

        colDefObj.targets = 0;
        colDefObj.visible = true;
		colDefObj.width = '10px';
        colDefObj.searchable = false;
        colDefObj.orderable = false;
		colDefArr.push(colDefObj);

		for(var i=0; i<param.length; i++){
			var colDefObj = new Object;
            colDefObj.targets = i+1;
			colDefObj.searchable = searchables[i];
			colDefObj.orderable = orderables[i];
            colDefObj.visible = visibles[i];
            colDefObj.render = ( data, type, row ) => {
                if (data == null) data = ""; 
                return type === 'display' && data.length > 15 ?
                    data.substr( 0, 150 ) :
                    data;
                
            }
			colDefArr.push(colDefObj); 
		}

		myData = {};

        myData.fields = field;
        myData.table = table;
        myData.cond = cond;
        myData.order = order;

		var table = $('#' + id).DataTable({
            order: [],
            ajax: {
                url: '/api/search' ,
                type: 'GET',
                data:function(d){
					return $.extend(d, myData);
				},
                dataSrc: "data",
            },
            scrollY: height  + 'vh',
            columnDefs: colDefArr,
            columns: colArr,
            buttons: buttons,
            scrollX: true,
            select: true,
            /* select : {
                style: 'multi',
            }, */
			pageLength: -1, //한 페이지에 기본으로 보여줄 항목 수를 뜻한다.
			bPaginate: false, //페이징 처리를 할 것인지를 정한다. "false"로 주면 "pageLength"와는 관계 없이 전체 데이터를 출력한다.
			bLengthChange: false, //한 페이지에 보여줄 항목 수를 변경할 것인지를 정한다. "true"로 주면 그리드에 리스트박스를 추가한다.
			lengthMenu : [ [ 15, 30, 50, -1 ], [ 15, 30, 50, "All" ] ], // "bLengthChange" 리스트 항목을 구성할 옵션들을 정해준다.
            bAutoWidth: false, //자동 컬럼 폭을 계산하여 반영한다.
			ordering: true, //항목들에 대한 정렬을 사용할 것인가를 결정한다.
			searching: false, //글로벌 searching 기능을 제공하는데, 타이핑한 캐릭터 별로 검색이 자동 적용되기 때문에 상당히 강력하다.
			processing: true, //"true"로 주면 값을 가져오는 등의 처리 상황에서 대기가 발생할 때, "processing"인디케이터를 보여준다.
			serverSide: false, //그리드 내에서 이루어지는 모든 상황들(검색, 페이징, 정렬 등)에 대한 처리를 서버측에서 수행할 것인가를 뜻한다.
            dom: 'ftBp',
        });

        table.buttons().container()
            .appendTo( '#menu-icon'  );
        $('.dt-button').addClass('btn btn-md');

        //처음 인덱스 값은 검색이나 정렬되더라도 유지
		table.on('order search', function(){
			table.column(0, {search:'applied', order:'applied'}).nodes().each(function(cell, i){
				cell.innerHTML = i+1;
			});
        }).draw();

        /* $('#' + id + ' tbody').on('click', 'tr', function () {
            if ( $(this).hasClass('selected') ) {
                $(this).removeClass('selected');
            }
            else {
                table.$('tr.selected').removeClass('selected');
                 $(this).addClass('selected');
            }
        }); */

        // $('#'+id+'_paginate').css({
        //     'display':'none'
        // })
    },

    GetValue : function(id, index, field){
        var value = $('#' + id).DataTable().row(index).data();

        if(value[field] != undefined){
            return value[field];
        }else{
            return -1; //err
        }
    },

    SetValue : function(id, index, field, value){
        var table = $('#' + id).DataTable();

        var changeValue = table.row(index).data();

        changeValue[field] = value;

        table.row(index).data(changeValue).draw();
    },

    GetSelectedIndex : function(id){
        var table = $('#' + id).DataTable();

        var length = table.rows('.selected').data().length;

        if(length == 1){
            return table.row('.selected').index();
        }else{
            return -1;
        }
    },
    GetSelectedMultiIndex : function(id){
        var table = $('#' + id).DataTable();

        var length = table.rows('.selected').data().length;

        if(length > 0){
            return length;
        }else{
            return -1;
        }
    },
    Length : function(id){
        return $('#' + id).DataTable().data().count();
    },

    Select : function(id, index){
		$('#' + id).DataTable().row(index).select();
    },

    SetIndex : function(id, field, value){
        var table = $('#' + id).DataTable();
        
        var lastIndex = 0;

        for(var i=0; i<table.data().count(); i++){
            if(table.row(i).data()[field] == value){
                lastIndex = i;
                break;
            }
        }
        table.row(lastIndex).select();

        var $row = $(table.row(lastIndex - 7).node());
        if ($row.length) {
            $('.dataTables_scrollBody').animate({ scrollTop: $row.offset().top }, 1000);
        }
        //table.row(lastIndex).scrollTo();
    },

    Redraw : function(id, field, table, cond, order){
        myData.fields = field;
        myData.table = table;
        myData.cond = cond;
        myData.order = order;

        $('#' + id).DataTable().ajax.url('/api/search').load(); //url 변경되어야 함.
        
        $('div.customMenu').css({
            'visibility' : 'hidden'
        });
    },

    AddRow: function (id, param) {
        var table = $('#' + id).DataTable();
        var data = new Object();

        data['index'] = '1';

        for(var i=0; i<param.length; i++){
            data[param[i].field] = param[i].value;
        }

        table.row.add(data).draw();

        var rowCount = table.data().length - 1;
        var insertedRow = table.row(rowCount).data();
        var tempRow;
        var sortIndex = rowCount - addCount;

        for (var i = rowCount; i > sortIndex; i--) {
            tempRow = table.row(i - 1).data();
            table.row(i).data(tempRow);
            table.row(i - 1).data(insertedRow);
        }

        tempRow = table.row(0).data();
        table.row(sortIndex).data(tempRow);
        table.row(0).data(insertedRow);

        addCount++;

        //인덱스 다시 정렬
        table.column(0, { search: 'applied', order: 'applied' }).nodes().each(function (cell, i) {
            cell.innerinnerHTML = i + 1;
        });

        table.$('tr.selected').removeClass('selected'); 
		table.rows(0).nodes().to$().addClass('selected');
    },

    RemoveRow: function(id){
        $('#' + id).DataTable().rows('.selected').remove().draw();
    },
    
    Clear: function(id){
        $('#' + id).DataTable().clear().draw();
    },

    Order: function(id, columnIndex, way){
        $('#' + id).DataTable().order([columnIndex, way]).draw();
    },

	SubTotal: function(id, field){
        var table = $('#' + id).DataTable();

        var subTotal = 0;

        for(var i=0; i<table.data().count(); i++){
            var value = $('#' + id).DataTable().row(i).data();
            var temp = 0;

            if(value[field] != undefined){
                temp = value[field];
            }else{
                temp = -1; //err
            }

            temp = temp * 1;

            if(temp == -1){
                temp = 0;
            }  else if(typeof temp === 'string'){
                temp = 0;
            }

            subTotal += temp;
        }

        if(subTotal_flag == false){
            subTotal_flag = true;
            var data = table.row(0).data();
            
            for(key in data){
                if(data.hasOwnProperty(key)){
                  data[key] = '';
                }
            }
            
            data[field] = subTotal;

            table.row.add(data).draw();

            data['index'] = table.data().length;
            table.order(0,'asc');
            table.rows(table.data().length-1).nodes().to$().addClass('subTotal');
        }else{
            var subTotalIdx = table.data().length-1;
            var data = table.row(subTotalIdx).data();

            data[field] = subTotal;

            table.row(subTotalIdx).data(data).draw();
        }
    },
    
    BackgroundColor: function(id, index, color){
        $('#' + id).DataTable().row(index).nodes().to$().css('background-color', color);
    }, 

    ForegroundColor: function(id, index, color){
        $('#' + id).DataTable().row(index).nodes().to$().css('color', color);
    },
    /*
    OnAdd: function(id, callback){
        $('#' + id + '_contextmenu span[name = add]').on('click', callback);
    },
    */
    OnDoubleClick: function(id, callback){
        $('#' + id + ' tbody').on('dblclick', 'tr', callback);
    },

    OnSelect: function(id, callback){
        $('#' + id).DataTable().on('select', callback);
    },
    OnMultiSelect : function(id){
        var data = $('#' + id).DataTable().rows('.selected').data();
        return data;
    },
    OnDeSelect: function(id, callback){
        $('#' + id).DataTable().on('deselect', callback);
    },

    OnSearch: function(id, callback){
        $('#' + id).on('search.dt', callback).DataTable();
    },

    OnOrder: function(id, callback){
        $('#' + id).on('order.dt', callback).DataTable();
    },

    OnPage: function(id, callback){
        $('#' + id).on('page.dt', callback).DataTable();
    },

    OnDraw: function(id, callback){
        $('#' + id).DataTable().on('draw', callback);
    },

    OnPreDraw: function(id, callback){
        $('#' + id).DataTable().on('preDraw', callback);
    },
}