package com.ilove.ilove.Object

import android.content.Context
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

    fun getSendLikeUserReq(userId:String, context: Context, success: (JSONArray?) -> Unit) {
        var url = "${ip}/expression/get/send/like"
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

    fun getReceiveLikeUserReq(userId:String, context: Context, success: (JSONArray?) -> Unit) {
        var url = "${ip}/expression/get/receive/like"
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

    fun getEachLikeUser1Req(userId:String, context: Context, success: (JSONArray?) -> Unit) {
        var url = "${ip}/expression/get/each/like1"
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

    fun getEachLikeUser2Req(userId:String, context: Context, success: (JSONArray?) -> Unit) {
        var url = "${ip}/expression/get/each/like2"
        var json = JSONObject()
        json.put("user_id", userId)

        var array = JSONArray()

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

    fun getUserListReq(gender: String, context: Context, success: (JSONArray?) -> Unit) {
        var url = "${ip}/user/get/list"
        var json = JSONObject()
        json.put("user_gender", gender)

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
}