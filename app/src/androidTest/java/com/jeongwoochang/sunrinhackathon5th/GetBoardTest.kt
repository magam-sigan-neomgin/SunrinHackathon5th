package com.jeongwoochang.sunrinhackathon5th

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.jeongwoochang.sunrinhackathon5th.API.APIClient
import com.jeongwoochang.sunrinhackathon5th.API.APIInterface
import com.jeongwoochang.sunrinhackathon5th.data.BoardRes
import com.jeongwoochang.sunrinhackathon5th.data.ResBody
import okhttp3.MediaType
import okhttp3.RequestBody

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
class GetBoardTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        val service = APIClient.getClient(appContext).create(APIInterface::class.java)
        service.board.enqueue(object : Callback<BoardRes> {
            override fun onFailure(call: Call<BoardRes>, t: Throwable) {
            }

            override fun onResponse(call: Call<BoardRes>, response: Response<BoardRes>) {
                if (response.code() == 200 && (response.body() as BoardRes).status) {
                    val board = response.body() as BoardRes
                    assertEquals(board.board.size, 1)
                }
            }
        })
    }
}
