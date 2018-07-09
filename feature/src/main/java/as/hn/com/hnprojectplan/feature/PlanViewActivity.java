package as.hn.com.hnprojectplan.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PlanViewActivity extends MyActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //region 加载数据
        TextView base_swipe_item_Date = (TextView) findViewById(R.id.base_swipe_item_Date);
        TextView base_swipe_item_Title = (TextView) findViewById(R.id.base_swipe_item_title);
        TextView plan_info_desc = (TextView) findViewById(R.id.plan_info_desc);

        Intent intent = getIntent();
        ProjectPlanEntity entity = (ProjectPlanEntity) intent.getSerializableExtra("plan");

        base_swipe_item_Date.setText(entity.GetPublishDate());
        base_swipe_item_Title.setText(entity.getPlanTitle());
        plan_info_desc.setText(entity.getPlanContent());
        //endregion

    }

}
