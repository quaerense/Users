package org.quaerense.users.presentation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import org.quaerense.users.R

@BindingAdapter("errorInputFirstName")
fun bindErrorInputFirstName(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_input_first_name)
    } else {
        null
    }

    textInputLayout.error = message
}

@BindingAdapter("errorInputLastName")
fun bindErrorInputLastName(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_input_last_name)
    } else {
        null
    }

    textInputLayout.error = message
}

@BindingAdapter("errorInputEmail")
fun bindErrorInputEmail(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_input_email)
    } else {
        null
    }

    textInputLayout.error = message
}