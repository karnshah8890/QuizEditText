package com.ks.quizedittext;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This class provides quiz EditText functionality.
 * @author Karn Shah
 *
 */
public class QuizEditText extends EditText {
		
	    private String originalText = "";
	    private int characterLimit=0;
	    private QuizEditTextListner editTextListner;
	    
	    /**
	     * Interface to update whether any letter is inserted or not.
	     */
	    interface QuizEditTextListner{
	    	void hasLetters(boolean hasLetters);
	    }
	    
	    public QuizEditText(Context context) {
	        super(context);
	    }

	    public QuizEditText(Context context, AttributeSet attrs){
	        super(context, attrs);
	    }

	    public QuizEditText(Context context, AttributeSet attrs, int defStyle){
	        super(context, attrs, defStyle);
	    }

	    /**
	     * TextWatcher for EditText
	     */
	    private TextWatcher textWather = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				Log.d(VIEW_LOG_TAG, "s : "+s + " start : "+start + " count :"+count);
				if (s!=null && s.length()>0) {
					removeTextChangedListener(textWather);
					if (count==0) {
						final StringBuilder demo=new StringBuilder(originalText);
						demo.replace(start, start+1, " ");
						originalText=demo.toString();
						
					}else if(start<characterLimit) {
						if (originalText.charAt(start)==' ') {
							final StringBuilder demo=new StringBuilder(originalText);
							demo.replace(start, start+1, s.subSequence(start, start+1).toString());
							originalText=demo.toString();
							start++;
						}
					}
					if (editTextListner!=null) {
						editTextListner.hasLetters(hasLetters(originalText));
					}
					setLetterChip();
					setSelection(start);
					addTextChangedListener(textWather);
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			@Override
			public void afterTextChanged(Editable s) {}
		};
		
		private boolean hasLetters(String name) {
		    return !name.matches("[\\s]+");
		}
		
		/**
		 * Method to set text to create blank dashes
		 * @param originalText
		 */
		public void setOriginalText(String originalText) {
			this.originalText = originalText;
			characterLimit=originalText.length();
		}
		
		/**
		 * get entered text with space
		 * @return
		 */
		public String getOriginalText() {
			return originalText;
		}

		/**
		 * Create chip for every letter and add it to edit text
		 */
		public void setLetterChip() {
			final SpannableStringBuilder ssb = new SpannableStringBuilder(originalText);
			int x =0;
			final LayoutInflater lf = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			for (int i = 0; i < originalText.length(); i++) {
				TextView textView = (TextView) lf.inflate(R.layout.quiz_textview, null);
				textView.setText(originalText.charAt(i)+""); // set text
				// capture bitmap of generated text view
				int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
				textView.measure(spec, spec);
				textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
				Bitmap b = Bitmap.createBitmap(textView.getWidth(), textView.getHeight(),Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(b);
				canvas.translate(-textView.getScrollX(), -textView.getScrollY());
				textView.draw(canvas);
				textView.setDrawingCacheEnabled(true);
				Bitmap cacheBmp = textView.getDrawingCache();
				Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
				textView.destroyDrawingCache(); 
				BitmapDrawable bmpDrawable = new BitmapDrawable(getResources(),viewBmp);
				bmpDrawable.setBounds(0, 0,bmpDrawable.getIntrinsicWidth(),bmpDrawable.getIntrinsicHeight());
				// create and set image span 
				ssb.setSpan(new ImageSpan(bmpDrawable),x ,x + 1 , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				x = x+ 1;
			}
			// set chips span 
			setText(ssb);
			setSelection(0);
		}

		/**
		 * Method to set TextWatcher
		 */
		public void setTextWatcher() {
			addTextChangedListener(textWather);
		}

		/**
		 * set editTextListner
		 * @param editTextListner
		 */
		public void setQuizEditTextListner(QuizEditTextListner editTextListner) {
			this.editTextListner=editTextListner;
			
		}
	}