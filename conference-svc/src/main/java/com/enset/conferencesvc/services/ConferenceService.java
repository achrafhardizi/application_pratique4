package com.enset.conferencesvc.services;

import com.enset.conferencesvc.dto.KeynoteDTO;
import com.enset.conferencesvc.feign.KeynoteFeignClient;
import com.enset.conferencesvc.models.Conference;
import com.enset.conferencesvc.repositories.ConferenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final KeynoteFeignClient keynoteFeignClient;

    public ConferenceService(ConferenceRepository conferenceRepository, KeynoteFeignClient keynoteFeignClient) {
        this.conferenceRepository = conferenceRepository;
        this.keynoteFeignClient = keynoteFeignClient;
    }

    public List<Conference> getAllConferences() {
        return conferenceRepository.findAll();
    }

    public Conference getConferenceById(int id) {
        return conferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conference not found"));
    }

    public List<KeynoteDTO> getKeynotesForConference(int conferenceId) {
        Conference conf = getConferenceById(conferenceId);
        return conf.getKeynoteIds().stream()
                .map(keynoteFeignClient::getKeynoteById)
                .collect(Collectors.toList());
    }
}
