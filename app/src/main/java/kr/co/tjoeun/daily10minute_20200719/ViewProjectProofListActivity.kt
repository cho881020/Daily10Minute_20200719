package kr.co.tjoeun.daily10minute_20200719

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_view_project_proof_list.*
import java.text.SimpleDateFormat
import java.util.*

class ViewProjectProofListActivity : BaseActivity() {

//    인증을 확인할 날짜를 저장해주는 변수
//    proofDate => 기본값이 현재 시간으로 저장됨.
    val proofDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_project_proof_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        날짜 변경 버튼을 누르면 proofDate에 저장된 날짜를 변경
        changeProofDateBtn.setOnClickListener {

//            날짜 선택하는 팝업창을 띄우자.
            val datePickerDialog = DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

//                인증 날짜를 선택된 날짜로 변경
                proofDate.set(Calendar.YEAR, year)
                proofDate.set(Calendar.MONTH, month)
                proofDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)


//                변경된 인증 날짜를 화면에 반영
                val sdf = SimpleDateFormat("yyyy년 M월 d일")
                proofDateTxt.text = sdf.format(proofDate.time)

//                변경된 날짜의 인증 내역 가져오기
                getProofListFromServer()



            }, proofDate.get(Calendar.YEAR), proofDate.get(Calendar.MONTH), proofDate.get(Calendar.DAY_OF_MONTH))

            datePickerDialog.show()

        }

    }

    override fun setValues() {

//        proofDate에 담긴 일시 (Calendar) 를 => 2020년 6월 9일 형태로 문구 (String) 출력.
//        SimpleDateFormat의 기능중 format 사용

        val sdf = SimpleDateFormat("yyyy년 M월 d일")

        val proofDateStr = sdf.format(proofDate.time)

        proofDateTxt.text = proofDateStr


//        오늘 날짜의 인증 내역 가져오기
        getProofListFromServer()

    }

//    서버에서 이 프로젝트의 날짜별 인증 내역을 가져오는 기능

    fun getProofListFromServer() {



    }

}