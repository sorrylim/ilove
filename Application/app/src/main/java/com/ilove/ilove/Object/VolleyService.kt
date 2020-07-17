package com.ilove.ilove.Object

import android.content.Context
import android.util.Log
import android.graphics.Bitmap
import com.android.volley.Request
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ilove.ilove.Class.UserInfo
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Method

object VolleyService {
    val ip= "http://18.217.130.157:3000"

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
                else if (userPassword == it.getString("user_Password"))
                    result.put("code", 3)
                success(result)
            }
            , Response.ErrorListener {
                Log.d("test",it.toString())
                if (it is com.android.volley.TimeoutError) {
                    Log.d("test", "TimeoutError")
                    result.put("code", 0)
                } else if (it is com.android.volley.ParseError) {
                    Log.d("test", "ParserError")
                    result.put("code", 1)
                }
                success(result)
            }
        ) {
        }
        //요청을 보내는 부분
        Volley.newRequestQueue(context).add(request)
    }

    fun getExpressionCountReq(userId:String, context: Context, success: (JSONObject?) -> Unit) {
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

    fun getCountViewReq(userId:String, context: Context, success:(JSONObject?) -> Unit) {
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

    fun getStoryImageReq(userId:String, imageUsage:String, context: Context, success:(JSONArray)->Unit) {
        var url = "${ip}/image/get/story"
        var json = JSONObject()
        json.put("user_id", userId)
        json.put("image_usage", imageUsage)

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

    fun deleteImageReq(imageId:Int, context:Context, success:(String) -> Unit) {
        var url = "${ip}/image/delete"
        var json = JSONObject()
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

        var title="좋아요 알림"
        json.put("title",title)
        var content="${UserInfo.NICKNAME}님이 좋아요를 눌렀습니다"
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

    fun getMyChatRoom(userId: String, context: Context, success: (JSONArray) -> Unit) {
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
        success: (String) -> Unit
    ) {

    }
}