package com.picpaysimplificado.picpaysimplificado.producers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HTMLSettings {
    private String backgroundImage;
    private String logoURL;
    private String titleColor;
    private String txtColor;
    private String title;
    private String txt;
    private String actionButton;
}
