var express = require('express');
var router = express.Router();

var db_usertype=require('../public/SQL/usertypechange_sql')()


router.get('/', function(req, res, next) {
    db_usertype.get_usertype(function(err,result){
      if(err) console.log(err);
      else res.send(result);
    })
  });
  
  module.exports = router;
