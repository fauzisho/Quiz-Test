package com.uzi.quiz.ui.exit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uzi.quiz.R
import com.uzi.quiz.ui.Updateable

class ExitFragment : Fragment(), Updateable {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_exit, container, false)
        return root
    }

    override fun update() {

    }
}
