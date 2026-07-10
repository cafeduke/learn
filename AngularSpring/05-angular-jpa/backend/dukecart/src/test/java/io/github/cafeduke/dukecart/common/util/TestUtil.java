package io.github.cafeduke.dukecart.common.util;

import static io.github.cafeduke.dukecart.common.util.TestProperties.LineSep;
import static io.github.cafeduke.dukecart.common.util.TestProperties.PACKAGE_PREFIX_DOT;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.util.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A static class (private constructor) and only static utility methods to be used by all tests
 */
public class TestUtil
{
  private TestUtil()
  {

  }

  /**
   * @param prefix Additional prefixes to be appended
   * @return Name derived using <b>prefix</b> and {@link #testClass}
   */
  public static String getTestPrefix(Class<?> testClass, String... prefix)
  {
    StringBuilder builder = new StringBuilder(testClass.getName().replace(PACKAGE_PREFIX_DOT, ""));
    for (String token : prefix)
      builder.append(".").append(token);

    return builder.toString();
  }

  /**
   * ---------------------------------------------------------------------------------------------------
   * I/O
   * ---------------------------------------------------------------------------------------------------
   */

  /**
   * Log JSON file by name <b>fname</b>. The file is assumed to be pretty.
   *
   * @param logger Logger
   * @param fname JSON filename
   */
  public static void logJSON(Logger logger, String fname) throws IOException
  {
    logJSONText(logger, asText(fname));
  }

  public static <T> void logObjectAsJSON(Logger logger, T obj) throws IOException
  {
    TestUtil.logJSONText(logger, TestUtil.convertObjectToJSON(obj));
  }

  /**
   * Log JSON text. The text is assumed to be pretty.
   *
   * @param logger
   * @param text
   */
  public static void logJSONText(Logger logger, String text)
  {
    StringBuilder builder = new StringBuilder()
      .append(LineSep)
      .append("<pre>")
      .append(text)
      .append(LineSep)
      .append("</pre>");
    logger.info(builder.toString());
  }

  /**
   * ---------------------------------------------------------------------------------------------------
   * I/O
   * ---------------------------------------------------------------------------------------------------
   */

  /**
   * Invoke toText(new File(fname))
   *
   * @see #asText(File)
   */
  public static String asText(String fname) throws IOException
  {
    return asText(new File(fname));
  }

  /**
   * Read contents of file and return the String
   *
   * @param file File to be read
   * @return Contents of file
   * @throws IOException
   */
  public static String asText(File file) throws IOException
  {
    return Files.readString(file.toPath());
  }

  /**
   * ---------------------------------------------------------------------------------------------------
   * JSON operations
   * ---------------------------------------------------------------------------------------------------
   */

  /**
   * Convert JSON file by name <b>fname</b> to an object of class <b>classDTO</b>
   *
   * @param <T> Type of the class
   * @param fname name of the file
   * @param classDTO DTO class
   * @return an object of type T
   */
  public static <T> T convertJSONToObject(String fname, Class<T> classDTO) throws IOException
  {
    Object obj = new ObjectMapper().readValue(new File(fname), classDTO);
    return classDTO.cast(obj);
  }

  /**
   * Convert object <b>obj</b> to JSON string
   *
   * @param <T> Type of the object
   * @param obj Object
   * @return JSON string representation of the object
   */
  public static <T> String convertObjectToJSON(T obj) throws IOException
  {
    return new ObjectMapper()
      .writerWithDefaultPrettyPrinter()
      .writeValueAsString(obj);
  }

  /**
   * Extract the value at <b>pathJSON</b> from <b>fname</b>
   *
   * @param fname JSON filename
   * @param path Path to navigate in JSON structure
   * @return Extract the value at <b>pathJSON</b> from <b>fname</b>
   */
  public static String getJSONValue(String fname, String path) throws IOException
  {
    String value = new ObjectMapper()
      .readTree(new File(fname))
      .at(path)
      .asText();

    if (value.isEmpty())
      throw new IllegalArgumentException("Path " + path + " not found in JSON" + LineSep + getPrettyJSON(fname));

    return value;
  }

  /**
   * @param fname JSON filename
   * @return formatted JSON output
   * @throws IOException
   */
  public static String getPrettyJSON(String fname) throws IOException
  {
    return new ObjectMapper()
      .readTree(new File(fname))
      .toPrettyString();
  }

  /**
   * Format a JSON file overwriting its contents
   *
   * @param fname
   * @throws IOException
   */
  public static void writePrettyJSON(String fname) throws IOException
  {
    File jsonFile = new File(fname);
    JsonNode jsonNode = new ObjectMapper().readTree(jsonFile);
    new ObjectMapper()
      .writerWithDefaultPrettyPrinter()
      .writeValue(jsonFile, jsonNode);
  }

}
