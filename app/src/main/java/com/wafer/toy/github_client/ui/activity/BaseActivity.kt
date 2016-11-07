package com.wafer.toy.github_client.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * The BaseActivity class
 * Please put more info here.
 * @author wafer
 * @since 16/10/10 23:25
 */

abstract class BaseActivity : AppCompatActivity() {

    abstract fun init()
    abstract fun getLayoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        init()
    }

    protected fun goToActivity(clazz: Class<*>, bundle: Bundle? = null, vararg flags: Int = intArrayOf()) {
        val intent: Intent = Intent()
        intent.setClass(this, clazz)

        if (bundle != null) {
            intent.putExtras(bundle)
        } else if (flags.isNotEmpty()) {
            for (flag in flags) {
                intent.addFlags(flag)
            }
        }
        startActivity(intent)
    }

    protected fun getExtraBundles(): Bundle? {
        return this.intent?.extras
    }
}
