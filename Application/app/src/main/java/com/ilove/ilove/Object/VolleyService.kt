package com.ilove.ilove.Object

import android.content.Context
import android.util.Base64
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ilove.ilove.Class.UserInfo
import org.json.JSONArray
import org.json.JSONObject
import java.sql.Timestamp
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object VolleyService {
    val ip= "http://18.217.130.157:3000"

    fun insertReq(table:String, col:String,values:String,  context: Context, success:(Int)->Unit) {
        var url = "${ip}/user/db/add"
        var json = JSONObject()
        json.put("table", table)
        json.put("values", values)
        json.put("col",col)


        var request = object : JsonObjectRequest(
                Method.POST,
                url,
                json,
                Response.Listener {
                    success(1)
                },
                Response.ErrorListener {
                    success(0)
                }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun updateReq(table:String, values:String,cond:String,  context: Context, success:(JSONObject)->Unit) {
        var url = "${ip}/user/db/update"
        var json = JSONObject()
        json.put("table", table)
        json.put("values", values)
        json.put("cond", cond)


        var request = object : JsonObjectRequest(
                Method.POST,
                url,
                json,
                Response.Listener {
                    success(it)
                },
                Response.ErrorListener {

                }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun deleteReq(table:String, cond:String,  context: Context, success:(JSONObject)->Unit) {
        var url = "${ip}/user/db/delete"
        var json = JSONObject()
        json.put("table", table)
        json.put("cond", cond)

        var request = object : JsonObjectRequest(
                Method.POST,
                url,
                json,
                Response.Listener {
                    success(it)
                },
                Response.ErrorListener {

                }) {}
        Volley.newRequestQueue(context).add(request)
    }


    fun loginReq(userId: String, userPassword: String, context: Context, success: (JSONObject) -> Unit) {
        val url = "${ip}/user/login"

        val json = JSONObject()
        json.put("user_id", userId)

        var result = JSONObject()

        var request = object : JsonObjectRequest(Method.POST
            , url
            , json
            , Response.Listener {
                result.put("user", it)
                if (userPassword != it.getString("user_password"))
                    result.put("code", 2)
                else if (userPassword == it.getString("user_password"))
                    result.put("code", 3)
                success(result)
            }
            , Response.ErrorListener {
                Log.d("test",it.toString())
                if (it is com.android.volley.TimeoutError) {
                    Log.d("test", "TimeoutError")
                    result.put("code", 2)
                } else if (it is com.android.volley.ParseError) {
                    Log.d("test", "ParserError")
                    result.put("code", 1)
                } else if (it is com.android.volley.NoConnectionError) {
                    Log.d("test", "NoConnectionError")
                    result.put("code", 0)
                }
                success(result)
            }
        ) {
        }
        //요청을 보내는 부분
        Volley.newRequestQueue(context).add(request)
    }

    fun getExpressionCountReq(userId:String, context: Context, success: (JSONObject) -> Unit) {
        var url = "${ip}/expression/get/count"

        var json = JSONObject()
        json.put("user_id", userId)


        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun getCountViewReq(userId:String, context: Context, success:(JSONObject) -> Unit) {
        var url = "${ip}/expression/get/count/view"
        var json = JSONObject()
        json.put("user_id", userId)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun getSendUserReq(userId:String, expressionType:String, context: Context, success: (JSONArray?) -> Unit) {
        var url = "${ip}/expression/get/send/user"
        var json = JSONObject()
        json.put("user_id", userId)
        json.put("expression_type", expressionType)

        var array = JSONArray()
        array.put(json)


        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun getReceiveUserReq(userId:String, expressionType:String, context: Context, success: (JSONArray?) -> Unit) {
        var url = "${ip}/expression/get/receive/user"
        var json = JSONObject()
        json.put("user_id", userId)
        json.put("expression_type", expressionType)

        var array = JSONArray()
        array.put(json)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun getEach1UserReq(userId:String, expressionType:String, context: Context, success: (JSONArray?) -> Unit) {
        var url = "${ip}/expression/get/each1/user"
        var json = JSONObject()
        json.put("user_id", userId)
        json.put("expression_type", expressionType)

        var array = JSONArray()
        array.put(json)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun getEach2UserReq(userId:String, expressionType:String, context: Context, success: (JSONArray?) -> Unit) {
        var url = "${ip}/expression/get/each2/user"
        var json = JSONObject()
        json.put("user_id", userId)
        json.put("expression_type", expressionType)

        var array = JSONArray()
        array.put(json)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun getVisitUserReq(userId:String, visitType:String, context: Context, success: (JSONArray?) -> Unit) {
        var url = "${ip}/expression/get/visit/user"
        var json = JSONObject()
        json.put("user_id", userId)
        json.put("visit_type", visitType)

        var array = JSONArray()
        array.put(json)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun getUserListReq(gender: String, userId:String, context: Context, success: (JSONArray?) -> Unit) {
        var url = "${ip}/user/get/list"
        var json = JSONObject()
        json.put("user_gender", gender)
        json.put("user_id", userId)

        var array = JSONArray()
        array.put(json)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun getUpProfileUserListReq(gender: String, userId:String, context: Context, success: (JSONArray?) -> Unit) {
        var url = "${ip}/user/get/upprofile/list"
        var json = JSONObject()
        json.put("user_gender", gender)
        json.put("user_id", userId)

        var array = JSONArray()
        array.put(json)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun getProfileImageReq(userId: String, context: Context, success: (JSONArray) -> Unit) {
        var url = "${ip}/image/get/profile"
        var json = JSONObject()
        json.put("user_id", userId)



        var array = JSONArray()
        array.put(json)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun getNewUserListReq(userGender: String, context: Context, success: (JSONArray) -> Unit) {
        var url = "${ip}/user/get/new/list"
        var json = JSONObject()
        json.put("user_gender", userGender)

        var array = JSONArray()
        array.put(json)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun insertHistoryReq(userId: String, partnerId: String, visitType: String, visitDate:String, context:Context, success:(String?) -> Unit) {
        var url = "${ip}/expression/insert/history"
        var json = JSONObject()
        json.put("user_id", userId)
        json.put("partner_id", partnerId)
        json.put("visit_type", visitType)
        json.put("visit_date", visitDate)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun insertExpressionReq(userId: String, partnerId: String, expressionType:String, expressionDate: String, context:Context, success:(String?) -> Unit) {
        var url = "${ip}/expression/insert"
        var json = JSONObject()
        json.put("user_id", userId)
        json.put("partner_id", partnerId)
        json.put("expression_type", expressionType)
        json.put("expression_date", expressionDate)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun deleteExpressionReq(userId: String, partnerId: String, expressionType:String, context:Context, success:(String?) -> Unit) {
        var url = "${ip}/expression/delete"
        var json = JSONObject()
        json.put("user_id", userId)
        json.put("partner_id", partnerId)
        json.put("expression_type", expressionType)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun chatInitReq(roomId: String, context: Context, success: (JSONArray?) -> Unit) {
        var url = "${ip}/chat/init"

        var json = JSONObject()
            .put("room_id",roomId)

        var array = JSONArray()
            .put(json)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            array,
            Response.Listener{
                success(it)
            },
            Response.ErrorListener {
                Log.d("test","${it}")
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

    fun getStoryImageReq(userId:String, imageUsage:String, userGender: String, context: Context, success:(JSONArray)->Unit) {
        var url = "${ip}/image/get/story"
        var json = JSONObject()
        json.put("user_id", userId)
        json.put("image_usage", imageUsage)
        json.put("user_gender", userGender)

        var array = JSONArray()
        array.put(json)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun getStoryUserReq(userId:String,imageId:Int, context: Context, success:(JSONObject)->Unit) {
        var url = "${ip}/image/get/story/user"
        var json = JSONObject()
        json.put("user_id", userId)
        json.put("image_id", imageId)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun insertStoryExpressionReq(userId:String, imageId:Int, expressionDate:String, context: Context, success:(String)->Unit) {
        var url = "${ip}/image/insert/expression"
        var json = JSONObject()
        json.put("user_id", userId)
        json.put("image_id", imageId)
        json.put("expression_date", expressionDate)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun deleteStoryExpressionReq(userId:String, imageId:Int, context: Context, success:(String)->Unit) {
        var url = "${ip}/image/delete/expression"
        var json = JSONObject()
        json.put("user_id", userId)
        json.put("image_id", imageId)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun updateUserOptionReq(userId:String, userOption:String, userOptionData:String, context: Context, success:(String)->Unit) {
        var url = "${ip}/user/update/option"
        var json = JSONObject()
        json.put("user_id", userId)
        json.put("user_option", userOption)
        json.put("user_optiondata", userOptionData)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun updateUserCityReq(userId:String, userOption:String, userOptionData:String, context: Context, success:(String)->Unit) {
        var url = "${ip}/user/update/option/city"
        var json = JSONObject()
        json.put("user_id", userId)
        json.put("user_option", userOption)
        json.put("user_optiondata", userOptionData)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun getUserOptionReq(userId:String, context:Context, success:(JSONObject)->Unit) {
        var url = "${ip}/user/get/option"
        var json = JSONObject()
        json.put("user_id", userId)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun deleteImageReq(imageId:Int, deleteImage:String, context:Context, success:(String) -> Unit) {
        var url = "${ip}/image/delete"
        var json = JSONObject()
        json.put("image_id", imageId)
        json.put("delete_image", deleteImage)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {

            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun getMyStoryImageReq(userId:String, context:Context, success:(JSONObject) -> Unit) {
        var url = "${ip}/image/get/my/story"
        var json = JSONObject()
        json.put("user_id", userId)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
            }) {}
        Volley.newRequestQueue(context).add(request)
    }

    fun sendFCMReq(userId: String, type: String, context:Context) {

        var url = "${ip}/chat/send/fcm"

        var json = JSONObject()
        json.put("user_id", userId)

        var title=""
        var content=""
        if(type=="like") {
            title = "${UserInfo.NICKNAME}님이 좋아요를 눌렀어요!"
            content = "상대의 프로필을 확인해보세요"
        }
        else if(type=="meet") {
            title = "${UserInfo.NICKNAME}님이 연락해요를 눌렀어요!"
            content = "상대의 프로필을 확인해보세요"
        }
        else if(type=="visitprofile"){
            title = "${UserInfo.NICKNAME}님이 프로필을 방문했어요!"
            content = "상대의 프로필을 방문해보세요"
        }
        else if(type=="visitstory"){
            title = "${UserInfo.NICKNAME}님이 스토리를 방문했어요!"
            content = "상대의 스토리를 방문해보세요"
        }
        else if(type=="likestory"){
            title = "${UserInfo.NICKNAME}님이 스토리를 좋아해요!"
            content = "상대의 스토리를 방문해보세요"
        }
        json.put("title",title)
        json.put("content", content)
        json.put("type", type)

        var request = object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
                Log.d("test",it.toString())
            },
            Response.ErrorListener {
            }) {
        }

        Volley.newRequestQueue(context).add(request)
    }

    fun getMyRoomReq(userId: String, context: Context, success: (JSONArray) -> Unit) {
        var url="${ip}/chat/get/my/room"

        var json = JSONObject()
            .put("user_id",userId)

        var array = JSONArray()
            .put(json)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
                Log.d("test","${it}")
            }) {

        }

        Volley.newRequestQueue(context).add(request)
    }

    fun updateToken(userId: String,token: String?,context: Context, success: (JSONObject) -> Unit) {
        var url="${ip}/user/update/token"

        var json=JSONObject()
            .put("user_id",userId)
            .put("token",token)

        Log.d("test","${token}")

        var request = object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
            },
            Response.ErrorListener {
            }) {
        }

        Volley.newRequestQueue(context).add(request)
    }

    fun createRoomReq(userId: String, userNickname: String, context: Context, success: (JSONObject) -> Unit) {
        var url="${ip}/chat/create/room"

        var json=JSONObject()
            .put("room_maker", UserInfo.ID)
            .put("room_partner",userId)
            .put("room_title","${UserInfo.NICKNAME}&${userNickname}")

        var request = object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
                Log.d("test",it.toString())
            }) {
        }

        Volley.newRequestQueue(context).add(request)
    }

    fun updateIntroduce(userId: String, introduceType: String, introduceData:String, context:Context, success: (String) -> Unit) {
        var url="${ip}/user/update/introduce"

        var json=JSONObject()
            .put("user_id",userId)
            .put("introduce_type", introduceType)
            .put("introduce_data", introduceData)


        var request = object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {
            }) {
        }

        Volley.newRequestQueue(context).add(request)
    }

    fun updateRecentGps(userId: String, userRecentGps: String, userRecentTime: String, context:Context, success: (String) -> Unit) {
        var url="${ip}/user/update/recentgps"

        var json=JSONObject()
            .put("user_id",userId)
            .put("user_recentgps", userRecentGps)
            .put("user_recenttime", userRecentTime)


        var request = object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {
            }) {
        }

        Volley.newRequestQueue(context).add(request)
    }

    fun insertChatReq(
        roomId: String,
        userId: String,
        userNickname: String,
        chatPartner: String,
        chatContent: String,
        currentDate: String?,
        context:Context,
        success: (String) -> Unit
    ) {

        val url = "${ip}/chat/insert/chat"

        var json=JSONObject()
            .put("room_id",roomId)
            .put("chat_speaker",userId)
            .put("chat_speaker_nickname",userNickname)
            .put("chat_content",chatContent)
            .put("chat_time",currentDate)
            .put("chat_partner",chatPartner)

        val request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {

            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

    fun checkChatRoom(userId: String, partnerId: String, context: Context, success: (JSONObject?) -> Unit) {
        val url = "${ip}/chat/check/room"

        var json=JSONObject()
            .put("user_id",userId)
            .put("partner_id",partnerId)

        val request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
                if(it is com.android.volley.ParseError)
                    success(null)
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

    fun updateAlarm(
        userId: String,
        alarmType: String,
        alarmState: Boolean,
        context: Context
    ) {

        var url = "${ip}/user/update/alarm"

        var json=JSONObject()
            .put("user_id",userId)
            .put("alarm_type",alarmType)

        if(alarmState) json.put("alarm_state",1)
        else json.put("alarm_state",0)

        val request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
            },
            Response.ErrorListener {
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

    fun readChatReq(roomId: String, chatTime: String?, context: Context, success : (String) -> Unit) {
        val url = "${ip}/chat/read/chat"

        var json=JSONObject()
            .put("room_id",roomId)
            .put("chat_time",chatTime)

        val request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

    fun sendInquireReq(userId: String, sendDate: String, inquireTitle: String, inquireContent: String, inquireType: String, context: Context, success : (String) -> Unit) {
        val url = "${ip}/inquire/send"

        var json=JSONObject()
            .put("user_id",userId)
            .put("send_date", sendDate)
            .put("inquire_title", inquireTitle)
            .put("inquire_content", inquireContent)
            .put("inquire_type", inquireType)

        val request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

    fun getInquireListReq(userId: String, context: Context, success : (JSONArray) -> Unit) {
        val url = "${ip}/inquire/get"

        var json=JSONObject()
            .put("user_id", userId)

        var array = JSONArray()
            .put(json)

        val request=object : JsonArrayRequest(
            Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

    fun updateUpProfileReq(userId : String, upProfileDate: String, context: Context, success : (String) -> Unit) {
        val url = "${ip}/user/upprofile"

        var json=JSONObject()
            .put("user_id", userId)
            .put("upprofile_date", upProfileDate)

        val request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

    fun getProfileReq(userId : String, context: Context, success: (JSONObject) -> Unit){
        val url = "${ip}/user/get/profile"

        var json=JSONObject()
            .put("user_id", userId)

        val request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

    fun sendSMSReq(phone:String, certifyyNum: Int, context: Context, success: (JSONObject) -> Unit) {
        var url = "https://sens.apigw.ntruss.com/sms/v2/services/ncp:sms:kr:260114845161:iloveting/messages"
        val timestamp = System.currentTimeMillis().toString()

        fun makeSignature(): String? {
            val space = " "
            val newLine = "\n"
            val method = "POST"
            val url = "/sms/v2/services/ncp:sms:kr:260114845161:iloveting/messages"
            Log.d("test", timestamp)
            val accessKey = "rKWG41t79UZoLH1w5NXT"
            val secretKey = "nTvn2zShDAXMF3st84keJOQfH9aXutV9tSnsdq8r"
            val message = java.lang.StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString()
            val signingKey =
                SecretKeySpec(secretKey.toByteArray(charset("UTF-8")), "HmacSHA256")
            val mac = Mac.getInstance("HmacSHA256")
            mac.init(signingKey)
            val rawHmac = mac.doFinal(message.toByteArray(charset("UTF-8")))
            return java.util.Base64.getEncoder().encodeToString(rawHmac)
        }

        var signature = makeSignature()

        var array = JSONArray()
        var json1 = JSONObject()
        json1.put("to", phone)
        array.put(json1)

        var json = JSONObject()
        json.put("type", "SMS")
        json.put("from", "01038911100")
        json.put("content", "[아이러브팅] 인증번호 ${certifyyNum} 를 입력해주세요.")
        json.put("messages", array)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
                Log.d("test", "성공")
            },
            Response.ErrorListener {
                Log.d("test", it.toString())
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                var header = HashMap<String, String>()
                header.put("Content-Type", "application/json; charset=utf-8")
                header.put("x-ncp-apigw-timestamp", timestamp)
                header.put("x-ncp-iam-access-key", "rKWG41t79UZoLH1w5NXT")
                header.put("x-ncp-apigw-signature-v2", signature!!)
                return header
            }
        }
        Volley.newRequestQueue(context).add(request)
    }

    fun updateCandyReq(userId : String, candyCount: Int, updateType: String, context: Context, success : (String) -> Unit) {
        val url = "${ip}/user/update/candy"

        var json=JSONObject()
            .put("user_id", userId)
            .put("candy_count", candyCount)
            .put("update_type", updateType)

        val request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

    fun getPartnerExpressionReq(userId : String, partnerId: String, context: Context, success : (JSONObject) -> Unit) {
        val url = "${ip}/expression/get/partner"

        var json=JSONObject()
            .put("user_id", userId)
            .put("partner_id", partnerId)

        val request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }


    fun getBlockingListReq(userId: String, context: Context, success : (JSONArray) -> Unit) {
        val url = "${ip}/user/get/blocking"

        var json=JSONObject()
            .put("user_id", userId)

        var array = JSONArray()
        array.put(json)

        val request=object : JsonArrayRequest(
            Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

    fun getMyBlockingListReq(userId: String, context: Context, success : (JSONObject) -> Unit) {
        val url = "${ip}/user/get/my/blocking"

        var json=JSONObject()
            .put("user_id", userId)


        val request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

    fun insertShowExpressionReq(userId : String, partnerId: String, expressionDate:String, candyCount: Int, context: Context, success : (String) -> Unit) {
        val url = "${ip}/expression/insert/showprofile"

        var json=JSONObject()
            .put("user_id", userId)
            .put("partner_id", partnerId)
            .put("expression_date", expressionDate)
            .put("candy_count", candyCount)

        val request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

    fun userCheckReq(userId : String, context: Context, success : (String) -> Unit) {
        val url = "${ip}/user/check"

        var json=JSONObject()
            .put("user_id", userId)

        val request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("count"))
            },
            Response.ErrorListener {
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

}