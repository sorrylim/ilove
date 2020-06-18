var express = require('express');
var router = express.Router();

var db_user = require('../public/SQL/user_sql')()

/* GET users listing. */
router.post('/check/email', function (req, res, next) {
  var email = req.body.email

  db_user.check_email(email, function (err, result) {
    if (err) console.log(err)
    else res.send(result[0])
  })
});

router.get('/', function (req, res, next) {
  db_user.get_user(function (err, result) {
    if (err) console.log(err);
    else res.send(result);
  })
});

router.get('/search', function (req, res, next) {

  var search = req.body.search

  console.log(search)

  db_user.search_user(search, function (err, result) {
    if (err) console.log(err)
    else res.send(result)
  })
})

router.post('/join', function (req, res, next) {
  var id = req.body.id
  var password = req.body.password
  var nickname = req.body.nickname
  var userType = req.body.user_type
  var userBank = 'asd'
  var userAccount = req.body.user_account
  var joinDate = req.body.user_join_date
  var image = req.body.image
  var phone = req.body.phone
  var loginType = req.body.login_type
  db_user.join(id, password, loginType, nickname, userType, image, phone, userBank, userAccount, joinDate, function (err, result) {
    if (err) console.log(err)
    else {
      var result = new Object()
      result.result = "success"
      console.log("회원가입")
      res.send(result)
    }
  })
})

router.post('/login', function (req, res, next) {
  db_user.login(req.body.id, function (err, result) {
    if (err) console.log(err);
    else {
      var data = result[0]
      res.send(data);
    }
  })
});

router.post('/web/login', function (req, res, next) {
  db_user.login(req.body.id, function (err, result) {
    if (err) console.log(err)
    else {
      if (result[0].user_pw == req.body.pw) {
        res.cookie("user", req.body.id, {
          expires: new Date(Date.now() + 900000),
          httpOnly: true
        })
        var obj = new Object()
        obj.result = "success"
        res.send(obj)
      }
      else {
        obj.result = "fail"
        res.send(obj)
      }
    }
  })
})

router.post('/update/editUserReq', function (req, res, next) {
  var id = req.body.id
  var nickname = req.body.nickname
  var phone = req.body.phone
  var about = req.body.about
  var address = req.body.address
  var userType = req.body.user_type

  db_user.edit_user(id, nickname, phone, about, address, userType, function (err, result) {
    if (err) console.log(err)
    else {
      var object = new Object()
      object.result = "success"
      res.send(result)
    }
  })
})

router.post('/my', function (req, res, next) {
  var id = req.body.id

  db_user.get_my_info(id, function (err, result) {
    if (err) console.log
    else res.send(result[0])
  })
})

router.post('/remove/token', function (req, res, next) {
  var id = req.body.id

  db_user.remove_token(id)

  var object = new Object()
  object.result = "success"
  res.send(object)
})

router.post('/insert/token', function (req, res, next) {
  var id = req.body.id
  var token = req.body.token
  db_user.insert_token(id, token)

  var object = new Object()
  object.result = "success"
  res.send(object)
})
router.post('/my/product', function (req, res, next) {
  var userId = req.body[0].user_id
  db_user.get_my_product(userId, function (err, result) {
    if (err) console.log(err)
    else res.send(result)
  })
})

module.exports = router;
