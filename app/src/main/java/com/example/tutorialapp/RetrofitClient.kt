package com.example.tutorialapp

import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object RetrofitClient {
  fun getInstance(): Retrofit {
    var mHttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    var mOkHttpClient = OkHttpClient.Builder().addInterceptor(mHttpLoggingInterceptor).build()
    var retrofit: Retrofit = retrofit2.Retrofit.Builder()
      .baseUrl("http://103.187.147.99:5000")
      .addConverterFactory(GsonConverterFactory.create())
      .client(mOkHttpClient)
      .build()
    return retrofit
  }
}

interface ApiInterface {
  @GET("/fetchAllData")
  suspend fun fetchAllData(): Response<ResponseData>
}

data class ResponseData (
  @SerializedName("produk")
  val produk: List<Produk>,
  @SerializedName("relasi")
  val relasi: List<Relasi>,
//  @SerializedName("rekomendasi")
//  var rekomendasi: List<Rekomendasi>,
//  @SerializedName("pesanan")
//  var pesanan: List<Produk>,
//  @SerializedName("target")
//  var target: List<Target>
)