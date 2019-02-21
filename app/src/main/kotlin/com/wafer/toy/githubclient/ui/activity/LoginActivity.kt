package com.wafer.toy.githubclient.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class LoginActivity : AppCompatActivity() {

    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_toolbar.setNavigationOnClickListener { onBackPressed() }

        val onTextChangeListener: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (login_username.isErrorEnabled) {
                    login_username.error = null
                    login_username.isErrorEnabled = false
                }

                if (login_password.isErrorEnabled) {
                    login_username.error = null
                    login_username.isErrorEnabled = false
                }
            }
        }

        login_username.editText?.addTextChangedListener(onTextChangeListener)
        login_password.editText?.addTextChangedListener(onTextChangeListener)

        login_button.setOnClickListener {
            val username = login_username.editText?.text.toString()
            val password = login_password.editText?.text.toString()

            if (username.isBlank())
                login_username.error = getString(R.string.empty_username)
            if (password.isBlank())
                login_password.error = getString(R.string.empty_password)
            else {
                val observer = object : Observer<ResponseBody> {
                    override fun onComplete() {
                        setResult(Constants.RESULT_SUCCESS)
                        finish()
                    }

                    override fun onSubscribe(d: Disposable) {
                        progressBar.visibility = View.VISIBLE
                    }

                    override fun onNext(t: ResponseBody) {
                        val token = ApiManager.gson
                                .fromJson(t.string(), Properties::class.java)
                                .getProperty("token")

                        val pref = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

                        pref.edit().putString(Constants.PREF_OAUTH_TOKEN, token)
                                .commit() // ensure token is stored
                    }

                    override fun onError(e: Throwable) {
                        progressBar.visibility = View.GONE

                        when (e) {
                            is HttpException -> {
                                Log.d("Login Error", e.message())

                                Log.d("Login ErrorBody", e.response().errorBody().toString())

                                if (e.code() == 401 &&
                                        e.response().headers()["X-GitHub-OTP"]
                                                ?.contains("required") != false) {

                                    val intent = Intent(this@LoginActivity, TwoFactorAuthInput::class.java)

                                    intent.putExtra("username", username)
                                    intent.putExtra("password", password)

                                    startActivityForResult(intent, Constants.REQUEST_2FA_CODE)
                                } else {
                                    Toast.makeText(this@LoginActivity, e.message(), Toast.LENGTH_LONG)
                                            .show()
                                }
                            }

                            is IOException ->
                                Toast.makeText(this@LoginActivity, "Network Interrupt", Toast.LENGTH_LONG)
                                        .show()
                        }
                    }
                }

                ApiManager.createService(Api::class.java, username, password)
                        .getAuthCode(
                                AuthRequest(getString(R.string.app_name),
                                        BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET,
                                        Constants.DEFAULT_SCOPE)
                                , null)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.REQUEST_2FA_CODE && resultCode == Constants.RESULT_SUCCESS) {
            setResult(Constants.RESULT_SUCCESS)
            finish()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
