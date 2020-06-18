/* ****************
 *  일정 편집
 * ************** */
var editEvent = function (event, element, view) {

    $('.popover.fade.top').remove();
    $(element).popover("hide");

    if (event.allDay === 'Y') {
        editAllDay.prop('checked', true);
    } else {
        editAllDay.prop('checked', false);
    }

    if (event.end === null) {
        event.end = event.start;
    }

    if (event.allDay === 'Y' && event.end !== event.start) {
        editEnd.val(moment(event.end).subtract(1, 'days').format('YYYY-MM-DD HH:mm'))
    } else {
        editEnd.val(event.end.format('YYYY-MM-DD HH:mm'));
    }

    modalTitle.html('일정 수정');
    editTitle.val(event.title);
    editStart.val(event.start.format('YYYY-MM-DD HH:mm'));
    editType.val(event.type);
    editDesc.val(event.description);
    editColor.val(event.backgroundColor).css('color', event.backgroundColor);

    addBtnContainer.hide();
    modifyBtnContainer.show();
    eventModal.modal('show');

    //업데이트 버튼 클릭시
    $('#updateEvent').unbind();
    $('#updateEvent').on('click', function () {

        if (editStart.val() > editEnd.val()) {
            alert('끝나는 날짜가 앞설 수 없습니다.');
            return false;
        }

        if (editTitle.val() === '') {
            alert('일정명은 필수입니다.')
            return false;
        }

        var statusAllDay;
        var startDate;
        var endDate;
        var displayDate;

        if (editAllDay.is(':checked')) {
            statusAllDay = "Y";
            startDate = moment(editStart.val()).format('YYYY-MM-DD');
            endDate = moment(editEnd.val()).format('YYYY-MM-DD');
            displayDate = moment(editEnd.val()).add(1, 'days').format('YYYY-MM-DD');
        } else {
            statusAllDay = 'N';
            startDate = editStart.val();
            endDate = editEnd.val();
            displayDate = endDate;
        }

        eventModal.modal('hide');
        
        event.allDay = statusAllDay;
        event.title = editTitle.val();
        event.start = startDate;
        event.end = displayDate;
        event.type = editType.val();
        /* event.backgroundColor = editColor.val(); */
        event.description = editDesc.val();

        $("#calendar").fullCalendar('updateEvent', event);
        var cond = "_id = "+event._id;
        console.log(event);
        var editevent = new Object();
        editevent._id = event._id;
        editevent.title = event.title;
        editevent.start = event.start;
        editevent.end = event.end;
        editevent.description = event.description;
        editevent.type = event.type;
        editevent.allDay = event.allDay;
        a2s_modifyDBByCond('CALENDAR',JSON.stringify(editevent), cond, function(res){
            console.log("s");
            if(res.affectedRows > 0){
                alert("성공");
                
            }
            else{
                alert("실패");
            }
        });
    });

    // 삭제버튼
    /* $('#deleteEvent').on('click', function () {
        $('#deleteEvent').unbind();
        $("#calendar").fullCalendar('removeEvents', [event._id]);
        eventModal.modal('hide');
        var cond = "_id = "+event._id;
        a2s_deleteDBByCon('CALENDAR',cond, function(res){
            if(res.affectedRows > 0)
            {
                alert("성공");
            }
            else{
                alert("실패");
            }
        });
        
    }); */
};