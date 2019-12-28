package com.example.foodrecipesdemokotlin.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodrecipesdemokotlin.FADE_DURATION
import com.example.foodrecipesdemokotlin.domain.Recipe
import com.example.foodrecipesdemokotlin.domain.RecipeList
import com.example.foodrecipesdemokotlin.network.NetworkRecipesContainer
import com.example.foodrecipesdemokotlin.network.asDomainModel
import com.example.foodrecipesdemokotlin.repository.RecipeRepository
import com.example.foodrecipesdemokotlin.util.Konstant
import com.example.foodrecipesdemokotlin.database.getDatabase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : BaseViewModel(application) {

    private val repository = RecipeRepository(getDatabase(application))


    //Main Activity
    fun searchQuery(searchQuery: String) {
        _searchView.value = searchQuery
        _recipeList.value = ArrayList()
        searchRecipes(searchQuery)
    }

    fun setTitle(title: String) {
        _title.value = title
    }


    //CATEGORY FRAGMENT
    private
    val _category = MutableLiveData<String>()
    val category: LiveData<String>
        get() = _category

    fun setCategory(string: String) {
        _category.value = string
        setStatus(Status.LOADING)
    }

    fun completedNavigationToRecipeList() {
        _category.value = null
    }


    //RECIPE LIST FRAGMENT
    private val _recipeList = MutableLiveData<List<RecipeList>>()
    val recipeList: LiveData<List<RecipeList>>
        get() = _recipeList

    init {
        _recipeList.value = ArrayList()
    }

    private fun getRecipeList(query: String, page: String = "1") {
        Log.i("SharedViewModel", "getRecipeList: called \nQuery:$query\nPage: $page")
        viewModelScope.launch {
            if (status.value != Status.LOADING) setStatus(Status.LOADING)
            val getDeferredRecipes: Deferred<NetworkRecipesContainer> =
                repository.getRecipeList(query = query, page = page)
            try {
                setStatus(Status.LOADING)
                delay(FADE_DURATION)
                val listResult = getDeferredRecipes.await()
                Log.i("SharedViewModel", "recipe count: ${listResult.count}")
                if (listResult.count == 0) {
                    setStatus(Status.NO_RESULTS)
                    _recipeList.value = listOf(RecipeList("", query, Konstant.NO_RESULTS, 0f, ""))
                } else {
                    setStatus(Status.DONE)
                    _recipeList.value = listResult.asDomainModel()
                }

            } catch (e: Exception) {
                Log.i("SharedViewModel", "error message: ${e.message}")
                setStatus(Status.ERROR)
                //return cached data if possible
                _recipeList.value = ArrayList()
            }
        }
    }

    fun searchRecipes(query: String, page: String = "1") {
        _query.value = query
        _pageNumber.value = page
        getRecipeList(query, page)
        setTitle(query)
    }

    fun searchNextPage() {
        getRecipeList(_query.value!!, _pageNumber.value!!)
    }

    fun completedNavigationToDetailList() {

    }

    fun clearList() {
        _recipeList.value = ArrayList()
        setStatus(Status.NONE)
    }


    //Recipe Detail Fragment
    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe>
        get() = _recipe


    private fun getRecipe(recipeId: String) {
        viewModelScope.launch {
            val deferredRecipe = repository.getRecipe(recipeId)
            try {
                val recipe = deferredRecipe.await()
                _recipe.value = recipe.networkRecipe.asDomainModel()

            } catch (e: Exception) {
                Log.i("SharedViewModel", "getRecipe: ${e.message}")
                setStatus(Status.ERROR)
            }
        }
    }

    fun setRecipeId(recipeId: String) {
        getRecipe(recipeId)
    }

    fun loadFromCache(query: String, page: String = "1") {
        println(repository.loadFromCache(query, page))
    }
}