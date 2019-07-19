package com.jeongwoochang.sunrinhackathon5th

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.jeongwoochang.sunrinhackathon5th.API.APIClient
import com.jeongwoochang.sunrinhackathon5th.API.APIInterface
import com.jeongwoochang.sunrinhackathon5th.data.DiaryRes
import com.jeongwoochang.sunrinhackathon5th.data.ResBody

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class GetDiaryTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        val service = APIClient.getClient(appContext).create(APIInterface::class.java)
        service.login("test@test.com", "test").enqueue(object : Callback<ResBody> {
            override fun onFailure(call: Call<ResBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResBody>, response: Response<ResBody>) {
                service.board.enqueue(object : Callback<DiaryRes> {
                    override fun onFailure(call: Call<DiaryRes>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<DiaryRes>, response: Response<DiaryRes>) {
                        if (response.code() == 200 && (response.body() as DiaryRes).status) {
                            val board = response.body() as DiaryRes
                            System.out.println(board.toString())
                        }
                    }
                })
            }
        })
    }
}
