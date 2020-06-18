var available_printers = [];
var selected_category = null;
var default_printer = null;
var selected_printer = null;
var format_start = "^XA^LL200^FO80,50^A0N36,36^FD";
var format_end = "^FS^XZ";
var default_mode = true;
var printers_available = null;
var printer_list = [];
var target_printer = null;

function GetBarcodePrinter(id) 
{
    default_printer = null;
    selected_printer = null;
    available_printers = null;
    target_printer = null;
    BrowserPrint.getDefaultDevice('printer', function(printer) {
        var html = "";
        default_printer = printer;
        if ((printer != null) && (printer.connection != undefined)) {
            selected_printer = printer;
        }
        BrowserPrint.getLocalDevices(function(printers) {
            available_printers = printer;
            if (printers != undefined) {
                for (var i = 0; i < printers.length; i++) {
                    //console.log(printers[i].connection);
                    //console.log(printers[i].uid);
                    printer_list[i] = printers[i];
                    if (printers[i].connection == 'usb') {
                        printers[i].connection + ": " + printers[i].uid;
                        html += `<option value="${i}">${printers[i].uid}</option>`;
                        printers_available = true;
                    }
                }
            }
            $("#print").html(html);
            if (!printers_available) {
                logger.error(`출력 가능한 프린터가 없음`);
                document.querySelector(`#print`).innerHTML = "";
                return;
            } 
           // console.log(html);
        }, undefined, 'printer');
    });
}
function SendBarcode(data, idx) {
    target_printer = printer_list[idx];
    checkPrinterStatus(function(text) {
        if (text == "Ready to Print") {
            var barcode = `^XA^FO50,50^BCN,100,Y,N,N^FD${data}^XZ`;
            target_printer.send(barcode, undefined, errorCallback);
        } 
        else {
            logger.error(`프린트 할 수 없습니다. ${text}`)
        }        
    });
}
function SendStatus(idx) {
    target_printer = printer_list[idx];
    checkPrinterStatus(function(text) {
        printStatus(text);
    });
}
function printStatus(text) {
    if (text == "Ready to Print") {
        $("#statusicon").attr('class','fas fa-circle fa-lg green-text');
        $("#errstr").html("프린터가 정상 동작 중입니다.");
    }
    else {
        $("#statusicon").attr('class','fas fa-exclamation-circle fa-lg red-text');
        if (text == "Media Door Open") {
            $("#errstr").html("도어가 열려있습니다.");
        }
        else if (text == "Paper out") {
            $("#errstr").html("용지가 부족합니다.");
        }
        else if (text == "Ribbon Out") {
            $("#errstr").html("바코드 리본이 부족합니다.");
        }
        else {
            $("#errstr").html("비정상.");
        }
    }
}
function errorCallback(errorMessage){
	alert("Error: " + errorMessage);	
}
function checkPrinterStatus(finishedFunction) {
    target_printer.sendThenRead("~HQES",
    function(text) {
      var that = this;
      var statuses = new Array();
      var ok = false;
      var is_error = text.charAt(70);
      var media = text.charAt(88);
      var head = text.charAt(87);
      var pause = text.charAt(84);
      // check each flag that prevents printing
      if (is_error == '0') {
        ok = true;
        statuses.push("Ready to Print");
      }
      if (media == '1')
        statuses.push("Paper out");
      if (media == '2')
        statuses.push("Ribbon Out");
      if (media == '4')
        statuses.push("Media Door Open");
      if (media == '8')
        statuses.push("Cutter Fault");
      if (head == '1')
        statuses.push("Printhead Overheating");
      if (head == '2')
        statuses.push("Motor Overheating");
      if (head == '4')
        statuses.push("Printhead Fault");
      if (head == '8')
        statuses.push("Incorrect Printhead");
      if (pause == '1')
        statuses.push("Printer Paused");
      if ((!ok) && (statuses.Count == 0))
        statuses.push("Error: Unknown Error");
      finishedFunction(statuses.join());
    }, printerError);
};
function printerError(text) {
  logger.error("An error occurred while printing. Please try again." + text)
}