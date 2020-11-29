package com.example.edunachaladmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        firebaseAuth=FirebaseAuth.getInstance();
    }

    public void currentAppscQuiz(View view) {
        Intent intent =  new Intent(this,AppscQuiz.class);
        intent.putExtra("flag","appsc current");
        startActivity(intent);
    }

    public void LogOutMe(View view) {
        firebaseAuth.signOut();
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void appscCurrentQuizResponse(View view) {
        Intent intent = new Intent(this,AppscQuizResponses.class);
        intent.putExtra("flag","appsc current");
        startActivity(intent);
    }

    public void allUsers(View view) {
        Intent intent = new Intent(this, DisplayUsers.class);
        startActivity(intent);
    }

    public void upscQuiz(View view) {
        Intent intent =  new Intent(this,AppscQuiz.class);
        intent.putExtra("flag","upsc");
        startActivity(intent);
    }

    public void appscQuiz(View view) {
        Intent intent =  new Intent(this,AppscQuiz.class);
        intent.putExtra("flag","appsc");
        startActivity(intent);
    }

    public void currentAffairs(View view) {
        Intent intent = new Intent(this, CurrentAffairsDisplay.class);
        startActivity(intent);
    }

    public void appscQuizResponse(View view) {
        Intent intent = new Intent(this,AppscQuizResponses.class);
        intent.putExtra("flag","appsc");
        startActivity(intent);
    }

    public void upscQuizResponse(View view) {
        Intent intent = new Intent(this,AppscQuizResponses.class);
        intent.putExtra("flag","upsc");
        startActivity(intent);
    }

    public void appsbMaths(View view) {
        Intent intent = new Intent(this,DisplayPdf.class);
        intent.putExtra("flagExtra","appsb");
        intent.putExtra("flagExtra1","maths");
        startActivity(intent);
    }

    public void appsbReasoning(View view) {
        Intent intent = new Intent(this,DisplayPdf.class);
        intent.putExtra("flagExtra","appsb");
        intent.putExtra("flagExtra1","reasoning");
        startActivity(intent);
    }

    public void appsbEnglish(View view) {
        Intent intent = new Intent(this,DisplayPdf.class);
        intent.putExtra("flagExtra","appsb");
        intent.putExtra("flagExtra1","english");
        startActivity(intent);
    }

    public void appsbGK(View view) {
        Intent intent = new Intent(this,DisplayPdf.class);
        intent.putExtra("flagExtra","appsb");
        intent.putExtra("flagExtra1","gk");
        startActivity(intent);
    }

    public void sscBankReasoning(View view) {
        Intent intent = new Intent(this,DisplayPdf.class);
        intent.putExtra("flagExtra","sscBank");
        intent.putExtra("flagExtra1","reasoning");
        startActivity(intent);
    }

    public void sscBankMaths(View view) {
        Intent intent = new Intent(this,DisplayPdf.class);
        intent.putExtra("flagExtra","sscBank");
        intent.putExtra("flagExtra1","maths");
        startActivity(intent);
    }

    public void sscBankEnglish(View view) {
        Intent intent = new Intent(this,DisplayPdf.class);
        intent.putExtra("flagExtra","sscBank");
        intent.putExtra("flagExtra1","english");
        startActivity(intent);
    }

    public void sscBankGK(View view) {
        Intent intent = new Intent(this,DisplayPdf.class);
        intent.putExtra("flagExtra","sscBank");
        intent.putExtra("flagExtra1","gk");
        startActivity(intent);
    }

    public void upscAnswerWriting(View view) {
        Intent intent = new Intent(this,DisplayPdf.class);
        intent.putExtra("flagExtra","upsc");
        intent.putExtra("flagExtra1","answerWriting");
        startActivity(intent);
    }

    public void upscEssayWriting(View view) {
        Intent intent = new Intent(this,DisplayPdf.class);
        intent.putExtra("flagExtra","upsc");
        intent.putExtra("flagExtra1","essayWriting");
        startActivity(intent);
    }

    public void upscStudyNotes(View view) {
        Intent intent = new Intent(this,DisplayPdf.class);
        intent.putExtra("flagExtra","upsc");
        intent.putExtra("flagExtra1","studyNotes");
        startActivity(intent);
    }

    public void appscAnswerWriting(View view) {
        Intent intent = new Intent(this,DisplayPdf.class);
        intent.putExtra("flagExtra","appsc");
        intent.putExtra("flagExtra1","answerWriting");
        startActivity(intent);
    }

    public void appscStudyNotes(View view) {
        Intent intent = new Intent(this,DisplayPdf.class);
        intent.putExtra("flagExtra","appsc");
        intent.putExtra("flagExtra1","studyNotes");
        startActivity(intent);
    }

    public void appscEssayWriting(View view) {
        Intent intent = new Intent(this,DisplayPdf.class);
        intent.putExtra("flagExtra","appsc");
        intent.putExtra("flagExtra1","essayWriting");
        startActivity(intent);
    }
}