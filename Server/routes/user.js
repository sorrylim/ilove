var express = require('express');
var async = require('async');
var router = express.Router();

var db_user = require('../public/SQL/user_sql')()
var db_expression = require('../public/SQL/expression_sql')()

router.post('/login', function(req, res, next) {
  var user_id = req.body.user_id

  db_user.login(user_id, function(err, result) {
    if(err) console.log(err)
    else res.send(result)
  })
})

router.post('/get/list', function(req, res, next) {
  var user_gender = req.body[0].user_gender
  var user_id = req.body[0].user_id

  db_user.get_user_list(user_gender, function(err, user) {
    if(err) console.log(err)
    else {
      db_expression.get_expression_data(user_id, function(err, expression) {
        if(err) console.log(err)
        else {
          for (var i in user) {
            user[i]["like"] = 0;
            user[i]["meet"] = 0;

            for (var j in expression) {

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

router.post('/get/new/list', function(req, res, next) {
  var user_gender = req.body[0].user_gender

  db_user.get_new_user_list(user_gender, function(err, result) {
    if(err) console.log(err)
    else res.send(result)
  })
})

router.post('/update/option', function(req, res, next) {
  var user_id = req.body.user_id
  var user_option = req.body.user_option
  var user_optiondata = req.body.user_optiondata

  db_user.update_option(user_id, user_option, user_optiondata, function(err, result) {
    if(err) console.log(err)
    else {
      var object = new Object()
      object.result = "success"
      res.send(object)
    }
  })
})

router.post('/update/option/city', function(req, res, next) {
  var user_id = req.body.user_id
  var user_option = req.body.user_option
  var user_optiondata = req.body.user_optiondata

  db_user.update_option_city(user_id, user_option, user_optiondata, function(err, result) {
    if(err) console.log(err)
    else {
      var object = new Object()
      object.result = "success"
      res.send(object)
    }
  })
})

router.post('/update/token',function(req,res,next){
        var user_id=req.body.user_id
        var token=req.body.token

        console.log(`${user_id}, ${token}`)

        db_user.update_token(user_id,token,function(err, result){
                if(err) console.log(err)
                else{
                        var obj=new Object()
                        obj.result="success"
                        res.send(obj)
                }
        })
})

router.post('/get/option', function(req, res, next) {
  var user_id = req.body.user_id

  db_user.get_option(user_id, function(err, option) {
    if(err) console.log(err)
    else {
      db_user.get_option_city(user_id, function(err, result) {
        if(err) console.log(err)
        else {
          option[0]["user_city"] = result[0]["user_city"];
          option[0]["user_introduce"] = result[0]["user_introduce"];
          option[0]["user_previewintroduce"] = result[0]["user_previewintroduce"];
          console.log(option[0]);
          res.send(option[0]);
        }
      })
    }
  })
})

router.post('/update/introduce', function(req, res, next) {
  var user_id = req.body.user_id
  var introduce_type = req.body.introduce_type
  var introduce_data = req.body.introduce_data

  db_user.update_introduce(user_id, introduce_type, introduce_data, function(err, result) {
    if(err) console.log(err)
    else {
      var object = new Object()
      object.result = "success"
      res.send(object)
    }
  })
})

router.post('/update/recentgps', function(req, res, next) {
  var user_id = req.body.user_id
  var user_recentgps = req.body.user_recentgps
  var user_recenttime = req.body.user_recenttime

  db_user.update_recentgps(user_id, user_recentgps, function(err, result) {
    if(err) console.log(err)
    else {
      db_user.update_recenttime(user_id, user_recenttime, function(err, result) {
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

router.post('/get/item/count', function(req, res, next) {
  var user_id = req.body.user_id

  db_user.get_item_count(user_id, function(err, result) {
    if(err) console.log(err)
    else res.send(result[0])
  })
})

module.exports = router;