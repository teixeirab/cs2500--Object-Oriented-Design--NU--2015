package connect_n.mocks;

import connect_n.console_ui.IntReader;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * A mock for the {@link IntReader} interface. This allows the test to
 * specify the expected series of prompts provided by the client and the
 * inputs to return to the client.
 */
public class MockIntReader implements IntReader {
  Queue<Interaction> queue = new ArrayDeque<>();

  @Override
  public int nextInt(String prompt, Validator validator) {
    // This works by removing the next expected interaction from the
    // interaction queue and simulating its behavior given the prompt and
    // validator supplied by the client.
    return queue.remove().simulate(prompt, validator);
  }

  /**
   * Adds an interaction to the interaction queue. This consists of the prompt
   * that we expect to the client to provide, and a sequence of user inputs
   * to simulate. Simulation uses the client-provided validator to validate
   * each input. All but the last should fail, and the last should succeed.
   * This way, at each interaction step we can test the client's validation
   * before returning it a valid integer.
   *
   * @param prompt the expected prompt
   * @param inputs the sequence of user inputs to simulate
   * @return {@code this}, for method chaining
   */
  public MockIntReader add(String prompt, int... inputs) {
    queue.add(new Interaction(prompt, inputs));
    return this;
  }

  /**
   * Represents one interaction step.
   */
  private static class Interaction {
    private final String prompt;
    private final int[] inputs;

    Interaction(String prompt, int[] inputs) {
      Objects.requireNonNull(prompt);
      Objects.requireNonNull(inputs);
      assert inputs.length > 0 : "Must provide at least one result";

      this.prompt = prompt;
      this.inputs = inputs;
    }

    int simulate(String actualPrompt, Validator validator) {
      assertEquals(prompt, actualPrompt);

      for (int i = 0; i < inputs.length - 1; ++i) {
        // All but the last result should be invalid, as indicated by the
        // validator returning an error message (rather than null for success).
        assertNotNull(validator.apply(inputs[i]));
      }

      // For the last input, we expect the validator to succeed and then
      // return that input.
      int result = inputs[inputs.length - 1];
      assertNull(validator.apply(result));
      return result;
    }
  }
}
