package com.jesusc24.xroadsthroughthecastle.ui.singUp

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.jesusc24.xroadsthroughthecastle.databinding.ActivitySignUpBinding
import com.jesusc24.xroadsthroughthecastle.utils.CommonUtils
import java.io.FileNotFoundException

class SignUpActivity : AppCompatActivity() {
    private val signUpViewModel = SignUpViewModel()
    private lateinit var binding: ActivitySignUpBinding
    companion object {
        lateinit var context : Context
        var encodedImage : String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = applicationContext

        //Inicializar Data Binding
        binding.viewmodel = signUpViewModel
        //Para que aplique los cambios
        binding.executePendingBindings()
        //Para que Data Binding conozca el ciclo de vida del Fragment
        binding.lifecycleOwner = this
        binding.layoutImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            pickImage.launch(intent)
        }

        //Creamos el observador
        signUpViewModel.state.observe(this) {
            when (it) {
                is State.NameEmptyError -> setNameEmpty(it.message)
                is State.EmailError -> setEmailError(it.message)
                is State.PasswordError -> setPasswordError(it.message)
                is State.ConfirmPasswordError -> setConfirmPasswordError(it.message)
                is State.AuthError -> setToast(it.message)
                is State.Loading -> setProgressBar(it.process)
                is State.Success -> onSuccess(it.message)
            }
        }
    }

    private fun setNameEmpty(message: Int) {
        binding.tilUser.error = resources.getString(message)
    }

    private fun setPasswordError(message: Int) {
        binding.tilPassword.error = resources.getString(message)
    }

    private fun setConfirmPasswordError(message: Int) {
        binding.tilConfirmPassword.error = resources.getString(message)
    }

    private fun setEmailError(message: Int) {
        binding.tilEmail.error = resources.getString(message)
    }

    private fun setProgressBar(process: Boolean) {
        if(process) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun setToast(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private val pickImage = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            if (result.data != null) {
                val imageUri: Uri? = result.data!!.data
                try {
                    if(imageUri != null) {
                        val inputStream = contentResolver.openInputStream(imageUri)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.imageProfile.setImageBitmap(bitmap)
                        binding.txtAddImage.visibility = View.GONE
                        encodedImage = CommonUtils.encodeImage(bitmap)
                    }
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun onSuccess(message: Int) {
        finish()
    }

}

