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
import project.fpt.edu.vn.registerscreen.Model.Emr_female;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;
import project.fpt.edu.vn.registerscreen.Session;


public class ActivityFemaleContent extends AppCompatActivity {

    LinearLayout lineHidden02, lineHidden03, lineHidden04, lineHidden05, lineHidden06;
    TextView txtMedicalRecordII, txtMedicalRecordIII,
            txtMedicalRecordIV, txtMedicalRecordV, txtMedicalRecordVI, txtDoctorName,txtPatientName,txtEmrDate;
    ImageButton imgBtnBack;
    Session session;
    Emr_female emr_female;
    CheckBox cbDauBung;
    EditText edtQuaTrinh, edtTienSu, edtGiaDinh, edtNamCoKinh, edtTuoiCoKinh,
            edtTinhChatKN, edtNgayThayKinh,edtChuKiKN, edtLuongKinh, edtKinhLanCuoi, edtLayChong,
            edtTuoiLayChong, edtHetKinh, edtTuoiHetKinh, edtBenhDaDieuTri,
            edtMach, edtNhietDo, edtHuyetAp, edtNhipTho, edtCanNang,
            edtToanThan, edtTuanHoan, edtHoHap, edtTieuHoa, edtThanKinh, edtXuong, edtThan, edtCQK,
            edtDauHieu, edtMoiLon, edtMoiBe, edtAmVat, edtAmHo, edtMangTrinh, edtTangSinhMon,
            edtCoTuCung, edtAmDao, edtThanTuCung, edtPhanPhu, edtCacTuiCung, edtXetNghiem, edtTomTat,
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
    public void anhXaText(){
        //Hoi benh
        edtQuaTrinh = (EditText) findViewById(R.id.edtProcess);
        edtTienSu = (EditText) findViewById(R.id.edtAnamnesis);
        edtGiaDinh = (EditText) findViewById(R.id.edtFamily);

        edtNamCoKinh = (EditText) findViewById(R.id.edtKNyear);
        edtTuoiCoKinh = (EditText) findViewById(R.id.edtKNage);
        edtTinhChatKN = (EditText) findViewById(R.id.edtKNtype);
        edtNgayThayKinh = (EditText) findViewById(R.id.edtKNday);
        edtChuKiKN = (EditText) findViewById(R.id.edtKNcycle);
        edtLuongKinh = (EditText) findViewById(R.id.edtKNquantity);
        edtKinhLanCuoi = (EditText) findViewById(R.id.edtKNlast);
        edtLayChong = (EditText) findViewById(R.id.edtMarryYear);
        edtTuoiLayChong = (EditText) findViewById(R.id.edtMarryAge);
        edtHetKinh = (EditText) findViewById(R.id.edtOverYear);
        edtTuoiHetKinh = (EditText) findViewById(R.id.edtOverAge);
        edtBenhDaDieuTri = (EditText) findViewById(R.id.edtTreated);
        cbDauBung = (CheckBox)findViewById(R.id.cbStomachache);


        //Kham benh
        edtMach = (EditText) findViewById(R.id.edtCircuit);
        edtNhietDo = (EditText) findViewById(R.id.edtTemperature);
        edtHuyetAp = (EditText) findViewById(R.id.edtPressure);
        edtNhipTho = (EditText) findViewById(R.id.edtBreath);
        edtCanNang = (EditText) findViewById(R.id.edtWeight);

        edtToanThan = (EditText) findViewById(R.id.edtBody);
        edtTuanHoan = (EditText) findViewById(R.id.edtCyclic);
        edtHoHap = (EditText) findViewById(R.id.edtRespiratory);
        edtTieuHoa = (EditText) findViewById(R.id.edtDigest);
        edtThan = (EditText) findViewById(R.id.edtKidney);
        edtThanKinh = (EditText) findViewById(R.id.edtMental);
        edtXuong = (EditText) findViewById(R.id.edtBone);
        edtCQK = (EditText) findViewById(R.id.edtOtherPart);

        edtDauHieu = (EditText) findViewById(R.id.edtDauHieu);
        edtMoiLon = (EditText) findViewById(R.id.edtMoiLon);
        edtMoiBe = (EditText) findViewById(R.id.edtMoiBe);
        edtAmVat = (EditText) findViewById(R.id.edtAmVat);
        edtAmHo = (EditText) findViewById(R.id.edtAmho);
        edtMangTrinh = (EditText) findViewById(R.id.edtTrinh);
        edtTangSinhMon = (EditText) findViewById(R.id.edtTangSM);

        edtCoTuCung = (EditText) findViewById(R.id.edtCoTuCung);
        edtAmDao = (EditText) findViewById(R.id.edtAmDao);
        edtThanTuCung = (EditText) findViewById(R.id.edtThanTuCung);
        edtPhanPhu = (EditText) findViewById(R.id.edtPhanPhu);
        edtCacTuiCung = (EditText) findViewById(R.id.edtTuiCung);
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
    private void LoadData(){
        if(emr_female.getStomachache() == 1){
            cbDauBung.setChecked(true);
        }else{
            cbDauBung.setChecked(false);
        }
        txtPatientName.setText(emr_female.getPatient_name());
        txtDoctorName.setText(emr_female.getDoctor_name());
        txtEmrDate.setText(formatDate(emr_female.getEmr_date()));
        edtQuaTrinh.setText(emr_female.getPathology());
        edtTienSu.setText(emr_female.getHistory_disease());
        edtGiaDinh .setText(emr_female.getFamily());
        edtNamCoKinh.setText(String.valueOf(emr_female.getPeriods_year()));
        edtTuoiCoKinh.setText(String.valueOf(emr_female.getPeriods_age()));
        edtTinhChatKN.setText(emr_female.getPeriods_nature());
        edtNgayThayKinh.setText(String.valueOf(emr_female.getPeriods_noofdate()));
        edtChuKiKN.setText(String.valueOf(emr_female.getPeriods_cycle()));
        edtLuongKinh.setText(emr_female.getPeriods_amount());
        edtKinhLanCuoi .setText(emr_female.getPeriods_lastdate());
        edtLayChong.setText(String.valueOf(emr_female.getMarriage_year()));
        edtTuoiLayChong.setText(String.valueOf(emr_female.getMarriage_age()));
        edtHetKinh.setText(String.valueOf(emr_female.getPeriods_endyear()));
        edtTuoiHetKinh .setText(String.valueOf(emr_female.getPeriods_endage()));
        edtBenhDaDieuTri.setText(emr_female.getPeriods_treatment());
        edtMach.setText(String.valueOf(emr_female.getVascular()));
        edtNhietDo.setText(String.valueOf(emr_female.getTemperature()));
        edtHuyetAp.setText(String.valueOf(emr_female.getBlood_pressure()));
        edtNhipTho.setText(String.valueOf(emr_female.getBreathing()));
        edtCanNang.setText(String.valueOf(emr_female.getWeight()));
        edtToanThan.setText(emr_female.getBody());
        edtTuanHoan.setText(emr_female.getCyclic());
        edtHoHap.setText(emr_female.getRespiratory());
        edtTieuHoa .setText(emr_female.getDigest());
        edtThan.setText(emr_female.getKidney());
        edtThanKinh.setText(emr_female.getNerve());
        edtXuong.setText(emr_female.getBone());
        edtCQK.setText(emr_female.getOthers_o());
        edtDauHieu .setText(emr_female.getSecondary_signs());
        edtMoiLon.setText(emr_female.getBig_lips());
        edtMoiBe.setText(emr_female.getBaby_lips());
        edtAmVat.setText(emr_female.getClitoris());
        edtAmHo.setText(emr_female.getVulva());
        edtMangTrinh.setText(emr_female.getHymen());
        edtTangSinhMon.setText(emr_female.getPerineal());
        edtCoTuCung.setText(emr_female.getCervical());
        edtAmDao.setText(emr_female.getVagina());
        edtThanTuCung.setText(emr_female.getThe_uterus());
        edtPhanPhu.setText(emr_female.getExtra());
        edtCacTuiCung .setText(emr_female.getDouglas());
        edtXetNghiem.setText(emr_female.getClinical_test());
        edtTomTat.setText(emr_female.getSummary());
        edtBenhChinh.setText(emr_female.getMain_disease());
        edtBenhKemTheo.setText(emr_female.getSecondary_disease());
        edtPhanBiet.setText(emr_female.getDistinguish());
        edtTienLuong.setText(emr_female.getPrognosis());
        edtHuongDieuTri.setText(emr_female.getTreatment_direction_dt());
        edtQuaTrinhBenh.setText(emr_female.getPathology_process());
        edtKetQua.setText(emr_female.getLabs_result());
        edtPhuongAn.setText(emr_female.getTreatments());
        edtTinhTrang.setText(emr_female.getPatient_status());
        edtHuongTiepTheo.setText(emr_female.getTreatment_direction_s());


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_female_content);
        onCreateSocket();
        anhXa();
        anhXaText();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("emr_female");
        emr_female = (Emr_female) bundle.getSerializable("emr_female");
        LoadData();
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityFemaleContent.this,ActivityFemaleExaminationHistory.class));
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
    public String formatDate(String date){
        String[] part = date.split("-");
        String pp1 = part[0];
        String pp2 = part[1];
        String pp3 = part[2];
        String p2 = part[2]+"-"+part[1]+"-"+part[0];
        date = p2;
        return date;
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
        txtEmrDate = (TextView) findViewById(R.id.txtEmrDate);
        txtPatientName = (TextView) findViewById(R.id.txtPatientName);

        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);

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
    private void doBindService() {
        bindService(new Intent(ActivityFemaleContent.this, SocketServiceProvider.class), socketConnection, Context.BIND_AUTO_CREATE);
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
