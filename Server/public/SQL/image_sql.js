var pool = require('../../config/db_config');

module.exports = function () {
  return {
        insert_image: function(user_id, image, image_usage, image_content, callback) {
          pool.getConnection(function(err, con){
              var sql = `insert into image(user_id, image, image_usage, image_content) values('${user_id}', '${image}', '${image_usage}', '${image_content}')`
              con.query(sql, function(err, result) {
                  con.release()
                  if(err) callback(err)
                  else callback(null, result)
              })
          })
        },
        update_image: function(image_id, image, callback) {
          pool.getConnection(function(err, con) {
            var sql = `update image set image='${image}' where image_id='${image_id}'`
            con.query(sql, function(err, result) {
              con.release()
              if(err) callback(err)
              else callback(null, result)
            })
          })
        },
        delete_image: function(image_id, callback) {
          pool.getConnection(function(err, con) {
            var sql = `delete from image where image_id='${image_id}'`
            con.query(sql, function(err, result) {
              con.release()
              if(err) callback(err)
              else callback(null, result)
            })
          })
        },
        get_story_image:function(user_id, image_usage, callback) {
          pool.getConnection(function(err, con){
              var sql = `select image_id, image from image where image_usage = '${image_usage}' and user_id <> '${user_id}'`
              con.query(sql, function(err, result) {
                con.release()
                if(err) callback(err)
                else callback(null, result)
            })
          })
        },
        get_my_story_image: function(user_id, callback) {
          pool.getConnection(function(err, con) {
            var sql = `select image_id, image from image where user_id='${user_id}' order by image_date desc limit 1`
            con.query(sql, function(err, result) {
              con.release()
              if(err) callback(err)
              else callback(null, result)
            })
          })
        },
        get_story_data: function(image_id, callback) {
          pool.getConnection(function(err, con) {
            var sql = `select user.user_id, user_nickname, user_birthday, user_recentgps, image_content, viewcount, likecount from user, image where image_id = ${image_id} and user.user_id = image.user_id`
            con.query(sql, function(err, result) {
              con.release()
              if(err) callback(err)
              else callback(null, result)
            })
          })
        },
        get_profile_image: function(user_id, callback) {
          pool.getConnection(function(err, con) {
            var sql = `select image_id, image, image_usage from image where user_id='${user_id}' and image_usage='mainprofile' or image_usage='profile'`
            con.query(sql, function(err, result) {
              con.release()
              if(err) callback(err)
              else callback(null, result)
            })
          })
        },
        increase_view: function(image_id, callback) {
          pool.getConnection(function(err, con) {
            var sql = `update image set viewcount=viewcount+1 where image_id = ${image_id}`
            con.query(sql, function(err, result) {
              con.release()
              if(err) callback(err)
              else callback(null, result)
            })
          })
        },
        increase_like: function(image_id, callback) {
          pool.getConnection(function(err, con) {
            var sql = `update image set likecount=likecount+1 where image_id = ${image_id}`
            con.query(sql, function(err, result) {
              con.release()
              if(err) callback(err)
              else callback(null, result)
            })
          })
        },
        decrease_like: function(image_id, callback) {
          pool.getConnection(function(err, con) {
            var sql = `update image set likecount=likecount-1 where image_id = ${image_id}`
            con.query(sql, function(err, result) {
              con.release()
              if(err) callback(err)
              else callback(null, result)
            })
          })
        },
        check_expression_data : function(user_id, image_id, callback) {
          pool.getConnection(function(err, con) {
              var sql = `select count(*) as count from expressionstory where user_id='${user_id}' and image_id=${image_id}`
              con.query(sql, function(err, result) {
                  con.release()
                  if(err) callback(err)
                  else callback(null, result)
              })
          })
        },
        insert_expression_data: function(user_id, image_id, callback) {
          pool.getConnection(function(err, con) {
            var sql = `insert into expressionstory values('${user_id}', ${image_id}, '${expression_date}')`
            con.query(sql, function(err, result) {
              con.release()
              if(err) callback(err)
              else callback(null, result)
            })
          })
        },
        delete_expression_data: function(user_id, image_id, callback) {
          pool.getConnection(function(err, con) {
            var sql = `delete from expressionstory where user_id='${user_id}' and image_id = ${image_id}`
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
