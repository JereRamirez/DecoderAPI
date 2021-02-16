package service;

import org.springframework.stereotype.Service;
import utils.MessageUtils;

@Service
public class MessageService {
    public MessageService() {
    }

    public String getMessage(String[]... messages){
        return MessageUtils.getMessage(messages);
    }
}
