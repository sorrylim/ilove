var express=require('express');
var router=express.Router();

var db_view=require('../public/SQL/view_sql')();

router.post('/insert', function(req,res,next){
  var registerId=req.body.register_id
  var userId=req.body.user_id
  var viewDate=req.body.view_date
  var registerTitle=req.body.register_title

  db_view.insert_view(userId, registerId, viewDate, registerTitle, function(err,result){
    if(err) console.log(err)
    else {
      var object = new Object()
      object.result="success"
      res.send(object)
    }
  })
})

router.post('/update', function(req,res,next){
  var registerId=req.body.register_id
  var userId=req.body.user_id
  var viewDate=req.body.view_date

  db_view.update_view(userId, registerId, viewDate, function(err, result){
      if(err) console.log(err)
      else{
          var object = new Object()
          object.result="success"
          res.send(object)
      }
  })
})

router.post('/check', function(req, res, next){
    var userId=req.body.user_id
    var registerId=req.body.register_id

    db_view.check_view(userId, registerId, function(err, result){
        if(err) console.log(err)
        else res.send(result)
    })
})

router.post('/get', function(req, res, next){
  var userId=req.body[0].user_id

  db_view.get_view(userId, function(err, result) {
    if(err) console.log(err)
    else {
      res.send(result)
      console.log("뷰데이터 불러오기 완료")
    }
  })
})

module.exports = router;
