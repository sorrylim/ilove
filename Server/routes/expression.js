var express = require('express');
var router = express.Router();

var db_expression = require('../public/SQL/expression_sql')()
var db_user = require('../public/SQL/user_sql')()

router.post('/save/showprofile', function(req, res, next) {
    var user_id = req.body.user_id
    var partner_id = req.body.partner_id
    var expression_date = req.body.expression_date
    var candy_count = req.body.candy_count

    db_expression.insert_expression_data(user_id, partner_id, "show", expression_date, function(err, result) {
        if(err) console.log(err)
        else {
            db_user.decrease_candy(user_id, candy_count, function(err, result) {
                if(err) console.log(err)
                else {
                    var object = new Object()
                    object.result = "success"
                    res.send(object)
                }
            })
        }
    })
})

router.post('/get/count', function(req, res, next){
    var user_id = req.body.user_id

    var send_like = 0;
    var receive_like = 0;
    var send_meet = 0;
    var receive_meet = 0;
    var each_like = 0;
    var each_meet = 0;

    db_expression.get_count_expression(user_id, function(err, result){
        if(err) console.log(err)
        else {
            for(var i in result) {
                if(result[i]["user_id"] == user_id && result[i]["expression_type"] == "like") {
                    send_like++;
                }
                else if(result[i]["partner_id"] == user_id && result[i]["expression_type"] == "like") {
                    receive_like++;
                }
                else if(result[i]["user_id"] == user_id && result[i]["expression_type"] == "meet") {
                    send_meet++;
                }
                else if(result[i]["partner_id"] == user_id && result[i]["expression_type"] == "meet") {
                    receive_meet++;
                }
            }

            db_expression.get_count_each(user_id, function (err, result) {
                if (err) console.log(err)
                else {
                    for (var i in result) {
                        if (result[i]["user_id"] == user_id || result[i]["partner_id"] == user_id && result[i]["expression_type"] == "like") {
                            each_like++;
                        }
                        else if (result[i]["user_id"] == user_id || result[i]["partner_id"] == user_id && result[i]["expression_type"] == "meet") {
                            each_meet++;
                        }
                    }

                    var object = new Object();
                    object.send_like = send_like;
                    object.receive_like = receive_like;
                    object.send_meet = send_meet;
                    object.receive_meet = receive_meet;
                    object.each_like = each_like;
                    object.each_meet = each_meet;
                    res.send(obejct);
                }
            })
        }
    })
})

router.post('/get/send/user', function(req, res, next) {
    var user_id = req.body[0].user_id
    var expression_type = req.body[0].expression_type

    db_expression.get_send_user(user_id, expression_type, function(err, user) {
        if(err) console.log(err)
        else {
            db_expression.get_expression_data(user_id, function(err, expression) {
                if(err) console.log(err)
                else {
                    for(var i in user) {
                        user[i]["like"] = 0;
                        user[i]["meet"] = 0;
                        for(var j in expression) {
                            if(user[i]["user_id"] == expression[j]["partner_id"] && expression[j]["expression_type"] == "like") {
                                user[i]["like"] = 1;
                            }
                              else if(user[i]["user_id"] == expression[j]["partner_id"] && expression[j]["expression_type"] == "meet") {
                                user[i]["meet"] = 1;
                            }
                        }
                    }
                    res.send(user);
                    console.log(user);
                }
            })
        }
    })
})

router.post('/get/receive/user', function(req, res, next) {
    var user_id = req.body[0].user_id
    var expression_type = req.body[0].expression_type

    db_expression.get_receive_user(user_id, expression_type, function(err, user) {
        if(err) console.log(err)
        else {
            db_expression.get_expression_data(user_id, function(err, expression) {
                if(err) console.log(err)
                else {
                    for(var i in user) {
                        user[i]["like"] = 0;
                        user[i]["meet"] = 0;
                        for(var j in expression) {
                            if(user[i]["user_id"] == expression[j]["partner_id"] && expression[j]["expression_type"] == "like") {
                                user[i]["like"] = 1;
                            }
                              else if(user[i]["user_id"] == expression[j]["partner_id"] && expression[j]["expression_type"] == "meet") {
                                user[i]["meet"] = 1;
                            }
                        }
                    }
                    res.send(user);
                    console.log(user);
                }
            })
        }
    })
})

router.post('/get/each1/user', function(req, res, next) {
    var user_id = req.body[0].user_id
    var expression_type = req.body[0].expression_type

    db_expression.get_each1_user(user_id, expression_type, function(err, user) {
        if(err) console.log(err)
        else {
            db_expression.get_expression_data(user_id, function(err, expression) {
                if(err) console.log(err)
                else {
                    for(var i in user) {
                        user[i]["like"] = 0;
                        user[i]["meet"] = 0;
                        for(var j in expression) {
                            if(user[i]["user_id"] == expression[j]["partner_id"] && expression[j]["expression_type"] == "like") {
                                user[i]["like"] = 1;
                            }
                              else if(user[i]["user_id"] == expression[j]["partner_id"] && expression[j]["expression_type"] == "meet") {
                                user[i]["meet"] = 1;
                            }
                        }
                    }
                    res.send(user);
                    console.log(user);
                }
            })
        }
    })
})

router.post('/get/each2/user', function(req, res, next) {
    var user_id = req.body[0].user_id
    var expression_type = req.body[0].expression_type

    db_expression.get_each2_user(user_id, expression_type, function(err, user) {
        if(err) console.log(err)
        else {
            db_expression.get_expression_data(user_id, function(err, expression) {
                if(err) console.log(err)
                else {
                    for(var i in user) {
                        user[i]["like"] = 0;
                        user[i]["meet"] = 0;
                        for(var j in expression) {
                            if(user[i]["user_id"] == expression[j]["partner_id"] && expression[j]["expression_type"] == "like") {
                                user[i]["like"] = 1;
                            }
                              else if(user[i]["user_id"] == expression[j]["partner_id"] && expression[j]["expression_type"] == "meet") {
                                user[i]["meet"] = 1;
                            }
                        }
                    }
                    res.send(user);
                    console.log(user);
                }
            })
        }
    })
})


router.post('/insert/history', function(req, res, next) {
    var user_id = req.body.user_id
    var partner_id = req.body.partner_id
    var visit_type = req.body.visit_type
    var visit_date = req.body.visit_date

    db_expression.check_visit_data(user_id, partner_id, visit_type, function(err, result) {
        if(err) console.log(err)
        else {
            if(result[0]["count"] == 1) {
                db_expression.update_visit_data(user_id, partner_id, visit_type, visit_date, function(err, result){
                    if(err) console.log(err)
                    else {
                        var object = new Object();
                        object.result = "success";
                        res.send(object);
                        console.log("방문기록 업데이트 완료")
                    }
                })
            }
            else if(result[0]["count"] == 0) {
                db_expression.insert_visit_data(user_id, partner_id, visit_type, visit_date, function(err, result) {
                    if(err) console.log(err)
                    else {
                        var object = new Object();
                        object.result = "success";
                        res.send(object);
                        console.log("방문기록 삽입 완료")
                    }
                })
            }
        }
    })
})

router.post('/get/count/view', function(req, res, next) {
    var user_id = req.body.user_id
    var profile_count = 0;
    var story_count = 0;

    db_expression.get_count_view(user_id, function(err, result) {
        if(err) console.log(err)
        else {
            for(var i in result) {
                if(result[i]["visit_type"] == "profile") {
                    profile_count = result[i]["count(visit_type)"];
                }
                else if(result[i]["visit_type"] == "story") {
                    story_count = result[i]["count(visit_type)"];
                }
            }
            var obj = new Object();
            obj.profile = profile_count;
            obj.story = story_count
            res.send(obj);
        }
    })
})

router.post('/get/visit/user', function(req, res, next) {
    var user_id = req.body[0].user_id
    var visit_type = req.body[0].visit_type

    db_expression.get_visit_user(user_id, visit_type, function(err, user) {
        if(err) console.log(err)
        else {
            db_expression.get_expression_data(user_id, function(err, expression) {
                if(err) console.log(err)
                else {
                    for(var i in user) {
                        user[i]["like"] = 0;
                        user[i]["meet"] = 0;
                        for(var j in expression) {
                            if(user[i]["user_id"] == expression[j]["partner_id"] && expression[j]["expression_type"] == "like") {
                                user[i]["like"] = 1;
                            }
                              else if(user[i]["user_id"] == expression[j]["partner_id"] && expression[j]["expression_type"] == "meet") {
                                user[i]["meet"] = 1;
                            }
                        }
                    }
                    res.send(user);
                    console.log(user);
                }
            })
        }
    })
})

router.post('/insert', function(req, res, next) {
    var user_id=req.body.user_id
    var partner_id=req.body.partner_id
    var expression_type=req.body.expression_type
    var expression_date=req.body.expression_date

    db_expression.check_expression_data(partner_id, user_id, expression_type, function(err, expression) {
        if(err) console.log(err)
        else {
            if(expression[0]["count"] == 1) {
                db_expression.insert_expression_data(user_id, partner_id, expression_type, expression_date, function(err, result) {
                    if(err) console.log(err)
                    else {
                        db_expression.insert_expressioneach_data(user_id, partner_id, expression_type, expression_date, function(err, result) {
                            if(err) console.log(err)
                            else {
                                var object = new Object()
                                object.result = "success"
                                res.send(object)
                            }
                        })
                    }
                })
            }
            else if(expression[0]["count"] == 0) {
                db_expression.insert_expression_data(user_id, partner_id, expression_type, function(err, result){
                    if(err) console.log(err)
                    else {
                        var object = new Object();
                        object.result = "success";
                        res.send(object);
                    }
                })
            }
        }
    })
})

router.post('/delete', function(req, res, next) {
    var user_id=req.body.user_id
    var partner_id=req.body.partner_id
    var expression_type=req.body.expression_type

    db_expression.check_expression_data(partner_id, user_id, expression_type, function(err, expression) {
        if(err) console.log(err)
        else {
            if(expression[0]["count"] == 1) {
                db_expression.delete_expressioneach_data(user_id, partner_id, expression_type, function(err, result){
                    if(err) console.log(err)
                    else {
                        db_expression.delete_expression_data(user_id, partner_id, expression_type, function(err, result){
                            if(err) console.log(err)
                            else {
                                var object = new Object()
                                object.result = "success"
                                res.send(object)
                            }
                        })
                    }
                })
            }
            else if(expression[0]["count"] == 0) {
                db_expression.delete_expression_data(user_id, partner_id, expression_type, function(err, result) {
                    if(err) console.log(err)
                    else {
                        var object = new Object()
                        object.result = "success"
                        res.send(object)
                    }
                })
            }
        }
    })
})


module.exports = router;
