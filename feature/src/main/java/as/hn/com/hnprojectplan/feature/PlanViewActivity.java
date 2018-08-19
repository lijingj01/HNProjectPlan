package as.hn.com.hnprojectplan.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hn.business.Data.PlanPercentageEntity;
import com.hn.business.Data.ProjectPlanEntity;
import com.hn.business.Data.ServiceHelper;
import com.hn.gc.materialdesign.views.CircleProgressView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PlanViewActivity extends MyActivityBase {

    private ProjectPlanEntity entity;
    private PlanPercentageListAdapter cadapter;
    private List<PlanPercentageEntity> perList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //region 加载数据
        TextView base_swipe_item_Date = (TextView) findViewById(R.id.plan_info_date);
        TextView base_swipe_item_Title = (TextView) findViewById(R.id.plan_info_title);
        TextView plan_info_desc = (TextView) findViewById(R.id.plan_info_desc);

        Intent intent = getIntent();
        entity = (ProjectPlanEntity) intent.getSerializableExtra("plan");

        base_swipe_item_Date.setText(entity.GetPublishDate());
        base_swipe_item_Title.setText(entity.getPlanTitle());
        plan_info_desc.setText(entity.getPlanContent());
        //endregion

        Button btnAdd = (Button) findViewById(R.id.btn_add_percentage);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开添加进度
                Intent intent = new Intent(PlanViewActivity.this, AddPercentageActivity.class);
                intent.putExtra("plan", entity);
                startActivity(intent);
            }
        });

        Button btnView = (Button) findViewById(R.id.btn_view_percentage);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开所有进度情况

            }
        });

        //region 加载进度条数据
        CircleProgressView mCircleBar = (CircleProgressView) findViewById(R.id.circleProgressbar);
        CircleProgressView mPercentageBar = (CircleProgressView) findViewById(R.id.endPercentage);

        mCircleBar.setProgress(entity.getTimeUsePercentage());
        mCircleBar.setmTxtHint1("使用了");
        mCircleBar.setmTxtHint2("的时间");

        mPercentageBar.setProgress(entity.getEndPercentage());
        mPercentageBar.setmTxtHint1("已完成");
        mPercentageBar.setmTxtHint2("工作量");
        //endregion

        //region 进度数据
        ServiceHelper serviceHelper = new ServiceHelper();
//        perList = serviceHelper.getpl
        //endregion
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            //打开主页
            //打开列表页面
            Intent intent = new Intent(PlanViewActivity.this, PlanListActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
