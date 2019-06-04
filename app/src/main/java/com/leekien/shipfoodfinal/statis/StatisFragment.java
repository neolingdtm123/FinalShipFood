package com.leekien.shipfoodfinal.statis;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.Statis;
import com.leekien.shipfoodfinal.common.CommonActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class StatisFragment extends Fragment implements StatisManager.View {
    StatisPresenter statisPresenter;
//    LineChartView lineChart;
    LineChart lineChart;
    List<String> xAxes = new ArrayList<>();
    List<Entry> list= new ArrayList<>();
    List<Integer> yAxes = new ArrayList<>();
    ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
    String check;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_statis, container, false);
        Bundle bundle = getArguments();
        if(!CommonActivity.isNullOrEmpty(bundle)){
            check = bundle.getString("check");
        }
        statisPresenter = new StatisPresenter(this);
        lineChart = view.findViewById(R.id.linechart);
        statisPresenter.getSuccessOrder();
        return view;
    }

    @Override
    public void showSuccessOrder(List<Order> orderList) {
        List<Statis> statisList = new ArrayList<>();
        for(Order order : orderList){
            if("0".equals(check)){
                Statis statis = new Statis(order.getEndtime().split("/")[1],order.getPrice());
                statisList.add(statis);
            }
            else {
                Statis statis = new Statis(order.getEndtime().split("/")[1],order.getPricefood());
                statisList.add(statis);
            }

        }
        String pattern = "dd/MM/yyyy";
        Date today = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat(pattern);
        String date = df.format(today);
        String month = date.split("/")[1];
        for(int i =1;i<13;i++){
            if(i<=Integer.valueOf(month)){
                xAxes.add("Tháng" + " " + String.valueOf(i));
                int gia =0;
                for(Statis statis :statisList){
                    if(Integer.valueOf(statis.getMonth()) ==i){
                        gia+= Integer.valueOf(statis.getGia());
                    }
                }
                list.add(new Entry(i-1,gia));
//                yAxes.add(gia);
            }
        }
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxes));

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);

        LineDataSet lineDataSet = new LineDataSet(list,"value");
        lineDataSet.setDrawCircles(true);
        lineDataSet.setColor(Color.BLUE);
        lineDataSets.add(lineDataSet);
        lineChart.setData(new LineData(lineDataSets));
        lineChart.invalidate();
        lineChart.setDragEnabled(true);
        lineChart.setTouchEnabled(true);


        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

//        List yAxisValues = new ArrayList();
//        List axisValues = new ArrayList();
//        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));
//        for(int i = 0; i < xAxes.size(); i++){
//            axisValues.add(i, new AxisValue(i).setLabel(xAxes.get(i)));
//        }
//
//        for (int i = 0; i < yAxes.size(); i++){
//            yAxisValues.add(new PointValue(i, yAxes.get(i)));
//        }
//        List lines = new ArrayList();
//        lines.add(line);
//        LineChartData data = new LineChartData();
//        data.setLines(lines);
//        Axis axis = new Axis();
//        axis.setValues(axisValues);
//        axis.setTextSize(16);
//        axis.setTextColor(Color.parseColor("#03A9F4"));
//        data.setAxisXBottom(axis);
//
//        Axis yAxis = new Axis();
//        yAxis.setName("Doanh thu từng tháng");
//        yAxis.setTextColor(Color.parseColor("#03A9F4"));
//        yAxis.setTextSize(16);
//        data.setAxisYLeft(yAxis);
//        lineChart.setLineChartData(data);
//        Viewport viewport = new Viewport(lineChart.getMaximumViewport());
//        viewport.top = 300000;
//        lineChart.setMaximumViewport(viewport);
//        lineChart.setCurrentViewport(viewport);
    }
}
