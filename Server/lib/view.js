var fs = require('fs');
var cheerio = require('cheerio');
var async = require('async');
var cache = require('memory-cache');
var logger = require(__lib + '/logger');
var opDB = require(__lib + '/database').opDB;

var attribs = {
    option: '',
    labeloption: '',
    label: '',
    id: '',
    style: '',
    value: '',
    data: '',
    display: '',
    date: {
        rangedate: 'yes',
        positionx: 'left',
        positiony: 'down',
        format: 'YYYY-MM-DD',
    },
    content: '',
    icon: '',
    labelwidth: '',
    caption: '',
    all: '',
    selected: '',
};

var convertAttribs = {
    label: function (obj, attr, callback) {
        attr.label += obj;
        return attr;
    },
    class: function (obj, attr, callback) {
        attr.option += `class="${obj}" `;
        return attr;
    },
    labelclass: function (obj, attr, callback) {
        attr.labeloption += `class="${obj}" `;
        return attr;
    },
    id: function (obj, attr, callback) {
        attr.id = obj;
        return attr;
    },
    text: function (obj, attr, callback) {
        attr.value += obj;
        return attr;
    },
    value: function (obj, attr, callback) {
        attr.value += obj;
        return attr;
    },
    labelwidth: function (obj, attr, callback) {
        attr.labelwidth += `width:${obj}px; `;
        return attr;
    },
    width: function (obj, attr, callback) {
        attr.style += `width:${obj}px; `;
        return attr;
    },
    height: function (obj, attr, callback) {
        attr.style += `height:${obj}px; `;
        return attr;
    },
    background: function (obj, attr, callback) {
        attr.style += `background-color:${obj}; `;
        return attr;
    },
    foreground: function (obj, attr, callback) {
        attr.style += `color:${obj};`;
        return attr;
    },
    visibility: function (obj, attr, callback) {
        if (obj === 'no') {
            attr.display += `display:none`;
            return attr;
        }
        else {
            return attr;
        }
    },
    name: function (obj, attr, callback) {
        attr.option += `name="${obj}" `;
        return attr;
    },
    texttype: function (obj, attr, callback) {
        attr.option += `type="${obj}" `;
        return attr;
    },
    textstyle: function (obj, attr, callback) {
        return attr;
    },
    dismiss: function (obj, attr, callback) {
        attr.option += `data-dismiss="${obj}"`;
    },
    focusinevent: function (obj, attr, callback) {
        attr.option += `onfocusin="${obj}" `;
        return attr;
    },
    focusoutevent: function (obj, attr, callback) {
        attr.option += `onfocusout="${obj}" `;
        return attr;
    },
    valuechangeevent: function (obj, attr, callback) {
        attr.option += `onchange="${obj}" `;
        return attr;
    },
    clickevent: function (obj, attr, callback) {
        attr.option += `onclick="${obj}" `;
        return attr;
    },
    changeevent: function (obj, attr, callback) {
        attr.option += `onchange="${obj}" `;
        return attr;
    },
    mouseoverevent: function (obj, attr, callback) {
        attr.option += `onmouseover="${obj}" `;
        return attr;
    },
    mouseoutevent: function (obj, attr, callback) {
        attr.option += `onmouseout="${obj}" `;
        return attr;
    },
    hint: function (obj, attr, callback) {
        attr.option += `placeholder="${obj}" `;
        return attr;
    },
    scroll: function (obj, attr, callback) {
        if (obj == "yes") {
            attr.style += `overflow:scroll; `;
            return attr;
        }
        else {
            return attr;
        }
    },
    textstyle: function (obj, attr, callback) {
        var style = obj.split("|");
        for (var i = 0; i < style.length; i++) {
            if (style[i] === 'italic') {
                attr.style += `font-style:italic; `;
            }
            else if (style[i] === 'bold') {
                attr.style += `font-weight:bold; `;
            }
            else if (style[i] === 'underline') {
                attr.style += `text-decoration: underline; `;
            }
            else { continue; }
        }
        return attr;
    },
    maxlength: function (obj, attr, callback) {
        attr.option += `maxlength="${obj}" `;
        return attr;
    },
    allinit: function (obj, attr, callback) {
        return attr;
    },
    initvalue: function (obj, attr, callback) {
        return attr;
    },
    src: function (obj, attr, callback) {
        attr.value += obj;
        return attr;
    },
    data: function (obj, attr, callback) {
        attr.data += obj;
        return attr;
    },
    rangedate: function (obj, attr, callback) {
        attr.date.rangedate = obj;
        return attr;
    },
    format: function (obj, attr, callback) {
        attr.date.format = obj;
        return attr;
    },
    positionx: function (obj, attr, callback) {
        attr.date.positionx = obj;
        return attr;
    },
    positiony: function (obj, attr, callback) {
        attr.date.positiony = obj;
        return attr;
    },
    itemnum: function (obj, attr, callback) {
        attr.itemNum = parseInt(obj);

        return attr;
    },
    selectednum: function (obj, attr, callback) {
        attr.selectedNum = parseInt(obj);
        return attr;
    },
    addsearch: function (obj, attr, callback) {
        attr.option += `data-live-search="${obj}" `;
        return attr;
    },
    addyn: function (obj, attr, callback) {
        if (obj == "yes") {
            attr.addyn = true;
            return attr;
        }
        else {
            attr.addyn = false;
            return attr;
        }
        return attr;
    },
    action: function (obj, attr, callback) {
        attr.action = obj;
        return attr;
    },
    target: function (obj, attr, callback) {
        attr.target = obj;
        return attr;
    },
    mode: function (obj, attr, callback) {
        attr.mode = obj;
        return attr;
    },
    icon: function (obj, attr, callback) {
        attr.icon = obj;
        return attr;
    },
    caption: function (obj, attr, callback) {
        attr.caption = obj;
        return attr;
    },
    all: function (obj, attr, callback) {
        if (obj == "yes") {
            attr.all = true;
            return attr;
        }
        else {
            attr.all = false;
            return attr;
        }
    },
    searchable: function (obj, attr, callback) {
        attr.option += `searchable="${obj}" `;
        return attr;
    },
    zindex: function (obj, attr, callback) {
        attr.style += `z-index:${obj};`;
        return attr;
    },
    splaceholder: function (obj, attr, callback) {
        attr.option += `data-placeholder="${obj}" `;
        return attr;
    },
    bpad: function (obj, attr, callback) {
        attr.style += `padding-bottom:${obj};`;
        return attr;
    },
    bmargin: function (obj, attr, callback) {
        attr.style += `margin-bottom:${obj};`;
        return attr;
    },
    view: function (obj, attr, callback) {
        attr.option += ` ${obj}`;
        return attr;
    },
};
var _parseTag = function (obj, tag, callback) {
    var attr = Object.create(attribs);
    var key = Object.keys(tag.attribs);
    var resp;
    var err;
    for (var i = 0; i < key.length; i++) {
        if (convertAttribs[key[i]] === undefined) {
            err += ` ${key[i]} `;
            continue;
        }
        resp = convertAttribs[key[i]](tag.attribs[key[i]], attr);
    }
    callback(err, resp);
};
var text = {
    build: function (obj, attr, tag, callback) {
        if (attr.style.indexOf('width') == -1) {
            attr.style += 'width: 130px;'
        }
        if (attr.style.indexOf('height') == -1) {
            attr.style += 'height: 25px;'
        }
        if (attr.labelwidth.indexOf('width') == -1) {
            attr.labelwidth += 'width: 80px;'
        }

        var html = "";
        html += `<div id="div_${attr.id}" style="${attr.display}">\n`;
        html += `\t<label style="display: inline-block; ${attr.labelwidth} text-align: center;">${attr.label}</label>\n`;
        html += `\t<input style="${attr.style}" id="${attr.id}" ${attr.option} value="${attr.value}" readonly></input>\n`;
        html += `</div>\n`;
        callback(null, html);
    }
};
var edittext = {
    build: function (obj, attr, tag, callback) {
        if (attr.style.indexOf('width') == -1) {

        }
        if (attr.style.indexOf('height') == -1) {

        }
        if (attr.labelwidth.indexOf('width') == -1) {

        }

        var html = "";
        html += `<div id="div_${attr.id}" style="${attr.display}" class="md-form  md-outline">\n`;
        html += `<input type="text" autocomplete="off"  class="form-control" id="${attr.id}" style="${attr.style}" ${attr.option} value="${attr.value}"></input>\n`;
        html += `<label for="${attr.id}"class="active">${attr.label}</label>\n`;
        html += `</div>\n`;
        callback(null, html);
    }
};
var editor = {
    build: function (obj, attr, tag, callback) {
        var html = "";
        html += `<div id="div_${attr.id}" style="${attr.display}" class="md-form">\n`;
        html += `<label for="${attr.id}" class="active">${attr.label}</label>\n`;
        html += `<textarea class="summernote form-control" id="${attr.id}" style="${attr.style}" ${attr.option} value="${attr.value}"></textarea>\n`;
        html += `</div>\n`;
        callback(null, html);
    }
};
var edittextarea = {
    build: function (obj, attr, tag, callback) {
        if (attr.style.indexOf('width') == -1) {
            attr.style += 'width: 200px;'
        }
        if (attr.style.indexOf('height') == -1) {
            attr.style += 'height: 50px;'
        }
        if (attr.labelwidth.indexOf('width') == -1) {
            attr.labelwidth += 'width: 150px;'
        }

        var html = "";
        html += `<div id="div_${attr.id}" style="${attr.display}">\n`;
        html += `<div class="input-group mb-3"> <div class="padding">\n`;
        html += `<div class="input-group-prepend"style = "background-color: #47748b;"> \n`;
        html += `<div class="input-group-text" style = "background-color: #47748b;"> \n`;
        html += `<label style="display: inline-block; color : #ffffff; ${attr.labelwidth} text-align: center; ">${attr.label}</label>\n`;
        html += `</div>\n`;
        html += `</div>\n`
        html += `<textarea class="form-control" style="${attr.style}" id="${attr.id}" ${attr.option}>`;
        html += `${attr.value}`;
        html += `</textarea>\n`;
        html += `</div>\n`;
        html += `</div>\n`;
        callback(null, html);
    }
};
var combo = {
    build: function (obj, attr, tag, callback) {
        if (attr.style.indexOf('width') == -1) {

        }
        if (attr.style.indexOf('height') == -1) {

        }
        if (attr.labelwidth.indexOf('width') == -1) {

        }

        var html = "";
        html += `<select style="${attr.style}" id="${attr.id}" ${attr.option} data-size="${attr.itemNum}">\n`;
        if (attr.data) {
            if (attr.mode === 'find') {
                opDB.getField(attr.data, function (err, result) {
                    html += `\t<option value='all'>All</option>\n`;
                    for (var i = 0; i < result.length; i++) {
                        html += `\t<option value=${result[i].Field}>${result[i].Field}</option>\n`;
                    }
                    html += `</select>\n`;
                    html += `<label for="${attr.id}" class="active">${attr.label}</label>`;
                    callback(null, html);
                });
            }
            else {
                /*
                var comm = cache.get('commonDB');
                var data = comm[attr.data];
                */
                var data = cache.get(attr.data);
                if (attr.all) {
                    html += `\t<option value="all">전체</option>\n`;
                }
                for (var i = 0; i < data.length; i++) {
                    html += `\t<option value="${data[i].ID}">${data[i].VALUE}</option>\n`;
                }
                if (attr.addyn) {
                    html += `\t<option value="addValue">+</option>\n`;
                }
                html += `</select>\n`;
                html += `<label>${attr.label}</label>`
                callback(null, html);
            }
        }
        else {
            html += `</select>\n`;
            html += `<label>${attr.label}</label>`
            callback(null, html);
        }
    }
};
var searchcombo = {
    build: function (obj, attr, tag, callback) {
        var html = "";
        html += `<select id="${attr.id}" ${attr.option} style="${attr.style}">\n`;
        var data = cache.get(attr.data);
        if (attr.all) {
            html += `\t<option value="all">전체</option>\n`;
        }
        for (var i = 0; i < data.length; i++) {
            html += `\t<option value="${data[i].ID}">${data[i].VALUE}</option>\n`;
        }
        html += `</select>\n`;
        html += `<label for="${attr.id}" class="active">${attr.label}</label>\n`;
        callback(null, html);
    }
};
var button = {
    build: function (obj, attr, tag, callback) {
        if (attr.style.indexOf('width') == -1) {
        }
        if (attr.style.indexOf('height') == -1) {
        }
        if (attr.labelwidth.indexOf('width') == -1) {
        }

        var html = "";
        html += `<div id="div_${attr.id}">\n`;
        html += `<button type="button" ${attr.option} style="${attr.style}" id="${attr.id}" ><i class="${attr.icon}"></i>&nbsp${attr.value}</button>\n`;
        html += `</div>\n`;
        callback(null, html);
    }
};
var checkgroup = {
    build: function (obj, attr, tag, callback) {
        var html = "";
        html += `<div id="div_${attr.id}" ${attr.option} style="${attr.display}">\n`;
        callback(null, html);
    }
};
var check = {
    build: function (obj, attr, tag, callback) {
        if (attr.style.indexOf('width') == -1) {
        }
        if (attr.style.indexOf('height') == -1) {
        }
        if (attr.labelwidth.indexOf('width') == -1) {
        }

        var html = "";
        html += `<div id="div_${attr.id}" style="${attr.style}" class="md-form">\n`;
        html += `\t<input type="checkbox" class="form-check-input" id="${attr.id}" style="${attr.style}" ${attr.option} value="${attr.value}"></input>\n`;
        html += `\t<label for="${attr.id}" class="form-check-label">${attr.label}</label>\n`;
        html += `</div>\n`;
        callback(null, html);
    }
};
var radio = {
    build: function (obj, attr, tag, callback) {
        if (attr.style.indexOf('width') == -1) {
            attr.style += 'width: 30px;'
        }
        if (attr.style.indexOf('height') == -1) {
            attr.style += 'height: 15px;'
        }
        if (attr.labelwidth.indexOf('width') == -1) {
            attr.labelwidth += 'width: 80px;'
        }

        var html = "";
        html += `<div id="div_${attr.id}" style="${attr.display}">\n`;
        html += `<label style="display: inline-block; ${attr.labelwidth} text-align: center;">${attr.label}</label>\n`;
        if (attr.data) {
            var comm = cache.get('commonDB');
            var data = comm[attr.data];
            for (var i = 0; i < data.length; i++) {
                html += `<input type="radio" style="${attr.style}" id="${attr.id}" ${attr.option} value="${data[i].ID}">${data[i].VALUE}</input>\n`;
            }
        }
        html += `</div>\n`;
        callback(null, html);
    }
};
var imageview = {
    build: function (obj, attr, tag, callback) {
        if (attr.style.indexOf('width') == -1) {
            attr.style += 'width: 100px;'
        }
        if (attr.style.indexOf('height') == -1) {
            attr.style += 'height: 100px;'
        }

        var html = "";
        html += `<div id="div_${attr.id}" style="${attr.display}" class="box">\n`;
        html += `<figure>`
        html += `<img id="${attr.id}" src="${attr.value}" ${attr.option} style="${attr.style}" class="img"></img>\n`;
        html += `<figcaption>${attr.caption}</figcaption>`
        html += `</figure>`
        html += `</div>\n`;
        callback(null, html);
    }
};
var date = {
    build: function (obj, attr, tag, callback) {
        if (attr.style.indexOf('width') == -1) {
        }
        if (attr.style.indexOf('height') == -1) {
        }
        if (attr.labelwidth.indexOf('width') == -1) {
        }
        var html = "";
        html += `<div id="div_${attr.id}" style="${attr.display}" class="md-form">\n`;
        html += `\t<input autocomplete = "off" type="text" class="form-control" id="${attr.id}" style="${attr.style}" ${attr.option} value="${attr.value}"></input>\n`;
        html += `\t<label for="${attr.id}" class="active">${attr.label}</label>\n`;
        html += `</div>\n`;
        html += `<script>$('input[id="${attr.id}"]').daterangepicker(getDateFormat(\n
            \t"${attr.date.positionx}","${attr.date.positiony}",
            \t"${attr.date.format}", "${attr.date.rangedate}"));\t</script>\n`;
        callback(null, html);
    }
};
var tab = {
    build: function (obj, attr, tag, callback) {
        var html = "";
        html += `<div id="div_${attr.id}"\n`;
        html += `<nav>`
        html += `<div class="nav nav-tabs" id="${attr.id}" role="tablist">\n`;
        for (var i = 0; i < attr.itemNum; i++) {
            var selected = "false";
            var active = "";
            if (i == attr.selectedNum) { selected = "true"; active = "active"; }
            html += `<a class="nav-item nav-link ${active}" style="${attr.style}" id="tab_${attr.id}_${i}" 
                     ${attr.option} data-toggle="tab" href="#tabcontent_${attr.id}_${i}" role="tab" 
                      aria-controls="tabcontent_${attr.id}_${i}" aria-selected="${selected}">Tab${i}</a>\n`;
        }
        html += `</div></nav>\n`;
        html += `<div class="tab-content" id="nav-tabContent">\n`;
        for (var i = 0; i < attr.itemNum; i++) {
            var active = "";
            var show = "";
            if (i == attr.selectedNum) { active = "active"; show = "show"; }
            html += `<div class="tab-pane fade ${show} ${active}" id="tabcontent_${attr.id}_${i}"
                     role="tabpanel" aria-labelledby="tab_${attr.id}_${i}">${i}${i}${i}${i}${i}${i}</div>\n`;
        }
        html += `</div></div>`
        callback(null, html);
    }
};

var panel = {
    build: function (obj, attr, tag, callback) {
        var html = "";
        html += `<div id="div_${attr.id}" ${attr.option} style="${attr.style}">\n`;
        callback(null, html);
    }
};
var onoff = {
    build: function (obj, attr, tag, callback) {
        var html = "";
        html += `<div id="div_${attr.id}" style="${attr.style}">\n`;
        html += `<input type="checkbox" id="${attr.id}" ${attr.option} checked data-toggle="toggle">\n`;
        html += `</div>\n`;
        callback(null, html);
    }
};

var find = {
    build: function (obj, attr, tag, callback) {
        var html = "";
        html += `<div id="div_${attr.id}" style="${attr.style}">\n`;
        html += `<input type="text" id="${attr.id}" placeholder="Search..">\n`;
        html += `<input type="button" id="${attr.id}_button" value="검색" ${attr.option}>\n`;
        html += `</div>\n`;
        callback(null, html);
    }
};
var modal = {
    build: function (obj, attr, tag, callback) {
        var labelId = `${attr.id}Label`;
        var formID = `${attr.id}Form`;
        var html = "";
        html += `<div class="modal fade" id="${attr.id}" style="${attr.style}" tabindex="-1" role="dialog" aria-labelledby="${labelId}" aria-hidden="true">\n`;
        html += `\t<div class="modal-dialog" role="document">\n`;
        html += `\t\t<div class="modal-content">\n`;
        html += `\t\t\t<div class="modal-header">\n`;
        html += `\t\t\t\t<h5 class="modal-title" id="${labelId}"></h5>\n`;
        html += `\t\t\t\t<button type="button" class="close" data-dismiss="modal" aria-label="Close">\n`;
        html += `\t\t\t\t\t<span aria-hidden="true">&times;</span>\n`;
        html += `\t\t\t\t</button>\n`;
        html += `\t\t\t</div>\n`;
        html += `\t\t\t<div class="modal-body">\n`;
        callback(null, html);
    }
};

var grid = {
    build: function (obj, attr, tag, callback) {
        var html = "";
        html += `<div id="div_${attr.id}" style="${attr.display}" class="table-wrapper-scroll-y my-custom-scrollbar">\n`;
        html += `<table id="${attr.id}" class="table table-sm" style="${attr.style} width:100%" ${attr.option}>\n`;
        //html += `<table id="${attr.id}" class="table fixed-bottom" style="${attr.style}" ${attr.option}>\n`;
        html += `<thead>\n`
        html += `<tr id="tr_${attr.id}">\n`
        html += `</tr>\n`
        html += `</thead>\n`
        html += `</table>\n`
        html += `</div>\n`;
        callback(null, html);
    }
};

var objMap = {
    "text": text,
    "edittext": edittext,
    "editor": editor,
    "edittextarea": edittextarea,
    "combo": combo,
    "button": button,
    "checkgroup": checkgroup,
    "check": check,
    "radio": radio,
    "imageview": imageview,
    "date": date,
    "tab": tab,
    "panel": panel,
    "onoff": onoff,
    "find": find,
    "modal": modal,
    "grid": grid,
    "searchcombo": searchcombo,
};
exports.render = function (filePath, options, callback) {
    var Tags = [];
    var result_html = "";
    fs.readFile(filePath, 'utf-8', function (err, content) {
        if (err) {
            callback(err, null);
        }
        else {
            var $ = cheerio.load(content, { recognizeSelfClosing: true });
            $('*').each(function (i, tag) {
                if (objMap[tag.name] !== undefined) {
                    Tags.push(tag);
                }
            });
            async.eachSeries(Tags, function (tag, outCb) {
                var objVal = objMap[tag.name];
                if (!objVal) {
                    outCb();
                }
                else {
                    var obj = Object.create(objVal);
                    _parseTag(obj, tag, function (err, result) {
                        if (err) {
                            logger.error(`${err} in ${tag.attribs.type}`);
                        }
                        else {
                            obj.build(obj, result, tag, function (err, html) {
                                if (tag.name === 'gb-panel' || tag.name === 'gb-checkgroup' || tag.name === 'gb-modal' || tag.name === 'gb-form') {
                                    for (var i = 0; i < $(tag).children().length; i++) {
                                        var child = $(tag).children()[i];
                                        var subobjVal = objMap[child.name];
                                        if (subobjVal) {
                                            var subobj = Object.create(subobjVal);
                                            _parseTag(null, child, function (err, subresult) {
                                                subobj.build(subobj, subresult, tag, function (err, subhtml) {
                                                    html += subhtml;
                                                });
                                            });
                                        }
                                    }
                                    html += `</div>`;
                                    $(tag).replaceWith(html);
                                }
                                else {
                                    $(tag).replaceWith(html);
                                }
                                outCb();
                            })
                        }
                    })
                }
            },
                function (err) {
                    callback(null, $.html({ decodeEntities: false }));
                });
        }
    })
}
