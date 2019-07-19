package com.jeongwoochang.sunrinhackathon5th

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.jeongwoochang.sunrinhackathon5th.API.APIClient
import com.jeongwoochang.sunrinhackathon5th.API.APIInterface
import com.jeongwoochang.sunrinhackathon5th.data.DiaryRes
import com.jeongwoochang.sunrinhackathon5th.data.ResBody
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
class GetDiaryTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        val service = APIClient.getClient(appContext).create(APIInterface::class.java)
        Timber.plant(Timber.DebugTree())
        service.login("test@test.com", "test").enqueue(object : Callback<ResBody> {
            override fun onFailure(call: Call<ResBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResBody>, response: Response<ResBody>) {
                Timber.d("*")
                service.myBoard.enqueue(object : Callback<DiaryRes> {
                    override fun onFailure(call: Call<DiaryRes>, t: Throwable) {
                        Timber.d("*")
                        t.printStackTrace()
                    }

                    override fun onResponse(call: Call<DiaryRes>, response: Response<DiaryRes>) {
                        Timber.d("*")
                        Timber.d((response.body() as DiaryRes).toString())
                        if (response.code() == 200) {
                            Timber.d("*")
                            val board = response.body() as DiaryRes
                            Timber.d(board.toString())
                        }
                    }
                })
            }
        })
    }
}
