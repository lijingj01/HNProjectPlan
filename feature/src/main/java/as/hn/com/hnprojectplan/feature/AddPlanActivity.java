package as.hn.com.hnprojectplan.feature;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.hn.business.Data.ServiceHelper;
import com.hn.gc.materialdesign.views.ButtonRectangle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddPlanActivity extends MyActivityBase {

    //region 日期选择的相关方法
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            //一旦开始日期发生了改变，就需要将结束日期设置成空日期
            EditText editEndText = (EditText) findViewById(R.id.editEndText);
            editEndText.setText("");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        String strUserCode ="lijingj";
//        ServiceHelper serviceHelper = new ServiceHelper();
//        List<ProjectPlanEntity> items = serviceHelper.GetUserProjectPlanList(strUserCode);

        //region 日期操作方法
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(watcher);
        editText.setInputType(InputType.TYPE_NULL);
        editText.setFocusable(false);

        EditText editEndText = (EditText) findViewById(R.id.editEndText);
        editEndText.setInputType(InputType.TYPE_NULL);
        editEndText.setFocusable(false);

        //开始日期
        Button btnDate = (Button) findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editBeginText = (EditText) findViewById(R.id.editText);
                Date dt = new Date();
                onCreateDialog(editBeginText, dt).show();
            }
        });

        //结束日期
        Button btnEndDate = (Button) findViewById(R.id.btnEndDate);
        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //结束日期要获取开始日期的时间来定义下限
                EditText editText = (EditText) findViewById(R.id.editText);
                EditText editEndText = (EditText) findViewById(R.id.editEndText);
                Date dt = new Date();
                String beginDate = editText.getText().toString();
                if (!(beginDate == null || beginDate.isEmpty())) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        dt = dateFormat.parse(beginDate);

                    } catch (Exception ex) {
                    }
                }

                onCreateDialog(editEndText, dt).show();
            }
        });
        //endregion


        ButtonRectangle btSave = findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = ((EditText) findViewById(R.id.txtTitle)).getText().toString();
                String remark = ((EditText) findViewById(R.id.txtContent)).getText().toString();
                String beginDate = ((EditText) findViewById(R.id.editText)).getText().toString();
                String endDate = ((EditText) findViewById(R.id.editEndText)).getText().toString();

                ProjectPlanEntity item = new ProjectPlanEntity();
                item.setPlanTitle(title);
                item.setPlanContent(remark);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dtBegin = new Date();
                Date dtEnd = new Date();

                try {
                    dtBegin = dateFormat.parse(beginDate);
                    dtEnd = dateFormat.parse(endDate);
                } catch (Exception ex) {
                }
                item.setBeginDate(dtBegin);
                item.setEndDate(dtEnd);
                //默认进度0
                item.setProgressIndex(0);

                //region 将数据保存到数据库里面
//                ProjectPlanDbAdapter dbHelper = new ProjectPlanDbAdapter(AddPlanActivity.this);
//                dbHelper.open();
//                dbHelper.createProjectPlan(item);
//                dbHelper.close();
                //endregion

                //region 将数据写入webservice
                ServiceHelper serviceHelper = new ServiceHelper();
                serviceHelper.AddPlanEntity(item);
                //endregion

                //跳转进入列表页
                Intent intent = new Intent(AddPlanActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    protected Dialog onCreateDialog(EditText editText, Date minDate) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = null;
        DatePickerDialog.OnDateSetListener dateListener =
                new MyOnDateSetListener(editText);

        String txt = editText.getText().toString();

        if (!(txt == null || txt.isEmpty())) {
            //设置日期控件当前定位的时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = new Date();
            try {
                dt = dateFormat.parse(txt);

            } catch (Exception ex) {
            }
            calendar.setTime(dt);
        }

        dialog = new DatePickerDialog(this,
                dateListener,
                calendar.get(calendar.YEAR),
                calendar.get(calendar.MONTH),
                calendar.get(calendar.DATE));

        DatePicker dp = dialog.getDatePicker();
        dp.setMinDate(minDate.getTime());

        return dialog;
    }

    private static class MyOnDateSetListener implements DatePickerDialog.OnDateSetListener {
        private final EditText editText;

        public MyOnDateSetListener(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void onDateSet(DatePicker datePicker,
                              int year, int month, int dayOfMonth) {
            //Calendar月份是从0开始,所以month要加1
            editText.setText(year + "-" +
                    (month + 1) + "-" + dayOfMonth);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            //打开列表页面
            Intent intent = new Intent(AddPlanActivity.this, PlanListActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_login) {
            //打开登陆页面
            Intent intent = new Intent(AddPlanActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            //打开主页
            Intent intent = new Intent(AddPlanActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
