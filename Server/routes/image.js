var express = require('express');
var router = express.Router();

var db_image = require('../public/SQL/image_sql')()


router.post('/insert', function(req, res, next) {
    var user_id = req.body.user_id
    var image = req.body.image
    var image_usage = req.body.image_usage
    var image_content = req.body.image_content

    db_image.insert_image(user_id, image, image_usage, image_content, function(err, result) {
        if(err) console.log(err)
        else {
            var object = new Object()
            object.result = "success"
            res.send(object)
        }
    })
})

router.post('/get/story', function(req, res, next) {
    var user_id = req.body[0].user_id
    var image_usage = req.body[0].image_usage

    db_image.get_image(user_id, image_usage, function(err, result) {
        if(err) console.log(err)
        else {
            var array = new Array()
            for(var i in result) {
                var object = new Object()
                object.image_id = result[i]["image_id"];
                object.image = result[i]["image"].toString();

                array.push(object);
            }
            res.send(array);
        }
    })
})

router.post('/get/story/user', function(req, res, next) {
    var user_id = req.body.user_id
    var image_id = req.body.image_id

    db_image.increase_view(image_id, function(err, result) {
        if(err) console.log(err)
        else {
            db_image.get_story_data(image_id, function(err, user) {
                if(err) console.log(err)
                else {
                    db_image.check_expression_data(user_id, image_id, function(err, check) {
                        if(err) console.log(err)
                        else {
                            if(check[0].count == 1) {
                                user[0]["like"] = 1;
                                res.send(user[0]);
                                console.log(user[0]);
                            }
                            else if(check[0].count == 0) {
                                user[0]["like"] = 0;
                                res.send(user[0]);
                                console.log(user[0]);
                            }
                        }
                    })
                }
            })
        }
    })
})

router.post('/insert/expression', function(req, res, next) {
    var user_id = req.body.user_id
    var image_id = req.body.image_id
    var expression_date = req.body.expression_date

    db_image.insert_expression_data(user_id, image_id, expression_date, function(err, result) {
        if(err) console.log(err)
        else {
            db_image.increase_like(image_id, function(err, result) {
                if(err) console.log(err)
                else {
                    console.log("스토리 데이터 삽입완료");
                }
            })
        }
    })
})

router.post('/delete/expression', function(req, res, next) {
    var user_id = req.body.user_id
    var image_id = req.body.image_id
    

    db_image.delete_expression_data(user_id, image_id, function(err, result) {
        if(err) console.log(err)
        else {
            db_image.decrease_like(image_id, function(err, result) {
                if(err) console.log(err)
                else {
                    console.log("스토리 데이터 제거완료");
                }
            })
        }
    })
})




module.exports = router;
