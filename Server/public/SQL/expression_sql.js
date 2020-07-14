var pool = require('../../config/db_config');

module.exports = function () {
  return {
        get_count_expression: function(user_id, callback) {
          pool.getConnection(function(err, con){
              var sql = `select * from expression where user_id='${user_id}' or partner_id='${user_id}'`
              con.query(sql, function(err, result) {
                  con.release()
                  if(err) callback(err)
                  else callback(null, result)
              })
          })
        },
        get_count_each: function(user_id, callback) {
            pool.getConnection(function(err, con) {
                var sql = `select * from expressioneach where user_id='${user_id}' or partner_id='${user_id}'`
                con.query(sql, function(err, result) {
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
        get_count_view: function(user_id, callback) {
            pool.getConnection(function(err, con) {
                var sql = `select visit_type, count(visit_type) from viewhistory where partner_id='${user_id}' group by visit_type`
                con.query(sql, function(err, result) {
                    con.release()
                    if(err) callback(err)
                    else callback(null,result)
                })
            })  
        },
        get_send_user: function(user_id, expression_type, callback) {
            pool.getConnection(function(err, con){
                var sql = `select user.user_id, user_nickname, user_birthday, user_city, expression_date, user_phone, image from user, expression, image where expression.user_id = '${user_id}' and expression.partner_id = user.user_id and expression_type='${expression_type}' and image.user_id = user.user_id and image.image_usage='mainprofile'`
                con.query(sql, function(err, result) {
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
        get_receive_user: function(user_id, expression_type, callback) {
            pool.getConnection(function(err, con){
                var sql = `select user.user_id, user_nickname, user_birthday, user_city, expression_date, user_phone, image from user, image, expression where expression.partner_id = '${user_id}' and expression.user_id = user.user_id and expression_type='${expression_type}' and image.user_id = user.user_id and image.image_usage='mainprofile'`
                con.query(sql, function(err, result) {
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
        get_each1_user: function(user_id, expression_type, callback) {
            pool.getConnection(function(err, con){
                var sql = `select user.user_id, user_nickname, user_birthday, user_city, expression_date, user_phone, image from user, expressioneach, image where expressioneach.user_id='${user_id}' and expressioneach.partner_id = user.user_id and expression_type = '${expression_type}' and image.user_id = user.user_id and image.image_usage='mainprofile'`
                con.query(sql, function(err, result) {
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
        get_each2_user: function(user_id, expression_type, callback) {
            pool.getConnection(function(err, con) {
                var sql = `select user.user_id, user_nickname, user_birthday, user_city, expression_date, user_phone, image from user, expressioneach, image where expressioneach.partner_id='${user_id}' and expressioneach.user_id = user.user_id and expression_type = '${expression_type}' and image.user_id = user.user_id and image.image_usage='mainprofile'` 
                con.query(sql, function(err, result) {
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
        get_visit_user: function(user_id, visit_type, callback) {
            pool.getConnection(function(err, con) {
                var sql = `select user.user_id, user_nickname, user_birthday, user_city, visit_date, user_phone, image from user, viewhistory, image where viewhistory.partner_id='${user_id}' and viewhistory.visit_type='${visit_type}' and viewhistory.user_id = user.user_id and image.user_id = user.user_id and image.image_usage='mainprofile'`
                con.query(sql, function(err, result) {
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
        insert_visit_data : function(user_id, partner_id, visit_type, visit_date, callback) {
            pool.getConnection(function(err, con) {
                var sql = `insert into viewhistory values('${user_id}', '${partner_id}', '${visit_type}', '${visit_date}')`
                con.query(sql, function(err, result) {
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
        update_visit_data : function(user_id, partner_id, visit_type, visit_date, callback) {
            pool.getConnection(function(err, con) {
                var sql = `update viewhistory set visit_date='${visit_date}' where user_id='${user_id}' and partner_id='${partner_id}' and visit_type='${visit_type}'`
                con.query(sql, function(err, result) {
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
        check_visit_data : function(user_id, partner_id, visit_type, callback) {
            pool.getConnection(function(err, con) {
                var sql = `select count(*) as count from viewhistory where user_id='${user_id}' and partner_id='${partner_id}' and visit_type='${visit_type}'`
                con.query(sql, function(err, result) {
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
        get_expression_data : function(user_id, callback) {
            pool.getConnection(function(err, con) {
                var sql = `select partner_id, expression_type from expression where user_id='${user_id}'`
                con.query(sql, function(err, result) {
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
        insert_expression_data : function(user_id, partner_id, expression_type, expression_date, callback) {
            pool.getConnection(function(err, con) {
                var sql = `insert into expression values('${user_id}', '${partner_id}', '${expression_type}', '${expression_date}')`
                con.query(sql, function(err, result) {
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
        delete_expression_data : function(user_id, partner_id, expression_type, callback) {
            pool.getConnection(function(err, con) {
                var sql = `delete from expression where user_id='${user_id}' and partner_id='${partner_id}' and expression_type='${expression_type}'`
                con.query(sql, function(err, result) {
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
        insert_expressioneach_data : function(user_id, partner_id, expression_type, expression_date, callback) {
            pool.getConnection(function(err, con) {
                var sql = `insert into expressioneach values('${user_id}', '${partner_id}', '${expression_type}', '${expression_date}')`
                con.query(sql, function(err, result) {
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
        delete_expressioneach_data: function(user_id, partner_id, expression_type,callback) {
            pool.getConnection(function(err, con) {
                var sql = `delete from expressioneach where expression_type='${expression_type}' and (user_id='${user_id}' and partner_id='${partner_id}') or (user_id='${partner_id}' and partner_id='${user_id}')`
                con.query(sql, function(err, result) {
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })  
        },
        check_expression_data : function(user_id, partner_id, expression_type, callback) {
            pool.getConnection(function(err, con) {
                var sql = `select count(*) as count from expression where partner_id='${user_id}' and user_id='${partner_id}' and expression_type='${expression_type}'`
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
