var pool=require('../../config/db_config');

module.exports = function () {
    return {
        get_usertype: function ( callback) {
        pool.getConnection(function (err, con) {
          con.release()
          var sql = `select * from UserTypeChange `
          con.query(sql, function (err, result) {
            if (err) console.log(err);
            else callback(null, result);
          })
        })
      },
      pool: pool
    }
  }
  
