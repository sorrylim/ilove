var pool = require('../../config/db_config');

module.exports = function () {
  return {
    get_user_list: function(user_gender, callback) {
      pool.getConnection(function(err, con) {
        var sql = `select user.user_id, user_nickname, user_birthday, user_city, user_recentgps, user_previewintroduce, user_phone, image from user, image where user_gender='${user_gender}' and user.user_id=image.user_id and image.image_usage='mainprofile'`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    get_new_user_list : function(user_gender, callback) {
      pool.getConnection(function(err, con) {
        var sql = `select user.user_id, user_nickname, user_birthday, user_city, user_recentgps, user_recenttime, user_phone, image from user, image where user_gender='${user_gender}' and user.user_id=image.user_id and image.image_usage='mainprofile' order by user_signdate desc limit 4`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    update_option: function(user_id, user_option, user_optiondata, callback) {
      pool.getConnection(function(err, con) {
        var sql = `update useroption set ${user_option} = '${user_optiondata}' where user_id='${user_id}'`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    update_option_city: function(user_id, user_option, user_optiondata, callback) {
      pool.getConnection(function(err, con) {
        var sql = `update user set ${user_option} = '${user_optiondata}' where user_id='${user_id}'`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    get_option_city: function(user_id, callback) {
      pool.getConnection(function(err, con) {
        var sql = `select user_city, user_previewintroduce, user_introduce from user where user_id='${user_id}'`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    pool: pool
  }
}