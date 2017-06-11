package com.sedsoftware.bakingapp.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class TextViewUtils {

  // Src: https://stackoverflow.com/questions/10979821/

  public static void setTextWithSpan(TextView textView, String fullText, String styledText, StyleSpan style) {
    SpannableStringBuilder sb = new SpannableStringBuilder(fullText);
    int start = fullText.indexOf(styledText);
    int end = start + styledText.length();
    sb.setSpan(style, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    textView.setText(sb);
  }

}
