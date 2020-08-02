package kr.co.tjoeun.daily10minute_20200719

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        액션바가 있는지 확인하고 실행.
        supportActionBar?.let {

//            액션바가 null 이 아닐때만 실행되는 내용
            setCustomActionBar()
        }

    }

//    각각의 화면마다 구현해야할 내용이 달라지는 함수 : abstract
    abstract fun setupEvents()
    abstract fun setValues()

//    모든 화면에서 실행해야할 내용이 같다 => 무슨 일을 할지도 실제 작성

    fun setCustomActionBar() {

//        액션바가 절대 null이 아니라고 별개의 변수에 옮겨담자.
        val myActionBar = supportActionBar!!

//        액션바를 커스텀으로 사용할 수 있도록 세팅
        myActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
//        실제로 보여줄 커스텀 화면을 세팅
        myActionBar.setCustomView(R.layout.custom_action_bar)

//        커스텀액션바 뒤의 기본 색 제거 => 액션바를 들고 있는 툴바의 좌우 여백을 0으로 설정하자.
        val parentToolBar = myActionBar.customView.parent as Toolbar
        parentToolBar.setContentInsetsAbsolute(0,0)

    }

}