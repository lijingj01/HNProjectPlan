package as.hn.com.hnprojectplan.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.hn.business.Data.ServiceHelper;
import com.hn.business.Data.UserInfoEntity;

public class MyActivityBase extends AppCompatActivity {

    /***封装toast对象**/

    private static Toast toast;
    /***获取TAG的activity名称**/

    protected final String TAG = this.getClass().getSimpleName();
    /***是否显示标题栏*/

    private boolean isshowtitle = true;
    /***是否显示标题栏*/

    private boolean isshowstate = true;

    private UserInfoEntity MyUser;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (!isshowtitle) {

            requestWindowFeature(Window.FEATURE_NO_TITLE);

        }


        if (isshowstate) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }

        //设置布局

//        setContentView(intiLayout());

        //初始化控件

//        initView();

        //设置数据

//        initData();
        //region 初始化登陆
        ServiceHelper serviceHelper = new ServiceHelper();
        this.MyUser = serviceHelper.LocalToUser(MyActivityBase.this);
        if (!this.MyUser.UserIsLogin()) {
            //未成功登陆需要进入登陆界面
            Intent intent = new Intent(MyActivityBase.this, LoginActivity.class);
            startActivity(intent);
        }
        //endregion
    }


    /**
     * 设置布局
     *
     * @return
     */

//    public abstract int intiLayout();


    /**
     * 初始化布局
     */

//    public abstract void initView();


    /**
     * 设置数据
     */

//    public abstract void initData();


    /**
     * 是否设置标题栏
     *
     * @return
     */

    public void setTitle(boolean ishow) {

        isshowtitle = ishow;

    }


    /**
     * 设置是否显示状态栏
     *
     * @param ishow
     */

    public void setState(boolean ishow) {

        isshowstate = ishow;

    }


    /**
     * 显示长toast
     *
     * @param msg
     */

    public void toastLong(String msg) {

        if (null == toast) {

            toast = new Toast(this);

            toast.setDuration(Toast.LENGTH_LONG);

            toast.setText(msg);

            toast.show();

        } else {

            toast.setText(msg);

            toast.show();

        }

    }


    /**
     * 显示短toast
     *
     * @param msg
     */

    public void toastShort(String msg) {

        if (null == toast) {

            toast = new Toast(this);

            toast.setDuration(Toast.LENGTH_SHORT);

            toast.setText(msg);

            toast.show();

        } else {

            toast.setText(msg);

            toast.show();

        }

    }


    public UserInfoEntity getMyUser() {
        return MyUser;
    }

    public void setMyUser(UserInfoEntity myUser) {
        MyUser = myUser;
    }
}
