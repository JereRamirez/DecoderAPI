package com.decoder.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public abstract class MessageUtils {

    public static final String INVALID_STRING = "invalidString";

    private MessageUtils(){}

    public static String getMessage(String[]... messages){
        String[] thirdSatelliteMessage = messages[2];
        log.info("Starting  to decode message...");
        String[] message = backtrackMessage(0, messages[0], messages[1]);
        log.info("Message obtained from the first two satellites: ");
        logArray(message);
        log.info("Processing message obtained from the first two satellites with the third one...");
        return String.join(" ", backtrackMessage(0, message, thirdSatelliteMessage));
    }

    private static String[] backtrackMessage(int backtrackingStep, String[] firstMessage, String[] secondMessage) {
        log.info("Starting backtracking step: {} with messages: ", backtrackingStep);
        log.info("First message: ");
        logArray(firstMessage);
        log.info("Second message: ");
        logArray(secondMessage);

        if(backtrackingStep < 0){
            log.error("This sequence produced an invalid word...");
            return new String[]{INVALID_STRING};
        }

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
            log.info("Word obtained: {}", word);
            if (word.equals(INVALID_STRING)) {
                log.error("Could not get a valid word from the comparison, breaking iteration...");
                isValidState = false;
                break;
            }
            wordsList.add(word);
            largestArrayIndex++;
        }

        if(isValidState)
            return wordsList.toArray(new String[0]);

        int nextBacktrackingStep = backtrackingStep < largestArray.length ? ++backtrackingStep : -1;

        log.info("Could not find a valid sequence in this step, continuing with the next one...");
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
        log.info("Comparing words: {} {}", firstWord, secondWord);
        String result = INVALID_STRING;
        if(firstWord.equals(secondWord) || secondWord.isBlank())
            result = firstWord;
        else if(firstWord.isBlank())
            result = secondWord;

        return result;
    }

    private static void logArray(String[] message) {
        Stream.of(message).forEach(m -> log.info("{}", m));
    }
}
