package uz.gita.maxwayappclone.presentation.util

import uz.gita.maxwayappclone.data.model.OrdersUIData
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.getDate(): String {
	return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))
}

fun Long.toFormatted(): String {
	val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
		groupingSeparator = ' '
	}
	return DecimalFormat("#,###", symbols).format(this)
}

fun List<OrdersUIData>.getCurrent() =
	this.filter { (System.currentTimeMillis() - it.createTime < 1200000) }