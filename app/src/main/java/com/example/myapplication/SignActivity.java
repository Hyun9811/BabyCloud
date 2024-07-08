package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SignActivity extends AppCompatActivity {
    Button backBtn, signCheckBtn;
    TextView idCheckTv, pwCheckTv, pwCheckSecondTv, nameCheckTv;
    EditText nameCheckEt, idCheckEt, pwCheckEt, pwCheckSecondEt;
    String nameCheck, idCheck, pwCheck, pwCheckSecond,email,userImage;
    Intent intent;
    String TAG = "SignActivity";
    SignRetrofitData signRetrofitData;
    boolean signCheckId = false, signCheckPw = false, signCheckPwSecond = false, signCheckName = false;
    String signCheck;
    IdCheckThread idCheckThread;
    Pattern pw_pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,20}$")
            , namePattern = Pattern.compile("^[가-힣]*$")
            , idPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        init();
        signCheck();
        onClickListener();

    } // onCreate end

    void init() {
        pwCheckSecondEt = findViewById(R.id.pw_second_check_et);
        nameCheckTv = findViewById(R.id.name_check_tv);
        idCheckTv = findViewById(R.id.id_check_tv);
        backBtn = findViewById(R.id.back_btn);
        signCheckBtn = findViewById(R.id.sign_check_btn);
        nameCheckEt = findViewById(R.id.name_check_et);
        idCheckEt = findViewById(R.id.id_check_et);
        pwCheckEt = findViewById(R.id.pw_check_et);
        pwCheckTv = findViewById(R.id.pw_check_tv);
        pwCheckSecondTv = findViewById(R.id.pw_check_second_tv);
        intent = new Intent(SignActivity.this, LoginActivity.class);
        idCheckThread = new IdCheckThread();
        pwCheckTv.setText("숫자, 문자, 특수문자 모두 포함한 8~15자리 비밀번호를 입력해주세요");
        pwCheckTv.setTextSize(10);
    }  // init end

    void onClickListener() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
            }
        });
        signCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();

            }
        });
    }  // onClickListener end

    void register() {
        nameCheck = nameCheckEt.getText().toString();
        idCheck = idCheckEt.getText().toString();
        pwCheck = pwCheckEt.getText().toString();
        String social = "local";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RegisterInterface.REGISTER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterInterface api = retrofit.create(RegisterInterface.class);
        Call<String> call = api.getUserRegist(nameCheck, idCheck, pwCheck,email,social,userImage);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    Log.e("onSuccess" + response.body());
                    Log.d(TAG, "onResponse: " + response.body());
                    signCheck = response.body();
                    Toast.makeText(SignActivity.this,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                }
            } // onResponse end

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            } // onFailure end
        });
    }  // register end

    void idCheck() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RegisterIdCheckApi.REGISTER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterIdCheckApi api = retrofit.create(RegisterIdCheckApi.class);
        Call<String> call = api.getUserId(idCheck);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    Log.e("onSuccess" + response.body());
                    Log.d(TAG, "onResponse: " + response.body());
                    signCheck = response.body();

                    if (signCheck.equals("중복된 아이디가 존재합니다.")) {
                        idCheckTv.setText(signCheck);
                        idCheckTv.setTextColor(Color.parseColor("#DC3030"));
                        signCheckId = false;
                    } else {
                        idCheckTv.setText(signCheck);
                        idCheckTv.setTextColor(Color.parseColor("#3f51b5"));
                        signCheckId = true;
                    }
                    signOkBtn();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }  // idCheck end

    void signCheck() {

        nameCheckEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nameCheck = nameCheckEt.getText().toString();
                Matcher matcher = namePattern.matcher(nameCheck);
                if (matcher.matches() && nameCheck.length() > 1 && nameCheck.length() < 11) {
                    nameCheckTv.setText(null);
                    signCheckName = true;
                } else {
                    nameCheckTv.setText("2~10자리 한글을 입력해 주세요.");
                    nameCheckTv.setTextColor(Color.parseColor("#DC3030"));
                    signCheckName = false;
                }
                signOkBtn();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        idCheckEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                idCheck = idCheckEt.getText().toString();
                Matcher matcher = idPattern.matcher(idCheck);
                if (matcher.matches() && charSequence.length() > 0) {
                    if (charSequence.length() >= 5 && charSequence.length() <= 12) {
                        idCheck();
                    } else {
                        idCheckTv.setText("영문,숫자를 포함한 5~12자리 아이디를 입력해 주세요.");
                        idCheckTv.setTextColor(Color.parseColor("#DC3030"));
                        signCheckId = false;
                    }
                } else {
                    idCheckTv.setText("영문,숫자를 포함한 5~12자리 아이디를 입력해 주세요.");
                    idCheckTv.setTextColor(Color.parseColor("#DC3030"));
                    signCheckId = false;

                }
                signOkBtn();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        pwCheckEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pwCheck = pwCheckEt.getText().toString();
                Matcher matcher = pw_pattern.matcher(pwCheck);
                if (!matcher.matches()) {
                    // 일치하지 않을 경우
                    pwCheckTv.setText("숫자, 문자, 특수문자 모두 포함한 8~15자리 비밀번호를 입력해주세요");
                    pwCheckTv.setTextColor(Color.parseColor("#DC3030"));
                    signCheckPw = false;
                    if (pwCheckSecond != null && !pwCheck.equals(pwCheckSecond)) {
                        Log.d(TAG, "onTextChanged: 들어왔니?");
                        signCheckPwSecond = false;
                        pwCheckSecondTv.setText("비밀번호를 확인해 주세요.");
                        pwCheckSecondTv.setTextColor(Color.parseColor("#DC3030"));
                    }
                } else {
                    //일치할경우
                    pwCheckTv.setText(null);
                    signCheckPw = true;
                    if (pwCheckSecond != null && !pwCheck.equals(pwCheckSecond)) {
                        Log.d(TAG, "onTextChanged: 들어왔니?");
                        signCheckPwSecond = false;
                        pwCheckSecondTv.setText("비밀번호를 확인해 주세요.");
                        pwCheckSecondTv.setTextColor(Color.parseColor("#DC3030"));
                    } else if (pwCheckSecond != null && pwCheck.equals(pwCheckSecond)) {
                        signCheckPwSecond = true;
                        Log.d(TAG, "onTextChanged: pwCheck : " + pwCheck + " pwCheckSecond : " + pwCheckSecond);
                        Log.d(TAG, "onTextChanged: 안들어옴");
                        pwCheckSecondTv.setText(null);
                    }


                }
                signOkBtn();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        pwCheckSecondEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pwCheckSecond = pwCheckSecondEt.getText().toString();
                if (charSequence.length() > 0) {
                    if (charSequence.toString().equals(pwCheck)) {
                        pwCheckSecondTv.setText(null);
                        signCheckPwSecond = true;
                    } else {
                        pwCheckSecondTv.setText("비밀번호를 확인해 주세요.");
                        pwCheckSecondTv.setTextColor(Color.parseColor("#DC3030"));
                        signCheckPwSecond = false;
                    }
                } else {
                    pwCheckSecondTv.setText(null);
                    signCheckPwSecond = false;

                }
                signOkBtn();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signOkBtn();

    }  // signCheck end

    void signOkBtn() {
        Log.d(TAG, "signOkBtn: " + "signCheckId : " + signCheckId + " signCheckPw : " + signCheckPw + " signCheckPwSecond : " + signCheckPwSecond);
        if (signCheckId == true && signCheckPw == true && signCheckPwSecond == true && signCheckName == true) {
            signCheckBtn.setEnabled(true);
            signCheckBtn.setBackgroundResource(R.drawable.btn_color);
        } else {
            signCheckBtn.setEnabled(false);
            signCheckBtn.setBackgroundResource(R.drawable.btn_color_after);

        }

    }

}  // SignActivity Class end