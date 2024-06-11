
import com.tolib.weather.ApiException
import com.tolib.weather.data.model.MainInfo
import com.tolib.weather.data.model.Weather
import com.tolib.weather.data.model.WeatherResponse
import com.tolib.weather.data.repository.ApiService
import com.tolib.weather.data.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class WeatherRepositoryTest {

    private val mockApiService: ApiService = mockk()
    private val weatherRepository = WeatherRepository(mockApiService)

    @Test
    fun `test getCurrentWeather success`(): Unit = runBlocking {
        val mockResponse = mockResponse()
        coEvery { mockApiService.getCurrentWeather(any(), any(), any(), any()) } returns mockResponse
        val city = "Moscow"
        val unit = "C"
        val result = weatherRepository.getCurrentWeather(city, 12.2, 32.1, unit)
        assertEquals(mockResponse.body(), result)
    }


    @Test
    fun `test getCurrentWeather error`() = runBlocking{
        coEvery { mockApiService.getCurrentWeather(any(), any(), any(), any()) } throws ApiException(400, "Unauthorized")
        val city = "Moscow"
        val unit = "C"
        try{
            val result = weatherRepository.getCurrentWeather(city, 12.2, 32.1, unit)
        } catch (e: ApiException){
            assertEquals("Unauthorized", e.message)
        }
    }

    private fun mockResponse(): Response<WeatherResponse> {
        val mainInfo = MainInfo(100.00, -19.0, 200.0, 123)
        val list = listOf(Weather(1L, "rainy", "fast rain", "10d"))
        return Response.success(WeatherResponse(list, mainInfo, "Moscow", "200", "ok" ))
    }
}
