package id.ac.ubaya.advweek4.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.ubaya.advweek4.R
import id.ac.ubaya.advweek4.model.Student
import id.ac.ubaya.advweek4.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_student_list.*


class StudentListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val studentListAdapter = StudentListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = studentListAdapter

        observeViewModel()

        refreshLayout.setOnRefreshListener {
            recView.visibility = View.GONE
            txtError.visibility = View.GONE
            progressLoad.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel(){
        viewModel.studentsLD.observe(viewLifecycleOwner ){
            studentListAdapter.updateStudentList(it)
        }

        viewModel.studentLoadErrorLD.observe(viewLifecycleOwner)
        {
            if(it == true){
                txtError.visibility = View.VISIBLE
            } else {
                txtError.visibility = View.GONE
            }
        }

        viewModel.loadingLD.observe(viewLifecycleOwner)
        {
            if(it == true){
                recView.visibility = View.GONE
                progressLoad.visibility = View.VISIBLE
            } else
            {
                recView.visibility = View.VISIBLE
                progressLoad.visibility = View.GONE
            }
        }
    }
}