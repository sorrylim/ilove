var pool=require('../../config/db_config');

module.exports = function () {
    return {
        send_inquire : function (user_id, send_date, inquire_title, inquire_content, inquire_type, callback) {
            pool.getConnection(function (err, con) {
                con.release()
                var sql = `insert into inquire(user_id, date, inquire_title, inquire_content, inquire_type) values('${user_id}', '${send_date}', '${inquire_title}', '${inquire_content}', '${inquire_type}')`
                con.query(sql, function (err, result) {
                    if (err) console.log(err);
                    else callback(null, result);
                })
            })
        },
        get_inquire : function(user_id , callback) {
            pool.getConnection(function(err, con) {
                con.release()
                var sql = `select * from inquire where user_id='${user_id}' and inquire_type='qna'`
                con.query(sql, function(err, result) {
                    if(err) console.log(err)
                    else callback(null, result)
                })
            })
        },
        pool: pool
    }
}
  