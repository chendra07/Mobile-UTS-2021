package umn.ac.id.chendradewangga_00000029610_if633_el_uts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login_page.*

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        btnlogin.setOnClickListener {
            if(username.text.toString() == "uasmobile" && password.text.toString().equals("uasmobilegenap")){
                val intent = Intent(this, MusicList::class.java)
                startActivity(intent)
                Toast.makeText(this, "Welcome!", Toast.LENGTH_LONG).show()
            }else if(username.text.isEmpty() || password.text.isEmpty()){
                Toast.makeText(this, "password or username are still empty", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "wrong password or username", Toast.LENGTH_LONG).show()
            }
        }

    }
}