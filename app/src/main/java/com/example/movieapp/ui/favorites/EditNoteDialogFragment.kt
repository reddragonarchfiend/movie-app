package com.example.movieapp.ui.favorites

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.DialogEditNoteBinding
import dagger.hilt.android.AndroidEntryPoint


class EditNoteDialogFragment : DialogFragment() {

    private val args: EditNoteDialogFragmentArgs by navArgs()
    private lateinit var binding: DialogEditNoteBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogEditNoteBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle(getString(R.string.edit_note))
            .setPositiveButton(getString(R.string.edit)) { dialog, id ->

                val note = binding.etNote.text.toString().trim()
                // send result to favorites fragment that a note is updated
                setFragmentResult(
                    "note_request",
                    bundleOf("note" to note, "movieId" to args.movieId)
                )
                dialog.cancel()
            }
            .setNegativeButton(
                getString(R.string.cancel)
            ) { dialog, id ->
                dialog.cancel()
            }

        val note = args.note
        binding.etNote.setText(note)

        return dialog.create()
    }
}