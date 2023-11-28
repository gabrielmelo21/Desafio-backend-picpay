package com.picpaysimplificado.picpaysimplificado.producers;

import com.picpaysimplificado.picpaysimplificado.dtos.EmailDto;
import lombok.Data;

@Data
public class MessageWrapper {
    private Email email;
    private HTMLSettings htmlSettings;



}