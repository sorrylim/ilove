var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var userRouter = require('./routes/user');
var registerRouter = require('./routes/register');
var chatRoomRouter=require('./routes/chat_room');
var wishlistRouter=require('./routes/wishlist');
var paymentRouter=require('./routes/payment');
var viewRouter=require('./routes/view');
var followRouter=require('./routes/follow');
var usertypeRouter=require('./routes/usertypechange');
var pointRouter=require('./routes/point');
var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(express.json({limit:5000000}));
app.use(express.urlencoded({limit:5000000, extended: true, parameterLimit:50000}));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/user', userRouter);
app.use('/register',registerRouter);
app.use('/chat_room',chatRoomRouter);
app.use('/wishlist', wishlistRouter);
app.use('/payment', paymentRouter);
app.use('/view', viewRouter);
app.use('/follow', followRouter);
app.use('/point',pointRouter);
app.use('/usertypechange',usertypeRouter);
// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.send(err.message)
});

module.exports = app;
