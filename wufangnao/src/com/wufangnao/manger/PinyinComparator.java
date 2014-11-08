package com.wufangnao.manger;

import java.util.Comparator;

import com.wufangnao.item.CityListItem;
/**
 * 拼音比较
 * @ClassName: PinyinComparator 
 * @Description: TODO 
 * @author lrk
 * @date 2014-3-7 上午11:03:24 
 *
 */
public class PinyinComparator implements Comparator<CityListItem> {

	public int compare(CityListItem o1, CityListItem o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
