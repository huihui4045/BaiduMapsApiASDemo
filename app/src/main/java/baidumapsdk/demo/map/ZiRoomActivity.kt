package baidumapsdk.demo.map

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import baidumapsdk.demo.R
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.search.geocode.GeoCodeResult
import com.baidu.mapapi.search.geocode.GeoCoder
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult

import kotlinx.android.synthetic.main.activity_zi_room.*

class ZiRoomActivity : AppCompatActivity() ,OnGetGeoCoderResultListener{



    lateinit var mBaiduMap: BaiduMap

    lateinit var mSearch: GeoCoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zi_room)

        mBaiduMap = mapView.map

        mSearch = GeoCoder.newInstance()

        mSearch.setOnGetGeoCodeResultListener(this)
    }

    override fun onGetGeoCodeResult(p0: GeoCodeResult?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetReverseGeoCodeResult(p0: ReverseGeoCodeResult?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
