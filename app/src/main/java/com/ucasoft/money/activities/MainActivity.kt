package com.ucasoft.money.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.ucasoft.money.R
import com.ucasoft.money.fragments.AccountFragment
import com.ucasoft.money.fragments.LocationsFragment
import com.ucasoft.money.fragments.MainFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        onNavigationItemSelected(nav_view.menu.findItem(R.id.nav_main))
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment : Fragment? = null
        when (item.itemId) {
            R.id.nav_main -> {
                fragment = MainFragment.newInstance()
            }
            R.id.nav_accounts -> {
                fragment = AccountFragment.newInstance()
            }
            R.id.nav_transactions -> {

            }
            R.id.nav_locations -> {
                fragment = LocationsFragment.newInstance()
            }
            R.id.nav_recipients -> {

            }
        }

        val manager = supportFragmentManager
        if (fragment != null) {//// TODO Only before all actions will be finished!
            manager.beginTransaction().replace(R.id.content, fragment).commit()
        }

        title = item.title
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
