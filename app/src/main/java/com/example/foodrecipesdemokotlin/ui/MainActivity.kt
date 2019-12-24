package com.example.foodrecipesdemokotlin.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.foodrecipesdemokotlin.BaseActivity
import com.example.foodrecipesdemokotlin.R
import com.example.foodrecipesdemokotlin.ui.recipe_list.RecipeListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_layout.*
import kotlinx.android.synthetic.main.loading_layout.load_layout

class MainActivity : BaseActivity() {

    private lateinit var mSearchView: androidx.appcompat.widget.SearchView
    private lateinit var fragment: Fragment
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.fragment)
    }

    private fun initSearchView(searchView: androidx.appcompat.widget.SearchView) {

        mSearchView.isSubmitButtonEnabled = true
        mSearchView.queryHint = getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                setSearch(query, searchView)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setSearch(query: String?, view: View) {
        val text = query?.capitalize()
        loading_text.text = text ?: getString(R.string.loading_text)
        // TODO("24/12/2019 - temporary to test purposes")
        (fragment as RecipeListFragment).searchQuery(query!!)
        closeKeyboard(view)
        supportActionBar?.collapseActionView()
        this.title = text
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        mSearchView =
            menu?.findItem(R.id.app_bar_search)?.actionView as androidx.appcompat.widget.SearchView
        initSearchView(mSearchView);
        return true
    }

    override fun closeKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun loadingText(string: String) {
        loading_text.text = string
    }

    // TODO("24/12/2019 - enable search")

    override fun onBackPressed() {
        if (load_layout.visibility == View.VISIBLE) {
            load_layout.visibility = View.INVISIBLE
            frame_layout.visibility = View.VISIBLE
        }
        super.onBackPressed()
    }

}
