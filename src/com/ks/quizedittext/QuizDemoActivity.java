package com.ks.quizedittext;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.ks.quizedittext.QuizEditText.QuizEditTextListner;
import com.ks.quizedittext.palette.DominantColorCalculator;

public class QuizDemoActivity extends Activity implements OnClickListener {
	
	private int [] images = {R.drawable.ben10,R.drawable.bheem,R.drawable.doremon,R.drawable.mrbean,R.drawable.tomjerry};
	private String[] names={"BEN10","BHEEM","DOREMON","MRBEAN","TOM&JERRY"};
	private QuizEditText edtItemName;
	private StringBuilder stringBuilder = new StringBuilder("       ");// Blank spaces for letters for word "CRICKET"
	private int level=0;
	private ImageView img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		edtItemName = (QuizEditText) findViewById(R.id.editText1);
		img = (ImageView) findViewById(R.id.imageView1);
		img.setImageResource(images[level]);
		stringBuilder = new StringBuilder();
		for (int i = 0; i < names[level].length(); i++) {
			stringBuilder.append(" ");
		}
		edtItemName.setOriginalText(stringBuilder.toString());
		edtItemName.setLetterChip();
		edtItemName.setTextWatcher();
		edtItemName.setQuizEditTextListner(editTextListner);
		
		DominantColorCalculator calculator =new DominantColorCalculator(BitmapFactory.decodeResource(getResources(), images[level]));
		edtItemName.setBackgroundColor(calculator.getColorScheme().primaryAccent);
		edtItemName.setTextColor(calculator.getColorScheme().primaryText);
	}

	private QuizEditTextListner editTextListner = new QuizEditTextListner() {

		@Override
		public void hasLetters(boolean hasLetters) {
			((ImageButton) findViewById(R.id.btn_done)).setEnabled(hasLetters);
		}
	};

	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.btn_done) {
			if (edtItemName.getText().toString().equalsIgnoreCase(names[level])) {
				level++;
				if (level<names.length) {
					img.setImageResource(images[level]);
					stringBuilder = new StringBuilder();
					for (int i = 0; i < names[level].length(); i++) {
						stringBuilder.append(" ");
					}
					edtItemName.setOriginalText(stringBuilder.toString());
					edtItemName.setLetterChip();
					DominantColorCalculator calculator =new DominantColorCalculator(BitmapFactory.decodeResource(getResources(), images[level]));
					edtItemName.setBackgroundColor(calculator.getColorScheme().primaryAccent);
					edtItemName.setTextColor(calculator.getColorScheme().primaryText);
				}else {
					img.setImageResource(R.drawable.images);
					((ImageButton) findViewById(R.id.btn_done)).setEnabled(false);
					edtItemName.setQuizEditTextListner(null);
					DominantColorCalculator calculator =new DominantColorCalculator(BitmapFactory.decodeResource(getResources(), images[level]));
					edtItemName.setBackgroundColor(calculator.getColorScheme().primaryAccent);
					edtItemName.setTextColor(calculator.getColorScheme().primaryText);
				}
			}else {
				Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
				img.startAnimation(shake);
			}
		}
		
	}

}
