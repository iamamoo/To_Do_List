package com.app.todolist

import android.content.Context
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.todolist.databinding.ActivitySettingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    private var editPermission: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        setSupportActionBar(binding.settingsToolbar)
        supportActionBar?.title = "Settings"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val data = this@SettingActivity.getSharedPreferences("allowEdit", Context.MODE_PRIVATE)
        val userEmail = data.getString("email", "")
        val userName = data.getString("username", "")
        val isAllowed = data.getBoolean("isAllowEdit", false)

        if (userEmail!!.isNotEmpty() && userName!!.isNotEmpty()) {
            binding.yourEmail.setText(userEmail)
            binding.yourName.setText(userName)
            binding.allowedit.isChecked = isAllowed
        } else {
            binding.yourEmail.setText("")
            binding.yourName.setText("")
            binding.allowedit.isChecked = isAllowed
        }


        binding.allowedit.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                buttonView.isChecked = true
                editPermission = isChecked
                Log.d("Button Clicked", this.editPermission.toString())
            } else {
                buttonView.isChecked = false
                editPermission = false
                Log.d("Button Clicked", this.editPermission.toString())
            }
        }


        binding.updateSettings.setOnClickListener {
            coroutineScope.launch {

                val name = binding.yourName.text.toString()
                val email = binding.yourEmail.text.toString()

                if (name != "" && email != "" && name.isNotEmpty() && email.isNotEmpty()) {
                    if (editPermission) {
                        with(data.edit()) {
                            putString("email", email)
                            putString("username", name)
                            putBoolean("isAllowEdit", true)
                                .apply()
                        }
                        Toast.makeText(
                            this@SettingActivity,
                            "Details Saved",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        editPermission = false
                        with(data.edit()) {
                            putString("email", email)
                            putString("username", name)
                            putBoolean("isAllowEdit", false)
                                .apply()
                        }
                        Toast.makeText(
                            this@SettingActivity,
                            "Details Saved",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@SettingActivity,
                        "Please fill all the details",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}