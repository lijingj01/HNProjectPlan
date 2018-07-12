package as.hn.com.hnprojectplan.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hn.business.Data.ProjectPlanEntity;
import com.hn.business.Data.ServiceHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlanViewActivity extends MyActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //region 加载数据
        TextView base_swipe_item_Date = (TextView) findViewById(R.id.plan_info_date);
        TextView base_swipe_item_Title = (TextView) findViewById(R.id.plan_info_title);
        TextView plan_info_desc = (TextView) findViewById(R.id.plan_info_desc);

        Intent intent = getIntent();
        ProjectPlanEntity entity = (ProjectPlanEntity) intent.getSerializableExtra("plan");

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
    }

}
