package kr.co.tjoeun.daily10minute_20200719

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.tjoeun.daily10minute_20200719.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        loginBtn.setOnClickListener {

//            입력한 아이디 / 비번 받아오기
            val inputId = emailEdt.text.toString()
            val inputPw = pwEdt.text.toString()

//            서버에 로그인 요청
            ServerUtil.postRequestLogin(mContext, inputId, inputPw, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    code에 적힌 숫자가 몇인지 저장하자.
                    val codeNum = json.getInt("code")

//                    codeNum이 200일땐 로그인 성공
//                    그 외의 모든 숫자는 로그인 실패.

                    if (codeNum == 200) {
//                        로그인 성공
                    }
                    else {
//                        로그인 실패

//                        서버 통신 중에 UI에 영향을 주려면 runOnUiThread 활용하자
                        runOnUiThread {
                            Toast.makeText(mContext, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }


                    }

                }

            })

        }

    }

    override fun setValues() {

    }


}