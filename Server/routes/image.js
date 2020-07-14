var express = require('express');
var router = express.Router();

var db_image = require('../public/SQL/image_sql')()

var upload = multer({storage: multer.diskStorage({
    destination: function(req, file, cb) {
            cb(null, 'uploads/')
    },
    filename: function(req, file, cb) {
            cb(null, new Date().valueOf() +  path.extname(file.originalname));
    }
}),
});

router.post('/upload/profile', upload.single('img'), (req, res) => {

    var user_id = req.body.user_id
    var image = "http://18.217.130.157:3000/" + req.file.filename
    var image_usage = req.body.image_usage
    var image_date = req.body.image_date
    var upload_type = req.body.upload_type
    var image_id = req.body.image_id

    if(upload_type == "insert") {
            db_image.insert_image(user_id, image, image_usage, "NULL", image_date, function(err, result) {
                    if(err) console.log(err)
                    else {
                            console.log(req.file.filename + user_id)
                            res.send(req.file)
                    }
            })
    }
    else if(upload_type == "update") {
            db_image.update_image(parseInt(image_id), image, function(err, result) {
                    if(err) console.log(err)
                    else {
                            console.log(req.file.filename + user_id)
                            res.send(req.file)
                    }
            })
    }

})

router.post('/upload/story', upload.single('img'), (req, res) => {

    var user_id = req.body.user_id
    var image = "http://18.217.130.157:3000/" + req.file.filename
    var image_content = req.body.image_content
    var image_date = req.body.image_date

    db_image.insert_image(user_id, image, "story", image_content, image_date, function(err, result) {
            if(err) console.log(err)
            else {
                    console.log(req.file.filename + user_id)
                    res.send(req.file)
            }
    })
})



router.post('/delete', function(req, res, next) {
    var image_id = req.body.image_id

    db_image.delete_image(image_id, function(err, result) {
        if(err) console.log(err)
        else {
            var object = new Object()
            object.result = "success"
            res.send(object)
        }
    })
})

router.post('/get/profile', function(req, res, next) {
    var user_id = req.body[0].user_id

    db_image.get_profile_image(user_id, function(err, result) {
        if(err) console.log(err)
        else res.send(result)
    })
})

router.post('/get/story', function(req, res, next) {
    var user_id = req.body[0].user_id
    var image_usage = req.body[0].image_usage

    db_image.get_story_image(user_id, image_usage, function(err, result) {
        if(err) console.log(err)
        else {
                res.send(result)
        }
    })
})

router.post('/get/my/story', function(req, res, next) {
    var user_id = req.body.user_id
    
    db_image.get_my_story_image(user_id, function(err, result) {
        if(err) console.log(err)
        else res.send(result[0])
    })
})

router.post('/get/story/user', function(req, res, next) {
    var user_id = req.body.user_id
    var image_id = req.body.image_id

    db_image.increase_view(image_id, function(err, result) {
        if(err) console.log(err)
        else {
            db_image.get_story_user(image_id, function(err, user) {
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
                    var object = new Object()
                    object.result = "success"
                    res.send(object)
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
                    var object = new Object()
                        object.result="success"
                        res.send(object);
                }
            })
        }
    })
})

module.exports = router;