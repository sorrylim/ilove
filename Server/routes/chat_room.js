var express = require('express');
var router = express.Router();

var db_chat_room = require('../public/SQL/chat_room_sql')();


var admin = require("firebase-admin");

var serviceAccount = require("../ourcountry-10694-firebase-adminsdk-fkur0-43c13f06a2.json");

admin.initializeApp({
	credential: admin.credential.cert(serviceAccount),
	databaseURL: "https://ourcountry-10694.firebaseio.com"
});

router.post('/', function (req, res, next) {
	var maker = req.body.maker
	var partner = req.body.partner
	var roomDate = req.body.room_date
	var roomTitle = req.body.room_title

	db_chat_room.create_room(maker, partner, roomDate, roomTitle, function (err, result) {
		if (err) console.log(err)
		else {
			console.log("채팅방 생성")
		}
	})
	db_chat_room.get_room_info(maker, partner, roomDate, function (err, roomInfo) {
		if (err) console.log(err)
		else {
			res.send(roomInfo[0])
		}
	})
	db_user.get_token(user, function (err, result) {
		if (err) console.log(err)
		else {
			console.log(result)
			var registrationTokens = [result[0].token]

			admin.messaging().subscribeToTopic(registrationTokens, room_id).then(function (response) {
				console.log('success subscribeToTopic : ', response)
			}).catch(function (error) {
				console.log('error subscribeToTopic : ', error)
			})
		}
	})
})

router.post('/my_room', function (req, res, next) {
	var id = req.body[0].user_id

	db_chat_room.get_my_room(id, function (err, result) {
		if (err) console.log(err)
		else {
			res.send(result)
		}
	})
})

router.post('/check', function(req,res,next){
	var maker=req.body.maker
	var partner=req.body.partner
	var registerTitle=req.body.register_title

	db_chat_room.check_chat_room(maker,partner,registerTitle,function(err,result){
		if(err) console.log(err)
		else res.send(result)
	})
	
})

router.post('/fcm/send', function (req, res, next) {

	var topic = req.body.topic
	var content = req.body.content
	var title = req.body.title


	var message = {
		notification: {
			body: content,
			title: title
		},
		topic: topic

	}

	admin.messaging().send(message)
		.then((response) => {
			//Response is a message ID string
			console.log('Successfully sent message:', response)
			var object = new Object()
			object.result = response
			res.send(object)
		})
		.catch((error) => {
			console.log('Error sending message:', error)
			var object = new Object()
			object.result = error
			res.send(object)
		})
})

module.exports = router;
