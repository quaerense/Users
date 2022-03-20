package org.quaerense.users.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import org.quaerense.users.R
import org.quaerense.users.databinding.FragmentUserEditBinding
import org.quaerense.users.databinding.FragmentUserInfoBinding
import org.quaerense.users.presentation.viewmodel.UserInfoViewModel

class UserEditFragment : Fragment() {

    private var _binding: FragmentUserEditBinding? = null
    private val binding: FragmentUserEditBinding
        get() = _binding ?: throw RuntimeException("FragmentUserEditBinding is null")

    private var userId = UNDEFINED_ID

    private val viewModel by lazy {
        ViewModelProvider(this)[UserInfoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        viewModel.user.observe(viewLifecycleOwner) {
            Picasso.get().load(it.avatarUrl).into(binding.ivAvatar)
            binding.user = it
        }
        viewModel.getUser(userId)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun parseParams() {
        userId = requireArguments().getInt(USER_ID)
        if (userId == UNDEFINED_ID) {
            Toast.makeText(
                requireContext(),
                getString(R.string.error_user_not_found),
                Toast.LENGTH_SHORT
            ).show()
            requireActivity().onBackPressed()
        }
    }

    companion object {

        private const val USER_ID = "id"
        private const val UNDEFINED_ID = -1

        fun getInstance(userId: Int): UserEditFragment {
            return UserEditFragment().apply {
                arguments = Bundle().apply {
                    putInt(USER_ID, userId)
                }
            }
        }
    }
}