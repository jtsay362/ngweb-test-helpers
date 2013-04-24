package com.ngweb.test.testngutil;

import java.beans.PropertyDescriptor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;

import static org.testng.Assert.*;

/**
 *
 * @author jtsay
 */
public class TestNGHelper {
  protected TestNGHelper() { }

  public static void verifyMatch
  (
    Object o1,
    Object o2,
    Set<String> propertyNames
  )
  throws Exception
  {
    for (String propertyName : propertyNames)
    {
      assertTrue(ObjectUtils.equals(
        PropertyUtils.getProperty(o1, propertyName),
        PropertyUtils.getProperty(o2, propertyName)), propertyName);
    }
  }
  
  public static void verifyMatch
  (
    Class<?> clazz,
    Object o1,
    Object o2    
  )
  throws Exception
  {
    verifyMatch(clazz, o1, o2, null);
  }

  public static void verifyMatch
  (
    Class<?> clazz,
    Object o1,
    Object o2,    
    Set<String> excludedPropertyNames
  )
  throws Exception
  {
    assertNotNull(o1);
    assertNotNull(o2);

    assertTrue(clazz.isAssignableFrom(o1.getClass()) &&
               clazz.isAssignableFrom(o2.getClass()));

    for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(clazz)) 
    {
      String propertyName = pd.getName();

      if ((excludedPropertyNames == null) ||
          !excludedPropertyNames.contains(propertyName))
      {
        assertTrue(ObjectUtils.equals(
          PropertyUtils.getProperty(o1, propertyName),
          PropertyUtils.getProperty(o2, propertyName)), propertyName);
      }
    }
  }

  /** Because databases don't always have millisecond precision. */
  public static void
  assertDateEquals
  (
    Date d1,
    Date d2
  )
  {
    assertEquals(DATE_FORMAT.format(d1), DATE_FORMAT.format(d2));
  }

  public static final Set<String> ID_VERSION_SET = new HashSet<String>(3);

  static
  {
    ID_VERSION_SET.add("id");
    ID_VERSION_SET.add("version");
  }

  private static DateFormat DATE_FORMAT = new SimpleDateFormat(
    "MM-dd-yyyy HH:mm:ss");
}
