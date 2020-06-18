var pool=require('../../config/db_config');

module.exports=function(){
    return {
        insert_wishlist : function(registerId, userId, callback){
            pool.getConnection(function(err, con){
                var sql=`insert into Wishlist values(${registerId}, '${userId}')`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else callback(null,result)
                })
            })
        },
        delete_wishlist : function(registerId, userId, callback){
            pool.getConnection(function(err, con){
                var sql=`delete from Wishlist where register_id=${registerId} and user_id='${userId}'`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else callback(null,result)
                })
            })
        },
        check_wishlist : function(registerId, userId, callback){
            pool.getConnection(function(err, con){
                var sql=`select count(*) as count from Wishlist where register_id=${registerId} and user_id='${userId}'`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else callback(null, result)
                })
            })
        },
	get_wishlist : function(userId, callback) {
            pool.getConnection(function(err, con){
                var sql=`select Register.register_id, register_title, product_price, product_status from Register, Wishlist where Register.register_id=Wishlist.register_id and Wishlist.user_id='${userId}'`
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

