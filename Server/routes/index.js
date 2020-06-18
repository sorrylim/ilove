var express = require('express');
var router = express.Router();
var db_test=require('../public/SQL/test')()


/* GET home page. */
router.get('/', function(req, res, next) {
	res.render("login",{})
});

router.get('/main', function(req,res,next){
	res.render("sidebar_menu",{})
});

router.get('/sidebar', function(req,res,next){
	res.render("sidebar_menu",{})
})

router.get('/member',function(req,res,next){
	res.render("member/member",{})
})

router.get('/member/member001',function(req,res,next){
	res.render("member/member001",{})
})
router.get('/member/member002',function(req,res,next){
	res.render("member/member002",{})
})
router.get('/payment',function(req,res,next){
	res.render("payment",{})
})
module.exports = router;
