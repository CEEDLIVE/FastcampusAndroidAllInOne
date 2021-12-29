package ceed.live.android.kotlin.fastcampus.instagram.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

/*
    키-값 데이터 저장
    주의: MODE_WORLD_READABLE 및 MODE_WORLD_WRITEABLE 모드는 API 수준 17부터 지원 중단되었습니다.
    Android 7.0(API 수준 24)부터 Android에서 이러한 모드를 사용하면 SecurityException이 발생합니다.
    앱에서 비공개 파일을 다른 앱과 공유해야 하면 FLAG_GRANT_READ_URI_PERMISSION과 함께 FileProvider를 사용하면 됩니다.
    자세한 내용은 파일 공유를 참조하세요.
    https://developer.android.com/training/data-storage/shared-preferences?hl=ko

    파일 공유
    https://developer.android.com/training/secure-file-sharing?hl=ko
 */
class MySharedPreferences(context: Context) {

    private val PREFIX_PREFERENCE_FILE_KEY: String = "ceed.live.android.kotlin.fastcampus.instagram."
    private val PREFERENCE_FILE_NAME: String = PREFIX_PREFERENCE_FILE_KEY + "pref"

    private val TOKEN_AUTHORIZATION: String = "token"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)

    var token: String?
        get() = sharedPreferences.getString(TOKEN_AUTHORIZATION, "")
        set(value) = sharedPreferences.edit().putString(TOKEN_AUTHORIZATION, value).apply()
    /* get/set 함수 임의 설정. get 실행 시 저장된 값을 반환하며 default 값은 ""
     * set(value) 실행 시 value로 값을 대체한 후 저장 */
}