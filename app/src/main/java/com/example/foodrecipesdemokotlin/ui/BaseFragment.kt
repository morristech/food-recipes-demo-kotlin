package com.example.foodrecipesdemokotlin.ui


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.foodrecipesdemokotlin.ui.viewmodels.SharedViewModel

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment : Fragment() {
    lateinit var statusListener: StatusListener
    lateinit var viewModel: SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProviders.of(this)[SharedViewModel::class.java]
        }
    }


    override fun onAttach(context: Context) {
        Log.i("BaseFragment", "onAttach: called")
        super.onAttach(context)
        try {
            statusListener = context as StatusListener
        } catch (e: Exception) {
            Log.i("BaseFragment", "onAttach: Must implement interface ${e.message} ")
        }
    }

    open fun searchQuery(query: String, page: String = "1") {
        viewModel.searchRecipes(query, page)
    }
}
