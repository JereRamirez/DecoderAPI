package com.decoder.service;

import com.decoder.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public String getMessage(String[]... messages){
        return MessageUtils.getMessage(messages);
    }
}
