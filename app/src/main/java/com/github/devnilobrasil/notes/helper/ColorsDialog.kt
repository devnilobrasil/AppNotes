package com.github.devnilobrasil.notes.helper

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import com.github.devnilobrasil.notes.helper.DatabaseConstants.COLORS.IdColors
import androidx.fragment.app.DialogFragment
import com.github.devnilobrasil.notes.R
import com.github.devnilobrasil.notes.databinding.ColorsNoteLayoutBinding
import com.github.devnilobrasil.notes.model.NotesModel

class ColorsDialog : DialogFragment()
{
    private val binding: ColorsNoteLayoutBinding by lazy { ColorsNoteLayoutBinding.inflate(layoutInflater) }

    private val notesModel = NotesModel()

    var colorChoice = notesModel.color

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,b: Bundle?
    ): View
    {
        return binding.root.let {
            if (it.parent != null) {
                (it.parent as ViewGroup).removeView(it)
            }
            it
        }
    }

    override fun onStart()
    {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        colors()

    }

     private fun statusBarColor(color: Int){
        val window: Window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireContext(), color)
        window.findViewById<View>(R.id.topAppBar).setBackgroundColor(ContextCompat.getColor(requireContext(), color))
        window.findViewById<View>(R.id.view_bottom).setBackgroundColor(ContextCompat.getColor(requireContext(), color))
    }

    private fun colors(){


        binding.colorDefault.setOnClickListener {
            colorChoice = IdColors.DEFAULT
            statusBarColor(colorChoice)
            dismiss()
        }

        binding.colorRed.setOnClickListener {
            colorChoice = IdColors.RED
            statusBarColor(colorChoice)
            dismiss()
        }

        binding.colorPurple.setOnClickListener {
            colorChoice = IdColors.PURPLE
            statusBarColor(colorChoice)
            dismiss()
        }

        binding.colorGreen.setOnClickListener {
            colorChoice = IdColors.GREEN
            statusBarColor(colorChoice)
            dismiss()
        }

        binding.colorGrey.setOnClickListener {
            colorChoice = IdColors.GREY
            statusBarColor(colorChoice)
            dismiss()
        }

        binding.colorOrange.setOnClickListener {
            colorChoice = IdColors.ORANGE
            statusBarColor(colorChoice)
            dismiss()
        }

        binding.colorIndigo.setOnClickListener {
            colorChoice = IdColors.INDIGO
            statusBarColor(colorChoice)
            dismiss()
        }

        binding.colorYellow.setOnClickListener {
            colorChoice = IdColors.YELLOW
            statusBarColor(colorChoice)
            dismiss()
        }


    }




}