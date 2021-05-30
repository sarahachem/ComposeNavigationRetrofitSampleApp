package com.example.dogs

import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.dogs.apis.ApiBuilders
import com.example.dogs.databinding.ActivityDogBreedsBinding
import com.example.dogs.ui.BreedsFragmentDirections
import com.example.dogs.ui.DogsViewModel
import com.example.dogs.ui.DogsViewModelFactory
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
class DogBreedsActivity : AppCompatActivity() {

    private lateinit var viewModel: DogsViewModel
    private val binding by lazy { ActivityDogBreedsBinding.inflate(layoutInflater) }
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViewModel()
        lifecycleScope.launch {
            viewModel.fetchBreeds()
        }
    }

    private fun initViewModel() {
        val factory = DogsViewModelFactory(
            application, ApiBuilders.getDogsApi(),
            (this.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager)
        )
        viewModel = ViewModelProvider(this, factory).get(DogsViewModel::class.java)
        viewModel.errorLiveData.observe(this) {
            it?.takeIf { it.isNotEmpty() }?.let { error ->
                Toast.makeText(this@DogBreedsActivity, error, Toast.LENGTH_LONG).show()
            }
        }
        viewModel.dogBreedsLiveData.observe(this) {
            it?.takeIf { it.isNotEmpty() }?.let { navController.navigate(R.id.breeds_fragment) }
        }
        viewModel.dogImagesLiveData.observe(this) {
            it?.takeIf { it.second?.isNotEmpty() == true }
                ?.let {
                    if (navController.currentDestination?.id == R.id.breed_images_fragment) {
                        refreshCurrentFragment(it.first)
                    } else {
                        navController.navigate(BreedsFragmentDirections.actionToImages(it.first))
                    }
                }
        }
    }

    private fun refreshCurrentFragment(name: String) {
        val id = navController.currentDestination?.id
        navController.popBackStack(id!!, true)
        navController.navigate(BreedsFragmentDirections.actionToImages(name))
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.breeds_fragment -> finish()
            else -> if (!navController.popBackStack()) super.onBackPressed()
        }
    }

}