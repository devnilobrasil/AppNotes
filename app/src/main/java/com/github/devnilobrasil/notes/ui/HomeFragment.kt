package com.github.devnilobrasil.notes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.github.devnilobrasil.notes.R
import com.github.devnilobrasil.notes.adapter.NotesAdapter
import com.github.devnilobrasil.notes.databinding.FragmentHomeBinding
import com.github.devnilobrasil.notes.helper.DatabaseConstants
import com.github.devnilobrasil.notes.helper.NotesListeners
import com.github.devnilobrasil.notes.viewmodels.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment()
{

    private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var homeViewModel: HomeViewModel
    private val adapter = NotesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter


        eventNotes()
        observe()
        goToAddNotesFragment()
        return binding.root
    }

    override fun onResume()
    {
        super.onResume()
        homeViewModel.getAllNotes()
    }

    private fun observe()
    {
        homeViewModel.listAllNotes.observe(viewLifecycleOwner) {
            adapter.updateNotes(it)
        }

        homeViewModel.delete.observe(viewLifecycleOwner) {
            if (it != "")
            {
                Snackbar.make(
                    requireContext(),
                    binding.floatingButton,
                    it, Snackbar.LENGTH_SHORT
                )
                    .setAnchorView(binding.floatingButton)
                    .setBackgroundTint(resources.getColor(R.color.teal_100, null))
                    .show()
            }
        }
    }

    private fun goToAddNotesFragment()
    {
        binding.floatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addNotesFragment)
        }
    }

    private fun eventNotes()
    {

        val listener = object : NotesListeners
        {
            override fun onClick(id: Int)
            {
                val bundle = Bundle()
                bundle.putInt(DatabaseConstants.NOTES.ID, id)
                findNavController().navigate(R.id.action_homeFragment_to_addNotesFragment, bundle)
            }

            override fun onDelete(id: Int)
            {
                homeViewModel.deleteNote(id, requireContext())
                homeViewModel.getAllNotes()
            }
        }
        adapter.attachNotes(listener)
    }


}