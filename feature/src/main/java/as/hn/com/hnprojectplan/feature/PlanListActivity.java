package as.hn.com.hnprojectplan.feature;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hn.business.Data.ServiceHelper;

import java.util.List;

public class PlanListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ProjectPlanEntity> planList;
    private ProjectPlanListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //加载数据集合

        String strUserCode = "lijingj";
        ServiceHelper serviceHelper = new ServiceHelper();
        planList = serviceHelper.GetUserProjectPlanList(strUserCode);

        adapter = new ProjectPlanListAdapter(planList, PlanListActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
