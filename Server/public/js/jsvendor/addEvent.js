var eventModal = $('#eventModal');

var modalTitle = $('.modal-title');
var editAllDay = $('#edit-allDay');
var editTitle = $('#edit-title');
var editStart = $('#edit-start');
var editEnd = $('#edit-end');
var editType = $('#edit-type');
var editColor = $('#edit-color');
var editDesc = $('#edit-desc');

var addBtnContainer = $('.modalBtnContainer-addEvent');
var modifyBtnContainer = $('.modalBtnContainer-modifyEvent');


/* ****************
 *  새로운 일정 생성
 * ************** */
var newEvent = function (start, end, eventType) {

    $("#contextMenu").hide(); //메뉴 숨김

    modalTitle.html('새로운 일정');
    editStart.val(start);
    editEnd.val(end);
    editType.val(eventType).prop("selected", true);

    addBtnContainer.show();
    modifyBtnContainer.hide();
    eventModal.modal('show');

    /******** 임시 RAMDON ID - 실제 DB 연동시 삭제 **********/
    
    /******** 임시 RAMDON ID - 실제 DB 연동시 삭제 **********/
    /* const fs = require('fs');
 */
    //새로운 일정 저장버튼 클릭
    $('#save-event').unbind();
    $('#save-event').on('click', function () {
        var eventData = new Object();

        eventData.title = editTitle.val();
        eventData.start = editStart.val();
        eventData.end = editEnd.val();
        eventData.description = editDesc.val();
        eventData.type = editType.val();
        eventData.allDay = GbFunc.GetSelect('edit-allDay');
        if (eventData.start > eventData.end) {
            alert('끝나는 날짜가 앞설 수 없습니다.');
            return false;
        }

        if (eventData.title === '') {
            alert('일정명은 필수입니다.');
            return false;
        }
        var realEndDay;

        if (editAllDay.is(':checked')) {
            eventData.start = moment(eventData.start).format('YYYY-MM-DD');
            //render시 날짜표기수정
            eventData.end = moment(eventData.end).add(1, 'days').format('YYYY-MM-DD');
            //DB에 넣을때(선택)
            realEndDay = moment(eventDataend).format('YYYY-MM-DD');

            eventData.allDay = true;
        }
        $("#calendar").fullCalendar('renderEvent', eventData, true);
        eventModal.find('input, textarea').val('');
        editAllDay.prop('checked', false);
        eventModal.modal('hide');
        
        a2s_addDB('CALENDAR',JSON.stringify(eventData),function(res){
            if(res.affectedRows > 0) {
                alert('추가 완료');
            }
			else {
				alert('추가 실패');
			}
        })

      
       /*  var eventData = {
            _id: eventId,
            title: editTitle.val(),
            start: editStart.val(),
            end: editEnd.val(),
            description: editDesc.val(),
            type: editType.val(),
            backgroundColor: editColor.val(),
            textColor: '#ffffff',
            allDay: false
        };
 */
        

       
        /* var eventData = JSON.stringify(eventData); */
        
        //새로운 일정 저장
       /*  $.ajax({
            type: "get",
            url: "",
            dataType: "text",
            contentType: "application/json",
           
           
           data : {
            _id: eventData._id,
            title: eventData.title,
            start: eventData.start,
            end: eventData.end,
            description: eventData.description,
            type: eventData.type,
            backgroundColor:eventData.backgroundColor,
            textColor: eventData.textColor,
            allDay: eventData.allDay,
           },
           success: function (response) {
                
                var fixedDate = response.map(function (array) {
                    console.log(array);
                    if (array.allDay && array.start !== array.end) {
                      
                      array.end = moment(array.end).add(1, 'days');
                    }
                    return array;
                  })
                  fixedDate += JSON.stringify(eventData);
                  console.log(fixedDate);
                  callback(fixedDate);
            },
            
        }) */
    });
};