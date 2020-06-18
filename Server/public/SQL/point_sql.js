var pool=require('../../config/db_config');

module.exports = function () {
    return {
        get_point: function ( callback) {
        pool.getConnection(function (err, con) {
          con.release()
          var sql = `select * from ReturnPoint `
          con.query(sql, function (err, result) {
            if (err) console.log(err);
            else callback(null, result);
          })
        })
      },    
      search_point:function(search,callback){
        pool.getConnection(function(err,con){
          var sql=`select * from ReturnPoint where user_id like '%${search}%'`
          con.query(sql,function(err,result,fields){
            con.release()
            if(err) callback(err)
            else callback(null,result)
          })
        })
      },
      pool: pool
    }
  }
  