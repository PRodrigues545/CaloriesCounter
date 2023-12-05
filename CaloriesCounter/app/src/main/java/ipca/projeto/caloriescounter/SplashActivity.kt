package ipca.projeto.caloriescounter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var auth = Firebase.auth

        lifecycleScope.launch(Dispatchers.IO) {
            delay(1000)
            lifecycleScope.launch(Dispatchers.Main) {
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    val intent = Intent(this@SplashActivity,  MainActivity::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this@SplashActivity,  LoginActivity::class.java)
                    startActivity(intent)
                }
                finish()
            }
        }
    }
}