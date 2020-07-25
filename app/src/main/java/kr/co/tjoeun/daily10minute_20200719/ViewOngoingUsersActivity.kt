package kr.co.tjoeun.daily10minute_20200719

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_view_ongoing_users.*
import kr.co.tjoeun.daily10minute_20200719.adapters.OngoingUserAdapter
import kr.co.tjoeun.daily10minute_20200719.datas.Project
import kr.co.tjoeun.daily10minute_20200719.datas.User
import kr.co.tjoeun.daily10minute_20200719.utils.ServerUtil
import org.json.JSONObject

class ViewOngoingUsersActivity : BaseActivity() {

    var mProjectId = 0

//    서버에서 받아온 프로젝트 정보
    lateinit var mProject : Project

//    사용자 정보를 저장할 목록
    val mOngoingUserList = ArrayList<User>()

    lateinit var mOngoingUserAdapter : OngoingUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_ongoing_users)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mProjectId = intent.getIntExtra("projectId", 0)

        getOngoingUsersFromServer()

        mOngoingUserAdapter = OngoingUserAdapter(mContext, R.layout.ongoing_user_list_item, mOngoingUserList)
        userListView.adapter = mOngoingUserAdapter
    }

//    진행중인 사람 명단 + 상세정보 불러오기

    fun getOngoingUsersFromServer() {

        ServerUtil.getRequestProjectDetailWithUsers(mContext, mProjectId, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val projectObj = data.getJSONObject("project")

                mProject = Project.getProjectFromJson(projectObj)

//                projectObj 내부에 ongoing_users 배열을 활용해서 사용자 명단을 목록에 저장

                val ongoingUsers = projectObj.getJSONArray("ongoing_users")

                for (i in 0 until ongoingUsers.length()) {
                    val userObj = ongoingUsers.getJSONObject(i)

                    val user = User.getUserFromJson(userObj)

                    mOngoingUserList.add(user)
                }


//                프로젝트 정보 UI에 반영

                runOnUiThread {
                    titleTxt.text = mProject.title
                    userCountTxt.text = "참여중 인원 : ${mProject.ongoingUserCount}명"

//                    참여중인 사용자 명단도 반영
                    mOngoingUserAdapter.notifyDataSetChanged()
                }

            }

        })

    }

}