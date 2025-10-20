package com.enset.conferencesvc.controllers;

import com.enset.conferencesvc.dto.KeynoteDTO;
import com.enset.conferencesvc.models.Conference;
import com.enset.conferencesvc.services.ConferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/conferences")
public class ConferenceController {

    private final ConferenceService conferenceService;

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @GetMapping
    public List<Conference> getAllConferences() {
        return conferenceService.getAllConferences();
    }

    @GetMapping("/{id}")
    public Conference getConference(@PathVariable int id) {
        return conferenceService.getConferenceById(id);
    }

    @GetMapping("/{id}/with-keynotes")
    public ResponseEntity<Map<String, Object>> getConferenceWithKeynotes(@PathVariable int id) {
        Conference conf = conferenceService.getConferenceById(id);
        List<KeynoteDTO> keynotes = conferenceService.getKeynotesForConference(id);

        Map<String, Object> result = new HashMap<>();
        result.put("conference", conf);
        result.put("keynotes", keynotes);

        return ResponseEntity.ok(result);
    }
}
