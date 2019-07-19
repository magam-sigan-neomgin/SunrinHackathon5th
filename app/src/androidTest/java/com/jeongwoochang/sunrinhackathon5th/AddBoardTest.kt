package com.jeongwoochang.sunrinhackathon5th

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.jeongwoochang.sunrinhackathon5th.API.APIClient
import com.jeongwoochang.sunrinhackathon5th.API.APIInterface
import com.jeongwoochang.sunrinhackathon5th.data.ResBody
import okhttp3.MediaType
import okhttp3.RequestBody

import org.junit.Test
import org.junit.runner.RunWith

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AddBoardTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        val map = HashMap<String, RequestBody>()
        map["title"] = RequestBody.create(MediaType.parse("text/plain"), "test title")
        map["content"] = RequestBody.create(MediaType.parse("text/plain"), "test")
        map["photo\"; filename=\"photo.png\""] = RequestBody.create(MediaType.parse("image/png"), "/sdcard/DCIM/Camera/IMG_20190621_090539.jpg")
        val service = APIClient.getClient(appContext).create(APIInterface::class.java)
        service.addBoard(map).enqueue(object : Callback<ResBody> {
            override fun onFailure(call: Call<ResBody>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResBody>, response: Response<ResBody>) {
                if (response.code() == 200)
                    assert(response.body()!!.isStatus)
            }
        })
    }
}
