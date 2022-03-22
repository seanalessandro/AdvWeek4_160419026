package id.ac.ubaya.advweek4.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import id.ac.ubaya.advweek4.R
import id.ac.ubaya.advweek4.util.loadImage
import id.ac.ubaya.advweek4.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_student_detail.*
import kotlinx.android.synthetic.main.student_list_item.view.*


class StudentDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if(arguments != null){
            val studentId = StudentDetailFragmentArgs.fromBundle(requireArguments()).studentId
            viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
            viewModel.fetch(studentId)
        }


        observeViewModel()
    }

    fun observeViewModel(){
       viewModel.studentsLD.observe(viewLifecycleOwner){
           textID.setText(viewModel.studentsLD.value?.id)
           textName.setText(viewModel.studentsLD.value?.name)
           textDOB.setText(viewModel.studentsLD.value?.dob)
           textPhone.setText(viewModel.studentsLD.value?.phone)
           imageViewDetail.loadImage(viewModel.studentsLD.value?.photoUrl, progressBarDetailStudent)
       }
    }
}