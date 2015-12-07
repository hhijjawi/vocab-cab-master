package com.vocab.vocab.Verify;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.vocab.vocab.ModelData.WordListSingleton;
import com.vocab.vocab.R;
import com.vocab.vocab.Utilities.Utils;

/**
 * Created by Hisham on 11/30/2015.
 */
public class VerifyActivity extends AppCompatActivity {
    public Button mDefinitionOne;
    public Button mDefinitionTwo;
    public Button mDefinitionThree;
    public Button mTitleButton;
    public Context mContext;
    int mNumTimesRun = 0;
    int mWordIndex = 0;
    private int mPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_verify);
        Log.d("onCreateCalled", "onCreateCalled");
        wireUpLayout(mPosition);
    }

    public int getmPosition() {
        return mPosition;
    }

    public void wireUpLayout(int position) {

        if (WordListSingleton.getInstance().getWordList().get(position).getmFamiliarityScore() > Utils.randInt(1, 5)) {
            position++;
            wireUpLayout(position);
            return;
        }
        ProgressDialog pd = new ProgressDialog(this);

        mTitleButton = (Button) findViewById(R.id.wordTitle);
        mDefinitionOne = (Button) findViewById(R.id.definition1);
        mDefinitionTwo = (Button) findViewById(R.id.definition2);
        mDefinitionThree = (Button) findViewById(R.id.definition3);
//        FamScoreCheck
        switch (Utils.randInt(1, 3)) {
            case 1:
                mDefinitionOne.setText(WordListSingleton.getInstance().getWordList().get(position).getmDefinition());
                mDefinitionTwo.setText(WordListSingleton.getInstance().getWordList().get(Utils.randInt(1, WordListSingleton.getInstance().getWordList().size()-1)).getmDefinition());
                mDefinitionThree.setText(WordListSingleton.getInstance().getWordList().get(Utils.randInt(1, WordListSingleton.getInstance().getWordList().size()-1)).getmDefinition());
            case 2:
                mDefinitionTwo.setText(WordListSingleton.getInstance().getWordList().get(position).getmDefinition());
                mDefinitionTwo.setText(WordListSingleton.getInstance().getWordList().get(Utils.randInt(1, WordListSingleton.getInstance().getWordList().size()-1)).getmDefinition());
                mDefinitionOne.setText(WordListSingleton.getInstance().getWordList().get(Utils.randInt(1, WordListSingleton.getInstance().getWordList().size()-1)).getmDefinition());

            case 3:
                mDefinitionThree.setText(WordListSingleton.getInstance().getWordList().get(position).getmDefinition());
                mDefinitionTwo.setText(WordListSingleton.getInstance().getWordList().get(Utils.randInt(1, WordListSingleton.getInstance().getWordList().size()-1)).getmDefinition());
                mDefinitionOne.setText(WordListSingleton.getInstance().getWordList().get(Utils.randInt(1, WordListSingleton.getInstance().getWordList().size()-1)).getmDefinition());

        }
        mTitleButton.setText(WordListSingleton.getInstance().getWordList().get(position).getWord());
        final int finalPosition = position;
        mDefinitionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // StackOverflow
             /*   new AlertDialog.Builder(mContext)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();           */
                if (
                        mDefinitionOne.getText().toString().equals(WordListSingleton.getInstance().getWordList().get(getmPosition()).getmDefinition())) {
                    setmPosition(finalPosition +1);
                    wireUpLayout(getmPosition());
                }else {
                    //new AlertDialog.Builder(mContext).setMessage("Wrong. Try Again").setIcon(R.drawable.wrong).show();
                    final Dialog dialog = new Dialog(mContext);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.wrong);
                    dialog.setCancelable(true);
                    LinearLayout wrongLayout = (LinearLayout) dialog.findViewById(R.id.wrongLayout);
                    wrongLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }

            }
        });
        mDefinitionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        mDefinitionTwo.getText().toString().equals(WordListSingleton.getInstance().getWordList().get(getmPosition()).getmDefinition())) {
                    setmPosition(finalPosition +1);
                    wireUpLayout(getmPosition());
                }else {
                    //new AlertDialog.Builder(mContext).setMessage("Wrong. Try Again").setIcon(R.drawable.wrong).show();
                    final Dialog dialog = new Dialog(mContext);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.wrong);
                    dialog.setCancelable(true);
                    LinearLayout wrongLayout = (LinearLayout) dialog.findViewById(R.id.wrongLayout);
                    wrongLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }

            }
        });

        mDefinitionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        mDefinitionThree.getText().toString().equals(WordListSingleton.getInstance().getWordList().get(getmPosition()).getmDefinition())) {
                    setmPosition(finalPosition +1);
                    wireUpLayout(getmPosition());
                    return;
                }else {
                    //new AlertDialog.Builder(mContext).setMessage("Wrong. Try Again").setIcon(R.drawable.wrong).show();
                    final Dialog dialog = new Dialog(mContext);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.wrong);
                    dialog.setCancelable(true);
                    LinearLayout wrongLayout = (LinearLayout) dialog.findViewById(R.id.wrongLayout);
                    wrongLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }

            }
        });



    }


    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }
}
