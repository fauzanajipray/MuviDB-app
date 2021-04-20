package com.faprayyy.tonton.view.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.faprayyy.tonton.R

class MoviesFragment : Fragment() {

    private lateinit var dashboardViewModel: MoviesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(MoviesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_movie, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        textView.text = "Movies Fragment"
        return root
    }
}