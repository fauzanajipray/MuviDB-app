package com.faprayyy.tonton.view.ui.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.faprayyy.tonton.R

class SeriesFragment : Fragment() {

    private lateinit var notificationsViewModel: SeriesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProvider(this).get(SeriesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_series, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        textView.text = "Series Fragment"
        return root
    }
}