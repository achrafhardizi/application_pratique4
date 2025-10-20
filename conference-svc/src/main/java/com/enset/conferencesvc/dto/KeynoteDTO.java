package com.enset.conferencesvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeynoteDTO {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String fonction;
}