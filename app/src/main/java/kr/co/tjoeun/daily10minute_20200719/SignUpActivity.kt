package kr.co.tjoeun.daily10minute_20200719

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

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