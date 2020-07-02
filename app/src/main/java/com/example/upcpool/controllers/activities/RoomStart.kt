package com.example.upcpool.controllers.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upcpool.R
import com.example.upcpool.database.RoomDB
import com.example.upcpool.entity.User
import com.example.upcpool.models.LoginResponseDetails
import com.example.upcpool.models.Token
import com.example.upcpool.network.RoomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class RoomStart : AppCompatActivity() {

    lateinit var image : ImageView
    lateinit var login : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        var token = RoomDB.getInstance(this).getTokenDAO().getLastToken()
        if (token != null)
        {

            Log.d("ModelToken", "id: " + token.id.toString() + " token: " + token.token)
            val intento = Intent(this, MainActivity::class.java)
            intento.putExtra("token",token.token)
            startActivity(intento)
        }
        super.onCreate(savedInstanceState)

        setContentView(R.layout.home_main)

        login = findViewById(R.id.loginButton)
        val textTIU : EditText = findViewById(R.id.editTIU)
        val textPassword : EditText = findViewById(R.id.editPassword)
        var user = User()
        login.setOnClickListener{
            login.isClickable = false;
            user.userCode = textTIU.text.toString()
            user.password = textPassword.text.toString()
            Login(this,user)

        }

    }

    private fun Login(context: Context, user: User) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://upc-pool-ferluisxd.cloud.okteto.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val roomService: RoomService
        roomService = retrofit.create(RoomService::class.java)
        val request = roomService.login(user)

        request.enqueue(object: Callback<LoginResponseDetails>
        {
            override fun onFailure(call: Call<LoginResponseDetails>, t: Throwable) {
                Log.d("Activity Fail", "error: $t")
            }

            override fun onResponse(
                call: Call<LoginResponseDetails>,
                response: Response<LoginResponseDetails>
            ) {
                login.isClickable = true;
                if (response.isSuccessful) {
                    Log.d("Response", "message: " + response.code().toString())
                    if (response.code() == 201)
                    {
                        Log.d("Token", "message: " + response.body()!!.token)
                        var tk = response.body()!!.token
                        var token = Token(token = tk)
                        Log.d("ModelToken", "id: " + token.id.toString() + " token: " + token.token)
                        RoomDB.getInstance(context).getTokenDAO().insertToken(token)
                        val intento = Intent(context, MainActivity::class.java)
                        intento.putExtra("token",tk)
                        startActivity(intento)
                    }
                }

                else{
                    Log.d("Fail", "message: " + response.code().toString() + ' '+ response.errorBody())
                }
            }
        })

    }

}
