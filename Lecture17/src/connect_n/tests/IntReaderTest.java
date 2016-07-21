package connect_n.tests;

import connect_n.console_ui.IntReader;
import connect_n.console_ui.IntReader.Validator;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.IntPredicate;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for IntReader.
 */
public class IntReaderTest {
  // Given the validity test that the integer must be less than 5, if the
  // user enters "3" then the IntReader should accept it and }}}:w
  // return 3.
  @Test
  public void test1() throws IOException {
    testNextInt(i -> i < 5, 3, "3");
  }

  // Given the validity test that the integer must be less than 5, if the
  // user enters "6" then the IntReader prompt to try again, and then when
  // the user enters 3 the IntReader should accept and return it.
  @Test
  public void test2() throws IOException {
    testNextInt(i -> i < 5, 3, "6", "3");
  }

  // In this case, the validity predicate accepts integers larger than 20.
  // The expected result is 50. We will simulate the user entering "6",
  // "not a number", "20", and "3", and check that the IntReader correctly
  // rejects each of them. Then it should accept " 50 " and return it.
  @Test
  public void test3() throws IOException {
    testNextInt(i -> i > 20, 50, "6", "not a number", "20", "3", " 50 ");
  }

  static final String PROMPT         = "???";
  static final String ERROR_RESPONSE = "NO";

  /**
   * Given a validity checker, the expected result, and a sequence of user
   * input attempts, this method checks that the IntReader rejects all but
   * the last attempt and then accepts and returns the last one.
   *
   * @param isValid determines what input is valid
   * @param result the expected result
   * @param attempts the simulated user input
   */
  void testNextInt(IntPredicate isValid, int result, String... attempts)
      throws IOException // not really?
  {
    StringBuilder typed = new StringBuilder();
    StringBuilder expected = new StringBuilder();

    for (String attempt : attempts) {
      typed.append(attempt).append('\n');
    }

    for (int i = 0; i < attempts.length - 1; ++i) {
      expected.append(PROMPT).append(ERROR_RESPONSE).append('\n');
    }
    expected.append(PROMPT);

    InputStream in =
        new ByteArrayInputStream(typed.toString().getBytes("UTF8"));
    StringBuilder out = new StringBuilder();

    IntReader reader = IntReader.create(in, out, ERROR_RESPONSE);

    Validator validator = i -> isValid.test(i) ? null : ERROR_RESPONSE;

    assertEquals(result, reader.nextInt(PROMPT, validator));
    assertEquals(expected.toString(), out.toString());
  }
}
