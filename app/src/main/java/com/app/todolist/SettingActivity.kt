package com.app.todolist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.updateSettings.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
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
                    } else {
                        buttonView.isChecked = false
                        val sharedPreferences = this@SettingActivity.getSharedPreferences("allowEdit",Context.MODE_PRIVATE)
                        with(sharedPreferences.edit()){
                            putString("email",email)
                            putString("username",name)
                            putBoolean("isAllowEdit",true)
                                .apply()
                        }
                    }
                }
            }

        }






    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}