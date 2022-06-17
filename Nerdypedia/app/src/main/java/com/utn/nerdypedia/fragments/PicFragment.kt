package com.utn.nerdypedia.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.utn.nerdypedia.R
import com.utn.nerdypedia.viewmodels.PicViewModel

class PicFragment : Fragment() {

    companion object {
        fun newInstance() = PicFragment()
    }

    private lateinit var viewModel: PicViewModel
    private lateinit var v: View

    private lateinit var detailsNameText : TextView
    private lateinit var detailsAuthorText : TextView
    private lateinit var detailsDateText : TextView
    private lateinit var detailsUrlText : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.pic_fragment, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PicViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        detailsNameText = v.findViewById(R.id.detailsNameText)
        detailsAuthorText = v.findViewById(R.id.detailsAuthorText)
        detailsDateText = v.findViewById(R.id.detailsDateText)
        detailsUrlText = v.findViewById(R.id.detailsUrlText)

        viewModel.loadItemData()

        /* Observadores del viewModel */
        viewModel.nameText.observe(viewLifecycleOwner, Observer { name ->
            detailsNameText.text = name
        })

        viewModel.authorText.observe(viewLifecycleOwner, Observer { author ->
            detailsAuthorText.text = author
        })

        viewModel.dateText.observe(viewLifecycleOwner, Observer { date ->
            detailsDateText.text = date
        })

        viewModel.urlText.observe(viewLifecycleOwner, Observer { url ->
            detailsUrlText.text = url
        })
    }
}