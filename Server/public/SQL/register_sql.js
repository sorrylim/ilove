var pool=require('../../config/db_config');

module.exports=function(){
    return {
        register_product : function(userId, registerTitle, productCategory, productSubCategory, productType, productStatus, productBrand, productPrice, sellerStore, registerContent, tradeOption, sellerAddress, registerDate, registerLike, registerView, userNickname){
            pool.getConnection(function(err, con){
                var sql=`insert into Register(user_id,register_title,product_category,product_subcategory,product_type,product_status,product_brand,product_price,seller_store,register_content,trade_option,seller_address,register_date,register_like,register_view,user_nickname) values('${userId}', '${registerTitle}', '${productCategory}', '${productSubCategory}', '${productType}', '${productStatus}', '${productBrand}', '${productPrice}', ${sellerStore}, '${registerContent}', '${tradeOption}', '${sellerAddress}', '${registerDate}', 0, 0,'${userNickname}')`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else console.log("상품 등록 완료")
                })
            })
        },
        get_register : function(userId,registerTitle,registerDate,callback){
            pool.getConnection(function(err,con){
                var sql=`select * from Register where user_id='${userId}' and register_title='${registerTitle}' and register_date='${registerDate}'`
                con.query(sql, function(err,result,fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null,result)
                })
            })
        },
        get_register_byid : function(registerId,callback){
            pool.getConnection(function(err,con){
                var sql=`select * from Register where register_id=${registerId}`
                con.query(sql,function(err,result,fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null,result)
                })
            })
        },
        get_image : function(registerId,callback){
            pool.getConnection(function(err,con){
                var sql=`select * from image where register_id='${registerId}'`
                con.query(sql,function(err,result,fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null,result)
                })
            })
        },
        insert_image : function(registerId,registerTitle,image,callback){
            pool.getConnection(function(err,con){
                var sql=`insert into image(register_id,register_title,product_image) values(${registerId},'${registerTitle}','${image}')`
                con.query(sql, function(err,result,fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null,result)
                })
            })
        },
	remove_image : function(registerId,callback){
	    pool.getConnection(function(err,con){
		var sql="delete from image where register_id="+registerId
		con.query(sql,function(err,result,fields){
		    con.release()
		    if(err) callback(err)
		    else callback(null,result)
		})
	    })
	},
        get_register_recent:function(callback){
            pool.getConnection(function(err,con){
                var sql=`select register_id, register_title, product_price, product_status from Register order by register_date desc`
                con.query(sql, function(err,result,fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null,result)
                })
            })
        },
        get_register_popular:function(callback){
            pool.getConnection(function(err,con){
                var sql=`select register_id, register_title, product_price, product_status from Register order by register_like desc`
                con.query(sql, function(err,result,fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null,result)
                })
            })
        },
	get_register_search:function(searchText, callback){
            pool.getConnection(function(err, con){
                var sql=`select register_id, register_title, product_price, product_status from Register where register_title like '%${searchText}%'`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
        increase_register_like:function(registerId, callback){
            pool.getConnection(function(err, con){
                var sql=`update Register set register_like=register_like+1 where register_id=${registerId}`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })  
        },
        decrease_register_like:function(registerId, callback){
            pool.getConnection(function(err, con){
                var sql=`update Register set register_like=register_like-1 where register_id=${registerId}`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })  
        },
	get_subcategory:function(productSubCategory, callback){
            pool.getConnection(function(err, con){
                var sql=`select register_id, register_title, product_price, product_status from Register where product_subcategory='${productSubCategory}'`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })  
        },
	update_product:function(registerId, userId, registerTitle, productCategory, productSubCategory, productType, productStatus, productBrand, productPrice, sellerStore, registerContent, tradeOption, sellerAddress, registerDate, callback) {
            pool.getConnection(function(err, con){
                var sql=`update Register set register_title='${registerTitle}', product_category='${productCategory}', product_subcategory='${productSubCategory}', product_type='${productType}', product_status='${productStatus}', product_brand='${productBrand}', product_price='${productPrice}', seller_store=${sellerStore}, register_content='${registerContent}', trade_option='${tradeOption}', seller_address='${sellerAddress}', register_date='${registerDate}' where register_id=${registerId}`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
	increase_register_view:function(registerId, callback) {
            pool.getConnection(function(err, con){
                var sql=`update Register set register_view=register_view+1 where register_id=${registerId}`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null, result)
                })
            })
        },
        pool: pool
    }
};
