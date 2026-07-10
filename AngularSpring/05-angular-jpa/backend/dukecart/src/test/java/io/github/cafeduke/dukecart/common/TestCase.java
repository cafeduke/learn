package io.github.cafeduke.dukecart.common;

import org.testng.annotations.BeforeClass;

import com.github.cafeduke.jget.JGet;
import com.github.cafeduke.jreportng.AbstractTestCase;

import io.github.cafeduke.dukecart.common.util.RequestUtil;
import io.github.cafeduke.dukecart.common.util.TestUtil;

public abstract class TestCase extends AbstractTestCase
{
  public final Class<? extends TestCase> TestClass = getClass();

  public final String TestPrefix = TestUtil.getTestPrefix(TestClass);

  protected JGet jget = null;

  protected RequestUtil reqUtil = null;

  @BeforeClass
  public void beginClass()
  {
    jget = getJGet();
    reqUtil = new RequestUtil(jget, logger, TestClass);
  }

  protected JGet getJGet()
  {
    JGet jget = JGet.getInstance();
    jget.setLogger(logger);
    return jget;
  }
}
