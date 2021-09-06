package com.example.ishop

import android.text.Html
import android.text.Spanned
import com.example.ishop.database.GroceryItem

fun formatItems(nights: List<GroceryItem>): Spanned {
    val sb = StringBuilder()
    sb.apply {
        nights.forEach {
            append("<br>")
            append(it.Item)
            append("<br>")
        }
    }
    return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
}