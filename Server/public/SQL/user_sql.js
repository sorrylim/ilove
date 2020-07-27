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
	   insert: function(table,col,values, callback) {
      pool.getConnection(function(err, con) {
        var sql = `INSERT INTO ${table}(${col}) VALUES ${values}`
        con.query(sql, function(err, result, fields) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
	  update:function(table,values,cond,callback){
      pool.getConnection(function(err, con) {
       var query=`UPDATE ${table} SET ${values} WHERE ${cond}`
		  con.query(query, function(error, result) {
            callback(error, result);
          }) 
        }) 
      },
	deleteByCond: function ( table, cond, callback) {
    pool.getConnection(function(err, con) {
        var query = `DELETE FROM ${table} WHERE ${cond}`;
        con.query(query, function(error, result) {
            callback(error, result);
          })
        });
    },

    get_user_list: function(user_gender, callback) {
      pool.getConnection(function(err, con) {
        var sql = `select user.user_id, user_nickname, user_birthday, user_city, user_recentgps, user_recenttime, user_previewintroduce, user_phone, image from user, image where user_gender='${user_gender}' and user.user_id=image.user_id and image.image_usage='mainprofile' and user.user_upprofile=0`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
    get_upprofile_user_list: function(user_gender, callback) {
      pool.getConnection(function(err, con) {
        var sql = `select user.user_id, user_nickname, user_birthday, user_city, user_recentgps, user_recenttime, user_previewintroduce, user_phone, image from user, image where user_gender='${user_gender}' and user.user_id=image.user_id and image.image_usage='mainprofile' and user.user_upprofile=1`
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
	  update_token: function(user_id,token,callback){
		  pool.getConnection(function(err, con){
			  var sql = `update user set token='${token}' where user_id='${user_id}'`
			  con.query(sql,function(err,result){
				  con.release()
				  if(err) callback(err)
				  else callback(null,result)
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
        var sql = `select user_city, user_introduce, user_previewintroduce,user_gender from user where user_id='${user_id}'`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
	  get_token: function(user_id, callback) {
		  pool.getConnection(function(err,con){
			  var sql = `select token from user where user_id='${user_id}'`
			  con.query(sql,function(err,result){
				  con.release()
				  if(err) callback(err)
				  else callback(null,result)
			  })
		  })
	  },
    update_introduce: function(user_id, introduce_type, introduce_data, callback) {
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
        var sql = `select user_candycount, user_likecount, user_messageticket, user_vip from user where user_id='${user_id}'`
        con.query(sql, function(err, result) {
          con.release()
          if(err) callback(err)
          else callback(null, result)
        })
      })
    },
	  get_alarm_state : function(user_id, callback){
		  pool.getConnection(function(err,con){
			  var sql=`select alarm_like,alarm_meet,alarm_checkprofile,alarm_updateprofile,alarm_message from user where user_id='${user_id}'`
			  con.query(sql,function(err,result){
				  con.release()
				  if(err) callback(err)
				  else callback(null,result)
			  })
		  })
	  },
	  update_alarm : function(user_id,alarm_type,alarm_state){
		  pool.getConnection(function(err,con){
			  var sql=`update user set ${alarm_type}=${alarm_state} where user_id='${user_id}'`
			  con.query(sql,function(err,result){
				  con.release()
				  if(err) console.log(err)
				  else console.log("알람 설정 변경")
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
    pool: pool
  }
}

