var express = require('express');
var router = express.Router();
var db_inquire=require('../public/SQL/inquire_sql')()

router.post('/send', function(req, res, next) {
    var user_id = req.body.user_id
    var send_date = req.body.send_date
    var inquire_title = req.body.inquire_title
    var inquire_content = req.body.inquire_content
    var inquire_type = req.body.inquire_type

    db_inquire.send_inquire(user_id, send_date, inquire_title, inquire_content, inquire_type, function(err, result) {
        if(err) console.log(err)
        else {
            var object = new Object()
            object.result = "success"
            res.send(object)
        }
    })
})

router.post('/get', function (req, res, next) {
    var user_id = req.body[0].user_id
    
    db_inquire.get_inquire(user_id, function (err, result) {
      if (err) console.log(err)
      else res.send(result)
    })
  })

module.exports = router;
