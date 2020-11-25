package kr.loner.lessonpinkdiary

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_pingdatalk.*
import kotlinx.android.synthetic.main.fragment_pingdatalk.view.*

class PingDaTalkFragment : Fragment() {

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_pingdatalk, container, false).run {

        tb_pingDaTalk.navigationIcon = resources.getDrawable(R.drawable.ic_baseline_dehaze_24, null)
        tb_pingDaTalk.title = "핑다톡"

        tb_pingDaTalk.setNavigationOnClickListener {
            dL_sideBoard.open()
        }



        setBackButton(this)
        rootView
    }

    private fun setBackButton(view:View) {
        requireActivity().onBackPressedDispatcher.addCallback(this@PingDaTalkFragment) {
            if (view.dL_sideBoard.isOpen)
                view.dL_sideBoard.close()
            else
                requireActivity().finish()
        }
    }
}