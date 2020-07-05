package com.ilove.ilove.Object

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

object VolleyService {
    val ip= "http://18.217.130.157:3000"

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

        var request=object : JsonArrayRequest(
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


}