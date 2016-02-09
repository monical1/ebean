package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

import java.sql.Timestamp;

/**
 * Used to generate a timestamp when a bean is inserted.
 */
public class GeneratedInsertTimestamp implements GeneratedProperty, GeneratedWhenCreated {

  /**
   * Return the current time as a Timestamp.
   */
  @Override
  public Object getInsertValue(BeanProperty prop, EntityBean bean, long now) {
    return new Timestamp(now);
  }

  /**
   * Just returns the beans original insert timestamp value.
   */
  @Override
  public Object getUpdateValue(BeanProperty prop, EntityBean bean, long now) {
    return prop.getValue(bean);
  }

  /**
   * Return false.
   */
  public boolean includeInUpdate() {
    return false;
  }

  @Override
  public boolean includeInAllUpdates() {
    return false;
  }

  /**
   * Return true.
   */
  public boolean includeInInsert() {
    return true;
  }

  public boolean isDDLNotNullable() {
    return true;
  }

}
