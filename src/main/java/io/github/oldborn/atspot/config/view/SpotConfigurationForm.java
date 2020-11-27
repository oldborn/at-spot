package io.github.oldborn.atspot.config.view;

import lombok.*;

import javax.swing.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SpotConfigurationForm {
    private JTextField client_id;
    private JTextField client_secret;
    private JTextField redirect_url;
    private JPanel rootPanel;

    public JPanel getRootPanel() {
        return rootPanel;
    }

}
