package com.example.eungapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {
    private EditText userNameInput;
    private EditText idInput;
    private EditText passwordInput;
    private EditText passwordCheckInput;
    private Button signUpButton;

    private UserItem userItem;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View signInView = inflater.inflate(R.layout.fragment_signup, container, false);
        userNameInput = (EditText) signInView.findViewById(R.id.signUpUserNameInput);
        idInput = (EditText) signInView.findViewById(R.id.signUpIdInput);
        passwordInput = (EditText) signInView.findViewById(R.id.signUpPasswordInput);
        passwordCheckInput = (EditText) signInView.findViewById(R.id.signUpPasswordCheckInput);
        signUpButton = (Button) signInView.findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userItem = new UserItem(userNameInput.getText().toString(), idInput.getText().toString());
                if (userItem.getUserName().equals("")) {
                    Toast myToast = Toast.makeText(container.getContext(),"이름을 입력하세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (userItem.getEmail().equals("")) {
                    Toast myToast = Toast.makeText(container.getContext(),"이메일 입력하세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (passwordInput.getText().toString().equals("")) {
                    Toast myToast = Toast.makeText(container.getContext(),"비밀번호를 입력하세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (passwordCheckInput.getText().toString().equals("")) {
                    Toast myToast = Toast.makeText(container.getContext(),"2차 비밀번호를 입력하세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (!passwordCheckInput.getText().toString().equals(passwordInput.getText().toString())) {
                    Toast myToast = Toast.makeText(container.getContext(),"비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else {
                    Call<AuthenticateItem> signUpCall = MyAPICall.mMyAPI.signup(userItem.getUserName(), userItem.getEmail(), passwordInput.getText().toString());
                    signUpCall.enqueue(new Callback<AuthenticateItem>() {
                        @Override
                        public void onResponse(Call<AuthenticateItem> call, Response<AuthenticateItem> response) {
                            AuthenticateItem authenticateItem = response.body();
                            if (authenticateItem.getMessage().equals("success")) {
                                Toast myToast = Toast.makeText(container.getContext(),"회원가입 성공", Toast.LENGTH_SHORT);
                                myToast.show();
                            }
                            else {
                                Toast myToast = Toast.makeText(container.getContext(),"회원가입 실패", Toast.LENGTH_SHORT);
                                myToast.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthenticateItem> call, Throwable t) {
                            Log.d("SignUp", "Fail msg : " + t.getMessage()); // 오류 메시지 출력
                        }
                    });
                }
            }
        });

        return signInView;
    }
}

