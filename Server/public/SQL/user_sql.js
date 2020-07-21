var pool = require('../../config/db_config');

module.exports = function () {
  return {
    login: function(user_id, callback) {
      pool.getConnection(function(err, con) {
        var sql = `select * from user where user_id='${user_id}'`
        con.query(sql, function(err, result, fields) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    get_user_list: function(user_gender, callback) {
      pool.getConnection(function(err, con) {
        var sql = `select user.user_id, user_nickname, user_birthday, user_city, user_recentgps, user_introduce, user_phone, image from user, image where user_gender='${user_gender}' and user.user_id=image.user_id and image.image_usage='mainprofile' and user.user_upprofile=0`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    get_upprofile_user_list: function(user_gender, callback) {
      pool.getConnection(function(err, con) {
        var sql = `select user.user_id, user_nickname, user_birthday, user_city, user_recentgps, user_introduce, user_phone, image from user, image where user_gender='${user_gender}' and user.user_id=image.user_id and image.image_usage='mainprofile' and user.user_upprofile=1`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    get_new_user_list : function(callback) {
      pool.getConnection(user_gender, function(err, con) {
        var sql = `select user.user_id, user_nickname, user_birthday, user_recentgps, user_recenttime, user_phone, image from user, image where user_gender='${user_gender}' and user.user_id=image.user_id and image.image_usage='mainprofile' order by user_signdate desc limit 4`
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
    update_introduce: function(user_id, introduce_type, introduce_data, clalback) {
      pool.getConnection(function(err, con) {
        var sql = `update user set ${introduce_type} = '${introduce_data}' where user_id='${user_id}'`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    update_recentgps : function(user_id, user_recentgps, callback) {
      pool.getConnection(function(err, con) {
        var sql = `update user set user_recentgps='${user_recentgps}' where user_id='${user_id}'`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    update_recenttime: function(user_id, user_recenttime, callback) {
      pool.getConnection(function(err, con) {
        var sql = `update user set user_recenttime='${user_recenttime}' where user_id='${user_id}'`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    get_item_count : function(user_id, callback) {
      pool.getConnection(function(err, con) {
        var sql = `select user_candycount, user_likecount, user_messageticket from user where user_id='${user_id}'`
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
