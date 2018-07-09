package as.hn.com.hnprojectplan.feature;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hn.business.Data.ServiceHelper;

import java.util.List;

public class PlanListActivity extends MyActivityBase {

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        if (id == R.id.action_addplan) {
            //新增计划
            Intent intent = new Intent(PlanListActivity.this, AddPlanActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_login) {
            //打开登陆页面
            Intent intent = new Intent(PlanListActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            //打开主页
            Intent intent = new Intent(PlanListActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
