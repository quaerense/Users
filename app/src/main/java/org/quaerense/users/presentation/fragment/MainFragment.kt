package org.quaerense.users.presentation.fragment

import android.content.Context
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
import org.quaerense.users.presentation.UsersApp
import org.quaerense.users.presentation.adapter.UserListAdapter
import org.quaerense.users.presentation.viewmodel.MainViewModel
import org.quaerense.users.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    private lateinit var adapter: UserListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as UsersApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
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
            launchUserEditFragment(it.id)
        }
        binding.rvUserList.adapter = adapter
        binding.srlRefreshUserList.setOnRefreshListener(this)
        setupSwipeListener()
        viewModel.userList.observe(viewLifecycleOwner) {
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

    private fun launchUserEditFragment(userId: Int) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .addToBackStack(null)
            .replace(R.id.main_container, UserEditFragment.getInstance(userId))
            .commit()
    }

    override fun onRefresh() {
        binding.srlRefreshUserList.isRefreshing = true
        viewModel.loadData()
        binding.srlRefreshUserList.isRefreshing = false
    }
}