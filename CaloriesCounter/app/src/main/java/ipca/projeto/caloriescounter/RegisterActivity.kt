package ipca.projeto.caloriescounter


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import ipca.projeto.caloriescounter.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var binding: ActivityRegisterBinding

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.buttonReg.setOnClickListener {
            val email = binding.editTextTextEmailReg.text.toString()
            val password = binding.editTextTextPasswordReg.text.toString()
            val confirmPassword = binding.editTextTextPasswordConf.text.toString()


            if (password != confirmPassword){
                Toast.makeText(
                    baseContext,
                    "Passwords do not match.",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }

            if (!password.isPasswordValid()){
                Toast.makeText(
                    baseContext,
                    "Password must have at least 6 chars.",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }

            if (!email.isValidEmail()){
                Toast.makeText(
                    baseContext,
                    "Email is not valid.",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }

    }
}