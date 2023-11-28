package com.picpaysimplificado.picpaysimplificado.producers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private UUID userId;
    private String emailTo;
    private String subject;
    private String text;

}
