package com.renhard.contactme.module.main.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.renhard.contactme.R
import com.renhard.contactme.databinding.ContactListActivityBinding
import com.renhard.contactme.module.main.adapter.MainAdapter
import com.renhard.contactme.module.main.viewmodel.ContactListViewModel
import com.renhard.contactme.repository.model.Status
import com.renhard.contactme.utils.factory.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactListActivity: AppCompatActivity() {

    private lateinit var binding: ContactListActivityBinding
    private lateinit var mainAdapter: MainAdapter
    lateinit var viewModel: ContactListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ContactListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        mainAdapter = MainAdapter()
        val layoutManager = LinearLayoutManager(this)
        binding.rvHome.layoutManager = layoutManager
        binding.rvHome.setHasFixedSize(true)
        binding.rvHome.adapter = mainAdapter

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[ContactListViewModel::class.java]

        // Call API
        fetchData()
    }

    private fun fetchData() {
        val fetchUsersData = viewModel.loadUsersContact()
        fetchUsersData.observe(this) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    binding.progressLoader.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {
                    binding.progressLoader.visibility = View.GONE
                    resource.data?.let {
                        viewModel.dataUsers = it
                        lifecycleScope.launch(Dispatchers.Main) {
                            val pagingData = PagingData.from(viewModel.dataUsers)
                            mainAdapter.submitData(pagingData)
                            mainAdapter.notifyDataSetChanged()
                        }

                        // Add thumbnails
                        val thumbnails = resources.getStringArray(R.array.image_thumbnails)
                        viewModel.dataUsers.forEachIndexed { index, contactItemModel ->
                            viewModel.dataUsers[index].thumbnail = thumbnails[index]
                        }
                    }
                }

                Status.ERROR -> {
                    binding.progressLoader.visibility = View.GONE
                    binding.tvTextEmpty.text = resource.message
                }
            }
        }
    }
}