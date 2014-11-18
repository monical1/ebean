package com.avaje.tests.types;

import com.avaje.ebean.BaseTestCase;
import com.avaje.ebean.Ebean;
import com.avaje.tests.model.types.SomePeriodBean;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Period;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 */
public class TestPeriodType extends BaseTestCase {

  @Test
  public void testInsert() {

    SomePeriodBean bean = new SomePeriodBean();
    bean.setPeriod(Period.of(3, 4, 5));
    bean.setAnniversary(MonthDay.of(4, 29));
    Ebean.save(bean);

    SomePeriodBean bean1 = Ebean.find(SomePeriodBean.class, bean.getId());
    assertEquals(bean.getPeriod(), bean1.getPeriod());
    assertEquals(bean.getAnniversary(), bean1.getAnniversary());

    // insert fetch null value
    SomePeriodBean bean2 = new SomePeriodBean();
    Ebean.save(bean2);

    SomePeriodBean bean3 = Ebean.find(SomePeriodBean.class, bean2.getId());
    assertNull(bean3.getPeriod());
    assertNull(bean3.getAnniversary());

    List<SomePeriodBean> anniversaryList = Ebean.find(SomePeriodBean.class)
            .where()
              .eq("anniversary", MonthDay.of(4, 29))
              .eq("period_years", 3)
              .eq("period_months", 4)
            .findList();

    assertEquals(1, anniversaryList.size());

    // must use year 2000 for range predicates
    // ... using 2001 here so not finding anything
    anniversaryList = Ebean.find(SomePeriodBean.class)
            .where()
            .gt("anniversary", Date.valueOf(LocalDate.of(2001,4, 29)))
            .findList();

    assertEquals(0, anniversaryList.size());

    // can use year 2000 for range predicates
    // ... and can use LocalDate to bind
    anniversaryList = Ebean.find(SomePeriodBean.class)
            .where()
            .gt("anniversary", LocalDate.of(2000,4, 22))
            .findList();

    assertEquals(1, anniversaryList.size());

    Ebean.delete(bean);
    Ebean.delete(bean2);
  }

}
