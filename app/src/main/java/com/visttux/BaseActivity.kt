package com.visttux

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.visttux.btcchart.R
import com.visttux.btcchart.di.ActivityModule
import com.visttux.btcchart.di.DaggerActivityComponent
import kotlinx.android.synthetic.main.base_activity.*

class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjection()
        setContentView(R.layout.base_activity)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavHostFragment.findNavController(nav_host_fragment).navigateUp()
    }

    private fun initializeInjection() {
        DaggerActivityComponent
            .builder()
            .applicationComponent(App.instance.component)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }
}
