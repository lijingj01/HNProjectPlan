package as.hn.com.hnprojectplan.feature;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hn.business.Data.PlanPercentageEntity;
import com.hn.business.Data.ProjectPlanEntity;
import com.hn.gc.materialdesign.views.CircleProgressView;


import java.util.List;

public class PlanPercentageListAdapter extends RecyclerView.Adapter<PlanPercentageListAdapter.NormalItemHolder> {

    private static final int NORMAL_ITEM = 0;
    private static final int GROUP_ITEM = 1;

    private Context context;
    private List<PlanPercentageEntity> mDataList;
    private LayoutInflater mLayoutInflater;

    /**
     * 渲染具体的ViewHolder
     *
     * @param viewGroup ViewHolder的容器
     * @param i         一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    public PlanPercentageListAdapter.NormalItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NormalItemHolder(mLayoutInflater.inflate(R.layout.fragment_base_swipe_list, viewGroup, false));
    }

    /**
     * 绑定ViewHolder的数据。
     *
     * @param viewHolder
     * @param i          数据源list的下标
     */
    @Override
    public void onBindViewHolder(PlanPercentageListAdapter.NormalItemHolder viewHolder, int i) {
        final int j = i;
        PlanPercentageEntity entity = mDataList.get(i);

        if (null == entity)
            return;

        NormalItemHolder holder = (NormalItemHolder) viewHolder;
        bindNormalItem(entity, holder.dateText, holder.remarkText, holder.endText);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ProjectPlanEntity entity = mDataList.get(j);
//                Intent intent = new Intent(context, PlanViewActivity.class);
//                intent.putExtra("plan", entity);
//                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 决定元素的布局使用哪种类型
     *
     * @param position 数据源List的下标
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数
     */
    @Override
    public int getItemViewType(int position) {
        return NORMAL_ITEM;
    }

    @Override
    public long getItemId(int position) {
        return mDataList.get(position).getPlanId();
    }

    void bindNormalItem(PlanPercentageEntity entity, TextView dateText, TextView remarkText, TextView endText) {

        dateText.setText(entity.GetAddTimeString());
        remarkText.setText(entity.getRemark());
        endText.setText(String.valueOf(entity.getEndPercentage()));
    }

    public class NormalItemHolder extends RecyclerView.ViewHolder {
        TextView dateText;
        TextView remarkText;
        TextView endText;
        com.hn.gc.materialdesign.views.Card cardView;

        public NormalItemHolder(final View itemView) {
            super(itemView);
            cardView = (com.hn.gc.materialdesign.views.Card) itemView.findViewById(R.id.card_view);
            dateText = (TextView) itemView.findViewById(R.id.base_swipe_item_Date);
            remarkText = (TextView) itemView.findViewById(R.id.base_swipe_item_Remark);
            endText = (TextView) itemView.findViewById(R.id.base_swipe_item_EndPercentage);
        }
    }
}
