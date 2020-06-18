var express = require('express');
var router = express.Router();
var db_follow=require('../public/SQL/follow_sql')()


/* GET home page. */
router.post('/check', function(req, res, next) {
  db_follow.check_follow(follower, following, function(err,result){
    if(err) console.log(err)
    else res.send(result)
  })
})

router.post('/insert', function(req, res, next) {
    db_follow.insert_follow(follower, following, function(err, result) {
        if(err) console.log(err)
        else {
            var object = new Object()
            object.result = "success"
            res.send(object)
            console.log("팔로우데이터 삽입완료")
        }
    })
})

router.post('/delete', function(req, res, next) {
    db_follow.delete_follow(follower, following, function(err, result) {
        if(err) console.log(err)
        else {
            var object = new Object()
            object.result = "success"
            res.send(object)
            console.log("팔로우데이터 삭제완료")
        }
    })
})

router.post('/get/product', function(req, res, next) {
    var follower = req.body[0].follower
    var following = req.body[0].following

    db_follow.get_product(follower, following, function(err, result) {
        if(err) console.log(err)
        else res.send(result)
    })
})

router.post('/get/count/follower', function(req, res, next) {
    var following=req.body.following

    db_follow.get_count_follower(following, function(err, result) {
        if(err) console.log(err)
        else res.send(result[0])
    })
})

router.post('/get/count/following', function(req, res, next) {
    var follower=req.body.follower

    db_follow.get_count_following(follower, function(err, result) {
        if(err) console(err)
        else res.send(result[0])
    })
})

module.exports = router;
