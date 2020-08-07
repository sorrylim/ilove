package com.ilove.ilove.Class

import android.app.Activity
import android.content.Context
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails

class BillingModule(context: Context) : BillingProcessor.IBillingHandler{
    val licenseKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhIn5hyizykQWK1zohE4ta6ANE027B/cED+0p2gwNtEXmuo3Z0/H6rnIFsLqktadmwoarRAaDPENx1YdL9cXbAos2bvoRyoLR5GSMvJTSTh9fzJOkkv33WBY23wSNFEEp1uGYaM84rJqqw0xgb2dR5+/Ei3MxiLaIA1jBf3BlWy0R1WvJQzFDIwQP0xrOTdzegoKM0dg67x9CU+TYSTSzL55drJV8+UkZfIhHQfVPc/9bdzIulDByDoHEaTz1yjemkE5K54ccom53xSrcWj4jCtwLM7yiVQaoRVvvdMCsfR6Sldy1DuBN63ytQymvz5hhq07spxKfJwVs2GBzQHo0xwIDAQAB"
    lateinit var context : Context
    lateinit var bp : BillingProcessor

    init{
        this.context = context
        this.bp = BillingProcessor(context, licenseKey, this)
    }

    fun purchaseProduct(itemId: String) {
        bp.purchase(context as Activity, itemId)
    }

    fun releaseBillingProcessor() {
        if(bp != null) {
            bp.release()
        }
    }


    override fun onProductPurchased(productId: String, details: TransactionDetails?) {

    }

    override fun onPurchaseHistoryRestored() {

    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {
        if(errorCode != com.anjlab.android.iab.v3.Constants.BILLING_RESPONSE_RESULT_USER_CANCELED)
        {

        }
    }

    override fun onBillingInitialized() {

    }



}