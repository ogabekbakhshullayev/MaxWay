package uz.gita.maxwayappclone.presentation.dialogs.bottomSheeteDatePick

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.maxwayappclone.R
import java.util.*

class DatePickerBottomSheet : BottomSheetDialogFragment() {

    var onDateSelectedListener: ((day: String, month: String, year: String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_date, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val datePicker = view.findViewById<DatePicker>(R.id.date_picker)
        val btnConfirm = view.findViewById<Button>(R.id.continue_btn)

        val calendar = Calendar.getInstance()
        datePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            null
        )

        btnConfirm.setOnClickListener {
            var day = datePicker.dayOfMonth.toString()
            var month = (datePicker.month + 1).toString()
            var year = datePicker.year.toString()
            if (day.length < 2) day = "0$day"
            if (month.length < 2) month = "0$month"
            onDateSelectedListener?.invoke(day, month, year)
            dismiss()
        }
    }
}