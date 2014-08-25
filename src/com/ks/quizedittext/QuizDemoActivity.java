package com.ks.quizedittext;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.ks.quizedittext.QuizEditText.QuizEditTextListner;

public class QuizDemoActivity extends Activity {
	private QuizEditText edtItemName;
	private StringBuilder stringBuilder = new StringBuilder("       ");// Blank spaces for letters for word "CRICKET"

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		edtItemName = (QuizEditText) findViewById(R.id.editText1);

		edtItemName.setOriginalText(stringBuilder.toString());
		edtItemName.setLetterChip();
		edtItemName.setTextWatcher();
		edtItemName.setQuizEditTextListner(editTextListner);
	}

	private QuizEditTextListner editTextListner = new QuizEditTextListner() {

		@Override
		public void hasLetters(boolean hasLetters) {
			((Button) findViewById(R.id.btn_done)).setEnabled(hasLetters);

		}
	};

}
