import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.MessageUtils;

public class MessageUtilsTest {

    @Test
    public void getMessage_fromThreeSources_returnsOk(){
        String fullMessage = "este es un mensaje";
        String[] firstMessage = {"", "este", "es", "un", "mensaje"};
        String[] secondMessage = {"este", "", "un", "mensaje"};
        String[] thirdMessage = {"", "", "es", "", "mensaje"};

        String result = MessageUtils.getMessage(firstMessage, secondMessage, thirdMessage);

        Assertions.assertThat(result).isEqualTo(fullMessage);

        result = MessageUtils.getMessage(firstMessage, thirdMessage, secondMessage);

        Assertions.assertThat(result).isEqualTo(fullMessage);

        result = MessageUtils.getMessage(secondMessage, firstMessage, thirdMessage);

        Assertions.assertThat(result).isEqualTo(fullMessage);

        result = MessageUtils.getMessage(secondMessage, thirdMessage, firstMessage);

        Assertions.assertThat(result).isEqualTo(fullMessage);

        result = MessageUtils.getMessage(thirdMessage, secondMessage, firstMessage);

        Assertions.assertThat(result).isEqualTo(fullMessage);

        result = MessageUtils.getMessage(thirdMessage, firstMessage, secondMessage);

        Assertions.assertThat(result).isEqualTo(fullMessage);
    }
}
