package org.quaerense.users.presentation.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import org.quaerense.users.R
import org.quaerense.users.databinding.FragmentUserEditBinding
import org.quaerense.users.presentation.viewmodel.UserEditViewModel

class UserEditFragment : Fragment() {

    private var _binding: FragmentUserEditBinding? = null
    private val binding: FragmentUserEditBinding
        get() = _binding ?: throw RuntimeException("FragmentUserEditBinding is null")

    private var userId = UNDEFINED_ID

    private var imageUri: Uri? = null

    private lateinit var photoResultLauncher: ActivityResultLauncher<Intent>

    private val viewModel by lazy {
        ViewModelProvider(this)[UserEditViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
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
        registerActivityResult()
        observeViewModel()
        addTextChangeListeners()
        setOnClickListeners()
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

    private fun setOnClickListeners() {
        binding.fabChangeAvatar.setOnClickListener {
            createIntentForActivityResult()
        }
        binding.fabSaveUser.setOnClickListener {
            editUser()
        }
    }

    private fun observeViewModel() {
        viewModel.getUser(userId)
        viewModel.user.observe(viewLifecycleOwner) {
            Picasso.get().load(it.avatarUrl)
                .placeholder(R.drawable.ic_user_icon)
                .error(R.drawable.ic_user_icon)
                .into(binding.ivAvatar)
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            requireActivity().onBackPressed()
        }
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun addTextChangeListeners() {
        binding.etFirstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) =
                viewModel.resetErrorInputFirstName()

            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.etLastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) =
                viewModel.resetErrorInputLastName()

            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) =
                viewModel.resetErrorInputEmail()

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun registerActivityResult() {
        photoResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                    imageUri = intent?.data
                    Picasso.get()
                        .load(imageUri)
                        .error(R.drawable.ic_user_icon)
                        .into(binding.ivAvatar)
                }
            }
    }

    private fun createIntentForActivityResult() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        photoResultLauncher.launch(photoPickerIntent)
    }

    private fun editUser() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()
        viewModel.editUser(firstName, lastName, email, imageUri)
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