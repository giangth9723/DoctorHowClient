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
import project.fpt.edu.vn.registerscreen.Model.Emr_female;
import project.fpt.edu.vn.registerscreen.Model.Emr_mental;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;
import project.fpt.edu.vn.registerscreen.Session;


public class ActivityMentalContent extends AppCompatActivity {

    LinearLayout lineHidden02, lineHidden03, lineHidden04, lineHidden05, lineHidden06;
    TextView txtMedicalRecordII, txtMedicalRecordIII,
            txtMedicalRecordIV, txtMedicalRecordV, txtMedicalRecordVI, txtdoctorName,txtPatientName,txtEmrDate;
    ImageButton imgBtnBack;
    Session session;
    Emr_mental emr_mental;

    CheckBox cbDiUng, cbMaTuy, cbRuouBia, cbThuocLa, cbThuocLao, cbKhac;
    EditText edtQuaTrinh, edtTienSu, edtDiUng, edtMaTuy, edtRuouBia, edtThuocLa, edtThuocLao, edtKhac,
            edtGiaDinh,
            edtMach, edtNhietDo, edtHuyetAp, edtNhipTho, edtCanNang,
            edtToanThan, edtTuanHoan, edtHoHap, edtTieuHoa, edtThan, edtXuong, edtTai, edtRang, edtMat, edtCQK,
            edtDayThanKinh, edtDayMat, edtVanDong, edtTruongLucCo, edtCamGiac, edtPhanXa,
            edtBieuHien, edtYTKhongGian, edtYTThoiGian, edtYTBanThan, edtCamXuc, edtTriGiac,
            edtTDHinhThuc, edtTDNoiDung, edtYChi, edtBanNang, edtNhoMayMoc, edtNhoThongHieu, edtPhanTic, edtTongHop,
            edtChuY, edtXetNghiem, edtTomTat,
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
        edtTuanHoan = (EditText) findViewById(R.id.edtCyclic);
        edtHoHap = (EditText) findViewById(R.id.edtRespiratory);
        edtTieuHoa = (EditText) findViewById(R.id.edtDigest);
        edtThan = (EditText) findViewById(R.id.edtKidney);
        edtXuong = (EditText) findViewById(R.id.edtBone);
        edtTai = (EditText) findViewById(R.id.edtENT);
        edtRang = (EditText) findViewById(R.id.edtTooth);
        edtMat = (EditText) findViewById(R.id.edtEye);
        edtCQK = (EditText) findViewById(R.id.edtOtherPart);

        edtDayThanKinh = (EditText) findViewById(R.id.edtDayThanKinh);
        edtDayMat = (EditText) findViewById(R.id.edtDayMat);
        edtVanDong = (EditText) findViewById(R.id.edtVanDong);
        edtTruongLucCo = (EditText) findViewById(R.id.edtTruongLuc);
        edtCamGiac = (EditText) findViewById(R.id.edtCamGiac);
        edtPhanXa = (EditText) findViewById(R.id.edtPhanXa);

        edtBieuHien = (EditText) findViewById(R.id.edtBieuHien);
        edtYTKhongGian = (EditText) findViewById(R.id.edtYThucKhongGian);
        edtYTThoiGian = (EditText) findViewById(R.id.edtYThucThoiGian);
        edtYTBanThan = (EditText) findViewById(R.id.edtYThucBanThan);
        edtCamXuc = (EditText) findViewById(R.id.edtCamXuc);
        edtTriGiac = (EditText) findViewById(R.id.edtTriGiac);
        edtTDHinhThuc = (EditText) findViewById(R.id.edtHinhThuc);
        edtTDNoiDung = (EditText) findViewById(R.id.edtNoiDung);
        edtYChi = (EditText) findViewById(R.id.edtHDYC);
        edtBanNang = (EditText) findViewById(R.id.edtHDBN);
        edtNhoMayMoc = (EditText) findViewById(R.id.edtNhoMayMoc);
        edtNhoThongHieu = (EditText) findViewById(R.id.edtNhoThongHieu);
        edtPhanTic = (EditText) findViewById(R.id.edtKNPT);
        edtTongHop = (EditText) findViewById(R.id.edtKNTH);
        edtChuY = (EditText) findViewById(R.id.edtChuY);
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

        txtdoctorName = (TextView) findViewById(R.id.txtDoctorName);
        txtPatientName = (TextView) findViewById(R.id.txtPatientName);
        txtEmrDate = (TextView)findViewById(R.id.txtEmrDate);

        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        session = new Session(this);
    }
    private void LoadData(){
        if(emr_mental.getAllergy().equalsIgnoreCase("")){
            cbDiUng.setChecked(false);
        }else{
            cbDiUng.setChecked(true);
            edtDiUng.setText(emr_mental.getAllergy());
        }
        if(emr_mental.getDrug().equalsIgnoreCase("")){
            cbMaTuy.setChecked(false);
        }else{
            cbMaTuy.setChecked(true);
            edtMaTuy.setText(emr_mental.getDrug());
        }
        if(emr_mental.getAlcohol().equalsIgnoreCase("")){
            cbRuouBia.setChecked(false);
        }else{
            cbRuouBia.setChecked(true);
            edtRuouBia.setText(emr_mental.getAlcohol());
        }
        if(emr_mental.getTobacco().equalsIgnoreCase("")){
            cbThuocLa.setChecked(false);
        }else{
            cbThuocLa.setChecked(true);
            edtThuocLa.setText(emr_mental.getTobacco());
        }
        if(emr_mental.getPipe_tobacco().equalsIgnoreCase("")){
            cbThuocLao.setChecked(false);
        }else{
            cbThuocLao.setChecked(true);
            edtThuocLao.setText(emr_mental.getPipe_tobacco());
        }
        if(emr_mental.getOthers_rf().equalsIgnoreCase("")){
            cbKhac.setChecked(false);
        }else{
            cbKhac.setChecked(true);
            edtKhac.setText(emr_mental.getOthers_rf());
        }
        txtdoctorName.setText(emr_mental.getDoctor_name());
        txtPatientName.setText(emr_mental.getPatient_name());
        txtEmrDate.setText(formatDate(emr_mental.getEmr_date()));
        edtTienSu.setText(emr_mental.getHistory_disease());
        edtDiUng.setText(emr_mental.getAllergy());
        edtMaTuy.setText(emr_mental.getDrug());
        edtRuouBia.setText(emr_mental.getAlcohol());
        edtThuocLa.setText(emr_mental.getTobacco());
        edtThuocLao.setText(emr_mental.getPipe_tobacco());
        edtKhac.setText(emr_mental.getOthers_rf());
        edtGiaDinh.setText(emr_mental.getFamily());
        edtMach.setText(String.valueOf(emr_mental.getVascular()));
        edtNhietDo.setText(String.valueOf(emr_mental.getTemperature()));
        edtHuyetAp.setText(String.valueOf(emr_mental.getBlood_pressure()));
        edtNhipTho.setText(String.valueOf(emr_mental.getBreathing()));
        edtCanNang.setText(String.valueOf(emr_mental.getWeight()));
        edtToanThan.setText(emr_mental.getBody());
        edtHoHap.setText(emr_mental.getRespiratory());
        edtTieuHoa .setText(emr_mental.getDigest());
        edtThan.setText(emr_mental.getKidney());
        edtXuong.setText(emr_mental.getBone());
        edtTai.setText(emr_mental.getEar_nose_throat());
        edtRang.setText(emr_mental.getTeeth());
        edtMat.setText(emr_mental.getEye());
        edtCQK.setText(emr_mental.getEndocrine());
        edtDayThanKinh.setText(emr_mental.getCranial_nerves());
        edtDayMat.setText(emr_mental.getBottom_of_eye());

        edtVanDong.setText(emr_mental.getMotor());
        edtTruongLucCo.setText(emr_mental.getField_force());
        edtCamGiac.setText(emr_mental.getFeel());
        edtPhanXa.setText(emr_mental.getReflex());
        edtBieuHien.setText(emr_mental.getGeneral_expression());
        edtYTKhongGian.setText(emr_mental.getSpace());
        edtYTThoiGian.setText(emr_mental.getTime());
        edtYTBanThan.setText(emr_mental.getMyself());
        edtCamXuc.setText(emr_mental.getAffection());
        edtTriGiac.setText(emr_mental.getSense());
        edtTDHinhThuc.setText(emr_mental.getForm());
        edtTDNoiDung.setText(emr_mental.getContent());
        edtYChi.setText(emr_mental.getSpirit());
        edtBanNang.setText(emr_mental.getInstinct());
        edtNhoMayMoc.setText(emr_mental.getMechanically());
        edtNhoThongHieu .setText(emr_mental.getUnderstandably());
        edtPhanTic.setText(emr_mental.getAnalytical());
        edtTongHop.setText(emr_mental.getComprehensive());
        edtChuY.setText(emr_mental.getAttention());

        edtXetNghiem.setText(emr_mental.getClinical_test());
        edtTomTat.setText(emr_mental.getSummary());
        edtBenhChinh.setText(emr_mental.getMain_disease());
        edtBenhKemTheo .setText(emr_mental.getSecondary_disease());
        edtPhanBiet.setText(emr_mental.getDistinguish());
        edtTienLuong.setText(emr_mental.getPrognosis());
        edtHuongDieuTri.setText(emr_mental.getTreatment_direction_dt());
        edtQuaTrinhBenh .setText(emr_mental.getPathology_process());
        edtKetQua.setText(emr_mental.getLabs_result());
        edtPhuongAn.setText(emr_mental.getTreatments());
        edtTinhTrang.setText(emr_mental.getPatient_status());
        edtHuongTiepTheo.setText(emr_mental.getTreatment_direction_s());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_content);
        onCreateSocket();
        anhXa();
        anhXaText();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("emr_mental");
        emr_mental = (Emr_mental) bundle.getSerializable("emr_mental");
        LoadData();
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMentalContent.this,ActivityMentalExaminationHistory.class));
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
        bindService(new Intent(ActivityMentalContent.this, SocketServiceProvider.class), socketConnection, Context.BIND_AUTO_CREATE);
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
