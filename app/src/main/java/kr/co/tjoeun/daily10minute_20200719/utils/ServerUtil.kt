package kr.co.tjoeun.daily10minute_20200719.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

//    서버의 응답이 돌아올때 실행할 내용을 담아둘 목적의 인터페이스

    interface JsonResponseHandler {
        fun onResponse(json: JSONObject)
    }

//    ServerUtil클래스의 static 함수 / 변수들을 저장할 공간.

    companion object {

//        접근할 서버 주소를 담는 변수
        private val BASE_URL = "http://15.164.153.174"

//        로그인 요청을 해주는 기능

        fun postRequestLogin(context: Context, email:String, pw:String, handler: JsonResponseHandler?) {

//            앱을 클라이언트 역할로 동작하게 해주는 변수
            val client = OkHttpClient()

//            어떤 주소로 가야하는지 URL을 호스트주소(BASE_URL) + 기능주소 결합
            val urlString = "${BASE_URL}/user"

//            서버에 전달할 데이터를 폼바디에 담아주자. (POST - formData)
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .build()

//            서버에 요청할 모든 정보를 담아주는 request 변수 생성
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
//                .header() // API에서 헤더를 요구하면 여기서 첨부해야함.
                .build()

//            완성된 Request를 가지고 실제로 서버로 출발.
//            서버는 Request를 받으면 => Response를 내려줌.
//            그에 대한 처리도 필요함

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    서버에 연결 자체를 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
//                    일단 서버에 연결은 되어서 응답을 받아온 경우.
//                    그 응답에 뭐라고 적혀있는지 파악 => 화면(Activity)에 전달

//                    서버가 내려준 응답의 내용을 String으로 저장
                    val bodyString = response.body!!.string()

//                    String을 => 분석하기 쉬운 Json 클래스로 변환.
                    val json = JSONObject(bodyString)

                    Log.d("서버응답", json.toString())

//                    완성된 json을 액티비티에서 처리하도록 전달.
                    handler?.onResponse(json)


                }

            })

        }


//        프로젝트 참가 신청해주는 기능

        fun postRequestProjectJoin(context: Context, projectId: Int, handler: JsonResponseHandler?) {

//            앱을 클라이언트 역할로 동작하게 해주는 변수
            val client = OkHttpClient()

//            어떤 주소로 가야하는지 URL을 호스트주소(BASE_URL) + 기능주소 결합
            val urlString = "${BASE_URL}/project"

//            서버에 전달할 데이터를 폼바디에 담아주자. (POST - formData)
            val formData = FormBody.Builder()
                .add("project_id", projectId.toString())
                .build()

//            서버에 요청할 모든 정보를 담아주는 request 변수 생성
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getLoginUserToken(context)) // API에서 헤더를 요구하면 여기서 첨부해야함.
                .build()

//            완성된 Request를 가지고 실제로 서버로 출발.
//            서버는 Request를 받으면 => Response를 내려줌.
//            그에 대한 처리도 필요함

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    서버에 연결 자체를 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
//                    일단 서버에 연결은 되어서 응답을 받아온 경우.
//                    그 응답에 뭐라고 적혀있는지 파악 => 화면(Activity)에 전달

//                    서버가 내려준 응답의 내용을 String으로 저장
                    val bodyString = response.body!!.string()

//                    String을 => 분석하기 쉬운 Json 클래스로 변환.
                    val json = JSONObject(bodyString)

                    Log.d("서버응답", json.toString())

//                    완성된 json을 액티비티에서 처리하도록 전달.
                    handler?.onResponse(json)


                }

            })

        }

//        인증글 좋아요 기능

        fun postRequestLikeProof(context: Context, proofId: Int, handler: JsonResponseHandler?) {

//            앱을 클라이언트 역할로 동작하게 해주는 변수
            val client = OkHttpClient()

//            어떤 주소로 가야하는지 URL을 호스트주소(BASE_URL) + 기능주소 결합
            val urlString = "${BASE_URL}/like_proof"

//            서버에 전달할 데이터를 폼바디에 담아주자. (POST - formData)
            val formData = FormBody.Builder()
                .add("proof_id", proofId.toString())
                .build()

//            서버에 요청할 모든 정보를 담아주는 request 변수 생성
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getLoginUserToken(context)) // API에서 헤더를 요구하면 여기서 첨부해야함.
                .build()

//            완성된 Request를 가지고 실제로 서버로 출발.
//            서버는 Request를 받으면 => Response를 내려줌.
//            그에 대한 처리도 필요함

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    서버에 연결 자체를 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
//                    일단 서버에 연결은 되어서 응답을 받아온 경우.
//                    그 응답에 뭐라고 적혀있는지 파악 => 화면(Activity)에 전달

//                    서버가 내려준 응답의 내용을 String으로 저장
                    val bodyString = response.body!!.string()

//                    String을 => 분석하기 쉬운 Json 클래스로 변환.
                    val json = JSONObject(bodyString)

                    Log.d("서버응답", json.toString())

//                    완성된 json을 액티비티에서 처리하도록 전달.
                    handler?.onResponse(json)


                }

            })

        }


//        회원가입 요청을 해주는 기능

        fun putRequestSignUp(context: Context, email:String, pw:String, nickName:String, handler: JsonResponseHandler?) {

//            앱을 클라이언트 역할로 동작하게 해주는 변수
            val client = OkHttpClient()

//            어떤 주소로 가야하는지 URL을 호스트주소(BASE_URL) + 기능주소 결합
            val urlString = "${BASE_URL}/user"

//            서버에 전달할 데이터를 폼바디에 담아주자. (PUT - formData)
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .add("nick_name", nickName)
                .build()

//            서버에 요청할 모든 정보를 담아주는 request 변수 생성
            val request = Request.Builder()
                .url(urlString)
                .put(formData)
//                .header() // API에서 헤더를 요구하면 여기서 첨부해야함.
                .build()

//            완성된 Request를 가지고 실제로 서버로 출발.
//            서버는 Request를 받으면 => Response를 내려줌.
//            그에 대한 처리도 필요함

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    서버에 연결 자체를 실패한 경우
                }

                override fun onResponse(call: Call, response: Response) {
//                    일단 서버에 연결은 되어서 응답을 받아온 경우.
//                    그 응답에 뭐라고 적혀있는지 파악 => 화면(Activity)에 전달

//                    서버가 내려준 응답의 내용을 String으로 저장
                    val bodyString = response.body!!.string()

//                    String을 => 분석하기 쉬운 Json 클래스로 변환.
                    val json = JSONObject(bodyString)

                    Log.d("서버응답", json.toString())

//                    완성된 json을 액티비티에서 처리하도록 전달.
                    handler?.onResponse(json)


                }

            })

        }

//        프로젝트 목록을 가져와주는 기능

        fun getRequestProjectList(context: Context, handler: JsonResponseHandler?) {

//            서버에 Request를 날려주는 변수
            val client = OkHttpClient()

//            GET / DELETE방식의 파라미터 첨부는 query에 담아야 함.
//            주소에 붙여주는 방식 => 쉽게 가공하도록 도와주는 변수 생성.
            val urlBuilder = "${BASE_URL}/project".toHttpUrlOrNull()!!.newBuilder()
//            urlBuilder.addEncodedQueryParameter("이름표", "전달값")


//            주소 가공이 끝나면 최종 String으로 변환.
//            어디로 갈지 (url) + 어떤 데이터를 가져갈지 (parameter) 첨부된 String
            val urlString = urlBuilder.build().toString()


//            실제 전송 정보를 담는 Request 생성
            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getLoginUserToken(context))
                .build()


//            client변수를 이용해서 실제 호출 => 응답 처리

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    서버 연결 자체에 실패한 경우 (단선, 와이파이 끊김, 서버 다운)
//                    어떤 일이 생겨서 실패했는지 로그 출력
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
//                    서버 연결 자체는 성공 => 원하는 결과를 얻었다는 보장은 없다.
//                    응답 내용 (String)을 분석(JSONObject)해서 => 화면에 반영.

//                    응답 내용을 String으로 저장
                    val bodyString = response.body!!.string()

//                    저장한 String을 가지고 => JSONObject로 재가공 (분석의 편의성)

                    val json = JSONObject(bodyString)

//                    가공된 JSON을 로그로 출력
                    Log.d("서버 응답", json.toString())

//                    JSON내용 분석은 => 화면에서 진행하게 넘겨주자.
                    handler?.onResponse(json)

                }

            })



        }


//        원하는 프로젝트의 상세정보를 불러오는 기능

        fun getRequestProjectDetail(context: Context, projectId: Int, handler: JsonResponseHandler?) {

//            서버에 Request를 날려주는 변수
            val client = OkHttpClient()

//            GET / DELETE방식의 파라미터 첨부는 query에 담아야 함.
//            주소에 붙여주는 방식 => 쉽게 가공하도록 도와주는 변수 생성.
            val urlBuilder = "${BASE_URL}/project/${projectId}".toHttpUrlOrNull()!!.newBuilder()
//            urlBuilder.addEncodedQueryParameter("이름표", "전달값")


//            주소 가공이 끝나면 최종 String으로 변환.
//            어디로 갈지 (url) + 어떤 데이터를 가져갈지 (parameter) 첨부된 String
            val urlString = urlBuilder.build().toString()


//            실제 전송 정보를 담는 Request 생성
            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getLoginUserToken(context))
                .build()


//            client변수를 이용해서 실제 호출 => 응답 처리

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    서버 연결 자체에 실패한 경우 (단선, 와이파이 끊김, 서버 다운)
//                    어떤 일이 생겨서 실패했는지 로그 출력
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
//                    서버 연결 자체는 성공 => 원하는 결과를 얻었다는 보장은 없다.
//                    응답 내용 (String)을 분석(JSONObject)해서 => 화면에 반영.

//                    응답 내용을 String으로 저장
                    val bodyString = response.body!!.string()

//                    저장한 String을 가지고 => JSONObject로 재가공 (분석의 편의성)

                    val json = JSONObject(bodyString)

//                    가공된 JSON을 로그로 출력
                    Log.d("서버 응답", json.toString())

//                    JSON내용 분석은 => 화면에서 진행하게 넘겨주자.
                    handler?.onResponse(json)

                }

            })



        }


//        인증 게시글의 상세정보를 불러오는 기능

        fun getRequestProofDetail(context: Context, proofId: Int, handler: JsonResponseHandler?) {

//            서버에 Request를 날려주는 변수
            val client = OkHttpClient()

//            GET / DELETE방식의 파라미터 첨부는 query에 담아야 함.
//            주소에 붙여주는 방식 => 쉽게 가공하도록 도와주는 변수 생성.
            val urlBuilder = "${BASE_URL}/project_proof/${proofId}".toHttpUrlOrNull()!!.newBuilder()
//            urlBuilder.addEncodedQueryParameter("이름표", "전달값")


//            주소 가공이 끝나면 최종 String으로 변환.
//            어디로 갈지 (url) + 어떤 데이터를 가져갈지 (parameter) 첨부된 String
            val urlString = urlBuilder.build().toString()


//            실제 전송 정보를 담는 Request 생성
            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getLoginUserToken(context))
                .build()


//            client변수를 이용해서 실제 호출 => 응답 처리

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    서버 연결 자체에 실패한 경우 (단선, 와이파이 끊김, 서버 다운)
//                    어떤 일이 생겨서 실패했는지 로그 출력
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
//                    서버 연결 자체는 성공 => 원하는 결과를 얻었다는 보장은 없다.
//                    응답 내용 (String)을 분석(JSONObject)해서 => 화면에 반영.

//                    응답 내용을 String으로 저장
                    val bodyString = response.body!!.string()

//                    저장한 String을 가지고 => JSONObject로 재가공 (분석의 편의성)

                    val json = JSONObject(bodyString)

//                    가공된 JSON을 로그로 출력
                    Log.d("서버 응답", json.toString())

//                    JSON내용 분석은 => 화면에서 진행하게 넘겨주자.
                    handler?.onResponse(json)

                }

            })



        }


//        프로젝트의 상세정보 + 참여중 인원 목록 API 호출 기능

        fun getRequestProjectDetailWithUsers(context: Context, projectId: Int, handler: JsonResponseHandler?) {

//            서버에 Request를 날려주는 변수
            val client = OkHttpClient()

//            GET / DELETE방식의 파라미터 첨부는 query에 담아야 함.
//            주소에 붙여주는 방식 => 쉽게 가공하도록 도와주는 변수 생성.
            val urlBuilder = "${BASE_URL}/project/${projectId}".toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addEncodedQueryParameter("need_user_list", true.toString())


//            주소 가공이 끝나면 최종 String으로 변환.
//            어디로 갈지 (url) + 어떤 데이터를 가져갈지 (parameter) 첨부된 String
            val urlString = urlBuilder.build().toString()


//            실제 전송 정보를 담는 Request 생성
            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getLoginUserToken(context))
                .build()


//            client변수를 이용해서 실제 호출 => 응답 처리

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    서버 연결 자체에 실패한 경우 (단선, 와이파이 끊김, 서버 다운)
//                    어떤 일이 생겨서 실패했는지 로그 출력
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
//                    서버 연결 자체는 성공 => 원하는 결과를 얻었다는 보장은 없다.
//                    응답 내용 (String)을 분석(JSONObject)해서 => 화면에 반영.

//                    응답 내용을 String으로 저장
                    val bodyString = response.body!!.string()

//                    저장한 String을 가지고 => JSONObject로 재가공 (분석의 편의성)

                    val json = JSONObject(bodyString)

//                    가공된 JSON을 로그로 출력
                    Log.d("서버 응답", json.toString())

//                    JSON내용 분석은 => 화면에서 진행하게 넘겨주자.
                    handler?.onResponse(json)

                }

            })



        }


//        프로젝트의 상세정보 + 날짜별 인증 게시글 목록 API 호출
        fun getRequestProjectDetailWithProofList(context: Context, projectId: Int, dateStr: String, handler: JsonResponseHandler?) {

//            서버에 Request를 날려주는 변수
            val client = OkHttpClient()

//            GET / DELETE방식의 파라미터 첨부는 query에 담아야 함.
//            주소에 붙여주는 방식 => 쉽게 가공하도록 도와주는 변수 생성.
            val urlBuilder = "${BASE_URL}/project/${projectId}".toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addEncodedQueryParameter("proof_date", dateStr)


//            주소 가공이 끝나면 최종 String으로 변환.
//            어디로 갈지 (url) + 어떤 데이터를 가져갈지 (parameter) 첨부된 String
            val urlString = urlBuilder.build().toString()


//            실제 전송 정보를 담는 Request 생성
            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getLoginUserToken(context))
                .build()


//            client변수를 이용해서 실제 호출 => 응답 처리

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    서버 연결 자체에 실패한 경우 (단선, 와이파이 끊김, 서버 다운)
//                    어떤 일이 생겨서 실패했는지 로그 출력
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
//                    서버 연결 자체는 성공 => 원하는 결과를 얻었다는 보장은 없다.
//                    응답 내용 (String)을 분석(JSONObject)해서 => 화면에 반영.

//                    응답 내용을 String으로 저장
                    val bodyString = response.body!!.string()

//                    저장한 String을 가지고 => JSONObject로 재가공 (분석의 편의성)

                    val json = JSONObject(bodyString)

//                    가공된 JSON을 로그로 출력
                    Log.d("서버 응답", json.toString())

//                    JSON내용 분석은 => 화면에서 진행하게 넘겨주자.
                    handler?.onResponse(json)

                }

            })



        }


    }

}