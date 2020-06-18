var pool=require('../../config/db_config');

module.exports=function(){
    return {
        insert_view : function(userId, registerId, viewDate, registerTitle, callback){
            pool.getConnection(function(err, con){
                var sql=`insert into view values('${userId}', ${registerId}, '${viewDate}', '${registerTitle}')`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else callback(null, result)
                })
            })
        },
        update_view : function(userId, registerId, viewDate, callback){
            pool.getConnection(function(err, con){
                var sql=`update view set view_date='${viewDate}' where user_id='${userId}' and register_id=${registerId}`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else callback(null, result)
                })
            })
        },
	check_view : function(userId, registerId, callback){
            pool.getConnection(function(err, con){
                var sql=`select count(*) as count from view where user_id='${userId}' and register_id=${registerId}`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else callback(null, result)
                })
            })
        },
	get_view : function(userId, callback){
            pool.getConnection(function(err, con){
                var sql=`select * from view where user_id='${userId}'`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else callback(null, result)
                })
            })
        },
        pool: pool
    }
};

