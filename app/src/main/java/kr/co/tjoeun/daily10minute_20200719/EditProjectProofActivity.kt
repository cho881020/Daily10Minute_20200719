package kr.co.tjoeun.daily10minute_20200719

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_edit_project_proof.*
import kr.co.tjoeun.daily10minute_20200719.utils.ServerUtil
import org.json.JSONObject

class EditProjectProofActivity : BaseActivity() {

//    어떤 프로젝트에 대해 인증할지 구별하는 id값
    var mProjectId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_project_proof)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        postProofBtn.setOnClickListener {

            val input = proofContentEdt.text.toString()

            if (input.length < 5) {
                Toast.makeText(mContext, "인증 내용은 5자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            하루에 한번만 올릴 수 있으니 => 진짜 올릴건지 확인받고 올리자.

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("오늘의 인증 하기")
            alert.setMessage("인증글은 하루에 한번만 올릴 수 있습니다. 정말 인증 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->

//                의사가 확인 되었으니 => 서버에 게시글을 올리자.

                ServerUtil.postRequestProof(mContext, mProjectId, input, object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {

//                        등록 성공 / 실패 여부 구별 필요
                        val code = json.getInt("code")

                        if (code == 200) {

//                            등록 성공 케이스 => 토스트로 격려 메세지.
//                            완주 했다면 격려 X, 축하. => 완주 여부 파악 필요

                            val isProjectComplete = json.getBoolean("is_project_complete")

                            runOnUiThread {
                                if (isProjectComplete) {
                                    Toast.makeText(mContext, "축하합니다! 프로젝트를 완주했습니다.", Toast.LENGTH_SHORT).show()
                                }
                                else {
                                    Toast.makeText(mContext, "잘 하고 계십니다! 내일도 인증해주세요.", Toast.LENGTH_SHORT).show()
                                }

//                                오늘의 인증이 완료되면 입력화면 종료
                                finish()
                            }


                        }
                        else {
//                            등록 실패 케이스 => 왜 실패했는지 서버가 주는 메세지를 토스트로

                            val message = json.getString("message")

                            runOnUiThread {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }

                        }

                    }

                })

            })
            alert.setNegativeButton("취소", null)
            alert.show()

        }

    }

    override fun setValues() {

        mProjectId = intent.getIntExtra("projectId", 0)

//        프로젝트의 제목은 intent가 넘겨주는 내용을 그대로 표시
        projectTitleTxt.text = intent.getStringExtra("projectTitle")

    }

}