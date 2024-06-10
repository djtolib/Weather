package com.tolib.weather
import com.google.gson.Gson
import com.tolib.weather.data.model.MainInfo
import com.tolib.weather.data.model.Weather
import org.junit.Test
import org.junit.Assert.assertEquals
class EntitiesTest {
    @Test
    fun mainInfoTest(){
        val json = "{\"temp\":292.92,\"temp_min\":291.27,\"temp_max\":295.03,\"pressure\":1010,\"humidity\":41}"
        val obj = Gson().fromJson(json, MainInfo::class.java)
        assertEquals(obj.temp, 292.92, 0.0)
    }

    @Test
    fun weatherTest(){
        val json = "{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}"
        val obj = Gson().fromJson(json, Weather::class.java)
        assertEquals(obj.description, "few clouds")
    }
}