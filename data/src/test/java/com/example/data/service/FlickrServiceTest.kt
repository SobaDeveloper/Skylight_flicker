package com.example.data.service

import com.example.data.service.flickr.FlickrService
import com.example.data.service.flickr.dto.FlickrPhotosResponseDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class FlickrServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var flickrService: FlickrService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        flickrService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(FlickrService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `searchPhotosByTag returns expected data`() = runBlocking {
        val mockResponse = """
            {
              "photos": {
                "page": 1,
                "pages": 1,
                "perpage": 20,
                "total": 1,
                "photo": [
                  {
                    "id": "12345",
                    "owner": "ownerid",
                    "secret": "secret",
                    "server": "server",
                    "farm": 1,
                    "title": "Test Photo",
                    "ispublic": 1,
                    "isfriend": 0,
                    "isfamily": 0
                  }
                ]
              },
              "stat": "ok"
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockResponse).setResponseCode(200))

        val response = flickrService.searchPhotosByTag(tags = "test")

        assertTrue(response.isSuccessful)
        val body: FlickrPhotosResponseDto? = response.body()
        assertNotNull(body)
        assertEquals("ok", body?.stat)
        assertEquals(1, body?.photosDto?.photoDto?.size)
        assertEquals("Test Photo", body?.photosDto?.photoDto?.get(0)?.title)
    }
}
