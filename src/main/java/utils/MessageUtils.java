package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MessageUtils {

    public static final String INVALID_STRING = "invalidString";

    private MessageUtils(){}

    public static String getMessage(String[]... messages){
        String[] thirdSatelliteMessage = messages[2];
        String[] message = backtrackMessage(0, messages[0], messages[1]);
        return String.join(" ", backtrackMessage(0, message, thirdSatelliteMessage));
    }

    private static String[] backtrackMessage(int backtrackingStep, String[] firstMessage, String[] secondMessage) {
        if(backtrackingStep < 0)
            return new String[]{INVALID_STRING};

        boolean isValidState = true;
        List<String> wordsList = new ArrayList<>();

        List<String[]> arrengedMessages = arrangeMessages(firstMessage, secondMessage);
        String[] shortestArray = arrengedMessages.get(0);
        String[] largestArray = arrengedMessages.get(1);
        int largestArrayIndex = backtrackingStep;

        for (String oneWord : shortestArray) {
            if (largestArrayIndex == largestArray.length) {
                isValidState = false;
                break;
            }

            String word = decideWordBetween(largestArray[largestArrayIndex], oneWord);

            if (word.equals(INVALID_STRING)) {
                isValidState = false;
                break;
            }
            wordsList.add(word);
            largestArrayIndex++;
        }

        if(isValidState)
            return wordsList.toArray(new String[0]);

        int nextBacktrackingStep = backtrackingStep < largestArray.length ? ++backtrackingStep : -1;

        return backtrackMessage(nextBacktrackingStep, largestArray, shortestArray);
    }

    private static List<String[]> arrangeMessages(String[] firstMessage, String[] secondMessage){
        List<String[]> messagesList;
        if(firstMessage.length > secondMessage.length)
            messagesList = getArrangedList(secondMessage, firstMessage);
        else
            messagesList = getArrangedList(firstMessage, secondMessage);

        return messagesList;
    }

    private static List<String[]> getArrangedList(String[] shortest, String[] largest){
        List<String[]> arrangedList = new ArrayList<>();
        arrangedList.add(shortest);
        arrangedList.add(largest);
        return arrangedList;
    }

    private static String decideWordBetween(String firstWord, String secondWord) {
        String result = INVALID_STRING;
        if(firstWord.equals(secondWord) || secondWord.isBlank())
            result = firstWord;
        else if(firstWord.isBlank())
            result = secondWord;

        return result;
    }
}
