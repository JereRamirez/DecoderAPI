package com.decoderapi.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MessageUtilsTest {

    @Test
    public void getMessage_nullMessage_throwsException(){
        Assertions.assertThatThrownBy( ()-> MessageUtils.getMessage(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Los mensajes de los satelites no pueden ser vacios.");
    }

    @Test
    public void getMessage_otherThanThreeMessages_throwsException(){
        Assertions.assertThatThrownBy( ()-> MessageUtils.getMessage(new String[]{" "}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La cantidad de mensajes deben ser 3.");

        Assertions.assertThatThrownBy( ()-> MessageUtils.getMessage(new String[]{" "," "}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La cantidad de mensajes deben ser 3.");

        Assertions.assertThatThrownBy( ()-> MessageUtils.getMessage(new String[]{" ", " ", " ", " "}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La cantidad de mensajes deben ser 3.");
    }

    @Test
    public void getMessage_fromThreeMessages_returnsOk(){
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

    @Test
    public void getMessage_fromThreeInvalidMessagesCombination_throwsException(){
        String[] firstMessage = {"combinacion"};
        String[] secondMessage = {"no"};
        String[] thirdMessage = {"valida"};
        String result = MessageUtils.getMessage(secondMessage, thirdMessage, firstMessage);
        Assertions.assertThat(result).isEqualTo("invalidString");
    }
}
