package kr.co.tjoeun.daily10minute_20200719.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kr.co.tjoeun.daily10minute_20200719.R
import kr.co.tjoeun.daily10minute_20200719.datas.Project

class ProjectAdapter(
    val mContext:Context,
    resId:Int,
    val mList:List<Project>) : ArrayAdapter<Project>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if (tempRow == null) {
            tempRow = inf.inflate(R.layout.project_list_item, null)
        }

        val row = tempRow!!

        val projectImg = row.findViewById<ImageView>(R.id.projectImg)
        val projectTitleTxt = row.findViewById<TextView>(R.id.projectTitleTxt)
        val projectDescriptionTxt = row.findViewById<TextView>(R.id.projectDescriptionTxt)

        val data = mList[position]

        projectTitleTxt.text = data.title
        projectDescriptionTxt.text = data.description

        Glide.with(mContext).load(data.imageUrl).into(projectImg)

        return row
    }


}