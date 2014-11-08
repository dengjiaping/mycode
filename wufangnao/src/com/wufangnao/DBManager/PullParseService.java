package com.wufangnao.DBManager;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.wufangnao.item.Area;
import com.wufangnao.item.City;
import com.wufangnao.item.Sort;
import com.wufangnao.manger.CharacterParser;
import com.wufangnao.manger.PinyinComparator;

/**
 * pull解析类
 * @ClassName: PullParseService 
 * @Description: TODO  解析地区的文件列表
 * @author lrk
 * @date 2014-3-7 上午11:01:01 
 *
 */
public class PullParseService {
	public static List<Sort> getBooks(InputStream inputStream) throws Exception{
		
		List<Sort> sorts = null;
		List<City> cities = null;
		List<Area> areas = null;
		Sort sort = null;
		City city = null;
		Area area = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inputStream, "UTF-8");
		int event = parser.getEventType();//产生第一个事件
		while(event!=XmlPullParser.END_DOCUMENT){
			switch(event){
			case XmlPullParser.START_DOCUMENT://判断当前事件是否是文档开始事件
				sorts = new ArrayList<Sort>();//初始化Sort集合
				break;
			case XmlPullParser.START_TAG://判断当前事件是否是标签元素开始事件
				if("Sort".equals(parser.getName())){//判断开始标签元素是否是Sort
					sort = new Sort();
					cities = new ArrayList<City>();
					sort.setName(parser.getAttributeValue(0));//得到Sort标签的属性值，并设置Sort的name
				}
				if("City".equals(parser.getName()))
				{
					city = new City();
					areas = new ArrayList<Area>();
					
					city.setId(parser.getAttributeValue(0));
					city.setName(parser.getAttributeValue(1));
					
				}
				if("Area".equals(parser.getName()))
				{
					area = new Area();
					area.setId(parser.getAttributeValue(0));
					area.setName(parser.getAttributeValue(1));
				}
				break;
			case XmlPullParser.END_TAG://判断当前事件是否是标签元素结束事件
				if("Sort".equals(parser.getName())){//判断结束标签元素是否是book
					sort.setListCities(cities);
					sorts.add(sort);//将book添加到books集合
					cities = null;
					sort = null;
				}
				if("City".equals(parser.getName()))
				{
					city.setListAreas(areas);
					cities.add(city);
					city=null;
					areas = null;
				}
				if("Area".equals(parser.getName()))
				{
					areas.add(area);
					area = null;
				}
				break;
			}
			event = parser.next();//进入下一个元素并触发相应事件
		}//end while
		return sorts;
	}
}