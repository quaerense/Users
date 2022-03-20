package org.quaerense.users.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.quaerense.users.R
import org.quaerense.users.databinding.FragmentMainBinding
import org.quaerense.users.presentation.adapter.UserListAdapter
import org.quaerense.users.presentation.viewmodel.MainViewModel

class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    private lateinit var adapter: UserListAdapter
    val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserListAdapter()
        adapter.onUserClickListener = {
            launchUserInfoFragment(it.id)
        }
        binding.rvUserList.adapter = adapter
        binding.srlRefreshUserList.setOnRefreshListener(this)
        setupSwipeListener()
        viewModel.getUserListUseCase().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSwipeListener() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val user = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteUser(user)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvUserList)
    }

    private fun launchUserInfoFragment(userId: Int) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.main_container, UserInfoFragment.getInstance(userId))
            .commit()
    }

    override fun onRefresh() {
        binding.srlRefreshUserList.isRefreshing = true
        viewModel.loadData()
        binding.srlRefreshUserList.isRefreshing = false
    }
}