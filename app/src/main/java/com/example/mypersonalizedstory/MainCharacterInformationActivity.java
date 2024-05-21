package com.example.mypersonalizedstory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainCharacterInformationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerLanguage , spinnerLength;
    SeekBar seekBar;
    TextView textViewSeekBar;
    ChipGroup chipGroup;
    String language="English",size="150",age="5", gender="male",genres="Mystery",name="ABC",moral="Good Behaviour";
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    AppCompatButton button;
    EditText nameInputText, moralInputText;

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardItem> cardItemList;

    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_character_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_char), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameInputText= findViewById(R.id.nameInputText);
        seekBar=findViewById(R.id.seekbar);
        textViewSeekBar=findViewById(R.id.textView5);
        spinnerLanguage=findViewById(R.id.spinner1);
        spinnerLength=findViewById(R.id.spinner2);
        radioGroup = findViewById(R.id.gender);
        chipGroup=findViewById(R.id.chipGroup);
      //  moralInputText=findViewById(R.id.moralInputText);
        button=findViewById(R.id.button);
        scrollView=findViewById(R.id.scrollView);

        createGenreChips();

        ArrayAdapter<CharSequence> adapterLanguage = ArrayAdapter.createFromResource(this,R.array.languages, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterLength = ArrayAdapter.createFromResource(this,R.array.size, android.R.layout.simple_spinner_item);
        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapterLength.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLanguage.setAdapter(adapterLanguage);
        spinnerLength.setAdapter(adapterLength);
        spinnerLanguage.setOnItemSelectedListener(this);
        spinnerLength.setOnItemSelectedListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                age= String.valueOf(progress);
                textViewSeekBar.setText(age);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

      /*  moralInputText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) scrollView.fullScroll(View.FOCUS_DOWN);

            }
        });
*/
        recyclerView = findViewById(R.id.moralRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        String [] moralTiltles = getResources().getStringArray(R.array.moralTitle);
        String [] moralsubTexts = getResources().getStringArray(R.array.moralSubText);

        cardItemList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            cardItemList.add(new CardItem( moralTiltles[i],  moralsubTexts[i]));
        }

        cardAdapter = new CardAdapter(cardItemList);
        recyclerView.setAdapter(cardAdapter);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = nameInputText.getText().toString();

                int selectedGenderId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedGenderId);
                gender=radioButton.getText().toString();


                CardItem selectedmoral = cardAdapter.getCardItem();
                if(selectedmoral != null && selectedmoral.getTitle()!="")
                moral = selectedmoral.getTitle()+" "+selectedmoral.getSubtext();
                else
                    moral="Good behaviour";

                Toast.makeText(MainCharacterInformationActivity.this, moral+"", Toast.LENGTH_SHORT).show();


             /*   Log.e("name",name);
                Log.e("language",language);
                Log.e("length",size);
                Log.e("age",age);
                Log.e("gender",gender);
                Log.e("language",language);
                Log.e("genres",genres);
                Log.e("moral",moral);*/

                String storyPromptMessage = "You are an amazing story teller.Please generate a personalized bed time story "+
                        "for a child named as "+name+", "+
                        "who's age is "+age+", "+
                        "gender is "+gender+", "+
                        "the length of story should be in between "+size+" words, "+
                        "the language of story should be"+language+" , "+
                        "the genre of the story should be "+genres+", "+
                        "moral of the story will be"+moral+", "+
                        "Always generate the output in following format:" +
                        "Title: Main title of the story"+
                        "Story: Story text"+
                        "Moral:Moral of the story";

                Log.e("storyPromptMessage",storyPromptMessage);

                if(isNetworkConnected()){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("prompt_message", storyPromptMessage);
                    startActivity(intent);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainCharacterInformationActivity.this);
                    builder.setMessage("No internet connection,please check your internet connection  ")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    // Create the Alert dialog
                    AlertDialog alertDialog = builder.create();
                    // Show the Alert Dialog box
                    alertDialog.show();
                }
            }
        });


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    void createGenreChips(){
        List<String>genreList = Arrays.asList(getResources().getStringArray(R.array.genres));

        Random random = new Random();
        for(String s: genreList)
        {
            Chip chip =(Chip) LayoutInflater.from(MainCharacterInformationActivity.this).inflate(R.layout.chip_layout,null);
            chip.setText(s);
            chip.setId(random.nextInt());
            chipGroup.addView(chip);
        }
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                if(!checkedIds.isEmpty())
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i: checkedIds)
                    {
                        Chip chip = findViewById(i);
                        stringBuilder.append(", ").append(chip.getText());
                    }
                    genres=stringBuilder.toString().replaceFirst(",","");
                }
            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ((TextView) view).setTextColor(Color.WHITE);
        if (parent.getId() == R.id.spinner1) {
            language = parent.getItemAtPosition(position).toString();
        } else if (parent.getId() == R.id.spinner2) {
            size = parent.getItemAtPosition(position).toString();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        spinnerLanguage.setSelection(getIndex(spinnerLanguage, "English"));
        spinnerLength.setSelection(getIndex(spinnerLength, "150-200"));
    }
    private int getIndex(Spinner spinner, String value) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        return adapter.getPosition(value);
    }
}