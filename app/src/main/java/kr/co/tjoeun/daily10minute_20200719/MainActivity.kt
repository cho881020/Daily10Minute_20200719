package kr.co.tjoeun.daily10minute_20200719

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

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



        }

    }

    override fun setValues() {

    }


}