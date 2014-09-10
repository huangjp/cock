package cock.util;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

public class MySort1
  implements Comparator<Object>
{
  private String type;
  private String attr;
  private boolean whatOrder = true;

  public MySort1()
  {
  }

  public MySort1(String type, String name) {
    this.type = type;
    this.attr = name;
  }

  public MySort1(String type, String name, boolean whatOrder)
  {
    this.type = type;
    this.attr = name;
    this.whatOrder = whatOrder;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return this.attr;
  }

  public void setName(String name) {
    this.attr = name;
  }

  public boolean isWhatOrder() {
    return this.whatOrder;
  }

  public void setWhatOrder(boolean whatOrder) {
    this.whatOrder = whatOrder;
  }

  public int compare(Object o1, Object o2)
  {
    String name = o1.getClass().getSimpleName();
    if (name != "Map") {
      int index = ListCompare(o1, o2);
      return index;
    }
    try {
      int index = MapCompare(MyUtil.castMap(o1), MyUtil.castMap(o2));
      return index;
    } catch (Exception e) {
      e.printStackTrace();
    }return 0;
  }

  private int ListCompare(Object o1, Object o2)
  {
    Method m = null;
    try {
      m = o1.getClass().getDeclaredMethod("get" + MyUtil.initcap(this.attr.toString()), new Class[0]);
      if ("Date".equals(this.type))
        return myDateCompare(o1, o2, m);
      if (("Integer".equals(this.type)) || ("int".equals(this.type)))
        return myIntegerCompare(o1, o2, m);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  private int myDateCompare(Object o1, Object o2, Method m)
    throws Exception
  {
    Date date1 = (Date)m.invoke(o1, new Object[0]);
    Date date2 = (Date)m.invoke(o2, new Object[0]);
    if (this.whatOrder) {
      if (((date1 == null) && (date2 == null)) || ((date1 != null) && (date2 == null)))
        return 0;
      if ((date1 == null) && (date2 != null)) {
        return 1;
      }
      return 
        date1.getTime() == date2.getTime() ? 0 : date1.getTime() > date2.getTime() ? -1 : 
        1;
    }
    if (((date1 == null) && (date2 == null)) || ((date1 != null) && (date2 == null)))
      return 0;
    if ((date1 == null) && (date2 != null)) {
      return -1;
    }
    return 
      date1.getTime() == date2.getTime() ? 0 : date1.getTime() > date2.getTime() ? 1 : 
      -1;
  }

  private int myIntegerCompare(Object o1, Object o2, Method m)
    throws Exception
  {
    int int1 = Integer.parseInt(m.invoke(o1, new Object[0]).toString());
    int int2 = Integer.parseInt(m.invoke(o2, new Object[0]).toString());
    if (this.whatOrder) {
      return int1 == int2 ? 0 : int1 > int2 ? -1 : 1;
    }
    return int1 == int2 ? 0 : int1 > int2 ? 1 : -1;
  }

  private int MapCompare(Map<String, Object> o1, Map<String, Object> o2)
  {
    try
    {
      if ("Date".equals(this.type))
        return myDateCompare(o1, o2);
      if (("Integer".equals(this.type)) || ("int".equals(this.type)))
        return myIntegerCompare(o1, o2);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  private int myDateCompare(Map<String, Object> o1, Map<String, Object> o2)
  {
    if (this.whatOrder) {
      return 
        (Integer)o1.get(this.attr) == (Integer)o2.get(this.attr) ? 0 : ((Integer)o1.get(this.attr)).intValue() > ((Integer)o2.get(this.attr)).intValue() ? -1 : 
        1;
    }
    return 
      (Integer)o1.get(this.attr) == (Integer)o2.get(this.attr) ? 0 : ((Integer)o1.get(this.attr)).intValue() > ((Integer)o2.get(this.attr)).intValue() ? 1 : 
      -1;
  }

  private int myIntegerCompare(Map<String, Object> o1, Map<String, Object> o2)
  {
    if (this.whatOrder) {
      return 
        (Integer)o1.get(this.attr) == (Integer)o2.get(this.attr) ? 0 : ((Integer)o1.get(this.attr)).intValue() > ((Integer)o2.get(this.attr)).intValue() ? -1 : 
        1;
    }
    return 
      (Integer)o1.get(this.attr) == (Integer)o2.get(this.attr) ? 0 : ((Integer)o1.get(this.attr)).intValue() > ((Integer)o2.get(this.attr)).intValue() ? 1 : 
      -1;
  }

  public static class ListSortById
    implements Comparator<Object>
  {
    private boolean whatOrder = true;

    public ListSortById() {
    }
    public ListSortById(boolean whatOrder) { this.whatOrder = whatOrder; }

    public int compare(Object o1, Object o2)
    {
      Method m = null;
      try {
        m = o1.getClass().getDeclaredMethod("getId", new Class[0]);
        if (this.whatOrder) {
          return 
            (Integer)m.invoke(o1, new Object[0]) == (Integer)m.invoke(o2, new Object[0]) ? 0 : ((Integer)m.invoke(o1, new Object[0])).intValue() > ((Integer)m.invoke(o2, new Object[0])).intValue() ? -1 : 
            1;
        }
        return 
          (Integer)m.invoke(o1, new Object[0]) == (Integer)m.invoke(o2, new Object[0]) ? 0 : ((Integer)m.invoke(o1, new Object[0])).intValue() > ((Integer)m.invoke(o2, new Object[0])).intValue() ? 1 : 
          -1;
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      return 0;
    }
  }

  public static class MapSortById
    implements Comparator<Map<String, Object>>
  {
    private boolean whatOrder = true;

    public MapSortById() {
    }
    public MapSortById(boolean whatOrder) { this.whatOrder = whatOrder; }

    public int compare(Map<String, Object> o1, Map<String, Object> o2)
    {
      if (this.whatOrder) {
        return 
          (Integer)o1.get("id") == (Integer)o2.get("id") ? 0 : ((Integer)o1.get("id")).intValue() > ((Integer)o2.get("id")).intValue() ? -1 : 
          1;
      }
      return 
        (Integer)o1.get("id") == (Integer)o2.get("id") ? 0 : ((Integer)o1.get("id")).intValue() > ((Integer)o2.get("id")).intValue() ? 1 : 
        -1;
    }
  }
}