var pool = require('../../config/db_config');

module.exports = function () {
  return {
    check_follow: function(follower, following, callback) {
      pool.getConnection(function(err, con) {
        var sql = `select count(*) as count from follow where follower='${follower}' and following='${following}'`
        con.query(sql, function(err, result) {
            con.release()
            if (err) console.log(err);
            else callback(null, result);
        })
      })
    },
    insert_follow: function(follower, following, callback) {
        pool.getConnection(function(err, con) {
            var sql = `insert into follow values('${follower}', '${following}')`
            con.query(sql, function(err, result) {
                con.release()
                if(err) console.log(err)
                else callback(null, result)
            })
        })
    },
    delete_follow: function(follower, following, callback) {
        pool.getConnection(function(err, con) {
            var sql = `delete from follow where follower='${follower}' and following='${following}'`
            con.query(sql, function(err, result) {
                con.release()
                if(err) console.log(err)
                else callback(null, result)
            })
        })
    },
    get_product: function(follower, callback) {
        pool.getConnection(function(err, con) {
            var sql = `select Register.register_id, register_title, product_price, product_status from Register, follow where Register.user_id=follow.following and follow.follower='${follower}'`
            con.query(sql, function(err, result) {
                con.release()
                if(err) console.log(err)
                else callback(null, result)
            })
        })
    },
    get_count_follower: function(following, callback) {
        pool.getConnection(function(err, con) {
            var sql = `select count(*) as count from follow where following='${following}'`
            con.query(sql, function(err, result) {
                con.release()
                if(err) console.log(err)
                else callback(null, result)
            })
        })
    },
    get_count_following: function(follower, callback) {
        pool.getConnection(function(err, con) {
            var sql = `select count(*) as count from follow where follower='${follower}'`
            con.query(sql, function(err, result) {
                con.release()
                if(err) console.log(err)
                else callback(null, result)
            })
        })
    },
    pool: pool
  }
}

