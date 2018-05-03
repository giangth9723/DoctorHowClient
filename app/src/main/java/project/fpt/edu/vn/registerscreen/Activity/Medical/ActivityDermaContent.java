package project.fpt.edu.vn.registerscreen.Activity.Medical;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.Model.Emr_dermatology;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;
import project.fpt.edu.vn.registerscreen.Session;


public class ActivityDermaContent extends AppCompatActivity {

    LinearLayout lineHidden02, lineHidden03, lineHidden04, lineHidden05, lineHidden06;
    TextView txtMedicalRecordII, txtMedicalRecordIII,
            txtMedicalRecordIV, txtMedicalRecordV, txtMedicalRecordVI, txtDoctorName, txtPatientName,txtEmrDate;
    ImageButton imgBtnBack;
    Session session;
    Emr_dermatology emr_dermatology;

    CheckBox cbDiUng, cbMaTuy, cbRuouBia, cbThuocLa, cbThuocLao, cbKhac;
    EditText edtQuaTrinh, edtTienSu, edtDiUng, edtMaTuy, edtRuouBia, edtThuocLa, edtThuocLao, edtKhac,
            edtGiaDinh,
            edtMach, edtNhietDo, edtHuyetAp, edtNhipTho, edtCanNang,
            edtToanThan, edtCoNang, edtTonThuong, edtTuanHoan, edtHoHap, edtTieuHoa, edtThan, edtThanKinh, edtCQKhac,
            edtXetNghiem, edtTomTat,
            edtBenhChinh, edtBenhKemTheo, edtPhanBiet, edtTienLuong, edtHuongDieuTri,
            edtQuaTrinhBenh, edtKetQua, edtPhuongAn, edtTinhTrang, edtHuongTiepTheo;

    Boolean mIsBound;
    SocketApplication socketApplication;
    SocketServiceProvider mBoundService;
    protected ServiceConnection socketConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("serviceConnection", "connected");
            mBoundService = ((SocketServiceProvider.LocalBinder) service).getService();
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
            mIsBound = false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_derma_content);
        onCreateSocket();
        anhXa();
        anhXaText();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("emr_dermatology");
        emr_dermatology = (Emr_dermatology)bundle.getSerializable("emr_dermatology");
        LoadData();
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityDermaContent.this,ActivityDermaExaminationHistory.class));
                finish();
            }
        });

        txtMedicalRecordII.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lineHidden02.getVisibility() != view.VISIBLE){
                    expand(lineHidden02);
                }else{
                    collapse(lineHidden02);
                }
            }
        });

        txtMedicalRecordIII.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lineHidden03.getVisibility() != view.VISIBLE){
                    expand(lineHidden03);
                }else{
                    collapse(lineHidden03);
                }
            }
        });

        txtMedicalRecordIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lineHidden04.getVisibility() != view.VISIBLE){
                    expand(lineHidden04);
                }else{
                    collapse(lineHidden04);
                }
            }
        });

        txtMedicalRecordV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lineHidden05.getVisibility() != view.VISIBLE){
                    expand(lineHidden05);
                }else{
                    collapse(lineHidden05);
                }
            }
        });

        txtMedicalRecordVI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lineHidden06.getVisibility() != view.VISIBLE){
                    expand(lineHidden06);
                }else{
                    collapse(lineHidden06);
                }
            }
        });
    }
    private void onCreateSocket(){
        socketApplication = (SocketApplication) getApplication();
        Log.d("Check", "Login created");
        if (socketApplication.getSocket() != null) {
            Log.d("Socket", " is not null");
            startService(new Intent(getBaseContext(), SocketServiceProvider.class));
            doBindService();
        } else {
        }
    }
    public void anhXa(){
        lineHidden02 = (LinearLayout) findViewById(R.id.lineHidden02);
        lineHidden03 = (LinearLayout) findViewById(R.id.lineHidden03);
        lineHidden04 = (LinearLayout) findViewById(R.id.lineHidden04);
        lineHidden05 = (LinearLayout) findViewById(R.id.lineHidden05);
        lineHidden06 = (LinearLayout) findViewById(R.id.lineHidden06);

        txtMedicalRecordII = (TextView) findViewById(R.id.txtMedicalRecordII);
        txtMedicalRecordIII = (TextView) findViewById(R.id.txtMedicalRecordIII);
        txtMedicalRecordIV = (TextView) findViewById(R.id.txtMedicalRecordIV);
        txtMedicalRecordV = (TextView) findViewById(R.id.txtMedicalRecordV);
        txtMedicalRecordVI = (TextView) findViewById(R.id.txtMedicalRecordVI);

        txtDoctorName = (TextView) findViewById(R.id.txtDoctorName);
        txtPatientName = (TextView) findViewById(R.id.txtPatientName);
        txtEmrDate = (TextView) findViewById(R.id.txtEmrDate);

        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        session = new Session(this);
    }

    public void anhXaText(){
        //Hoi benh
        edtQuaTrinh = (EditText) findViewById(R.id.edtProcess);
        edtTienSu = (EditText) findViewById(R.id.edtAnamnesis);
        edtDiUng = (EditText) findViewById(R.id.edtDiUng);
        edtMaTuy = (EditText) findViewById(R.id.edtMaTuy);
        edtRuouBia = (EditText) findViewById(R.id.edtRuouBia);
        edtThuocLa = (EditText) findViewById(R.id.edtThuocLa);
        edtThuocLao = (EditText) findViewById(R.id.edtThuocLao);
        edtKhac = (EditText) findViewById(R.id.edtKhac);
        edtGiaDinh = (EditText) findViewById(R.id.edtFamily);

        cbDiUng = (CheckBox) findViewById(R.id.cbDiUng);
        cbMaTuy = (CheckBox) findViewById(R.id.cbMaTuy);
        cbRuouBia = (CheckBox) findViewById(R.id.cbRuouBia);
        cbThuocLa = (CheckBox) findViewById(R.id.cbThuocLa);
        cbThuocLao = (CheckBox) findViewById(R.id.cbThuocLao);
        cbKhac = (CheckBox) findViewById(R.id.cbKhac);

        //Kham benh
        edtMach = (EditText) findViewById(R.id.edtCircuit);
        edtNhietDo = (EditText) findViewById(R.id.edtTemperature);
        edtHuyetAp = (EditText) findViewById(R.id.edtPressure);
        edtNhipTho = (EditText) findViewById(R.id.edtBreath);
        edtCanNang = (EditText) findViewById(R.id.edtWeight);

        edtToanThan = (EditText) findViewById(R.id.edtBody);
        edtCoNang = (EditText) findViewById(R.id.edtSymptoms);
        edtTonThuong = (EditText) findViewById(R.id.edtInjury);
        edtTuanHoan = (EditText) findViewById(R.id.edtCyclic);
        edtHoHap = (EditText) findViewById(R.id.edtRespiratory);
        edtTieuHoa = (EditText) findViewById(R.id.edtDigest);
        edtThan = (EditText) findViewById(R.id.edtKidney);
        edtThanKinh = (EditText) findViewById(R.id.edtNerve);
        edtCQKhac = (EditText) findViewById(R.id.edtOtherPart);
        edtXetNghiem = (EditText) findViewById(R.id.edtClinical);
        edtTomTat = (EditText) findViewById(R.id.edtMedicalSum);

        //Chan doan va dieu tri
        edtBenhChinh = (EditText) findViewById(R.id.edtMainSickness);
        edtBenhKemTheo = (EditText) findViewById(R.id.edtSubSickness);
        edtPhanBiet = (EditText) findViewById(R.id.edtDisting);
        edtTienLuong = (EditText) findViewById(R.id.edtPrognosis);
        edtHuongDieuTri = (EditText) findViewById(R.id.edtDirection);

        //Tong ket benh an
        edtQuaTrinhBenh = (EditText) findViewById(R.id.edtProAndClin);
        edtKetQua = (EditText) findViewById(R.id.edtResult);
        edtPhuongAn = (EditText) findViewById(R.id.edtCureDirection);
        edtTinhTrang = (EditText) findViewById(R.id.edtStatus);
        edtHuongTiepTheo = (EditText) findViewById(R.id.edtNextDirection);
    }

    public static void expand(final View v) {
        v.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
    public String formatDate(String date){
        String[] part = date.split("-");
        String pp1 = part[0];
        String pp2 = part[1];
        String pp3 = part[2];
        String p2 = part[2]+"-"+part[1]+"-"+part[0];
        date = p2;
        return date;
    }
    private void LoadData(){
        if(emr_dermatology.getAllergy().equalsIgnoreCase("")){
            cbDiUng.setChecked(false);
        }else{
            cbDiUng.setChecked(true);
            edtDiUng.setText(emr_dermatology.getAllergy());
        }
        if(emr_dermatology.getDrug().equalsIgnoreCase("")){
            cbMaTuy.setChecked(false);
        }else{
            cbMaTuy.setChecked(true);
            edtMaTuy.setText(emr_dermatology.getDrug());
        }
        if(emr_dermatology.getAlcohol().equalsIgnoreCase("")){
            cbRuouBia.setChecked(false);
        }else{
            cbRuouBia.setChecked(true);
            edtRuouBia.setText(emr_dermatology.getAlcohol());
        }
        if(emr_dermatology.getTobacco().equalsIgnoreCase("")){
            cbThuocLa.setChecked(false);
        }else{
            cbThuocLa.setChecked(true);
            edtThuocLa.setText(emr_dermatology.getTobacco());
        }
        if(emr_dermatology.getPipe_tobacco().equalsIgnoreCase("")){
            cbThuocLao.setChecked(false);
        }else{
            cbThuocLao.setChecked(true);
            edtThuocLao.setText(emr_dermatology.getPipe_tobacco());
        }
        if(emr_dermatology.getOthers_rf().equalsIgnoreCase("")){
            cbKhac.setChecked(false);
        }else{
            cbKhac.setChecked(true);
            edtKhac.setText(emr_dermatology.getOthers_rf());
        }
        txtDoctorName.setText(emr_dermatology.getDoctor_name());
        txtEmrDate.setText(formatDate(emr_dermatology.getEmr_date()));
        txtPatientName.setText(emr_dermatology.getPatient_name());
        edtQuaTrinh.setText(emr_dermatology.getPathology());
        edtTienSu.setText(emr_dermatology.getHistory_disease());
        edtDiUng.setText(emr_dermatology.getAllergy());
        edtMaTuy.setText(emr_dermatology.getDrug());
        edtRuouBia.setText(emr_dermatology.getAlcohol());
        edtThuocLa.setText(emr_dermatology.getTobacco());
        edtThuocLao.setText(emr_dermatology.getPipe_tobacco());
        edtKhac.setText(emr_dermatology.getOthers_rf());
        edtGiaDinh.setText(emr_dermatology.getFamily());
        edtMach.setText(String.valueOf(emr_dermatology.getVascular()));
        edtNhietDo.setText(String.valueOf(emr_dermatology.getTemperature()));
        edtHuyetAp.setText(String.valueOf(emr_dermatology.getBlood_pressure()));
        edtNhipTho.setText(String.valueOf(emr_dermatology.getBreathing()));
        edtCanNang.setText(String.valueOf(emr_dermatology.getWeight()));
        edtToanThan.setText(emr_dermatology.getBody());
        edtCoNang.setText(emr_dermatology.getFunctional_symtoms());
        edtTonThuong.setText(emr_dermatology.getBasic_injury());
        edtTuanHoan.setText(emr_dermatology.getCyclic());
        edtHoHap.setText(emr_dermatology.getRespiratory());
        edtTieuHoa.setText(emr_dermatology.getDigest());
        edtThan.setText(emr_dermatology.getKug());
        edtThanKinh.setText(emr_dermatology.getPeripheral_neuropathy());
        edtCQKhac.setText(emr_dermatology.getOthers_o());
        edtXetNghiem.setText(emr_dermatology.getClinical_test());
        edtTomTat.setText(emr_dermatology.getSummary());
        edtBenhChinh.setText(emr_dermatology.getMain_disease());
        edtBenhKemTheo .setText(emr_dermatology.getSecondary_disease());
        edtPhanBiet.setText(emr_dermatology.getDistinguish());
        edtTienLuong.setText(emr_dermatology.getPrognosis());
        edtHuongDieuTri.setText(emr_dermatology.getTreatment_direction_dt());
        edtQuaTrinhBenh .setText(emr_dermatology.getPathology_process());
        edtKetQua.setText(emr_dermatology.getLabs_result());
        edtPhuongAn.setText(emr_dermatology.getTreatments());
        edtTinhTrang.setText(emr_dermatology.getPatient_status());
        edtHuongTiepTheo.setText(emr_dermatology.getTreatment_direction_s());
    }
    private void doBindService() {
        bindService(new Intent(ActivityDermaContent.this, SocketServiceProvider.class), socketConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        if(mIsBound) {
            unbindService(socketConnection);
            mIsBound = false;
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventChangeChatServerStateEvent event) {
        Toast.makeText(getBaseContext(), event.getState(), Toast.LENGTH_SHORT).show();
    }

}
