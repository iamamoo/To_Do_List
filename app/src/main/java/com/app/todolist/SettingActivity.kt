package com.app.todolist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.app.todolist.databinding.ActivitySettingBinding
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.settingsToolbar)
        supportActionBar?.title = "Settings"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val data = this@SettingActivity.getSharedPreferences("allowEdit",Context.MODE_PRIVATE)
        val userEmail = data.getString("email","")
        val userName = data.getString("username","")
        val isAllowed = data.getBoolean("isAllowEdit",false)

        if (userEmail!!.isNotEmpty() && userName!!.isNotEmpty() ){
            binding.yourEmail.setText(userEmail)
            binding.yourName.setText(userName)
            binding.allowedit.isChecked = isAllowed
        }else {
            binding.yourEmail.setText("")
            binding.yourName.setText("")
            binding.allowedit.isChecked = isAllowed
        }



        binding.updateSettings.setOnClickListener {
                if (userName!!.isNotEmpty() && userEmail.isNotEmpty() ){
                    val name = binding.yourName.text.toString()
                    val email = binding.yourEmail.text.toString()
                    val allowEdit : SwitchMaterial = binding.allowedit
                    allowEdit.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked) {
                            buttonView.isChecked = true
                            val sharedPreferences = this@SettingActivity.getSharedPreferences("allowEdit",Context.MODE_PRIVATE)
                            with(sharedPreferences.edit()){
                                putString("email",email)
                                putString("username",name)
                                putBoolean("isAllowEdit",true)
                                    .apply()
                            }
                            Toast.makeText(this@SettingActivity,"Details Saved",Toast.LENGTH_SHORT).show()

                        } else {
                            buttonView.isChecked = false
                            val sharedPreferences = this@SettingActivity.getSharedPreferences("allowEdit",Context.MODE_PRIVATE)
                            with(sharedPreferences.edit()){
                                putString("email",email)
                                putString("username",name)
                                putBoolean("isAllowEdit",true)
                                    .apply()
                            }
                            Toast.makeText(this@SettingActivity,"Details Saved",Toast.LENGTH_SHORT).show()
                        }
                    }

                }else {
                    Toast.makeText(this@SettingActivity,"Please fill all the details",Toast.LENGTH_SHORT).show()
                }

            }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}