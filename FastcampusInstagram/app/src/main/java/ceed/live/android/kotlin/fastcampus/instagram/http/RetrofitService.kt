package ceed.live.android.kotlin.fastcampus.instagram.http

import ceed.live.android.kotlin.fastcampus.instagram.data.Person
import ceed.live.android.kotlin.fastcampus.instagram.data.Post
import ceed.live.android.kotlin.fastcampus.instagram.data.Register
import ceed.live.android.kotlin.fastcampus.instagram.data.User
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @GET("json/students/")
    fun getStudentList(): Call<ArrayList<Person>>

    @POST("json/students/")
    fun createStudentHashMap(
        @Body params: HashMap<String, Any>
    ): Call<Person>

    @POST("json/students/")
    fun createStudentEntity(
        @Body person: Person
    ): Call<Person>

    // 서버 측에서 객체를 받도록 설정되어 있지 않아 회원 가입 실패
    @POST("user/signup/")
    fun registerObject(
        @Body register: Register
    ): Call<User>

    @POST("user/signup/")
    @FormUrlEncoded
    fun registerFormUrlEncoded(
        @Field("username") username: String,
        @Field("password1") password1: String,
        @Field("password2") password2: String
    ): Call<User>

    @POST("user/login/")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @GET("instagram/post/list/all/")
    fun getInstagramPostAll(): Call<ArrayList<Post>>
}