var pool = require('../../config/db_config');

module.exports = function () {
  return {
    get_user_list: function(user_gender, callback) {
      pool.getConnection(function(err, con) {
        var sql = `select user_id, user_nickname, user_birthday, user_city, user_recentgps, user_introduce, user_certification from user where user_gender='${user_gender}'`
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
    get_option : function(user_id, callback) {
      pool.getConnection(function(err, con) {
        var sql = `select * from useroption where user_id='${user_id}'`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    get_option_city: function(user_id, callback) {
      pool.getConnection(function(err, con) {
        var sql = `select user_city from user where user_id='${user_id}'`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    update_upprofile : function(user_id, upprofile_date, callback) {
      pool.getConnection(function(err, con) {
        var sql = `update user set user_upprofile=1, user_upprofiletime='${upprofile_date}' where user_id='${user_id}'`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    decrease_candy : function(user_id, candy_count, callback) {
      pool.getConnection(function(err, con) {
        var sql = `update user set user_candycount = user_candycount - ${candy_count} where user_id='${user_id}'`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    increase_candy : function(user_id, candy_count, callback) {
      pool.getConnection(function(err, con) {
        var sql = `update user set user_candycount = user_candycount + ${candy_count} where user_id='${user_id}'`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    get_blocking_user : function(user_id, callback) {
      pool.getConnection(function(err, con) {
        var sql = `select blocking_user from blocking where blocking_partner like '%${user_id}%'`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    get_my_blocking_user : function(user_id, callback) {
      pool.getConnection(function(err, con) {
        var sql = `select blocking_partner from blocking where user_id='${user_id}'`
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
