package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MessageUtils {

    public static final String INVALID_STRING = "invalidString";
    public static final String SHORTEST_KEY = "shortest";
    public static final String LARGEST_KEY = "largest";

    private MessageUtils(){}

    public static String getMessage(String[]... messages){
        String[] thirdSatelliteMessage = messages[2];
        String[] message = backTrackMessage(0, messages[0], messages[1]);
        return String.join(" ", backTrackMessage(0, message, thirdSatelliteMessage));
    }

    private static String[] backTrackMessage(int backTrackingStep, String[] firstMessage, String[] secondMessage) {
        if(backTrackingStep < 0)
            return new String[]{INVALID_STRING};

        boolean isValidState = true;
        List<String> wordsList = new ArrayList<>();

        Map<String, String[]> messagesMap = arrangeMessages(firstMessage, secondMessage);
        String[] shortestArray = messagesMap.get(SHORTEST_KEY);
        String[] largestArray = messagesMap.get(LARGEST_KEY);
        int largestArrayIndex = backTrackingStep;

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

        int nextBackTrackingStep = backTrackingStep < largestArray.length ? ++backTrackingStep : -1;

        return backTrackMessage(nextBackTrackingStep, largestArray, shortestArray);
    }

    private static Map<String, String[]> arrangeMessages(String[] firstMessage, String[] secondMessage){
        HashMap<String, String[]> messagesMap;
        if(firstMessage.length > secondMessage.length)
            messagesMap = getArrangedMap(secondMessage, firstMessage);
        else
            messagesMap = getArrangedMap(firstMessage, secondMessage);

        return messagesMap;
    }

    private static HashMap<String, String[]> getArrangedMap(String[] shortest, String[] largest){
        HashMap<String, String[]> arrangedMap = new HashMap<>();
        arrangedMap.put(SHORTEST_KEY, shortest);
        arrangedMap.put(LARGEST_KEY, largest);
        return arrangedMap;
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
