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
        get_image:function(image_usage, callback) {
          pool.getConnection(function(err, con){
              var sql = `select image_id, image from image where image_usage = '${image_usage}'`
              con.query(sql, function(err, result) {
                con.release()
                if(err) callback(err)
                else callback(null, result)
            })
          })
        },
        get_story_data: function(image_id, callback) {
          pool.getConnection(function(err, con) {
            var sql = `select user.user_id, user_nickname, user_birthday, user_recentgps, image_content from user, image where image_id = ${image_id} and user.user_id = image.user_id`
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
        pool: pool
  }
}
