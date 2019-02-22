package com.wafer.toy.githubclient.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.wafer.toy.githubclient.BuildConfig
import com.wafer.toy.githubclient.R
import com.wafer.toy.githubclient.application.Constants
import com.wafer.toy.githubclient.model.network.AuthRequest
import com.wafer.toy.githubclient.network.Api
import com.wafer.toy.githubclient.network.ApiManager
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_two_factor_auth_input.*
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException

class TwoFactorAuthInput : AppCompatActivity() {

    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_factor_auth_input)

        input_2fa_toolbar.setNavigationOnClickListener { onBackPressed() }

        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")

        val watcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (two_factor_code.isErrorEnabled) {
                    two_factor_code.error = null
                    two_factor_code.isErrorEnabled = false
                }
            }

        }

        two_factor_code.editText?.addTextChangedListener(watcher)

        two_fa_code_confirm.setOnClickListener {
            val twoFACode = two_factor_code.editText?.text.toString()

            if (twoFACode.isBlank())
                two_factor_code.error = getString(R.string.empty_two_fa_code)
            else {
                val observer = object : Observer<ResponseBody> {
                    override fun onComplete() {
                        setResult(Constants.RESULT_SUCCESS)
                        finish()
                    }

                    override fun onSubscribe(d: Disposable) {
                        progressBar2FA.visibility = View.VISIBLE
                    }

                    override fun onNext(it: ResponseBody) {
                        val token = ApiManager.gson
                                .fromJson(it.string(), JsonObject::class.java)["token"].asString

                        ApiManager.token = token
                        ApiManager.pref.edit().putString(Constants.PREF_OAUTH_TOKEN, token)
                                .commit() // ensure token is stored
                    }

                    override fun onError(it: Throwable) {
                        progressBar2FA.visibility = View.GONE

                        val errorMessage = when (it) {
                            is HttpException ->
                                it.message() + "\n" + it.response().errorBody()!!.string()
                            is IOException ->
                                "Network Error"
                            else ->
                                "Error"
                        }

                        Log.d("Two Factor Error", errorMessage)

                        Toast.makeText(this@TwoFactorAuthInput, errorMessage, Toast.LENGTH_LONG)
                                .show()
                    }

                }

                ApiManager.createService(Api::class.java, username, password)
                        .getAuthCode(
                                AuthRequest(Constants.APP_NAME,
                                        BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET,
                                        Constants.DEFAULT_SCOPE)
                                , twoFACode)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer)
            }
        }
    }
}
