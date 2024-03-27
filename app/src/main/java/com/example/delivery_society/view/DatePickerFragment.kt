package com.example.delivery_society.view

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment : DialogFragment() {

    private lateinit var mainActivity: Activity
    lateinit var onDismissListener: () -> Any

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mainActivity = requireActivity()

        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR);
        var month = calendar.get(Calendar.MONTH);
        var day = calendar.get(Calendar.DAY_OF_MONTH);

        return DatePickerDialog(mainActivity, mainActivity as (DatePickerDialog.OnDateSetListener),
            year, month, day);
    }

    override fun onDismiss(dialog: DialogInterface) {
        if (this::onDismissListener.isInitialized) {
            onDismissListener()
        }

        super.onDismiss(dialog)
    }
}