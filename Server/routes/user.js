var express = require('express');
var async = require('async');
var router = express.Router();

var db_user = require('../public/SQL/user_sql')()
var db_expression = require('../public/SQL/expression_sql')()

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

module.exports = router;
