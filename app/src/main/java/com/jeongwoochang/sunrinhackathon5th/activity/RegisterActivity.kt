package com.jeongwoochang.sunrinhackathon5th.activity

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStoragePublicDirectory
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.jeongwoochang.sunrinhackathon5th.API.APIClient
import com.jeongwoochang.sunrinhackathon5th.API.APIInterface
import com.jeongwoochang.sunrinhackathon5th.R
import com.jeongwoochang.sunrinhackathon5th.data.ResBody
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class RegisterActivity : AppCompatActivity() {

    private lateinit var currentProfileImgFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        TedPermission.with(this)
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    val bitmap = BitmapFactory.decodeResource(resources, R.drawable.default_profile_image)
                    val path = getExternalStoragePublicDirectory(DIRECTORY_PICTURES).toString() + "/profile.png"
                    val file = File(path)
                    val out: OutputStream? = FileOutputStream(file)
                    try {
                        bitmap.run { compress(Bitmap.CompressFormat.PNG, 100, out) }
                        out!!.flush()
                        out.close()
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                    currentProfileImgFile = file
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(applicationContext, "권한을 주세요", Toast.LENGTH_SHORT).show()
                    finish()
                }
            })
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .check()

        profileImage.setOnClickListener {
            TedBottomPicker.with(this)
                .show {
                    profileImage.setImageURI(it)
                    currentProfileImgFile = File(it.path)
                }
        }

        sign_up_button.setOnClickListener {
            if (username.text.isNotEmpty() && email.text.isNotEmpty() && password.text.isNotEmpty()) {
                val service = APIClient.getClient(this).create(APIInterface::class.java)
                val map = HashMap<String, RequestBody>()
                map.put("id", RequestBody.create(MediaType.parse("text/plain"), email.text.toString().trim()))
                map.put("pw", RequestBody.create(MediaType.parse("text/plain"), password.text.toString().trim()))
                map.put("username", RequestBody.create(MediaType.parse("text/plain"), username.text.toString().trim()))
                //map.put("profileimage\"; filename=\"profile.png\"", RequestBody.create(MediaType.parse("image/png"), currentProfileImgFile))
                service.register(
                    map
                ).enqueue(object : Callback<ResBody> {
                    override fun onFailure(call: Call<ResBody>, t: Throwable) {
                        Toast.makeText(applicationContext, "회원가입에 실패", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<ResBody>, response: retrofit2.Response<ResBody>) {
                        if ((response.body() as ResBody).isStatus) {
                            Toast.makeText(applicationContext, "회원가입에 성공", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(applicationContext, LoginActivity::class.java))
                            finish()
                        }
                    }

                })
            } else {
                Snackbar.make(findViewById(android.R.id.content), "빈칸을 모두 채워주세요", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
