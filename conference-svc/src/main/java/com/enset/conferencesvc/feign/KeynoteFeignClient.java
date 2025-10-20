package com.enset.conferencesvc.feign;

import com.enset.conferencesvc.dto.KeynoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "keynote-service", url = "http://localhost:8081/api")
public interface KeynoteFeignClient {
    @GetMapping("/keynotes/{id}")
    KeynoteDTO getKeynoteById(@PathVariable("id") int id);

    @GetMapping("/keynotes")
    List<KeynoteDTO> getAllKeynotes();
}
