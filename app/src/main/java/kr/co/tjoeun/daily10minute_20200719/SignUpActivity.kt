package kr.co.tjoeun.daily10minute_20200719

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import kr.co.tjoeun.daily10minute_20200719.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        회원가입 버튼 누르면 => 빈 입력값이 있는지 검사하고
//        => 괜찮으면 실제로 서버에 가입 요청

        okBtn.setOnClickListener {
//            입력한 이메일 부터 검사하자
            val inputEmail = emailEdt.text.toString()

            if (inputEmail.isEmpty()) {
                Toast.makeText(mContext, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()

//                가입 절차 강제 종료
                return@setOnClickListener
            }
            else if (!inputEmail.contains("@")) {
//                @가 없다면, 이메일 양식이 아닌걸로 간주하자.
                Toast.makeText(mContext, "이메일 양식으로 입력해주세요.", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }

//            이메일 검사는 모두 통과한 상황
//            비번 길이가 8자 이상인지

            val inputPw = pwEdt.text.toString()

            if (inputPw.length < 8) {
                Toast.makeText(mContext, "비밀번호가 너무 짧습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            닉네임은 입력 했는지만 검사

            val inputNickName = nickNameEdt.text.toString()

            if (inputNickName.isEmpty()) {
                Toast.makeText(mContext, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            이메일 / 비번 / 닉네임 검사를 모두 통과한 상황
//            서버에 실제로 가입 신청.

            ServerUtil.putRequestSignUp(mContext, inputEmail, inputPw, inputNickName, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    서버가 알려주는 코드값이 200이면 가입 성공처리.
//                    아니라면 가입 실패 처리.

                    val code = json.getInt("code")

                    if (code == 200) {
                        runOnUiThread {
                            Toast.makeText(mContext, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
//                            로그인 화면으로 복귀
                            finish()
                        }
                    }
                    else {
//                        가입 실패 상황. => 왜 실패했는지 토스트로 출력

                        val message = json.getString("message")

                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }

                    }


                }

            })


        }

//        EditText (비번 입력칸) 에 글자를 타이핑하는 이벤트 체크
        pwEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                비번 확인 로직 실행
                checkPasswords()
            }

        })

    }


//    비밀번호 + 비밀번호 확인 동시에 검사하는 함수

    fun checkPasswords() {

//        입력한 비밀번호
        val inputPw = pwEdt.text.toString()

//        글자 수 => 0자 : 비밀번호를 입력해주세요.
//        1~7자 : 비밀번호가 너무 짧습니다.
//        8자 이상 : 사용해도 좋은 비밀번호 입니다.

        if (inputPw.isEmpty()) {
            pwCheckResultTxt.text = "비밀번호를 입력해주세요."
        }
        else if (inputPw.length < 8) {
            pwCheckResultTxt.text = "비밀번호가 너무 짧습니다."
        }
        else {
            pwCheckResultTxt.text = "사용해도 좋은 비밀번호 입니다."
        }

    }

    override fun setValues() {

    }


}