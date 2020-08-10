package com.ilove.ilove.Class

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.billingclient.api.*

class BillingModule(context: Context) : PurchasesUpdatedListener{
    lateinit var context : Context
    lateinit var mBillingClient : BillingClient
    lateinit var mConsumeListener: ConsumeResponseListener

    val skuID10 = "candy10"
    lateinit var skuDetails10 : SkuDetails

    init{
        this.context = context
        this.mBillingClient = BillingClient.newBuilder(context).setListener(this).enablePendingPurchases().build()
        mBillingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult?) {
                if(billingResult?.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.d("test", "구글 결제 서버에 접속을 성공하였습니다.")
                    var skuList = ArrayList<String>()

                    skuList.add("candy10")

                    val params = SkuDetailsParams.newBuilder().setSkusList(skuList).setType(BillingClient.SkuType.INAPP).build()

                    mBillingClient.querySkuDetailsAsync(params, object : SkuDetailsResponseListener {
                        override fun onSkuDetailsResponse(billingResult: BillingResult?, skuDetailsList: MutableList<SkuDetails>?) {
                            if(billingResult?.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                                for(skuDetails in skuDetailsList) {
                                    if(skuID10.equals(skuDetails.sku)) {
                                        skuDetails10 = skuDetails
                                    }
                                }
                            }
                        }
                    })
                }
                else {
                    Log.d("test", "구글 결제 서버 접속에 실패하였습니다.\n오류코드: " + billingResult?.getResponseCode());
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.d("test", "구글 결제 서버와 접속이 끊어졌습니다.");
            }


        })

        mConsumeListener = object : ConsumeResponseListener {
            override fun onConsumeResponse(billingResult: BillingResult?, purchaseToken: String?) {
                if (billingResult?.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d("test", "상품을 성공적으로 소모하였습니다. 소모된 상품 => " + purchaseToken);
                    return;
                } else {
                    Log.d("test", "상품 소모에 실패하였습니다. 오류코드 (" + billingResult?.getResponseCode() + "), 대상 상품 코드: " + purchaseToken);
                    return;
                }
            }
        }
    }

    fun doBillingFlow(skuDetails: SkuDetails) {
        val flowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails).build()
        val responseCode = mBillingClient.launchBillingFlow(context as Activity, flowParams);
        Log.d("test", "$responseCode")
    }

    override fun onPurchasesUpdated(billingResult: BillingResult?, purchases: MutableList<Purchase>?) {
        if(billingResult?.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            Log.d("test", "결제에 성공하였습니다.")
            for(i in purchases) {
                val consumeParams = ConsumeParams.newBuilder().setPurchaseToken(i.purchaseToken).build()
                mBillingClient.consumeAsync(consumeParams, mConsumeListener)
            }
        }
        else if(billingResult?.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Log.d("test", "사용자에 의해 구매가 취소되었습니다.")
        }
        else {
            Log.d("test", "결제가 취소되었습니다." + billingResult?.responseCode)
        }
    }
}