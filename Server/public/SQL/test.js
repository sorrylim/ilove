var pool=require('../../config/db_config');

module.exports=function(){
    return {
        get_test: function(callback){
            pool.getConnection(function(err,con){
                if(err) console.log(err)
                else{
                    var sql=`select * from user`;
                    con.query(sql,function(err,result,field){
                        con.release();
                        if(err)return;
                        callback(null,result);
                    })
                }
            })
        },
        pool:pool
    }
}
