package project.fpt.edu.vn.registerscreen.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import project.fpt.edu.vn.registerscreen.R;

public class UserPasswordChangeActivity extends AppCompatActivity {

    ImageButton imgBtnBack;
    TextView txtSave;
    EditText edtOldPass, edtNewPass, edtConfirmPass;
    String oldPass, newPass, confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_password_change);
        anhXa();
        initialize();

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserPasswordChangeActivity.this);
                builder.setTitle("Đăng xuất");
                builder.setMessage("Bạn muốn lưu thay đổi?");
                builder.setNegativeButton("Không", null);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(UserPasswordChangeActivity.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                        changePass();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    public void anhXa(){
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        txtSave = (TextView) findViewById(R.id.txtSave);
        edtOldPass = (EditText) findViewById(R.id.edtOldPass);
        edtNewPass = (EditText) findViewById(R.id.edtNewPass);
        edtConfirmPass = (EditText) findViewById(R.id.edtConfirmPass);
    }

    public void initialize(){
        oldPass = edtOldPass.getText().toString().trim();
        newPass = edtNewPass.getText().toString().trim();
        confirmPass = edtConfirmPass.getText().toString().trim();

    }

    public boolean validate(){
        boolean valid = true;
        if(oldPass.isEmpty()){
            edtOldPass.setError("Hãy nhập mật khẩu cũ");
            valid = false;
        }
        if(newPass.isEmpty() || !newPass.equals(confirmPass)){
            edtNewPass.setError("Hãy nhập mật khẩu mới giống nhau");
            edtConfirmPass.setError("Hãy nhập mật khẩu mới giống nhau");
            valid = false;
        }
        return valid;
    }

    public void changePass(){
        if(!validate()){
            Toast.makeText(this, "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Đã đổi mật khẩu", Toast.LENGTH_SHORT).show();
        }
    }
}
