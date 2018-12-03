package baidumapsdk.demo.map;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.animation.Animation;
import com.baidu.mapapi.animation.Transformation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import java.util.ArrayList;
import java.util.List;

import baidumapsdk.demo.R;
import baidumapsdk.demo.adapter.HeadView;
import baidumapsdk.demo.adapter.MyAdapter;

public class ZiRoomDemoActivity extends AppCompatActivity implements OnGetGeoCoderResultListener {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private GeoCoder mSearch;
    private Point mScreenCenterPoint;
    private Marker mMarkerF;
    private ListView mListView;
    private LatLng llA;
    private LatLng llB;
    private LatLng llC;
    private LatLng llD;


    BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_marka);
    BitmapDescriptor bdB = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_markb);
    BitmapDescriptor bdC = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_markc);
    BitmapDescriptor bdD = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_markd);
    private Marker mMarkerA;
    private Marker mMarkerB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zi_room_demo);

        mMapView = ((MapView) findViewById(R.id.mapView));
       // mListView = ((ListView) findViewById(R.id.listView));

        mBaiduMap = mMapView.getMap();

        mSearch = GeoCoder.newInstance();

        mSearch.setOnGetGeoCodeResultListener(this);

        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                initOverlay();
            }
        });

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus status) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus status, int reason) {

            }

            @Override
            public void onMapStatusChange(MapStatus status) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus status) {

                if (null == mMarkerF) {
                    return;
                }
                mMarkerF.setAnimation(getTransformationPoint());
                mMarkerF.startAnimation();

            }
        });


        List<String> datas=new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            datas.add(i+"");
        }


      //  mListView.setAdapter(new MyAdapter(datas,this));
    }

    private void initOverlay() {

        llA = new LatLng(40.023537, 116.289429);
        llB = new LatLng(40.022211, 116.406137);
        llC = new LatLng(40.022211, 116.499274);
        llD = new LatLng(39.847829, 116.289429);

        if (null != mBaiduMap.getMapStatus()) {
         LatLng  llF = mBaiduMap.getMapStatus().target;
            mScreenCenterPoint = mBaiduMap.getProjection().toScreenLocation(llF);
            mScreenCenterPoint.y-=300;

            MarkerOptions ooF = new MarkerOptions().position(llF)
                    .anchor(0.5f,0.5f)
                    .icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.purple_pin)).perspective(true)
                    .scaleX(0.5f)
                    .scaleY(0.5f)
                    .fixedScreenPosition(mScreenCenterPoint);


            mMarkerF = (Marker) (mBaiduMap.addOverlay(ooF));


            MarkerOptions oa = new MarkerOptions().position(llA)
                    .anchor(0.5f,0.5f)
                    .icon(BitmapDescriptorFactory
                            .fromView(new HeadView(ZiRoomDemoActivity.this,R.drawable.yeats)));

            MarkerOptions ob= new MarkerOptions().position(llB)
                    .anchor(0.5f,0.5f)
                    .icon(BitmapDescriptorFactory
                            .fromView(new HeadView(ZiRoomDemoActivity.this,R.drawable.john)));

            mMarkerB = (Marker) (mBaiduMap.addOverlay(ob));

            mMarkerA= (Marker) (mBaiduMap.addOverlay(oa));

            MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
            mBaiduMap.setMapStatus(msu);




        }
    }

    /**
     * 创建平移坐标动画
     */
    private Animation getTransformationPoint() {

        if (null != mScreenCenterPoint) {
            Point pointTo = new Point(mScreenCenterPoint.x, mScreenCenterPoint.y - 100);
            Transformation mTransforma = new Transformation(mScreenCenterPoint, pointTo, mScreenCenterPoint);
            mTransforma.setDuration(500);
            mTransforma.setRepeatMode(Animation.RepeatMode.RESTART);//动画重复模式
            mTransforma.setRepeatCount(1);//动画重复次数
            mTransforma.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart() {
                }

                @Override
                public void onAnimationEnd() {

                    LatLng latLng=mBaiduMap.getProjection().fromScreenLocation(mScreenCenterPoint);

                    mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));

                }

                @Override
                public void onAnimationCancel() {
                }

                @Override
                public void onAnimationRepeat() {

                }
            });
            return mTransforma;
        }

        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
        MapView.setMapCustomEnable(false);
        mMapView.onDestroy();
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {


        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            //没有找到检索结果
        }

        Log.e("GeoCodeDemo", "ReverseGeoCodeResult = " + result.toString());

        if (result!=null){

            Toast.makeText(ZiRoomDemoActivity.this,result.getAddress()+result.getSematicDescription(),Toast.LENGTH_SHORT).show();
        }

        //获取反向地理编码结果

    }
}
