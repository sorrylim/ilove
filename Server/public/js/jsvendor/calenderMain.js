var draggedEventIsallDay;
var activeInactiveWeekends = true;

function getDisplayEventDate(event) {
  console.log("getDisplay");
  var displayEventDate;
  console.log(event);
  if (event.allDay == false) {
    var startTimeEventInfo = moment(event.start).format('HH:mm');
    var endTimeEventInfo = moment(event.end).format('HH:mm');
    displayEventDate = startTimeEventInfo + " - " + endTimeEventInfo;
  } else {
    displayEventDate = "하루종일";
  }

  return displayEventDate;
}

function filtering(event) {
  console.log("filtering");
  //var show_username = true;
  var show_type = true;

  /* var username = $('input:checkbox.filter:checked').map(function () {
    return $(this).val();
  }).get(); */
  var types = $('#type_filter').val();

  /* show_username = username.indexOf(event.username) >= 0; */

  if (types && types.length > 0) {
    if (types[0] == "all") {
      show_type = true;
    } else {
      show_type = types.indexOf(event.type) >= 0;
    }
  }
  console.log(show_type);
  console.log(event.start);
  console.log(event.end);
  /* return show_username && show_type; */
  return show_type;
}

/* function calDateWhenResize(event) {
  console.log("resize");
  var newDates = Object();

  if (event.allDay==true) {
    newDates.start = moment(event.start._d).format('YYYY-MM-DD');
    newDates.end = moment(event.end._d).subtract(1, 'days').format('YYYY-MM-DD');
  } else {
    newDates.start = moment(event.start._d).format('YYYY-MM-DD HH:mm');
    newDates.end = moment(event.end._d).format('YYYY-MM-DD HH:mm');
  }

  return newDates;
} */

/* function calDateWhenDragnDrop(event) {
  console.log("Drag&Drop Cal");
  newDates = new Object();

  if (event.allDay == true && event.end === null) {
    newDates.start = moment(event.start._d).format('YYYY-MM-DD');
    newDates.end = newDates.start;
  }

  else if (event.allDay == true && event.end !== null) {
    newDates.start = moment(event.start._d).format('YYYY-MM-DD');
    newDates.end = moment(event.end._d).subtract(1, 'days').format('YYYY-MM-DD');
  }

  else if (event.allDay == false) {
    console.log("Not AllDay");
    console.log(moment(event.start._d).format('YYYY-MM-DD HH:mm'))
    newDates.start = moment(event.start._d).format('YYYY-MM-DD HH:mm');
    newDates.end = moment(event.end._d).format('YYYY-MM-DD HH:mm');
  }
  console.log(newDates);
  return newDates;
} */
var colors = [
  '#00cc66',
  '#50bcdf',
  '#edacb1',
  '#83dcb7',
  '#f5f5dc',
]

var calendar = $('#calendar').fullCalendar({

  eventRender: function (event, element, view) {
    console.log("eventRender");
    //일정에 hover시 요약
    element.popover({
      title: $('<div />', {
        class: 'popoverTitleCalendar',
        text: event.title
      }),
      content: $('<div />', {
          class: 'popoverInfoCalendar'
        })/* .append('<p><strong>등록자:</strong> ' + event.username + '</p>') */
        .append('<p><strong>제품명:</strong> ' + event.title + '</p>')
        .append('<p><strong>시간:</strong> ' + moment(event.start._d).format('YYYY-MM-DD') + '</p>')
        .append('<div class="popoverDescCalendar"><strong>수량:</strong> ' + event.description + '</div>'),
      delay: {
        show: "800",
        hide: "50"
      },
      trigger: 'hover',
      placement: 'top',
      html: true,
      container: 'body'
    });

    return filtering(event);

  },

  

  header: {
    left : '',
    center: 'prev, title, next',
    right: 'month,listWeek'
  },
  views: {
    month: {
      columnFormat: 'dddd'
    },
    
    listWeek: {
      columnFormat: ''
    }
  },

  /* ****************
   *  일정 받아옴 
   * ************** */
  events: function (start, end, timezone, callback) {
    var success = false;
    console.log("get calendar");
    $.ajax({
      url: "/api/db/all",
      type: "GET",
      data: {table: 'PRODUCT_PLAN'},
      success: function(result, textStatus, jqXHR){
        var events = []; 
        console.log(`result1`, result);
        console.log(`status1`, jqXHR.status);
        var cnt = 0
        for(var i = 0; i < result.length; ++i)
        {
          events[i] = {
            _id : result[i].PlanCode,
            title : result[i].ProductName,
            description : result[i].PlanQuantity,
            start : moment(result[i].ProductDate).format('YYYY-MM-DD'),
            color : colors[cnt]
          }
          cnt += 1;
          if(cnt > 4) cnt = 0;
        }
        callback(events);
      },
      error: function(jqXHR, error) {
          console.log(`error1`, error);
          console.log(`status1`, jqXHR.status);
          callback(error);
      }
    });
    
  },

  eventAfterAllRender: function (view) {
    console.log("allrender");
    console.log(view);
    if (view.name == "month") {
      $(".fc-content").css('height', 'auto');
    }
  },

  //일정 리사이즈
  /* eventResize: function (event, delta, revertFunc, jsEvent, ui, view) {
    $('.popover.fade.top').remove();
    console.log("eventResize");
    
    var newDates = calDateWhenResize(event);
    var editevent = new Object();
        editevent._id = event._id;
        editevent.title = event.title;
        editevent.start = newDates.start;
        editevent.end = newDates.end;
        editevent.description = event.description;
        editevent.type = event.type;
        editevent.allDay = event.allDay;
    
    var cond  = "_id = "+editevent._id;
    a2s_modifyDBByCond('CALENDAR',JSON.stringify(editevent), cond, function(res){
      console.log("s");
      if(res.affectedRows > 0){
          alert("성공");
          
      }
      else{
          alert("실패");
      }
    });

  }, */

  /* eventDragStart: function (event, jsEvent, ui, view) {
    draggedEventIsallDay = event.allDay;
  }, */

  //일정 드래그앤드롭
/*   eventDrop: function (event, delta, revertFunc, jsEvent, ui, view) {
    $('.popover.fade.top').remove();
    console.log("drag & drop");
    //주,일 view일때 종일 <-> 시간 변경불가
    if (view.type === 'agendaWeek' || view.type === 'agendaDay') {
      if (draggedEventIsallDay !== event.allDay) {
        alert('드래그앤드롭으로 종일<->시간 변경은 불가합니다.');
        location.reload();
        return false;
      }
    }
    else{
      // 드랍시 수정된 날짜반영
      var newDates = calDateWhenDragnDrop(event);
      //드롭한 일정 업데이트
      var editevent = new Object();
      editevent._id = event._id;
      editevent.title = event.title;
      editevent.start = newDates.start;
      editevent.end = newDates.end;
      editevent.description = event.description;
      editevent.type = event.type;
      editevent.allDay = event.allDay;
    //리사이즈한 일정 업데이트
      var cond  = "_id = "+editevent._id;
      a2s_modifyDBByCond('CALENDAR',JSON.stringify(editevent), cond, function(res){
        console.log("s");
        if(res.affectedRows > 0){
            alert("성공");
            
        }
        else{
            alert("실패");
        }
      });
    } 
  }, */

  /* select: function (start, end, jsEvent, view) {
    console.log("select");
    $(".fc-body").unbind('click');
    $(".fc-body").on('click', 'td', function (e) {

      $("#contextMenu")
        .addClass("contextOpened")
        .css({
          display: "block",
          left: e.pageX,
          top: e.pageY
        });
      return false;
    });

    var today = moment();

    if (view.name == "month") {
      start.set({
        hours: today.hours(),
        minute: today.minutes()
      });
      start = moment(start).format('YYYY-MM-DD');
      end = moment(end).subtract(1, 'days');

      end.set({
        hours: today.hours() + 1,
        minute: today.minutes()
      });
      end = moment(end).format('YYYY-MM-DD');
    } else {
      start = moment(start).format('YYYY-MM-DD');
      end = moment(end).format('YYYY-MM-DD');
    }

    //날짜 클릭시 카테고리 선택메뉴
    /* var $contextMenu = $("#contextMenu");
    $contextMenu.on("click", "a", function (e) {
      e.preventDefault();


      if ($(this).data().role !== 'close') {
        newEvent(start, end, $(this).html());
      }

      $contextMenu.removeClass("contextOpened");
      $contextMenu.hide();
    }); 

    $('body').on('click', function () {
      $contextMenu.removeClass("contextOpened");
      $contextMenu.hide();
    });

  },  */

  //이벤트 클릭시 수정이벤트
  eventClick: function (event, jsEvent, view) {
    $('.popover.fade.top').remove();
    $(event.element).popover("hide");
    GbFunc.SetValue('PlanCode',String(event._id));
    GbFunc.SetValue('ProductName',String(event.title));
    GbFunc.SetValue('ProductDate',String(moment(event.start._d).format('YYYY-MM-DD')));
    GbFunc.SetValue('PlanQuantity',String(event.description));
    GbFunc.SetDisabled('ProductName',true);
    GbFunc.SetDisabled('ProductDate',true);
    GbFunc.SetDisabled('PlanQuantity',true);
    eventModal.modal('show');
  }, 

  locale: 'ko',
  timezone: "local",
  nextDayThreshold: "09:00:00",
  allDaySlot: true,
  displayEventTime: true,
  displayEventEnd: true,
  firstDay: 0, //월요일이 먼저 오게 하려면 1
  weekNumbers: false,
  selectable: true,
  weekNumberCalculation: "ISO",
  eventLimit: true,
  views: {
    month: {
      eventLimit: 12
    }
  },
  eventLimitClick: 'week', //popover
  navLinks: true,
  /* defaultDate: moment('2019-05'), //실제 사용시 삭제 */
  timeFormat: 'HH:mm',
  defaultTimedEventDuration: '01:00:00',
  editable: false,
  minTime: '00:00:00',
  maxTime: '24:00:00',
  slotLabelFormat: 'HH:mm',
  weekends: true,
  nowIndicator: true,
  dayPopoverFormat: 'MM/DD dddd',
  longPressDelay: 0,
  eventLongPressDelay: 0,
  selectLongPressDelay: 0,
  disableDragging: true,
  droppable : false // this allows things to be dropped onto the calendar
});