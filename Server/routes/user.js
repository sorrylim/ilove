var express = require('express');
var async = require('async');
var router = express.Router();

var db_user = require('../public/SQL/user_sql')()
var db_expression = require('../public/SQL/expression_sql')()


router.post('/login', function (req, res, next) {
  var user_id = req.body.user_id

  db_user.login(user_id, function (err, result) {
    if (err) console.log(err)
    else {
      db_user.get_my_blocking_user(user_id, function(err, blocking) {
        if(err) console.log(err)
        else {
          result[0]["user_blockingnumber"] = blocking[0]["blocking_partner"];
          res.send(result[0])
        }
      })
    }
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

router.post('/get/option', function(req, res, next) {
  var user_id = req.body.user_id

  db_user.get_option(user_id, function(err, option) {
    if(err) console.log(err)
    else {
      db_user.get_option_city(user_id, function(err, result) {
        if(err) console.log(err)
        else {
          option[0]["user_city"] = result[0]["user_city"];
          console.log(option);
          res.send(option);
        }
      })
    }
  })
})

router.post('/upprofile', function(req, res, next) {
  var user_id = req.body.user_id
  var upprofile_date = req.body.upprofile_date

  db_user.update_upprofile(user_id, upprofile_date, function(err, result) {
    if(err) console.log(err)
    else {
      var object = new Object()
      object.result = "success"
      res.send(object)
    }
  })
  
})

router.post('/get/blocking', function(req, res, next) {
  var user_id = req.body[0].user_id

  db_user.get_blocking_user(user_id, function(err, result) {
    if(err) console.log(err)
    else res.send(result)
  })
})

router.post('/get/my/blocking', function(req, res, next) {
  var user_id = req.body[0].user_id

  db_user.get_my_blocking_user(user_id, function(err, result) {
    if(err) console.log(err)
    else res.send(result)
  })
})

router.post('/save/showprofile', function(req, res, next) {
  var user_id = req.body.user_id
  var partner_id = req.body.partner_id
  var showprofile_date = req.body.showprofile_date
})

module.exports = router;
